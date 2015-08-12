package in.sayes.android.khadyam.parser;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.bean.CatagoryBean;
import in.sayes.android.khadyam.bean.CoursesBean;
import in.sayes.android.khadyam.bean.RecipeBean;
import in.sayes.android.khadyam.bean.RecipeBean.Ingredients;
import in.sayes.android.khadyam.bean.RecipeBean.RecipeSteps;
import in.sayes.android.khadyam.bean.ScheduleBean;
import in.sayes.android.khadyam.bean.TipsBean;
import in.sayes.android.khadyam.common.AlertUtility;
import in.sayes.android.khadyam.http.WebserviceKeyValues;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServiceResponceParser {
	static JSONObject resJsonObject;

	public static String loginResponceParser(String responceJSON) {
		Log.i("login", responceJSON);
		String status = "";
		String userId = "";
		String authCode = "";
		String userName = "";
		String resCode = "";
		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);

			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");

				userId = resData.getString("userId");
				authCode = resData.getString("authCode");
				userName = resData.getString("userName");
				resCode = resData.getString("resCode");
				// setting user details on login
				BaseActivity.getInstanceAppPreferences().setUserName(userName);
				BaseActivity.getInstanceAppPreferences().setUserId(userId);
				BaseActivity.getInstanceAppPreferences().setAuth(authCode);

				Log.d("Login", status + "  " + userId + "  " + userName + " "
						+ authCode);
			} else {
                resCode=WebserviceKeyValues.STATUS_FAIL;
				AlertUtility.printStatement("LOGIN RES", status);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return resCode;

	}

	@Nullable
    public static String signupResponceParser(String responceJSON) {
		Log.i("signup", responceJSON);
		@Nullable String status = null;

		try {
			resJsonObject = new JSONObject(responceJSON);
            @Nullable JSONObject resData=null;
            if (resJsonObject.getJSONObject("responseData")!=null){
                 resData = resJsonObject.getJSONObject("responseData");
            }

			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
            //signup success
				String resCode = resData.getString("resCode");
				String userId = resData.getString("userId");
				BaseActivity.getInstanceAppPreferences().setUserId(userId);
				status = resCode;
				Log.d("signup", "status   " + status);
			} else if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_FAIL)){


                if (resData!=null){
                    // user already exist

                    status = resData.getString("resCode");
                    Log.d("signup", "status   " + status);
                }
                Log.d("signup", "status   " + status);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return status;
	}

	@Nullable
    public static String otpResponceParser(String responceJSON) {
		Log.i("otp res", responceJSON);
		@Nullable String status = null;
		@Nullable String resCode = null;
		try {
			resJsonObject = new JSONObject(responceJSON);

			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				String userId = resData.getString("userId");
				String authCode = resData.getString("authCode");
				BaseActivity.getInstanceAppPreferences().setAuth(authCode);
				resCode = resData.getString("resCode");
				Log.d("otp", status + "  " + userId + " " + authCode);
			} else {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				resCode = resData.getString("resCode");

				Log.d("otp", "otp fail " + status + " - " + resCode);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return resCode;
	}

	@NotNull
    public static ArrayList<CatagoryBean> allFreeRecipeCatagoryParser(
			String responceJSON) {
		Log.i("allFreeRecipeCatagoryParser", responceJSON);
		@Nullable String status = null;
		@NotNull ArrayList<CatagoryBean> catagoryList = new ArrayList<CatagoryBean>();

		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				JSONArray recipeCatagoryArray = resData.getJSONArray("recipeCatagory");
				if (recipeCatagoryArray != null) {
					for (int i = 0; i < recipeCatagoryArray.length(); i++) {
						@NotNull CatagoryBean mapData = getParsedCatagoryResult((JSONObject) recipeCatagoryArray
								.get(i));
						catagoryList.add(mapData);
					}
				}

			} else {
				Log.d("otp", "otp fail else" + status);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return catagoryList;
	}

	@NotNull
    public static CatagoryBean getParsedCatagoryResult(@NotNull JSONObject jsonResult) {

		@NotNull CatagoryBean catagoryBean = new CatagoryBean();
		try {
			catagoryBean
					.setCatagoryId(jsonResult.getString("recipeCatagoryId"));
			Log.d("Catagory", "ID " + jsonResult.getString("recipeCatagoryId"));
			catagoryBean.setCatagoryName(jsonResult
					.getString("recipeCatagoryName"));
			Log.d("Catagory",
					"Name " + jsonResult.getString("recipeCatagoryName"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return catagoryBean;
	}

	/*
	 * "recipeList":[ { "recipeId":"1", "recipeName":"Cat1" }, { "recipeId":"2",
	 * "recipeName":"Cat2" } ]
	 */

	@NotNull
    public static ArrayList<RecipeBean> allFreeRecipeParser(String responceJSON) {
		Log.i("allFreeRecipeParser", responceJSON);
		@Nullable String status = null;
		@NotNull ArrayList<RecipeBean> recipeList = new ArrayList<RecipeBean>();

		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				@Nullable JSONArray recipesArray = null;

				recipesArray = resData.getJSONArray("recipeList");
				if (recipesArray != null) {
					for (int i = 0; i < recipesArray.length(); i++) {
						@NotNull RecipeBean recipeBean = getParsedRecipeListResult((JSONObject) recipesArray
								.get(i));
						recipeList.add(recipeBean);
					}
				}

			} else {
				Log.d("recipeList", "status " + status);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return recipeList;
	}

	@NotNull
    public static RecipeBean getParsedRecipeListResult(@NotNull JSONObject jsonResult) {

		@NotNull RecipeBean recipeBean = new RecipeBean();
		try {
			recipeBean.setRecipeId(jsonResult.getString("recipeId"));
			recipeBean.setRecipeName(jsonResult.getString("recipeName"));
			recipeBean.setmImageThumb(jsonResult.getString("recipeImageThumb"));
			recipeBean.setRecipeDetails(jsonResult.getString("recipeDetails"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return recipeBean;
	}

	/*
	 * { "status" : "Success", "responseData" : { "recipeName":"xyz",
	 * "imgUrl":"http://vbnavdvav.hjag/1",
	 * "recipeDetails":"jhajhfjkhakjfhjahfj hjahfj ah", "ingrediants":[ {
	 * "itemName":"qeq", "quantity":"20 gm" }, { "itemName":"qehjkhjq",
	 * "quantity":"60 gm" } ], "steps":[ { "stepTitle":"step 1", "details":
	 * "hajkhfkjhJH  JAHS VHA " }, { "stepTitle":"step 2", "details":
	 * "hajkhfkjhJH " } ], "tips":[ { "tipsName":"jdahjdha", "tipsDetails":
	 * "kajkfajfkajfka" }, { "tipsName":"jdahjdha", "tipsDetails":
	 * "kajkfajfkajfka" } ] } }
	 */
	@NotNull
    public static RecipeBean recipeDetailsParser(String responceJSON) {
		Log.i("recipeDetailsParser", responceJSON);
		@Nullable String status = null;
		@NotNull RecipeBean recipeDetails = new RecipeBean();

		@NotNull ArrayList<RecipeBean.Ingredients> ingredentsArray = new ArrayList<RecipeBean.Ingredients>();
		@NotNull ArrayList<RecipeBean.RecipeSteps> stepsArray = new ArrayList<RecipeBean.RecipeSteps>();
		@NotNull ArrayList<TipsBean> tipsArray = new ArrayList<TipsBean>();
		@NotNull ArrayList<String> recipeImageArray = new ArrayList<String>();

		@NotNull RecipeBean.Ingredients ingredents = new RecipeBean.Ingredients();
		@NotNull RecipeBean.RecipeSteps steps = new RecipeBean.RecipeSteps();
		@NotNull TipsBean tips = new TipsBean();
		@Nullable JSONArray ingrediantsJSONArray = null;
		@Nullable JSONArray stepsJSONArray = null;
		@Nullable JSONArray tipsJSONArray = null;
		@Nullable JSONArray recipeImages = null;

		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				recipeDetails.setRecipeName(resData.getString("recipeName"));
				recipeDetails.setRecipeDetails(resData
						.getString("recipeDetails"));
                if(resData.has("recipeImageThumb")){
                    recipeDetails.setmImageThumb(resData
                            .getString("recipeImageThumb"));
                }

				recipeDetails.setCookingTime(resData.getString("cooking_time"));
				recipeDetails.setPreprationTime(resData.getString("prep_time"));
                if(resData.has("recipeImages")){
				recipeImages = resData.getJSONArray("recipeImages");
				if (recipeImages != null) {
					for (int i = 0; i < recipeImages.length(); i++) {
						@NotNull JSONObject images = (JSONObject) recipeImages.get(i);
						recipeImageArray.add(images.getString("img"));
					}
					recipeDetails.setmImageUrls(recipeImageArray);
				}
            }
				ingrediantsJSONArray = resData.getJSONArray("ingrediants");
				if (ingrediantsJSONArray != null) {
					for (int i = 0; i < ingrediantsJSONArray.length(); i++) {
						ingredents = ingredientsParser((JSONObject) ingrediantsJSONArray
								.get(i));
						ingredentsArray.add(ingredents);
					}
					recipeDetails.setIngredients(ingredentsArray);

				}
				stepsJSONArray = resData.getJSONArray("steps");
				if (stepsJSONArray != null) {
					for (int i = 0; i < stepsJSONArray.length(); i++) {
						steps = recipeStepsParser((JSONObject) stepsJSONArray
								.get(i));
						stepsArray.add(steps);
					}
					recipeDetails.setSteps(stepsArray);
				}
				tipsJSONArray = resData.getJSONArray("tips");
				if (tipsJSONArray != null) {
					for (int i = 0; i < tipsJSONArray.length(); i++) {
						tips = tipsParser((JSONObject) tipsJSONArray.get(i));
						tipsArray.add(tips);
					}
					recipeDetails.setTips(tipsArray);
				}
			} else {
				Log.d("recipe Details", "status " + status);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return recipeDetails;

	}

	@NotNull
    private static RecipeSteps recipeStepsParser(@NotNull JSONObject jsonObject) {
		@NotNull RecipeBean.RecipeSteps steps = new RecipeBean.RecipeSteps();

		try {
			steps.setStepsTitle(jsonObject.getString("stepTitle"));
			steps.setStepsDetails(jsonObject.getString("details"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return steps;

	}

	@NotNull
    private static Ingredients ingredientsParser(@NotNull JSONObject jsonObject) {

		@NotNull RecipeBean.Ingredients ingredents = new RecipeBean.Ingredients();
		try {
			ingredents.setIngredientName(jsonObject.getString("itemName"));
			ingredents.setIngredientsQuantity(jsonObject.getString("quantity"));
			ingredents.setIngredientsUnit(jsonObject.getString("unit"));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return ingredents;
	}

	@NotNull
    public static ArrayList<TipsBean> getAllTipsParser(String responceJSON) {
		Log.i("alltips", responceJSON);
		String status;
		@Nullable JSONArray tipsJSONArray = null;
		@NotNull ArrayList<TipsBean> tipsArray = new ArrayList<TipsBean>();
		@NotNull TipsBean tips = new TipsBean();

		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				tipsJSONArray = resData.getJSONArray("tips");
				if (tipsJSONArray != null) {
					for (int i = 0; i < tipsJSONArray.length(); i++) {
						tips = tipsParser((JSONObject) tipsJSONArray.get(i));
						tipsArray.add(tips);
					}

				}

			} else {
				AlertUtility.printStatement("LOGIN RES", status);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tipsArray;

	}

	/*
	 * { "tips":[ { "tipsName":"jdahjdha", "tipsDetails": "kajkfajfkajfka" }, {
	 * "tipsName":"jdahjdha", "tipsDetails": "kajkfajfkajfka" } ] }
	 */

	@NotNull
    public static TipsBean tipsParser(@NotNull JSONObject jsonObject) {

		@NotNull TipsBean tips = new TipsBean();
		try {
			if (jsonObject.getString("tipsName") != null
					|| jsonObject.getString("tipsName") != "") {
				tips.setTipsName(jsonObject.getString("tipsName"));
			}

			if (jsonObject.getString("tipsDetails") != null
					|| jsonObject.getString("tipsDetails") != "") {
				tips.setTipsDescription(jsonObject.getString("tipsDetails"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return tips;

	}

	/*
	 * { "courseList":[ { "courseName":"qeq", "courseId":"2" }, {
	 * "courseName":"qeqadq", "courseId":"3" } ] }
	 */
	@NotNull
    public static ArrayList<CatagoryBean> myCourseParses(String responceJSON) {
		Log.i("my courses", responceJSON);
		@NotNull ArrayList<CatagoryBean> coursesList = new ArrayList<CatagoryBean>();
		String status;
		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				@Nullable JSONArray courseJSONArray = null;
				courseJSONArray = resData.getJSONArray("courseList");
				if (courseJSONArray != null) {
					for (int i = 0; i < courseJSONArray.length(); i++) {
						@NotNull CatagoryBean course = new CatagoryBean();
						course.setCatagoryId(((JSONObject) courseJSONArray.get(i))
								.getString("courseId"));
						course.setCatagoryName(((JSONObject) courseJSONArray
								.get(i)).getString("courseName"));

						coursesList.add(course);
					}
				}

			} else {
				AlertUtility.printStatement("LOGIN RES", status);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return coursesList;

	}

	@NotNull
    public static ArrayList<CoursesBean> allCoursesParses(String responceJSON) {
		Log.i("all courses", responceJSON);
		@NotNull ArrayList<CoursesBean> coursesList = new ArrayList<CoursesBean>();
		String status;
		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				@Nullable JSONArray courseJSONArray = null;
				courseJSONArray = resData.getJSONArray("courseList");
				if (courseJSONArray != null) {
					for (int i = 0; i < courseJSONArray.length(); i++) {
						@NotNull CoursesBean course = new CoursesBean();
						course.setCourseID(((JSONObject) courseJSONArray.get(i))
								.getString("courseId"));
						course.setCourseName(((JSONObject) courseJSONArray
								.get(i)).getString("courseName"));
						course.setCourseDescription(((JSONObject) courseJSONArray
								.get(i)).getString("courseDescription"));
						course.setCourseDuration(((JSONObject) courseJSONArray
								.get(i)).getString("courseDuration"));
						course.setCourseFees(((JSONObject) courseJSONArray
								.get(i)).getString("courseFeese"));
                        course.setCourseThumbImage(((JSONObject) courseJSONArray
                                .get(i)).getString("courseImage"));

						coursesList.add(course);
					}
				}

			} else {
				AlertUtility.printStatement("LOGIN RES", status);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return coursesList;

	}

	/*
     * {"status":"Success","responseData":{"courseName":"EGGLESS CAKE-1",
     * "courseDescription":"Learn to make and bake delicious cake ",
     * "courseDuration":"1","courseFees":"2000",
     * "courseDetails":[{"title":"day1",
     * "items":[{"recipeId":"1","recipeName":"Orange cake with Orange Sauce","recipeDetails":"A delicious cake with Orange Flavour. It is witx`hout milkmade ",
     * "recipeImageThumb":null},
     * {"recipeId":"4","recipeName":"Apple Cake With Cinnamon","recipeDetails":"There is nothing more soothing than the aroma of freshly baked apple cake with cinnamon!!",
     * "recipeImageThumb":"http:\/\/nwaystech.com\/MMCC\/img\/upload\/recipe\/5544e30d5c6a9_03_lrg_.jpg"}]}]}}
	 */
// todo need to add free recipe image and thumb url da
	@NotNull
    public static CoursesBean courseDetailsByCourseIDParser(String responceJSON) {
		Log.i("course details", responceJSON);
		@NotNull CoursesBean courseDetails = new CoursesBean();
		@NotNull ArrayList<CoursesBean.CourseDetails> courseDailyDetails = new ArrayList<CoursesBean.CourseDetails>();

		String status;
		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");

				courseDetails.setCourseName(resData.getString("courseName"));
				courseDetails.setCourseDescription(resData
						.getString("courseDescription"));
				courseDetails.setCourseDuration(resData
						.getString("courseDuration"));
				courseDetails.setCourseFees(resData.getString("courseFees"));
				JSONArray dailyDetailsArry = resData
						.getJSONArray("courseDetails");
				if (dailyDetailsArry != null) {
					for (int i = 0; i < dailyDetailsArry.length(); i++) {
						@NotNull CoursesBean.CourseDetails dailyDetails = new CoursesBean.CourseDetails();
                        @NotNull ArrayList<RecipeBean> recipeList = new ArrayList<RecipeBean>();
						dailyDetails.setTitle(((JSONObject) dailyDetailsArry
								.get(i)).getString("title"));

						JSONObject dailyItems = dailyDetailsArry.getJSONObject(i);
                        JSONArray recipesArray=dailyItems.getJSONArray("items");
                        //-------------------


                        if (recipesArray != null) {

                            for (int j = 0; j < recipesArray.length(); j++) {

                                @NotNull RecipeBean recipeBean = getParsedRecipeListResult((JSONObject) recipesArray
                                        .get(j));
                                recipeList.add(recipeBean);
                            }
                        }
                        dailyDetails.setItems(recipeList);

                        //--------------------

						courseDailyDetails.add(dailyDetails);
					}
					courseDetails.setCourseDetails(courseDailyDetails);
				}
			} else {

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return courseDetails;

	}

	/*
	 * {"status":"Success","responseData":{"schedules":[
	 * {"schedule_id":null,"course_id":"1","course_name":"course1",
	 * "course_description":"loremipsum loremipsum loremipsum loremipsum ",
	 * "session":null,"weekly_repeat":null,
	 * "days":{"mon":0,"tue":0,"wed":0,"thu":0,"fri":0,"sat":1,"sun":1},
	 * "start_time":"01:01","end_time":"01:01"}]}}
	 */

	@NotNull
    public static ArrayList<ScheduleBean> scheduleDetailsByCourseIdParser(
			String responceJSON, @Nullable String courseName) {
		Log.i("schedule details", responceJSON);
		@Nullable String status = null;
		@NotNull ArrayList<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

		try {
			resJsonObject = new JSONObject(responceJSON);
			status = resJsonObject.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				JSONObject resData = resJsonObject
						.getJSONObject("responseData");
				JSONArray scheduleListArray = resData.getJSONArray("schedules");
				if (scheduleListArray != null) {
					for (int i = 0; i < scheduleListArray.length(); i++) {
						@NotNull ScheduleBean scheduleBean = getParsedScheduleDetails((JSONObject) scheduleListArray
								.get(i));
                        if (courseName!=null){
                            scheduleBean.setmCourseName(courseName);
                        }
						scheduleList.add(scheduleBean);
					}
				}

			}

		} catch (Exception e) {
            e.printStackTrace();
		}

		return scheduleList;

	}

	/*
	 * {"status":"Success","responseData":{"schedules":[
	 * {"schedule_id":null,"course_id":"1","course_name":"course1",
	 * "course_description":"loremipsum loremipsum loremipsum loremipsum ",
	 * "session":null,"weekly_repeat":null,
	 * "days":{"mon":0,"tue":0,"wed":0,"thu":0,"fri":0,"sat":1,"sun":1},
	 * "start_time":"01:01","end_time":"01:01"}]}}
	 */
	@NotNull
    private static ScheduleBean getParsedScheduleDetails(@NotNull JSONObject scheduleJSON) {

		@NotNull ScheduleBean bean = new ScheduleBean();
		try {
			bean.setmScheduleId(scheduleJSON.getString("schedule_id"));
			if ( scheduleJSON.has("course_id") ) {
				bean.setmCourseId(scheduleJSON.getString("course_id"));
			}
			if (scheduleJSON.has("course_name") ) {
				bean.setmCourseName(scheduleJSON.getString("course_name"));
			}
			if (scheduleJSON.has("course_description")) {
				bean.setmCourseDescription(scheduleJSON
						.getString("course_description"));
			}
            if (scheduleJSON.has("duration")) {
                bean.setmCourseDuration(scheduleJSON.getString("duration"));
            }
			bean.setmStartDate(scheduleJSON.getString("start_date"));
			bean.setmWeeklyRepeat(scheduleJSON.getString("weekly_repeat"));
			bean.setmStartTime(scheduleJSON.getString("start_time"));
			bean.setmEndTime(scheduleJSON.getString("end_time"));
			JSONObject days = scheduleJSON.getJSONObject("days");
			bean.setmMonday(days.getString("mon"));
			bean.setmThrusday(days.getString("tue"));
			bean.setmWednesday(days.getString("wed"));
			bean.setmThrusday(days.getString("thu"));
			bean.setmFriday(days.getString("fri"));
			bean.setmSaturday(days.getString("sat"));
			bean.setmSunday(days.getString("sun"));

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return bean;
	}

	/*
	 * {"status":"Success","responseData":{"schedules":[{"schedule_id":"1",
	 * "course_id":"4","course_name":"EGGLESS CAKE-1","course_description":
	 * "Learn to make and bake delicious cake "
	 * ,"session":"June 2015","weekly_repeat"
	 * :"N","days":{"mon":0,"tue":1,"wed":0
	 * ,"thu":0,"fri":0,"sat":0,"sun":0},"start_time"
	 * :"03:05","end_time":"04:05",
	 * "available_seats":"60","isRegistered":""},{"schedule_id"
	 * :"2","course_id":"3"
	 * ,"course_name":null,"course_description":null,"session"
	 * :"March 2015","weekly_repeat"
	 * :"N","days":{"mon":0,"tue":1,"wed":0,"thu":0,
	 * "fri":1,"sat":1,"sun":0},"start_time"
	 * :"06:05","end_time":"08:05","available_seats"
	 * :"0","isRegistered":""},{"schedule_id"
	 * :"3","course_id":"5","course_name":"Eggless Cake 2"
	 * ,"course_description":"Second round of the eggless cake 2"
	 * ,"session":"May 2015"
	 * ,"weekly_repeat":"Y","days":{"mon":1,"tue":0,"wed":0,
	 * "thu":0,"fri":0,"sat"
	 * :0,"sun":0},"start_time":"03:05","end_time":"04:05","available_seats"
	 * :"10","isRegistered":""}]}}
	 */
@NotNull
public static ArrayList<ScheduleBean> getAllScheduleDetailsParsed(String responceJSON){
	
	Log.i("schedule details", responceJSON);
	@Nullable String status = null;
	@NotNull ArrayList<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();

	try {
		resJsonObject = new JSONObject(responceJSON);
		status = resJsonObject.getString(WebserviceKeyValues.STATUS);
		if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
			JSONObject resData = resJsonObject
					.getJSONObject("responseData");
			JSONArray scheduleListArray = resData.getJSONArray("schedules");
			if (scheduleListArray != null) {
				for (int i = 0; i < scheduleListArray.length(); i++) {
					@NotNull ScheduleBean scheduleBean = getParsedScheduleDetails((JSONObject) scheduleListArray
							.get(i));
					scheduleList.add(scheduleBean);
				}
			}

		}

	} catch (Exception e) {
        e.printStackTrace();
	}

	return scheduleList;

}
}

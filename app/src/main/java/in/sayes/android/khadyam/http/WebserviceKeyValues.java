package in.sayes.android.khadyam.http;

import org.jetbrains.annotations.NotNull;

public class WebserviceKeyValues {
	
	@NotNull
    public static String WEBSERVICE_CALL_LOGIN = "logIn";
	public static int REQUEST_CODE_LOGIN = 1;
	
	@NotNull
    public static String WEBSERVICE_CALL_SIGNUP="signUp";
	public static int REQUEST_CODE_SIGNUP=2;
	
	@NotNull
    public static String WEBSERVICE_CALL_GET_OTP="getOTP";
	public static int REQUEST_CODE_GET_OTP=3;
	
	@NotNull
    public static String WEBSERVICE_CALL_OTP_AUTH="otpAuth";
	public static int REQUEST_CODE_OTP_AUTH=4;
	
	//5
	
	
	//6
	
	@NotNull
    public static String WEBSERVICE_CALL_ALL_FREE_RECIPE_CATAGORY="getAllFreeRecipeCatagory";
	public static int REQUEST_CODE_ALL_FREE_RECIPE_CATAGORY=7;

	@NotNull
    public static String WEBSERVICE_CALL_FREE_RECIPE="getAllFreeRecipes";
	public static int REQUEST_CODE_FREE_RECIPE=8;
	
	@NotNull
    public static String WEBSERVICE_CAL_RECIPE_DETAILS="getRecipeDetails";
	public static int REQUEST_CODE_RECIPE_DETAILS=9;
	
	//10
	
	
	
	@NotNull
    public static String WEBSERVICE_CALL_TIPS="getTips";
	public static int REQUEST_CODE_TIPS=11;
	
	@NotNull
    public static String WEBSERVICE_CALL_COURSES="getAllCourses";
	public static int REQUEST_CODE_COURSES=12;
	
	
	@NotNull
    public static String WEBSERVICE_CALL_COURSE_DETAILS="getCourseDetails";
	public static int REQUEST_CODE_COURSE_DETAILS=13;
	
	//14
	@NotNull
    public static String WEBSERVICE_CALL_SCHEDULE_BY_COURSE="getScheduleByCourse";
	public static int REQUEST_CODE_SCHEDULE_BY_COURSE=14;
	
	//15
	@NotNull
    public static String WEBSERVICE_CALL_MY_COURSE="getMyCourses";
	public static int REQUEST_CODE_MY_COURSE=15;

    //16

    @NotNull
    public static String WEBSERVICE_CALL_FUTURE_SCHEDULE="getFutureSchedule";
    public static int REQUEST_CODE_FUTURE_SCHEDULE=16;
	
	public static final String PHONE_NO="userPhoneNo";
	public static final String DEVICE_ID="deviceId";
	public static final String USER_NAME="userName";
	public static final String EMAIL="emailId";
	public static final String USER_ID="userId";
	public static final String AUTH_CODE="authCode";
	public static final String RECIPE_CATAGORY_ID="recipeCatagoryId";
	public static final String RECIPE_ID="recipeId";
	public static final String COURSE_ID="courseId";
	public static final String OTP_CODE="otpCode";
	public static final String STATUS_SUCCESS="success";
	public static final String STATUS_FAIL="Fail";
	public static final String RES_CODE_OTP_AUTH="201";
	public static final String RES_CODE_LOGIN_SUCCESS="202";
	public static final String RES_CODE_USER_EXIST="203";
	public static final String STATUS="status";
    public static final String DEVICE_TYPE="deviceType";
    public static final String APP_ID="appId";
    public static final String APP_USER_TYPE="adminUserType";

}

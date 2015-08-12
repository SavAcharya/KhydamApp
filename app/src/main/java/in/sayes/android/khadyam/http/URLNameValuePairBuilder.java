package in.sayes.android.khadyam.http;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;

import in.sayes.android.khadyam.common.AppConstants;

public class URLNameValuePairBuilder {

@NotNull
private static String TAG = "URLNameValuePairBuilder";

	
	
@NotNull
public static List<NameValuePair> setLoginParam(String mobileNo, String deviceId) {

		@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.PHONE_NO, mobileNo));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_ID,deviceId));
        postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_TYPE, AppConstants.DEVICE_TTYPE));
        postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));
		
		return postParameters;
	}
	
@NotNull
public static List<NameValuePair> setSignUpParam(String userName, String email, String mobileNo,String deviceId){
		
		
		@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.PHONE_NO, mobileNo));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_ID,deviceId));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_NAME, userName));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.EMAIL,email));
        postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_TYPE, AppConstants.DEVICE_TTYPE));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_USER_TYPE, AppConstants.APP_USER_TYPE));



        return postParameters;
		
	}
	
	
@NotNull
public static List<NameValuePair> setCommonRequestParams(String userId,String authCode){
		
		
		@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.clear();
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;
}
	
@NotNull
public static List<NameValuePair> setFreeRecipesRequestParams(String userId,String authCode, String catagoryId){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.RECIPE_CATAGORY_ID, catagoryId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;
}	

@NotNull
public static List<NameValuePair> setRecipeDetailsRequestParams(String userId,String authCode, String recipeId){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.RECIPE_ID, recipeId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;
}
	
@NotNull
public static List<NameValuePair> setAllCoursesRequestParams(String userId,String authCode){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;
}

@NotNull
public static List<NameValuePair> setAllTipsRequestParams(String userId){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));


    return postParameters;
}

@NotNull
public static List<NameValuePair> setCourseDetailsRequestParam(String userId,
		String authCode, String courseId) {
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.COURSE_ID, courseId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;

}

@NotNull
public static List<NameValuePair> setScheduleListRequestParam(String userId,
		String authCode, String courseId) {
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.AUTH_CODE, authCode));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.COURSE_ID, courseId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));

    return postParameters;

}

@NotNull
public static List<NameValuePair> setOTPRequestParams(String userId){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userId));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));


    return postParameters;
}

@NotNull
public static List<NameValuePair> setOTPAuthRequestParams(String OTP,String deviceID,String userID){
	
	
	@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.clear();
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.OTP_CODE, OTP));

	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_ID, deviceID));
	postParameters.add(new BasicNameValuePair(WebserviceKeyValues.USER_ID, userID));
    postParameters.add(new BasicNameValuePair(WebserviceKeyValues.APP_ID, AppConstants.APPLICATION_NO));


    return postParameters;

}

}


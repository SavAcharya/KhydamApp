package in.sayes.android.khadyam.prefrence;

import in.sayes.android.khadyam.common.AlertUtility;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.jetbrains.annotations.NotNull;


public class AppPreferences {

	private static final String CLASS_NAME = "AppPreferences";
	private static String APP_SHARED_PREFS;
	private static final String KEY_PREFIX = "APP_PREFERENCE_";
	
	private static final String IS_FIRST = KEY_PREFIX+"is_first";
	private static final String IS_LOGIN = KEY_PREFIX+"is_login";
	private static final String USER_NAME = KEY_PREFIX+"user_name";
	private static final String USER_ID = KEY_PREFIX+"user_id";
	
	private static final String AUTH_CODE = KEY_PREFIX+"auth_code";
	
	
	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;
	
	
	public AppPreferences(@NotNull Context context) {

		APP_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(APP_SHARED_PREFS,Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	
	public void setFirst(boolean value)
	{
		mPrefsEditor.putBoolean(IS_FIRST, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setFirst=" + value);
	}
	public boolean isFirst() {
		boolean value = mPrefs.getBoolean(IS_FIRST, true);
		AlertUtility.printStatement(CLASS_NAME, "isFirst=" + value);
		return value;
	}
	
	public void setUserName(String value) {
		mPrefsEditor.putString(USER_NAME, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setName = " + value);
	}

	public String getUserName() {
		String value = mPrefs.getString(USER_NAME, "");
		AlertUtility.printStatement(CLASS_NAME, "getName=" + value);
		return value;
	}
	
	public boolean isLogin() {
		boolean value = mPrefs.getBoolean(IS_LOGIN, false);
		AlertUtility.printStatement(CLASS_NAME, "isLogin=" + value);
		return value;
	}

	public void setLogin(boolean value) {
		mPrefsEditor.putBoolean(IS_LOGIN, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setLogin = " + value);
	}
	
	public void setAuth(String value) {
		mPrefsEditor.putString(AUTH_CODE, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setAuth = " + value);
	}

	public String getAuth() {
		String value = mPrefs.getString(AUTH_CODE, "");
		AlertUtility.printStatement(CLASS_NAME, "getAuth=" + value);
		return value;
	}
	
	public void setUserId(String value) {
		mPrefsEditor.putString(USER_ID, value);
		mPrefsEditor.commit();
		AlertUtility.printStatement(CLASS_NAME, "setUserId = " + value);
	}

	public String getUserId() {
		String value = mPrefs.getString(USER_ID, "");
		AlertUtility.printStatement(CLASS_NAME, "getUserId=" + value);
		return value;
	}
	
}

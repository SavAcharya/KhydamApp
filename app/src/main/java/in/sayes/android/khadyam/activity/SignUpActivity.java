package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.AlertUtility;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

import static in.sayes.android.khadyam.http.WebserviceKeyValues.RES_CODE_USER_EXIST;

public class SignUpActivity extends BaseActivity implements OnClickListener{

	@NotNull
    private String TAG="SignUp Activity";
	
	private EditText mUserName;
	private EditText mPhoneNo;
	private EditText mEmail;
	private Button mSignup;
	private Button mLogIn;
	private Activity context;
    private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=(Activity)SignUpActivity.this;
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_sign_up);
        dialog=new ProgressDialog(context);
		setupViews();
	}

	private void setupViews() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mUserName=(EditText) findViewById(R.id.edt_signup_name);
		mEmail=(EditText) findViewById(R.id.edt_signup_email);
		mPhoneNo=(EditText) findViewById(R.id.edt_signup_phone);
		mSignup=(Button) findViewById(R.id.btn_signup_send);
		mLogIn=(Button) findViewById(R.id.btn_signup_login);
		mSignup.setOnClickListener(this);
		mLogIn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(@NotNull View v) {
		if (v.getId()==R.id.btn_signup_send){
			doSignUp();
		}else if (v.getId()==R.id.btn_signup_login) {
			goToLoginPage();
		}
			
		
	}

private void goToLoginPage() {
		
	@NotNull Intent i= new Intent(SignUpActivity.this, LoginActivity.class);
	startActivity(i);
    finish();
	}

private void doSignUp() {
	
	@NotNull String userNameString=mUserName.getText().toString().trim();
	String emailString=mEmail.getText().toString();
	@NotNull String phoneString=mPhoneNo.getText().toString().trim();
	String deviceId=Utils.getDeviceId(SignUpActivity.this);
	if(dataValidation(userNameString,emailString,phoneString)){
	httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_SIGNUP, URLNameValuePairBuilder.setSignUpParam(userNameString,emailString,phoneString,deviceId),WebserviceKeyValues.REQUEST_CODE_SIGNUP, AsyncHttpRequest.Type.POST, null);
	}
	
	}


private Boolean dataValidation(@Nullable String userNameString, @Nullable String emailString, @Nullable String phoneString) {
	boolean isValidate = true;
	
	if(userNameString==null||userNameString.equals("")){
		AlertUtility.showToast(context, "Enter User Name");
		
	//	mUserName.setError(getResources().getText(R.string.error));
		isValidate=false;
	}else if(emailString==null||emailString.equals("")) {
		AlertUtility.showToast(context, "Ener your mail id");
		//mUserName.setError(getResources().getText(R.string.error));
		isValidate=false;
	}else if (!Utils.isValidEmailAddress(emailString)) {
		AlertUtility.showToast(context, "Enter a valid mail id");
		//mEmail.setError(getResources().getText(R.string.error));
		isValidate=false;
	}else if (phoneString==null||phoneString.equals("")||phoneString.length()!=10) {
		AlertUtility.showToast(context, "Enter your 10 digit Phone No");
		//mPhoneNo.setError(getResources().getText(R.string.error));
		isValidate=false;
	}
	
	return isValidate;
	
	
}



@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	
	super.onHTTPRequestCompleted(response, requestCode);
	
	
	if(requestCode==WebserviceKeyValues.REQUEST_CODE_SIGNUP){
        dialog.dismiss();
		@Nullable String status=ServiceResponceParser.signupResponceParser(response);
		if(status.equalsIgnoreCase(WebserviceKeyValues.RES_CODE_OTP_AUTH)){
			@NotNull Intent i= new Intent(SignUpActivity.this, OTPActivity.class);
			startActivity(i);
			finish();
		}else if (status.equalsIgnoreCase(RES_CODE_USER_EXIST)) {
			// if user already exist move to login page
			@NotNull Intent i= new Intent(SignUpActivity.this, LoginActivity.class);
			startActivity(i);
			finish();
		}else if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_FAIL)){

            AlertUtility.showAlertDialog(context, "Login Fail", "Please try after some time", true);

        }

		Log.d(TAG, response);
	}
}
    @Override
    public void onHTTPRequestError(Exception e, int requestCode) {

        super.onHTTPRequestError(e, requestCode);

        if (requestCode==WebserviceKeyValues.REQUEST_CODE_SIGNUP) {

            dialog.setMessage("Please try again...");
            dialog.show();
        }
    }

    @Override
    public void onHTTPRequestStarted(int mRequestCode) {
        // TODO Auto-generated method stub
        super.onHTTPRequestStarted(mRequestCode);


        if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_SIGNUP) {

            dialog.setMessage("Please wait ...");
            dialog.show();
        }
    }
@Override
public void onBackPressed() {
	return;
}
	
}

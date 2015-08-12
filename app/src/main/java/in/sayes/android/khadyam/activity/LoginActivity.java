package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.AlertUtility;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class LoginActivity extends BaseActivity implements OnClickListener{
	@NotNull
    private String TAG="Login Activity";
private Button mBtnLogin;
private EditText mEdtPhoneNo;
private TextView mTxtLbl;
private ProgressDialog dialog;
private Activity context;
private RelativeLayout mLayoutSignup;
private Button mBtnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.fullScreenActivity(LoginActivity.this);
		context=(Activity)LoginActivity.this;
		dialog=new ProgressDialog(context);
		setContentView(R.layout.activity_login);
		mBtnLogin=(Button)findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
		mEdtPhoneNo=(EditText)findViewById(R.id.edt_login_phoneNo);
		mLayoutSignup=(RelativeLayout) findViewById(R.id.layour_loging_goToSignup);
		
		mBtnSignUp=(Button) findViewById(R.id.btn_signup_send);
		mBtnSignUp.setOnClickListener(this);
		mTxtLbl=(TextView)findViewById(R.id.txt_login_lbl);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
	}
	@Override
	public void onClick(@NotNull View v) {
		int id=v.getId();
		switch (id) {
		case R.id.btn_login:
			login();
			break;
		
		case R.id.btn_signup_send:
			//Go to signup activity back
			@NotNull Intent i = new Intent(context, SignUpActivity.class);
			startActivity(i);
			finish();
			
			break;
		}
		
	}
	private void login() {
		//mEdtPhoneNo.setText("9876132435");
		@NotNull String phoneNo=mEdtPhoneNo.getText().toString().trim();
		String deviceId=Utils.getDeviceId(LoginActivity.this);
		//call webservice for login
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_LOGIN, URLNameValuePairBuilder.setLoginParam(phoneNo, deviceId),WebserviceKeyValues.REQUEST_CODE_LOGIN, AsyncHttpRequest.Type.POST, null);
		
	}
	
	@Override
	public void onHTTPRequestError(Exception e, int requestCode) {
	
		super.onHTTPRequestError(e, requestCode);

		if (requestCode==WebserviceKeyValues.REQUEST_CODE_LOGIN) {
		
			dialog.setMessage("Please try again...");
			dialog.show();
		}
	}
	
	@Override
	public void onHTTPRequestStarted(int mRequestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestStarted(mRequestCode);
		

		if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_LOGIN) {
		
			dialog.setMessage("Please wait ...");
			dialog.show();
		}
	}
	
	
	@Override
		public void onHTTPRequestCompleted(String response, int requestCode) {
			// TODO Auto-generated method stub
		
		if(requestCode==WebserviceKeyValues.REQUEST_CODE_LOGIN){
			String status;
			status=ServiceResponceParser.loginResponceParser(response);
			if (status.equalsIgnoreCase(WebserviceKeyValues.RES_CODE_LOGIN_SUCCESS)) {
				//on login success with same deviceid 
				@NotNull Intent i= new Intent(LoginActivity.this, DashBoardActivity.class);
				startActivity(i);
				finish();
				BaseActivity.getInstanceAppPreferences().setFirst(false);
				
				
			}
				else if (status.equalsIgnoreCase(WebserviceKeyValues.RES_CODE_OTP_AUTH)) {
					//on device id changed with registered phone no
					@NotNull Intent i= new Intent(LoginActivity.this, OTPActivity.class);
					startActivity(i);
					finish();
				}
				else if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_FAIL)) {
					//on login fail
					dialog.dismiss();
					AlertUtility.showAlertDialog(context, "Login Fail", "Please try after some time", true);
					
					
				}
			
			}
			
			
		}


    @Override
    public void onBackPressed() {

    }
}

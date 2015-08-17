package in.sayes.android.khadyam.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class OTPActivity extends BaseActivity implements OnClickListener{
	Activity context;
	Button mSendOTPButton;
	Button mRetryOTPButton;
	TextView mErrorText;
	EditText mOTPEditText;
	private ProgressDialog dialog;
    private BroadcastReceiver mOTPBroadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=(Activity)OTPActivity.this;

		dialog=new ProgressDialog(context);
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_otp);



		initViews();

      mOTPBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                OTPActivity.this.receivedBroadcast(intent);
            }
        };
	}



    private void receivedBroadcast(Intent intent) {

        {
            // TODO Auto-generated method stub

            if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            if (msg_from.equalsIgnoreCase(getResources().getString(R.string.otp_msg))){
                                String msgBody = msgs[i].getMessageBody();
                                String[] parts = msgBody.split(" ");
                                String OTP = parts[(parts.length)-1 ];
                                mOTPEditText.setText(OTP);
                            }

                        }
                    }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }

    }

    private void initViews() {

		mErrorText=(TextView) findViewById(R.id.txt_OTP_errorMsg);
		mOTPEditText=(EditText) findViewById(R.id.edt_otpNo);
		mSendOTPButton=(Button) findViewById(R.id.btn_otp_send);
		mRetryOTPButton=(Button) findViewById(R.id.btn_otp_retry);
		mRetryOTPButton.setOnClickListener(this);
		mSendOTPButton.setOnClickListener(this);
		mRetryOTPButton.setVisibility(View.INVISIBLE);
		
		
	}

	@Override
	public void onClick(@NotNull View v) {
		switch (v.getId()) {
		case R.id.btn_otp_retry:
			// retry - getOTP
getOTPCode(BaseActivity.getInstanceAppPreferences().getUserId());
			break;
		case R.id.btn_otp_send:
			// send otp code for verification - getAuth
	@NotNull String OTP=	mOTPEditText.getText().toString().trim();
	String deviceId=Utils.getDeviceId(context);
	String userId=BaseActivity.getInstanceAppPreferences().getUserId();
	if(Utils.isNotNullOrEmpty(OTP)) {
		
		validateOTP(OTP,deviceId,userId);
	
	}
			else {
				mOTPEditText.setError("Enter a valid OTP");
				mOTPEditText.setText("");
				
			}

			break;
		
		}

	}
private void validateOTP(String OTP, String deviceId, String userId) {
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_OTP_AUTH, URLNameValuePairBuilder.setOTPAuthRequestParams(OTP,deviceId, userId), WebserviceKeyValues.REQUEST_CODE_OTP_AUTH, AsyncHttpRequest.Type.POST, null);
		
	}

private void getOTPCode(String userId) {
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_GET_OTP, URLNameValuePairBuilder.setOTPRequestParams(userId), WebserviceKeyValues.REQUEST_CODE_GET_OTP, AsyncHttpRequest.Type.POST, null);
		
	}

@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestCompleted(response, requestCode);
	
	if(requestCode==WebserviceKeyValues.REQUEST_CODE_GET_OTP) {
		//clear edit text 
		mOTPEditText.setText("");
		mErrorText.setText("");
		mRetryOTPButton.setVisibility(View.GONE);
		mSendOTPButton.setVisibility(View.VISIBLE);
		dialog.dismiss();
	}else if(requestCode==WebserviceKeyValues.REQUEST_CODE_OTP_AUTH) {
		@Nullable String status=ServiceResponceParser.otpResponceParser(response);
		//201
		if(status.equalsIgnoreCase(WebserviceKeyValues.RES_CODE_OTP_AUTH) ){
			//otp error
			mErrorText.setText("Pleas retry to get new OTP");
			
			mRetryOTPButton.setVisibility(View.VISIBLE);
			mSendOTPButton.setVisibility(View.GONE);
			mOTPEditText.setText("");
			mOTPEditText.setEnabled(false);
			
		}else if (status.equalsIgnoreCase(WebserviceKeyValues.RES_CODE_LOGIN_SUCCESS)) {
			BaseActivity.getInstanceAppPreferences().setFirst(false);
			
			@NotNull Intent i=new Intent(OTPActivity.this, DashBoardActivity.class);
		startActivity(i);
		finish();
		}
		dialog.dismiss();
	}
	
	
}
@Override
public void onHTTPRequestError(Exception e, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestError(e, requestCode);
	if (requestCode==WebserviceKeyValues.REQUEST_CODE_OTP_AUTH) {
		
		dialog.setMessage("Please try again...");
		dialog.show();
	}
}
@Override
public void onHTTPRequestStarted(int mRequestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestStarted(mRequestCode);

	if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_OTP_AUTH) {
	
		dialog.setMessage("Please wait ...");
		dialog.show();
	}
}


}

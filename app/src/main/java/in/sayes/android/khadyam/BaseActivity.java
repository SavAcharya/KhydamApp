/**
 * 
 */
package in.sayes.android.khadyam;

import in.sayes.android.khadyam.common.AlertUtility;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.AsyncHttpRequest.RequestListener;
import in.sayes.android.khadyam.http.AsyncHttpRequest.Type;
import in.sayes.android.khadyam.prefrence.AppPreferences;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntityBuilder;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @author sourav
 *
 */
public class BaseActivity extends FragmentActivity implements RequestListener{
	
	private static AppPreferences appPreferences;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private boolean instanceStateSaved;
	Activity mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appPreferences=new AppPreferences(this);
		mContext=(Activity)BaseActivity.this;
		/*BaseActivity.getInstanceAppPreferences().setAuth(AppConstants.AUTHCODE);
		BaseActivity.getInstanceAppPreferences().setUserId(AppConstants.USERID);*/
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}
	public static AppPreferences getInstanceAppPreferences() {
		
		return appPreferences;
	}
	
	   protected void httpCall(String baseUrl, String functionName, List<NameValuePair>params,int requestCode,Type type,MultipartEntityBuilder reqEntity)
	 	{
	 			
	 			AsyncHttpRequest mAppRequest = new AsyncHttpRequest(this, baseUrl, functionName, params,requestCode, type, reqEntity);
	 			mAppRequest.setRequestListener(this);
	 			mAppRequest.execute();
	 			
	 	}
	 	
	 	
	 	
	 	@Override
	 	public void onHTTPRequestCompleted(String response,int requestCode) {
	 		    

	 	}

	 	@Override
	 	public void onHTTPRequestError(Exception e,int requestCode) {
	 		AlertUtility.showToast(this, getResources().getString(R.string.async_task_no_internet));
	 		
	 	}

	 	@Override
		public void onHTTPRequestStarted(int mRequestCode) {
			// TODO Auto-generated method stub
			
		}}

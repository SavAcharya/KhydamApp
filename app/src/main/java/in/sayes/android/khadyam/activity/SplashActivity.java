package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.Utils;


public class SplashActivity extends BaseActivity {

	private static final int SPLASH_DISPLAY_TIME = 3000; 
	private final Handler handler = new Handler();	
	private Activity context;
	
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (Activity)SplashActivity.this;
        
        Utils.fullScreenActivity(context);
        setContentView(R.layout.activity_splash); 
        handler.postDelayed(runnableSplash, SPLASH_DISPLAY_TIME);
        // for testing
       /* BaseActivity.getInstanceAppPreferences().setAuth(AppConstants.AUTHCODE);
		BaseActivity.getInstanceAppPreferences().setUserId(AppConstants.USERID);*/
        /*JsonParser.readfile(DashBoardActivity.this,"signup");
		JsonParser.readfile(DashBoardActivity.this,"otp");
		JsonParser.readfile(DashBoardActivity.this,"login");
        JsonParser.readfile(SplashActivity.this,"getCatagory");

        JsonParser.readfile(SplashActivity.this,"recipeDetails");
        */
    }
	
	@NotNull
    private Runnable runnableSplash = new Runnable() {
    	public void run() {   		    		
    		handler.removeCallbacks(runnableSplash);    		
    		if(BaseActivity.getInstanceAppPreferences().isFirst()){
    			@NotNull Intent intent = new Intent(SplashActivity.this,SignUpActivity.class);
        		startActivity(intent);    	
        		finish();
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    		}else{
    			
        		@NotNull Intent intent = new Intent(SplashActivity.this,DashBoardActivity.class);
        		startActivity(intent);    	
        		finish();
        		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    		
    		}
    		
    		
    	}
    };
 }

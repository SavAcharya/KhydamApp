package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.os.Bundle;

import org.apache.http.client.methods.HttpGet;
import org.jetbrains.annotations.Nullable;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.Utils;

public class ProfileActivity extends BaseActivity{

	Activity context;
	@Nullable
    private HttpGet mGet = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=(Activity)ProfileActivity.this;
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_profile);
	}
		
	}
	


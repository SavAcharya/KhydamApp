/**
 * 
 */
package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;

/**
 * @author sourav
 *
 */
public class DashBoardActivity extends Activity implements OnClickListener{

	
	private Button mHome;
	private Button mCourses;
	private Button mTips;
	private Button mFreeRecipes;
	private Button mContactUs;
	private Button mProfile;
	private Button mSchedule;
	private Button mMyCourses;
	private Button mCatering;
	private Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=(Activity)DashBoardActivity.this;
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_dashboard);
		
		
		
		setupView();
	}

	

	@Override
	public void onClick(@NotNull View v) {
		int id=v.getId();
		switch (id) {
		case R.id.btn_dash_catering:
			openActivity(SecondBusinessActivity.class,"");
			break;
case R.id.btn_dash_contact_us:
			openActivity(ContactUsActivity.class,"");
			
			break;
case R.id.btn_dash_courses:
	openActivity(CoursesActivity.class,"");
	
	break;
case R.id.btn_dash_free_recipes:
	openActivity(CatagoryListActivity.class,AppConstants.FREE_RECIPE);
	
	break;
case R.id.btn_dash_home:
	openActivity(HomeActivity.class,"");
	break;
case R.id.btn_dash_my_courses:
	openActivity(MyCoursesActivity.class,"");
	break;
case R.id.btn_dash_profile:
	openActivity(ProfileActivity.class,"");
	break;
case R.id.btn_dash_schidule:
	openActivity(ScheduleDetailsActivity.class,"");
	break;
case R.id.btn_dash_tips:
	openActivity(TipsActivity.class,"");
	break;
		
		}
		
	}

	
	
	
	
	private void openActivity(Class<?> calledActivity,String extras) {
	    @NotNull Intent myIntent = new Intent(this, calledActivity);
	    myIntent.putExtra(AppConstants.PARENT_ACTIVITY, extras);
	    this.startActivity(myIntent);
	    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}



	private void setupView() {


		mCatering=(Button) findViewById(R.id.btn_dash_catering);
		mContactUs=(Button) findViewById(R.id.btn_dash_contact_us);
		mCourses=(Button) findViewById(R.id.btn_dash_courses);
		mFreeRecipes=(Button) findViewById(R.id.btn_dash_free_recipes);
		mHome=(Button) findViewById(R.id.btn_dash_home);
		mMyCourses=(Button) findViewById(R.id.btn_dash_my_courses);
		mProfile=(Button) findViewById(R.id.btn_dash_profile);
		mSchedule=(Button) findViewById(R.id.btn_dash_schidule);
		mTips=(Button) findViewById(R.id.btn_dash_tips);
		
		mCatering.setOnClickListener(this);
		mContactUs.setOnClickListener(this);
		mCourses.setOnClickListener(this);
		mFreeRecipes.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mMyCourses.setOnClickListener(this);
		mProfile.setOnClickListener(this);
		mSchedule.setOnClickListener(this);
		mTips.setOnClickListener(this);
		
	}
	
	
}

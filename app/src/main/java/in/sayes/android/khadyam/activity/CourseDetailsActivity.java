package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.CourseDetailsAdapter;
import in.sayes.android.khadyam.bean.CoursesBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class CourseDetailsActivity extends BaseActivity implements OnClickListener{

	private static final String TAG = "CourseDetailsActivity";
	private Activity context;
	private ProgressDialog dialog;
	private String courseId;
private CoursesBean mCourse;
private CourseDetailsAdapter mRecipesListAdapter;
private ArrayList<CoursesBean.CourseDetails> mCourseDetailsRecipes;
private TextView mCourseTitle;
private TextView mCourseDuration;
private TextView mCourseDescription;
private TextView mCourseFees;
    private LinearLayout mCourseDurationFeesLayout;
private ExpandableListView mCoursedailyRecipes;
    private String parentActivity;
    private Button viewScheduleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = (Activity) CourseDetailsActivity.this;
		Utils.fullScreenActivity(context);
		dialog = new ProgressDialog(context);
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            parentActivity = extras.getString(AppConstants.PARENT_ACTIVITY);
            // and get whatever type user account id is
        }

		courseId = getIntent().getExtras().getString(AppConstants.COURSE_ID);
		Log.i(TAG, courseId);
		intiViews();
		loadCourseDetails();
	}

	private void loadCourseDetails() {
		httpCall(AppConstants.URL_BASE,
				WebserviceKeyValues.WEBSERVICE_CALL_COURSE_DETAILS,
				URLNameValuePairBuilder.setCourseDetailsRequestParam(
						BaseActivity.getInstanceAppPreferences().getUserId(),
						BaseActivity.getInstanceAppPreferences().getAuth(),
						courseId),
				WebserviceKeyValues.REQUEST_CODE_COURSE_DETAILS,
				AsyncHttpRequest.Type.POST, null);

	}

	private void intiViews() {
setContentView(R.layout.activity_course_details);
        mCourseDurationFeesLayout= (LinearLayout) findViewById(R.id.linLayout_CourseDurationFees);
mCoursedailyRecipes=(ExpandableListView) findViewById(R.id.list_courseDetails_dailyRecipes);
mCourseTitle=(TextView) findViewById(R.id.txtHeader);
mCourseDescription=(TextView) findViewById(R.id.txt_courseDetailsDescription);
mCourseDuration=(TextView) findViewById(R.id.txt_courseDetailsDuration);
mCourseFees=(TextView) findViewById(R.id.txt_courseDetailsFees);
        viewScheduleButton=(Button)findViewById(R.id.btn_viewCourseSchedule);
        viewScheduleButton.setOnClickListener(this);

        if (parentActivity.equals(AppConstants.MYCOURSE_ACTIVITY)){
            mCourseDurationFeesLayout.setVisibility(View.GONE);

        }
        mCourseDescription.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	public void onHTTPRequestStarted(int mRequestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestStarted(mRequestCode);

		if (mRequestCode == WebserviceKeyValues.REQUEST_CODE_COURSE_DETAILS) {

			dialog.setMessage("Loading all catagories please wait ...");
			dialog.show();
		}

	}

	@Override
	public void onHTTPRequestCompleted(String response, int requestCode) {

		super.onHTTPRequestCompleted(response, requestCode);
		if (requestCode == WebserviceKeyValues.REQUEST_CODE_COURSE_DETAILS) {

			mCourse=ServiceResponceParser.courseDetailsByCourseIDParser(response);
			mCourseTitle.setText(mCourse.getCourseName());
			mCourseDescription.setText(mCourse.getCourseDescription());

			mCourseDetailsRecipes=mCourse.getCourseDetails();
            if (parentActivity.equals(AppConstants.COURSES_ACTIVITY)){
                //TODO patch work to show Day(s) in course duration.
                mCourseDuration.setText(mCourse.getCourseDuration()+"Day(s)");
                mCourseFees.setText(mCourse.getCourseFees());
                mRecipesListAdapter=new CourseDetailsAdapter(context,mCourseDetailsRecipes,false );
                mCoursedailyRecipes.setAdapter(mRecipesListAdapter);
            }else{
                mRecipesListAdapter=new CourseDetailsAdapter(context,mCourseDetailsRecipes,true );
                mCoursedailyRecipes.setAdapter(mRecipesListAdapter);
            }

			dialog.dismiss();
			
		}

	}

	@Override
	public void onHTTPRequestError(Exception e, int requestCode) {
	
		super.onHTTPRequestError(e, requestCode);

		

	}

    @Override
    public void onClick(@NotNull View view) {
        int id=view.getId();
        if (id==R.id.btn_viewCourseSchedule){

            @NotNull Intent i= new Intent(CourseDetailsActivity.this, ScheduleDetailsActivity.class);

//todo remove course name - change webcervice responce
            i.putExtra(AppConstants.COURSES_NAME, mCourse.getCourseName());

            i.putExtra(AppConstants.COURSE_ID, courseId);
            i.putExtra(AppConstants.PARENT_ACTIVITY,AppConstants.COURSES_ACTIVITY);
            startActivity(i);


        }
    }
}

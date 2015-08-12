package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.CourseListAdapter;
import in.sayes.android.khadyam.bean.CoursesBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class CourseListForScheduleActivity extends BaseActivity implements OnItemClickListener{
	
	private Activity context;
	private ListView mListView;
	private ArrayList<CoursesBean> mCoursesList;
	private CoursesBean mCourse;
	private CourseListAdapter mCoursesAdapter;
	private ProgressDialog dialog;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context=(Activity)CourseListForScheduleActivity.this;
	Utils.fullScreenActivity(context);
	dialog=new ProgressDialog(context);
	setContentView(R.layout.activity_courses);
	intiViews();
	loadCourses();
}

private void loadCourses() {
	httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_COURSES, URLNameValuePairBuilder.setAllCoursesRequestParams(BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth()), WebserviceKeyValues.REQUEST_CODE_COURSES, AsyncHttpRequest.Type.POST, null);	
}

private void intiViews() {
	mListView=(ListView) findViewById(R.id.listView_courses);
	mListView.setOnItemClickListener(this);
	
}

@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestCompleted(response, requestCode);
	
	if (requestCode==WebserviceKeyValues.REQUEST_CODE_COURSES) {
		mCoursesList=ServiceResponceParser.allCoursesParses(response);
		mCoursesAdapter=new CourseListAdapter(context, mCoursesList,imageLoader);
		mListView.setAdapter(mCoursesAdapter);
		dialog.dismiss();
	}
	
}
@Override
public void onHTTPRequestError(Exception e, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestError(e, requestCode);
}

@Override
public void onHTTPRequestStarted(int mRequestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestStarted(mRequestCode);
	if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_COURSES) {
		
		dialog.setMessage("Loading courses please wait ...");
		dialog.show();
	}
	
}



@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

@NotNull Intent i= new Intent(CourseListForScheduleActivity.this, ScheduleDetailsActivity.class);

	i.putExtra(AppConstants.COURSE_ID, mCoursesList.get(arg2).getCourseID());
startActivity(i);
}
}

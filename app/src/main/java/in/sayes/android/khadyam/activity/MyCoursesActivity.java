package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.CatagoryAdapter;
import in.sayes.android.khadyam.bean.CatagoryBean;
import in.sayes.android.khadyam.bean.CoursesBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class MyCoursesActivity extends BaseActivity implements OnItemClickListener{
	private Activity context;
	private ListView mListView;
   private TextView mTxtHeader;
	private ArrayList<CatagoryBean> mCoursesList;
	private CoursesBean mCourse;
	private CatagoryAdapter mCoursesAdapter;
	private ProgressDialog dialog;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context=(Activity)MyCoursesActivity.this;
	Utils.fullScreenActivity(context);
	dialog=new ProgressDialog(context);
	setContentView(R.layout.activity_courses);
	intiViews();
	loadCourses();
}

private void loadCourses() {
	//BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth()
	httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_MY_COURSE, URLNameValuePairBuilder.setCommonRequestParams(BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth()), WebserviceKeyValues.REQUEST_CODE_MY_COURSE, AsyncHttpRequest.Type.POST, null);
}

private void intiViews() {
    mTxtHeader= (TextView) findViewById(R.id.txt_coursesListHeader);
    mTxtHeader.setText(getResources().getString(R.string.my_courses));
	mListView=(ListView) findViewById(R.id.listView_courses);
	mListView.setOnItemClickListener(this);

	
}

@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestCompleted(response, requestCode);
	
	if (requestCode==WebserviceKeyValues.REQUEST_CODE_MY_COURSE) {
		mCoursesList=ServiceResponceParser.myCourseParses(response);
		mCoursesAdapter=new CatagoryAdapter(context, mCoursesList);
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
	if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_MY_COURSE) {
		
		dialog.setMessage("Loading courses please wait ...");
		dialog.show();
	}
	
}



@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    @NotNull Intent i= new Intent(MyCoursesActivity.this, CourseDetailsActivity.class);

    //TODO Using catagory adapter in place of course adapter.
    i.putExtra(AppConstants.COURSE_ID, mCoursesList.get(arg2).getCatagoryId());
    i.putExtra(AppConstants.PARENT_ACTIVITY, AppConstants.MYCOURSE_ACTIVITY);
    startActivity(i);
}
	
}

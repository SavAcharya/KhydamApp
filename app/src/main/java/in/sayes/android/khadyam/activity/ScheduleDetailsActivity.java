package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.ScheduleListAdapter;
import in.sayes.android.khadyam.bean.ScheduleBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class ScheduleDetailsActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "SchiduleActivity";
    Activity context;
    private String mCourseId;


    private ListView mSchedulesList;
    private ArrayList<ScheduleBean> scheduleList;
    private ScheduleListAdapter scheduleAdapter;
    private ImageView courseImage;
    private TextView courseName;
    //private TextView courseDecription;
    private TextView courseDuration;
    private TextView scheduleTimeSlot;
    private TextView scheduleStartDay;
    private TextView scheduleWeeklyDays;
    private ProgressDialog dialog;
    private String mCourseName;
    private String mCourseDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = (Activity) ScheduleDetailsActivity.this;
        Utils.fullScreenActivity(context);
        dialog = new ProgressDialog(context);
        if (getIntent().getExtras().getString(AppConstants.COURSE_ID) != null) {
            mCourseId = getIntent().getExtras().getString(AppConstants.COURSE_ID);
            mCourseName = getIntent().getExtras().getString(AppConstants.COURSES_NAME);
            Log.i(TAG, mCourseId);
        }
        intiViews();
        loadScheduleList();

    }

    private void intiViews() {
        setContentView(R.layout.activity_schedule);
        mSchedulesList = (ListView) findViewById(R.id.list_schedule_time_slot);
        //courseImage=(ImageView) findViewById(R.id.img_schedule_courseImage);
        scheduleWeeklyDays = (TextView) findViewById(R.id.scheduleHeaderDays);

        courseName = (TextView) findViewById(R.id.scheduleHeaderCourseName);

        scheduleTimeSlot = (TextView) findViewById(R.id.scheduleHeaderTiming);

        scheduleStartDay = (TextView) findViewById(R.id.scheduleHeaderStartingDate);

        courseDuration = (TextView) findViewById(R.id.scheduleHeaderTotaldays);

        //courseDecription=(TextView) findViewById(R.id.txt_schedule_courseDescription);

    }

    private void loadScheduleList() {
        if (mCourseId != null) {
            httpCall(AppConstants.URL_BASE,
                    WebserviceKeyValues.WEBSERVICE_CALL_SCHEDULE_BY_COURSE,
                    URLNameValuePairBuilder.setScheduleListRequestParam(
                            BaseActivity.getInstanceAppPreferences().getUserId(),
                            BaseActivity.getInstanceAppPreferences().getAuth(),
                            mCourseId),
                    WebserviceKeyValues.REQUEST_CODE_SCHEDULE_BY_COURSE,
                    AsyncHttpRequest.Type.POST, null);
        }


    else

    {


        httpCall(AppConstants.URL_BASE,
                WebserviceKeyValues.WEBSERVICE_CALL_FUTURE_SCHEDULE,
                URLNameValuePairBuilder.setCommonRequestParams(
                        BaseActivity.getInstanceAppPreferences().getUserId(),
                        BaseActivity.getInstanceAppPreferences().getAuth()),
                WebserviceKeyValues.REQUEST_CODE_FUTURE_SCHEDULE,
                AsyncHttpRequest.Type.POST, null);
    }

}

	
@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestCompleted(response, requestCode);

	if (requestCode==WebserviceKeyValues.REQUEST_CODE_FUTURE_SCHEDULE||requestCode==WebserviceKeyValues.REQUEST_CODE_SCHEDULE_BY_COURSE) {
		try {
			@NotNull JSONObject responce= new JSONObject(response);
			String status = responce.getString(WebserviceKeyValues.STATUS);
			if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_SUCCESS)) {
				//JSONObject resData=responce.getJSONObject("responseData");
				
					//mCourseName=	resData.getString("course_name");
					//mCourseDesc=resData.getString("course_description");
					scheduleList=ServiceResponceParser.scheduleDetailsByCourseIdParser(response,mCourseName);
					scheduleAdapter=new ScheduleListAdapter(context,imageLoader,scheduleList);
					mSchedulesList.setAdapter(scheduleAdapter);
					//courseDecription.setText(mCourseDesc);
					//courseName.setText(mCourseName);
				}
				
				else if (status.equalsIgnoreCase(WebserviceKeyValues.STATUS_FAIL)) {
					// ToDo add fail condition
					
				}
			
			} catch (JSONException e) {

			e.printStackTrace();
		}
		
		
		dialog.dismiss();
	}
}

@Override
public void onHTTPRequestError(Exception e, int requestCode) {
	// TODO Add error code
	super.onHTTPRequestError(e, requestCode);
}

@Override
public void onHTTPRequestStarted(int mRequestCode) {

	super.onHTTPRequestStarted(mRequestCode);
if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_SCHEDULE_BY_COURSE) {
		
		dialog.setMessage("Loading schedule please wait ...");
		dialog.show();
	}
}
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}

	

}

package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.TipsAdapter;
import in.sayes.android.khadyam.bean.TipsBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class TipsActivity extends BaseActivity implements OnItemClickListener{
	
	private Activity context;
	
	private ListView mListView;
	private ProgressDialog dialog;
	private TipsAdapter mTipsAdapter;
	private ArrayList<TipsBean> mTipsList;
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	context = (TipsActivity) this;
	Utils.fullScreenActivity(context);
	dialog=new ProgressDialog(context);
	setupView();
	loadTipsList();
}
private void setupView() {
	setContentView(R.layout.activity_tips);
	mListView=(ListView) findViewById(R.id.list_tips_ist);
	
}
@Override
	public void onBackPressed() {
	
		super.onBackPressed();
this.finish();	
}

private void loadTipsList(){
	
	httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_TIPS, URLNameValuePairBuilder.setAllTipsRequestParams(BaseActivity.getInstanceAppPreferences().getUserId()),WebserviceKeyValues.REQUEST_CODE_TIPS, AsyncHttpRequest.Type.POST, null);
	

}


@Override
public void onHTTPRequestStarted(int mRequestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestStarted(mRequestCode);
	
	if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_TIPS) {
		dialog.setMessage("Loading tips, Please wait ...");
		
		dialog.show();
	}
}
@Override
public void onHTTPRequestCompleted(String response, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestCompleted(response, requestCode);
	
	if (requestCode==WebserviceKeyValues.REQUEST_CODE_TIPS) {
	
		mTipsList=ServiceResponceParser.getAllTipsParser(response);
		mTipsAdapter=new TipsAdapter(context, mTipsList);
		mListView.setAdapter(mTipsAdapter);
		dialog.dismiss();
	}
	
}
@Override
public void onHTTPRequestError(Exception e, int requestCode) {
	// TODO Auto-generated method stub
	super.onHTTPRequestError(e, requestCode);
}


@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
	String name=mTipsList.get(arg2).getTipsName();
	
	
}
}

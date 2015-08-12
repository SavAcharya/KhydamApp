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

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.CatagoryAdapter;
import in.sayes.android.khadyam.bean.CatagoryBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;


public class CatagoryListActivity extends BaseActivity implements OnItemClickListener{

	private Activity context;
	private TextView mHeaderView;
	private ListView mListView;
	private CatagoryAdapter mCatagoryAdapter;
	private ArrayList<CatagoryBean> mCatagoryList;
	private ProgressDialog dialog;
	private String parentActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=(Activity)CatagoryListActivity.this;
		Utils.fullScreenActivity(context);
		dialog=new ProgressDialog(context);
		setContentView(R.layout.activity_catagory_list);
		parentActivity=getIntent().getExtras().getString(AppConstants.PARENT_ACTIVITY);
		initViews();
		loadCatagoryList();
	}
	private void initViews() {
		mHeaderView=(TextView) findViewById(R.id.txtHeader);
		mListView=(ListView) findViewById(R.id.list_CatagoryList);
		mListView.setOnItemClickListener(this);
	}
	
	
	private void loadCatagoryList(){
		
		
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_ALL_FREE_RECIPE_CATAGORY, URLNameValuePairBuilder.setCommonRequestParams(BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth()),WebserviceKeyValues.REQUEST_CODE_ALL_FREE_RECIPE_CATAGORY, AsyncHttpRequest.Type.POST, null);
		
	}
	
	@Override
	public void onHTTPRequestStarted(int mRequestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestStarted(mRequestCode);
		
		if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_ALL_FREE_RECIPE_CATAGORY) {
		
			dialog.setMessage("Loading all catagories please wait ...");
			dialog.show();
		}
		
		
	}
	@Override
	public void onHTTPRequestError(Exception e, int requestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestError(e, requestCode);
		
		
	}
	@Override
	public void onHTTPRequestCompleted(String response, int requestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestCompleted(response, requestCode);
		
		if (requestCode==WebserviceKeyValues.REQUEST_CODE_ALL_FREE_RECIPE_CATAGORY){
			mCatagoryList=ServiceResponceParser.allFreeRecipeCatagoryParser(response);
			mCatagoryAdapter=new CatagoryAdapter(context, mCatagoryList);
			mListView.setAdapter(mCatagoryAdapter);
			dialog.dismiss();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Check parent activity type and launch child activity
		String id=mCatagoryList.get(arg2).getCatagoryId();
		String name=mCatagoryList.get(arg2).getCatagoryName();
		
		Intent i;
		if (AppConstants.FREE_RECIPE.contentEquals(parentActivity)) {
			i= new Intent(context, FreeRecipesActivity.class);
			i.putExtra(AppConstants.CATAGORY_ID, id);
			startActivity(i);
		} else if (AppConstants.TIPS.contentEquals(parentActivity)) {
			i= new Intent(context, TipsActivity.class);
			i.putExtra(AppConstants.CATAGORY_ID, id);
			startActivity(i);
		} 
	

	}
	

}

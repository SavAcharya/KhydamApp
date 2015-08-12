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
import in.sayes.android.khadyam.adapter.RecipeListAdapter;
import in.sayes.android.khadyam.bean.RecipeBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;

public class FreeRecipesActivity extends BaseActivity implements OnItemClickListener{

	private Activity context;
	private TextView mHeaderView;
	private ListView mListView;
	private RecipeListAdapter mRecipeAdapter;
	private ArrayList<RecipeBean> mRecipeList;
	private ProgressDialog dialog;
	private String mCatagoryId; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=(Activity)FreeRecipesActivity.this;
		mCatagoryId = getIntent().getExtras().getString(AppConstants.CATAGORY_ID);
		Utils.fullScreenActivity(context);
		dialog=new ProgressDialog(context);
		setContentView(R.layout.activity_recipes_list);
		initViews();
		loadRecipeList();
	}

	private void initViews() {
		mHeaderView=(TextView) findViewById(R.id.txtHeader);
		mListView=(ListView) findViewById(R.id.list_recipes_list);
		mListView.setOnItemClickListener(this);
	}
	
	
	private void loadRecipeList(){
		
		
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CALL_FREE_RECIPE, URLNameValuePairBuilder.setFreeRecipesRequestParams(BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth(),mCatagoryId),WebserviceKeyValues.REQUEST_CODE_FREE_RECIPE, AsyncHttpRequest.Type.POST, null);
		
	}
	
	@Override
	public void onHTTPRequestStarted(int mRequestCode) {
		// TODO Auto-generated method stub
		super.onHTTPRequestStarted(mRequestCode);
		
		if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_FREE_RECIPE) {
		
			dialog.setMessage("Loading Recipes please wait ...");
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
		
		if (requestCode==WebserviceKeyValues.REQUEST_CODE_FREE_RECIPE) {
			mRecipeList=ServiceResponceParser.allFreeRecipeParser(response);
			mRecipeAdapter=new RecipeListAdapter(context, mRecipeList,imageLoader);
			mListView.setAdapter(mRecipeAdapter);
			dialog.dismiss();
		}
		
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		@NotNull Intent i=new Intent(FreeRecipesActivity.this, RecipeDetailsActivity.class);
		
		i.putExtra(AppConstants.RECIPE_ID, mRecipeList.get(arg2).getRecipeId()); 
		startActivity(i);
		
		
	}
	
}

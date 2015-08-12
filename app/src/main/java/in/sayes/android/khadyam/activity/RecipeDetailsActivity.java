package in.sayes.android.khadyam.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.adapter.IngredientsAdapter;
import in.sayes.android.khadyam.adapter.RecipeStepsAdapter;
import in.sayes.android.khadyam.adapter.TipsAdapter;
import in.sayes.android.khadyam.bean.RecipeBean;
import in.sayes.android.khadyam.bean.RecipeBean.Ingredients;
import in.sayes.android.khadyam.bean.RecipeBean.RecipeSteps;
import in.sayes.android.khadyam.bean.TipsBean;
import in.sayes.android.khadyam.common.AppConstants;
import in.sayes.android.khadyam.common.Utils;
import in.sayes.android.khadyam.http.AsyncHttpRequest;
import in.sayes.android.khadyam.http.URLNameValuePairBuilder;
import in.sayes.android.khadyam.http.WebserviceKeyValues;
import in.sayes.android.khadyam.parser.ServiceResponceParser;


public class RecipeDetailsActivity extends BaseActivity implements OnItemClickListener,OnClickListener{
	private Activity context;
	private String recipeId;
	private ListView mListView;
	private Button mIngridentsButton;
	private Button mStepsButton;
	private Button mTipsButton;
	private TextView mRecipeNmaeText;
	private TextView mRecipeDescriptionText;
	private TextView mRecipeCookingTimeText;
	private TextView mRecipePreprationTimeText;
    private TextView mEmptyDataText;
	private ProgressDialog dialog;
	private RecipeBean mRecipeDetails;
	private RecipeStepsAdapter mStepsAdapter;
	private IngredientsAdapter mIngredientsAdapter;
	private TipsAdapter mTipsAdapter;
	private ArrayList<TipsBean> mTips;
	private ArrayList<RecipeSteps> mSteps;
	private ArrayList<Ingredients> mIngredients;
	ArrayList<String> mImages;
	private ViewPager viewPager;
	//private ImageLoader imageLoader= ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	//DummyImages ...
	@NotNull
    //private int[] defaultRecipeImages = {R.drawable.recipe,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4};
	private int currentPosition = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=(Activity)RecipeDetailsActivity.this;
		Utils.fullScreenActivity(context);
		dialog=new ProgressDialog(context);
		setContentView(R.layout.activity_recipe_details);
		recipeId=getIntent().getExtras().getString(AppConstants.RECIPE_ID);
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.chef_logo).showImageForEmptyUri(R.drawable.chef_logo).cacheInMemory().cacheOnDisc().build();
	initViews();
	loadRecipeDetails(recipeId);
	}



	private void loadRecipeDetails(String recipeId2) {
		httpCall(AppConstants.URL_BASE, WebserviceKeyValues.WEBSERVICE_CAL_RECIPE_DETAILS, URLNameValuePairBuilder.setRecipeDetailsRequestParams(BaseActivity.getInstanceAppPreferences().getUserId(), BaseActivity.getInstanceAppPreferences().getAuth(),recipeId),WebserviceKeyValues.REQUEST_CODE_RECIPE_DETAILS, AsyncHttpRequest.Type.POST, null);
		
	}



	private void initViews() {
		mIngridentsButton=(Button) findViewById(R.id.btn_recipeIngridents);
		mStepsButton=(Button) findViewById(R.id.btn_recipeSteps);
		mTipsButton=(Button) findViewById(R.id.btn_recipeTips);
		mListView=(ListView) findViewById(R.id.list_recipeDetails);
		mRecipeDescriptionText=(TextView) findViewById(R.id.txt_recipeDetails_recipDescription);
		mRecipeNmaeText=(TextView) findViewById(R.id.txt_recipeDetails_recipeName);
		mRecipeCookingTimeText=(TextView) findViewById(R.id.txt_recipeDetails_cookingTime);
		mRecipePreprationTimeText=(TextView) findViewById(R.id.txt_recipeDetails_prepTime);
        mEmptyDataText= (TextView) findViewById(R.id.txt_recipeDetails_emptyData);
        mEmptyDataText.setVisibility(View.GONE);
		viewPager = (ViewPager) findViewById(R.id.gallery1);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				currentPosition = pos;
				
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		mIngridentsButton.setOnClickListener(this);
		
		mStepsButton.setOnClickListener(this);
		mTipsButton.setOnClickListener(this);
		
	}

@Override
public void onHTTPRequestCompleted(String response, int requestCode) {

	super.onHTTPRequestCompleted(response, requestCode);
	
	if (requestCode==WebserviceKeyValues.REQUEST_CODE_RECIPE_DETAILS){
		
		mRecipeDetails=ServiceResponceParser.recipeDetailsParser(response);
		mIngredients=mRecipeDetails.getIngredients();
		mSteps=mRecipeDetails.getSteps();
		mTips=mRecipeDetails.getTips();
		mRecipeNmaeText.setText(mRecipeDetails.getRecipeName());
        // showing no description
		//mRecipeDescriptionText.setText(mRecipeDetails.getRecipeDetails());
        //removing the view to adjust alignment.
        mRecipeDescriptionText.setVisibility(View.GONE);


		mRecipeCookingTimeText.setText(mRecipeDetails.getCookingTime());
		mRecipePreprationTimeText.setText(mRecipeDetails.getPreprationTime());


        if(mRecipeDetails.getmImageUrls()!=null) {
            viewPager.setAdapter(new RecipeImagesViewPagerAdapter(mRecipeDetails.getmImageUrls()));
        }
    // default view- ingredients
        loadRecipeIngridents();

		dialog.dismiss();
	}
}
@Override
public void onHTTPRequestError(Exception e, int requestCode) {

	super.onHTTPRequestError(e, requestCode);
}
@Override
public void onHTTPRequestStarted(int mRequestCode) {

	super.onHTTPRequestStarted(mRequestCode);
	if (mRequestCode==WebserviceKeyValues.REQUEST_CODE_RECIPE_DETAILS) {
		
		dialog.setMessage("Loading recipe please wait ...");
		dialog.show();
	}
}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		
	}

	
	

	@Override
	public void onClick(@NotNull View v) {
		switch (v.getId()) {
		case R.id.btn_recipeIngridents:
			loadRecipeIngridents();
			break;
		case R.id.btn_recipeSteps:
			loadRecipeSteps();
			break;
		case R.id.btn_recipeTips:
			loadRecipeTips();
			break;
		}
		
	}



	private void loadRecipeTips() {
        if(mTips!=null){
            mListView.setVisibility(View.VISIBLE);
            mEmptyDataText.setVisibility(View.GONE);
            mTipsAdapter=new TipsAdapter(context, mTips);
            mListView.setAdapter(mTipsAdapter);
        }else{
           showNoDataMessage();
        }

	}



	private void loadRecipeSteps() {
        if (mSteps != null) {
            mListView.setVisibility(View.VISIBLE);
            mEmptyDataText.setVisibility(View.GONE);
            mStepsAdapter = new RecipeStepsAdapter(context, mSteps);
            mListView.setAdapter(mStepsAdapter);

        } else {
showNoDataMessage();
        }
    }

	private void loadRecipeIngridents() {
        if(mIngredients!=null) {
            mListView.setVisibility(View.VISIBLE);
            mEmptyDataText.setVisibility(View.GONE);
            mIngredientsAdapter = new IngredientsAdapter(context, mIngredients);
            mListView.setAdapter(mIngredientsAdapter);
        }else {
            showNoDataMessage();
        }
	}
    private void showNoDataMessage(){

        mListView.setVisibility(View.GONE);
        mEmptyDataText.setVisibility(View.VISIBLE);

    }

	private class RecipeImagesViewPagerAdapter extends PagerAdapter{

		private LayoutInflater inflater;
		ArrayList<String> images= new ArrayList<String>();
		public RecipeImagesViewPagerAdapter(ArrayList<String> images) {
			super();
			inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.images= images;
		}
		
		public RecipeImagesViewPagerAdapter() {
			inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public Object instantiateItem(@NotNull View collection, int pos) {
			
			View view;
			view = inflater.inflate(R.layout.pager_item_how_use_other, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));  
			
			@NotNull ImageView imgDemo = (ImageView) view.findViewById(R.id.ItemHowUseOtherImageView);
			imgDemo.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));     
			imgDemo.setScaleType(ScaleType.FIT_XY);
			if (images.isEmpty()) {
				imgDemo.setImageDrawable(getResources().getDrawable(R.drawable.chef_logo));
			}else
			{
				//imgDemo.setImageResource(defaultRecipeImages[pos]);
				imageLoader.displayImage(images.get(pos).toString(),imgDemo,options);
			}
			
			
			
			((ViewPager) collection).addView(view,0);               	        
			return view;
		}
		
		@Override
		public int getItemPosition(Object view) {		
			return super.getItemPosition(view);
		}
		
		@Override
		public void destroyItem(@NotNull View collection, int pos, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public void finishUpdate(View collection) {
			
		}

		@Override
		public int getCount() {
            if(images.size()!=0)
			return images.size();

            return 0;
		}

		@Override
		public boolean isViewFromObject(View collection, Object view) {
			return collection==((View)view);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Nullable
        @Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View collection) {
			
		}
		
	}
	

	
	
	
	
}

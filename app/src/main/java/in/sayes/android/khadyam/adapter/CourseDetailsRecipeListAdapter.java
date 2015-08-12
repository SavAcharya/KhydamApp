package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.CoursesBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class CourseDetailsRecipeListAdapter extends BaseAdapter{

	private class ViewHolder {
		
		TextView mTitle;
		ListView mListRecipes;	
			}
	
	private Activity mContext;
	
	ArrayList<CoursesBean.CourseDetails> mDailyRecipes;	
	RecipesListAdapter adapter;
	private LayoutInflater mInflater;
	public CourseDetailsRecipeListAdapter(Activity context,
			ArrayList<CoursesBean.CourseDetails> recipesList) {
		super();
		this.mContext = context;
		this.mDailyRecipes = recipesList;
		
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	@Override
	public int getCount() {
		if(mDailyRecipes.size() > 0)
			return mDailyRecipes.size();
		else 
		    return 0;
	}

	@Nullable
    @Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Nullable
    @Override
	public View getView(int pos, @Nullable View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.adapter_row_course_details_recipes, null);
			holder = new ViewHolder();
			holder.mTitle=(TextView) convertView.findViewById(R.id.txt_adapterCourseDetailsRecipesTitle);
			holder.mListRecipes=(ListView) convertView.findViewById(R.id.list_adapterCourseDetailsRecipesList);
			
			convertView.setTag(holder);
		}else {
		holder = (ViewHolder) convertView.getTag();
	}
		try {
			if (mDailyRecipes.get(pos).getTitle()!=null) {
				holder.mTitle.setText(mDailyRecipes.get(pos).getTitle().toUpperCase());
				
			}
			if (mDailyRecipes.get(pos).getItems().size() > 0) {
				/*ArrayList<RecipeBean>items = mDailyRecipes.get(pos).getItems();
				adapter= new RecipesListAdapter(mContext, items);
				holder.mListRecipes.setAdapter(adapter);*/
			}
			
			
			
			
			//imageLoader.displayImage(viewRecipeList.get(pos).getImageURL(),holder.mRecipeImage,options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return convertView;

}
}

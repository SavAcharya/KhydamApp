package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class RecipesListAdapter extends BaseAdapter{


	private Activity mContext;
	ArrayList<String> mRecipeList;
	
	private LayoutInflater mInflater;
public class ViewHolder {
		
		TextView mCourseName;
		
	}
	

public RecipesListAdapter(Activity context, ArrayList<String> recipeList) {
	super();
	mContext=context;
	mRecipeList=recipeList;
	mInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
}


	@Override
	public int getCount() {
		if(mRecipeList.size() > 0)
			return mRecipeList.size();
		else 
		    return 0;
	}

	@Nullable
    @Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Nullable
    @Override
	public View getView(int position, @Nullable View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
					
					convertView = mInflater.inflate(R.layout.adapter_row_course_details_recipes, null);
					holder = new ViewHolder();
					holder.mCourseName=(TextView) convertView.findViewById(R.id.txt_adapterCourseDetailsRecipesTitle);
					
					convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
				try {
					if (mRecipeList.get(position).toString()!=null) {
						holder.mCourseName.setText(mRecipeList.get(position).toString());
						
					}
					
						
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return convertView;
				
			
			}
	
}

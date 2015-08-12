package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.RecipeBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class RecipeStepsAdapter extends BaseAdapter{

	private Activity mContext;
	private ArrayList<RecipeBean.RecipeSteps> mRecipeStepsList;
	
	private LayoutInflater mInflater;
public class ViewHolder {
		
		TextView mStepsTitle;
		TextView mStepsDescription;
	}
	

public RecipeStepsAdapter(Activity context, ArrayList<RecipeBean.RecipeSteps> stepsList) {
	super();
	mContext=context;
	mRecipeStepsList=stepsList;
	mInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
}


	@Override
	public int getCount() {
		if(mRecipeStepsList.size() > 0)
			return mRecipeStepsList.size();
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
					
					convertView = mInflater.inflate(R.layout.adapter_roe_recipe_steps, null);
					holder = new ViewHolder();
					holder.mStepsTitle=(TextView) convertView.findViewById(R.id.txt_recipe_adapter_row_stepTitle);
					holder.mStepsDescription=(TextView) convertView.findViewById(R.id.txt_recipe_adapter_row_stepDetails);
					convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			
		try {
			if (mRecipeStepsList.get(position).getStepsTitle()!=null) {
				holder.mStepsTitle.setText(mRecipeStepsList.get(position).getStepsTitle());
			}if (mRecipeStepsList.get(position).getStepsDetails()!=null) {
				holder.mStepsDescription.setText(mRecipeStepsList.get(position).getStepsDetails());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				
				
				
				return convertView;
				
			
			}
	}



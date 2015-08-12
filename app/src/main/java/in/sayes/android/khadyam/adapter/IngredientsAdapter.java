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

public class IngredientsAdapter extends BaseAdapter{


	private Activity mContext;
	private ArrayList<RecipeBean.Ingredients> mRecipeingridentsList;
	
	private LayoutInflater mInflater;
public class ViewHolder {
		
		TextView mIngridentName;
		TextView mIngridentQuantity;
	}
	

public IngredientsAdapter(Activity context, ArrayList<RecipeBean.Ingredients> ingridentsList) {
	super();
	mContext=context;
	mRecipeingridentsList=ingridentsList;
	mInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
}


	@Override
	public int getCount() {
		if(mRecipeingridentsList.size() > 0)
			return mRecipeingridentsList.size();
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
					
					convertView = mInflater.inflate(R.layout.adapter_row_ingrident, null);
					holder = new ViewHolder();
					holder.mIngridentName=(TextView) convertView.findViewById(R.id.txt_ingrgentName_row);
					holder.mIngridentQuantity=(TextView) convertView.findViewById(R.id.txt_ingrgentQuantity_row);
			
					convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
				try {
					if (mRecipeingridentsList.get(position).getIngredientName()!=null) {
						holder.mIngridentName.setText(mRecipeingridentsList.get(position).getIngredientName());
						
					}
					if (mRecipeingridentsList.get(position).getIngredientsQuantity()!=null) {
						if(mRecipeingridentsList.get(position).getIngredientsUnit()!=null){
							holder.mIngridentQuantity.setText(mRecipeingridentsList.get(position).getIngredientsQuantity()+""+mRecipeingridentsList.get(position).getIngredientsUnit());
							
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return convertView;
				
			
			}
	
}

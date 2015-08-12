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
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.Nullable;

public class RecipeListAdapter extends BaseAdapter{

	public class ViewHolder {
ImageView mRecipeImage;
TextView mRecipeName;
TextView mRecipeDesc;
		
		
	}

	private Activity mContext;
	private ArrayList<RecipeBean> viewRecipeList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	
	public RecipeListAdapter(Activity context,
			ArrayList<RecipeBean> viewRecipeList, ImageLoader _imageLoader) {
		super();
		this.mContext = context;
		this.viewRecipeList = viewRecipeList;
		this.imageLoader = _imageLoader;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.khadyam_logo).showImageForEmptyUri(R.drawable.khadyam_logo).cacheInMemory().cacheOnDisc().build();
	
	
	}

	@Override
	public int getCount() {
		if(viewRecipeList.size() > 0)
			return viewRecipeList.size();
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
			
			convertView = mInflater.inflate(R.layout.adapter_row_recipe, null);
			holder = new ViewHolder();
			holder.mRecipeImage=(ImageView) convertView.findViewById(R.id.img_recipeImg);
			holder.mRecipeName=(TextView) convertView.findViewById(R.id.txt_recipeName);
			holder.mRecipeDesc=(TextView) convertView.findViewById(R.id.txt_recipeDesc);
			
			convertView.setTag(holder);
	}else {
		holder = (ViewHolder) convertView.getTag();
	}
		try {
			if (viewRecipeList.get(pos).getRecipeName()!=null) {
				holder.mRecipeName.setText(viewRecipeList.get(pos).getRecipeName().toUpperCase());
			}
			if (viewRecipeList.get(pos).getRecipeDetails()!=null) {
	
			holder.mRecipeDesc.setText(viewRecipeList.get(pos).getRecipeDetails());
			}
			if (viewRecipeList.get(pos).getmImageThumb()!=null) {
			imageLoader.displayImage(viewRecipeList.get(pos).getmImageThumb(),holder.mRecipeImage,options);
			}
			} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return convertView;

}
}
package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.CatagoryBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;


public class CatagoryAdapter extends BaseAdapter{
	
	private Activity mContext;
	private ArrayList<CatagoryBean> mCatagoryList;
	
	private LayoutInflater mInflater;
	/**
	 * 
	 */
	public CatagoryAdapter(Activity context, ArrayList<CatagoryBean> catagoryList) {
		super();
		mContext=context;
		mCatagoryList=catagoryList;
		mInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	public class ViewHolder {
		
		TextView mCatagoryName;
	}


	@Override
	public int getCount() {
		if(mCatagoryList.size() > 0)
			return mCatagoryList.size();
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
			
			convertView = mInflater.inflate(R.layout.adapter_row_catagory, null);
			holder = new ViewHolder();
			holder.mCatagoryName=(TextView) convertView.findViewById(R.id.txt_catagory_name);
			convertView.setTag(holder);
		
	}else {
		holder = (ViewHolder) convertView.getTag();
	}
		try {
			if (mCatagoryList.get(position).getCatagoryName()!=null) {
				holder.mCatagoryName.setText(mCatagoryList.get(position).getCatagoryName());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return convertView;
		
	
	}
}

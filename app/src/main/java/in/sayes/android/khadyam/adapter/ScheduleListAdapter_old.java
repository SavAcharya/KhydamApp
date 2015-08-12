package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.ScheduleBean;

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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScheduleListAdapter_old extends BaseAdapter {
	private ArrayList<ScheduleBean> mScheduleList;
	private Activity mContext;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ScheduleListAdapter_old(@NotNull Activity mContext, ImageLoader imageLoader,
                                   ArrayList<ScheduleBean> scheduleList) {
		super();
		this.mContext = mContext;
		this.mScheduleList = scheduleList;
		this.imageLoader = imageLoader;

		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.chef_logo)
				.showImageForEmptyUri(R.drawable.chef_logo).cacheInMemory()
				.cacheOnDisc().build();

	}

	public class ViewHolder {
TextView mStartDate;
TextView mTimeSlot;

TextView mTotalSeats;
TextView mAvailableSeats;

ImageView sunday;
ImageView monday;
ImageView tuesday;
ImageView wednesday;
ImageView thrusday;
ImageView friday;
ImageView saturday;

	}

	@Override
	public int getCount() {
		if (mScheduleList.size() > 0)
			return mScheduleList.size();
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
		convertView = mInflater.inflate(R.layout.adapter_row_schedule_old, null);
		holder = new ViewHolder();
		holder.mStartDate=(TextView) convertView.findViewById(R.id.txt_schedule_starting_date);
		holder.mTimeSlot=(TextView) convertView.findViewById(R.id.txt_schedule_time_slot);
		holder.mTotalSeats=(TextView) convertView.findViewById(R.id.txt_schedule_total_seats);
		holder.mAvailableSeats=(TextView) convertView.findViewById(R.id.txt_schedule_availble_seats);
		
		holder.sunday=(ImageView) convertView.findViewById(R.id.img_sunday);
		holder.monday=(ImageView) convertView.findViewById(R.id.img_monday);
		holder.tuesday=(ImageView) convertView.findViewById(R.id.img_tuesday);
		holder.wednesday=(ImageView) convertView.findViewById(R.id.img_wednessday);
		holder.thrusday=(ImageView) convertView.findViewById(R.id.img_thrusday);
		holder.friday=(ImageView) convertView.findViewById(R.id.img_friday);
		holder.saturday=(ImageView) convertView.findViewById(R.id.img_saturday);
		convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (mScheduleList.get(position).getmStartDate()!=null) {
		holder.mStartDate.setText(mScheduleList.get(position).getmStartDate());
		}
		if (mScheduleList.get(position).getmStartTime()!=null && mScheduleList.get(position).getmEndTime()!=null  ) {
			holder.mTimeSlot.setText(mScheduleList.get(position).getmStartTime()+" - "+mScheduleList.get(position).getmEndTime());
		}
		
		if (mScheduleList.get(position).getmAvaiableSeats()!=null) {
			
		}
if (mScheduleList.get(position).getmTotalSeats()!=null) {
			
		}
if (mScheduleList.get(position).getmMonday()!=null && mScheduleList.get(position).getmMonday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}
if (mScheduleList.get(position).getmTuesday()!=null && mScheduleList.get(position).getmTuesday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}
if (mScheduleList.get(position).getmWednesday()!=null && mScheduleList.get(position).getmWednesday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}
if (mScheduleList.get(position).getmThrusday()!=null && mScheduleList.get(position).getmThrusday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}if (mScheduleList.get(position).getmFriday()!=null && mScheduleList.get(position).getmFriday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}
if (mScheduleList.get(position).getmSaturday()!=null && mScheduleList.get(position).getmSaturday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}if (mScheduleList.get(position).getmSunday()!=null && mScheduleList.get(position).getmSunday().equalsIgnoreCase("1")) {
	
	holder.monday.setColorFilter(R.color.green);
	
}

		
		return convertView;
	}

}

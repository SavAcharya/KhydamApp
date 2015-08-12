package in.sayes.android.khadyam.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.ScheduleBean;

/**
 * Created by sourav on 06/06/15.
 */
public class ScheduleListAdapter extends BaseAdapter {
    private ArrayList<ScheduleBean> mScheduleList;
    private Activity mContext;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    @NotNull
    private String weeklyDays="";

    public ScheduleListAdapter(@NotNull Activity mContext, ImageLoader imageLoader,
                                   ArrayList<ScheduleBean> scheduleList) {
        super();
        this.mContext = mContext;
        this.mScheduleList = scheduleList;
        this.imageLoader = imageLoader;

        this.mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.khadyam_logo)
                .showImageForEmptyUri(R.drawable.khadyam_logo).cacheInMemory()
                .cacheOnDisc().build();

    }

    public class ViewHolder {
        TextView mStartDate;
        TextView mTimeSlot;
        TextView mDuration;
        TextView mCourseNmae;
        TextView mWeeklyDays;
        TextView mTotalSeats;
        TextView mAvailableSeats;



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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        ViewHolder holder;
        weeklyDays="";
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_row_schedule, null);
            holder = new ViewHolder();
            holder.mStartDate=(TextView) convertView.findViewById(R.id.txt_schedule_starting_date);

            holder.mTimeSlot=(TextView) convertView.findViewById(R.id.txt_schedule_time_slot);

            holder.mCourseNmae=(TextView)convertView.findViewById(R.id.txt_schedule_courseName);

            holder.mWeeklyDays=(TextView)convertView.findViewById(R.id.txt_schedule_weeklyDays);

            holder.mDuration=(TextView)convertView.findViewById(R.id.txt_schedule_courseDuration);




            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mScheduleList.get(position).getmStartDate()!=null) {
            holder.mStartDate.setText(mScheduleList.get(position).getmStartDate());
        }
        if (mScheduleList.get(position).getmStartTime()!=null && mScheduleList.get(position).getmEndTime()!=null  ) {
            holder.mTimeSlot.setText(mScheduleList.get(position).getmStartTime()+"\n To \n"+mScheduleList.get(position).getmEndTime());
        }
        if (mScheduleList.get(position).getmCourseName()!=null ) {
            holder.mCourseNmae.setText(mScheduleList.get(position).getmCourseName());
        }
        if (mScheduleList.get(position).getmCourseDuration()!=null ) {
            holder.mDuration.setText(mScheduleList.get(position).getmCourseDuration());
        }


        if (mScheduleList.get(position).getmAvaiableSeats()!=null) {
// TODO add details
        }
        if (mScheduleList.get(position).getmTotalSeats()!=null) {
// TODO add details
        }

        if (mScheduleList.get(position).getmMonday()!=null && mScheduleList.get(position).getmMonday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+" Mon ";

        }
        if (mScheduleList.get(position).getmTuesday()!=null && mScheduleList.get(position).getmTuesday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Tue ";

        }
        if (mScheduleList.get(position).getmWednesday()!=null && mScheduleList.get(position).getmWednesday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Wed ";

        }
        if (mScheduleList.get(position).getmThrusday()!=null && mScheduleList.get(position).getmThrusday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Thus  ";

        }if (mScheduleList.get(position).getmFriday()!=null && mScheduleList.get(position).getmFriday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Fri ";

        }
        if (mScheduleList.get(position).getmSaturday()!=null && mScheduleList.get(position).getmSaturday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Sat ";

        }if (mScheduleList.get(position).getmSunday()!=null && mScheduleList.get(position).getmSunday().equalsIgnoreCase("1")) {

            weeklyDays=weeklyDays+"\n Sun ";

        }

        holder.mWeeklyDays.setText(weeklyDays);

        holder.mDuration.setMovementMethod(new ScrollingMovementMethod());
        return convertView;
    }

}

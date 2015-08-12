package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.CoursesBean;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class CourseListAdapter extends BaseAdapter{

	private class ViewHolder {
ImageView mCourseImage;
TextView mCourseName;
TextView mCourseDesc;
TextView mCourseFees;
TextView mCourseDuration;	
		
	}

	private Activity mContext;
	private ArrayList<CoursesBean> coursesList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	
	public CourseListAdapter(Activity context,
			ArrayList<CoursesBean> coursesList, ImageLoader _imageLoader) {
		super();
		this.mContext = context;
		this.coursesList = coursesList;
		this.imageLoader = _imageLoader;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.khadyam_logo).showImageForEmptyUri(R.drawable.khadyam_logo).cacheInMemory().cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		if(coursesList.size() > 0)
			return coursesList.size();
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
			
			convertView = mInflater.inflate(R.layout.adapter_row_course, null);
			holder = new ViewHolder();
			holder.mCourseImage=(ImageView) convertView.findViewById(R.id.img_courseImg);
			holder.mCourseName=(TextView) convertView.findViewById(R.id.txt_courseName);
			holder.mCourseDesc=(TextView) convertView.findViewById(R.id.txt_courseDescription);
			holder.mCourseFees=(TextView) convertView.findViewById(R.id.txt_courseFees);
			holder.mCourseDuration=(TextView) convertView.findViewById(R.id.txt_courseDuration);
	
			convertView.setTag(holder);
			
		}else {
		holder = (ViewHolder) convertView.getTag();
		
		
		
	}
		try {
			if (coursesList.get(pos).getCourseName()!=null) {
				holder.mCourseName.setText(coursesList.get(pos).getCourseName().toUpperCase());
				
			}
			if (coursesList.get(pos).getCourseDescription()!=null) {
				holder.mCourseDesc.setText(coursesList.get(pos).getCourseDescription());
			}
			
			if (coursesList.get(pos).getCourseDuration()!=null) {
                //TODO patch work to show Day(s) in course duration.
				holder.mCourseDuration.setText(coursesList.get(pos).getCourseDuration()+" Day(s)");
				
			}
			if (coursesList.get(pos).getCourseFees()!=null) {
				holder.mCourseFees.setText(coursesList.get(pos).getCourseFees());
			}
            if (coursesList.get(pos).getCourseThumbImage()!=null) {

                imageLoader.displayImage(coursesList.get(pos).getCourseThumbImage(),holder.mCourseImage,options);
            }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return convertView;

}
}

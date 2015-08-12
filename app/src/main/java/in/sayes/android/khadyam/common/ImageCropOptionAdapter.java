package in.sayes.android.khadyam.common;

import in.sayes.android.khadyam.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImageCropOptionAdapter  extends ArrayAdapter<ImageCropOption> {
	private ArrayList<ImageCropOption> mOptions;
	private LayoutInflater mInflater;
	
	public ImageCropOptionAdapter(@NotNull Context context, ArrayList<ImageCropOption> options) {
		super(context, R.layout.layout_crop_selector, options);
		
		mOptions 	= options;
		
		mInflater	= LayoutInflater.from(context);
	}
	
	@Nullable
    @Override
	public View getView(int position, @Nullable View convertView, ViewGroup group) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.layout_crop_selector, null);
		
		ImageCropOption item = mOptions.get(position);
		
		if (item != null) {
			((ImageView) convertView.findViewById(R.id.iv_icon)).setImageDrawable(item.icon);
			((TextView) convertView.findViewById(R.id.tv_name)).setText(item.title);
			
			return convertView;
		}
		
		return null;
	}
}
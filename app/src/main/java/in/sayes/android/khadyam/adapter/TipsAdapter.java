package in.sayes.android.khadyam.adapter;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.bean.TipsBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TipsAdapter extends BaseAdapter {

	public class ViewHolder {

		TextView mTipsName;
		TextView mTipsDesc;

	}

	private Activity context;
	private ArrayList<TipsBean> viewTipsList;
	private LayoutInflater mInflater;

	public TipsAdapter(@NotNull Activity context, ArrayList<TipsBean> viewTipsList) {
		super();
		this.context = context;
		this.viewTipsList = viewTipsList;

		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		if (viewTipsList.size() > 0)
			return viewTipsList.size();
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

			convertView = mInflater.inflate(R.layout.adapter_row_tips, null);
			holder = new ViewHolder();

			holder.mTipsName = (TextView) convertView
					.findViewById(R.id.txt_tips_title);
			holder.mTipsDesc = (TextView) convertView
					.findViewById(R.id.txt_tips_description);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			if (viewTipsList.get(pos).getTipsName() != null) {
				holder.mTipsName.setText(viewTipsList.get(pos).getTipsName()
						.toUpperCase());
			}
			if (viewTipsList.get(pos).getTipsDescription() != null) {

				holder.mTipsDesc.setText(viewTipsList.get(pos)
						.getTipsDescription());
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}

		return convertView;

	}
}

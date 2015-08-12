package in.sayes.android.khadyam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.Utils;

public class ContactUsActivity extends BaseActivity implements OnClickListener {

	TextView txtPhonw1;
	TextView txtPhonw2;
Activity context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=(Activity)ContactUsActivity.this;
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_contact_us);
		txtPhonw1 = (TextView) findViewById(R.id.txt_phone1);
		txtPhonw1.setOnClickListener(this);
		txtPhonw2 = (TextView) findViewById(R.id.txt_phone2);
		txtPhonw2.setOnClickListener(this);

	}



	@Override
	public void onClick(@NotNull View v) {

		int id = v.getId();
		switch (id) {
		case R.id.txt_phone1:
			Utils.makePhoneCall(context,txtPhonw1.getText().toString().trim());
			break;
		case R.id.txt_phone2:

			Utils.makePhoneCall(context,txtPhonw2.getText().toString().trim());
			break;

		default:
			break;
		}

	}

}

package in.sayes.android.khadyam.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.Utils;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
	private Activity context;
    private Button mButtonGallery;
    private Button mButtonCall;
    private TextView mInstractorDesc;
    private TextView mClassDesc;




    @Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		context=(Activity)HomeActivity.this;
		Utils.fullScreenActivity(context);
		setContentView(R.layout.activity_home);



        mButtonCall=(Button)findViewById(R.id.btn_home_call);
        mButtonGallery=(Button)findViewById(R.id.btn_home_gallery);
        mButtonGallery.setOnClickListener(this);
        mButtonCall.setOnClickListener(this);
        mInstractorDesc= (TextView) findViewById(R.id.txt_home_InstractorDesc);
        mClassDesc= (TextView) findViewById(R.id.txt_home_classDesc);
        Spanned spInstructorDesc = Html.fromHtml(getResources().getString(R.string.home_description1));
        Spanned spClassDesc= Html.fromHtml(getResources().getString(R.string.home_description2));
        mInstractorDesc.setText(spInstructorDesc);
        mClassDesc.setText(spClassDesc);

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        switch (id){
            case R.id.btn_home_call:
                Utils.makePhoneCall(context,getResources().getString(R.string.home_phone_no));
                break;
            case R.id.btn_home_gallery:
                Intent myIntent = new Intent(this, GalleryActivity.class);

                this.startActivity(myIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                break;
        }

    }

    private void makeCall() {

    }
}

package in.sayes.android.khadyam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;


public class SecondBusinessActivity extends BaseActivity{
	

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_second_business);
            TextView mTextDescription= (TextView) findViewById(R.id.txt_CateringService);

            Spanned sp = Html.fromHtml(getResources().getString(R.string.secondBusines_description));

            mTextDescription.setText(sp);
            //Load PDF File
           // CopyReadAssets();

        }

        private void CopyReadAssets()
        {
            AssetManager assetManager = getAssets();

            @Nullable InputStream in = null;
            @Nullable OutputStream out = null;
            @NotNull File file = new File(getFilesDir(), "MMCC.pdf");
            try
            {
                in = assetManager.open("MMCC.pdf");
                out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e)
            {
                Log.e("tag", e.getMessage());
            }

            @NotNull Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(
                    Uri.parse("file://" + getFilesDir() + "/MMCC.pdf"),
                    "application/pdf");

            startActivity(intent);
            finish();
        }

        private void copyFile( @NotNull InputStream in,  @NotNull OutputStream out) throws IOException
        {
            @NotNull byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
            }
        }

    
}

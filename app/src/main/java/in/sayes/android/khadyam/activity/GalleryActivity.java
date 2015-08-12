package in.sayes.android.khadyam.activity;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import in.sayes.android.khadyam.BaseActivity;
import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.Utils;

public class GalleryActivity extends BaseActivity {
    private ViewPager viewPager;
    int height;
    int width;
    Activity context;
    int[] defaultRecipeImages = {R.drawable.khadyam_picture1, R.drawable.khadyam_picture2, R.drawable.khadyam_picture3, R.drawable.khadyam_picture4};
    private int currentPosition = 0;
    private DisplayImageOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=(Activity)GalleryActivity.this;
        Utils.fullScreenActivity(context);
        setContentView(R.layout.activity_gallery);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.khadyam_logo).cacheInMemory().cacheOnDisc().build();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        viewPager = (ViewPager) findViewById(R.id.homeGalleryImage);
        viewPager.setAdapter(new RecipeImagesViewPagerAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                currentPosition = pos;

            }
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }





     class RecipeImagesViewPagerAdapter extends PagerAdapter {

         private LayoutInflater inflater;
         public RecipeImagesViewPagerAdapter() {
             inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         }

         @Override
         public Object instantiateItem(@NotNull View collection, int pos) {

             View view;
             view = inflater.inflate(R.layout.pager_item_how_use_other, null);
             view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

             ImageView imgDemo = (ImageView) view.findViewById(R.id.ItemHowUseOtherImageView);
             imgDemo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
             imgDemo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

imgDemo.setImageBitmap( decodeSampledBitmapFromResource(getResources(), defaultRecipeImages[pos], width, height));



             ((ViewPager) collection).addView(view, 0);
             return view;
         }

         @Override
         public int getItemPosition(Object view) {
             return super.getItemPosition(view);
         }

         @Override
         public void destroyItem(@NotNull View collection, int pos, Object view) {
             ((ViewPager) collection).removeView((View) view);
         }

         @Override
         public void finishUpdate(View collection) {

         }

         @Override
         public int getCount() {

            //4 images -hard coded
             return 4;
         }

         @Override
         public boolean isViewFromObject(View collection, Object view) {
             return collection == ((View) view);
         }

         @Override
         public void restoreState(Parcelable arg0, ClassLoader arg1) {

         }

         @Nullable
         @Override
         public Parcelable saveState() {
             return null;
         }

         @Override
         public void startUpdate(View collection) {

         }

     }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set


        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

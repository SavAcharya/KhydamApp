package in.sayes.android.khadyam.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImageCaptureUtils {
	 /*private static final int MAX_HEIGHT = 2024;
	    private static final int MAX_WIDTH = 2024;*/
	    @Nullable
        public static Bitmap decodeSampledBitmap(@NotNull Context context, Uri selectedImage)
	            throws IOException {

	        /*// First decode with inJustDecodeBounds=true to check dimensions
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	       
	        InputStream imageStream; = context.getContentResolver().openInputStream(selectedImage);
	        BitmapFactory.decodeStream(imageStream, null, options);
	        imageStream.close();

	        // Calculate inSampleSize
	        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);
	        options.inPreferredConfig = Bitmap.Config.RGB_565;
	        // Decode bitmap with inSampleSize set
	        options.inJustDecodeBounds = false;
	        options.inPurgeable=true; //if necessary purge pixels into disk
	        options.inScaled=true;
	        
	        imageStream = context.getContentResolver().openInputStream(selectedImage);
	        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);*/
	    	
	    	
	    	Uri uri = selectedImage;
	    	@Nullable InputStream in = null;
	    	try {
	    	    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
	    	    in = context.getContentResolver().openInputStream(uri);

	    	    // Decode image size
	    	    @NotNull BitmapFactory.Options o = new BitmapFactory.Options();
	    	    o.inJustDecodeBounds = true;
	    	    
	    	 // Calculate inSampleSize
		        o.inSampleSize = calculateInSampleSize(o, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE);
		        o.inPreferredConfig = Bitmap.Config.RGB_565;
		        // Decode bitmap with inSampleSize set
		        o.inJustDecodeBounds = false;
		        o.inPurgeable=true; //if necessary purge pixels into disk
		        o.inScaled=true;
	    	    BitmapFactory.decodeStream(in, null, o);
	    	    in.close();



	    	    int scale = 1;
	    	    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > 
	    	          IMAGE_MAX_SIZE) {
	    	       scale++;
	    	    }
	    	    Log.d("IMAGE", "scale = " + scale + ", orig-width: " + o.outWidth + ",orig-height: " + o.outHeight);

	    	    @Nullable Bitmap b = null;
	    	    in = context.getContentResolver().openInputStream(uri);
	    	    if (scale > 1) {
	    	        scale--;
	    	        // scale to max possible inSampleSize that still yields an image
	    	        // larger than target
	    	        o = new BitmapFactory.Options();
	    	        o.inSampleSize = scale;
	    	        b = BitmapFactory.decodeStream(in, null, o);

	    	        // resize to desired dimensions
	    	        int height = b.getHeight();
	    	        int width = b.getWidth();
	    	        Log.d("IMAGE", "1th scale operation dimenions - width: " + width + ",height: " + height);

	    	        double y = Math.sqrt(IMAGE_MAX_SIZE
	    	                / (((double) width) / height));
	    	        double x = (y / height) * width;

	    	        Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x, 
	    	           (int) y, true);
	    	        b.recycle();
	    	        b = scaledBitmap;
	    	        b= rotateBitmap(uri.getPath(), b);
	    	        System.gc();
	    	    } else {
	    	        b = BitmapFactory.decodeStream(in);
	    	    }
	    	    in.close();

	    	    Log.d("IMAGE", "bitmap size - width: " +b.getWidth() + ", height: " + 
	    	       b.getHeight());
	    	    return b;
	    	} catch (IOException e) {
	    	    Log.e("IMAGE", e.getMessage(),e);
	    	    return null;

	    	}
	       
	    }
	    /**
	     * Rotate an image if required.
	     * @param img
	     * @param selectedImage
	     * @return 
	     */
	    /*public static  Bitmap rotateImageIfRequired(Context context,Bitmap img) {

	        // Detect rotation
	        int rotation=getRotation(context);
	        if(rotation!=0){
	            Matrix matrix = new Matrix();
	            matrix.postRotate(rotation);
	            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
	            img.recycle();
	            return rotatedImg;        
	        }else{
	            return img;
	        }
	    }*/

	    /**
	     * Get the rotation of the last image added.
	     * @param context
	     * @param selectedImage
	     * @return
	     */
	 /*   public static int getRotation(Context context) {
	        int rotation =0;
	        ContentResolver content = context.getContentResolver();


	        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	                new String[] { "orientation", "date_added" },null, null,"date_added desc");

	        if (mediaCursor != null && mediaCursor.getCount() !=0 ) {
	            while(mediaCursor.moveToNext()){
	                rotation = mediaCursor.getInt(0);
	                break;
	            }
	        }
	        mediaCursor.close();
	        return rotation;
	    }
	    
	    
	    public static int getRotation(Context context,Uri imageUri) {
	    	Cursor cursor = context.getContentResolver().query(imageUri,
	                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

	        if (cursor.getCount() != 1) {
	            return -1;
	        }

	        cursor.moveToFirst();
	        return cursor.getInt(0);
	    }*/
	    
	    
	    public static int calculateInSampleSize(@NotNull BitmapFactory.Options options, int reqWidth,
	            int reqHeight)
	    {
	        // Raw height and width of image
	        final int height = options.outHeight;
	        final int width = options.outWidth;
	        int inSampleSize = 1;

	        if(reqWidth <= 0)
	        {
	            reqWidth = 1;
	        }

	        if(reqHeight <= 0)
	        {
	            reqHeight = 1;
	        }

	        if(height > reqHeight || width > reqWidth)
	        {
	            if(width > height)
	            {
	                inSampleSize = Math.round((float)height / (float)reqHeight);
	            }
	            else
	            {
	                inSampleSize = Math.round((float)width / (float)reqWidth);
	            }
	        }

	        return inSampleSize;
	    }
	    
	    
	    
	    
	    public static Bitmap rotateBitmap(String src, @NotNull Bitmap bitmap) {
	        try {
	            int orientation = getExifOrientation(src);
	            
	            if (orientation == 1) {
	                return bitmap;
	            }
	            
	            @NotNull Matrix matrix = new Matrix();
	            switch (orientation) {
	            case 2:
	                matrix.setScale(-1, 1);
	                break;
	            case 3:
	                matrix.setRotate(180);
	                break;
	            case 4:
	                matrix.setRotate(180);
	                matrix.postScale(-1, 1);
	                break;
	            case 5:
	                matrix.setRotate(90);
	                matrix.postScale(-1, 1);
	                break;
	            case 6:
	                matrix.setRotate(90);
	                break;
	            case 7:
	                matrix.setRotate(-90);
	                matrix.postScale(-1, 1);
	                break;
	            case 8:
	                matrix.setRotate(-90);
	                break;
	            default:
	                return bitmap;
	            }
	            
	            try {
	                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	                bitmap.recycle();
	                return oriented;
	            } catch (OutOfMemoryError e) {
	                e.printStackTrace();
	                return bitmap;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        
	        return bitmap;
	    }
	    
	    private static int getExifOrientation(String src) throws IOException {
	        int orientation = 1;
	        
	        try {
	            /**
	             * if your are targeting only api level >= 5
	             * ExifInterface exif = new ExifInterface(src);
	             * orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
	             */
	            if (Build.VERSION.SDK_INT >= 5) {
	                Class<?> exifClass = Class.forName("android.media.ExifInterface");
	                Constructor<?> exifConstructor = exifClass.getConstructor(new Class[] { String.class });
	                @NotNull Object exifInstance = exifConstructor.newInstance(new Object[] { src });
	                Method getAttributeInt = exifClass.getMethod("getAttributeInt", new Class[] { String.class, int.class });
	                Field tagOrientationField = exifClass.getField("TAG_ORIENTATION");
	                @NotNull String tagOrientation = (String) tagOrientationField.get(null);
	                orientation = (Integer) getAttributeInt.invoke(exifInstance, new Object[] { tagOrientation, 1});
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        } catch (NoSuchFieldException e) {
	            e.printStackTrace();
	        }
	        
	        return orientation;
	    }
	    public static String getImagePath(@NotNull Context ctx, @NotNull Uri uri){
	    	   Cursor cursor = ctx.getContentResolver().query(uri, null, null, null, null);
	    	   cursor.moveToFirst();
	    	   String document_id = cursor.getString(0);
	    	   document_id = document_id.substring(document_id.lastIndexOf(":")+1);
	    	   cursor.close();

	    	   cursor =ctx. getContentResolver().query( 
	    	   android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	    	   null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
	    	   cursor.moveToFirst();
	    	   String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
	    	   cursor.close();

	    	   return path;
	    	}
}

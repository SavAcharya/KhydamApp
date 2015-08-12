package in.sayes.android.khadyam.common;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Utils {

	@NotNull
    public static String listToString(@Nullable List<?> arr) {
		@NotNull String TOKEN = "|";
		if (arr != null && arr.size() > 0) {
			@NotNull StringBuilder sb = new StringBuilder();
			int size = arr.size();
			for (int i = 0; i < size; i++) {
				sb.append(arr.get(i));
				if (i < size - 1)
					sb.append(TOKEN);
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	@NotNull
    public static List<String> StringToList(@NotNull String str) {
		@NotNull String TOKEN = "|";
		@NotNull ArrayList<String> strList = new ArrayList<String>();
		if (str.trim().length() > 0) {
			@NotNull StringTokenizer strToken = new StringTokenizer(str, TOKEN);
			while (strToken.hasMoreTokens()) {
				strList.add(strToken.nextToken());
			}
		}
		// System.out.println("size : " + strList.size());
		return strList;
	}

	public static String stripHTML(@NotNull String str) {
		@NotNull String expr = "<.*?>";
		@NotNull String exprDiv = "</?div[^>]*?>";
		return str.replaceAll(exprDiv, ". ").replaceAll(expr, "");
	}

	@NotNull
    public static String secToString(int sec) {
		if (sec > 60) {
			// return (sec/60) + " min " + (sec % 60) + " sec";
			return Math.round(sec / 60.0) + " mins";
		} else {
			// return sec+ " sec";
			return "1 min"; // used by google as min time taken
		}
	}

	@NotNull
    public static String mtrToMile(int mtr) {
		return new DecimalFormat("#.##").format((mtr * 0.00062137119))
				+ " miles";
	}

	public static boolean isOnline(@NotNull Activity context) {
		@NotNull ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	@Nullable
    public static Bitmap getCroppedBitmap(@Nullable Bitmap bitmap) {
		if (bitmap != null) {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			@NotNull Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			@NotNull final Paint paint = new Paint();
			@NotNull final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
					bitmap.getWidth() / 2, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		}
		return null;
	}

	// http://graph.facebook.com/100003516621384/picture?type=large
	@Nullable
    public static Bitmap urlToBitmapImage(String url) {
		@Nullable String AvatarUrl = null;
		@Nullable Bitmap AvatarBitmap = null;
		try {
			AvatarUrl = url;
			@NotNull HttpGet httpRequest = new HttpGet(URI.create(AvatarUrl));
			@NotNull HttpClient httpclient = new DefaultHttpClient();
			@NotNull HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			@NotNull BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			AvatarBitmap = BitmapFactory.decodeStream(bufHttpEntity
					.getContent());
			httpRequest.abort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return AvatarBitmap;
	}

	@Nullable
    public static String urlToStringImage(String Url) {
		@Nullable URL AvatarUrl = null;
		@Nullable Bitmap AvatarBitmap = null;
		@Nullable String profileImage = null;
		try {
			AvatarUrl = new URL(Url);
			AvatarBitmap = BitmapFactory.decodeStream(AvatarUrl
					.openConnection().getInputStream());
			profileImage = Utils.BitMapToString(AvatarBitmap);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return profileImage;
	}

	@Nullable
    public static Bitmap getStringToBitMap(@NotNull String image) {
		try {
			byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	// To get Rounded Shape Image...
	@Nullable
    public static Bitmap getRoundedShape(@Nullable Bitmap bitmap) {
		// TODO Auto-generated method stub
		if (bitmap != null) {
			Bitmap targetBitmap = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Bitmap.Config.ARGB_8888);
			@NotNull Canvas canvas = new Canvas(targetBitmap);

			final int color = 0xff424242;
			@NotNull final Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);

			@NotNull Path path = new Path();
			path.addCircle(
					((float) bitmap.getWidth() - 1) / 2,
					((float) bitmap.getHeight() - 1) / 2,
					(Math.min(((float) bitmap.getWidth()),
							((float) bitmap.getHeight())) / 2),
					Path.Direction.CCW);
			canvas.clipPath(path);
			@Nullable Bitmap sourceBitmap = bitmap;
			canvas.drawBitmap(
					bitmap,
					new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
							.getHeight()),
					new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
							.getHeight()), paint);
			return targetBitmap;
		}
		return null;

	}

	public static void fullScreenActivity(@NotNull Activity context) {
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
		context.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@NotNull
    public static Boolean SaveMyProfileImageToApp(@NotNull Activity cxt, @NotNull Bitmap bitmap,
			String FileName) {
		try {
			FileOutputStream fos = cxt.openFileOutput(FileName,
					Context.MODE_PRIVATE);
			@NotNull ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the
																	// bitmap
																	// object
			byte[] b = baos.toByteArray();
			fos.write(b);
			fos.flush();
			fos.close();
			System.out.println("Image Saved : " + FileName);
			return true;
		} catch (Exception er) {
			System.out.println(" Errrrrrrrrrrrrrrrrrr while Saving  : ("
					+ FileName + ") " + er.getMessage());
			return false;
		}
	}

	@NotNull
    public static String BitMapToString(@NotNull Bitmap bitmap) {
		System.out.println("fb bitmap====" + bitmap);
		@NotNull ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] arr = baos.toByteArray();
		@NotNull String result = Base64.encodeToString(arr, Base64.DEFAULT);
		return result;
	}

	@Nullable
    public static Bitmap StringToBitMap(@NotNull String image) {
		try {
			byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	@Nullable
    public static Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			@NotNull URL url = new URL(src);
			@NotNull HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return null;
		}
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(@NotNull Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	@NotNull
    public static String calculateTimeDiff(@NotNull Calendar calServer, @NotNull Calendar calLocal) {

		@NotNull String lastActiveTime = "0";
		long milsecs1 = calServer.getTimeInMillis();
		long milsecs2 = calLocal.getTimeInMillis();
		long diff = milsecs2 - milsecs1;
		long dsecs = diff / 1000;
		long dminutes = diff / (60 * 1000);
		long dhours = diff / (60 * 60 * 1000);
		long ddays = diff / (24 * 60 * 60 * 1000);
		System.out.println("Vlaue of diff - " + diff);
		if (diff < 0) {
			lastActiveTime = " Now";
		} else {
			if (dsecs <= 60) {
				lastActiveTime = dsecs + " sec ago";
			}
			if (dsecs >= 60) {
				lastActiveTime = dminutes + " min ago";
			}
			if (dminutes >= 60) {
				lastActiveTime = dhours + " hr ago";
			}
			if (dhours >= 24) {
				lastActiveTime = ddays + " days ago";
			}
		}
		return lastActiveTime;
	}

	@Nullable
    public static Calendar convertStringIntoCalender(String strDate) {
		Calendar cal = Calendar.getInstance();
		@NotNull SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			cal.setTime(sdf.parse(strDate));
			return cal;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentDateTime() {

		@NotNull SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		@NotNull Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getGMTToLocalTime(String GMTTime, @NotNull String DateFormat) {

		String LocalTime = "";
		TimeZone localTimeZone = Calendar.getInstance().getTimeZone();
		int offset = TimeZone.getTimeZone(localTimeZone.getID()).getRawOffset();

		try {
			@NotNull SimpleDateFormat gmt_date_formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			Date dateGMT = gmt_date_formatter.parse(GMTTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateGMT);

			offset = offset + localTimeZone.getDSTSavings();
			cal.add(Calendar.MILLISECOND, offset);

			@NotNull SimpleDateFormat local_date_formatter = new SimpleDateFormat(
					DateFormat, Locale.getDefault());
			LocalTime = local_date_formatter.format(cal.getTime());
		} catch (Exception e) {

		}
		return LocalTime;
	}

	public static boolean isNotNullOrEmpty(@NotNull String data) {

		@NotNull Boolean isSuccess = true;

		if (data.equalsIgnoreCase(null)) {
			isSuccess = false;
		} else if (data.trim().length() <= 0) {
			isSuccess = false;
		} else if (data.trim().equalsIgnoreCase("null")) {
			isSuccess = false;
		} else if (data.trim().equalsIgnoreCase("\"\"")) {
			isSuccess = false;
		}
		return isSuccess;
	}

	@NotNull
    public static String FirstLetterInUpperCase(@NotNull String word) {

		@NotNull String result = "";
		if (word.length() > 0) {
			char c = word.charAt(0);
			@NotNull String splitedString = word.substring(1, word.length());
			result = Character.toUpperCase(c) + splitedString;
		}
		return result;
	}

	public static boolean isSuccess(@NotNull Activity context, String response) {

		@NotNull Boolean isSuccess = true;
		@Nullable JSONObject joResponse = null;
		try {
			joResponse = new JSONObject(response);
			int success = joResponse.getInt("success");
			if (success == 1) {
			} else {
				@NotNull String error = joResponse.getString("error").trim();
				AlertUtility.showToast(context, error);
				isSuccess = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	
	public static String getDeviceId(@NotNull Context context){
		
		String  deviceId=Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		
		return deviceId;
		
		
	}


	public final static boolean isValidEmailAddress(String email) {
		   boolean result;
		   CharSequence inputStr = email;
		   if (TextUtils.isEmpty(inputStr)) {
			    result= false;
			  } else {
			    result= android.util.Patterns.EMAIL_ADDRESS.matcher(inputStr).matches();
			  }
		   
		   
		   
		   
		   
		  /* String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		   result= email.matches(EMAIL_REGEX);*/
		   
		   return result;
		}

    public final static void makePhoneCall(Context context,String phoneNo) {
        @NotNull Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+" + phoneNo));
        context.startActivity(callIntent);
    }

}

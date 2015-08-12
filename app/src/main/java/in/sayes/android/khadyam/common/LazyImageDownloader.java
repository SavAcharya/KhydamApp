package in.sayes.android.khadyam.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;

import com.nostra13.universalimageloader.core.download.URLConnectionImageDownloader;

import org.jetbrains.annotations.NotNull;

public class LazyImageDownloader extends URLConnectionImageDownloader {

	public static final String PROTOCOL_ASSETS = "assets";
	public static final String PROTOCOL_DRAWABLE = "drawable";

	private static final String PROTOCOL_ASSETS_PREFIX = PROTOCOL_ASSETS + "://";
	private static final String PROTOCOL_DRAWABLE_PREFIX = PROTOCOL_DRAWABLE + "://";

	private Context context;

	public LazyImageDownloader(Context context) {
		this.context = context;
	}

	@Override
	protected InputStream getStreamFromOtherSource(@NotNull URI imageUri) throws IOException {
		String protocol = imageUri.getScheme();
		if (PROTOCOL_ASSETS.equals(protocol)) {
			return getStreamFromAssets(imageUri);
		} else if (PROTOCOL_DRAWABLE.equals(protocol)) {
			return getStreamFromDrawable(imageUri);
		} else {
			return super.getStreamFromOtherSource(imageUri);
		}
	}

	@NotNull
    private InputStream getStreamFromAssets(@NotNull URI imageUri) throws IOException {
		@NotNull String filePath = imageUri.toString().substring(PROTOCOL_ASSETS_PREFIX.length()); // Remove "assets://" prefix from image URI
		return context.getAssets().open(filePath);
	}

	@NotNull
    private InputStream getStreamFromDrawable(@NotNull URI imageUri) {
		@NotNull String drawableIdString = imageUri.toString().substring(PROTOCOL_DRAWABLE_PREFIX.length()); // Remove "drawable://" prefix from image URI
		int drawableId = Integer.parseInt(drawableIdString);
		@NotNull BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(drawableId);
		Bitmap bitmap = drawable.getBitmap();

		@NotNull ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, os);
		return new ByteArrayInputStream(os.toByteArray());
	}
}

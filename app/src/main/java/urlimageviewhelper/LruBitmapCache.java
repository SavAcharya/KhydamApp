package urlimageviewhelper;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

public class LruBitmapCache extends LruCache<String, Bitmap> {
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, @NotNull Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}

package urlimageviewhelper;

import android.graphics.drawable.Drawable;

import org.jetbrains.annotations.NotNull;

public final class DrawableCache extends SoftReferenceHashTable<String, Drawable> {
    @NotNull
    private static DrawableCache mInstance = new DrawableCache();
    
    @NotNull
    public static DrawableCache getInstance() {
        return mInstance;
    }
    
    private DrawableCache() {
    }
}

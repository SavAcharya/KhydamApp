
package urlimageviewhelper;

import java.io.InputStream;

import android.content.Context;
import android.os.AsyncTask;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AssetUrlDownloader implements UrlDownloader {
    @Override
    public void download(@NotNull final Context context, @NotNull final String url, final String filename,
            @NotNull final UrlDownloaderCallback callback, @NotNull final Runnable completion) {
        @NotNull final AsyncTask<Void, Void, Void> downloader = new AsyncTask<Void, Void, Void>() {
            @Nullable
            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    String relativePath = url.replaceFirst("file:///android_asset/", "");
                    @NotNull InputStream is = context.getAssets().open(relativePath);
                    callback.onDownloadComplete(AssetUrlDownloader.this, is, null);
                    return null;
                }
                catch (@NotNull final Throwable e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final Void result) {
                completion.run();
            }
        };

        UrlImageViewHelper.executeTask(downloader);
    }

    @Override
    public boolean allowCache() {
        return false;
    }

    @Override
    public boolean canDownloadUrl(@NotNull String url) {
        return url.startsWith("file:///android_asset/");
    }
}

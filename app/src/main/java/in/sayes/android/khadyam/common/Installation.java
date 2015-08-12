package in.sayes.android.khadyam.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Installation {
    @Nullable
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    @Nullable
    public synchronized static String getIMEINumber(@NotNull Activity context) {
        if (sID == null) {  
            @NotNull File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    @NotNull
    private static String readInstallationFile(File installation) throws IOException {
        @NotNull RandomAccessFile f = new RandomAccessFile(installation, "r");
        @NotNull byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(@NotNull File installation) throws IOException {
        @NotNull FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
package aie.sss.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class FileUtil {
    public static void openFile(Context context, File file) {
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
        context.startActivity(it);
    }
}

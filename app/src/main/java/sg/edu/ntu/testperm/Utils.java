package sg.edu.ntu.testperm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.android.common.logger.Log;

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    /**
     * check whether packageName has the permission
     *
     * @param context     current context
     * @param permission
     * @param packageName package name being checked
     * @return
     */
    public static boolean hasPermission(Context context, String permission, String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get the package name from Intent, and then check whether has permission
     *
     * @param context
     * @param permission
     * @param intent
     * @return
     */
    public static boolean hasPermission(Context context, String permission, Intent intent) {
        String pkgName = intent.getStringExtra("PackageNameInfo");
        if (pkgName == null) {
            Log.i(TAG, "PackageNameInfo not found, assuming no permission\n\t" + permission);
            return false;
        }
        Log.i(TAG, "pkgName=" + pkgName);
        return hasPermission(context, permission, pkgName);
    }

    /**
     * if is via startActivityForResult, get Calling package; otherwise, from Intent
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean hasPermForActivity(Activity activity, String permission) {
        String pkgName = activity.getCallingPackage();
        if (pkgName == null) { // not via startActivityForResult
            Intent intent = activity.getIntent();
            return hasPermission(activity, permission, intent);
        } else {
            return hasPermission(activity, permission, pkgName);
        }
    }

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length <= 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}

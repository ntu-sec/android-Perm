package sg.edu.ntu.testperm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

public class Utils {
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

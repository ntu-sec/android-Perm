package sg.edu.ntu.testperm;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.UserDictionary;

import com.example.android.common.logger.Log;

import java.util.Arrays;
import java.util.List;

public class MyContentProxy {
    private static final String TAG = MyContentProxy.class.getSimpleName();
    private ContextWrapper contextWrapper;

    public MyContentProxy(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    public void alertInfo(String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(contextWrapper).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void dumpUserDict() {
        Cursor cursor = contextWrapper.getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            Log.d(TAG, "cursor null");
        } else {
            if (cursor.getCount() == 0) {
                Log.d(TAG, "cursor empty");
            } else {
                int index = cursor.getColumnIndex(UserDictionary.Words.WORD);
                String newWord;
                Log.d(TAG, cursor.toString());
                while (cursor.moveToNext()) {
                    newWord = cursor.getString(index);
                    Log.d(TAG, "word: " + newWord);
                }
            }
            cursor.close();
        }
    }

    public void getContextInfo() {
        for (String db : contextWrapper.databaseList()) {
            Log.d(TAG, "db: " + contextWrapper.getDatabasePath(db));
        }
        for (String file : contextWrapper.fileList()) {
            Log.d(TAG, "file: " + file);
        }
        Log.d(TAG, contextWrapper.getPackageCodePath());
        Log.d(TAG, String.format("%s %s", contextWrapper, contextWrapper.getApplicationInfo()));
        Log.d(TAG, String.format("%s %s", contextWrapper.getApplicationContext(), contextWrapper.getApplicationContext().getApplicationInfo()));
    }

    public int getPidFromPackageName(String pkgname) {
        ActivityManager manager = (ActivityManager) contextWrapper.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo service : services) {
            Log.i(TAG, String.valueOf(service.pid));
            if (Arrays.asList(service.pkgList).contains(pkgname)) {
                Log.i(TAG, "XXX: " + pkgname);
                return service.pid;
            }
        }
        return 0;
    }

}

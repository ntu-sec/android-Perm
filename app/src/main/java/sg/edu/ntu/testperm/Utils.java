package sg.edu.ntu.testperm;

import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.util.Log;

public class Utils {
    public static final String TAG = Utils.class.getSimpleName();

    public static void dumpUserDict(ContextWrapper contextWrapper) {
        Cursor cursor = contextWrapper.getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            Log.d(TAG, "getContentResolver cursor null");
        } else {
            if (cursor.getCount() == 0) {
                Log.d(TAG, "getContentResolver cursor empty");
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

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length <= 0) {
            return false;
        }
        for (int result : grantResults) {
            Log.i(TAG, "" + result);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void getContextInfo(ContextWrapper wrapper) {
        for (String db : wrapper.databaseList()) {
            Log.d(TAG, "db: " + wrapper.getDatabasePath(db));
        }
        for (String file : wrapper.fileList()) {
            Log.d(TAG, "file: " + file);
        }
        Log.d(TAG, wrapper.getPackageCodePath());
        Log.d(TAG, String.format("%s %s", wrapper, wrapper.getApplicationInfo()));
        Log.d(TAG, String.format("%s %s", wrapper.getApplicationContext(), wrapper.getApplicationContext().getApplicationInfo()));
    }
}

package sg.edu.ntu.testperm.simpleservice;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.android.common.logger.Log;

import sg.edu.ntu.testperm.MyContentProxy;
import sg.edu.ntu.testperm.Utils;

public class SimpleService extends Service {

    MyContentProxy contentProxy;

    public static final String TAG = SimpleService.class.getSimpleName();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private String dumpDeviceInfoImpl(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();
        Log.i(TAG, "ID=" + id);
        return id;
    }

    public void dumpDeviceInfo(Context context, Intent intent) {
        String info;
        String perm = Manifest.permission.READ_PHONE_STATE;
        Log.i(TAG, "callPid=" + Binder.getCallingPid() + " myPid=" + Process.myPid());
        if (Utils.hasPermission(this, perm, intent)) {
            Log.i(TAG, perm + " already granted");
            info = dumpDeviceInfoImpl(context);
        } else {
            Log.i(TAG, "not granted " + perm);
            info = "denied perm " + perm;
        }
        Log.i(TAG, "info=" + info);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        contentProxy = new MyContentProxy(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "xxx    " + Binder.getCallingUid());
        Log.i(TAG, "xxx    " + Binder.getCallingPid());
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        dumpDeviceInfo(this, intent);
        return START_STICKY;
    }

}

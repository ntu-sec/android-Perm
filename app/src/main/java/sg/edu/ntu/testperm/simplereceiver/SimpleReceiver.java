package sg.edu.ntu.testperm.simplereceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import android.telephony.TelephonyManager;

import com.example.android.common.logger.Log;

public class SimpleReceiver extends BroadcastReceiver {
    public static final String TAG = SimpleReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String info;
        String perm = Manifest.permission.READ_PHONE_STATE;
        Log.i(TAG, "callPid=" + Binder.getCallingPid() + " myPid=" + Process.myPid());
//        if (PermissionChecker.checkCallingPermission(context, perm, "sg.edu.ntu.example.user") != PackageManager.PERMISSION_GRANTED) {
        if (context.checkCallingOrSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "not granted " + perm);
            info = "denied perm " + perm;
        } else {
            Log.i(TAG, perm + " already granted");
            info = dumpDeviceInfoImpl(context);
        }
        sendBack(context, info);
    }

    private void sendBack(Context context, String info) {
        Intent intent = new Intent(info);
        intent.setAction("sg.edu.ntu.user.MYINTENT");
        intent.putExtra("INFO", info);
        context.sendBroadcast(intent);
    }

    public void dumpDeviceInfo(Context context) {
        String info;
        String perm = Manifest.permission.READ_PHONE_STATE;
        Log.i(TAG, "callPid=" + Binder.getCallingPid() + " myPid=" + Process.myPid());
//        if (PermissionChecker.checkCallingPermission(context, perm, "sg.edu.ntu.example.user") != PackageManager.PERMISSION_GRANTED) {
        if (context.checkCallingOrSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "not granted " + perm);
            info = "denied perm " + perm;
        } else {
            Log.i(TAG, perm + " already granted");
            info = dumpDeviceInfoImpl(context);
        }
        sendBack(context, info);
    }

    private String dumpDeviceInfoImpl(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();
        Log.i(TAG, "ID=" + id);
        return id;
    }
}

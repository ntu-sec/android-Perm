package sg.edu.ntu.testperm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;

import java.util.Locale;

import sg.edu.ntu.testperm.provider.ListActivity;
import sg.edu.ntu.testperm.rtperm.RTPermActivity;
import sg.edu.ntu.testperm.simpleprovider.SimpleActivity;
import sg.edu.ntu.testperm.simpleservice.SimpleService;
import sg.edu.ntu.testperm.storageuser.StorageActivity;

public class MainActivity extends SampleActivityBase {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String STORAGE_INTENT = SimpleActivity.class.getPackage().getName();
    public static final String SIMPLE_INTENT = StorageActivity.class.getPackage().getName();
    private MyContentProxy contentProxy;

    View mLayout;

    public void requestPerm(final String perm, final int requestCode) {
        try {
            Log.i(TAG, String.format(Locale.getDefault(), "perm=%s; protectionLevel=%d", perm, getPackageManager().getPermissionInfo(perm, 0).protectionLevel));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{perm}, requestCode);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{perm}, 0);
        }
    }

    public void dumpDeviceInfo(View view) {
        String perm = Manifest.permission.READ_PHONE_STATE;
        if (ContextCompat.checkSelfPermission(getBaseContext(), perm) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "request " + perm);
            requestPerm(perm, Config.PHONE_PERM);
        } else {
            Log.i(TAG, perm + " already granted");
            dumpDeviceInfoImpl();
        }
    }

    public void dumpDeviceInfoSimple(View view) {
        String info;
        String perm = Manifest.permission.READ_PHONE_STATE;
        if (checkSelfPermission(perm) != PackageManager.PERMISSION_DENIED) {
            info = dumpDeviceInfoImpl();
        } else {
            info = "deviceID N.A.";
        }
        Log.i(TAG, "DEVICE INFO: " + info);
    }

    private String dumpDeviceInfoImpl() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
//        contentProxy.alertInfo("deviceInfo", id);
//        contentProxy.alertInfo("info", telephonyManager.getMmsUAProfUrl());
//        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        Display.Mode[] modes = windowManager.getDefaultDisplay().getSupportedModes();
//        for (Display.Mode mode : modes) {
//            Log.i(TAG, mode.toString());
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Config.PHONE_PERM:
                if (Utils.verifyPermissions(grantResults)) {
                    dumpDeviceInfoImpl();
                } else {
                    Log.i(TAG, "requested perm denied");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.simpleLayout);
        contentProxy = new MyContentProxy(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void intentStorageUser(View view) {
        Intent intent = new Intent(this, StorageActivity.class);
        intent.setAction(STORAGE_INTENT);
        startActivity(intent);
    }

    public void runtimePermTest(View view) {
        Intent intent = new Intent(this, RTPermActivity.class);
        intent.setAction("RTPerm");
        startActivity(intent);
    }

    public void dumpUserDict(View view) {
        contentProxy.dumpUserDict();
    }

    public void intentSimpleProvider(View view) {
        Intent intent = new Intent(this, SimpleActivity.class);
        intent.setAction(SIMPLE_INTENT);
        startActivity(intent);
    }

    public void intentPersonProvider(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.setAction("person provider");
        startActivity(intent);
    }

    public void startSimpleService(View view) {
        Intent intent = new Intent(getBaseContext(), SimpleService.class);
        startService(intent);
    }

    public void stopSimpleService(View view) {
        Intent intent = new Intent(getBaseContext(), SimpleService.class);
        stopService(intent);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("sg.edu.ntu.testperm.MYINTENT");
        sendBroadcast(intent);
    }
}

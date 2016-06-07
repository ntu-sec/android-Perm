package sg.edu.ntu.testperm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
            requestPerm(perm, Config.PHONE_PERM);
        } else {
            Log.i(TAG, perm + " already granted");
            dumpDeviceInfoImpl();
        }
    }

    private void dumpDeviceInfoImpl() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        Log.i(TAG, id);
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
}

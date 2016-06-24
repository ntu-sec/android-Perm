package sg.edu.ntu.testperm.provider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sg.edu.ntu.testperm.BuildConfig;
import sg.edu.ntu.testperm.R;
import sg.edu.ntu.testperm.provider.database.Person;

// https://github.com/spacecowboy/AndroidTutorialContentProvider

public class ListActivity extends FragmentActivity implements
        ListFragment.Callbacks {

    @Override
    public void onItemSelected(long id) {
        Intent detailIntent = new Intent(this, PersonActivity.class);
        detailIntent.putExtra(PersonFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    public static final String TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        if (BuildConfig.DEBUG && !getApplicationContext().getClass().getCanonicalName().equals("android.app.Application")) {
            throw new RuntimeException("context not android.app.Application");
        }
        super.onResume();
    }

    private String dumpDeviceInfoImpl(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();
        Log.i(TAG, "ID=" + id);
        return id;
    }

    public void dumpDeviceInfo(Context context) {
        String info;
        String perm = Manifest.permission.READ_PHONE_STATE;
        Log.i(TAG, "callingpid=" + Binder.getCallingPid() + " mypid=" + Process.myPid());
        if (context.checkCallingPermission(perm) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "not granted " + perm);
            info = "activity denied perm " + perm;
        } else {
            Log.i(TAG, perm + " already granted");
            info = dumpDeviceInfoImpl(context);
        }
        Intent intent = new Intent();
        intent.putExtra("INFO", info);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        Log.i(TAG, "onCreate:" + TAG);
        dumpDeviceInfo(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_person_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        boolean result = false;
        if (R.id.newPerson == item.getItemId()) {
            result = true;
            Person p = new Person();
            onItemSelected(p.id);
//            DatabaseHandler.getInstance(this).putPerson(p);
        }
        return result;
    }
}

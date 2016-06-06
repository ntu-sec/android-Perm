package sg.edu.ntu.testperm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    View hahaView;

    public void dumpUserDict() {
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
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

    public void requestPerm(View view) {
        //        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        final String perm = android.Manifest.permission.READ_PHONE_STATE;
        if (ContextCompat.checkSelfPermission(getBaseContext(), perm) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                Log.d(TAG, "SHOULD show");
                Snackbar.make(hahaView, "you should", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{perm}, 0);
                    }
                });
            } else {
                Log.d(TAG, "SHOULD not show");
                ActivityCompat.requestPermissions(this, new String[]{perm}, 0);
            }
        } else {
            Log.d(TAG, "already granted");
        }
    }

    public void dumpDeviceId(View view) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String id = telephonyManager.getDeviceId();
        Log.d(TAG, "deviceid=" + id);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        for (String db : databaseList()) {
            Log.d(TAG, "db: " + getDatabasePath(db));
        }
        for (String file : fileList()) {
            Log.d(TAG, "file: " + file);
        }
        Log.d(TAG, getPackageCodePath());
        Log.d(TAG, String.format("%s %s", this, this.getApplicationInfo()));
        Log.d(TAG, String.format("%s %s", getApplicationContext(), getApplicationContext().getApplicationInfo()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hahaView = findViewById(R.id.hahaha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.fab_notice, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        requestPerm(hahaView);
        dumpUserDict();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            dumpDeviceId(hahaView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

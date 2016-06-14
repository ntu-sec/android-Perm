package sg.edu.ntu.testperm.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        Log.i(TAG, "onCreate:" + TAG);
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

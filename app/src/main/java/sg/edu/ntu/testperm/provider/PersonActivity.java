package sg.edu.ntu.testperm.provider;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import sg.edu.ntu.testperm.R;

public class PersonActivity extends FragmentActivity {
    public static final String TAG = PersonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate:" + TAG);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            if (actionBar.getTitle() == null) {
                actionBar.setTitle("PersonInfo");
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putLong(PersonFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(PersonFragment.ARG_ITEM_ID, -1));
            PersonFragment fragment = new PersonFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.person_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, ListActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

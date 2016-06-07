package sg.edu.ntu.testperm.simpleprovider;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;

import sg.edu.ntu.testperm.R;

public class SimpleActivity extends SampleActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_provider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_provider, menu);
        return true;
    }

    public void onClickAddName(View view) {
        ContentValues values = new ContentValues();
        EditText editText = (EditText) findViewById(R.id.txtName);
        String oldText = editText.getText().toString();
        values.put(SimpleProvider.name, oldText);
        editText.setText("");
        Uri uri = getContentResolver().insert(SimpleProvider.CONTENT_URI, values);
        Log.i(SimpleActivity.class.getSimpleName(), "uri=" + uri);
        Toast.makeText(getBaseContext(), "inserted record: " + oldText, Toast.LENGTH_SHORT).show();
    }
}

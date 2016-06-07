/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package sg.edu.ntu.testperm.storageuser;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

import sg.edu.ntu.testperm.R;

// https://github.com/googlesamples/android-StorageProvider/blob/master/Application/src/main/java/com/example/android/storageprovider/MainActivity.java

/**
 * A simple launcher activity containing a summary sample description
 * and a few action bar buttons.
 */
public class StorageActivity extends SampleActivityBase {

    public static final String TAG = "StorageActivity";

    public static final String FRAGTAG = "StorageFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_user);

        if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            StorageFragment fragment = new StorageFragment();
            transaction.add(fragment, FRAGTAG);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_storage_user, menu);
        return true;
    }

    /**
     * Create a chain of targets that will receive log data
     */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());
        logFragment.getLogView().setTextAppearance(this, R.style.Log);
        logFragment.getLogView().setBackgroundColor(Color.WHITE);


        Log.i(TAG, "Ready");
    }
}

package sg.edu.ntu.testperm.rtperm;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.ntu.testperm.R;

public class RTPermFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rtperm_main, null);

        if (Build.VERSION.SDK_INT < 23) {
            root.findViewById(R.id.button_contacts).setVisibility(View.GONE);
        }
        return root;
    }
}

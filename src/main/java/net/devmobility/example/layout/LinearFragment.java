package net.devmobility.example.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.devmobility.example.R;

/**
 * Created by Ammo on 3/19/2015.
 */
public class LinearFragment extends Fragment {

        public LinearFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_testsql,  container, false);
            return rootView;
        }
}

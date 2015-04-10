package net.devmobility.example.service;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.devmobility.example.R;

public class BinderFragment extends Fragment {

    private static final String TAG = BinderFragment.class.getSimpleName();

    private BinderService binderService;
    private TextView results;
    private boolean bound;

    public BinderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_binder, container, false);
        View startButton = rootView.findViewById(R.id.button_start_binder_service);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bound) {
                    Log.d(TAG, "Binding service.");
                    getActivity().bindService(new Intent(getActivity(), BinderService.class), connection, Context.BIND_AUTO_CREATE);
                } else {
                    Log.d(TAG, "Starting updates.");
                    binderService.startUpdating();
                }
            }
        });
        View stopButton = rootView.findViewById(R.id.button_stop_binder_service);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bound) {
                    Log.d(TAG, "Stopping updates.");
                    binderService.stopUpdating();
                }
            }
        });
        results = (TextView) rootView.findViewById(R.id.binder_result);
        return rootView;
    }

    @Override
    public void onPause() {
        if (bound) {
            getActivity().unbindService(connection);
            bound = false;
            binderService = null;
        }
        super.onPause();
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            Log.i(TAG, "Service connected");
            BinderService.LocalBinder localBinder = (BinderService.LocalBinder) binder;
            binderService = localBinder.getService();
            bound = true;
            Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    switch (message.what) {
                        case BinderService.SHOW_RESULT: {
                            results.append(message.obj + "\n");
                            return true;
                        }
                        default:
                            return false;
                    }
                }
            });
            binderService.setHandler(handler);
            binderService.startUpdating();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "Service disconnected");
        }
    };
}

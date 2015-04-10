package net.devmobility.example.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.devmobility.example.R;

public class BroadcastReceiverFragment extends Fragment {

    private BroadcastReceiver receiver;

    private TextView results;

    public BroadcastReceiverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_broadcast_receiver, container, false);
        final EditText nameText = (EditText) rootView.findViewById(R.id.name_text);
        View sendButton = rootView.findViewById(R.id.button_send_receiver_service);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                Intent startIntent = new Intent(getActivity().getApplicationContext(), BroadcastReceiverService.class);
                startIntent.putExtra(BroadcastReceiverService.NAME, name);
                getActivity().startService(startIntent);
            }
        });
        results = (TextView) rootView.findViewById(R.id.broadcast_receiver_result);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastReceiverService.ACTION);
        getActivity().registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(BroadcastReceiverService.MESSAGE);
                results.setText(message);
            }
        },
        filter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(receiver);
        super.onStop();
    }
}

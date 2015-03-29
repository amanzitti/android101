package net.devmobility.example.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import net.devmobility.example.R;

/**
 * Created by Ammo on 3/19/2015.
 */
public class ListViewFragment extends Fragment {

        private ListView list;
        private SuperheroesAdapter adapter;
        private Superheroes model;

        public ListViewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
            list = (ListView) rootView.findViewById(R.id.list);
            model = new Superheroes();
            adapter = new SuperheroesAdapter(model);
            list.setAdapter(adapter);
            final EditText name = (EditText) rootView.findViewById(R.id.edit_name);
            final EditText ability = (EditText) rootView.findViewById(R.id.edit_ability);
            rootView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameVal = name.getText().toString();
                    String abilityVal = ability.getText().toString();
                    model.add(new Superhero(nameVal, abilityVal, Color.CYAN));
                    adapter.notifyDataSetChanged();
                }
            });
            return rootView;
        }
}

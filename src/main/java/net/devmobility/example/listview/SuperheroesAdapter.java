package net.devmobility.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.devmobility.example.R;

public class SuperheroesAdapter extends BaseAdapter {

    private Superheroes model;

    public SuperheroesAdapter(Superheroes model) {
        this.model = model;
    }
    @Override
    public int getCount() {
        return (model ==  null) ? 0 : model.size();
    }

    @Override
    public Object getItem(int i) {
        return (model == null)?null:model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem, parent, false);
        }
        Superhero hero = (Superhero) getItem(i);
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView ability = (TextView)view.findViewById(R.id.ability);
        name.setText(hero.getName());
        ability.setText(hero.getAbility());
        view.setBackgroundColor(hero.getCostumeColor());
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return (model==null)?true:model.isEmpty();
    }
}

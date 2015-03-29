package net.devmobility.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.devmobility.example.layout.GridFragment;
import net.devmobility.example.layout.LinearFragment;
import net.devmobility.example.layout.LinearVerticalFragment;
import net.devmobility.example.layout.RelativeFragment;
import net.devmobility.example.layout.TableFragment;
import net.devmobility.example.listview.ListViewFragment;

public class ExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RelativeFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_switch_layout) {
            switchLayout();
            return true;
        } else if (id == R.id.action_show_list) {
            swapFragment(new ListViewFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        CharSequence selections[] = new CharSequence[] {"Relative", "Linear - Default ", "Linear - Vertical", "Table", "Grid"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_layout);
        builder.setItems(selections, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        swapFragment(new RelativeFragment());
                        break;
                    case 1:
                        swapFragment(new LinearFragment());
                        break;
                    case 2:
                        swapFragment(new LinearVerticalFragment());
                        break;
                    case 3:
                        swapFragment(new TableFragment());
                        break;
                    case 4:
                        swapFragment(new GridFragment());
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        });
        builder.show();
    }

    private void swapFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

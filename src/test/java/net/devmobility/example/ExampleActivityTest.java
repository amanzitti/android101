package net.devmobility.example;

import android.graphics.Color;

import net.devmobility.example.listview.SuperheroesAdapter;
import net.devmobility.example.listview.Superhero;
import net.devmobility.example.listview.Superheroes;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class ExampleActivityTest {

    @Test
    @ Ignore
    public void testActivityCanBeBuilt() throws Exception {
        Assert.assertTrue(Robolectric.buildActivity(ExampleActivity.class).create().get() != null);
    }

    @Test
    public void testAdapterCount() {

        Superheroes  model = new Superheroes();
        model.add(new Superhero("Superman", "Flying", Color.BLUE));
        model.add(new Superhero("Spiderman", "Climbing", Color.RED));
        model.add(new Superhero("Batman","Driving",Color.BLACK));
        SuperheroesAdapter adapter =  new SuperheroesAdapter(model);
        Assert.assertEquals(3, adapter.getCount());
    }

    @Test
    public void testItemReturnedIsTheRightOne() {
        Superheroes  model = new Superheroes();
        model.add(new Superhero("Superman", "Flying", Color.BLUE));
        model.add(new Superhero("Spiderman", "Climbing", Color.RED));
        SuperheroesAdapter adapter = new SuperheroesAdapter((model));
        Superhero hero = (Superhero) adapter.getItem(1);
        Assert.assertNotNull(hero);
        Assert.assertEquals("Spiderman", hero.getName());
    }
}

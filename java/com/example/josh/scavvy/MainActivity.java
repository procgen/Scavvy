package com.example.josh.scavvy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kobe.scavvy.ScavHuntActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ScavHunt> scavHunts = new ArrayList<ScavHunt>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ScavHuntListAdapter adapter = new ScavHuntListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*makeSampleData();
        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.huntListLayout);

        for (int i = 0; i < scavHunts.size(); i++) {
            Button btn = new Button (MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            btn.setLayoutParams(params);
            btn.setTextSize(32);
            btn.setText(scavHunts.get(i).getName());
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(MainActivity.this, ScavHuntActivity.class);
                    myIntent.putExtra("key", scavHunts.get(i)); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }
            });
            yourlayout.addView(btn);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void makeSampleData(){
        //Make mammals scavhunt
        ArrayList<ScavItem> scavItems = new ArrayList<ScavItem>();
        scavItems.add(new ScavItem("Cat"));
        scavItems.add(new ScavItem("Dog"));
        scavItems.add(new ScavItem("Human"));
        scavItems.add(new ScavItem("Squirrel"));
        scavItems.add(new ScavItem("Cow"));
        scavItems.add(new ScavItem("Sheep"));
        ScavHunt bigAnimals = new ScavHunt("Mammals", scavItems, 0);
        this.scavHunts.add(bigAnimals);
        scavItems.clear();
        scavItems.add(new ScavItem("Doritos"));
        scavItems.add(new ScavItem("Mountain Dew"));
        scavItems.add(new ScavItem("Cheeto Dust"));
        scavItems.add(new ScavItem("Waifu"));
        ScavHunt neckbeard_shit = new ScavHunt("Neckbeard Shit", scavItems, 0);
        this.scavHunts.add(neckbeard_shit);
    }*/
}

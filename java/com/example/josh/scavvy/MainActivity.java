package com.example.josh.scavvy;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ScavHunt> scavHunts = new ArrayList<ScavHunt>();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_WRITE_PERMISSION = 2;

    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ScavvyPics");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("ScavvyPics", "failed to create directory");
                return null;
            }
        }

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_TEST" + ".jpg");
        return mediaFile;
    }

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
                    File photoFile = null;
                    photoFile = getOutputMediaFile();
                    if(photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewPictureIntent = new Intent(MainActivity.this, ViewPhotoActivity.class);
                viewPictureIntent.putExtra("uri", getOutputMediaFile().getAbsolutePath());
                startActivity(viewPictureIntent);
            }
        });
        makeSampleData();
        LinearLayout yourlayout= (LinearLayout) findViewById(R.id.huntListLayout);

        for (int i = 0; i < scavHunts.size(); i++) {
            Button btn = new Button (MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            btn.setLayoutParams(params);
            btn.setTextSize(32);
            btn.setText(scavHunts.get(i).getName());
            yourlayout.addView(btn);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = getOutputMediaFile();

            if(imgFile.exists()){

                final Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = (ImageView) findViewById(R.id.imageView);

                myImage.setImageBitmap(myBitmap);

                final VisionSingle vision = VisionSingle.getInstance();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            final List<EntityAnnotation> labels = vision.visionRequest(myBitmap);
                            for (EntityAnnotation label : labels)
                            {
                                Log.d("vision", "Label: " + label.getDescription() + " " + label.getScore());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message = "";
                                    for (EntityAnnotation label : labels)
                                    {
                                        message += label.getDescription() + ": " + label.getScore() +", ";
                                    }
                                    Toast.makeText(getApplicationContext(),
                                            message, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        catch (IOException e)
                        {
                            Log.d("vision", "Vision request gave IOException");
                        }
                    }
                });
            }
        }
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

    public void makeSampleData(){
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
    }
}

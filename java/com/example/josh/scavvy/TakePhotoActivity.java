package com.example.josh.scavvy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.view.Window;
import android.view.WindowManager;

import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {

    final int REQUEST_IMAGE_CAPTURE = 1;
    final int REQUEST_WRITE_PERMISSION = 2;

    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ScavvyPics");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_take_photo);

        android.content.Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = getOutputMediaFile();
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(TakePhotoActivity.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = getOutputMediaFile();

            if(imgFile.exists()){

                final Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = (ImageView) findViewById(R.id.processedImage);

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
                                    for (EntityAnnotation label : labels) {
                                        message += label.getDescription() + ": " + label.getScore() + ", ";
                                    }
                                    Toast.makeText(getApplicationContext(),
                                            message, Toast.LENGTH_LONG).show();
                                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
}

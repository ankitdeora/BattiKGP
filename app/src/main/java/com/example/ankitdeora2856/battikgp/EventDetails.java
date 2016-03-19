package com.example.ankitdeora2856.battikgp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ankitdeora2856 on 19-03-2016.
 */
public class EventDetails extends AppCompatActivity {
   // Toolbar toolbar;

    TextView heading;
    TextView description;
    ImageView d_img;
    Bitmap d_bitmap;
    ProgressDialog d_pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);

        heading = (TextView) findViewById(R.id.id_heading);
        description = (TextView) findViewById(R.id.id_description);
        d_img = (ImageView) findViewById(R.id.id_event_img);

        heading.setText(getIntent().getExtras().getString("EName"));
        description.setText(getIntent().getExtras().getString("EDescription"));
        String img_url = getIntent().getExtras().getString("EPhoto");
        new LoadImage().execute(img_url);
        //System.out.println("Inside new activity");
        //System.out.println(img_url);
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            d_pDialog = new ProgressDialog(EventDetails.this);
            d_pDialog.setMessage("Loading ....");
            d_pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                d_bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return d_bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                d_img.setImageBitmap(image);
                d_pDialog.dismiss();

            }else{

                d_pDialog.dismiss();
                Toast.makeText(EventDetails.this, "Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}

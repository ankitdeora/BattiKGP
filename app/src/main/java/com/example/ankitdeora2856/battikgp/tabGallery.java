package com.example.ankitdeora2856.battikgp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ankitdeora2856 on 17-03-2016.
 */
public class tabGallery extends Fragment {
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_gallery,container,false);
        Button bt = (Button) v.findViewById(R.id.id_ok);
        img = (ImageView) v.findViewById(R.id.id_img);
        new LoadImage().execute("http://www.iconshock.com/img_vista/ANDROID/business/jpg/service_electricity_icon.jpg");
        //Bundle bundle = getArguments();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Button pressed in Gallery Tab", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

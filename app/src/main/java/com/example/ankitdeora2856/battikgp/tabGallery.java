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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ankitdeora2856 on 17-03-2016.
 */
public class tabGallery extends Fragment {
    //ImageView img;
    ListView list;
    Bitmap d_bitmap;
    ProgressDialog d_pDialog;

    String myJSON;
    String JSON_STRING;
    private static final String TAG_PATH = "photoPath";
    private static final String TAG_YEAR = "year";
    private static final String TAG_NAME = "eventName";
    private static final String TAG_RESULTS = "result";
    JSONArray events = null;
    ArrayList<HashMap<String, String>> eventList;
    ArrayList<String> pathArray = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_gallery,container,false);
        //img = (ImageView) v.findViewById(R.id.id_img);
        list = (ListView) v.findViewById(R.id.galleryListView);
        //new LoadImage().execute("http://www.iconshock.com/img_vista/ANDROID/business/jpg/service_electricity_icon.jpg");

        eventList = new ArrayList<HashMap<String,String>>();
        getData();
        //list.setAdapter(new ImageListAdapter(getContext(), eatFoodyImages));
        return v;
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            events = jsonObj.getJSONArray(TAG_RESULTS);
            //System.out.println("Inside try");
            for (int i = 0; i < events.length(); i++) {
                JSONObject c = events.getJSONObject(i);
                String photo = c.getString(TAG_PATH);
                String name = c.getString(TAG_NAME);
                String year = c.getString(TAG_YEAR);
                HashMap<String, String> event = new HashMap<String, String>();
                pathArray.add(photo);

                event.put(TAG_PATH, photo);
                event.put(TAG_NAME, name);
                event.put(TAG_YEAR, year);
                eventList.add(event);
            }
            String[] urlArray = new String[pathArray.size()];
            pathArray.toArray(urlArray);
            list.setAdapter(new ImageListAdapter(getContext(), urlArray));
/*            for (int i = 0; i < 3 ; i++) {  //pathArray.size()
                String img_url = pathArray.get(i);
                //String img_url = "http://battikgp.net23.net/gallery/12.jpg";
                new LoadImage().execute(img_url);
            }
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData() {

        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    private class GetDataJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                //System.out.println("Inside url1");
                URL url = new URL("http://battikgp.net23.net/photo.php");
                //System.out.println("Inside url2");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(true);

                //System.out.println("Inside url3");
                InputStream inputStream = httpURLConnection.getInputStream();
                //System.out.println("Inside url4");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //System.out.println("Inside url4b");
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    //System.out.println("Inside url5");
                    stringBuilder.append(JSON_STRING + "\n");
                }
                // System.out.println("Inside url6");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                //System.out.println("Inside url7");
                e.printStackTrace();
            } catch (IOException e) {
                //System.out.println("Inside url8");
                e.printStackTrace();
            }
            //System.out.println("Inside url9");
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            //TextView tv = (TextView) getView().findViewById(R.id.id_eventTextView);
            //tv.setText(result.toString());
            //System.out.println("after post execute");
            if(result != null){
                myJSON=result;
                showList();
                //d_pDialog.dismiss();

            }else{
                //d_pDialog.dismiss();
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

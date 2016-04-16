package com.example.ankitdeora2856.battikgp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
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

/**
 * Created by ankitdeora2856 on 17-03-2016.
 */
public class tabSchedule extends Fragment {

    String myJSON;
    String JSON_STRING;
    private static final String TAG_SID = "scheduleId";
    private static final String TAG_DATE = "date";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "eventName";
    JSONArray events = null;
    ArrayList<HashMap<String, String>> eventList;
    ListView list;
    ProgressDialog sDialog;
    ImageButton ib;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_schedule,container,false);
        list = (ListView) v.findViewById(R.id.scheduleListView);

        eventList = new ArrayList<HashMap<String,String>>();
        ib = (ImageButton) getActivity().findViewById(R.id.id_switch);
        //System.out.println("before data");
        getData();
        //System.out.println("Inside list clickA");
        return v;
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            events = jsonObj.getJSONArray(TAG_RESULTS);
            //System.out.println("Inside try");
            for(int i=0;i<events.length();i++){
                JSONObject c = events.getJSONObject(i);
                String sid = c.getString(TAG_SID);
                String name = c.getString(TAG_NAME);
                String venue = c.getString(TAG_VENUE);
                String date = c.getString(TAG_DATE);
                HashMap<String,String> events = new HashMap<String,String>();

                events.put(TAG_SID,sid);
                events.put(TAG_NAME, name);
                events.put(TAG_VENUE, venue);
                events.put(TAG_DATE, date);
                eventList.add(events);
                //LinearLayout parentLayout = (LinearLayout) getView().findViewById(R.id.scheduleListView).getParent();
                //View view = ((ViewGroup)parentLayout).getChildAt(i);
                //sw = (Switch) getView().findViewById(R.id.id_reminder);

            }
            //System.out.println("Inside try1");
            ListAdapter myAdapter = new ScheduleListAdapter(
                    getContext(), eventList, R.layout.schedule_list,
                    new String[]{TAG_NAME,TAG_DATE,TAG_VENUE},
                    new int[]{R.id.id_s_name,R.id.id_s_date,R.id.id_s_venue}
            );
            //System.out.println("Inside show list");

            list.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(){

        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    private class GetDataJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                //System.out.println("Inside url1");
                URL url = new URL("http://battikgp.net23.net/schedule.php");
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
                while ((JSON_STRING = bufferedReader.readLine())!=null)
                {
                    //System.out.println("Inside url5");
                    stringBuilder.append(JSON_STRING+"\n");
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
                //sDialog.dismiss();

            }else{
                //sDialog.dismiss();
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

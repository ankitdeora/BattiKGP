package com.example.ankitdeora2856.battikgp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ankitdeora2856 on 21-03-2016.
 */
public class ScheduleListAdapter extends SimpleAdapter {
    Context context;
    ArrayList<HashMap<String,String>> scheduleData;
    int resourceId;
    String[] tags;
    int[] tagResourcIds;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ScheduleListAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.scheduleData = data;
        this.resourceId = resource;
        this.tags = from;
        this.tagResourcIds = to;

    }
    @Override
    public View getView(final int position,View convertView, ViewGroup parent)
    {
        View row = convertView;
        Holder holder = null;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.schedule_list,parent,false);
            holder = new Holder();
            holder.E_name = (TextView) row.findViewById(R.id.id_s_name);
            holder.E_date = (TextView) row.findViewById(R.id.id_s_date);
            holder.E_venue = (TextView) row.findViewById(R.id.id_s_venue);
            holder.E_imgBtn = (ImageButton) row.findViewById(R.id.id_switch);
            row.setTag(holder);
        }
        else{
            holder = (Holder) row.getTag();
        }

        HashMap<String,String> hm = scheduleData.get(position);

        final String eventName = hm.get(tags[0]); // 0 for TAG_NAME
        final String date = hm.get(tags[1]);            // 1 for TAG_DATE
        final String venue = hm.get(tags[2]);     // 2 for TAG_VENUE

        holder.E_name.setText(eventName);
        holder.E_date.setText(date);
        holder.E_venue.setText(venue);

        holder.E_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDate myDate = new mDate(date);
                int f_year = myDate.getYear();
                int f_month = myDate.getMonth()-1;
                int f_day = myDate.getDay();

                System.out.println("f:"+f_year+"-"+f_month+"-"+f_day);

                Date currentDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                int c_year = cal.get(Calendar.YEAR);
                int c_month = cal.get(Calendar.MONTH);
                int c_day = cal.get(Calendar.DAY_OF_MONTH);
                System.out.println("c:"+c_year+"-"+c_month+"-"+c_day);

                boolean reminderValid = false;

                if(f_year>c_year) {
                    reminderValid = true;
                }
                else if(f_year==c_year) {
                    if(f_month>c_month){
                        reminderValid = true;
                    }
                    else if(f_month==c_month){
                        if(f_day>=c_day){
                            reminderValid = true;
                        }
                    }
                }

                if(reminderValid)
                {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    Calendar start = Calendar.getInstance();

                    myDate = new mDate(date);
                    start.set(f_year, f_month,f_day);

                    Calendar end = Calendar.getInstance();
                    end.set(f_year, f_month, f_day);

                    long startTime = start.getTimeInMillis();
                    long endTime = end.getTimeInMillis();
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                    intent.putExtra(CalendarContract.Events.TITLE, eventName);
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, venue);
                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY");
                    intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Event already finished...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return row;
    }

    static class Holder
    {
        TextView E_name;
        TextView E_date;
        TextView E_venue;
        ImageButton E_imgBtn;
    }
}

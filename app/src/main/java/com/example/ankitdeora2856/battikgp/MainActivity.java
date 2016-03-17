package com.example.ankitdeora2856.battikgp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Context context;
    Toolbar toolbar;
    ViewPager mpager;
    ViewPagerAdapter adapter;
    SlidingTabLayout mTabs;
    CharSequence Titles[]={"Events","Gallery","Schedule","Sponsors"};
    int NumTabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,NumTabs);

        mpager = (ViewPager) findViewById(R.id.pager);
        mpager.setAdapter(adapter);

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);

        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(context,R.color.tabsScrollColor);
            }
        });

        mTabs.setViewPager(mpager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.id_about_us) {
            Toast.makeText(MainActivity.this, "About us pressed", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.id_contact_us) {
            Toast.makeText(MainActivity.this, "Contact us pressed", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

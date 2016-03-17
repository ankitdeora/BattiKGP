package com.example.ankitdeora2856.battikgp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ankitdeora2856 on 17-03-2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumberOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[],int mNumOfTabs) {
        super(fm);
        this.Titles=mTitles;
        this.NumberOfTabs=mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            tabEvents m_tabEvents = new tabEvents();
            return m_tabEvents;
        }
        else if(position==1)
        {
            tabGallery m_tabGallery = new tabGallery();
            return m_tabGallery;
        }
        else if(position==2)
        {
            tabSchedule m_tabSchedule = new tabSchedule();
            return m_tabSchedule;
        }
        else
        {
            tabSponsors m_tabSponsors = new tabSponsors();
            return m_tabSponsors;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumberOfTabs;
    }
}

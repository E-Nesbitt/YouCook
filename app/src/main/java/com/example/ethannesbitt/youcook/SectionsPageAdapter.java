package com.example.ethannesbitt.youcook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//section page adapter class used to create tabs, containing the methods needed
//addFragment needed to add title to tab and the tab fragment
//getItem used to return the correct tab
public class SectionsPageAdapter extends FragmentPagerAdapter
{
    private final List<String> titleList = new ArrayList<>();
    private final List<Fragment> fragmentList = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    public void addFragment(String title, Fragment frag)
    {
        titleList.add(title);
        fragmentList.add(frag);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

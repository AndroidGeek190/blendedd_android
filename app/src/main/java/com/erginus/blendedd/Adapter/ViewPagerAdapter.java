package com.erginus.blendedd.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.erginus.blendedd.fragments.CategoriesFragment;
import com.erginus.blendedd.fragments.HomeFragment;
import com.erginus.blendedd.fragments.LoginFragment;

/**
 * Created by paramjeet on 1/9/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        if(position == 0) // if the position is 0 we are returning the First tab
        {
            HomeFragment homeFragment=new HomeFragment();
            return homeFragment;
        }
        if(position == 1) // if the position is 0 we are returning the First tab
        {
            CategoriesFragment categoriesFragment=new CategoriesFragment();
            return  categoriesFragment;
        }

       else// if the position is 0 we are returning the First tab
        {
            LoginFragment loginFragment=new LoginFragment();
            return  loginFragment;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
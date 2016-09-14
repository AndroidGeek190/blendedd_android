package com.erginus.blendedd.Commons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erginus.blendedd.R;
import com.erginus.blendedd.fragments.User_Change_Password;
import com.erginus.blendedd.fragments.Credit_Card;
import com.erginus.blendedd.fragments.User_Personal_Setting;

/**
 * Created by nazer on 11/3/2015.
 */
 public class User_Tabs extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
SharedPreference getdata;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.user_tabs,null);
        getdata=new SharedPreference(getActivity());
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);

        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Credit_Card();
                case 1 : return new User_Personal_Setting();
                case 2 : return new User_Change_Password();
            }
            return null;
        }

        @Override
        public int getCount() {
            if(getdata.getloginwith().equalsIgnoreCase("null")) {
                int_items=3;
                return int_items;
            }
else {
                int_items=2;
                return int_items;
            }
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    CharSequence credit="CREDIT CARD";
                    return credit;
                case 1 :
                    return "PERSONAL INFO";
                case 2 :
                    return "CHANGE PASSWORD";
            }
            return null;
        }
    }

}

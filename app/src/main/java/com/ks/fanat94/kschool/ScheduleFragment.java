package com.ks.fanat94.kschool;

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
import android.widget.Spinner;

import com.ks.fanat94.kschool.tabs_fragments.FridayFragment;
import com.ks.fanat94.kschool.tabs_fragments.MondayFragment;
import com.ks.fanat94.kschool.tabs_fragments.ThursdayFragment;
import com.ks.fanat94.kschool.tabs_fragments.TuesdayFragment;
import com.ks.fanat94.kschool.tabs_fragments.WednesdayFragment;

/**
 * Created by user on 27.01.2016.
 */
public class ScheduleFragment extends Fragment {

    public  TabLayout tabLayout;
    public  ViewPager viewPager;
    public static int int_items = 5;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x = inflater.inflate(R.layout.fragment_schedule, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        setHasOptionsMenu(true);

        viewPager.setAdapter(new MyAdapterr(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    class MyAdapterr extends FragmentPagerAdapter {

        public MyAdapterr(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MondayFragment();
                case 1:
                    return new TuesdayFragment();
                case 2:
                    return new WednesdayFragment();
                case 3:
                    return new ThursdayFragment();
                case 4:
                    return new FridayFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "ПН";
                case 1:
                    return "ВТ";
                case 2:
                    return "СР";
                case 3:
                    return "ЧТ";
                case 4:
                    return "ПТ";
            }
            return null;
        }
    }
}


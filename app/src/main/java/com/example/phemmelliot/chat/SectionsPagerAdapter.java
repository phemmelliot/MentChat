package com.example.phemmelliot.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



class SectionsPagerAdapter extends FragmentPagerAdapter{
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LandingFragment();
            case 1:
                return new TutorialsFragment();
            case 2:
                return new ChatsFragment();
            case 3:
                return new MentorFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "DASH";
            case 1:
                return "ARTICLES";
            case 2:
                return "CHATS";
            case 3:
                return "MENTORS";
            default:
                return null;
        }
    }
}

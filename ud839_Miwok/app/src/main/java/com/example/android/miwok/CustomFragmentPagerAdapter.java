package com.example.android.miwok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    public CustomFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    private String[] tabTitles = {"Number","Family","Colors","Phrase"};


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position ==0)
        {
            return new NumberFragment();
        }
        else if(position == 1)
        {
            return new FamilyFragment();
        }
        else if (position == 2)
        {
            return new ColorsFragment();
        }
        else
        {
            return new PhraseFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

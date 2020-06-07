package com.mycompany.mobile.android.traveljournalapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    public static final int NUM_PAGES = 2;


    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment ;
        switch (position){
            case 0 : fragment = new HomeFragment();
            break ;
            case 1 : fragment = new FavoriteListFragment();
                break ;
            default : throw new IllegalStateException("Error");
        }

        return fragment ;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}

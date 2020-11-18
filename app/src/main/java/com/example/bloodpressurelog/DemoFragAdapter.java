package com.example.bloodpressurelog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DemoFragAdapter extends FragmentStateAdapter {
    public DemoFragAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment frag;
                switch (position){
            case 0:
                frag = new ListFragment();
                break;
            case 1:
                frag = new GraphFrag();
                break;
            default:
                frag =new DemoFrag2();
                break;
        }
        return frag;
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}

package com.app.tabletopdiceroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    private static SettingsFragment fragmentInstance = null;

    public static SettingsFragment getFragment() {
        if (fragmentInstance == null) {
            fragmentInstance = new SettingsFragment();
        }
        return fragmentInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settingsl, container, false);
    }
}



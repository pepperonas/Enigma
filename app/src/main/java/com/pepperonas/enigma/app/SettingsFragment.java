package com.pepperonas.enigma.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance(int i) {
        SettingsFragment fragment = new SettingsFragment();

        Bundle args = new Bundle();
        args.putInt("the_id", i);
        fragment.setArguments(args);

        return fragment;
    }
}

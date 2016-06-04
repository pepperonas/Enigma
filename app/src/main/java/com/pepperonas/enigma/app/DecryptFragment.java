package com.pepperonas.enigma.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class DecryptFragment extends Fragment {

    private static final String TAG = "DecryptFragment";

    private MainActivity mMain;


    public static Fragment newInstance(int i) {
        DecryptFragment fragment = new DecryptFragment();

        Bundle args = new Bundle();
        args.putInt("the_id", i);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.fragment_decrypt, container, false);
        mMain = (MainActivity) getActivity();
        mMain.setTitle(getString(R.string.decrypt));
        setRetainInstance(true);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated()");

    }
}

package com.pepperonas.enigma.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class EncryptFragment extends Fragment {

    private static final String TAG = "EncryptFragment";

    private MainActivity mMain;


    public static Fragment newInstance(int i) {
        EncryptFragment fragment = new EncryptFragment();

        Bundle args = new Bundle();
        args.putInt("the_id", i);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.fragment_encrypt, null, false);
        mMain = (MainActivity) getActivity();
        mMain.setTitle(getString(R.string.encrypt));
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated()");

        EditText encrypt_et_iv = (EditText) getActivity().findViewById(R.id.encrypt_et_iv);
        encrypt_et_iv.setText(String.valueOf(System.currentTimeMillis()));
    }
    
}

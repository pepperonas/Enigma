package com.pepperonas.enigma.app;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pepperonas.andcommon.base.ClipboardUtils;
import com.pepperonas.andcommon.base.ToastUtils;
import com.pepperonas.javabase.security.CryptUtils;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView mNavView;
    private int mLastSelectedNavItemPos = 0;
    private Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initNavDrawer();

        initNavView();

        selectNavViewItem(mNavView.getMenu().getItem(0).getSubMenu().getItem(0));
    }


    private void initNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }


    private void initNavView() {
        mNavView = (NavigationView) findViewById(R.id.navigation_view);

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(menuItem.isChecked());
                mDrawerLayout.closeDrawers();

                // return if the selected fragment is shown
                return selectNavViewItem(menuItem);

            }
        });

    }


    public boolean selectNavViewItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.audio:
                if (mFragment instanceof EncryptFragment) return true;
                makeFragmentTransaction(EncryptFragment.newInstance(0));
                mLastSelectedNavItemPos = 0;
                return true;

            case R.id.dsp:
                if (mFragment instanceof DecryptFragment) return true;
                makeFragmentTransaction(DecryptFragment.newInstance(1));
                mLastSelectedNavItemPos = 1;
                return true;

            case R.id.settings:
                if (mFragment instanceof SettingsFragment) return true;
                makeFragmentTransaction(SettingsFragment.newInstance(5));
                mLastSelectedNavItemPos = 2;
                return true;

            case R.id.help_and_feedback:
                new DialogHelpAndFeedback(MainActivity.this);
                return true;

        }
        return false;
    }


    private void makeFragmentTransaction(Fragment fragment) {
        mFragment = fragment;
        android.support.v4.app.FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, mFragment);
        fragmentTransaction.commit();
    }


    public void onGetIv(View view) {
        EditText encrypt_et_iv = (EditText) findViewById(R.id.encrypt_et_iv);
        encrypt_et_iv.setText(String.valueOf(System.currentTimeMillis()));
    }


    public void onEncrypt(View view) {
        EditText encrypt_et_iv = (EditText) findViewById(R.id.encrypt_et_iv);
        EditText encrypt_et_msg = (EditText) findViewById(R.id.encrypt_et_msg);
        EditText encrypt_et_pwd = (EditText) findViewById(R.id.encrypt_et_pwd);

        if (encrypt_et_iv.getText() == null || encrypt_et_iv.getText().length() == 0
            || encrypt_et_msg.getText() == null || encrypt_et_msg.getText().length() == 0
            || encrypt_et_pwd.getText() == null || encrypt_et_pwd.getText().length() == 0) {
            ToastUtils.toastShort("Encryption failed.");
            return;
        }

        try {
            String encrypted = CryptUtils.aesEncrypt(
                    encrypt_et_pwd.getText().toString(),
                    encrypt_et_msg.getText().toString(),
                    Long.parseLong(encrypt_et_iv.getText().toString()));

            encrypted = "-----BEGIN AES256 MESSAGE-----\n(IV:" +
                        Long.parseLong(encrypt_et_iv.getText().toString()) + ")\n\n" +
                        encrypted +
                        "\n-----END AES256 MESSAGE-----";

            ClipboardUtils.setClipboard(encrypted);

            ToastUtils.toastShort("Encryption passed.");
            Log.d(TAG, "onEncrypt  " + encrypted);
        } catch (Exception e) {
            ToastUtils.toastShort("An error occured.");
            Log.e(TAG, "onDecrypt Error: " + e);
        }
    }


    public void onDecrypt(View view) {
        EditText decrypt_et_pwd = (EditText) findViewById(R.id.decrypt_et_pwd);
        EditText decrypt_et_msg = (EditText) findViewById(R.id.decrypt_et_msg);
        TextView decrypt_tv_msg = (TextView) findViewById(R.id.decrypt_tv_msg);

        if (decrypt_et_pwd.getText() == null || decrypt_et_pwd.getText().length() == 0
            || decrypt_et_msg.getText() == null || decrypt_et_msg.getText().length() == 0) {
            ToastUtils.toastShort("Decryption failed.");
            return;
        }

        try {
            String encrypted = decrypt_et_msg.getText().toString();
            encrypted = encrypted.replace("\n", "");
            encrypted = encrypted.replace("-----END AES256 MESSAGE-----", "");

            String iv = encrypted.split("\\(")[1].split("\\)")[0];
            iv = iv.replace("(", "");
            iv = iv.replace(")", "");
            iv = iv.replace("IV", "");
            iv = iv.replace(":", "");


            Log.d(TAG, "onDecrypt  " + "iv: " + iv);

            String content = encrypted.split("\\)")[1];

            Log.d(TAG, "onDecrypt  " + "content: " + content);

            decrypt_tv_msg.setText(
                    CryptUtils.aesDecrypt(
                            decrypt_et_pwd.getText().toString(),
                            content,
                            Long.parseLong(iv)));
        } catch (Exception e) {
            ToastUtils.toastShort("An error occured.");
            Log.e(TAG, "onDecrypt Error: " + e);
        }
    }
}

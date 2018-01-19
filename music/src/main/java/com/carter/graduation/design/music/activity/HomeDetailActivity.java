package com.carter.graduation.design.music.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.fragment.MusicDynamicFragment;
import com.carter.graduation.design.music.fragment.MusicFragment;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MusicFragment.OnFragmentInteractionListener, MusicDynamicFragment.OnFragmentInteractionListener {

    private static final String TAG = "HomeDetailActivity";
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private ImageView barnet, barmusic;
    private NotificationManager mNm;
    private ShareActionProvider mShareActionProvider;
    private CustomViewPager mMainViewPager;
    private ArrayList<MusicInfo> mMusicInfos;
    private long time = 0;
    private MusicFragment mMusicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        barnet = (ImageView) findViewById(R.id.bar_net);
        barmusic = (ImageView) findViewById(R.id.bar_music);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setViewPager();
        barmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainViewPager.setCurrentItem(1);
            }
        });
        barnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainViewPager.setCurrentItem(0);
            }
        });


    }

    private void setViewPager() {
        tabs.add(barnet);
        tabs.add(barmusic);
        mMainViewPager = findViewById(R.id.main_viewpager);
        //MusicFragment musicFragment = new MusicFragment();
        MusicDynamicFragment musicDynamicFragment = new MusicDynamicFragment();
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(musicDynamicFragment);
        onFragmentInteraction();
        mMusicFragment = new MusicFragment();
        pagerAdapter.addFragment(mMusicFragment);

//        添加music页面 和  摇动界面
        mMainViewPager.setAdapter(pagerAdapter);
        mMainViewPager.setCurrentItem(1);
        barmusic.setSelected(true);
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "abc");
        sendIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(sendIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about_app) {
            Intent intent = new Intent(this, AboutAppActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_exit) {
            finish();
        }/* else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);/*
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //mMusicInfos = scanAllMusicFiles();
                } else {
                    showDialog();
                    //Toast.makeText(this, "本app需要此权限否则无法使用", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
*/
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }

    }


    @Override
    public void onFragmentInteraction() {
//        mMusicFragment = MusicFragment.newInstance(mMusicInfos, "音乐");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time) > 1000) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setNotification();
    }

    private void setNotification() {
        mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, HomeDetailActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("music running")
                .setContentText("别摸我 摸我咬你 (￢_￢)智商三岁")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setSmallIcon(R.drawable.small_icon)
                .setContentIntent(pi)
                .setOngoing(true)
                .build();

        if (mNm != null) {
            mNm.notify(1, notification);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNm.cancel(1);
    }


    static class MainViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mFragments = new ArrayList<>();

        MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}

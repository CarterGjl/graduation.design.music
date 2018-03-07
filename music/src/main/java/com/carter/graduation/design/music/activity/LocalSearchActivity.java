package com.carter.graduation.design.music.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.adapter.SearchAdapter;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.utils.SearchUtils;
import com.carter.graduation.design.music.utils.UiUtil;


import java.util.ArrayList;


public class LocalSearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private SearchAdapter mSearchAdapter;

    private static final String TAG = "LocalSearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_search);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("查找音乐");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView re = findViewById(R.id.recyclerview);
        re.setLayoutManager(new LinearLayoutManager(UiUtil.getContext()));
        mSearchAdapter = new SearchAdapter();
        re.setAdapter(mSearchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = ((SearchView) searchItem.getActionView());
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("输入歌曲名字查找");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: ");
                onQueryTextChange(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: ");

                ArrayList<MusicInfo> musicInfos = SearchUtils.queryMusic(UiUtil.getContext(), newText);

                for (int i = 0; i < musicInfos.size(); i++) {
                    Log.d(TAG, "onQueryTextChange: "+musicInfos.get(i).getTitle());
                }
                mSearchAdapter.updateSearchResults(musicInfos);
                mSearchAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}

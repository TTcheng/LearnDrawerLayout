package com.example.wangchuncheng.learndrawerlayout;

import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ArrayList<String> menuList;
    private ArrayAdapter<String> adapter;
    private ActionBarDrawerToggle drawerToggle;
    private String title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (String) getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        menuList = new ArrayList<>();
        for (int i = 0; i<4 ; i++){
            menuList.add("列表项"+i);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuList);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(this);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();//call onPrepareOptionsMenu
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("请选择");
                invalidateOptionsMenu();
//                getActionBar().setSubtitle("请选择");

            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //将Acton Bar 上的图标与Drawer结合起来
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri baidu = Uri.parse("http://www.baidu.com");
                intent.setData(baidu);
                startActivity(intent);
                break;//click search goto baidu.com
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //需要将ActionDrawerToggle与ActionLayout同步
        //将ActionDrawerToggle中的drawer图标设置为ActionBar的Home-Button的icon
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);//inflate menu layout xml
//        return super.onCreateOptionsMenu(menu);1
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("text",menuList.get(position));
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.content_frame,contentFragment)
                .commit();
        drawerLayout.closeDrawer(drawerList);
        //开启ActionBar app icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}

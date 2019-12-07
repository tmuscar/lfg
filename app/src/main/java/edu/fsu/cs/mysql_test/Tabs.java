package edu.fsu.cs.mysql_test;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;

public class Tabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter; // from FragmentAdpt class

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.AddFragment(new GameList(), "");
        adapter.AddFragment(new FriendList(),"");
        adapter.AddFragment(new EventsList(),"");
        adapter.AddFragment(new Setting(),"");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_videogame);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_friends);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_event);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_settings);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setElevation(0);
    }

}
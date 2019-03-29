package blockchainvideoapp.com.goviddo.goviddo.activity;


import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import blockchainvideoapp.com.goviddo.goviddo.Fragments.HomeFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.OthersFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.RecentFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.SubscriptionFragment;
import blockchainvideoapp.com.goviddo.goviddo.R;

public class HomeActivity extends AppCompatActivity  {

    Toolbar toolbar;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    transaction.replace( R.id.fragment_container,homeFragment );
                    transaction.commit();
                    return true;
                case R.id.navigation_subscription:

                    SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
                    transaction.replace( R.id.fragment_container,subscriptionFragment );
                    transaction.commit();
                    return true;
                case R.id.navigation_recent:
                    HomeFragment homeFragment1 = new HomeFragment();
                    transaction.replace( R.id.fragment_container,homeFragment1 );
                    transaction.commit();
                    return true;
                case R.id.navigation_more:
                    RecentFragment recentFragment = new RecentFragment();
                    transaction.replace( R.id.fragment_container,recentFragment );
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
        frameLayout = findViewById( R.id.fragment_container );
        bottomNavigationView = findViewById( R.id.navigation );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );


        HomeFragment homeFragment = new HomeFragment();
        transaction.add( R.id.fragment_container,homeFragment );
        transaction.commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
           Toast.makeText(HomeActivity.this, "Search", android.widget.Toast.LENGTH_LONG).show();
            return true;
        }

        else if (id == R.id.c) {
         Toast.makeText(HomeActivity.this, "Add Movie", android.widget.Toast.LENGTH_LONG).show();
                    return true;
        }

        else if(id == R.id.action_notification1) {
          Toast.makeText(HomeActivity.this, "Notification", android.widget.Toast.LENGTH_LONG).show();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}









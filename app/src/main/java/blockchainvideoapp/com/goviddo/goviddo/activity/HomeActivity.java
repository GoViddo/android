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
import android.view.MenuItem;
import android.widget.FrameLayout;


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
                    RecentFragment recentFragment = new RecentFragment();
                    transaction.replace( R.id.fragment_container,recentFragment );
                    transaction.commit();
                    return true;
                case R.id.navigation_more:
                    OthersFragment othersFragment = new OthersFragment();
                    transaction.replace( R.id.fragment_container,othersFragment );
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
        frameLayout = findViewById( R.id.fragment_container );
        bottomNavigationView = findViewById( R.id.navigation );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );


        HomeFragment homeFragment = new HomeFragment();
        transaction.add( R.id.fragment_container,homeFragment );
        transaction.commit();


    }
}





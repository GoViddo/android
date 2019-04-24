package blockchainvideoapp.com.goviddo.goviddo.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.BuildConfig;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.HomeFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.OthersFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.RecentFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.SubscriptionFragment;
import blockchainvideoapp.com.goviddo.goviddo.R;

public class HomeActivity extends AppCompatActivity  {

    Toolbar toolbar;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;



    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    transaction.replace( R.id.fragment_container, homeFragment );
                    transaction.commit();
                    return true;
                case R.id.navigation_subscription:

                    SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
                     transaction.replace( R.id.fragment_container, subscriptionFragment );
                    transaction.commit();
                    return true;
                case R.id.navigation_recent:
                    HomeFragment homeFragment1 = new HomeFragment();
                    transaction.replace( R.id.fragment_container, homeFragment1 );
                    transaction.commit();
                    return true;
                case R.id.navigation_more:
                    RecentFragment recentFragment = new RecentFragment();
                    transaction.replace( R.id.fragment_container, recentFragment );
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

        String update_url = "http://178.128.173.51:3000/updateApp";

        RequestQueue requestQueue = Volley.newRequestQueue(this );


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, update_url,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    int version_code_current = response.getInt("version_code_current");
                    int version_code_min = response.getInt("version_code_min");
                    String update_info = response.getString("update_info");
                    String update_date = response.getString("update_date");

                    if(BuildConfig.VERSION_CODE <version_code_current){

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                        builder1.setMessage(update_info);
                        builder1.setPositiveButton(
                                "Update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setTextColor( Color.parseColor("#FF0000"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put( "Content-Type", "application/json" );
                return headers;
            }

        };

        requestQueue.add( jsonObjectRequest );




        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        frameLayout = findViewById( R.id.fragment_container );
        bottomNavigationView = findViewById( R.id.navigation );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );


        HomeFragment homeFragment = new HomeFragment();
        transaction.add( R.id.fragment_container,homeFragment );
        transaction.commit();


    }



    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//           Toast.makeText(HomeActivity.this, "Search", android.widget.Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        else
    if (id == R.id.c) {
            Toast.makeText(HomeActivity.this, "This feature added soon...", android.widget.Toast.LENGTH_LONG).show();
                    return true;
        }

        else if(id == R.id.action_notification1) {
          //Toast.makeText(HomeActivity.this, "", android.widget.Toast.LENGTH_LONG).show();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


}









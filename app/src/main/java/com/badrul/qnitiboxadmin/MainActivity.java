package com.badrul.qnitiboxadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    boolean openF2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openF2"))
            openF2 = extras.getBoolean("openF2");
        if(openF2){
            CompleteFragment fragment = new CompleteFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new ScannerFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_scanner:
                fragment = new ScannerFragment();
                break;

            case R.id.navigation_processing:
                fragment = new ProcessingFragment();
                break;

            case R.id.navigation_complete:
                fragment = new CompleteFragment();
                break;

            case R.id.navigation_feedback:
                fragment = new FeedbackFragment();
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

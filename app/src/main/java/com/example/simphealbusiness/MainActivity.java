package com.example.simphealbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.simphealbusiness.fragments.Cartfragment;
import com.example.simphealbusiness.fragments.Homefragment;
import com.example.simphealbusiness.fragments.Ordersfragment;
import com.example.simphealbusiness.fragments.Profilefragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newFragment(new Homefragment());


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);

    }

    private boolean newFragment(Fragment fragment) {

        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.HomeActivity, fragment).commit();
            return true;

        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.app_home:
                fragment = new Homefragment();
                break;
            case R.id.cart:
                fragment = new Cartfragment();
                break;
            case R.id.profile:
                fragment = new Profilefragment();
                break;
            case R.id.order:
                fragment = new Ordersfragment();
                break;
        }
        return newFragment(fragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
package com.example.myapprute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_start);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_start) {
                // butonul selectat - aici esti, nu facem nimic
            } else if (itemId == R.id.bottom_route) {
                startActivity(new Intent(getApplicationContext(), RoutesActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.bottom_navigation) {
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.bottom_notifications) {
                startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            return true;
        });
    }
}
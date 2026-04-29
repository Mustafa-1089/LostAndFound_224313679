package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public void jumpClickCreateAdvert(View view) {
        Intent intent = new Intent(this, CreateAdvert.class);
        startActivity(intent);
    }

    public void jumpClickLostFound(View view) {
        Intent intent = new Intent(this, LostFoundActivity.class);
        startActivity(intent);
    }

    Button newAdvertButton;
    Button lostFoundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        newAdvertButton = findViewById(R.id.newAdvertButton);
        lostFoundButton = findViewById(R.id.lostFoundButton);
    }
}
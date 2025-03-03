package com.example.mongodb1nd;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    static FragmentManager fm;
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

        fm = getSupportFragmentManager();
    }

    public static void showFragmentWithSomething(String string){
        FragmentTransaction ft = fm.beginTransaction();
        FragmentWithSomething textStatisticsFragment = new FragmentWithSomething();
        Bundle bundle = new Bundle();
        String rez = string;
        bundle.putString("key", rez);
        textStatisticsFragment.setArguments(bundle);
        ft.replace(R.id.frameLayout, textStatisticsFragment, null);
        ft.commit();
    }
}
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

    public static void showFragmentWithSomething(SomeItem someItem) {
        FragmentTransaction ft = fm.beginTransaction();
        FragmentWithSomething fragmentWithSomething = new FragmentWithSomething();
        Bundle args = new Bundle();
        args.putString("_id", someItem.getId());
        args.putString("name", someItem.getName());
        args.putString("something", someItem.getSomething());
        fragmentWithSomething.setArguments(args);

        ft.replace(R.id.frameLayout, fragmentWithSomething, null);
        ft.commit();
    }

    public static void showFragmentNewDocument() {
        FragmentTransaction ft = fm.beginTransaction();
        FragmentNewDocument fragmentNewDocument = new FragmentNewDocument();
        ft.replace(R.id.frameLayout, fragmentNewDocument);
        ft.commit();
    }
}
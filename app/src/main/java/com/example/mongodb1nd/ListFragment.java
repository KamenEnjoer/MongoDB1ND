package com.example.mongodb1nd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ListView listView;
    Button addButton;
    List<SomeItem> itemList;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listView);
        addButton = view.findViewById(R.id.add_doc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mongodb1nd.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getItems().enqueue(new Callback<List<SomeItem>>() {
            @Override
            public void onResponse(Call<List<SomeItem>> call, Response<List<SomeItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemList = response.body();

                    // Создаём список только с именами для отображения в ListView
                    List<String> names = new ArrayList<>();
                    for (SomeItem item : itemList) {
                        names.add(item.getName());
                    }

                    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(adapter);
                } else {
                    Log.e("API Response", "Error: " + response.code() + " " + response.message());
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SomeItem>> call, Throwable t) {
                Log.e("API Failure", "Error: " + t.getMessage());
                Toast.makeText(getActivity(), "Data loading error!", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SomeItem selectedItem = itemList.get(position);
                MainActivity.showFragmentWithSomething(selectedItem);
            }
        });

        addButton.setOnClickListener(v -> {
            MainActivity.showFragmentNewDocument();
        });

        return view;
    }
}
package com.example.mongodb1nd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWithSomething#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWithSomething extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentWithSomething() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWithSomething.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWithSomething newInstance(String param1, String param2) {
        FragmentWithSomething fragment = new FragmentWithSomething();
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

    EditText editText;
    Button deleteButton;
    Button updateButton;
    String itemId;
    String somethingText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_something, container, false);
        editText = view.findViewById(R.id.textViewFragmentWithSomething);
        deleteButton = view.findViewById(R.id.delete_doc);
        updateButton = view.findViewById(R.id.update_doc_something);

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemId = bundle.getString("_id", "");
            somethingText = bundle.getString("something", "No data");
            editText.setText(somethingText);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mongodb1nd.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        deleteButton.setOnClickListener(v -> {
            apiService.deleteItem(itemId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack(); // Закрываем фрагмент
                    } else {
                        Toast.makeText(getActivity(), "Delete failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), "Server error!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        updateButton.setOnClickListener(v -> {
            HashMap<String, String> body = new HashMap<>();
            body.put("something", editText.getText().toString());

            apiService.updateItem(itemId, body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Update failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), "Server error!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
package com.zeynelinho.myjavatry3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeynelinho.myjavatry3.R;
import com.zeynelinho.myjavatry3.adapter.CryptoAdapter;
import com.zeynelinho.myjavatry3.databinding.ActivityMainBinding;
import com.zeynelinho.myjavatry3.model.CryptoModel;
import com.zeynelinho.myjavatry3.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<CryptoModel> cryptoModelArrayList;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    CryptoAdapter cryptoAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);





        //Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        loadData();


    }


    private void loadData() {

        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        /*Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {

                    List<CryptoModel> responseList = response.body();

                    cryptoModelArrayList = new ArrayList<>(responseList);

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    cryptoAdapter = new CryptoAdapter(cryptoModelArrayList);
                    binding.recyclerView.setAdapter(cryptoAdapter);



                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

         */


    }

    public void handleResponse(List<CryptoModel> cryptoModelList) {

        cryptoModelArrayList = new ArrayList<>(cryptoModelList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        cryptoAdapter = new CryptoAdapter(cryptoModelArrayList);
        binding.recyclerView.setAdapter(cryptoAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
package com.example.myapplication.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProductAdapter;
import com.example.myapplication.databinding.FragmentMixMoodBinding;
import com.example.myapplication.databinding.FragmentWeatherBinding;
import com.example.myapplication.interfaces.RecyclerViewInterface;
import com.example.myapplication.models.ProductModel;
import com.example.myapplication.ui.moods.MixMoodFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class WeatherFragment extends Fragment implements RecyclerViewInterface {

    private FragmentWeatherBinding binding;
    List<ProductModel> productModelList, tempProductModelList;
    RecyclerView rvWeather;
    ProductAdapter productAdapter;
    String weather;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        if (bundle != null){
            productModelList = new ArrayList<>();
            tempProductModelList = new ArrayList<>();
            productModelList = (List<ProductModel>) getArguments().getSerializable("productList");
            weather = bundle.getString("weather");
            for(int i = 0 ; i < productModelList.size() ; i++){
                if(productModelList.get(i).getWeather().toLowerCase().compareTo(weather.toLowerCase()) == 0 ){
                    tempProductModelList.add(productModelList.get(i));
                }
            }
        }
        Collections.shuffle(tempProductModelList);
        rvWeather = root.findViewById(R.id.rv_weather);
        productAdapter = new ProductAdapter(getActivity(),tempProductModelList, WeatherFragment.this);
        rvWeather.setAdapter(productAdapter);
        rvWeather.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvWeather.setHasFixedSize(true);
        rvWeather.setNestedScrollingEnabled(false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClickForYou(int position) {

    }

    @Override
    public void onItemClickStorePopular(int position) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClickStoreRec(int position) {

    }

    @Override
    public void onItemClickStoreRec2(int position) {

    }

    @Override
    public void onItemClickSearch(int position) {

    }

    @Override
    public void onItemClickCategory(int pos) {

    }
}
package com.zeynelinho.myjavatry3.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeynelinho.myjavatry3.R;
import com.zeynelinho.myjavatry3.databinding.RecyclerRowBinding;
import com.zeynelinho.myjavatry3.model.CryptoModel;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoHolder> {

    private ArrayList<CryptoModel> cryptoModelArrayList;
    private String [] colors = {"#F93B12","#F9CF12","#86F912","#1294F9","#12F9B3","#8D12F9","#F91274","#12F2F9"};

    public CryptoAdapter(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;
    }

    @NonNull
    @Override
    public CryptoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CryptoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoHolder holder, int position) {

        holder.bind(cryptoModelArrayList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoModelArrayList.size();
    }

    public class CryptoHolder extends RecyclerView.ViewHolder {
        TextView recyclerName;
        TextView recyclerPrice;
        RecyclerRowBinding binding;

        public CryptoHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
        }

        public void bind(CryptoModel cryptoModel,String[] colors,Integer position) {

            recyclerName = itemView.findViewById(R.id.recyclerName);
            recyclerPrice = itemView.findViewById(R.id.recyclerPrice);
            recyclerName.setText(cryptoModel.currency);
            recyclerPrice.setText(cryptoModel.price);
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
        }

    }

}

package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.HomeFoodForYouModel;

import org.w3c.dom.Text;

import java.util.List;

public class HomeFoodForYouAdapter extends RecyclerView.Adapter<HomeFoodForYouAdapter.ViewHolder> {
    Context context;
    List<HomeFoodForYouModel> list;

    public HomeFoodForYouAdapter(Context context, List<HomeFoodForYouModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeFoodForYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_food_for_you_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFoodForYouAdapter.ViewHolder holder, int position) {
        holder.iv_food_for_you.setImageResource(list.get(position).getImage());
        holder.tv_fff_prod_name.setText(list.get(position).getProduct_name());
        holder.tv_fff_store_name.setText(list.get(position).getStore_name());
        holder.tv_fff_prod_price.setText("P " + list.get(position).getProduct_price().toString());
        holder.tv_fff_prod_cal.setText(list.get(position).getProduct_calories() + "cal");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_food_for_you;
        TextView tv_fff_prod_name;
        TextView tv_fff_store_name;
        TextView tv_fff_prod_price;
        TextView tv_fff_prod_cal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_food_for_you = itemView.findViewById(R.id.iv_food_for_you);
            tv_fff_prod_name = itemView.findViewById(R.id.tv_fff_prod_name);
            tv_fff_store_name = itemView.findViewById(R.id.tv_fff_store_name);
            tv_fff_prod_price = itemView.findViewById(R.id.tv_fff_prod_price);
            tv_fff_prod_cal = itemView.findViewById(R.id.tv_fff_prod_cal);
        }
    }
}
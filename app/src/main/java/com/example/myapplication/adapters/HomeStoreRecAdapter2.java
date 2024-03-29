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
import com.example.myapplication.interfaces.RecyclerViewInterface;
import com.example.myapplication.models.StoreModel;

import java.util.List;

public class HomeStoreRecAdapter2 extends RecyclerView.Adapter<HomeStoreRecAdapter2.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<StoreModel> list;

    public HomeStoreRecAdapter2(Context context, List<StoreModel> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public HomeStoreRecAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeStoreRecAdapter2.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_store_rec_item,parent,false),recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeStoreRecAdapter2.ViewHolder holder, int position) {
        holder.iv_store_image.setImageBitmap(list.get(position).getBitmapImage());
        holder.tv_store_name.setText(list.get(position).getStore_name());
        holder.tv_store_location.setText(list.get(position).getStore_location());
        holder.tv_store_category.setText(list.get(position).getStore_category());
        holder.tv_store_rating.setText(list.get(position).getStore_rating().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_store_image;
        TextView tv_store_name;
        TextView tv_store_location;
        TextView tv_store_category;
        TextView tv_store_rating;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            iv_store_image = itemView.findViewById(R.id.iv_food_image);
            tv_store_name = itemView.findViewById(R.id.tv_food_name);
            tv_store_location = itemView.findViewById(R.id.tv_food_price);
            tv_store_category = itemView.findViewById(R.id.tv_store_category);
            tv_store_rating = itemView.findViewById(R.id.tv_store_rating);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClickStoreRec2(pos);
                        }
                    }
                }
            });
        }
    }
}

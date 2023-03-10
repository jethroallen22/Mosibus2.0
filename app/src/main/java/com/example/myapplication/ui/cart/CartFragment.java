package com.example.myapplication.ui.cart;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.R;
import com.example.myapplication.adapters.CartAdapter;
import com.example.myapplication.adapters.HomeStorePopularAdapter;
import com.example.myapplication.adapters.SearchAdapter;
import com.example.myapplication.databinding.FragmentCartBinding;
import com.example.myapplication.interfaces.RecyclerViewInterface;
import com.example.myapplication.interfaces.Singleton;
import com.example.myapplication.models.IPModel;
import com.example.myapplication.models.OrderItemModel;
import com.example.myapplication.models.OrderModel;
import com.example.myapplication.models.StoreModel;
import com.example.myapplication.ui.order.OrderFragment;
import com.example.myapplication.ui.search.SearchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CartFragment extends Fragment implements RecyclerViewInterface {

    private FragmentCartBinding binding;
    //Cart List Recycler View
    RecyclerView rv_cart;
    CartAdapter cartAdapter;
    Button btn_remove;
    CheckBox cb_cart_item;
    CheckBox checkBox;
    List<OrderModel> order_list;
    List<StoreModel> temp_store_list;
    List<OrderItemModel> order_item_list;
    RequestQueue requestQueueCart, requestQueueStore;
    int userID = 0;

    //School IP
    private static String JSON_URL;
    private IPModel ipModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ipModel = new IPModel();
        JSON_URL = ipModel.getURL();

        Bundle bundle = getArguments();
        if(bundle != null) {
            userID = bundle.getInt("userID");
            Log.d("USERID", String.valueOf(userID));
        }

        rv_cart = root.findViewById(R.id.rv_cart);
        order_list = new ArrayList<>();
        order_item_list = new ArrayList<>();
        temp_store_list = new ArrayList<>();
        requestQueueCart = Singleton.getsInstance(getActivity()).getRequestQueue();
        requestQueueStore = Singleton.getsInstance(getActivity()).getRequestQueue();
        extractStoreCartItem();



        btn_remove = root.findViewById(R.id.btn_remove);
        cb_cart_item = root.findViewById(R.id.cb_cart_item);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){

                }
            }
        });



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
    public void onItemClickStoreRec(int position) {

    }

    @Override
    public void onItemClickStoreRec2(int position) {

    }

    @Override
    public void onItemClickCategory(int position) {

    }

    @Override
    public void onItemClickSearch(int pos) {

    }



    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        float tempTotal = 0;
        for(int i = 0 ; i < order_list.get(position).getOrderItem_list().size() ; i++){
            tempTotal+= order_list.get(position).getOrderItem_list().get(i).getTotalPrice();
        }
        order_list.get(position).setOrderItemTotalPrice(tempTotal);
        bundle.putParcelable("order", order_list.get(position));

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_home,fragment).commit();
    }

    public void extractStoreCartItem(){

        List<StoreModel> tempStoreList;
        tempStoreList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest4 = new JsonArrayRequest(Request.Method.GET, JSON_URL+"apipopu.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i < response.length(); i++){
                    try {
                        JSONObject jsonObjectPop = response.getJSONObject(i);
                        int r_id = jsonObjectPop.getInt("idStore");
                        String r_image = jsonObjectPop.getString("storeImage");
                        String r_name = jsonObjectPop.getString("storeName");
                        String r_description = jsonObjectPop.getString("storeDescription");
                        String r_location = jsonObjectPop.getString("storeLocation");
                        String r_category = jsonObjectPop.getString("storeCategory");
                        float r_rating = (float) jsonObjectPop.getDouble("storeRating");
                        int r_popularity = jsonObjectPop.getInt("storePopularity");
                        String r_open = jsonObjectPop.getString("storeStartTime");
                        String r_close = jsonObjectPop.getString("storeEndTime");

                        StoreModel store3 = new StoreModel(r_id,r_image,r_name,r_description,r_location,r_category,
                                (float) r_rating, r_popularity, r_open, r_close);
                        temp_store_list.add(store3);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueueStore.add(jsonArrayRequest4);

        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, JSON_URL+"apicart.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 for (int i=0; i < response.length(); i++){
                    try {
                        int temp_not = 0;
                        JSONObject jsonObjectCart = response.getJSONObject(i);
                        if (jsonObjectCart.getInt("temp_usersId") == userID) {
                            int c_productId = jsonObjectCart.getInt("temp_productId");
                            int c_storeId = jsonObjectCart.getInt("temp_storeId");
                            int c_usersId = jsonObjectCart.getInt("temp_usersId");
                            String c_productName = jsonObjectCart.getString("temp_productName");
                            double c_productPrice = jsonObjectCart.getDouble("temp_productPrice");
                            int c_productQuantity = jsonObjectCart.getInt("temp_productQuantity");
                            double c_totalProductPrice = jsonObjectCart.getDouble("temp_totalProductPrice");
                            String c_storeName = jsonObjectCart.getString("storeName");
                            String c_storeImage = jsonObjectCart.getString("storeImage");

                            OrderItemModel orderItemModel = new OrderItemModel(c_productId, c_storeId, userID, c_productName, (float) c_productPrice, c_productQuantity,
                                    (float) (c_productPrice * c_productQuantity));

                            Log.d("BEFORE", String.valueOf(userID));
                            Log.d("BEFOREDB", String.valueOf(c_usersId));
                            if(order_list.isEmpty()){
                                Log.d("STOREMATCH", c_productName + " Empty " + c_storeName);
                                order_item_list = new ArrayList<>();
                                order_item_list.add(orderItemModel);
                                order_list.add(new OrderModel((float) c_totalProductPrice,"pending",c_storeId,
                                        c_storeImage,c_storeName,
                                        c_usersId, order_item_list));
                            }else{
                                for (int h = 0; h < order_list.size(); h++) {
                                    Log.d("Inside for", String.valueOf(order_list.size()));
                                    //Check if Order already exist in CartList
                                    if (order_list.get(h).getStore_name().toLowerCase().trim().compareTo(c_storeName.toLowerCase().trim()) == 0) {
                                        Log.d("STOREMATCH", c_productName + " MATCH " + c_storeName);
                                        // Check if order item already exist
                                        for (int k = 0 ; k < order_list.get(h).getOrderItem_list().size() ; k++){
                                            if(c_productName.toLowerCase().trim().compareTo(order_list.get(h).getOrderItem_list().get(k).getProductName().toLowerCase().trim()) == 0){
//                                                temp_count = c_productQuantity;
                                                int tempItemQuantity = 0;
                                                tempItemQuantity = order_list.get(h).getOrderItem_list().get(k).getItemQuantity();
                                                Log.d("tempItemQuantityBefore", String.valueOf(tempItemQuantity));
                                                tempItemQuantity += c_productQuantity;
                                                Log.d("tempItemQuantityAfter", String.valueOf(tempItemQuantity));
                                                order_list.get(h).getOrderItem_list().get(k).setItemQuantity(tempItemQuantity);
                                                tempItemQuantity = 0;
                                                break;
                                            } else{
                                                order_list.get(h).getOrderItem_list().add(orderItemModel);
                                                break;
                                            }
                                        }
                                    } else{
                                        if(temp_not == order_list.size()) {
                                            float totalTotal = 0;
                                            order_item_list = new ArrayList<>();
                                            order_item_list.add(orderItemModel);
                                            for (int y = 0 ; y < order_item_list.size() ; y++)
                                                totalTotal+=order_item_list.get(y).getTotalPrice();
                                            order_list.add(new OrderModel((float) totalTotal, "pending", c_storeId,
                                                    c_storeImage, c_storeName,
                                                    c_usersId, order_item_list));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        Log.d("MINE OL SIZE", String.valueOf(order_list.size()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                cartAdapter = new CartAdapter(getActivity(),order_list, CartFragment.this);
                rv_cart.setAdapter(cartAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rv_cart.setLayoutManager(layoutManager);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueueCart.add(jsonArrayRequest1);
        Log.d("OUTSIDE LIST", String.valueOf(order_list.size()));

    }
}
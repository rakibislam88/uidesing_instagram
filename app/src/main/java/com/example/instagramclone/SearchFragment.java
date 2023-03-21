package com.example.instagramclone;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFragment extends Fragment {

    public ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    RecyclerView recyclerView;
    MyAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);



        String url = "https://raquib.000webhostapp.com/apps/image.json";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String image = jsonObject.getString("image");
                        String imagetitle = jsonObject.getString("imagename");
                        hashMap = new HashMap<>();
                        hashMap.put("img", image);
                        hashMap.put("imgtitle", imagetitle);
                        arrayList.add(hashMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new MyAdapter();
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(arrayRequest);

        return v;
    }

   public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyView>{
       @NonNull
       @Override
       public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           LayoutInflater layoutInflater = getLayoutInflater();
           View view = layoutInflater.inflate(R.layout.imglayout, parent, false);
           return new MyView(view);
       }

       @Override
       public void onBindViewHolder(@NonNull MyView holder, int position) {

           HashMap<String, String> map = arrayList.get(position);
           Picasso.get().load(map.get("img")).into(holder.imageView);

       }

       @Override
       public int getItemCount() {
           return arrayList.size();
       }

       public class MyView extends RecyclerView.ViewHolder{
           ImageView imageView;
            public MyView(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.img);

            }
        }
   }

}
package com.example.instagramclone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    public ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
    public ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    MyStoryAdapter storyAdapter;
    MyAdapter adapter;
    RecyclerView recyclerView, recyclerViewStory;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);






        drawerLayout = v.findViewById(R.id.drawer);
        toolbar = v.findViewById(R.id.toolbar);
        navigationView = v.findViewById(R.id.navigation_view);
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerViewStory = v.findViewById(R.id.recycler_view_story);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity) getContext(), drawerLayout, toolbar, R.string.drawer_close, R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home){
                    Toast.makeText(getContext(), "home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

        // story
        String url1 = "https://raquib.000webhostapp.com/apps/insArr.json";
        JsonArrayRequest arrayRequest1 = new JsonArrayRequest(Request.Method.GET, url1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("username");
                        String image = jsonObject.getString("image");
                        hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("image", image);
                        arrayList1.add(hashMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    storyAdapter = new MyStoryAdapter();
                    recyclerViewStory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
                    recyclerViewStory.setAdapter(storyAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        queue1.add(arrayRequest1);

        String url = "https://raquib.000webhostapp.com/apps/insArr.json";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("username");
                        String image = jsonObject.getString("image");
                        String like = jsonObject.getString("like");
                        String text = jsonObject.getString("text");
                        String comment = jsonObject.getString("comment");
                        hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("image", image);
                        hashMap.put("like", like);
                        hashMap.put("text", text);
                        hashMap.put("comment", comment);
                        arrayList.add(hashMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new MyAdapter();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(arrayRequest);



        return  v;
    }



    public class MyStoryAdapter extends RecyclerView.Adapter<MyStoryAdapter.myView>{

        @Override
        public int getItemViewType(int position) {
            if (position==9) return 9;
            else return 1;
        }

        @NonNull
        @Override
        public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType==9){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.layoutyou , parent , false);
                return new MyStoryAdapter.myView(view);
            }
                View view = LayoutInflater.from(getContext()).inflate(R.layout.storylayout , parent , false);
                return new MyStoryAdapter.myView(view);

        }

        @Override
        public void onBindViewHolder(@NonNull myView holder, int position) {

            HashMap<String, String> map = arrayList1.get(position);

            switch (position) {
                case 9:
                    //Picasso.get().load(map.get("image")).into(holder.storyimg);
                    //holder.usersname.setText(map.get("name"));
                    break;
                case 1:
                    Picasso.get().load(map.get("image")).into(holder.storyimg);
                    holder.usersname.setText(map.get("name"));

                default:
                    Picasso.get().load(map.get("image")).into(holder.storyimg);
                    holder.usersname.setText(map.get("name"));
                    return;
            }



        }

        @Override
        public int getItemCount() {
            return arrayList1.size();
        }

        public class myView extends RecyclerView.ViewHolder{
            TextView usersname, your;
            ImageView imgyou, storyimg;
            public myView(@NonNull View itemView) {
                super(itemView);
                usersname = itemView.findViewById(R.id.userstoryname);
                your = itemView.findViewById(R.id.your);
                imgyou = itemView.findViewById(R.id.imgyoustory);
                storyimg = itemView.findViewById(R.id.storyimg);
            }
        }


    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myView>{

        @NonNull
        @Override
        public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.instaui, parent, false);
            return new myView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myView holder, int position) {

            HashMap<String, String> map = arrayList.get(position);

            Picasso.get().load(map.get("image")).into(holder.user);
            Picasso.get().load(map.get("image")).into(holder.image);
            holder.name.setText(map.get("name"));
            holder.like.setText(map.get("like"));
            holder.comment.setText(map.get("comment"));
            holder.text.setText(map.get("text"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class myView extends RecyclerView.ViewHolder{
            TextView name, like, text, comment;
            ImageView user, image;
            public myView(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.username);
                like = itemView.findViewById(R.id.like);
                text = itemView.findViewById(R.id.textpost);
                comment = itemView.findViewById(R.id.comment);
                user = itemView.findViewById(R.id.userimg);
                image = itemView.findViewById(R.id.postimg);
            }
        }
    }
}
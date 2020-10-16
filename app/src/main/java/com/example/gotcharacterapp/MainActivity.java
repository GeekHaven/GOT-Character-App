package com.example.gotcharacterapp;


import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {
    private List<Character> characterList = new ArrayList<>();
    private static final String API_REQUEST = "https://raw.githubusercontent.com/jeffreylancaster/game-of-thrones/master/data/characters.json";
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mTopToolbar;
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        characterList.clear();
        fetchData();

        //Using recycler view


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView)searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchData(){

        StringRequest request = new StringRequest(Request.Method.GET, API_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray allCharacters = jsonObject.getJSONArray("characters");
                    for(int i = 0 ; i < allCharacters.length() ; i++){
                        JSONObject character = allCharacters.getJSONObject(i);
                        String name = "";
                        String image_url = "";
                        String house = "";
                        List<String> people_killed = new ArrayList<>();
                        List<String> killed_by = new ArrayList<>();
                        List<String> parents = new ArrayList<>();
                        List<String> siblings = new ArrayList<>();
                        List<String> spouse = new ArrayList<>();

                        if(character.has("characterName"))  name += character.getString("characterName");
                        if(character.has("characterImageThumb"))  image_url += character.getString("characterImageThumb");
                        if(character.has("houseName"))  house += character.getString("houseName");
                        if(character.has("killed")){
                            JSONArray jsonKilled = character.getJSONArray("killed");
                            for(int j = 0 ; j < jsonKilled.length() ; j++){
                                people_killed.add(jsonKilled.getString(j));
                            }
                        }
                        if(character.has("killedBy")){
                             JSONArray jsonKilledBy = character.getJSONArray("killedBy");
                             for(int j = 0 ; j < jsonKilledBy.length() ; j++){
                                 killed_by.add(jsonKilledBy.getString(j));
                             }
                        }
                        if(character.has("parents")){
                            JSONArray jsonParent = character.getJSONArray("parents");
                            for(int j = 0 ; j < jsonParent.length() ; j++){
                                parents.add(jsonParent.getString(j));
                            }
                        }
                        if(character.has("siblings")){
                            JSONArray jsonSiblings = character.getJSONArray("siblings");
                            for(int j = 0 ; j < jsonSiblings.length() ; j++){
                                siblings.add(jsonSiblings.getString(j));
                            }
                        }
                        if(character.has("marriedEngaged")){
                            JSONArray jsonSpouse = character.getJSONArray("marriedEngaged");
                            for(int j = 0 ; j < jsonSpouse.length() ; j++){
                                spouse.add(jsonSpouse.getString(j));
                            }
                        }

                        Character c = new Character(name,image_url,house,people_killed,killed_by,parents,siblings,spouse);
                        characterList.add(c);
                    }

                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,characterList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



}
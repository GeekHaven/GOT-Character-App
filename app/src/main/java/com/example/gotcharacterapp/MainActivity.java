package com.example.gotcharacterapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;

import android.os.DeadObjectException;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import adapter.*;


import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.YELLOW;

public class MainActivity extends AppCompatActivity {



    List<String> generalList = new ArrayList<>();
    Toolbar mTopToolbar;
    private List<CharacterItem> characterList = new ArrayList<>();
    private static final String API_REQUEST = "https://raw.githubusercontent.com/jeffreylancaster/game-of-thrones/master/data/characters.json";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton floatingActionButton;
    private  MyDbHandler myDbHandler;
    int click=0;
    private List<CharacterItem> fav_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_favorite_white__24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        characterList.clear();

        myDbHandler = new MyDbHandler(getApplicationContext());

        if(myDbHandler.getCount() == 0){
            fetchData();
        }
        else{
            Log.d("MainActivity.java","fetch form sql database");
            characterList = myDbHandler.getCharacterItemList();
            setRecycleView();
        }

        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fav_list = new ArrayList<>();
                for(CharacterItem character : characterList){
                    if(character.getFavourite()){
                        fav_list.add(character);
                    }
                }


                click++;
                if(click%2!=0){
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_favorite_24);// set drawable icon
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,fav_list);
                }
                else{
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_favorite_white__24);// set drawable icon
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,characterList);

                }
                recyclerView.setAdapter(recyclerViewAdapter);

            }
        });










        //Using recycler view
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0 && floatingActionButton.getVisibility() == View.INVISIBLE){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }else if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0){
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    //Toast.makeText(MainActivity.this, "At Top!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)searchViewItem.getActionView();
        SearchView searchView1 = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        
        searchView1.setBackgroundColor(Color.TRANSPARENT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView1.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    searchView1.setBackgroundColor(Color.TRANSPARENT);
                }
                else{
                    searchView1.setBackgroundColor(Color.rgb(31,31,31));
                }
                recyclerViewAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void fetchData(){

        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();

        StringRequest request = new StringRequest(Request.Method.GET, API_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("MainActivity.java","Fetch from internet");
                
                characterList.clear();
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
                        List<String> children = new ArrayList<>();

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
                        if(character.has("parentOf")){
                            JSONArray jsonChildren = character.getJSONArray("parentOf");
                            for(int j = 0 ; j < jsonChildren.length() ; j++) {
                                children.add(jsonChildren.getString(j));
                            }
                        }
                        CharacterItem c = new CharacterItem(name,image_url,house,people_killed,killed_by,parents,siblings,spouse,children);
                        characterList.add(c);
                    }
                    myDbHandler.addCharacterItems(characterList);
                    setRecycleView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadingDialog.dismissDialog();


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

    private void setRecycleView(){
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,characterList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0){
            assert data != null;
            CharacterItem characterItem = (CharacterItem) data.getSerializableExtra("character Item");
            int index = (int) data.getIntExtra("index",-1);
            if(index != -1) {
                characterList.set(index, characterItem);
                myDbHandler.updateFavouriteState(characterItem, index);
                recyclerViewAdapter.notifyItemChanged(index);
            }
        }else{
            Log.d("MainActivity","Bad intent");
        }
    }

    @Override
    protected void onDestroy() {
        myDbHandler.close();
        super.onDestroy();
    }


}
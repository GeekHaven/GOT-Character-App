package com.example.gotcharacterapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplayCharacterItem extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_page);
        CharacterItem character = (CharacterItem) getIntent().getSerializableExtra("Character Items");
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(character.getName());
        TextView house = (TextView)findViewById(R.id.house);
        house.setText(character.getHouse());

        TextView people_killed = (TextView)findViewById(R.id.people_killed);
        people_killed.setText((getString(character.getPeople_killed()).equals("")) ? "--" : getString(character.getPeople_killed()));


        TextView children = (TextView)findViewById(R.id.children);
        children.setText((getString(character.getChildren()).equals("")) ? "--" : getString(character.getChildren()));

        TextView killed_by = (TextView)findViewById(R.id.killed_by);
        killed_by.setText((getString(character.getKilled_by()).equals("")) ? "--" : getString(character.getKilled_by()));

        TextView parents = (TextView)findViewById(R.id.parent);
        parents.setText((getString(character.getParents()).equals(""))? "--" : getString(character.getParents()));

        TextView siblings = (TextView)findViewById(R.id.siblings);
        siblings.setText((getString(character.getSiblings()).equals("")) ? "--" : getString(character.getSiblings()));

        TextView spouse = (TextView)findViewById(R.id.spouse);
        spouse.setText((getString(character.getSpouse()).equals("")) ? "--" : getString(character.getSpouse()));

        if(character.getFavourite()){
            ImageView fav = (ImageView) findViewById(R.id.imageView2);
            fav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            ImageView fav = (ImageView) findViewById(R.id.imageView2);
            fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        if(!character.getImage_url().equals("")){
            CircleImageView profile_image = (CircleImageView) findViewById(R.id.profile_image);
            Picasso.with(getApplicationContext()).load(character.getImage_url()).placeholder(R.drawable.gotimage).into(profile_image);
        }

    }

    private static String getString(List<String> list){
        String str = "";
        for(String s : list){
            str += s + "\n";
        }
        return str.trim();
    }

}

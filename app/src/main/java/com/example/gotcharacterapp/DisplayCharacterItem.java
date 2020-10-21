package com.example.gotcharacterapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.Serializable;

public class DisplayCharacterItem extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row);
        CharacterItem character = (CharacterItem) getIntent().getSerializableExtra("Character Items");
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(character.getName());
        TextView house = (TextView)findViewById(R.id.house);
        house.setText(character.getHouse());
    }
}

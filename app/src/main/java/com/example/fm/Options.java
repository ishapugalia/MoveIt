package com.example.fm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {

    //Variables
    Button addF,listofItems,checkStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        addF = findViewById(R.id.addfrieght);
        listofItems = findViewById(R.id.listofitems);
        checkStat = findViewById(R.id.checkstatus);

        addF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Options.this,Newgoods.class);
                startActivity(i);
            }
        });
    }


}

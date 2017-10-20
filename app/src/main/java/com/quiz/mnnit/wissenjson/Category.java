package com.quiz.mnnit.wissenjson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Button gk = (Button)findViewById(R.id.gkb);
        Button geo = (Button)findViewById(R.id.geob);
        Button hist = (Button)findViewById(R.id.histb);
        Button myth = (Button)findViewById(R.id.mythb);
        Button pol = (Button)findViewById(R.id.polb);
        Button sp = (Button)findViewById(R.id.spd);


        gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","9");
                startActivity(i);
            }
        });
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","22");
                startActivity(i);
            }
        });
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","23");
                startActivity(i);
            }
        });
        myth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","20");
                startActivity(i);
            }
        });
        pol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","24");
                startActivity(i);
            }
        });
        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, MainActivity.class);
                i.putExtra("ct","21");
                startActivity(i);
            }
        });

    }


}

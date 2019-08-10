package com.example.compalintapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class viewcomplaint extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomplaint);

        listView = findViewById(R.id.listview);

        DBHelper dbHelper = new DBHelper(this);
        ArrayList<Storage> compfetch= dbHelper.readComplaint();
        String num[] = {"e","f","g","h"};
        String name[] = {"a","b","c","d"};
        String comp[] = {"e","f","g","h"};


        int i=0;
//
//        for(Storage eachstorage:compfetch)
//        {
//            name[i]=eachstorage.getName();
//            comp[i]=eachstorage.getComplaint();
//            num[i]=eachstorage.getNum();
//            i++;
//
//        }


        Log.d("VIEWCOMPLAINT",name[0]);
//        MyAdapter myAdapter=new MyAdapter(viewcomplaint.this,R.layout.list_row_layout,viewcomplaint.this,name,comp,num);
        MyAdapter myAdapter=new MyAdapter(viewcomplaint.this,R.layout.list_row_layout,viewcomplaint.this, (ArrayList<Storage>) compfetch);
        listView.setAdapter(myAdapter);
     }
    }


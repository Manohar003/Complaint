package com.example.compalintapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    Activity context;
   ArrayList<Storage> listFetched;
   static int counter=1;

    public MyAdapter(Context context, int resource, Activity context1,ArrayList<Storage> listFetched) {
        super(context, resource,listFetched);
        this.context = context1;
//        this.name = name;
//        this.comp = comp;
//        this.num = num;
        this.listFetched = listFetched;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view= LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout,null,true);
        TextView cnum=view.findViewById(R.id.textView3);
        TextView cname=view.findViewById(R.id.textView);
        TextView compp=view.findViewById(R.id.textView2);


//        cname.setText(name[position]);
//        compp.setText(comp[position]);
//        cnum.setText(num[position]);


        cnum.setText(counter+"");
        cname.setText(listFetched.get(position).getName());
        compp.setText(listFetched.get(position).getComplaint());
        counter++;

        return view;
    }
}

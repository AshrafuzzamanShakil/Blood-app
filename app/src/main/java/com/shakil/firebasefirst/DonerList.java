package com.shakil.firebasefirst;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class DonerList extends ArrayAdapter<Doner> {
    private Activity context;
    List<Doner>donerList;
    public DonerList(Activity context,List<Doner>donerList){
        super(context,R.layout.sympleview,donerList);
        this.context=context;
        this.donerList=donerList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listviewitem=layoutInflater.inflate(R.layout.sympleview,null,true);

        TextView textView1= listviewitem.findViewById(R.id.naid);
        TextView textView2=listviewitem.findViewById(R.id.agid);
        TextView textView3=listviewitem.findViewById(R.id.bdgroupid);
        TextView textView4=listviewitem.findViewById(R.id.areaid);
        ImageView imageView=listviewitem.findViewById(R.id.image);
        ImageView badge=listviewitem.findViewById(R.id.bazeid);
        Doner doner=donerList.get(position);
        textView1.setText(doner.getName());
        textView2.setText(doner.getDate());
        textView3.setText(doner.getBloodGroup());
        textView4.setText(doner.getArea());
        try {
            if(doner.getImagepath().toString().contentEquals("shakil"))
            {
                imageView.setImageResource(R.drawable.male);
            }
            else {
                Picasso.get().load(doner.getImagepath().toString()).into(imageView);
            }
        }catch (Exception e)
        {
            e.getMessage();
        }

        if(doner.getNumber_of_donation()<10)
        {
            badge.setImageResource(R.drawable.bronze);
        }
        if(doner.getNumber_of_donation()>=10&&doner.getNumber_of_donation()<25)
        {
            badge.setImageResource(R.drawable.silver);
        }
        if (doner.getNumber_of_donation()>=25)
        {
            badge.setImageResource(R.drawable.gold);
        }




        return listviewitem;


    }
}

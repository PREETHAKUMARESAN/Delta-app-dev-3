package com.example.preethakumaresan.appdev3demo;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * Created by PREETHA KUMARESAN on 28-06-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    public String[] nams;
    public String[] numbs;
    public Bitmap[] imgs;
    Context context;

    //Constructor for CustomAdapter
    public CustomAdapter(Context context,String[] names,String[] numbers,Bitmap[] pics) {
        super(context,R.layout.contacts_display);
        this.nams= names;
        this.numbs = numbers;
        this.context = context;
        this.imgs = pics;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){

        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View customView = Inflater.inflate(R.layout.contacts_display, parent, false);
        final String NameClicked = getItem(position);

        TextView nameShit = (TextView) customView.findViewById(R.id.contactname);
        TextView numberShit = (TextView) customView.findViewById(R.id.contactnumber);
        ImageView imageShit = (ImageView) customView.findViewById(R.id.picture);
        Button edit = (Button) customView.findViewById(R.id.edit);

        //Update a contact using the button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                alertdialog.setMessage("Are you sure you want to edit?").setCancelable(false).setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bitmap bitmap = imgs[position];

                        ByteArrayOutputStream shit = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, shit);

                        //context here refers to the mainactivity
                        Intent intent = new Intent(context, edit_contact_class.class);
                        intent.putExtra("namemod",NameClicked).putExtra("imgmod",bitmap).putExtra("numbmod",numbs[position]);
                        context.startActivity(intent);
                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();
            }
        });
        //alloting the values to the textView
        nameShit.setText(NameClicked);
        numberShit.setText(numbs[position]);
        imageShit.setImageBitmap(imgs[position]);
        return customView;
    }
}

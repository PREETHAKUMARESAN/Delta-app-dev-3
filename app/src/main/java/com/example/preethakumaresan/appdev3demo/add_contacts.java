package com.example.preethakumaresan.appdev3demo;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by PREETHA KUMARESAN on 28-06-2016.
 */
public class add_contacts extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    Bitmap bitmap = null;
    byte[] pics;
    EditText name;
    EditText number;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);

        name= (EditText) findViewById(R.id.nameAdd);
        number = (EditText) findViewById(R.id.numberAdd);
        database = new Database(this);

        ((Button) findViewById(R.id.choose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                myIntent.setType("image/*");
                startActivityForResult(myIntent, 1);
            }
        });

        ((Button) findViewById(R.id.addContact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameShit = name.getText().toString();
                String numberShit = number.getText().toString();

                if(bitmap == null) {
                    bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();}
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                pics = baos.toByteArray();

                ByteArrayInputStream ByteShit= new ByteArrayInputStream(pics);
                Bitmap finalImage= BitmapFactory.decodeStream(ByteShit);

                ImageView imgNew= (ImageView) findViewById(R.id.imageView2);
                imgNew.setImageBitmap(finalImage);

                if(nameShit.equals(""))
                {
                    Toast.makeText(add_contacts.this,"I need a name...U dumbass!! ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean add = database.insertContact(nameShit,numberShit,pics);
                    if(add)
                    {
                        Toast.makeText(add_contacts.this,"done!",Toast.LENGTH_SHORT).show();
                    }
                }
                //To receive a result
                Intent intent = new Intent(add_contacts.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });}

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            Bundle bdn = data.getExtras();
            Bitmap btm = (Bitmap) bdn.get("data");
            bitmap = btm;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            pics = baos.toByteArray();

            ImageView img_here = (ImageView) findViewById(R.id.imageView2);
            img_here.setImageBitmap(btm);
        }

    }}


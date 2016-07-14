package com.example.preethakumaresan.appdev3demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by PREETHA KUMARESAN on 28-06-2016.
 */
public class edit_contact_class extends AppCompatActivity {

    public static final int SELECT_FILE = 1;
    EditText nameE,number;
    Button edit,editPic;
    Bitmap bitImage;
    byte[] byteImage;

    ImageView imageNew;
    String nameUser,numbUser;
    Database database=new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_layout);

        nameE= (EditText)findViewById(R.id.nameEdit) ;
        number = (EditText) findViewById(R.id.numberEdit);
        edit=(Button) findViewById(R.id.confirm);
        editPic = (Button) findViewById(R.id.editPic);

        Intent intent = getIntent();
        Bundle bun = intent.getExtras();

        nameUser= bun.getString("namemod");
        nameE.setText(nameUser);

        numbUser= bun.getString("numbmod");
        number.setText(numbUser);

        bitImage = (Bitmap)intent.getParcelableExtra("imgmod");

        setTitle(nameUser);
        imageNew= (ImageView) findViewById(R.id.imageView3);
        imageNew.setImageBitmap(bitImage);

        ByteArrayOutputStream shite = new ByteArrayOutputStream();
        bitImage.compress(Bitmap.CompressFormat.JPEG, 100, shite);
        byteImage = shite.toByteArray();

        //change the picture with something from the gallery
        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm=null;
                bm = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
                bitImage=bm;
                ByteArrayOutputStream shit = new ByteArrayOutputStream();
                bitImage.compress(Bitmap.CompressFormat.JPEG, 100, shit);
                byteImage= shit.toByteArray();
            }
        });

        //Edit button is clicked
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameUser= nameE.getText().toString();
                numbUser= number.getText().toString();

                if (nameUser.equals("")||numbUser.equals("")) {
                    Toast.makeText(edit_contact_class.this, "you need to enter something...moron", Toast.LENGTH_SHORT).show();
                } else {
                    boolean bool = database.edit_data(nameUser,numbUser,byteImage);
                    if (bool)
                        Toast.makeText(edit_contact_class.this, "done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(edit_contact_class.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Function that captures image, and updates the DB.
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

    }
}

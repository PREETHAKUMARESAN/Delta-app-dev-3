package com.example.preethakumaresan.appdev3demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * Created by PREETHA KUMARESAN on 28-06-2016.
 */
public class contact extends AppCompatActivity {

    TextView number_contact,name_contact;
    ImageView img_contact;
    Button edit;
    Bitmap bit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_class_layout);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Bitmap bit_now = (Bitmap) b.get("bitm");
        bit=bit_now;
        final String name = b.getString("name");
        final String number = b.getString("number");

        img_contact = (ImageView) findViewById(R.id.pic);
        number_contact = (TextView) findViewById(R.id.the_num);
        name_contact=(TextView)findViewById(R.id.the_name);
        edit = (Button) findViewById(R.id.edit_contact);

        name_contact.setText(name);
        number_contact.setText(number);
        img_contact.setImageBitmap(bit_now);

        //edit
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap bitmap=bit;

                ByteArrayOutputStream shit = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, shit);

                Intent p = new Intent(contact.this,edit_contact_class.class);
                p.putExtra("namemod", name).putExtra("imgmod",bitmap).putExtra("numbmod",number);
                startActivity(p);

            }
        });

        setTitle(name);
    }

}

package com.example.preethakumaresan.appdev3demo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Database dataBase = new Database(this);
    List<String> list_names = new ArrayList<>();
    List<String> list_numbers = new ArrayList<>();
    List<Bitmap> list_images = new ArrayList<>();

    String[] name;
    String[] number;
    Bitmap[] image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtain contacts from our Database
        obtainShit();
        name = new String[list_names.size()];
        number = new String[list_numbers.size()];
        image = new Bitmap[list_images.size()];
        for (int i = 0; i < list_names.size(); ++i) {
            name[i] = list_names.get(i);
            number[i] = list_numbers.get(i);
            image[i] = list_images.get(i);
        }

        //Search for the entered name or number.
        ((SearchView) findViewById(R.id.search)).setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = ((EditText) findViewById(R.id.search)).getText().toString();
                boolean present = false;
                //If not found
                if(!present) Toast.makeText(MainActivity.this,"Contact Missing",Toast.LENGTH_SHORT).show();
                Pattern pat = Pattern.compile("^[0-9]+$");
                if(pat.matcher(num).matches()) {
                    for (int i = 0; i < number.length; i++) {
                        if (number[i].equals(num)) {
                            present = true;
                            break;
                        }
                    }
                }
                else{
                    for(int i=0; i<name.length; ++i){
                        if(name[i].equalsIgnoreCase(num)){
                            present = true;
                            break;
                        }
                    }
                }}
        });

        //the adapter is set to the listView-to show the items
        ListAdapter mAdapter = new CustomAdapter(MainActivity.this, name, number, image);
        ListView con_list = (ListView) findViewById(R.id.con_list);
        assert con_list != null;
        con_list.setAdapter(mAdapter);

        //Listener when user clicks a contact
        con_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name_user = String.valueOf(parent.getItemAtPosition(position));
                ImageView img_user = (ImageView) view.findViewById(R.id.picture);
                String number_user = number[position];

                img_user.buildDrawingCache();

                Bitmap bitm = img_user.getDrawingCache();
                Bundle extras = new Bundle();
                extras.putParcelable("bitm",bitm);

                Intent intent = new Intent(MainActivity.this, contact.class);
                intent.putExtra("name", name_user).putExtra("number", number_user);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
    }
    public void obtainShit(){
        Cursor cur = dataBase.getAllData();
        if (cur.getCount() == 0) {
            return;
        }
        while (cur.moveToNext()){
            list_names.add(cur.getString(0));
            list_numbers.add(cur.getString(1));
            byte[] bytes = cur.getBlob(2);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(bytes);
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            list_images.add(theImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.addContacts)
        {
        Intent intent = new Intent(MainActivity.this,add_contacts.class);
        startActivity(intent);
        finish();
        }

        return super.onOptionsItemSelected(item);
    }



}

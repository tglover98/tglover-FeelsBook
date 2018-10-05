package com.example.tglover_feelsbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This activity is reponsible for drsplaying the history of the emotions enterd. at the bottom of the
 * display has two toggle switches for changeing between aditing mode and deleting mode and defult viewing.
 * the editing mode makes the list items clickable and that allows for the contents of the selected item
 * to be edited. Deleteing mode deltes seleted item from the lsit and updates adapter
 * this activity loads and saves changes everytime it is used.
 * @author Tristen Glover
 */
public class HistoryActivity extends AppCompatActivity {
    private static final String FILENAME = "file1.sav";
    ArrayList<Emotion> EmotionList;
    //private CustomAdapter adapter;
    //ArrayAdapter<Emotion> adapter;
    public boolean deleting = false;
    public boolean editing = false;
    public static final int REQUEST_CODE_GET_EDITS = 2099;
    public CustomAdapter adapter;
    public Emotion emotion;
    public ListView EmotionHistory;
    public SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Intent intent = getIntent();
    }

    protected void onStart() {
        super.onStart();
        loadFromFile();
        EmotionHistory = (ListView) findViewById(R.id.History_list);

        adapter = new CustomAdapter(this,R.layout.list_view_adapter,EmotionList);
        EmotionHistory.setAdapter(adapter);


        // When item is tapped, toggle checked properties of CheckBox and
        // Planet.


    }

    private void loadFromFile() {
        try {

            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();

            EmotionList = gson.fromJson(in, listType);
            fis.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //EmotionList = new ArrayList<Emotion>();
        } catch (IOException e) {
            //throw new RuntimeException();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        Switch deleteSwitch = (Switch) findViewById(R.id.DeleteSwitch);
        deleteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    deleting = true;
                    editing = false;
                }
                else{
                    deleting = false;
                }
            }
        });
        Switch editSwitch = (Switch) findViewById(R.id.EditSwitch);
        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    deleting = false;
                    editing = true;
                }
                else{
                    editing = false;
                }
            }
        });

        EmotionHistory.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id)
            {
                emotion = adapter.getItem(position);

                if (deleting){
                    EmotionList.remove(emotion);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
                if(editing) {
                    Log.i("TAG123", "onItemClick: ");

                    String date = formater.format(emotion.getDate());
                    String comment = emotion.getComment();
                    Bundle Extras = new Bundle();
                    Intent intent = new Intent(item.getContext(), EditActivity.class);
                    Extras.putString("DATE", date);
                    Extras.putString("COMMENT", comment);
                    intent.putExtras(Extras);
                    startActivityForResult(intent, REQUEST_CODE_GET_EDITS);
                }

            }
        });
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(EmotionList, out);
            out.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case REQUEST_CODE_GET_EDITS:
                if (resultCode == Activity.RESULT_OK){

                    String strDate = data.getStringExtra("Date");
                    String newcomment = data.getStringExtra("COMMENT");

                    //Log.i("SWITCHH", strDate);

                    try {
                        Date date = formater.parse(strDate);
                        emotion.setDate(date);


                    } catch (ParseException e) {
                       // Log.i("SWITCHH", "exception");

                    }

                    //emotion.setDate(date);

                        emotion.set_comment(newcomment);




                    sort();
                    adapter.notifyDataSetChanged();
                    saveInFile();



                }
        }
    }

    private void sort() {
        for (int i = 0; i < EmotionList.size(); i++){
            for(int j = 0; j<EmotionList.size()-i-1; j++){
                // more recent on the left
                if (EmotionList.get(j).getDate().compareTo(EmotionList.get(j+1).getDate())<0 ){
                    Log.i("SWITCHH", "sort: ");
                    Emotion temp = EmotionList.get(j+1);
                    EmotionList.set(j+1, EmotionList.get(j));
                    EmotionList.set(j, temp);
                }
            }

        }
    }
}

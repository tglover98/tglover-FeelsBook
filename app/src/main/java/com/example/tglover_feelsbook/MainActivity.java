package com.example.tglover_feelsbook;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Date;

import static java.sql.DriverManager.println;

/**
 * This activity id the main menu for the app the user clicks on an emotion and it adds that emotion and send them to the comment activity to do optional comment.
 * the program also displays a count for each emotion and everytime the activity is returned to. The activity loads in an array list of emotions and evertime onResume the array
 * list is saved into file
 * @author Tristen Glover
 */

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file1.sav";
    public static final String EXTRA_MESSAGE = "com.example.tgover_feelsbook.MESSAGE";
    ArrayList<Emotion> EmotionList = new ArrayList<>();
    ArrayAdapter<Emotion> adapter;
    public static final int REQUEST_CODE_GET_COMMENT = 1014;
    public Emotion emotion= new Emotion();
    public boolean update = false;

    public boolean NeedLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (NeedLoad){
            //loadFromFile();
        }

        if (update){
            EmotionList.add(emotion);
            emotion = new Emotion();
            update = false;
            saveInFile();
        }

       UpdateCounts();


        Button loveButton = (Button) findViewById(R.id.love);
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("love");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });
        Button joyButton = (Button) findViewById(R.id.joy);
        joyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("joy");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });
        Button supriseButton = (Button) findViewById(R.id.suprise);
        supriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("surprise");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });
        Button angerButton = (Button) findViewById(R.id.anger);
        angerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("anger");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });
        Button sadButton = (Button) findViewById(R.id.sadness);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("sadness");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });
        Button fearButton = (Button) findViewById(R.id.fear);
        fearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setEmotion("fear");
                emotion.setDate(new Date());
                Intent intent = new Intent(v.getContext(), comment_activity.class);
                startActivityForResult(intent,REQUEST_CODE_GET_COMMENT);
                update = true;
            }
        });


        Button historyButton = (Button) findViewById(R.id.history);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                startActivity(intent);

                //TO DO CALLS HISTORY ACTIVITY
            }
        });


    }

    private void UpdateCounts() {
        int loveCount = 0;
        int JoyCount = 0;
        int SurpriseCount = 0;
        int AngerCount = 0;
        int SadCount = 0;
        int FearCount = 0;
        String CountStr = "count: ";

        for (Emotion emot :EmotionList){
            Log.i("THIS_IS_THE_DATE", String.valueOf(emot.getDate()));
            if (emot.getEmotion().equals("love")){
                loveCount ++;
            }
            if(emot.getEmotion().equals("joy")){
                JoyCount ++;
            }
            if(emot.getEmotion().equals("surprise")){
                SurpriseCount ++;
            }
            if(emot.getEmotion().equals("anger")){
                AngerCount ++;
            }
            if(emot.getEmotion().equals("sadness")){
                SadCount ++;
            }
            if(emot.getEmotion().equals("fear")){
                FearCount ++;
            }

        }
        TextView LoveText = (TextView)findViewById(R.id.love_count);
        LoveText.setText(CountStr + String.valueOf(loveCount));

        TextView JoyText = (TextView)findViewById(R.id.joy_count);
        JoyText.setText(CountStr + String.valueOf(JoyCount));

        TextView SurpriseText = (TextView)findViewById(R.id.suprise_count);
        SurpriseText.setText(CountStr + String.valueOf(SurpriseCount));

        TextView AngerText = (TextView)findViewById(R.id.anger_count);
        AngerText.setText(CountStr + String.valueOf(AngerCount));

        TextView SadText = (TextView)findViewById(R.id.sadness_count);
        SadText.setText(CountStr + String.valueOf(SadCount));

        TextView fearText = (TextView)findViewById(R.id.fear_count);
        fearText.setText(CountStr + String.valueOf(FearCount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case REQUEST_CODE_GET_COMMENT:
                if (resultCode == Activity.RESULT_OK){
                    String comment = data.getStringExtra("theComment");
                    emotion.set_comment(comment);

                }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

    }

    private void saveInFile() {
        try {
            Log.i("TEST12345", String.valueOf(EmotionList.size()));
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

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); // Library to save objects
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();

            EmotionList =gson.fromJson(in, listType);
            fis.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //EmotionList = new ArrayList<Emotion>();
        } catch (IOException e) {
            //throw new RuntimeException();
        }
    }
}

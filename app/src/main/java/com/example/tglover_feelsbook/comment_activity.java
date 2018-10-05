package com.example.tglover_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class comment_activity extends AppCompatActivity {
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_activity);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final Button commentButton = (Button) findViewById(R.id.comment_button);
        commentText = (EditText) findViewById(R.id.comment);

        commentButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                EditText edit = (EditText) findViewById(R.id.comment);
                String comment = edit.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("theComment",comment);
                setResult(Activity.RESULT_OK,intent);
                finish();

            }

        });
        

    }

}

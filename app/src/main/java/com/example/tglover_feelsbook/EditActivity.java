package com.example.tglover_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * recieves a bundle of comment and date for editing and returns the new values back to history activity
 * @author Tristen glover
 */

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        Bundle extras =  intent.getExtras();
        String Date = extras.getString("DATE");
        String comment = extras.getString("COMMENT");

        TextView DateView = (TextView) findViewById(R.id.DateEdit);
        DateView.setText(Date);

        TextView CommentView = (TextView) findViewById(R.id.CommentEdit);
        CommentView.setText(comment);

        Button Save = (Button) findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                EditText edit1 = (EditText) findViewById(R.id.DateEdit);
                EditText edit2 = (EditText) findViewById(R.id.CommentEdit);
                String newDate = edit1.getText().toString();
                String NewComment = edit2.getText().toString();



                Intent intent = new Intent();
                intent.putExtra("Date", newDate);
                intent.putExtra("COMMENT",NewComment);

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}

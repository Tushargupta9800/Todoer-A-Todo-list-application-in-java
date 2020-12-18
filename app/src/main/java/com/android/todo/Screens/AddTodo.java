package com.android.todo.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.todo.R;

import java.util.ArrayList;

public class AddTodo extends AppCompatActivity {

    EditText header,des;
    Button sendback;
    ArrayList<String> ToMainScreen;
    ArrayList<String> FromMainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        header = findViewById(R.id.header);
        FromMainScreen = new ArrayList<>();
        FromMainScreen.addAll(getIntent().getStringArrayListExtra("Extra"));
        des = findViewById(R.id.Description);
        sendback = findViewById(R.id.Sendback);
        ToMainScreen = new ArrayList<>();
        listner();
    }

    private void listner() {
        sendback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int a = 0;
                for(int i=0;i<FromMainScreen.size();i++){
                    if(header.getText().toString().equals(FromMainScreen.get(i))){
                        a=1;
                    }
                }
                if(a == 0) {
                    if (header.getText().toString().length() > 0) {
                        ToMainScreen.add(header.getText().toString());
                        if (des.getText().toString().length() > 0) {
                            ToMainScreen.add(des.getText().toString());
                        } else {
                            ToMainScreen.add("No Description");
                        }
                        setResult(RESULT_OK, intent);
                    } else {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Nothing to Add", Toast.LENGTH_SHORT).show();
                        ToMainScreen.add("qwertyuiop");
                        ToMainScreen.add("asdfghjkl");
                        setResult(RESULT_CANCELED, intent);
                    }
                    intent.putExtra("message", ToMainScreen);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Header Name Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

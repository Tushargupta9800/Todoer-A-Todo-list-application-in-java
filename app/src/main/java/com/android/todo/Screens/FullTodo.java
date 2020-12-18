package com.android.todo.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.todo.R;

import java.util.ArrayList;

public class FullTodo extends AppCompatActivity {

    TextView head,des;
    ImageView delete,edit_Todo;
    ArrayList<String> Todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_todo);
        head = findViewById(R.id.Full_todo_header);
        des = findViewById(R.id.Full_todo_description);
        edit_Todo = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        Todo = new ArrayList<>();
        Todo.addAll(getIntent().getStringArrayListExtra("fulltodo"));
        head.setText(Todo.get(0));
        des.setText(Todo.get(1));
        listner();
    }

    private void listner() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int pos = Integer.valueOf(Todo.get(2));
                intent.putExtra("Todelete",pos);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        edit_Todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Screens.Edit_Activity");
                intent.putExtra("toedit",Todo);
                startActivityForResult(intent,120);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 120 && resultCode == RESULT_OK){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }
    }
}

package com.android.todo.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.todo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;

public class Edit_Activity extends AppCompatActivity {

    TextView edit_header,edit_description;
    Button done_edit;
    ArrayList<String> toEdit;
    ArrayList<String> Edit_MyTodos;
    File file,folder,todofile;
    String MyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        toEdit = new ArrayList<>();
        Edit_MyTodos = new ArrayList<>();
        toEdit.addAll(getIntent().getStringArrayListExtra("toedit"));
        edit_header = findViewById(R.id.edit_header);
        edit_description = findViewById(R.id.edit_Description);
        done_edit = findViewById(R.id.doedit);
        edit_header.setText(toEdit.get(0));
        edit_description.setText(toEdit.get(1));
        listner();
    }

    private void listner() {

        done_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                file = new File(folder, toEdit.get(0) + ".txt");
                try {
                    file.delete();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                file = new File(folder, edit_header.getText().toString().trim() + ".txt");
                todofile = new File(folder, "SavingToDoOfTheToDoerSoDontDeleteThisFile.txt");
                SaveDesText(file,edit_description.getText().toString());
                checkAgain();
                Intent intnt = new Intent();
                setResult(RESULT_OK,intnt);
                finish();
            }
        });
    }

    private void checkAgain(){
        MyText = OpenFile();
        Edit_MyTodos.addAll(Arrays.asList(MyText.substring(1,MyText.length()-1).split(",")));
        for(int i=0;i<Edit_MyTodos.size();i++){
            Edit_MyTodos.set(i, Edit_MyTodos.get(i).trim());
        }
        Edit_MyTodos.set(Integer.parseInt(toEdit.get(2)),edit_header.getText().toString());
        SaveText(Edit_MyTodos.toString());

    }
    private String OpenFile() {
        String string = getdata(todofile);
        return string;
    }

    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private void SaveText(String savedText) {
        writeTextData(todofile, savedText);
    }

    private void SaveDesText(File DesFile,String savedText) {
        writeTextData(DesFile, savedText);
    }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

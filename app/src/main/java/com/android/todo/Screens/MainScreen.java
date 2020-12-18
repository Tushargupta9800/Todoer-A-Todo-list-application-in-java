package com.android.todo.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.todo.Adapter.adapter;
import com.android.todo.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static android.widget.Toast.LENGTH_SHORT;

public class MainScreen extends AppCompatActivity {

    ArrayList<String> MyTodos;
    ArrayList<String> MyTodosDes;
    ArrayList<String> fromChild;
    ArrayList<File> Description_files;
    private String MyText;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    adapter MyAdapter;
    ListView lst;
    ImageView empty;
    Button Add_Button;
    File folder;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getIds();
        getLists();
        getFiles();
        CheckPermission();
        checkAgain();
        listners();
        Add_Adapter();
    }

    private void getLists() {
        Description_files = new ArrayList<>();
        MyTodosDes = new ArrayList<>();
        MyTodos = new ArrayList<>();
        fromChild = new ArrayList<>();
    }

    private void getIds() {
        Add_Button = findViewById(R.id.AddTodo);
        lst = findViewById(R.id.ListView);
    }

    private void Add_Adapter() {
        MyAdapter = new adapter(getApplicationContext(),MyTodos,MyTodosDes);
        lst.setAdapter(MyAdapter);
    }

    private void listners() {
        Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Screens.AddTodo");
                intent.putExtra("Extra", MyTodos);
                startActivityForResult(intent,999);
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(".Screens.FullTodo");
                ArrayList<String> both;
                both = new ArrayList<>();
                both.add(MyTodos.get(position));
                both.add(MyTodosDes.get(position));
                both.add(Integer.toString(position));
                intent.putExtra("fulltodo",both);
                startActivityForResult(intent,100);
            }
        });
    }

    private void checkAgain(){
        File temp_file;
        MyText = OpenFile();
        empty = findViewById(R.id.empty);
        if(MyText.length() == 0 || MyText.substring(1,MyText.length()-1).length() == 0) {
            int i = 0;
            empty.setVisibility(i);
        }
        else {
            MyTodos.clear();
            MyTodosDes.clear();
            MyTodos.addAll(Arrays.asList(MyText.substring(1,MyText.length()-1).split(",")));
            for(int i=0;i<MyTodos.size();i++){
                MyTodos.set(i, MyTodos.get(i).trim());
                temp_file = new File(folder, MyTodos.get(i) + ".txt");
                Description_files.add(temp_file);
                String Des = OpenDesFile(temp_file);
                MyTodosDes.add(Des);
            }
        }
    }

    private void updateFile(File desFile,String des){
        String SavingingTodos;
        SavingingTodos = MyTodos.toString();
        SaveText(SavingingTodos);
        SaveDesText(desFile,des);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"Bye Bye", LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            int pos = 0;
            pos = data.getIntExtra("Todelete", 0);
            deletedesfile(MyTodos.get(pos));
            MyTodos.remove(pos);
            UpdateTodoFile();
            checkAgain();
            MyAdapter.SetAgain(MyTodos,MyTodosDes);
            MyAdapter.notifyDataSetChanged();
        }
        else if(requestCode == 100){
            checkAgain();
            MyAdapter.SetAgain(MyTodos,MyTodosDes);
            MyAdapter.notifyDataSetChanged();
        }
        if (requestCode == 999 && resultCode == RESULT_OK){
            empty.setVisibility(View.GONE);
            if(data.getStringArrayListExtra("message").get(0) != "qwertyuiop"){
                MyTodos.add(data.getStringArrayListExtra("message").get(0));
                MyTodosDes.add(data.getStringArrayListExtra("message").get(1));
                MyAdapter.add(data.getStringArrayListExtra("message").get(0),data.getStringArrayListExtra("message").get(1));
                MyAdapter.notifyDataSetChanged();
                File temp_file;
                temp_file = new File(folder, data.getStringArrayListExtra("message").get(0) + ".txt");
                Description_files.add(temp_file);
                updateFile(temp_file,data.getStringArrayListExtra("message").get(1));
            }
        }
    }

    private void UpdateTodoFile() {
        String SavingingTodos;
        SavingingTodos = MyTodos.toString();
        SaveText(SavingingTodos);
    }

    private void deletedesfile(String todelete) {
        try {
            File file = new File(folder, todelete + ".txt");;
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void getFiles() {
        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        file = new File(folder, "SavingToDoOfTheToDoerSoDontDeleteThisFile.txt");
    }

    private void CheckPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
    }

    private void SaveText(String savedText) {
        writeTextData(file, savedText);
    }

    private void SaveDesText(File DesFile,String savedText) {
        writeTextData(DesFile, savedText);
    }

    private String OpenDesFile(File DesFile) {
        String string = getdata(DesFile);
        return string;
    }

    private String OpenFile() {
        String string = getdata(file);
        return string;
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

}

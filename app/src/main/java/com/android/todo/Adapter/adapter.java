package com.android.todo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.todo.R;
import com.android.todo.Screens.AddTodo;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class adapter extends ArrayAdapter {

    Context context;
    ArrayList<String> Adapter_Header;
    ArrayList<String> Adapter_Description;

    public adapter(@NonNull Context cntxt, ArrayList<String> H,ArrayList<String> D ) {
        super(cntxt, R.layout.row, H);
        this.Adapter_Description = new ArrayList<>();
        this.Adapter_Header = new ArrayList<>();
        this.context = cntxt;
        this.Adapter_Header.addAll(H);
        this.Adapter_Description.addAll(D);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        TextView Row_Header,Row_Description;
        Row_Header = view.findViewById(R.id.Header);
        Row_Description = view.findViewById(R.id.Descrip);
        Row_Header.setText(this.Adapter_Header.get(position));
        Animation left_anim = AnimationUtils.loadAnimation(context,R.anim.left_anim);
        Row_Header.setAnimation(left_anim);
        Animation right_anim = AnimationUtils.loadAnimation(context,R.anim.right_anim);
        Row_Description.setAnimation(right_anim);

        String des = "";
        if(this.Adapter_Description.get(position).length() >= 30){
            des = Adapter_Description.get(position).substring(0,30);
            des += "...";
        }
        else{
            des = Adapter_Description.get(position);
        }
        Row_Description.setText(des);
        return view;
    }

    public void add(String message, String message1) {
        this.Adapter_Header.add(message);
        this.Adapter_Description.add(message1);
    }

    public void Remove(int pos) {
        this.Adapter_Header.remove(pos);
        this.Adapter_Description.remove(pos);
    }

    public void SetAgain(ArrayList<String> myTodos, ArrayList<String> myTodosDes) {
        this.Adapter_Description.clear();
        this.Adapter_Header.clear();
        this.Adapter_Header.addAll(myTodos);
        this.Adapter_Description.addAll(myTodosDes);
    }
}

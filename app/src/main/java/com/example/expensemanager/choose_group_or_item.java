package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_group_or_item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group_or_item);
    }
    public void navigateToAddAcategory(View v){
        Intent i= new Intent(this,AddCategory.class);
        startActivity(i);
    }

    public void navigateToAddAItem(View v){
        Intent i= new Intent(this,AddExpenseItem.class);
        startActivity(i);
    }
    public void deleteGroup(View v){
        Intent i= new Intent(this,DeleteGroup.class);
        startActivity(i);
    }
    public void deleteItem(View v){
        Intent i= new Intent(this,DeleteItem.class);
        startActivity(i);
    }

}
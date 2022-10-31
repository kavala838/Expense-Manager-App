package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ExpensesTypesData.ExpensesTypes;
import utilities.ExpensesContext;

public class AddCategory extends AppCompatActivity {
    EditText et;
    ExpensesContext expensesContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        expensesContext=new ExpensesContext();
        et=findViewById(R.id.group);
    }
    public void addCategory(View v){
        String val=et.getText().toString();
        if(expensesContext.isGroupPresent(val)){
            Toast.makeText(getApplicationContext(),"Group already exists",Toast.LENGTH_LONG).show();
        }
        else {
            try {
                expensesContext.insertGroup(val);
                Toast.makeText(getApplicationContext(), "Group Added", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
            }
        }
    }
}
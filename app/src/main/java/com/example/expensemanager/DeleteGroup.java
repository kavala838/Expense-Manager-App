package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import utilities.ExpensesContext;

public class DeleteGroup extends AppCompatActivity {
    Spinner spn;
    ExpensesContext expensesContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
        expensesContext=new ExpensesContext();
        spn=findViewById(R.id.spinner);
        fillSpinner();
    }
    public void fillSpinner(){
        List<String> list=expensesContext.getAllGroups();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
    }
    public void delete(View v){
        String group=spn.getSelectedItem().toString();
        expensesContext.deleteGroup(group);
        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
    }
}
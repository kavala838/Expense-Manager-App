package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import utilities.ExpensesContext;

public class DeleteItem extends AppCompatActivity {
    Spinner spn;
    ExpensesContext expensesContext;
    Spinner spnI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        expensesContext=new ExpensesContext();
        spn=findViewById(R.id.spinner);
        spnI=findViewById(R.id.spinnerItem);
        fillSpinner();
        fillSpinnerItem();
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillSpinnerItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void fillSpinner(){
        List<String> list=expensesContext.getAllGroups();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
    }
    public void fillSpinnerItem(){
        String group=spn.getSelectedItem().toString();
        List<String> list=expensesContext.getItemsOfGroup(group);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnI.setAdapter(dataAdapter);
    }
    public void delete(View v){
        String item=spnI.getSelectedItem().toString();
        expensesContext.deleteItem(item);
        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
    }
}
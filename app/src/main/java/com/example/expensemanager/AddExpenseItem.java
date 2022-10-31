package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import utilities.ExpensesContext;

public class AddExpenseItem extends AppCompatActivity {
    Spinner spn;
    EditText et;
    ExpensesContext expensesContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_item);
        spn=findViewById(R.id.spinner);
        expensesContext = new ExpensesContext();
        et=findViewById(R.id.item);
        fillSpinner();
    }
    public void fillSpinner(){
        List<String> list=expensesContext.getAllGroups();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
    }
    public void addItem(View v){
        String item=et.getText().toString();
        String group=spn.getSelectedItem().toString();
        if(expensesContext.isItemPresent(item)){
            Toast.makeText(getApplicationContext(),"Group already exists in "+expensesContext.getGrpName(item),Toast.LENGTH_LONG).show();
        }
        else {
            try {
                expensesContext.insertItem(group,item);
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
            }
        }
    }
}
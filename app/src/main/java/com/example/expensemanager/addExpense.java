package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Optional;

import DateR.DateTimeMaintainer;
import utilities.ExpensesContext;

public class addExpense extends AppCompatActivity {
    ExpensesContext expensesContext;
    String itemName;
    EditText et;
    EditText etc;
    Spinner spn;
    DateTimeMaintainer dtm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expensesContext = new ExpensesContext();
        Bundle bundle = getIntent().getExtras();
        itemName = (String)bundle.getString("itemName");
        TextView tv=findViewById(R.id.itemName);
        tv.setText(itemName);
         et=findViewById(R.id.value);
         etc=findViewById(R.id.comment);
        //et.setText(0);
        spn=findViewById(R.id.spinner);spn=findViewById(R.id.spinner);
        dtm=ExpensesContext.getDtm();
        fillSpinner();
    }
    public void fillSpinner(){
        List<String> list=dtm.getAllMonths();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
        String curr=dtm.getCurrentMonth();
        ArrayAdapter myAdap = (ArrayAdapter) spn.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(curr);
        spn.setSelection(spinnerPosition);
    }
    public void addExpense(View v){
       // EditText et=(EditText)v.findViewById(R.id.value);
        String val=et.getText().toString();
        String comment= etc.getText().toString();

        try{
            int value=Integer.parseInt(val);
            String cur=spn.getSelectedItem().toString();
            expensesContext.insert(cur,itemName,value,comment);
            Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
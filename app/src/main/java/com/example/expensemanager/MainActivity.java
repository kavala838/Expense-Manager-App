package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import DateR.DateTimeMaintainer;
import Db.DBHandler;
import utilities.ExpensesContext;

public class MainActivity extends AppCompatActivity {
    DBHandler dbHandler;
    TextView tv;
    ExpensesContext expensesContext;
    DateTimeMaintainer dtm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("triggered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        Boolean firstStart=prefs.getBoolean("firstStart",true);
        dbHandler = new DBHandler(MainActivity.this);
        tv=findViewById(R.id.total);
        expensesContext=new ExpensesContext();
        ExpensesContext.setDBHandler(dbHandler);
         dtm = new DateTimeMaintainer();
        ExpensesContext.setDtm(dtm);


        if(firstStart){
            List<Integer> curr=dtm.getCurrentMonthYearNumber();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart", false);
            editor.putInt("startMonth",curr.get(0));
            editor.putInt("startYear",curr.get(1));
            editor.apply();
            dbHandler.createTypesTable();
        }
        dtm.setInstallDate(prefs.getInt("startMonth",dtm.getCurrentMonthYearNumber().get(0)),prefs.getInt("startYear",dtm.getCurrentMonthYearNumber().get(1)));
        int total=expensesContext.getTotal(dtm.getCurrentMonth());
        NumberFormat formatter= NumberFormat.getCurrencyInstance(new Locale("en","in"));
        String currency=formatter.format(total);
        tv.setText(currency);
    }
    @Override
    public void onStart(){
        super.onStart();
        int total=expensesContext.getTotal(dtm.getCurrentMonth());
        NumberFormat formatter= NumberFormat.getCurrencyInstance(new Locale("en","in"));
        String currency=formatter.format(total);
        tv.setText(currency);
    }
    public void naviagteToMainCategoryExpenses(View v){
        Intent i= new Intent(this,Expenses.class);
        startActivity(i);
    }
    public void naviagteToItemsList(View v){
        Intent i= new Intent(this,ItemsList.class);
        startActivity(i);
    }
    public void naviagteToChangesTypes(View v){
        Intent i= new Intent(this,choose_group_or_item.class);
        startActivity(i);
    }
    public void searchExpenses(View v){
        Intent i= new Intent(this,SearchExpenses.class);
        startActivity(i);
    }
    public void navigateToDownloads(View v){
        Intent i= new Intent(this,DownloadStatements.class);
        startActivity(i);
    }
}
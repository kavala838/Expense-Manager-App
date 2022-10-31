package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DateR.DateTimeMaintainer;
import utilities.ExpensesContext;

public class SearchExpenses extends AppCompatActivity {
    Spinner spnMain;
    Spinner spnBy;
    Spinner spnSearch;
    EditText tv;
    ExpensesContext expensesContext;
    LinearLayout ll;
    DateTimeMaintainer dtm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_expenses);
        expensesContext=new ExpensesContext();
        spnMain=findViewById(R.id.spinnerMain);
        spnBy=findViewById(R.id.spinnerBy);
        spnSearch=findViewById(R.id.spinnerSearch);
        tv=findViewById(R.id.textView);
        ll=findViewById(R.id.container);
        dtm=ExpensesContext.getDtm();
        spnMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spnBy.setSelection(0);
                ll.removeAllViews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ll.removeAllViews();
                if(spnBy.getSelectedItem().toString()=="byComment"){
                    tv.setVisibility(view.VISIBLE);
                    spnSearch.setVisibility(view.INVISIBLE);
                }
                else{
                    tv.setVisibility(view.INVISIBLE);
                    spnSearch.setVisibility(view.VISIBLE);
                    fillSearchSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fillSpinnerMain();
        fillBySpinner();
        spnBy.setSelection(0);
    }
    public void fillSpinnerMain(){
        List<String> list=dtm.getAllMonths();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMain.setAdapter(dataAdapter);
        String curr=dtm.getCurrentMonth();
        ArrayAdapter myAdap = (ArrayAdapter) spnMain.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(curr);
        spnMain.setSelection(spinnerPosition);
    }
    public void fillBySpinner(){
        ArrayList<String> list =new ArrayList<>();
        list.add("byGroup");
        list.add("byType");
        list.add("byComment");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBy.setAdapter(dataAdapter);
    }
    public void fillSearchSpinner(){
        String searchQuery=spnBy.getSelectedItem().toString();
        List<String> list=new ArrayList<String>();
        if(searchQuery.equals("byGroup")){
            list=expensesContext.getAllGroups();
        }
        if(searchQuery.equals("byType")){
            list=expensesContext.getAllItems();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSearch.setAdapter(dataAdapter);
    }
    public void get(View v){
        String table=spnMain.getSelectedItem().toString();
        String by=spnBy.getSelectedItem().toString();
        if(by.equals("byGroup")){
            fillLayout(expensesContext.getAllEntriesByGroup(spnSearch.getSelectedItem().toString(),table));
        }
        else if(by.equals("byType")){
            fillLayout(expensesContext.getAllEntriesByType(spnSearch.getSelectedItem().toString(),table));
        }
        else{
            fillLayout(expensesContext.getAllEntriesByComment(tv.getText().toString(),table));
        }
    }
    public void fillLayout(List<List<String>> lst) {
        ll.removeAllViews();
        for (List<String> l : lst) {
            View v = getLayoutInflater().inflate(R.layout.expense_card, null);
            TextView tvG = (TextView) v.findViewById(R.id.group);
            tvG.setText(l.get(0));
            tvG=(TextView) v.findViewById(R.id.item);
            tvG.setText(l.get(1));
            tvG=(TextView) v.findViewById(R.id.comment);
            tvG.setText(l.get(2));
            tvG=(TextView) v.findViewById(R.id.date);
            tvG.setText(l.get(3));
            tvG=(TextView) v.findViewById(R.id.value);
            tvG.setText(l.get(4));
            ll.addView(v);
        }
    }
}
package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import DateR.DateTimeMaintainer;
import utilities.ExpensesContext;

public class Expenses extends AppCompatActivity {
    LinearLayout ll;
    ExpensesContext expensesContext;
    Spinner spn;
    DateTimeMaintainer dtm;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        ll=findViewById(R.id.container);
        spn=findViewById(R.id.spinner);
        expensesContext = new ExpensesContext();
        dtm=ExpensesContext.getDtm();
        fillSpinner();
        fillLayout();
        tv=findViewById(R.id.total);
        int total=expensesContext.getTotal(spn.getSelectedItem().toString());
        NumberFormat formatter= NumberFormat.getCurrencyInstance(new Locale("en","in"));
        String currency=formatter.format(total);
        tv.setText(currency);
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
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillLayout();
                int total=expensesContext.getTotal(spn.getSelectedItem().toString());
                NumberFormat formatter= NumberFormat.getCurrencyInstance(new Locale("en","in"));
                String currency=formatter.format(total);
                tv.setText(currency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void fillLayout(){
        Context context=this;
        ll.removeAllViews();
        String selectedMonth=spn.getSelectedItem().toString();
        Map<String,String> ans=expensesContext.getGroupViews(selectedMonth);
        for (Map.Entry<String,String> entry : ans.entrySet()){
            View v=getLayoutInflater().inflate(R.layout.category_item_card,null);
            TextView tv=(TextView) v.findViewById(R.id.itemName);
            tv.setText(entry.getKey());

            TextView tv1=(TextView) v.findViewById(R.id.itemValue);
            tv1.setText(entry.getValue());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(context,ExpensesSub.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("groupName",entry.getKey());
                    bundle.putString("tableName",selectedMonth);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
            ll.addView(v);
        }
    }
}
package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import utilities.ExpensesContext;

public class ExpensesSub extends AppCompatActivity {
    LinearLayout ll;
    ExpensesContext expensesContext;
    String groupName;
    String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_sub);
        ll=findViewById(R.id.container);
        expensesContext = new ExpensesContext();
        Bundle bundle = getIntent().getExtras();
        groupName = (String)bundle.getString("groupName");
        tableName=(String)bundle.getString("tableName");
        fillLayout();
    }
    public void fillLayout(){
        ll.removeAllViews();
        Map<String,String> ans=expensesContext.getGroupItemViews(tableName,groupName);
        for (Map.Entry<String,String> entry : ans.entrySet()){
            View v=getLayoutInflater().inflate(R.layout.category_item_card,null);
            TextView tv=(TextView) v.findViewById(R.id.itemName);
            tv.setText(entry.getKey());

            TextView tv1=(TextView) v.findViewById(R.id.itemValue);
            tv1.setText(entry.getValue());

            ll.addView(v);
        }
    }
}
package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import utilities.ExpensesContext;

public class ItemsList extends AppCompatActivity {
    LinearLayout ll;
    ExpensesContext expensesContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ll=findViewById(R.id.container);
        expensesContext = new ExpensesContext();
        fillLayout();
    }

    public void fillLayout(){
        Context context=this;
        ll.removeAllViews();
        List<String> ans=expensesContext.getAllItems();
        for (String item:ans){
            View v=getLayoutInflater().inflate(R.layout.category_item,null);
            TextView tv=(TextView) v.findViewById(R.id.itemName);
            tv.setText(item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(context,addExpense.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("itemName",item);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
            ll.addView(v);
        }
    }
}
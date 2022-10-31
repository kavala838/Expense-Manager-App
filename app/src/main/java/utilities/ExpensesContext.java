package utilities;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DateR.DateTimeMaintainer;
import Db.DBHandler;
import ExpensesTypesData.ExpensesTypes;

public class ExpensesContext {
    public static DBHandler db;
    public static DateTimeMaintainer dtm;
    protected ExpensesTypes expensesTypes;
    public static void setDBHandler(DBHandler db1){
        db=db1;
    }
    public static DBHandler getDBHandler(){return db;}
    public static void setDtm(DateTimeMaintainer dtm1){dtm=dtm1;}
    public static DateTimeMaintainer getDtm(){return dtm;}

    public ExpensesContext(){
        expensesTypes=new ExpensesTypes();
    }

    public Map<String,String> getGroupViews(String table){
        Map<String,String> ans=new HashMap<String,String>();
        db.createTableIfNotExists(table);
        List<String> groups=expensesTypes.getGroups();
        for (String group:groups) {
         ans.put(group,Integer.toString(db.getValueOfAGroup(group,table)));
        }
        return ans;
    }

    public Map<String,String> getGroupItemViews(String table, String group){
        Map<String,String> ans=new HashMap<String,String>();
        db.createTableIfNotExists(table);
        List<String> items=expensesTypes.getItemsOfGroup(group);
        for (String item:items) {
            ans.put(item,Integer.toString(db.getValueOfAType(item,table)));
        }
        return ans;
    }

    public int getTotal(String table){
        int ans=0;
        List<String> groups=db.getAllGroups();
        for(String group:groups){
            ans=ans+db.getValueOfAGroup(group,table);
        }
        return ans;
    }

    public List<String> getAllItems(){
        return expensesTypes.getTypes();
    }
    public ArrayList<String> getAllGroups(){
        return expensesTypes.getGroups();
    }


    public String getGrpName(String item){
        return db.getGroupNameOfAItem(item);
    }

    public void insert(String table, String item, int value, String comment){
        String grp=getGrpName(item);
        String time=dtm.getCurrentTime();
        db.insert(table,grp,item,value,comment,time);
    }
    public void insertGroup(String group){
        db.insertGroup(group);
    }
    public void insertItem(String group, String item){
        db.insertExpenseItem(group,item);
    }
    public boolean isGroupPresent(String group){
        return db.getAllGroups().contains(group);
    }
    public boolean isItemPresent(String item){
        return getAllItems().contains(item);
    }

    public List<List<String>> getAllEntriesByGroup(String group, String table){
        return db.getAllEntriesByGroup(group,table);
    }
    public List<List<String>> getAllEntriesByType(String item, String table){
        return db.getAllEntriesByItem(item,table);
    }
    public List<List<String>> getAllEntriesByComment(String comment, String table){
        return db.getAllEntriesByGroup(comment,table);
    }
    public void deleteGroup(String group){
        db.deleteGroup(group);
    }
    public void deleteItem(String item){
        db.deleteItem(item);
    }
    public ArrayList<String> getItemsOfGroup(String group){
       return db.getItemsOfGroup(group);
    }
}

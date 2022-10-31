package Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "exp";
    private static final int DB_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createTableIfNotExists(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount()==0){
            query = "CREATE TABLE "+tableName+" (groupItem TEXT, type TEXT, date TEXT, value INTEGER, comment TEXT)";
            db.execSQL(query);
        }
    }

    public int getValueOfAGroup(String groupName, String tableName){
        int ans=0;
        createTableIfNotExists(tableName);
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(value) FROM "+tableName+" WHERE groupItem='"+groupName+"';";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
            ans = cursor.getInt(0);
        return ans;
    }

    public int getValueOfAType(String typeName, String tableName){
        createTableIfNotExists(tableName);
        int ans=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(value) FROM "+tableName+" WHERE type='"+typeName+"';";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
            ans = cursor.getInt(0);
        return ans;
    }

    public void insert(String table, String groupName, String typeName, int value, String comment, String time){
        createTableIfNotExists(table);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("groupItem",groupName);
        values.put("type",typeName);
        values.put("value",value);
        values.put("date",time);
        values.put("comment",comment);
        db.insert(table, null, values);
    }

    public void createTypesTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "CREATE TABLE GROUPS (groupItem TEXT)";
        db.execSQL(query);
        query ="CREATE TABLE TYPES (groupItem TEXT, item TEXT)";
        db.execSQL(query);
        insertGroup("Bills");
        insertExpenseItem("Bills","Electricity");
    }
    public void deleteGroup(String group){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from GROUPS where groupItem='"+group+"'";
        db.execSQL(query);
        query="DELETE FROM TYPES WHERE groupItem='"+group+"'";
        db.execSQL(query);
    }
    public void deleteItem(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from TYPES where item='"+item+"'";
        db.execSQL(query);
    }
    public ArrayList<String> getAllGroups(){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT groupItem FROM GROUPS";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);

                list.add(name);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getAllItems(){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT item FROM TYPES";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(0);

                list.add(name);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getItemsOfGroup(String group){
            ArrayList<String> list=new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String query="SELECT item FROM TYPES WHERE groupItem='"+group+"'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String name = cursor.getString(0);

                    list.add(name);
                    cursor.moveToNext();
                }
            }
            return list;
    }

    public String getGroupNameOfAItem(String item){
        String name="";
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT groupItem FROM TYPES WHERE item='"+item+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
                 name = cursor.getString(0);

        }
        return name;
    }

    public void insertGroup(String group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("groupItem",group);
        db.insert("GROUPS", null, values);
    }
    public void insertExpenseItem(String group, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("groupItem",group);
        values.put("item",item);
        db.insert("TYPES", null, values);
    }
    public List<List<String>> getAllEntriesByGroup(String group, String table){
        createTableIfNotExists(table);
        List<List<String>> ans=new ArrayList<List<String>>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT groupItem,type,comment,date,value FROM "+table+" WHERE groupItem='"+group+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                List<String> ll=new ArrayList<>();
                String name = cursor.getString(0);
                ll.add(name);
                name=cursor.getString(1);
                ll.add(name);
                name=cursor.getString(2);
                ll.add(name);
                name=cursor.getString(3);
                ll.add(name);
                int val=cursor.getInt(4);
                ll.add(String.valueOf(val));
                ans.add(ll);
                cursor.moveToNext();
            }
        }
        System.out.println(ans.size());
        return ans;
    }

    public List<List<String>> getAllEntriesByItem(String item, String table) {
        createTableIfNotExists(table);
        List<List<String>> ans = new ArrayList<List<String>>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT groupItem,type,comment,date,value FROM " + table + " WHERE type='" + item + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                List<String> ll = new ArrayList<>();
                String name = cursor.getString(0);
                ll.add(name);
                name = cursor.getString(1);
                ll.add(name);
                name = cursor.getString(2);
                ll.add(name);
                name = cursor.getString(3);
                ll.add(name);
                int val = cursor.getInt(4);
                ll.add(String.valueOf(val));
                ans.add(ll);
                cursor.moveToNext();
            }
        }
        return ans;
    }
    public List<List<String>> getAllEntriesByComment(String comment, String table) {
        createTableIfNotExists(table);
        List<List<String>> ans = new ArrayList<List<String>>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT groupItem,type,comment,date,value FROM " + table + " WHERE comment like '%" + comment + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                List<String> ll = new ArrayList<>();
                String name = cursor.getString(0);
                ll.add(name);
                name = cursor.getString(1);
                ll.add(name);
                name = cursor.getString(2);
                ll.add(name);
                name = cursor.getString(3);
                ll.add(name);
                int val = cursor.getInt(4);
                ll.add(String.valueOf(val));
                ans.add(ll);
                cursor.moveToNext();
            }
        }
        return ans;
    }
}


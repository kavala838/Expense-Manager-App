package ExpensesTypesData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Db.DBHandler;
import utilities.ExpensesContext;

public class ExpensesTypes {
    protected  Map<String, ArrayList<String>> types;
    protected  ArrayList<String> groups;
    DBHandler db;
    public ExpensesTypes(){
        db= ExpensesContext.getDBHandler();
        types=new HashMap<>();
        types.put("Bills",new ArrayList<String>(Arrays.asList("current bill", "recharges", "dish", "wifi","usefulls")));
        types.put("shopping", new ArrayList<String>(Arrays.asList("Clothings", "appliances")));
        types.put("Travelling",new ArrayList<String>(Arrays.asList("Bus","car","train","petrol")));
        types.put("Food",new ArrayList<String>(Arrays.asList("Persnol","Family","Friends","cc")));
        types.put("Home Supplies",new ArrayList<String>(Arrays.asList("Grocieries","Health","others")));
        types.put("Donations",new ArrayList<String>(Arrays.asList("God")));
        types.put("Investments",new ArrayList<String>(Arrays.asList("Mutual Funds","Equity")));
        types.put("Savings",new ArrayList<String>(Arrays.asList("Fi","Amma","Chit")));
        types.put("OTH",new ArrayList<String>(Arrays.asList("vk","ks","y","n","b","gifts")));
        types.put("Resp",new ArrayList<String>(Arrays.asList("Rishi","Dad","mom")));

        groups=new ArrayList<String>(Arrays.asList("Bills","shopping","Travelling","Food","Home Supplies","Donations","Investments","Savings","OTH","Resp"));
    }

    public ArrayList<String> getGroups(){
        return db.getAllGroups();
    }

    public ArrayList<String> getItemsOfGroup(String group){
        return db.getItemsOfGroup(group);
    }

    public ArrayList<String> getTypes(){
        return db.getAllItems();
    }
}

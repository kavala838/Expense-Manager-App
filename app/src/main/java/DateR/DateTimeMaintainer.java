package DateR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateTimeMaintainer {
    static String startMonth;
    static int startYear;
    static int startMonthInNumericals;
    Map<Integer,String> months;

    public  void setInstallDate(int month, int year){
        startMonth=months.get(month);
        startYear=year;
        startMonthInNumericals=month;
    }
    public String getCurrentTime(){
        Date now = new Date();
        String numericPattern ="MM-dd-yyyy";
        SimpleDateFormat numericDateFormat = new SimpleDateFormat(numericPattern);
        return numericDateFormat.format(now);
    }
    public DateTimeMaintainer(){


        months=new HashMap<Integer,String>();
        months.put(1,"JANUARY");
        months.put(2,"FEBRUARY");
        months.put(3,"MARCH");
        months.put(4,"APRIL");
        months.put(5,"MAY");
        months.put(6,"JUNE");
        months.put(7,"JULY");
        months.put(8,"AUGUST");
        months.put(9,"SEPTEMBER");
        months.put(10,"OCTOBER");
        months.put(11,"NOVEMBER");
        months.put(12,"DECEMBER");
    }

    public ArrayList<String> getAllMonths(){
        ArrayList<String> ans=new ArrayList<>();
        Date now=new Date();
        String stringPattern = "MMMM-dd-yyyy";
        String numericPattern ="MM-dd-yyyy";
        SimpleDateFormat StringDateFormat = new SimpleDateFormat(stringPattern);
        SimpleDateFormat numericDateFormat = new SimpleDateFormat(numericPattern);
        String dateNowString=StringDateFormat.format(now);
        String dateNowNumeric=numericDateFormat.format(now);
        String[] str=dateNowString.split("-");
        String currentMonth=str[0].toUpperCase();
        int currentYear=Integer.parseInt(str[2]);
        String[] num=dateNowNumeric.split("-");
        int currentMonthInNumericals=Integer.parseInt(num[0]);
        int y=startYear;
        int m=startMonthInNumericals;
        if(currentYear==y){
            fillAllMonthsBetween(y,m,currentMonthInNumericals,ans);
        }
        else{

            fillAllMonthsBetween(y,m,12,ans);

            y++;
            while(currentYear>y){
                fillAllMonthsBetween(y,1,12,ans);
                y++;
            }
            fillAllMonthsBetween(y,1,currentMonthInNumericals,ans);
        }
        return ans;
    }
    public void fillAllMonthsBetween(int year, int startM, int endM, List<String> ans){
        for(int i=startM;i<=endM;i++){
            String m=months.get(i);
            ans.add(m+"_"+year);
        }
    }
    public String getCurrentMonth(){
        Date now=new Date();
        String stringPattern = "MMMM-dd-yyyy";
        SimpleDateFormat StringDateFormat = new SimpleDateFormat(stringPattern);
        String dateNowString=StringDateFormat.format(now);
        String[] str=dateNowString.split("-");
        String currentMonth=str[0].toUpperCase();
        int currentYear=Integer.parseInt(str[2]);
        return currentMonth+"_"+currentYear;
    }
    public ArrayList<Integer> getCurrentMonthYearNumber(){
        ArrayList<Integer> ans=new ArrayList<>();
        Date now=new Date();
        String stringPattern = "MM-dd-yyyy";
        SimpleDateFormat StringDateFormat = new SimpleDateFormat(stringPattern);
        String dateNowString=StringDateFormat.format(now);
        String[] str=dateNowString.split("-");
        int currentMonth=Integer.parseInt(str[0]);
        int currentYear=Integer.parseInt(str[2]);
        ans.add(currentMonth);
        ans.add(currentYear);
        return ans;
    }
}

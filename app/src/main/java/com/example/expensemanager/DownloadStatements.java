package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import DateR.DateTimeMaintainer;
import utilities.ExpensesContext;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
public class DownloadStatements extends AppCompatActivity {
Spinner spn;
ExpensesContext expensesContext;
DateTimeMaintainer dtm;
    int pageHeight = 2520;
    int pagewidth = 792;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_statements);
        expensesContext=new ExpensesContext();
        spn=findViewById(R.id.spinner);
        dtm=ExpensesContext.getDtm();
        fillSpinner();
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
    }
    public void fillSpinner(){
        List<String> list=dtm.getAllMonths();
        list.remove(dtm.getCurrentMonth());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
    }
    public void download(View v){
        String table=spn.getSelectedItem().toString();
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(40);
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setStyle(Paint.Style.FILL);
        title.setUnderlineText(true);

        canvas.drawText(spn.getSelectedItem().toString(), 260, 100, title);

        title.setTextSize(30);
        title.setUnderlineText(false);
        title.setStyle(Paint.Style.FILL);

        canvas.drawText("Total- "+expensesContext.getTotal(spn.getSelectedItem().toString()),100,200,title);

        int x=100;
        int y=250;
        Map<String, String> map=expensesContext.getGroupViews(table);
        List<String> groups=expensesContext.getAllGroups();
        for(String group:groups) {
            title.setTextSize(20);
            canvas.drawText(group+": "+map.get(group),x,y,title);
            y=y+15;

            title.setTextSize(15);
            Map<String,String> ls=expensesContext.getGroupItemViews(table,group);
            for (Map.Entry<String,String> entry : ls.entrySet()){
                canvas.drawText(entry.getKey()+": "+entry.getValue(),x,y,title);
                y=y+15;
            }
            y=y+25;
        }
        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory()+"/documents", spn.getSelectedItem().toString()+".pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(DownloadStatements.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();


    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
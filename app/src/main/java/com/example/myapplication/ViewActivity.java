package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewActivity extends AppCompatActivity {

    private static final String dbName = "AccountBook.db";
    private static final String tableName = "Account_book";
    private static final String TAG_CHECKEXPENSE = "CHECK_EXPENSE";
    private static final String TAG_DATES = "DATES";
    private static final String TAG_ITEM = "ITEM";
    private static final String TAG_PRICE = "PRICE";


    ArrayList<Integer> dbIdstore = new ArrayList<Integer>();

    int tmpIntent = 0;

    ArrayList<HashMap<String, String>> personList;
    ListView list;
    TextView textView;

    SQLiteDatabase mDB = null;
    ListAdapter adapter;
    CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);



        list = (ListView) findViewById(R.id.view_db);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChangeActivity.class);
                tmpIntent = dbIdstore.get(i);
                intent.putExtra("id", tmpIntent);
                startActivity(intent);
                finish();
            }
        });

        personList = new ArrayList<HashMap<String, String>>();

        showList();
        calc_balance();

    }

    protected void showList() {

        SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex("id"));
                    String checkexpense = c.getString(c.getColumnIndex("check_expense"));
                    if (checkexpense.equals("2")) {
                        checkexpense = "支出";
                    } else {
                        checkexpense = "收入";
                    }
                    String dates = c.getString(c.getColumnIndex("dates"));
                    String item = c.getString(c.getColumnIndex("item"));
                    String price = c.getString(c.getColumnIndex("price"));

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put(TAG_CHECKEXPENSE, checkexpense);
                    persons.put(TAG_DATES, dates);
                    persons.put(TAG_ITEM, item);
                    persons.put(TAG_PRICE, price);

                    dbIdstore.add(Integer.parseInt(id));

                    personList.add(persons);

                } while (c.moveToNext());
            }
        }

        ReadDB.close();

        adapter = new SimpleAdapter(
                this, personList, R.layout.list_item,
                new String[]{TAG_CHECKEXPENSE, TAG_DATES, TAG_ITEM, TAG_PRICE},
                new int[]{R.id.checkexpenseview, R.id.dateview, R.id.itemview, R.id.priceview}
        );

        list.setAdapter(adapter);
    }

    protected void calc_balance(){

        int priceTemp = 0;
        int tmp;

        SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        Cursor c = ReadDB.rawQuery("SELECT check_expense, price FROM " + tableName, null);

        if(c != null) {

            if(c.moveToFirst()){
                do{
                    String checkexpense = c.getString(c.getColumnIndex("check_expense"));
                    String price = c.getString(c.getColumnIndex("price"));
                    tmp = Integer.parseInt(price);
                    if(checkexpense.equals("2")){
                        tmp = -tmp;
                    }

                    priceTemp = priceTemp + tmp;

                }while(c.moveToNext());
            }
        }

        TextView textView = (TextView)findViewById(R.id.show_balance);
        textView.setText(String.valueOf(priceTemp));
    }

}

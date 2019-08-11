package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class InputActivity extends AppCompatActivity {

    TextView textView_Date;
    DatePickerDialog.OnDateSetListener callbackMethod;
    RadioGroup radioGroup;
    RadioButton radioButton;
    SQLiteHelper mSQLiteHelper;
    SQLiteDatabase mDb;

    int selectNumber = 2;
    int year = -1;
    int month = -1;
    int day = -1;
    int moneyTemp = 0;
    String itemTemp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        year = Integer.parseInt(yearFormat.format(currentTime));
        month = Integer.parseInt(monthFormat.format(currentTime));
        day = Integer.parseInt(dayFormat.format(currentTime));

        mSQLiteHelper = new SQLiteHelper(this);

        this.InitializeView();
        this.InitializeListener();
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio_input){
                    selectNumber = 1;   // 수입 == 1
                }
                else{
                    selectNumber = 2;   // 지출 == 2
                }
            }
        });
    }

    public void InitializeView() {
        textView_Date = findViewById(R.id.textView_date);
    }

    public void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
                textView_Date.setText(getString(R.string.showdate, year,month,day));
            }
        };
    }

    public void saveButtonClicked(View v) {
        EditText money = (EditText)findViewById(R.id.money);
        moneyTemp = Integer.parseInt(money.getText().toString());

        EditText item = (EditText)findViewById(R.id.item);
        itemTemp = item.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("保存しますか");
        builder.setNegativeButton("いいえ",null);
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDb = mSQLiteHelper.getWritableDatabase();
                mDb.execSQL("INSERT INTO Account_book (check_expense, dates, item, price) VALUES (" + selectNumber + ", '" + year + "-" + month + "-" + day + "', '" + itemTemp + "', " + moneyTemp +");");
                mDb.close();
                Toast.makeText(getApplicationContext(),"保存されました",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.show();
    }

    public void cancelButtonClicked(View v) {
        moveMainActivity();
    }

    public void onBackPressed() {
        moveMainActivity();
    }

    public void moveMainActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("初期画面に戻りますか");
        builder.setNegativeButton("いいえ",null);
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    public void selectDate(View v) {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, year, month-1, day);
        dialog.show();
    }

}

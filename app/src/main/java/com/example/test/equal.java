package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class equal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equal);

        //ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Show button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //declare
        Button btn_submit = findViewById(R.id.btn_submit);
        EditText ta = findViewById(R.id.ta_et);
        EditText np = findViewById(R.id.np_et);

        //Listener
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input1 = ta.getText().toString();
                String input2 = np.getText().toString();
                double total_number = 0;
                int total_person = 0;
                try {
                    total_number = Double.parseDouble(input1);
                    total_person = Integer.parseInt(input2);
                    double amount_per = total_number / total_person;
                    //Convert the result in two decimal format
                    List<PersonList> personList = new ArrayList<>();
                    personList.add(new PersonList(amount_per,0));
                    String total_amount = String.format("%.2f",total_number);
                    //Pass the data to activity_result
                    Intent intent = new Intent(getApplicationContext(), result.class);
                    intent.putExtra("TOTAL_AMOUNT", total_amount);
                    intent.putExtra("TOTAL_PERSON",String.valueOf(total_person));
                    intent.putExtra("PERSON_LIST",(Serializable) personList);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Function of back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}


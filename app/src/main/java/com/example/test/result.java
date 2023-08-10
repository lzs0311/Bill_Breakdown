package com.example.test;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Show button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Body
        //Declare
        LinearLayout result = findViewById(R.id.ll);
        Intent intent = getIntent();
        //Get data from last activity
        String total_amount = intent.getStringExtra("TOTAL_AMOUNT");
        String total_person = intent.getStringExtra("TOTAL_PERSON");
        List<PersonList> personList = (List<PersonList>) intent.getSerializableExtra("PERSON_LIST");
        TextView tv_ta = findViewById(R.id.tv_ta);
        TextView tv_np = findViewById(R.id.tv_np);
        tv_ta.setText("RM "+ total_amount);
        tv_np.setText(total_person);
        if (!personList.isEmpty()) {
            for (int i = 0; i < personList.size(); i++) {
                TextView tv = new TextView(this);
                tv.setTextSize(18.0f);
                tv.setTextColor(Color.BLACK);
                tv.setLineSpacing(18.0f,1.0f);
                tv.setText(personList.get(i).toString());
                if(personList.get(i).getMode() == 3)
                {
                    if(personList.get(i).getAmount() != 0)
                    {
                        tv.setTextColor(Color.RED);
                    }
                }
                result.addView(tv);
            }
        }
        //Button
        Button btn_home = findViewById(R.id.btn_home);
        Button btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                if(personList.get(personList.size() - 1).getMode() == 0) {
                    intent.putExtra(Intent.EXTRA_TEXT, getEqualResult(total_amount,total_person,personList));
                }
                else{
                    intent.putExtra(Intent.EXTRA_TEXT,getCustomResult(total_amount,total_person,personList));
                }
                intent.setType("text/plain");

                if(intent.resolveActivity((getPackageManager()))!= null){
                    startActivity(intent);
                }
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(result.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Function of back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
    private String getEqualResult(String total_amount, String total_person, List<PersonList> personList){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total amount : " + total_amount
                + "\nNumber of person : " + total_person
                + "\nAmount per person : " + personList.get(personList.size() - 1).getNumber());
        return stringBuilder.toString();
    }

    private String getCustomResult(String total_amount, String total_person, List<PersonList> personList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total amount : " + total_amount
                + "\nNumber of person : " + total_person
                + "\nDetails per person:\n");
        for (int i = 0; i < personList.size(); i++) {
            String temp = personList.get(i).toString();
            stringBuilder.append(temp).append("\n\n"); // Separate items with newlines
        }
        return stringBuilder.toString();
    }
}
package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Person;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class custom extends AppCompatActivity {

    private TextView tx_cnp;
    private int mode = 1;
    private int times = 1;
    private final float size = 18.0f;
    private final int color = Color.BLACK;
    private List<PersonList> personList = new ArrayList<>();
    private List<PersonList> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        //-------------------AppBar-------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Show button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-------------------Body-------------------
        //Declare
        mode = 1;
        tx_cnp = findViewById(R.id.tx_cnp);


        //Button
        Button btn_p = findViewById(R.id.btn_p);
        Button btn_r = findViewById(R.id.btn_r);
        Button btn_a = findViewById(R.id.btn_a);
        Button btn_plus = findViewById(R.id.btn_plus);
        Button btn_minus = findViewById(R.id.btn_minus);
        Button btn_submit = findViewById(R.id.btn_submit2);

        //Declare the title of the mode 1 which is percentage
        LinearLayout show = findViewById(R.id.show);
        addTitle(show);
        //Create two default linear layout match to the editTextNumber
        int number = Integer.parseInt(tx_cnp.getText().toString());
        for (int i = 0; i < number; i++) {
            createLinearLayout(show);
        }
        //Function
        btn_plus.setOnClickListener(v -> {
            //create new line
            times = 1;
            createLinearLayout(show);
        });
        btn_minus.setOnClickListener(v -> {
            //remove last line
            times = 1;
            removeLinearLayout(show);
        });
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 1;
                times = 1;
                removeAllLinearLayout(show);
                addTitle(show);
                int number = Integer.parseInt(tx_cnp.getText().toString());
                for (int i = 0; i < number; i++) {
                    createLinearLayout(show);
                }
            }
        });
        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 2;
                times = 1;
                removeAllLinearLayout(show);
                addTitle(show);
                int number = Integer.parseInt(tx_cnp.getText().toString());
                for (int i = 0; i < number; i++) {
                    createLinearLayout(show);
                }
            }
        });
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 3;
                times = 1;
                removeAllLinearLayout(show);
                addTitle(show);
                int number = Integer.parseInt(tx_cnp.getText().toString());
                for (int i = 0; i < number; i++) {
                    createLinearLayout(show);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_ta = findViewById(R.id.et_ta);
                // Clear the 'temp' to avoid duplicates
                if(times == 1)
                {
                    personList.clear();
                }
                temp.clear();
                //Check and get the value
                if(!empty(show)){
                    double total_amount = 0, result = 0;
                    int total_person = 0;
                    try {
                        total_amount = Double.parseDouble(et_ta.getText().toString());
                        total_person = Integer.parseInt(tx_cnp.getText().toString());
                        //Calculate the result
                        switch (mode) {
                            case 1:
                                //Count by percentage
                                if(!checkPercentage()){
                                    Toast.makeText(getApplicationContext(), "The percentage not equal 100%", Toast.LENGTH_SHORT).show();
                                }else{
                                    for(int i = 0 ; i < personList.size();i++){
                                        result = total_amount * (personList.get(i).getNumber()/100);
                                        personList.get(i).setAmount(result);
                                        personList.get(i).setMode(1);

                                    }
                                    goToResult(total_amount, total_person);
                                }
                                break;
                            case 2:
                                //Count by ratio
                                double total_ratio = 0;
                                for (int i = 0; i < personList.size(); i++) {
                                    total_ratio += personList.get(i).getNumber();
                                }
                                for (int i = 0; i < personList.size(); i++) {
                                    result = personList.get(i).getNumber() / total_ratio * total_amount;
                                    personList.get(i).setAmount(result);
                                    personList.get(i).setMode(2);
                                }
                                goToResult(total_amount, total_person);
                                break;
                            case 3:
                                //Count by amount
                                double sum_amount = 0;
                                if (times == 1) {
                                    for (int i = 0; i < personList.size(); i++) {
                                        sum_amount += personList.get(i).getNumber();
                                    }
                                    if (sum_amount == total_amount) {
                                        for (int i = 0; i < personList.size(); i++) {
                                            personList.get(i).setAmount(0);
                                            personList.get(i).setMode(3);
                                        }
                                        goToResult(total_amount, total_person);
                                    } else {
                                        String toast = "Not equal to total amount"
                                                + "\nStill add / deduct RM" + String.format("%.2f",total_amount-sum_amount) ;
                                        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                                        times++;
                                    }
                                } else {
                                    for (int i = 0; i < temp.size(); i++) {
                                        sum_amount += temp.get(i).getNumber();
                                    }
                                    if (sum_amount == total_amount && temp.size() == personList.size()) {
                                        for (int i = 0; i < temp.size(); i++) {
                                            result = temp.get(i).getNumber() - personList.get(i).getNumber();
                                            personList.get(i).setAmount(result);
                                            personList.get(i).setMode(3);
                                        }
                                        goToResult(total_amount, total_person);
                                    } else {
                                        String toast = "Not equal to total amount"
                                                + "\nStill add / deduct RM" + String.format("%.2f",total_amount-sum_amount) ;
                                        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                                        times++;
                                    }
                                }
                                break;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Please enter the total amount", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //-----------------Attribute-------------------
    public LinearLayout.LayoutParams layoutParams(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        return layoutParams;
    }

    //-----------------Widget---------------------
    public TextView tv1(){
        TextView tv1 = new TextView(this);
        tv1.setText(R.string.name);
        tv1.setTextSize(size);
        tv1.setTextColor(color);
        tv1.setGravity(Gravity.CENTER);
        return tv1;
    }

    public TextView tv2(){
        TextView tv2 = new TextView(this);
        tv2.setTextColor(color);
        tv2.setTextSize(size);
        tv2.setGravity(Gravity.CENTER);

        switch (mode) {
            case 2:
                tv2.setText(R.string.r);
                break;
            case 3:
                tv2.setText(R.string.amount);
                break;
            default:
                tv2.setText(R.string.p);
                break;
        }
        return tv2;
    }

    public EditText et1(){
        EditText et1 = new EditText(this);
        et1.setHint(R.string.name);
        et1.setTextSize(size);
        et1.setTextColor(color);
        et1.setText(R.string.unknown);
        return et1;
    }

    public EditText et2(){
        EditText et2 = new EditText(this);
        et2.setTextSize(size);
        et2.setTextColor(color);
        et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        switch (mode){
            case 1:
                et2.setHint(R.string.et_1);
                break;
            case 2:
                et2.setHint(R.string.et_2);
                et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 3:
                et2.setHint(R.string.et_3);
        }
        return et2;
    }

    //-------------------AppBar-------------------
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    //-------------------Body-------------------
    // Method to dynamically create EditText widgets

    private void addTitle(@NonNull LinearLayout show){
        LinearLayout horizontal = new LinearLayout(this);
        horizontal.addView(tv1(),layoutParams());
        horizontal.addView(tv2(),layoutParams());
        show.addView(horizontal);
    }

    private void createLinearLayout (@NonNull LinearLayout show) {
        LinearLayout horizontal = new LinearLayout(this);
        horizontal.addView(et1(),layoutParams());
        horizontal.addView(et2(),layoutParams());
        show.addView(horizontal);
        tx_cnp.setText(String.valueOf(show.getChildCount() - 1));
    }

    private void removeLinearLayout (@NonNull LinearLayout show) {
        if (show.getChildCount() >  3) {
            show.removeViewAt(show.getChildCount() - 1);
            tx_cnp.setText(String.valueOf(show.getChildCount() - 1));
        }
    }

    //remove linear layout when switch to other
    private void removeAllLinearLayout(@NonNull LinearLayout show){
        int loop = show.getChildCount();
        for(int k = 0; k < loop; k++){
            show.removeViewAt(show.getChildCount() - 1);
        }
        tx_cnp.setText("2");
    }

    private boolean empty(@NonNull LinearLayout show){
        //Get value from the et1 & et2
        // Iterate through the 'show' (LinearLayout)
        // 0 is the title
        for (int i = 1; i < show.getChildCount(); i++) {
            View childView = show.getChildAt(i);
            //Ensure the childView is LinearLayout
            if (childView instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) childView;
                //Ensure the linearLayout has total two child
                if (linearLayout.getChildCount() == 2) {
                    //Get position on the first child of linearLayout
                    View et1View = linearLayout.getChildAt(0);
                    //Ensure the et1View is EditText
                    if (et1View instanceof EditText) {
                        EditText et1 = (EditText) et1View;
                        String name = et1.getText().toString().trim();
                        //Get position on the first child of linearLayout
                        View et2View = linearLayout.getChildAt(1);
                        //Ensure the et2View is EditText
                        if (et2View instanceof EditText) {
                            EditText et2 = (EditText) et2View;
                            String input = et2.getText().toString().trim();
                            if (!name.isEmpty() && !input.isEmpty()) {
                                try {
                                double inputValue = Double.parseDouble(input);
                                if(times == 1){
                                personList.add(new PersonList(name, inputValue));
                                } else {
                                    temp.add(new PersonList(name, inputValue));
                                }
                                }catch (NumberFormatException e){
                                    Toast.makeText(getApplicationContext(), "Please the value", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Please fill both name and value", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkPercentage(){
        double totalPercentage = 0;
        for(int i = 0; i < personList.size();i++){
            totalPercentage += personList.get(i).getNumber();
        }
        return totalPercentage == 100;
    }

    private void goToResult(double total_amount, int total_person){

        Intent intent = new Intent(custom.this, result.class);

        //Pass the existing data to activity_result
        intent.putExtra("TOTAL_AMOUNT",String.valueOf(total_amount));
        intent.putExtra("TOTAL_PERSON",String.valueOf(total_person));
        intent.putExtra("PERSON_LIST",(Serializable) personList);
        startActivity(intent);

    }
}

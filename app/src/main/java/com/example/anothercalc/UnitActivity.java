package com.example.anothercalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class UnitActivity extends AppCompatActivity {

    String[] typesOfUnits = {"Distance", "Speed", "Mass", "Volume"};

    Spinner types;
    Spinner firstUnit;
    Spinner secondUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ArrayAdapter<CharSequence> type = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
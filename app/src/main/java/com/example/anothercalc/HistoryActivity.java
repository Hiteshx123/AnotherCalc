package com.example.anothercalc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    ListView history;

    Button clearHistory;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context = getApplicationContext();
        history = findViewById(R.id.history);
        clearHistory = findViewById(R.id.button23);
        history.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getIntent().getStringArrayListExtra("History")){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView= view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        });

        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(parent.getContext(), MainActivity.class);
                mainIntent.putExtra("History", getIntent().getStringArrayListExtra("History"));
                mainIntent.putExtra("CurrentData", ((String)history.getItemAtPosition(position)).split("\n")[1]);
                startActivity(mainIntent);
            }
        });

        history.setLongClickable(true);

        history.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.putExtra("History", getIntent().getStringArrayListExtra("History"));
                mainIntent.putExtra("CurrentData", ((String)history.getItemAtPosition(i)).split("\n")[0]);
                startActivity(mainIntent);
                return true;
            }
        });

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}

package com.bgsp.activitiesandintents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String dataFromMain = getIntent().getStringExtra("mainData");

        TextView dataText = findViewById(R.id.data_txt);
        dataText.setText(dataFromMain);
    }
}

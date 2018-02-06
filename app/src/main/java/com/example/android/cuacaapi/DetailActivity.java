package com.example.android.cuacaapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ASUS on 2/4/2018.
 */

public class DetailActivity extends AppCompatActivity {

    //TODO: 23
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
        TextView detailDate = (TextView) findViewById(R.id.detail_date);
        TextView detailTemp = (TextView) findViewById(R.id.detail_temp);
        TextView detailWeather = (TextView) findViewById(R.id.detail_weather);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();

            detailDate.setText(bundle.getString("date"));
            detailTemp.setText(bundle.getString("temp"));
            detailWeather.setText(bundle.getString("weather"));

            //TODO: 24
            if (detailWeather.getText().toString().equals("clear")) {
                detailImage.setBackgroundResource(R.drawable.art_clear);
            } else if (detailWeather.getText().toString().equals("clouds")) {
                detailImage.setBackgroundResource(R.drawable.art_clouds);
            } else if (detailWeather.getText().toString().equals("light_clouds")) {
                detailImage.setBackgroundResource(R.drawable.art_light_clouds);
            } else{
                detailImage.setBackgroundResource(R.drawable.art_light_rain);
            }
        } else{
            Toast.makeText(this,"Data tidak tersedia.", Toast.LENGTH_LONG).show();
        }
    }
}

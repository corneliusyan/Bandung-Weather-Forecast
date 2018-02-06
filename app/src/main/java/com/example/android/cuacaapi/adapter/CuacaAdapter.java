package com.example.android.cuacaapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.cuacaapi.R;
import com.example.android.cuacaapi.entity.Cuaca;

import java.util.List;

/**
 * Created by ASUS on 2/4/2018.
 */

public class CuacaAdapter extends ArrayAdapter<Cuaca> {

    //TODO: 8
    private final AppCompatActivity context;
    private final List<Cuaca> cuaca;

    //TODO: 9
    // Constructor kelas (pake alt + insert constructor)
    public CuacaAdapter(AppCompatActivity context1, List<Cuaca> cuaca) {
        super(context1, R.layout.items,cuaca);
        this.context = context1;
        this.cuaca = cuaca;
    }

    //TODO: 10
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.items,null,true);

        TextView dateText = (TextView) rowView.findViewById(R.id.date);
        TextView tempText = (TextView) rowView.findViewById(R.id.temp);
        TextView weatherText = (TextView) rowView.findViewById(R.id.weather);

        dateText.setText(cuaca.get(position).getDate());
        tempText.setText(cuaca.get(position).getTemp());
        weatherText.setText(cuaca.get(position).getWeather());

        return rowView;
    }
}

package com.example.android.cuacaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.cuacaapi.adapter.CuacaAdapter;
import com.example.android.cuacaapi.entity.Cuaca;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: 12
// tulis implements swiperefreshlayout.onrefreshlistener
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    //TODO: 11
    // buat alertdialog+list cuaca + json url
    private AlertDialog.Builder alert;
    private List<Cuaca> cuaca;

    //TODO: 4.1
    private SwipeRefreshLayout refresh;
    private ListView listView;
    private String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&APPID=2939b4f9a70e7dd25e181b06ab14bc5d&mode=json&units=metric&cnt=17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 4.2
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        listView = (ListView) findViewById(R.id.main_list);

        //TODO: 20.5
        alert = new AlertDialog.Builder(this);
        cuaca = new ArrayList<Cuaca>();

        //TODO: 21
        //Jalankan refresh ketika pertama kali app dibuka
        refresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) MainActivity.this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                cuaca.clear();
                RequestParams params = new RequestParams();
                requestWeather(params);
            }
        });

        //TODO: 22
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("date",cuaca.get(position).getDate());
                bundle.putString("temp",cuaca.get(position).getTemp());
                bundle.putString("weather",cuaca.get(position).getWeather());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    //TODO: 13
    // onrefresh = apa yang dilakuin program saat melakukan refresh (ketika app di swipe kebawah banget)
    @Override
    public void onRefresh() {
        refresh.setRefreshing(true);
        cuaca.clear();
        //request params yaitu suatu fungsi untuk nge request JSON nya lagi
        RequestParams params = new RequestParams();
        requestWeather(params);
    }

    //TODO: 14
    private String convertDateTime(long dateTime) {
        Date date = new Date(dateTime * 1000);
        // EEE = hari dalam 3 huruf (Sun), dd itu tanggal 2 digit, MMM itu month 3 huruf (Jan)
        Format dateTimeFormat = new SimpleDateFormat("EEE,dd MMM");

        return dateTimeFormat.format(date);
    }

    //TODO: 15
    // asynhttp client untuk ...... ,  refresh.setrefreshing buat tiap x manggil requestWeather (termasuk ketika buka app) ngelakuin refresh
    public void requestWeather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        refresh.setRefreshing(true);

        //TODO: 16
        client.get(JSON_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                refresh.setRefreshing(false);

                //TODO: 17
                Log.d("LOG", "Response: " + response);

                //TODO: 18
                try {
                    JSONObject weather = new JSONObject(response);
                    JSONArray listWeather = weather.getJSONArray("list");

                    for (int i = 0; i < listWeather.length(); i++) {
                        JSONObject weatherData = listWeather.getJSONObject(i);
                        String dt = convertDateTime(weatherData.getLong("dt"));

                        JSONObject temp = weatherData.getJSONObject("temp");
                        String t = String.valueOf(temp.getInt("day")) + " C";

                        JSONArray main = weatherData.getJSONArray("weather");
                        String w = main.getJSONObject(0).getString("main");

                        //Masukan ke list cuaca
                        cuaca.add(new Cuaca(dt, t, w));
                    }
                    CuacaAdapter adapter = new CuacaAdapter(MainActivity.this, cuaca);
                    listView.setAdapter(adapter);
                    //TODO: 19
                    // Catch itu dilakukan jika error
                } catch (Exception e) {
                    //Request timeout(RTO) sistem membaca ada koneksi tapi tidak tersambung internet
                    e.printStackTrace();
                    alert.setTitle("Terjadi Kesalahan.");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("Request Time Out");
                    alert.show();
                }
            }

            //TODO: 20
            //Kalau benar" Fail
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                refresh.setRefreshing(false);

                //404 jika server udah masuk web nya, tapi gada page yg dituju
                if (statusCode == 404) {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("404 Not Found.");
                    alert.show();
                }
                //error 500 kalau servernya yang rusak
                else if (statusCode == 500) {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("500 Internal Server Error.");
                    alert.show();
                } else {
                    alert.setTitle("Terjadi Kesalahan");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setMessage("No Internet Connection.");
                    alert.show();
                }
            }
        });
    }
}

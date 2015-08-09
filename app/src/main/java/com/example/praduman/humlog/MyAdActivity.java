package com.example.praduman.humlog;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdActivity extends ActionBarActivity {

    private String username;
    private HumLogController humLogController;
    private ListView myAdListView;
    private ArrayList<String> tradeList;
    private ArrayList<String> cityList;
    private ArrayList<String> adList;
    private String [] trade;
    private String [] city;
    private String [] ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad);
        setWindow();
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        username = getIntent().getStringExtra("username");
        myAdListView = (ListView) findViewById(R.id.myAdsListView);
        setAdRowData();
        myAdListView.setAdapter(new myAdAdapter(this,trade ,city  ,ad ) );
    }

    private void setWindow(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
    }

    private void setAdRowData(){
        // do something with controller


        trade=tradeList.toArray(new String[tradeList.size()]);
        city= cityList.toArray(new String[cityList.size()]);
        ad=adList.toArray(new String[adList.size()]);
    }


    /**
     * The codes for two inner classes (myAdRow and myAdAdapter) below are taken from a you tube video,
     * having the link https://www.youtube.com/watch?v=_l9e2t4fcfM .
     */
    class myAdRow{
        String trade;
        String city;
        String ad;

        public myAdRow(String trade , String city , String ad){
            this.trade = trade;
            this.city = city;
            this.ad = ad;
        }
    }


    class myAdAdapter extends BaseAdapter{

        ArrayList<myAdRow> list;
        Context context;

        public myAdAdapter(Context context ,String [] trade , String [] city , String [] ad){
            this.context = context;
            list = new ArrayList<myAdRow>();
            for(int i =0; i< trade.length; i++){
                list.add(new myAdRow(trade[i] , city[i] , ad[i]));
            }
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View adRow = inflater.inflate(R.layout.my_ad_row_view , parent , false);

            TextView myAdTrade = (TextView) adRow.findViewById(R.id.myAdRowTrade);
            TextView myAdCity = (TextView) adRow.findViewById(R.id.myAdRowCity);
            TextView myAdRowAdText = (TextView) adRow.findViewById(R.id.myAdRowAdText);

            myAdRow temp = list.get(position);
            myAdTrade.setText(temp.trade);
            myAdCity.setText(temp.city);
            myAdRowAdText.setText(temp.ad);

            return adRow;
        }
    }
}

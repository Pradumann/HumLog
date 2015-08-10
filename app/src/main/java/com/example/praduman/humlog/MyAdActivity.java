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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyAdActivity extends ActionBarActivity {

    private String username;
    private HumLogController humLogController;
    private ListView myAdListView;
    private List<String> tradeList;
    private List<String> cityList;
    private List<String> adList;
    private String [] trade;
    private String [] city;
    private String [] ad;
    private String [] button;
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
        myAdListView.setAdapter(new myAdAdapter(this,trade ,city  ,ad , button ) );
    }

    private void setWindow(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
    }

    private void setAdRowData(){

        tradeList = humLogController.getTradeList(username);
        cityList = humLogController.getCityList(username);
        adList = humLogController.getAdList(username);

        trade=tradeList.toArray(new String[tradeList.size()]);
        city= cityList.toArray(new String[cityList.size()]);
        ad=adList.toArray(new String[adList.size()]);
        button = new String[tradeList.size()];
        for(int i =0; i<tradeList.size(); i++){
            int position = i+1;
            button[i] = "Delete Ad " + position;
        }
    }


    /**
     * The codes for two inner classes (myAdRow and myAdAdapter) below are taken from a you tube video,
     * having the link https://www.youtube.com/watch?v=_l9e2t4fcfM .
     */
    class myAdRow{
        String trade;
        String city;
        String ad;
        String button;

        public myAdRow(String trade , String city , String ad , String button){
            this.trade = trade;
            this.city = city;
            this.ad = ad;
            this.button = button;
        }
    }


    class myAdAdapter extends BaseAdapter{

        ArrayList<myAdRow> list;
        Context context;

        public myAdAdapter(Context context ,String [] trade , String [] city , String [] ad , String [] button){
            this.context = context;
            list = new ArrayList<myAdRow>();
            for(int i =0; i< trade.length; i++){
                list.add(new myAdRow(trade[i] , city[i] , ad[i] , button[i]));
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View adRow = inflater.inflate(R.layout.my_ad_row_view , parent , false);

            TextView myAdTrade = (TextView) adRow.findViewById(R.id.myAdRowTrade);
            TextView myAdCity = (TextView) adRow.findViewById(R.id.myAdRowCity);
            TextView myAdRowAdText = (TextView) adRow.findViewById(R.id.myAdRowAdText);
            Button deleteButton = (Button) adRow.findViewById(R.id.myAdRowDeleteAdButton);

            myAdRow temp = list.get(position);
            myAdTrade.setText(temp.trade);
            myAdCity.setText(temp.city);
            myAdRowAdText.setText(temp.ad);
            deleteButton.setText(temp.button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDeleteButtonActionListener(position);
                }
            });
            return adRow;
        }
    }

    private void setDeleteButtonActionListener(int position){
        humLogController.deleteAdvertisement(username , position);
        Toast.makeText(this , "Advertisement "+ position+1 + "has been deleted" , Toast.LENGTH_LONG).show();
        this.finish();
    }
}

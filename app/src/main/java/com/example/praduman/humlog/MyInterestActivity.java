package com.example.praduman.humlog;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyInterestActivity extends ActionBarActivity {

    private String username;
    private String userType;
    private HumLogController humLogController;
    private List<String> myInterestUsernameList;
    private List<String> otherInterestUsernameList;
    private List<String> myInterestFirstNameList;
    private List<String> myInterestLastNameList;
    private List<String> myInterestStreetList;
    private List<String> myInterestLocalityList;
    private List<String> myInterestCityList;
    private List<String> myInterestPostCodeList;
    private List<String> myInterestNumberList;
    private List<String> otherInterestFirstNameList;
    private List<String> otherInterestLastNameList;
    private List<String> otherInterestStreetList;
    private List<String> otherInterestLocalityList;
    private List<String> otherInterestCityList;
    private List<String> otherInterestPostCodeList;
    private List<String> otherInterestNumberList;
    private String[] myInterestFirstName;
    private String[] myInterestLastName;
    private String[] myInterestStreet;
    private String[] myInterestLocality;
    private String[] myInterestCity;
    private String[] myInterestPostCode;
    private String[] myInterestNumber;
    private String[] otherInterestFirstName;
    private String[] otherInterestLastName;
    private String[] otherInterestStreet;
    private String[] otherInterestLocality;
    private String[] otherInterestCity;
    private String[] otherInterestPostCode;
    private String[] otherInterestNumber;
    private TextView myInterestMyInterestText;
    private TextView myInterestOtherInterestText;
    private ListView myInterestList;
    private ListView otherInterestList;
    private Button myInterestsRateButton;
    private Button otherInterestRateButton;
    private Button myInterestsDeleteButton;
    private Button otherInterestDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_interest);
        setDetails();
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        if(userType.equalsIgnoreCase("customer")){
            setTradeProfileInterests();
        }else {
            setCustomerInterests();
        }
    }

    private void setDetails(){
        username = getIntent().getStringExtra("username");
        userType = getIntent().getStringExtra("userType");
        myInterestMyInterestText = (TextView) findViewById(R.id.myInterestMyInterestText);
        myInterestOtherInterestText = (TextView) findViewById(R.id.myInterestOtherInterestText);
        myInterestList = (ListView) findViewById(R.id.myInterestMyInterestList);
        otherInterestList = (ListView) findViewById(R.id.myInterestOtherInterestList);
    }

    private void setTradeProfileInterests(){
        myInterestUsernameList = new ArrayList<String>();
        otherInterestUsernameList = new ArrayList<String>();
        myInterestUsernameList = humLogController.getCustomerRelationTableList(username , userType);
        otherInterestUsernameList = humLogController.getTradesmanRelationTableList(username , userType);
        if(myInterestUsernameList == null || myInterestUsernameList.isEmpty()){
            myInterestMyInterestText.setText("Your do not have any interests");
        }else {
            myInterestMyInterestText.setText("Tradesman you showed interest in");
            setMyInterestAdapter(myInterestUsernameList);
        }
        if(otherInterestUsernameList == null || otherInterestUsernameList.isEmpty()){
            myInterestOtherInterestText.setText("No Tradesman shown interest in your AD");
        }else {
            myInterestOtherInterestText.setText("Tradesman which shown interest in you");
            setOtherInterestAdapter(otherInterestUsernameList);
        }
    }

    private void setCustomerInterests(){

        myInterestUsernameList = new ArrayList<String>();
        otherInterestUsernameList = new ArrayList<String>();
        otherInterestUsernameList = humLogController.getCustomerRelationTableList(username , userType);
        myInterestUsernameList = humLogController.getTradesmanRelationTableList(username , userType);
        if(myInterestUsernameList.isEmpty()){
            myInterestMyInterestText.setText("Your do not have any interests");
        }else {
            myInterestMyInterestText.setText("Customer you showed interest in");
            setMyInterestAdapter(myInterestUsernameList);
        }
        if(otherInterestUsernameList.isEmpty()){
            myInterestOtherInterestText.setText("No Customer shown interest in your AD");
        }else {
            myInterestOtherInterestText.setText("Customer which shown interest in you");
            setOtherInterestAdapter(otherInterestUsernameList);
        }
    }

    private void setMyInterestAdapter(List<String> usernameList){

        if(userType.equalsIgnoreCase("customer")) {
            myInterestFirstNameList = humLogController.getTradeFirstNameList(usernameList);
            myInterestLastNameList = humLogController.getTradeLastNameList(usernameList);
            myInterestStreetList = humLogController.getTradeStreetList(usernameList);
            myInterestLocalityList = humLogController.getTradeLocalityList(usernameList);
            myInterestCityList = humLogController.getTradeCityList(usernameList);
            myInterestPostCodeList = humLogController.getTradePostCodeList(usernameList);
            myInterestNumberList = humLogController.getTradeMobileNumberList(usernameList);

            myInterestFirstName = myInterestFirstNameList.toArray(new String[myInterestFirstNameList.size()]);
            myInterestLastName = myInterestLastNameList.toArray(new String[myInterestLastNameList.size()]);
            myInterestStreet = myInterestStreetList.toArray(new String[myInterestStreetList.size()]);
            myInterestLocality = myInterestLocalityList.toArray(new String[myInterestLocalityList.size()]);
            myInterestNumber = myInterestNumberList.toArray(new String[myInterestNumberList.size()]);
            myInterestPostCode = myInterestPostCodeList.toArray(new String[myInterestPostCodeList.size()]);
            myInterestCity = myInterestCityList.toArray(new String[myInterestCityList.size()]);

            myInterestList.setAdapter(new myInterestAdapterForCustomerForMyInterests(this, myInterestFirstName, myInterestLastName,
                    myInterestStreet, myInterestLocality, myInterestCity, myInterestPostCode, myInterestNumber));
        }else {
            myInterestFirstNameList = humLogController.getAdvertisementFirstNameList(usernameList);
            myInterestLastNameList = humLogController.getAdvertisementLastNameList(usernameList);
            myInterestStreetList = humLogController.getAdvertisementStreetList(usernameList);
            myInterestLocalityList = humLogController.getAdvertisementLocalityList(usernameList);
            myInterestCityList = humLogController.getAdvertisementCityList(usernameList);
            myInterestPostCodeList = humLogController.getAdvertisementPostCodeList(usernameList);
            myInterestNumberList = humLogController.getAdvertisementMobileNumberList(usernameList);

            myInterestFirstName = myInterestFirstNameList.toArray(new String[myInterestFirstNameList.size()]);
            myInterestLastName = myInterestLastNameList.toArray(new String[myInterestLastNameList.size()]);
            myInterestStreet = myInterestStreetList.toArray(new String[myInterestStreetList.size()]);
            myInterestLocality = myInterestLocalityList.toArray(new String[myInterestLocalityList.size()]);
            myInterestNumber = myInterestNumberList.toArray(new String[myInterestNumberList.size()]);
            myInterestPostCode = myInterestPostCodeList.toArray(new String[myInterestPostCodeList.size()]);
            myInterestCity = myInterestCityList.toArray(new String[myInterestCityList.size()]);

            myInterestList.setAdapter(new myInterestAdapterForCustomerForMyInterests(this, myInterestFirstName, myInterestLastName,
                    myInterestStreet, myInterestLocality, myInterestCity, myInterestPostCode, myInterestNumber));
        }

    }

    private void setOtherInterestAdapter(List<String> usernameList){

        if(userType.equalsIgnoreCase("customer")) {
            otherInterestFirstNameList = humLogController.getTradeFirstNameList(usernameList);
            otherInterestLastNameList = humLogController.getTradeLastNameList(usernameList);
            otherInterestStreetList = humLogController.getTradeStreetList(usernameList);
            otherInterestLocalityList = humLogController.getTradeLocalityList(usernameList);
            otherInterestCityList = humLogController.getTradeCityList(usernameList);
            otherInterestPostCodeList = humLogController.getTradePostCodeList(usernameList);
            otherInterestNumberList = humLogController.getTradeMobileNumberList(usernameList);

            otherInterestFirstName = otherInterestFirstNameList.toArray(new String[otherInterestFirstNameList.size()]);
            otherInterestLastName = otherInterestLastNameList.toArray(new String[otherInterestLastNameList.size()]);
            otherInterestStreet = otherInterestStreetList.toArray(new String[otherInterestStreetList.size()]);
            otherInterestLocality = otherInterestLocalityList.toArray(new String[otherInterestLocalityList.size()]);
            otherInterestNumber = otherInterestNumberList.toArray(new String[otherInterestNumberList.size()]);
            otherInterestPostCode = otherInterestPostCodeList.toArray(new String[otherInterestPostCodeList.size()]);
            otherInterestCity = otherInterestCityList.toArray(new String[otherInterestCityList.size()]);

            otherInterestList.setAdapter(new myInterestAdapterForCustomerForOtherInterests(this, otherInterestFirstName, otherInterestLastName,
                    otherInterestStreet, otherInterestLocality, otherInterestCity, otherInterestPostCode, otherInterestNumber));
        }else {

            otherInterestFirstNameList = humLogController.getAdvertisementFirstNameList(usernameList);
            otherInterestLastNameList = humLogController.getAdvertisementLastNameList(usernameList);
            otherInterestStreetList = humLogController.getAdvertisementStreetList(usernameList);
            otherInterestLocalityList = humLogController.getAdvertisementLocalityList(usernameList);
            otherInterestCityList = humLogController.getAdvertisementCityList(usernameList);
            otherInterestPostCodeList = humLogController.getAdvertisementPostCodeList(usernameList);
            otherInterestNumberList = humLogController.getAdvertisementMobileNumberList(usernameList);

            otherInterestFirstName = otherInterestFirstNameList.toArray(new String[otherInterestFirstNameList.size()]);
            otherInterestLastName = otherInterestLastNameList.toArray(new String[otherInterestLastNameList.size()]);
            otherInterestStreet = otherInterestStreetList.toArray(new String[otherInterestStreetList.size()]);
            otherInterestLocality = otherInterestLocalityList.toArray(new String[otherInterestLocalityList.size()]);
            otherInterestNumber = otherInterestNumberList.toArray(new String[otherInterestNumberList.size()]);
            otherInterestPostCode = otherInterestPostCodeList.toArray(new String[otherInterestPostCodeList.size()]);
            otherInterestCity = otherInterestCityList.toArray(new String[otherInterestCityList.size()]);

            otherInterestList.setAdapter(new myInterestAdapterForCustomerForOtherInterests(this, otherInterestFirstName, otherInterestLastName,
                    otherInterestStreet, otherInterestLocality, otherInterestCity, otherInterestPostCode, otherInterestNumber));
        }
    }

    /**
     * The codes for two inner classes (myAdRow and myAdAdapter) below are taken from a you tube video,
     * having the link https://www.youtube.com/watch?v=_l9e2t4fcfM .
     */
    class myInterestRowForCustomer{
        String firstName;
        String lastName;
        String street;
        String locality;
        String mobileNumber;
        String postCode;
        String city;

        public myInterestRowForCustomer(String firstName , String lastName , String street , String locality , String city
                , String postCode ,  String mobileNumber  ){

            this.firstName = firstName;
            this.lastName = lastName;
            this.street = street;
            this.locality = locality;
            this.city = city;
            this.postCode = postCode;
            this.mobileNumber = mobileNumber;
        }
    }


    class myInterestAdapterForCustomerForMyInterests extends BaseAdapter {

        ArrayList<myInterestRowForCustomer> list;
        Context context;

        public myInterestAdapterForCustomerForMyInterests(Context context ,String [] firstName , String [] lastName , String [] street , String [] locality
                , String [] city , String [] postCode ,  String[] mobileNumber ){
            this.context = context;
            list = new ArrayList<myInterestRowForCustomer>();
            for(int i =0; i< firstName.length; i++){
                list.add(new myInterestRowForCustomer(firstName[i] , lastName[i] , street[i] , locality[i] ,city [i],
                        postCode[i] , mobileNumber[i] ));
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

            if(userType.equalsIgnoreCase("customer")) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myInterestRow = inflater.inflate(R.layout.my_interest_row, parent, false);

                TextView myInterestRowFirstName = (TextView) myInterestRow.findViewById(R.id.myInterestRowFirstNameText);
                TextView myInterestRowLastName = (TextView) myInterestRow.findViewById(R.id.myInterestRowLastNameText);
                TextView myInterestRowStreet = (TextView) myInterestRow.findViewById(R.id.myInterestRowStreetText);
                TextView myInterestRowLocality = (TextView) myInterestRow.findViewById(R.id.myInterestRowLocalityText);
                TextView myInterestRowMobileNumber = (TextView) myInterestRow.findViewById(R.id.myInterestRowNumberText);
                TextView myInterestRowPostCode = (TextView) myInterestRow.findViewById(R.id.myInterestRowPostCodeText);
                TextView myInterestCity = (TextView) myInterestRow.findViewById(R.id.myInterestRowCityText);

                myInterestRowForCustomer temp = list.get(position);
                myInterestRowFirstName.setText(temp.firstName);
                myInterestRowLastName.setText(temp.lastName);
                myInterestRowStreet.setText(temp.street);
                myInterestRowLocality.setText(temp.locality);
                myInterestRowMobileNumber.setText(temp.mobileNumber);
                myInterestRowPostCode.setText(temp.postCode);
                myInterestCity.setText(temp.city);
                myInterestsRateButton = (Button) myInterestRow.findViewById(R.id.myInterestRowRateButton);
                myInterestsRateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myInterestRateButtonAction(position);
                    }
                });
                myInterestsDeleteButton = (Button) myInterestRow.findViewById(R.id.myInterestRowDeleteButton);
                myInterestsDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myInterestDeleteButtonAction(position);
                    }
                });
                return myInterestRow;
            }else {// do something
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myInterestRow = inflater.inflate(R.layout.my_interest_row_for_customer_view, parent, false);

                TextView myInterestRowFirstName = (TextView) myInterestRow.findViewById(R.id.myInterestRowFirstNameText);
                TextView myInterestRowLastName = (TextView) myInterestRow.findViewById(R.id.myInterestRowLastNameText);
                TextView myInterestRowStreet = (TextView) myInterestRow.findViewById(R.id.myInterestRowStreetText);
                TextView myInterestRowLocality = (TextView) myInterestRow.findViewById(R.id.myInterestRowLocalityText);
                TextView myInterestRowMobileNumber = (TextView) myInterestRow.findViewById(R.id.myInterestRowNumberText);
                TextView myInterestRowPostCode = (TextView) myInterestRow.findViewById(R.id.myInterestRowPostCodeText);
                TextView myInterestCity = (TextView) myInterestRow.findViewById(R.id.myInterestRowCityText);

                myInterestRowForCustomer temp = list.get(position);
                myInterestRowFirstName.setText(temp.firstName);
                myInterestRowLastName.setText(temp.lastName);
                myInterestRowStreet.setText(temp.street);
                myInterestRowLocality.setText(temp.locality);
                myInterestRowMobileNumber.setText(temp.mobileNumber);
                myInterestRowPostCode.setText(temp.postCode);
                myInterestCity.setText(temp.city);

                myInterestsDeleteButton = (Button) myInterestRow.findViewById(R.id.myInterestRowDeleteButton);
                myInterestsDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myInterestDeleteButtonAction(position);
                    }
                });
                return myInterestRow;
            }

        }
    }

    class myInterestAdapterForCustomerForOtherInterests extends BaseAdapter {

        ArrayList<myInterestRowForCustomer> list;
        Context context;

        public myInterestAdapterForCustomerForOtherInterests(Context context ,String [] firstName , String [] lastName , String [] street , String [] locality
                , String [] city , String [] postCode ,  String[] mobileNumber ){
            this.context = context;
            list = new ArrayList<myInterestRowForCustomer>();
            for(int i =0; i< firstName.length; i++){
                list.add(new myInterestRowForCustomer(firstName[i] , lastName[i] , street[i] , locality[i] ,city [i],
                        postCode[i] , mobileNumber[i] ));
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

            if(userType.equalsIgnoreCase("customer")) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myInterestRow = inflater.inflate(R.layout.my_interest_row, parent, false);

                TextView myInterestRowFirstName = (TextView) myInterestRow.findViewById(R.id.myInterestRowFirstNameText);
                TextView myInterestRowLastName = (TextView) myInterestRow.findViewById(R.id.myInterestRowLastNameText);
                TextView myInterestRowStreet = (TextView) myInterestRow.findViewById(R.id.myInterestRowStreetText);
                TextView myInterestRowLocality = (TextView) myInterestRow.findViewById(R.id.myInterestRowLocalityText);
                TextView myInterestRowMobileNumber = (TextView) myInterestRow.findViewById(R.id.myInterestRowNumberText);
                TextView myInterestRowPostCode = (TextView) myInterestRow.findViewById(R.id.myInterestRowPostCodeText);
                TextView myInterestCity = (TextView) myInterestRow.findViewById(R.id.myInterestRowCityText);

                myInterestRowForCustomer temp = list.get(position);
                myInterestRowFirstName.setText(temp.firstName);
                myInterestRowLastName.setText(temp.lastName);
                myInterestRowStreet.setText(temp.street);
                myInterestRowLocality.setText(temp.locality);
                myInterestRowMobileNumber.setText(temp.mobileNumber);
                myInterestRowPostCode.setText(temp.postCode);
                myInterestCity.setText(temp.city);
                otherInterestRateButton = (Button) myInterestRow.findViewById(R.id.myInterestRowRateButton);
                otherInterestRateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otherInterestRateButtonAction(position);
                    }
                });
                otherInterestDeleteButton = (Button) myInterestRow.findViewById(R.id.myInterestRowDeleteButton);
                otherInterestDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otherInterestDeleteButtonAction(position);
                    }
                });
                return myInterestRow;
            }else {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myInterestRow = inflater.inflate(R.layout.my_interest_row_for_customer_view, parent, false);

                TextView myInterestRowFirstName = (TextView) myInterestRow.findViewById(R.id.myInterestRowFirstNameText);
                TextView myInterestRowLastName = (TextView) myInterestRow.findViewById(R.id.myInterestRowLastNameText);
                TextView myInterestRowStreet = (TextView) myInterestRow.findViewById(R.id.myInterestRowStreetText);
                TextView myInterestRowLocality = (TextView) myInterestRow.findViewById(R.id.myInterestRowLocalityText);
                TextView myInterestRowMobileNumber = (TextView) myInterestRow.findViewById(R.id.myInterestRowNumberText);
                TextView myInterestRowPostCode = (TextView) myInterestRow.findViewById(R.id.myInterestRowPostCodeText);
                TextView myInterestCity = (TextView) myInterestRow.findViewById(R.id.myInterestRowCityText);

                myInterestRowForCustomer temp = list.get(position);
                myInterestRowFirstName.setText(temp.firstName);
                myInterestRowLastName.setText(temp.lastName);
                myInterestRowStreet.setText(temp.street);
                myInterestRowLocality.setText(temp.locality);
                myInterestRowMobileNumber.setText(temp.mobileNumber);
                myInterestRowPostCode.setText(temp.postCode);
                myInterestCity.setText(temp.city);

                otherInterestDeleteButton = (Button) myInterestRow.findViewById(R.id.myInterestRowDeleteButton);
                otherInterestDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otherInterestDeleteButtonAction(position);
                    }
                });
                return myInterestRow;
            }
        }
    }

    private void myInterestRateButtonAction(int position){

        View view = getViewByPosition(position , myInterestList);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.myInterestRowRatingBar);
        float rating =  ratingBar.getRating();
        humLogController.updateScoreAndRatingAndJobs(rating , myInterestUsernameList.get(position));
        humLogController.deleteCustomerRelationInterest(username, myInterestUsernameList.get(position), position);
        Toast.makeText(this , "Tradesman have been rated and Relation is deleted" , Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void otherInterestRateButtonAction(int position){

        View view = getViewByPosition(position , otherInterestList);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.myInterestRowRatingBar);
        float rating = ratingBar.getRating();
        humLogController.updateScoreAndRatingAndJobs(rating , otherInterestUsernameList.get(position));
        humLogController.deleteTradesmanRelationInterest(otherInterestUsernameList.get(position), username, position);
        Toast.makeText(this , "Tradesman have been rated and Relation is deleted" , Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void myInterestDeleteButtonAction(int position){

        if(userType.equalsIgnoreCase("customer")) {
            humLogController.deleteCustomerRelationInterest(username, myInterestUsernameList.get(position), position);
            Toast.makeText(this, "Tradesman Relation is deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }else {
            humLogController.deleteTradesmanRelationInterest(username, myInterestUsernameList.get(position), position);
            Toast.makeText(this, "Customer Relation is deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    private void otherInterestDeleteButtonAction(int position){

        if(userType.equalsIgnoreCase("customer")) {
            humLogController.deleteTradesmanRelationInterest(otherInterestUsernameList.get(position), username, position);
            Toast.makeText(this, "Tradesman  Relation is deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }else {
            humLogController.deleteCustomerRelationInterest(otherInterestUsernameList.get(position), username, position);
            Toast.makeText(this, "Customer  Relation is deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }


    /**
     * http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position
     * @param pos
     * @param listView
     * @return
     */
    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}

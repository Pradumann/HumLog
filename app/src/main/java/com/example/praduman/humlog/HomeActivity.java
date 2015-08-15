/**
 * This class is the home page of the application.
 * This will be launched when ever the user is logged In.
 *
 * @author: Praduman Raparia
 * @version: 1.0
 */


package com.example.praduman.humlog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends ActionBarActivity {

    private HumLogController humLogController;
    private Spinner citySpinner;
    private Spinner tradesSpinner;
    private TextView firstNameTextView;
    private String username;
    private String userType;
    private String firstName;
    private String lastName;
    private ListView searchList;
    private Button searchButton;
    private Button interestedButton;
    private String [] searchResultFirstName;
    private String [] searchResultLastName;
    private String [] searchResultStreet;
    private String [] searchResultLocality;
    private String [] searchResultCity;
    private String [] searchResultPostCode;
    private String [] searchResultRatings;
    private String [] searchResultMobileNumber;
    private String [] searchResultDetails;
    private String [] interestButton;
    private List<String> usernameList;
    private List<String> searchResultFirstNameList;
    private List<String> searchResultLastNameList;
    private List<String> searchResultStreetList;
    private List<String> searchResultLocalityList;
    private List<String> searchResultCityList;
    private List<String> searchResultPostCodeList;
    private List<String> searchResultRatingsList;
    private List<String> searchResultMobileNumberList;

    private List<String> searchResultDetailsList;
    private List<String> interestButtonList;
    private String citySelected;
    private String tradeSelected;
    private ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        setDetails();
        setSpinners();
        setFirstNameText();
        setNavigationList();
        setButtonAction();
        Toast.makeText(this , "Swipe right for menu" , Toast.LENGTH_LONG).show();
    }

    private void setDetails(){
        username = getIntent().getStringExtra("username");
        humLogController.setDetails(username);
        userType = humLogController.getUserType();
        firstName = humLogController.getFirstName();
        lastName = humLogController.getLastName();
    }

    private void setFirstNameText(){
        firstNameTextView = (TextView) findViewById(R.id.userbarFirstNameText);
        firstNameTextView.setText(firstName);
    }
    /**
     * This method will set the spinners of the home page.
     * One spinner will be city spinner and other is trade spinner.
     */
    private void setSpinners(){

        citySpinner = (Spinner) findViewById(R.id.selectionbarHomeCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter);

        tradesSpinner = (Spinner) findViewById(R.id.selectionbarHomeTradesSpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this , R.array.trades, android.R.layout.simple_list_item_1);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tradesSpinner.setAdapter(arrayAdapter2);

    }

    private void setNavigationList(){
        if(userType.equalsIgnoreCase("customer")){
            createCustomerNavList();
        }else {
            createTradesmanNavList();
        }
    }

    /**
     * The method will create a navigation list for the
     * navigation slider of the home page.
     */
    private void createTradesmanNavList(){
        String [] navMenu = {"" , "My Interests"  ,"My Ratings" , "My Trade Details" , "Edit Profile" , "Log Out"};
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_view, navMenu);
        ListView navList = (ListView)findViewById(R.id.navList);
        navList.setAdapter(navAdapter);
        setTradesmanListViewAction();
    }

    private void createCustomerNavList(){
        String [] navMenu = {"" , "My Interests"  ,"My Ad's" , "Post Ad" , "Edit Profile" , "Log Out"};
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_view, navMenu);
        ListView navList = (ListView)findViewById(R.id.navList);
        navList.setAdapter(navAdapter);
        setCustomerListViewAction();
    }

    /**
     * This method have action listeners for all of the
     * items in the navigation list
     */
    private void setTradesmanListViewAction(){
        ListView navList = (ListView) findViewById(R.id.navList);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String stringClicked = textView.getText().toString();
                if (stringClicked.equalsIgnoreCase("Log Out")) {
                    humLogController.logOut();
                    startLogInActivity();
                } else if (stringClicked.equalsIgnoreCase("Edit Profile")) {
                    startEditProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("My Trade Details")) {
                    startTradeProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("My Ratings")) {
                    if(checkPreRatings()){
                        startMyRatingActivity();
                    }else {
                        showError("OOPS...!!!" , "You do not have any ratings yet. Make your trade profile first");
                    }
                } else if (stringClicked.equalsIgnoreCase("My Interests")) {
                    startMyInterestActivityForTradesman();
                }
            }
        });
    }

    /**
     * This method have the action listeners for all the
     * items in customer navigation list
     */
    private void setCustomerListViewAction(){

        ListView navList = (ListView) findViewById(R.id.navList);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String stringClicked = textView.getText().toString();
                if (stringClicked.equalsIgnoreCase("Log Out")) {
                    humLogController.logOut();
                    startLogInActivity();
                } else if (stringClicked.equalsIgnoreCase("Edit Profile")) {
                    startEditProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("Post Ad")) {
                    startPostAdActivity();
                } else if (stringClicked.equalsIgnoreCase("My Ad's")) {
                    if(checkPreAd()){
                        startMYAdActivity();
                    }else {
                        showError("OOPS...!!!" , "You do not have any ads posted. Go ahead, post one");
                    }
                } else if (stringClicked.equalsIgnoreCase("My Interests")) {
                    startMyInterestActivityForCustomer();
                }
            }
        });
    }

    private void setButtonAction(){
        bar = (ProgressBar) findViewById(R.id.progressBar1);
        searchList = (ListView) findViewById(R.id.searchResultRelativeLayout);
        searchButton = (Button) findViewById(R.id.selectionbarHomeSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                citySelected = citySpinner.getSelectedItem().toString();
                tradeSelected= tradesSpinner.getSelectedItem().toString();
                if(citySelected.equalsIgnoreCase("Select City")|| tradeSelected.equalsIgnoreCase("Select Trade")) {
                   showError("Error" ,  "Select both city and trade");
                    bar.setVisibility(View.GONE);
                }else {
                    if (userType.equalsIgnoreCase("customer")) {
                        searchTradeProfiles();
                        bar.setVisibility(View.GONE);
                    } else {
                        searchAdvertisement();
                        bar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


    /**
     * This method will start the log In activity.
     */
    private void startLogInActivity(){
        Intent logInActivityIntent = new Intent(this , LogInActivity.class);
        logInActivityIntent.putExtra("controllerObject" , humLogController);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInActivityIntent);
    }

    private void startPostAdActivity(){
        Intent postAdActivityIntent = new Intent(this, PostAdActivity.class);
        postAdActivityIntent.putExtra("username", username);
        postAdActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(postAdActivityIntent);
    }

    private boolean checkPreAd(){
        return humLogController.checkAdPosted(username);
    }

    private void startEditProfileActivity(){
        Intent EditProfileActivityIntent = new Intent(this , EditProfileActivity.class);
        EditProfileActivityIntent.putExtra("username" , username);
        EditProfileActivityIntent.putExtra("userType" , userType);
        EditProfileActivityIntent.putExtra("firstName" , firstName);
        EditProfileActivityIntent.putExtra("lastName", lastName);
        EditProfileActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(EditProfileActivityIntent);
    }

    private void startMYAdActivity(){
        Intent myAdActivityIntent = new Intent(this , MyAdActivity.class);
        myAdActivityIntent.putExtra("username" , username);
        myAdActivityIntent.putExtra("controllerObject" , humLogController);
        startActivity(myAdActivityIntent);
    }

    private void startMyInterestActivityForCustomer(){

        // for the customer.
    }


    private void searchAdvertisement(){
        usernameList = new ArrayList<String>();
        usernameList = humLogController.getAdSearchResultUsernameList(citySelected, tradeSelected);
        if(usernameList.isEmpty()){
            showError("OOPS..!!!" , "Sorry..! we do not have results to show for this time");
            bar.setVisibility(View.GONE);
        }

        else {
            searchResultFirstNameList = humLogController.getAdvertisementFirstNameList(usernameList);
            searchResultLastNameList = humLogController.getAdvertisementLastNameList(usernameList);
            searchResultStreetList = humLogController.getAdvertisementStreetList(usernameList);
            searchResultLocalityList = humLogController.getAdvertisementLocalityList(usernameList);
            searchResultCityList = humLogController.getAdvertisementCityList(usernameList);
            searchResultPostCodeList = humLogController.getAdvertisementPostCodeList(usernameList);
            searchResultMobileNumberList = humLogController.getAdvertisementMobileNumberList(usernameList);
            searchResultDetailsList = humLogController.getAdvertisementDetailsList(citySelected, tradeSelected);

            searchResultFirstName = searchResultFirstNameList.toArray(new String[searchResultFirstNameList.size()]);
            searchResultLastName = searchResultLastNameList.toArray(new String[searchResultLastNameList.size()]);
            searchResultStreet = searchResultStreetList.toArray(new String[searchResultStreetList.size()]);
            searchResultLocality = searchResultLocalityList.toArray(new String[searchResultLocalityList.size()]);
            searchResultMobileNumber = searchResultMobileNumberList.toArray(new String[searchResultMobileNumberList.size()]);
            searchResultPostCode = searchResultPostCodeList.toArray(new String[searchResultPostCodeList.size()]);
            searchResultDetails = searchResultDetailsList.toArray(new String[searchResultDetailsList.size()]);
            searchResultCity = searchResultCityList.toArray(new String[searchResultCityList.size()]);
            interestButton = new String[searchResultFirstNameList.size()];
            searchResultRatings = new String[usernameList.size()];
            for (int i = 0; i < searchResultFirstNameList.size(); i++) {
                interestButton[i] = "Interested";
                searchResultRatings[i] = "";
            }

            searchList.setAdapter(new myAdAdapter(this, searchResultFirstName, searchResultLastName, searchResultStreet
                    , searchResultLocality, searchResultMobileNumber, searchResultPostCode, searchResultDetails, interestButton
                    , searchResultCity ,searchResultRatings));

        }
    }

    private void startTradeProfileActivity(){

        Intent tradeProfileActivityIntent = new Intent(this , TradeProfileActivity.class);
        tradeProfileActivityIntent.putExtra("username", username);
        tradeProfileActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(tradeProfileActivityIntent);
    }

    private boolean checkPreRatings(){
       return humLogController.checkTradeProfile(username);
    }

    private void startMyRatingActivity(){
        Intent myRatingActivityIntent = new Intent(this , MyRatingActivity.class);
        myRatingActivityIntent.putExtra("username" , username);
        myRatingActivityIntent.putExtra("controllerObject" , humLogController);
        startActivity(myRatingActivityIntent);
    }

    private void startMyInterestActivityForTradesman(){
        // for tradesman
    }

    private void searchTradeProfiles(){

        usernameList = new ArrayList<String>();
        usernameList = humLogController.getTradeUsernameList(citySelected , tradeSelected);

        if(usernameList.isEmpty()){
            showError("OOPS..!!!" , "Sorry..! we do not have results to show for this time");
            bar.setVisibility(View.GONE);
        }else {
            searchResultFirstNameList = humLogController.getTradeFirstNameList(usernameList);
            searchResultLastNameList = humLogController.getTradeLastNameList(usernameList);
            searchResultStreetList = humLogController.getTradeStreetList(usernameList);
            searchResultLocalityList = humLogController.getTradeLocalityList(usernameList);
            searchResultCityList = humLogController.getTradeCityList(usernameList);
            searchResultPostCodeList = humLogController.getTradePostCodeList(usernameList);
            searchResultMobileNumberList = humLogController.getTradeMobileNumberList(usernameList);
            searchResultRatingsList = humLogController.getTradeRatingList(usernameList);
            searchResultDetailsList = humLogController.getTradeDetailsList(citySelected, tradeSelected);

            searchResultFirstName = searchResultFirstNameList.toArray(new String[searchResultFirstNameList.size()]);
            searchResultLastName = searchResultLastNameList.toArray(new String[searchResultLastNameList.size()]);
            searchResultStreet = searchResultStreetList.toArray(new String[searchResultStreetList.size()]);
            searchResultLocality = searchResultLocalityList.toArray(new String[searchResultLocalityList.size()]);
            searchResultMobileNumber = searchResultMobileNumberList.toArray(new String[searchResultMobileNumberList.size()]);
            searchResultPostCode = searchResultPostCodeList.toArray(new String[searchResultPostCodeList.size()]);
            searchResultDetails = searchResultDetailsList.toArray(new String[searchResultDetailsList.size()]);
            searchResultRatings = searchResultRatingsList.toArray(new String[searchResultRatingsList.size()]);
            searchResultCity = searchResultCityList.toArray(new String[searchResultRatingsList.size()]);
            searchResultRatings = searchResultRatingsList.toArray(new String[searchResultRatingsList.size()]);
            interestButton = new String[searchResultFirstNameList.size()];
            for (int i = 0; i < searchResultFirstNameList.size(); i++) {
                interestButton[i] = "Interested";
            }

            searchList.setAdapter(new myAdAdapter(this, searchResultFirstName, searchResultLastName, searchResultStreet
                    , searchResultLocality, searchResultMobileNumber, searchResultPostCode, searchResultDetails,
                    interestButton , searchResultCity , searchResultRatings));

        }
    }



    /**
     * The codes for two inner classes (myAdRow and myAdAdapter) below are taken from a you tube video,
     * having the link https://www.youtube.com/watch?v=_l9e2t4fcfM .
     */
    class myAdRow{
        String firstName;
        String lastName;
        String street;
        String locality;
        String mobileNumber;
        String postCode;
        String details;
        String button;
        String ratings;
        String city;

        public myAdRow(String firstName , String lastName , String street , String locality , String mobileNumber
        , String postCode , String details , String button , String city , String ratings){

            this.firstName = firstName;
            this.lastName = lastName;
            this.street = street;
            this.locality = locality;
            this.mobileNumber = mobileNumber;
            this.postCode = postCode;
            this.details = details;
            this.button = button;
            this.city = city;
            this.ratings = ratings;
        }
    }


    class myAdAdapter extends BaseAdapter {

        ArrayList<myAdRow> list;
        Context context;

        public myAdAdapter(Context context ,String [] firstName , String [] lastName , String [] street , String [] locality
        , String [] mobileNumber , String [] postCode , String [] details , String[] button , String[] city, String [] ratings){
            this.context = context;
            list = new ArrayList<myAdRow>();
            for(int i =0; i< firstName.length; i++){
                list.add(new myAdRow(firstName[i] , lastName[i] , street[i] , locality[i] , mobileNumber[i],
                        postCode[i] , details[i] , button[i] , city[i] , ratings [i]));
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
            View searchResultRow = inflater.inflate(R.layout.my_search_result_row , parent , false);

            TextView mySearchResultRowFirstName = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowFirstNameText);
            TextView mySearchResultRowLastName = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowLastNameText);
            TextView mySearchResultRowStreet = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowStreetText);
            TextView mySearchResultRowLocality = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowLocalityText);
            TextView mySearchResultRowMobileNumber = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowNumberText);
            TextView mySearchResultRowPostCode = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowPostCodeText);
            TextView mySearchResultRowDetails = (TextView) searchResultRow.findViewById(R.id.mySearchResultDetailText);
            TextView mySearchResultCity = (TextView) searchResultRow.findViewById(R.id.mySearchResultRowCityText);
            interestedButton = (Button) searchResultRow.findViewById(R.id.mySearchResultInterestedButton);
            interestedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInterestedButtonAction(position);
                    disableItem(position);
                }
            });

            myAdRow temp = list.get(position);
            mySearchResultRowFirstName.setText(temp.firstName);
            mySearchResultRowLastName.setText(temp.lastName);
            mySearchResultRowStreet.setText(temp.street);
            mySearchResultRowLocality.setText(temp.locality);
            mySearchResultRowMobileNumber.setText(temp.mobileNumber);
            mySearchResultRowPostCode.setText(temp.postCode);
            mySearchResultRowDetails.setText(temp.details);
            mySearchResultCity.setText(temp.city);
            if(userType.equalsIgnoreCase("customer")){
                RatingBar mySearchResultRatingBar = (RatingBar) searchResultRow.findViewById(R.id.mySearchResultRatingBar);
                float rating = Float.parseFloat(temp.ratings);
                mySearchResultRatingBar.setRating(rating);
                mySearchResultRatingBar.setFocusable(false);
            }else {
                RatingBar mySearchResultRatingBar = (RatingBar) searchResultRow.findViewById(R.id.mySearchResultRatingBar);
                mySearchResultRatingBar.setVisibility(View.INVISIBLE);
            }
            interestedButton.setText(temp.button);
            return searchResultRow;
        }
    }

    private void setInterestedButtonAction(int position){

        String otherUsername = usernameList.get(position);
        humLogController.setRelations(username , otherUsername , citySelected , tradeSelected);
    }

    private void disableItem(int position){
        getViewByPosition(position, searchList).setBackgroundColor((Color.parseColor("#e4e4e4")));
        getViewByPosition(position , searchList).setEnabled(false);
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

    private void showError (String title , String errorMessage){
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(HomeActivity.this);
        errorBuilder.setMessage(errorMessage)
                .setTitle(title).setPositiveButton("OK" , null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }
}

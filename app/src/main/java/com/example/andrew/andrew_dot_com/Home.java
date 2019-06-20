package com.example.andrew.andrew_dot_com;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SaveOrderDialog.saveOrderDialogListener {

    Intent intent;
    OnlineShoppingDataBase dataBase;
    ListView Products;
    CustomListView customListView;
    ArrayList<String> ListItemName;
    ArrayList<Integer> ListItemPrice;
    ArrayList<Integer> ListItemQuantity;
    ArrayList<Integer> tempListItemImagesID;
    GlobalID customer;
    FirebaseAuth firebaseAuth ;

    Integer[] ListItemImagesID = {
            R.drawable.apple_dock, R.drawable.apple_iphone_x1, R.drawable.apple_iphone_x2, R.drawable.apple_macbook1, R.drawable.apple_macbook2, R.drawable.apple_tv_bg, R.drawable.apple_watch1, R.drawable.apple_watch2,
            R.drawable.innovative_charger, R.drawable.innovative_cup_stand, R.drawable.innovative_modeling, R.drawable.innovative_mugg1, R.drawable.innovative_mugg2, R.drawable.innovative_mugg3,
            R.drawable.kitchen_coffe_machine1, R.drawable.kitchen_coffe_machine2, R.drawable.kitchen_electrical_presstor1, R.drawable.kitchen_electrical_presstor2, R.drawable.kitchen_electrical_presstor3, R.drawable.kitchen_mixer1, R.drawable.kitchen_mixer2,
            R.drawable.laptop1, R.drawable.laptop2, R.drawable.laptop3, R.drawable.laptop4, R.drawable.laptop5, R.drawable.laptop6, R.drawable.laptop7,
            R.drawable.leather_bag1, R.drawable.leather_bag2, R.drawable.leather_bag3, R.drawable.leather_bag4, R.drawable.leather_bag5, R.drawable.leather_gloves1, R.drawable.leather_gloves2, R.drawable.leather_shoes1, R.drawable.leather_shoes2, R.drawable.leather_shoes3, R.drawable.leather_shoes4, R.drawable.leather_shoes5, R.drawable.leather_waistband1, R.drawable.leather_waistband2, R.drawable.leather_waistband3, R.drawable.leather_wallet1, R.drawable.leather_wallet2, R.drawable.leather_wallet3,
            R.drawable.makeup_iliner, R.drawable.makeup_mascara1, R.drawable.makeup_mascara2, R.drawable.makeup_rouge1, R.drawable.makeup_rouge2, R.drawable.makeup_rouge3, R.drawable.makeup_rouge4, R.drawable.makeup_skincare1, R.drawable.makeup_skincare2, R.drawable.makeup_skincare3,
            R.drawable.supplement1, R.drawable.supplement2, R.drawable.supplement3, R.drawable.supplement4, R.drawable.supplement5, R.drawable.supplement6, R.drawable.supplement7, R.drawable.supplement8, R.drawable.supplement9
    };


    private DrawerLayout drawer;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home_id:
                intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;

            case R.id.history_id:
                intent = new Intent(this, ShowOrders.class);
                startActivity(intent);
                return true;

            case R.id.make_id:
                intent = new Intent(this, makeOrder.class);
                intent.putExtra("id", "makeOrder");
                startActivity(intent);
                return true;

            case R.id.show_id:
                if (customer.CartLength() == 0) {
                    Toast.makeText(this, "The cart is empty", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    intent = new Intent(this, makeOrder.class);
                    intent.putExtra("id", "showOrder");
                    startActivity(intent);
                    return true;
                }

            case R.id.edit_id:
                if (customer.CartLength() == 0) {
                    Toast.makeText(this, "The cart is empty", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    intent = new Intent(this, makeOrder.class);
                    intent.putExtra("id", "editOrder");
                    startActivity(intent);
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.home_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null)
            navigationView.setCheckedItem(R.id.Home_id);
        TextView id = (TextView) findViewById(R.id.customer_id);

//--------------------------------------------------------------------------------------------------------------------------------------------------------------
        //final Spinner catList = (Spinner) findViewById(R.id.Categories);
        EditText SearchTXT = (EditText) findViewById(R.id.ed_search);
        Products = (ListView) findViewById(R.id.Items_list);
        dataBase = new OnlineShoppingDataBase(this);
        firebaseAuth=FirebaseAuth.getInstance();
        customer = new GlobalID();

        id.setText("customer ID:  " + String.valueOf(customer.getId()));
//        customer.setEmail(dataBase.GetCustomerEmail(customer.getId()));

        registerForContextMenu(Products);
        selectCategory("allCategories");

        Products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Home.this, "From <option menu> choose make order", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

        finish();
        moveTaskToBack(true) ;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Home_id:
                intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.history_id:
                intent = new Intent(this, ShowOrders.class);
                startActivity(intent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.make_id:
                intent = new Intent(this, makeOrder.class);
                intent.putExtra("id", "makeOrder");
                startActivity(intent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.show_id:
                if (customer.CartLength() == 0) {
                    Toast.makeText(this, "The cart is empty", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    intent = new Intent(this, makeOrder.class);
                    intent.putExtra("id", "showOrder");
                    startActivity(intent);
                    return true;
                }
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.edit_id:
                if (customer.cartItems.size() <= 0) {
                    Toast.makeText(this, "The cart is empty", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    intent = new Intent(this, makeOrder.class);
                    intent.putExtra("id", "editOrder");
                    startActivity(intent);
                    return true;
                }
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.show_map_id:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.email_id:
                String emailContent = "Your Order: { / ";
                for (int i = 0; i < customer.CartLength(); i++)
                    emailContent += customer.getSelectedProducts().get(i) + " / ";
                emailContent += "}  , to the address: " + customer.getLocation();
                email(customer.getEmail(), emailContent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.logOut_id:
                if (!customer.saved) {
                    onRestart();
                    return true;
                } else {
                    customer.clearCart();
                    customer.cartItems.clear();
                    firebaseAuth.signOut();
                    intent = new Intent(this, log_in.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    return true;
                }
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.delete_account_id:
                dataBase.deleteAccount(customer.getId());
                customer.clearCart();
                customer.cartItems.clear();
                Toast.makeText(this, "we will missed you , bye", Toast.LENGTH_LONG).show();
                intent = new Intent(this, log_in.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.apple_cat:
                selectCategory("apple");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.innovative_cat:
                selectCategory("innovative");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.kitchen_cat:
                selectCategory("kitchen");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.laptop_cat:
                selectCategory("laptop");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.leather_cat:
                selectCategory("leather");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.makeup_cat:
                selectCategory("makeup");
                return true;
//-----------------------------------------------------------------------------------------------------------------------------
            case R.id.supplement_cat:
                selectCategory("supplement");
                return true;
        }

        return false;
    }

    public void selectCategory(String selectedCat) {
        if (selectedCat.equals(""))
            Toast.makeText(Home.this, "Select Category", Toast.LENGTH_SHORT).show();
        else {
            Cursor cursor1 = dataBase.SelectProducts(selectedCat);
            // [0] name , [1] price , [2] quantity , [3] imageID
            if (cursor1.getCount() <= 0)
                Toast.makeText(Home.this, "NOT found", Toast.LENGTH_SHORT).show();

            else {
                ListItemName = new ArrayList<String>();
                ListItemPrice = new ArrayList<Integer>();
                ListItemQuantity = new ArrayList<Integer>();
                tempListItemImagesID = new ArrayList<Integer>();

                while (!cursor1.isAfterLast()) {
                    ListItemName.add(cursor1.getString(0));
                    ListItemPrice.add(Integer.parseInt(cursor1.getString(1)));
                    tempListItemImagesID.add(ListItemImagesID[Integer.parseInt(cursor1.getString(3))]);
                    ListItemQuantity.add(Integer.parseInt(cursor1.getString(2)));
                    cursor1.moveToNext();
                }
                customListView = new CustomListView(Home.this, ListItemName, ListItemPrice, tempListItemImagesID, ListItemQuantity);
                Products.setAdapter(customListView);
            }
        }
    }

    public void openDialog() {
        SaveOrderDialog saveOrderDialog = new SaveOrderDialog();
        saveOrderDialog.show(getSupportFragmentManager(), "save order dialog");
    }

    @Override
    public void getCustomer(String location) {
        if (location.isEmpty())
            Toast.makeText(this, "Enter an address or we will use your current location", Toast.LENGTH_SHORT).show();
        customer.setLocation(location);
    }

    public void email(String mail, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = {mail, "andrew.amin20@gmial.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmation mail from OnlineShopping App");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        Intent chooser = Intent.createChooser(intent, "Send Email");
        startActivity(chooser);
    }

    @Override
    protected void onResume() {
        if(!customer.saved)
        {
            openDialog();
            customer.saved=true;
        }
        super.onResume();
    }
}



package com.example.andrew.andrew_dot_com;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowOrders extends AppCompatActivity {

    TextView orders  ;
    OnlineShoppingDataBase dataBase ;
    GlobalID customer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orders = (TextView)findViewById(R.id.tv_allOrders);
        dataBase = new OnlineShoppingDataBase(this);
        customer = new GlobalID() ;
        Cursor cursor = dataBase.showAllOrders(customer.getId());

        String data ="" ;
        Integer totalPrice =0;
        while (!cursor.isAfterLast())
        {
             data += cursor.getString(0);
             totalPrice += Integer.valueOf(cursor.getString(1));
             cursor.moveToNext();
        }

        String [] items = data.split("$");
        data ="" ;
        for (int i=0 ;i<items.length ;i++)
        {
            data += items[i]+"\n" ;
        }
        orders.setText(data+"\n with total price \n"+String.valueOf(totalPrice));

    }
}
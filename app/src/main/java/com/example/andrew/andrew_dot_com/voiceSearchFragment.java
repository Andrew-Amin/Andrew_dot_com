package com.example.andrew.andrew_dot_com;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class voiceSearchFragment extends Fragment {
    private int voiceCode = 1;
    TextView voiceResult;
    OnlineShoppingDataBase dataBase;
    Integer quantity;
    ListView Products;
    CustomListView customListView;
    ArrayList<String> ListItemName;
    ArrayList<Integer> ListItemPrice;
    ArrayList<Integer> tempListItemImagesID;
    ArrayList<Integer> ListItemQuantity;
    ArrayList<Integer> tempQuantity;
    double totalPrice = 0;
    String intent_id;
    boolean em = false;
    GlobalID customer;

    Integer[] ListItemImagesID = {
            R.drawable.apple_dock, R.drawable.apple_iphone_x1, R.drawable.apple_iphone_x2, R.drawable.apple_macbook1, R.drawable.apple_macbook2, R.drawable.apple_tv_bg, R.drawable.apple_watch1, R.drawable.apple_watch2,
            R.drawable.innovative_charger, R.drawable.innovative_cup_stand, R.drawable.innovative_modeling, R.drawable.innovative_mugg1, R.drawable.innovative_mugg2, R.drawable.innovative_mugg3,
            R.drawable.kitchen_coffe_machine1, R.drawable.kitchen_coffe_machine2, R.drawable.kitchen_electrical_presstor1, R.drawable.kitchen_electrical_presstor2, R.drawable.kitchen_electrical_presstor3, R.drawable.kitchen_mixer1, R.drawable.kitchen_mixer2,
            R.drawable.laptop1, R.drawable.laptop2, R.drawable.laptop3, R.drawable.laptop4, R.drawable.laptop5, R.drawable.laptop6, R.drawable.laptop7,
            R.drawable.leather_bag1, R.drawable.leather_bag2, R.drawable.leather_bag3, R.drawable.leather_bag4, R.drawable.leather_bag5, R.drawable.leather_gloves1, R.drawable.leather_gloves2, R.drawable.leather_shoes1, R.drawable.leather_shoes2, R.drawable.leather_shoes3, R.drawable.leather_shoes4, R.drawable.leather_shoes5, R.drawable.leather_waistband1, R.drawable.leather_waistband2, R.drawable.leather_waistband3, R.drawable.leather_wallet1, R.drawable.leather_wallet2, R.drawable.leather_wallet3,
            R.drawable.makeup_iliner, R.drawable.makeup_mascara1, R.drawable.makeup_mascara2, R.drawable.makeup_rouge1, R.drawable.makeup_rouge2, R.drawable.makeup_rouge3, R.drawable.makeup_rouge4, R.drawable.makeup_skincare1, R.drawable.makeup_skincare2, R.drawable.makeup_skincare3,
            R.drawable.supplement1, R.drawable.supplement2, R.drawable.supplement3, R.drawable.supplement4, R.drawable.supplement5, R.drawable.supplement6, R.drawable.supplement7, R.drawable.supplement8, R.drawable.supplement9
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.voice_search_fragment, container, false);

        dataBase = new OnlineShoppingDataBase(getActivity().getApplicationContext());
        Button btnBuy = (Button) root.findViewById(R.id.btn_Buy_v);
        ImageButton btnSearch = (ImageButton) root.findViewById(R.id.btn_search_voice);
        final TextView tv_price = (TextView) root.findViewById(R.id.tv_totalPrice_v);
        voiceResult = (TextView) root.findViewById(R.id.tv_voice_search_result);
        Products = (ListView) root.findViewById(R.id.lv_productsList_type_v);
        customer = new GlobalID();
        init();
        intent_id = getActivity().getIntent().getExtras().getString("id");

        //check edit of make
        if (intent_id.equals("editOrder") || intent_id.equals("showOrder")) {
            Cursor cursor;
            String tempName;
            totalPrice = 0;
            Integer temp;
            em = true;
            for (int i = 0; i < customer.cartItems.size(); i++) {
                // [0] name , [1] price , [2] ImageID , [3] quantity
                tempName = customer.getSelectedProducts().get(i);
                cursor = dataBase.FetchProducts(tempName);
                temp = customer.cartItems.get(cursor.getString(0));

                ListItemName.add(cursor.getString(0));
                ListItemPrice.add(Integer.parseInt(cursor.getString(1)));
                tempListItemImagesID.add(ListItemImagesID[Integer.parseInt(cursor.getString(2))]);
                ListItemQuantity.add(Integer.parseInt(cursor.getString(3)));
                tempQuantity.add(temp);
                totalPrice += Integer.parseInt(cursor.getString(1)) * temp;
            }
            tv_price.setText(String.valueOf(totalPrice));
            customListView = new CustomListView(getActivity(), ListItemName, ListItemPrice, tempListItemImagesID, tempQuantity);
            Products.setAdapter(customListView);
        }

//search method [voice]
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent_id.equals("showOrder")) {
                    Toast.makeText(getActivity().getApplicationContext(), "GO to EditOrder activity", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, voiceCode);
            }
        });

        Products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (intent_id.equals("showOrder")) {
                    Toast.makeText(getActivity().getApplicationContext(), "GO to EditOrder activity", Toast.LENGTH_SHORT).show();
                    return;
                }

                TextView temp = (TextView) view.findViewById(R.id.tv_quantity);
                TextView temp2 = (TextView) view.findViewById(R.id.tv_productName_id);
                TextView temp3 = (TextView) view.findViewById(R.id.tv_productPrice_id);

                quantity = Integer.parseInt(temp.getText().toString().trim());
                quantity++;
                ListItemQuantity.set(position, ListItemQuantity.get(position) - 1);

                // if (quantity > dataBase.getProductQuantity(temp2.getText().toString().trim()))
                if (ListItemQuantity.get(position) < 0) {
                    ListItemQuantity.set(position, ListItemQuantity.get(position) + 1);
                    Toast.makeText(getActivity().getApplicationContext(), "Stock is now empty", Toast.LENGTH_SHORT).show();
                    quantity--;
                    temp.setText(String.valueOf(quantity));
                    return;
                }
                //dataBase.UpdateProductQuantity(temp2.getText().toString().trim() , q) ;
                temp.setText(String.valueOf(quantity));

                if (customer.cartItems.containsKey(temp2.getText().toString().trim())) {
                    Integer x = customer.cartItems.get(temp2.getText().toString().trim());
                    customer.cartItems.remove(temp2.getText().toString().trim());
                    customer.cartItems.put(temp2.getText().toString().trim(), x + 1);
                } else {
                    customer.cartItems.put(temp2.getText().toString().trim(), Integer.valueOf(temp.getText().toString().trim()));
                    customer.addToCart(temp2.getText().toString().trim());
                }


                totalPrice += Double.parseDouble(temp3.getText().toString().trim());
                tv_price.setText(String.valueOf(totalPrice) + " $");


            }
        });

        Products.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (intent_id.equals("showOrder")) {
                    Toast.makeText(getActivity().getApplicationContext(), "GO to EditOrder activity", Toast.LENGTH_SHORT).show();
                    return true;
                }

                TextView temp = (TextView) view.findViewById(R.id.tv_quantity);
                TextView temp2 = (TextView) view.findViewById(R.id.tv_productName_id);
                TextView temp3 = (TextView) view.findViewById(R.id.tv_productPrice_id);

                quantity = Integer.parseInt(temp.getText().toString().trim());
                quantity--;
                if (quantity < 0) {
                    quantity++;
                    return true;
                }
                //ListItemQuantity[position]+=quantity;

                //dataBase.UpdateProductQuantity(temp.getText().toString().trim() , q) ;
                temp.setText(String.valueOf(quantity));

                if (customer.cartItems.containsKey(temp2.getText().toString().trim())) {
                    Integer x = customer.cartItems.get(temp2.getText().toString().trim());
                    customer.cartItems.remove(temp2.getText().toString().trim());

                    if (x - 1 > 0)
                        customer.cartItems.put(temp2.getText().toString().trim(), x - 1);

                    else
                        customer.removeFromCart(temp2.getText().toString().trim());
                }

                totalPrice -= Double.parseDouble(temp3.getText().toString().trim());
                tv_price.setText(String.valueOf(totalPrice) + " $");

                return true;
            }
        });

        // FeedContainers(ProName.getText().toString());
        //for (int i=0 ; i<ListItemQuantity.length ; i++)
        //  totalPrice += ListItemQuantity[i]*ListItemPrice[i];
        // tv_price.setText(String.valueOf(totalPrice));

        btnBuy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (intent_id.equals("showOrder")) {
                    String CustAddress = customer.getLocation();

                    //update quantities after the client take up his items
                    //needs to optimize
                    if (customer.cartItems.size() <= 0)
                        Toast.makeText(getActivity().getApplicationContext(), "you has not select any product to add it to cart", Toast.LENGTH_SHORT).show();
                    else {
                        String cart = "Your Order: { / ";
                        for (int i = 0; i < customer.cartItems.size(); i++) {
                            dataBase.UpdateProductQuantity(customer.getSelectedProducts().get(i), customer.cartItems.get(customer.getSelectedProducts().get(i)));
                            cart += customer.getSelectedProducts().get(i) + " / ";
                        }
                        cart += "}";
                        boolean done = dataBase.makeOrder(customer.getSelectedProducts(), customer.cartItems, totalPrice, customer.getId(), CustAddress);

                        if (done) {
                            Toast.makeText(getActivity().getApplicationContext(), "Order has been successfully saved", Toast.LENGTH_LONG).show();
                            customer.clearCart();
                            customer.cartItems.clear();
                        } else
                            Toast.makeText(getActivity().getApplicationContext(), "oops ! , Something went wrong with your order , please try again later", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else
                    return false;
            }
        });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent_id.equals("showOrder")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Long Press to save the order", Toast.LENGTH_SHORT).show();
                } else {
                    if (customer.cartItems.size() <= 0)
                        Toast.makeText(getActivity().getApplicationContext(), "you has not select any product to add it to cart", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Consider as honer to serve you ^_^", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), Home.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == voiceCode && resultCode == getActivity().RESULT_OK) {
            ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            voiceResult.setText(res.get(0));

            if (!em) {
                init();
                if (customer.cartItems.isEmpty())
                    totalPrice = 0;
            }
            Cursor cursor = dataBase.getProductInfo(res.get(0));
            // [0] name , [1] price , [2] ImageID , [3] quantity

            if (cursor.getCount() <= 0)
                Toast.makeText(getActivity(), "NOT found", Toast.LENGTH_SHORT).show();

            else {
                ListItemName = new ArrayList<String>();
                ListItemPrice = new ArrayList<Integer>();
                tempListItemImagesID = new ArrayList<Integer>();
                ListItemQuantity = new ArrayList<Integer>();
                tempQuantity = new ArrayList<Integer>();

                while (!cursor.isAfterLast()) {
                    ListItemName.add(cursor.getString(0));
                    ListItemPrice.add(Integer.parseInt(cursor.getString(1)));
                    tempListItemImagesID.add(ListItemImagesID[Integer.parseInt(cursor.getString(2))]);
                    ListItemQuantity.add(Integer.parseInt(cursor.getString(3)));
                    tempQuantity.add(0);
                    cursor.moveToNext();
                }
                customListView = new CustomListView(getActivity(), ListItemName, ListItemPrice, tempListItemImagesID, tempQuantity);
                Products.setAdapter(customListView);

            }
        }
    }

    public void init() {
        ListItemName = new ArrayList<>();
        ListItemPrice = new ArrayList<>();
        tempListItemImagesID = new ArrayList<>();
        ListItemQuantity = new ArrayList<>();
        tempQuantity = new ArrayList<>();
    }
}


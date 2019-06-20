package com.example.andrew.andrew_dot_com;

import java.util.ArrayList;
import java.util.Hashtable;

public class GlobalID  {
    private static   int id ;
    private static ArrayList<String> Cart=new ArrayList<>() ;
    private static String location = "21 , Ali ebn Abi Taleb ST , ShobraMasr , Cairo , Egypt" ;
    private static String email ;
    static boolean saved = false ;
    static Hashtable <String , Integer> cartItems = new Hashtable<>();

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public int getId() { return GlobalID.id ; }

    public void setId(int id) { GlobalID.id = id; }

    public ArrayList <String> getSelectedProducts (){ return GlobalID.Cart; }

    public void setSelectedProducts(ArrayList<String> Cart) { GlobalID.Cart = Cart; }

    public void addToCart (String ProductName) { GlobalID.Cart.add(ProductName); }

    public void removeFromCart (String ProductName) {GlobalID.Cart.remove(ProductName); }

    public int CartLength (){return GlobalID.Cart.size();}

    public void clearCart() {GlobalID.Cart.clear();}

    public String getLocation() { return GlobalID.location; }

    public void setLocation(String location) { GlobalID.location = location; }

    public static String getEmail() { return GlobalID.email; }

    public static void setEmail(String email) { GlobalID.email = email; }
}

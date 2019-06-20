package com.example.andrew.andrew_dot_com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Hashtable;

public class OnlineShoppingDataBase extends SQLiteOpenHelper {

    SQLiteDatabase onlineShoppingDataBase ;

    public OnlineShoppingDataBase (Context context){
        super(context , "OnlineShoppingDataBase", null , 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Customers (CustID integer primary key autoincrement  , CutName text not null , Username text not null, Password text not null, Gender text not null , Birthdate text , Job text , secret text)");
        db.execSQL("create table Categories (CatID integer primary key autoincrement, CatName text not null)");
        db.execSQL(" create table Products (ProID integer primary key autoincrement , ProName text not null , Price real not null , Quantity integer not null , ImageID integer ,  CatID integer not null , foreign key (CatID) references  Categories (CatID))");
        db.execSQL("create table Orders (OrdID integer primary key autoincrement , OrdData  text not null , TotalPrice real not null , CustID  integer not null , Address  text not null , foreign key (CustID) references Customers (CustID))");
        db.execSQL("create table OrderDetails (OrdID integer not null , ProID integer not null  , Quantity text not null , foreign key (OrdID) references Orders(OrdID) , primary key (OrdID,ProID) ,  foreign key (ProID) references Products (ProID))");

        db.execSQL("insert into Categories (CatName) values ('apple'),('innovative'),('kitchen'),('laptop'),('leather'),('makeup'),('supplement')");

        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('apple_dock',540,50,0,1),('apple_iphone_x1',1200,50,1,1),('apple_iphone_x2',1300,50,2,1),('apple_macbook1',2400,50,3,1),('apple_macbook2',2500,50,4,1),('apple_tv_bg',1600,40,5,1),('apple_watch1',650,40,6,1),('apple_watch2',550,40,7,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('innovative_charger',50,40,8,2),('innovative_stand',10,40,9,2),('innovative_modeling',5,40,10,2),('innovative_mugg1',7,40,11,2),('innovative_mugg2',7,40,12,2),('innovative_mugg3',7,40,13,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('kitchen_Coffee1',450,30,14,3),('kitchen_Coffee2',460,30,15,3),('kitchen_presstor1',350,30,16,3),('kitchen_presstor2',300,30,17,3),('kitchen_presstor3',320,30,18,3),('kitchen_mixer1',200,30,19,3),('kitchen_mixer2',200,30,20,3)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('laptop1',2000,30,21,4),('laptop2',2150,30,22,4),('laptop3',3000,30,23,4),('laptop4',3100,30,24,4),('laptop5',3200,30,25,4),('laptop6',2900,30,26,4),('laptop7',2950,30,27,4)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('leather_bag1',75,50,28,5),('leather_bag2',55,50,29,5),('leather_bag3',65,50,30,5),('leather_bag4',58,50,31,5),('leather_bag5',50,50,32,5),('leather_gloves1',20,50,33,5),('leather_gloves2',15,50,34,5),('leather_shoes1',50,50,35,5),('leather_shoes2',60,50,36,5),('leather_shoes3',55,50,37,5),('leather_shoes4',70,50,38,5),('leather_shoes5',80,50,39,5),('leather_waistband1',5,50,40,5),('leather_waistband2',5,50,41,5),('leather_waistband3',5,50,42,5),('leather_wallet1',5,50,43,5),('leather_wallet2',5,50,44,5),('leather_wallet3',5,50,45,5)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('makeup_iliner',3,50,46,6),('makeup_mascara1',4,50,47,6),('makeup_mascara2',6,50,48,6),('makeup_skincare3',10,50,49,6),('makeup_rouge1',6,50,50,6),('makeup_rouge2',8,50,51,6),('makeup_rouge3',4,50,52,6),('makeup_rouge4',7,50,53,6),('makeup_skincare1',10,50,54,6),('makeup_skincare2',11,50,55,6)");
        db.execSQL("insert into Products (ProName,Price,Quantity,ImageID,CatID) values ('supplement1',150,20,56,7),('supplement2',50,20,57,7),('supplement3',40,20,58,7),('supplement4',60,20,59,7),('supplement5',160,20,60,7),('supplement6',165,20,61,7),('supplement7',170,20,62,7),('supplement8',80,20,63,7),('supplement9',170,20,64,7)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.resetDB(db);
        onCreate(db);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------

    public int GetCustomerID (String CustomerName)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor= onlineShoppingDataBase.rawQuery("select CustID from Customers where CutName=?" , new String[] {CustomerName}) ;
        if (cursor !=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public String GetCustomerName (int CustomerID)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor= onlineShoppingDataBase.rawQuery("select  CutName from Customers where CustID=?" , new String[] {String.valueOf(CustomerID)}) ;
        if (cursor !=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor.getString(0);
    }

    public String GetCustomerEmail (int CustomerID)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor= onlineShoppingDataBase.rawQuery("select Username  from Customers where CustID=?" , new String[] {String.valueOf(CustomerID)}) ;
        if (cursor !=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor.getString(0);
    }

    public Cursor fetchAllCategories ()
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select CatName from Categories" , null);
        if (cursor !=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor ;
    }

    public Cursor SelectProducts (String CatName)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor ;

         if (CatName.equals("allCategories"))
             cursor = onlineShoppingDataBase.rawQuery("select ProName , Price , Quantity , ImageID  from Categories inner join Products on " +
                     "Categories.CatID = Products.CatID" ,null) ;
        else
         cursor = onlineShoppingDataBase.rawQuery("select ProName , Price , Quantity , ImageID  from Categories inner join Products on " +
                "Categories.CatID = Products.CatID where Categories.CatName=?" , new String[] {CatName}) ;

        if(cursor!=null) cursor.moveToFirst() ;
        onlineShoppingDataBase.close();
        return cursor ;
    }

    public Cursor FetchProducts (String name)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select ProName , Price , ImageID , Quantity  from Products where ProName = ?" ,
                new String[] {name});
        if(cursor!=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor;
    }

    public int getProductPrice (String ProductName)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select Price from Products where ProName=?" , new String[] {ProductName});
        if(cursor!=null)cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public int getProductQuantity (String ProductName)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select Quantity from Products where ProName=?" , new String[] {ProductName}) ;
        if (cursor!=null)cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public void UpdateProductQuantity (String ProductName , int quantity)
    {
        int x =this.getProductQuantity(ProductName) ;
        onlineShoppingDataBase=getWritableDatabase();
        ContentValues rew = new ContentValues();
        rew.put("Quantity" , x-quantity);
        onlineShoppingDataBase.update("Products" , rew , "ProName = ?" , new String[] {ProductName});
        onlineShoppingDataBase.close();
    }

    public int getProductImage (String ProductName)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select ImageID from Products where ProName=?" , new String[] {ProductName}) ;
        if (cursor!=null)cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return Integer.parseInt(cursor.getString(0));
    }

    public Cursor getProductInfo (String ProductName)
    {
        if(ProductName.equals(""))
            ProductName="_";

        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select ProName , Price , ImageID , Quantity , ProID, CatID from" +
                " Products where ProName like ?" , new String[] {'%'+ProductName+'%'});
        if(cursor!=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor;
    }

    public Cursor getAllProductInfo ()
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select ProName , Price , Quantity , ProID, CatID from Products" , null);
        if(cursor!=null) cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor;
    }

    public boolean makeOrder (ArrayList<String> names , Hashtable<String , Integer> cartItems , double totalPrice , int CustomerID  , String Address  )
    {
        String itemsString = "";
        String data ;
        for (int i=0 ; i<names.size() ; i++)
        {
            data = names.get(i)+"      ( "+String.valueOf(cartItems.get(names.get(i))+" )") ;
            itemsString+=data ;
        }

        ContentValues rew = new ContentValues();
        rew.put("OrdData" , itemsString);
        rew.put("TotalPrice" , totalPrice);
        rew.put("CustID" , CustomerID );
        rew.put("Address" , Address);
        onlineShoppingDataBase=getWritableDatabase();
        onlineShoppingDataBase.insert("Orders" , null , rew ) ;
        onlineShoppingDataBase.close();
        return true;
    }

    public Cursor showAllOrders (int CustomerID)
    {
        onlineShoppingDataBase=getReadableDatabase();
        //int CustomerID = this.GetCustomerID(CustomerName) ;
        Cursor cursor = onlineShoppingDataBase.rawQuery("select OrdData , TotalPrice from Orders where CustID =?" , new String[]{String.valueOf(CustomerID)});
        if (cursor!=null)cursor.moveToFirst();
        onlineShoppingDataBase.close();
        return cursor;
    }

    private int countUsers ()
    {
        //optimize by "select count (CustID) from Customers"
        int id =0 ;
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select CustID from Customers " , null);
        if (cursor!=null)cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            id++;
            cursor.moveToNext();
        }
        return id ;
    }

    public int SignUp (ArrayList<String> data )
    {
        onlineShoppingDataBase=getWritableDatabase();
        ContentValues CustomerDate = new ContentValues() ;
        CustomerDate.put("CutName" , data.get(0) );
        CustomerDate.put("Username" , data.get(1));
        CustomerDate.put("Password" , data.get(2));
        CustomerDate.put("Job" , data.get(3));
        CustomerDate.put("Gender" , data.get(4));
        CustomerDate.put("Birthdate" , data.get(5));
        CustomerDate.put("secret" , data.get(6));

        onlineShoppingDataBase.insert("Customers" , null , CustomerDate) ;
        onlineShoppingDataBase.close();

        // if dataBase insert first return this.countUsers () ;
        //else if dataBase insert after return this.countUsers()+1;
        return this.countUsers()+1;
    }

    public  int SignIN(String userName , String Password)
    {
        onlineShoppingDataBase=getReadableDatabase();
        Cursor cursor = onlineShoppingDataBase.rawQuery("select CustID from Customers where Username=? and " +
                "Password=?" ,new String[]{userName , Password});
        if (cursor!=null)cursor.moveToFirst();

        if(cursor.getCount()>0)
            return Integer.parseInt(cursor.getString(0));
        else
            return -1;
    }

    public String recoveryPassword (String [] info)
    {
        //[0] email , [1] birthday , [2] job , [3] secret
        onlineShoppingDataBase = getWritableDatabase() ;
        Cursor cursor = onlineShoppingDataBase.
                rawQuery("select Password from Customers where Username=? and Birthdate=? and Job=? and secret=?" , info);
        if (cursor!=null)cursor.moveToFirst();

        return cursor.getString(0);
    }

    public void deleteAccount (int CustID)
    {
        onlineShoppingDataBase = getWritableDatabase();
        onlineShoppingDataBase.delete("Customers" , "CustID=?" , new String[] {String.valueOf(CustID)});
        onlineShoppingDataBase.close();
    }

    public void resetDB (SQLiteDatabase db)
    {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists OrderDetails");
    }

}

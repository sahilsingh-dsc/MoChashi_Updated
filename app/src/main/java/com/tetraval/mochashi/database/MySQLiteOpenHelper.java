package com.tetraval.mochashi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.tetraval.mochashi.haatgrocerymodule.data.models.CartProductModel;
import java.util.ArrayList;
public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;
    Context ctx;

    public static final String  DB_name="growsack";
    public  static final int  DB_version=1;

 /*   public  static final String TB_name="ProfilePic";
    public static final String id="_id";
    public static final String userId="userid";
    public static final String image="image";*/

    public  static final String TB_name2="Cart";
    public static final String id1="_id";
    public static final String Product_id="productid";
    public static final String user_id="userid";
    public static final String Prduct_Prize="productoriginalprize";
    public static final String Product_Name="productname";
    public static final String Product_Seleted_Qty="productselectedqty";
    public static final String Product_Offer_Prize="productofferprize";
    public static final String Product_Total_Prize="producttotalprize";
    public static final String Product_Qty="productqty";
    public static final String Product_Discount="discount";
    public static final String Product_cat="productcat";
    public static final String Product_Image="productimage";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_name, null, DB_version);
        db=this.getWritableDatabase();
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       /* db.execSQL("CREATE TABLE "+TB_name
                +" ( "
                +id +" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +userId+" TEXT, "
                +image+" BLOB "
                +" ); "
        );*/

        db.execSQL("CREATE TABLE "+TB_name2
                +" ( "
                +id1 +" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +user_id+" TEXT, "
                +Product_id+" TEXT, "
                +Prduct_Prize+" TEXT, "
                +Product_Name+" TEXT, "
                +Product_Seleted_Qty+" TEXT, "
                +Product_Offer_Prize+" TEXT, "
                +Product_Total_Prize+" TEXT, "
                +Product_Qty+" TEXT ,"
                +Product_Discount+" TEXT ,"
                +Product_cat+" TEXT ,"
                +Product_Image+" TEXT "
                +" ); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE "+TB_name+ " IF EXISTS ");
        //db.execSQL("DROP TABLE "+TB_name2+ " IF EXISTS ");
    }

   /* public long saveDocument(String userid, byte[] imageInByte) {

        String cols[] = {userId, image};
      *//*  Cursor c = db.query(TB_name, cols, userId + "=?", new String[]{userid}, null, null, null);
        if (c.moveToFirst()) {
            Toast.makeText(ctx,""+c.getCount(),Toast.LENGTH_LONG).show();
            ContentValues val = new ContentValues();
            val.put(userId, userid);
            val.put(image, imageInByte);
            long y=db.update(TB_name, val,userId+"=?",new String[]{userid});
            return y;


        } else {*//*

        ContentValues val = new ContentValues();
        val.put(userId, userid);
        val.put(image, imageInByte);

        return db.insert(TB_name, null, val);
        //     }

    }

    public byte[] getPic(String userid){
        byte []z=null;
        String cols[]={image};
        Cursor c = db.query(TB_name, cols,
                userId+"= ?", new String[] {userid}, null, null, null);
        if(c.moveToFirst()){
            z=c.getBlob(0);
            return z;
        }
        else{
            return z;
        }


    }*/
    public long addToCart(String userid,String productid,String productprize,String productname,String productselectedqty,String productofferprize,String producttotalprize,String productqty,String discount,String productcat,String productimage) {
        // TODO Auto-generated method stub
        ContentValues val=new ContentValues();
        val.put(user_id, userid);
        val.put(Product_id, productid);
        val.put(Prduct_Prize, productprize);
        val.put(Product_Name,productname);
        val.put(Product_Seleted_Qty,productselectedqty);
        val.put(Product_Offer_Prize,productofferprize);
        val.put(Product_Total_Prize,producttotalprize);
        val.put(Product_Qty,productqty);
        val.put(Product_Discount,discount);
        val.put(Product_cat,productcat);
        val.put(Product_Image,productimage);


        long y=db.insert(TB_name2, null, val);
        return y;
    }

    public ArrayList<CartProductModel> getCarts(String userid){

        final ArrayList<CartProductModel> data = new ArrayList<>();
        String cols[]={Product_id,Prduct_Prize,Product_Name,Product_Seleted_Qty,Product_Offer_Prize,Product_Total_Prize,id1,Product_Qty,Product_Discount,Product_cat,Product_Image};
        Cursor c=db.query(TB_name2, cols, user_id+"=?", new String[]{userid}, null, null, null);
        if(c!=null){

            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                CartProductModel c1;
                c1 = new CartProductModel(c.getString(0),c.getString(1),c.getString(2),
                        c.getString(3),c.getString(4),c.getString(5),c.getString(7),c.getString(8),c.getString(9),c.getString(10));
                c1.setCart_id(c.getString(6));
                data.add(c1);
            }
            return data;
        }
        else{
            return data;
        }
    }

    public long deleteItem(String id) {
        long y = db.delete(TB_name2,id1+"=?",new String[]{id});
        return y;
    }
    public long deleteProductItem(String id) {
        long y = db.delete(TB_name2,Product_id+"=?",new String[]{id});
        return y;
    }
    public long deleteAll() {
        long y = db.delete(TB_name2,null,null);
        return y;
    }

    public void updateData(String id, String quantity, String total_price, String discount){
        // this is a key value pair holder used by android's SQLite functions
        ContentValues values = new ContentValues();
        values.put(Product_Seleted_Qty, quantity);
        values.put(Product_Total_Prize, total_price);
        values.put(Product_Discount, discount);
        // ask the database object to update the database row of given rowID
        try {db.update(TB_name2, values, Product_id + "=" + id, null);}
        catch (Exception e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TB_name2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int Check(String productid){
        String cols[]={Product_id,Prduct_Prize,Product_Name,Product_Seleted_Qty,Product_Offer_Prize,Product_Total_Prize,id1,Product_Qty,Product_Discount,Product_cat,Product_Image};
        Cursor c=db.query(TB_name2, cols, Product_id+"=?", new String[]{productid}, null, null, null);
        Log.e("","Cursor Count : " + c.getCount());
        int count = c.getCount();
        c.close();
        return count;
      /* if(c.getCount()>0){
           //PID Found
       }else{
           //PID Not Found
       }
       c.close();*/

    }


}

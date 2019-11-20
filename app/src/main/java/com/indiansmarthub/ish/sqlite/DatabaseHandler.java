package com.indiansmarthub.ish.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.indiansmarthub.ish.model.Addtocart;
import com.indiansmarthub.ish.model.ModelLocalCart;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "ISH";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            boolean dbExist = checkDataBase();
            if (!dbExist) {
                getWritableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File("/data/data/com.breezewaytech.ish/databases/ISH");
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CART = "CREATE TABLE cart (auto_id integer primary key autoincrement, productqty TEXT,productid TEXT,isconfigurable TEXT,superattribute TEXT,sku TEXT,price TEXT,valueIndex TEXT,productName TEXT,labelOfProduct TEXT,image TEXT)";
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    /*public void exportDataBase() throws IOException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        FileInputStream dbFile = new FileInputStream("/data/data/com.skykart.skykart/databases/skykart");
        String outFileName = "sdcard/" + formattedDate + DATABASE_NAME + ".db";
        String outFileName1 = "sdcard/";
        File yourFile = new File(outFileName1);
        if (!yourFile.exists()) {
            yourFile.mkdir();
        }
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = dbFile.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();

    }*/

    public long addToCart(String productId, String productQty,  String Sku, String Price, String productName, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productid", productId);
        values.put("productqty", productQty);
        values.put("sku", Sku);
        values.put("price", Price);
        values.put("productName", productName);
        values.put("image", image);
        long i = db.insert("cart", null, values);
        db.close();
        return i;
    }

    public long updateCart(String productId, String productQty,String SKU, String price, String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productqty", productQty);
        values.put("price", price);
        values.put("productName", productName);

        return (long) db.update("cart", values, "productid = '" + productId + "' and sku = '" + SKU + "'", null);
    }

    public long updateCartConfigurable(String productId, String productQty,String SKU,String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productqty", productQty);
        values.put("productName", productName);

        return (long) db.update("cart", values, "productid = '" + productId + "' and sku = '" + SKU + "'", null);
    }

    public ArrayList<Addtocart> getCart() {
        ArrayList<Addtocart> localCarts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Addtocart modelLocalCart = new Addtocart();
                    modelLocalCart.setAutoId(cursor.getString(cursor.getColumnIndex("auto_id")));
                    modelLocalCart.setQty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("productqty"))));
                    modelLocalCart.setId(cursor.getString(cursor.getColumnIndex("productid")));
                    //   modelLocalCart.setIsConfigurable(cursor.getString(cursor.getColumnIndex("isconfigurable")));
                    //   modelLocalCart.setSuperAttribute(cursor.getString(cursor.getColumnIndex("superattribute")));
                    modelLocalCart.setSku(cursor.getString(cursor.getColumnIndex("sku")));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    float f = Float.parseFloat(price);
                    int pr= (int) Float.parseFloat(cursor.getString(cursor.getColumnIndex("price")));
                    modelLocalCart.setPrice(pr);
                    modelLocalCart.setPricefloat(f);
                    //   modelLocalCart.setValueIndex(cursor.getString(cursor.getColumnIndex("valueIndex")));
                    modelLocalCart.setName(cursor.getString(cursor.getColumnIndex("productName")));
                    //    modelLocalCart.setLabel(cursor.getString(cursor.getColumnIndex("labelOfProduct")));
                    modelLocalCart.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    localCarts.add(modelLocalCart);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return localCarts;
    }



/*
    public ArrayList<ModelLocalCart> getCart() {
        ArrayList<ModelLocalCart> localCarts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ModelLocalCart modelLocalCart = new ModelLocalCart();
                modelLocalCart.setAutoId(cursor.getString(cursor.getColumnIndex("auto_id")));
                modelLocalCart.setProductQty(cursor.getString(cursor.getColumnIndex("productqty")));
                modelLocalCart.setProductId(cursor.getString(cursor.getColumnIndex("productid")));
                modelLocalCart.setIsConfigurable(cursor.getString(cursor.getColumnIndex("isconfigurable")));
                modelLocalCart.setSuperAttribute(cursor.getString(cursor.getColumnIndex("superattribute")));
                modelLocalCart.setSku(cursor.getString(cursor.getColumnIndex("sku")));
                modelLocalCart.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                modelLocalCart.setValueIndex(cursor.getString(cursor.getColumnIndex("valueIndex")));
                modelLocalCart.setName(cursor.getString(cursor.getColumnIndex("productName")));
                modelLocalCart.setLabel(cursor.getString(cursor.getColumnIndex("labelOfProduct")));
                modelLocalCart.setImage(cursor.getString(cursor.getColumnIndex("image")));
                localCarts.add(modelLocalCart);
            } while (cursor.moveToNext());
        }
        return localCarts;
    }
*/

    public int cartCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (count == 0) {
                    count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productqty")));
                } else {
                    count = count + Integer.parseInt(cursor.getString(cursor.getColumnIndex("productqty")));
                }
            } while (cursor.moveToNext());
        }
        return count;
    }

    public boolean checkProduct(String productId, String SKU) {
        boolean flag = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String checkQuery = "SELECT * FROM cart WHERE productid = '" + productId + "' and sku = '" + SKU + "'";
        Cursor c = db.rawQuery(checkQuery, null);
        if (c.moveToFirst()) {
            flag = true;
        }
        return flag;
    }

    public int getQty(String productId, String SKU) {
        int qtyCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String checkQuery = "SELECT * FROM cart WHERE productid = '" + productId + "' and sku = '" + SKU + "'";
        Cursor c = db.rawQuery(checkQuery, null);
        if (c.moveToFirst()) {
            qtyCount = Integer.parseInt(c.getString(c.getColumnIndex("productqty")));
        }
        return qtyCount;
    }

    public boolean checkProductConfigurable(String productId, String SKU, String valueIndex) {
        boolean flag = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String checkQuery = "SELECT * FROM cart WHERE productid = '" + productId + "' and sku = '" + SKU + "' and valueIndex = '" + valueIndex + "' ";
        Cursor c = db.rawQuery(checkQuery, null);
        if (c.moveToFirst()) {
            flag = true;
        }
        return flag;
    }

    public int getQtyConfigurable(String productId, String SKU, String valueIndex) {
        int qtyCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String checkQuery = "SELECT * FROM cart WHERE productid = '" + productId + "' and sku = '" + SKU + "' and valueIndex = '" + valueIndex + "' ";
        Cursor c = db.rawQuery(checkQuery, null);
        if (c.moveToFirst()) {
            qtyCount = Integer.parseInt(c.getString(c.getColumnIndex("productqty")));
        }
        return qtyCount ;
    }

    public void getProductIdFromAutoID(String product_id, String valueIndex) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "productid = " + product_id + " AND  valueIndex = "+ valueIndex , null);

    }
    public void getProductIdFromAutoIDSimple(String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "productid = " + product_id , null);

    }
    public void deleteProductFromCart(String autoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "auto_id = " + autoId, null);
    }

    public void emptyCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", null, null);
    }

    public String getProductQty(String productId, String SKU, String valueIndex) {
        String qty = "0" ;
        SQLiteDatabase db = this.getReadableDatabase();
        String checkQuery = "SELECT * FROM cart WHERE productid = '" + productId + "' and sku = '" + SKU + "' and valueIndex = '" + valueIndex + "' ";
        Cursor c = db.rawQuery(checkQuery, null);
        if (c.moveToFirst()) {
            qty = c.getString(c.getColumnIndex("productqty"));
        }
        return qty;

    }
}

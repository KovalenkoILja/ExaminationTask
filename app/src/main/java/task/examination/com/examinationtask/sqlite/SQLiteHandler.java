package task.examination.com.examinationtask.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.models.Order;
import task.examination.com.examinationtask.models.Order_Products;
import task.examination.com.examinationtask.models.Product;

public class SQLiteHandler
{
    private static final String TAG = "SQLiteHandler";

    public static ArrayList<Product> GetAllProducts(ShopDatabase db, Context context)
    {
        if (db.DATABASE != null)
        {
            ArrayList<Product> products = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            String query = context.getResources().getString(R.string.select_all_products);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor != null)
            {
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        product.setID(cursor.getInt(
                                cursor.getColumnIndex("_id")));
                        product.setName(cursor.getString(
                                cursor.getColumnIndex("name")));
                        product.setDescription(cursor.getString(
                                cursor.getColumnIndex("description")));
                        product.setPrice(cursor.getDouble(
                                cursor.getColumnIndex("price")));
                        products.add(product);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                return products;
            }
        }
        Log.i(TAG, "DATABASE == null" );
        return null;
    }

    public static ArrayList<Order> GetAllOrders(ShopDatabase db, Context context)
    {
        if (db.DATABASE != null)
        {
            ArrayList<Order> orders = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            String query = context.getResources().getString(R.string.select_all_orders);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    do {
                        Order order = new Order();

                        order.setID(cursor.getInt(
                                cursor.getColumnIndex("_id")));
                        order.setDatetime(cursor.getString(
                                cursor.getColumnIndex("datetime")));
                        orders.add(order);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                return orders;
            }
        }

        Log.i(TAG, "DATABASE == null" );
        return null;
    }

    public static ArrayList<Order_Products> GetAllOrder_Products(ShopDatabase db, Context context)
    {
        if (db.DATABASE != null)
        {
            ArrayList<Order_Products> order_products = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            String query = context.getResources().getString(R.string.select_all_orders_products);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    do {
                        Order_Products order_product = new Order_Products();

                        order_product.setID(cursor.getInt(
                                cursor.getColumnIndex("_id")));
                        order_product.setOrderID(cursor.getInt(
                                cursor.getColumnIndex("order_id")));
                        order_product.setProductID(cursor.getInt(
                                cursor.getColumnIndex("product_id")));
                        order_product.setProductQuantity(cursor.getInt(
                                cursor.getColumnIndex("product_quantity")));

                        order_products.add(order_product);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                return order_products;
            }
        }

        Log.i(TAG, "DATABASE == null" );
        return null;
    }

    public static boolean InsertProduct(ShopDatabase db, Product product)
    {
        if (db.DATABASE != null)
        {
            try {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("name", product.getName());
                values.put("description", product.getDescription() );
                values.put("price", product.getPrice());

                long query = sqLiteDatabase.insert("PRODUCTS", null, values);

                return query != 0;
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
               return false;
            }
        }
        Log.i(TAG, "DATABASE == null" );
        return false;
    }

    public static boolean UpdateProduct(ShopDatabase db, Product product)
    {
        if (db.DATABASE != null)
        {
            try {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("name", product.getName());
                values.put("description", product.getDescription() );
                values.put("price", product.getPrice());

                long query = sqLiteDatabase.update("PRODUCTS",
                        values,
                        "_id =?",
                        new String[] {Integer.toString(product.getID() )} );

                return query != 0;
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
                return false;
            }
        }
        Log.i(TAG, "DATABASE == null" );
        return false;
    }

    public static boolean DeleteProduct(ShopDatabase db, Product product)
    {
        if (db.DATABASE != null)
        {
            try {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                long query = sqLiteDatabase.delete("PRODUCTS",
                        "_id =?",
                        new String[] {Integer.toString(product.getID() )} );

                return query != 0;
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
                return false;
            }
        }

        Log.i(TAG, "DATABASE == null" );
        return false;
    }

    public static boolean InsertOrder(ShopDatabase db, Context context,
                                      Order order, ArrayList<Product> products)
    {
        if (db.DATABASE != null)
        {
            try {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("datetime", order.getDatetime());

                long insert = sqLiteDatabase.insert("ORDERS", null, values);

                if(insert != 0)
                {
                    String query = context.getResources().getString(R.string.select_last_inserted_id);
                    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                    if (cursor != null)
                    {
                        int newOrderID = 0;
                        if (cursor.moveToFirst())
                        {
                            do {
                                String tableName = cursor.getString(
                                        cursor.getColumnIndex("name"));
                                if(tableName.equals("ORDERS"))
                                {
                                    newOrderID = cursor.getInt(
                                            cursor.getColumnIndex("seq"));
                                }
                            }
                            while (cursor.moveToNext());
                        }
                        cursor.close();
                        if(newOrderID != 0)
                        {
                            for (Product p: products)
                            {
                                values = new ContentValues();
                                values.put("order_id", newOrderID);
                                values.put("product_id", p.getID());
                                values.put("product_quantity", p.getQuantity());
                                insert = sqLiteDatabase.insert("ORDER_PRODUCTS", null, values);
                                if (insert == 0) return false;
                            }
                            return true;
                        }
                        else return false;
                    }
                    else return false;
                }
                else return false;
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
                return false;
            }
        }
        Log.i(TAG, "DATABASE == null" );
        return false;
    }

    public static boolean DeleteOrder(ShopDatabase db, Order order)
    {
        if (db.DATABASE != null)
        {
            try {
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                long query = sqLiteDatabase.delete("ORDERS",
                        "_id =?",
                        new String[] {Integer.toString(order.getID() )} );

                return query != 0;
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
                return false;
            }
        }

        Log.i(TAG, "DATABASE == null" );
        return false;
    }
}

package task.examination.com.examinationtask.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.adaptors.OrderViewAdaptor;
import task.examination.com.examinationtask.models.Order;
import task.examination.com.examinationtask.models.Order_Products;
import task.examination.com.examinationtask.models.Product;
import task.examination.com.examinationtask.sqlite.SQLiteHandler;
import task.examination.com.examinationtask.sqlite.ShopDatabase;

import static task.examination.com.examinationtask.sqlite.SQLiteHandler.GetAllOrder_Products;
import static task.examination.com.examinationtask.sqlite.SQLiteHandler.GetAllOrders;
import static task.examination.com.examinationtask.sqlite.SQLiteHandler.GetAllProducts;

public class ShowOrderListActivity extends AppCompatActivity implements OrderViewAdaptor.OrderItemClickListener
{
    private static final String TAG = ShowOrderListActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private OrderViewAdaptor adapter;
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Order_Products> order_products = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_list);

        if (GetLists())
        {
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            adapter = new OrderViewAdaptor( orders, order_products, products);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setClickListener(this);
        }
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Order order = adapter.getItem(position);
        String msg = "Заказ № " + order.getID() + " от " + order.getDatetime();

        new AlertDialog.Builder(this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Удалить заказ")
                .setMessage("Удалить " + msg + " ?")
                .setPositiveButton("Продолжить", (dialog, which) -> {
                    if(RemoveOrderFromDB(order))
                    {
                        orders.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this,
                                "Заказ " + msg + " успешно удален",
                                Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(this,
                            "Заказ " + msg + " не удалось удалить!",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    public boolean GetLists()
    {
        try
        {
            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();

            orders = GetAllOrders(myShopDB,  this);
            order_products = GetAllOrder_Products(myShopDB,  this);
            products = GetAllProducts(myShopDB,  this);

        }
        catch (Exception ex)
        {
            Toast.makeText( this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }
        return true;
    }

    public boolean RemoveOrderFromDB(Order order)
    {
        try
        {
            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();

            return SQLiteHandler.DeleteOrder(myShopDB, order);
        }
        catch (Exception ex)
        {
            Toast.makeText( this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }
    }
}

package task.examination.com.examinationtask.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.adaptors.ProductViewAdaptor;
import task.examination.com.examinationtask.models.Product;
import task.examination.com.examinationtask.sqlite.SQLiteHandler;
import task.examination.com.examinationtask.sqlite.ShopDatabase;

import static task.examination.com.examinationtask.sqlite.SQLiteHandler.GetAllProducts;

public class ShowProductListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private static final String TAG = ShowProductListActivity.class.getSimpleName();;

    private ListView listView;
    private ProductViewAdaptor adapter;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_list);

        try{
            listView = findViewById(R.id.products_list_view);
            products = GetProductList();
            adapter = new ProductViewAdaptor(
                    this,
                    R.layout.product_list_view,
                    products);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        catch (Exception ex)
        {
            Toast.makeText( this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Product product = adapter.getItem(position);

        if (product != null) {
            new AlertDialog.Builder(this)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle("Удалить товар")
                    .setMessage("Удалить " +  product.getName().trim() + " ?")
                    .setPositiveButton("Продолжить", (dialog, which) -> {
                        if(RemoveProductFromDB(product))
                        {
                            products.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(this,
                                    "Товар " + product.getName().trim() + " успешно удален",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(this,
                                    "Товар " + product.getName().trim() + " не удалось удалить!",
                                    Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        }
    }

    public ArrayList<Product> GetProductList()
    {
        ArrayList<Product> products = new ArrayList<>();

        try {
            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();
            products = GetAllProducts(myShopDB,  this);
        }catch (Exception ex)
        {
            Toast.makeText( this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, Log.getStackTraceString(ex));

        }
        return products;
    }

    public boolean RemoveProductFromDB(Product product)
    {
        try {
            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();
            return SQLiteHandler.DeleteProduct(myShopDB,product);
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

package task.examination.com.examinationtask.activities;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.fragments.DetailFragment;
import task.examination.com.examinationtask.fragments.ListFragment;
import task.examination.com.examinationtask.sqlite.ShopDatabase;
import task.examination.com.examinationtask.utilities.DialogHandler;

import static task.examination.com.examinationtask.utilities.DialogHandler.ShowExitDialog;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private ShopDatabase MyShopDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyShopDB = new ShopDatabase(this);

        if(TryOpenDB())
        {
            Toast.makeText(this,
                    "База "  + MyShopDB.getDatabaseName() + " успешно открыта",
                    Toast.LENGTH_LONG).show();

            //ArrayList<Product> products = GetAllProducts(MyShopDB, this);
            //ArrayList<Order> orders = GetAllOrders(MyShopDB, this);
            //ArrayList<Order_Products> order_products = GetAllOrder_Products(MyShopDB, this);

        }
        else
        {
            DialogHandler.ShowExceptionDialog(this,"Работа с базой даннных невозможна!");
            this.finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override // метод интерфейса ListFragment.OnFragmentInteractionListener
    public void onFragmentInteraction(String link)
    {
        DetailFragment fragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);

        // если фрагмент создан и добавлен в активность - передать
        // данные во фрагмент
        if (fragment != null && fragment.isInLayout())
        {
            // передача данных во фрагмент
            fragment.setText(link);
        }
        else {
            // если фрагмента нет - создатm активность, которая и будет
            // содержать в себе фрагмент
            // тогда в активность передадим данные для фрагмента
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL, link);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        ShowExitDialog(this);
    }

    private boolean TryOpenDB()
    {
        boolean result = false;

        try
        {
            MyShopDB.openDataBase();
            result = true;
        }
        catch (SQLException ex)
        {
            Log.e(TAG, Log.getStackTraceString(ex));
        }
        return result;
    }
}

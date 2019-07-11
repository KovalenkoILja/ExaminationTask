package task.examination.com.examinationtask.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.adaptors.ProductToAddAdaptor;
import task.examination.com.examinationtask.models.Order;
import task.examination.com.examinationtask.models.Product;
import task.examination.com.examinationtask.sqlite.ShopDatabase;

import static task.examination.com.examinationtask.sqlite.SQLiteHandler.GetAllProducts;
import static task.examination.com.examinationtask.sqlite.SQLiteHandler.InsertOrder;
import static task.examination.com.examinationtask.utilities.DialogHandler.ShowExceptionDialog;

public class AddOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    private static final String TAG = AddOrderActivity.class.getSimpleName();

    final Calendar newCalendar = Calendar.getInstance();

    private Order order = new Order();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> ProductToAdd;

    private Button DatePickerBtn;
    private Button AddProducts;
    private Button AddOrder;
    private TextView DateText;
    private ListView listView;
    private ProductToAddAdaptor adaptor;
    private boolean isDateSet = false;
    private boolean isProductSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        if(GetLists())
        {
            DatePickerBtn = findViewById(R.id.date_picker_button);
            AddProducts = findViewById(R.id.add_products_button);
            AddOrder = findViewById(R.id.add_order_button);
            DateText = findViewById(R.id.date_text);
            listView = findViewById(R.id.product_to_add_list);

            AddOrder.setEnabled(false);
        }
        else this.finish();
    }

    private boolean CheckOrder()
    {
        return isDateSet && isProductSet;
    }

    private boolean GetLists()
    {
        try
        {
            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();

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

    public void DatePicker_OnClick(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog,
                this,
                2018, 11, 1);

        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        this.newCalendar.set(year, month, dayOfMonth);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(newCalendar.getTime());
        if (!date.isEmpty())
        {
            DateText.setText(date);
            this.order.setDatetime(date);
            this.isDateSet = true;

            if(CheckOrder())
                AddOrder.setEnabled(true);
        }
    }

    public void AddNewProduct_OnClick(View view)
    {
        String[] productNames = new String[products.size()];
        boolean[] checkedItems = new boolean[products.size()];

        for (int i = 0; i < products.size(); i++)
        {
            products.get(i).setQuantity(1);
            productNames[i] = products.get(i).getName().trim();
            checkedItems[i] = false;
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle("Выберите товары")
                .setMultiChoiceItems(productNames, checkedItems, (dialog, which, isChecked) -> {
                })
                .setPositiveButton("OK", (dialog, which) ->
                {
                    ProductToAdd = new ArrayList<>();

                    for(int i = 0; i < checkedItems.length; i++)
                        if (checkedItems[i])
                            ProductToAdd.add(products.get(i));

                    adaptor = new ProductToAddAdaptor(this,
                            R.layout.product_order_list_view, ProductToAdd);
                    listView.setAdapter(adaptor);

                    this.isProductSet = true;
                    if(CheckOrder())
                        AddOrder.setEnabled(true);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    public void AddOrder_OnClick(View view)
    {
        if(CheckOrder())
        {
            try
            {
                ShopDatabase myShopDB = new ShopDatabase(this);
                myShopDB.openDataBase();
                if(InsertOrder(myShopDB, this, order, ProductToAdd ))
                    Toast.makeText(this,
                            "Заказ успешно добавлен",
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,
                            "Не удалось добавить заказ!",
                            Toast.LENGTH_LONG).show();
            }
            catch (Exception ex)
            {
                Log.e(TAG, Log.getStackTraceString(ex));
                ShowExceptionDialog(this, ex.getMessage());
            }
        }
    }
}

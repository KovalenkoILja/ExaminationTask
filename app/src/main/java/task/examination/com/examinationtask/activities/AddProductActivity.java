package task.examination.com.examinationtask.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.models.Product;
import task.examination.com.examinationtask.sqlite.ShopDatabase;
import task.examination.com.examinationtask.utilities.MinMaxInputFilter;

import static task.examination.com.examinationtask.sqlite.SQLiteHandler.InsertProduct;
import static task.examination.com.examinationtask.utilities.DialogHandler.ShowExceptionDialog;

public class AddProductActivity extends AppCompatActivity
{
    private static final String TAG = AddProductActivity.class.getSimpleName();

    public static final String UpdateProductId = "0";

    public static final Product product = new Product();

    private EditText IdEditText;
    private EditText NameEditText;
    private EditText DescriptionEditText;
    private EditText PriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        IdEditText = findViewById(R.id.id_product_edit);
        NameEditText = findViewById(R.id.name_product_edit);
        DescriptionEditText = findViewById(R.id.description_product_edit);
        PriceEditText = findViewById(R.id.price_product_edit);
        PriceEditText.setFilters(new InputFilter[]{new MinMaxInputFilter(0, 100000)});

        try {
            IdEditText.setText(UpdateProductId);
        }
        catch (Exception ex)
        {
            Log.e(TAG, Log.getStackTraceString(ex));
            ShowExceptionDialog(this, ex.getMessage());
        }


        DisableEditText(IdEditText);
    }

    @Override // сохранение состояния при повороте устройства
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("IdEditText", IdEditText.getText().toString());
        outState.putString("NameEditText", NameEditText.getText().toString());
        outState.putString("DescriptionEditText", DescriptionEditText.getText().toString());
        outState.putString("PriceEditText", PriceEditText.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override // Восстановление значений, сохраненных при повороте устройства
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        IdEditText.setText(savedInstanceState.getString("IdEditText"));
        NameEditText.setText(savedInstanceState.getString("NameEditText"));
        DescriptionEditText.setText(savedInstanceState.getString("DescriptionEditText"));
        PriceEditText.setText(savedInstanceState.getString("PriceEditText"));
    }

    public void AddNewProduct_OnClick(View view)
    {
        String name = NameEditText.getText().toString();
        String desc = DescriptionEditText.getText().toString();
        double price = Double.valueOf(PriceEditText.getText().toString());

        if (name.isEmpty())
        {
            Toast.makeText(this,
                    "Название пусто!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else if (desc.isEmpty())
        {
            Toast.makeText(this,
                    "Описание пусто!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else if(price == 0.0)
        {

            Toast.makeText(this,
                    "Цена указана не верно!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        try
        {
            product.setName(name);
            product.setDescription(desc);
            product.setPrice(price);

            ShopDatabase myShopDB = new ShopDatabase(this);
            myShopDB.openDataBase();
            if(InsertProduct(myShopDB, product))
                Toast.makeText(this,
                        "Продукт успешно добавлен",
                        Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,
                        "Не удалось добавить продукт",
                        Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Log.e(TAG, Log.getStackTraceString(ex));
            ShowExceptionDialog(this, ex.getMessage());
        }
    }

    private void DisableEditText(EditText editText)
    {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

}

package task.examination.com.examinationtask.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import task.examination.com.examinationtask.activities.AddOrderActivity;
import task.examination.com.examinationtask.activities.AddProductActivity;
import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.activities.ShowOrderListActivity;
import task.examination.com.examinationtask.activities.ShowProductListActivity;

public class DetailFragment extends Fragment
{
    private static final String TAG = DetailFragment.class.getSimpleName();

    private View mainView;

    @Override public View
    onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainView = inflater.inflate(R.layout.fragment_detail_default, container, true);
        return mainView;
    }

    // обновление текстовых полей
    public void setText(String action)
    {
        Button button;

        try
        {
            if (action.equals("Заказы"))
            {
                setViewLayout(R.layout.fragment_detail_orders);

                TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.txtHeader);
                button = getView().findViewById(R.id.show_list_order_button);
                button.setOnClickListener(v -> ShowListOfOrders());
                button = getView().findViewById(R.id.add_order_button);
                button.setOnClickListener(v -> ShowAddOrder());
                textView.setText(action);
            }
            else if(action.equals("Товары"))
            {
                setViewLayout(R.layout.fragment_detail_products);

                TextView textView = Objects.requireNonNull(getView()).findViewById(R.id.txtHeader);
                button = getView().findViewById(R.id.show_list_product_button);
                button.setOnClickListener(v -> ShowListOfProducts());
                button = getView().findViewById(R.id.add_product_button);
                button.setOnClickListener(v -> ShowAddProduct());
                textView.setText(action);
            }

        }
        catch (Exception ex)
        {
            Toast.makeText( mainView.getContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, Log.getStackTraceString(ex));
        }
    }

    private void ShowListOfProducts()
    {
        Intent intent = new Intent(mainView.getContext(), ShowProductListActivity.class);
        mainView.getContext().startActivity(intent);
    }

    private void ShowAddProduct()
    {
        Intent intent = new Intent(mainView.getContext(), AddProductActivity.class);
        mainView.getContext().startActivity(intent);
    }

    private void ShowListOfOrders()
    {
        Intent intent = new Intent(mainView.getContext(), ShowOrderListActivity.class);
        mainView.getContext().startActivity(intent);
    }

    private void ShowAddOrder()
    {
        Intent intent = new Intent(mainView.getContext(), AddOrderActivity.class);
        mainView.getContext().startActivity(intent);
    }

    private void setViewLayout(int id)
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        if (rootView != null) {
            rootView.removeAllViews();
            rootView.addView(mainView);
        }
    }
}

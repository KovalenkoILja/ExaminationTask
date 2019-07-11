package task.examination.com.examinationtask.adaptors;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.models.Product;

public class ProductViewAdaptor extends ArrayAdapter<Product>
{
    private static final String TAG = ProductViewAdaptor.class.getSimpleName();

    private Context context;
    private ArrayList<Product> products;
    private int resource;

    public ProductViewAdaptor(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);

        this.products = objects;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        if (products.size() == 0) return 0;
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getID();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View child = convertView;
        ProductHolder holder;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        try {
            if (child == null) {
                child = inflater.inflate(resource, parent, false);
                holder = new ProductHolder(child);
                child.setTag(holder);
            } else holder = (ProductHolder) child.getTag();

            final Product product = products.get(position);

            final String header = "#" + product.getID() + " " + product.getName();

            holder.Name_TextView.setText(header);
            holder.Description_TextView.setText(product.getDescription());
            holder.Price_TextView.setText(String.valueOf(product.getPrice()));

            return child;
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            return new View(context);
        }
    }

    public class ProductHolder {
        TextView Name_TextView;
        TextView Description_TextView;
        TextView Price_TextView;

        ProductHolder(View v) {
            this.Name_TextView = v.findViewById(R.id.Name_TextView);
            this.Description_TextView = v.findViewById(R.id.Description_TextView);
            this.Price_TextView = v.findViewById(R.id.Price_TextView);
        }
    }
}

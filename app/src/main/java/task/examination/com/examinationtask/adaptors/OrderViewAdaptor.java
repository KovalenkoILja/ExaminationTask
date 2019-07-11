package task.examination.com.examinationtask.adaptors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import task.examination.com.examinationtask.R;
import task.examination.com.examinationtask.models.Order;
import task.examination.com.examinationtask.models.Order_Products;
import task.examination.com.examinationtask.models.Product;

public class OrderViewAdaptor extends RecyclerView.Adapter<OrderViewAdaptor.OrderViewHolder>
{
    private static final String TAG = OrderViewAdaptor.class.getSimpleName();

    private ArrayList<Order> orders;
    private ArrayList<Order_Products> order_products;
    private ArrayList<Product> products;
    private OrderItemClickListener mClickListener;

    public OrderViewAdaptor(ArrayList<Order> orders, ArrayList<Order_Products> orderProducts, ArrayList<Product> products)
    {
        this.orders = orders;
        this.order_products = orderProducts;
        this.products = products;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.order_recycler_view, viewGroup, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i)
    {
        Order order = orders.get(i);

        StringBuilder content = new StringBuilder();
        double overallPrice = 0.;

        String title = "Заказ № " + order.getID() + " от " + order.getDatetime();

        for (Order_Products o: order_products)
            if (order.getID() == o.getOrderID())
                for (Product pr : products)
                    if (pr.getID() == o.getProductID())
                    {
                        content.append(pr.getName().trim())
                                .append("\t")
                                .append(pr.getPrice())
                                .append("\t (")
                                .append(o.getProductQuantity())
                                .append(" шт) \t")
                                .append(Math.round((o.getProductQuantity() * pr.getPrice()) * 100.0) / 100.0)
                                .append("\n");
                        overallPrice += o.getProductQuantity() * pr.getPrice();
                    }

        orderViewHolder.vTitle.setText(title);
        orderViewHolder.vProductTitle.setText("Список продуктов:");
        orderViewHolder.vProductList.setText(content);
        orderViewHolder.vAmount.setText("Сумма заказа:");
        orderViewHolder.vOrderAmount.setText(String.valueOf( Math.round(overallPrice * 100.0 ) / 100.0));
    }

    @Override
    public int getItemCount()
    {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView vTitle;
        TextView vProductTitle;
        TextView vProductList;
        TextView vAmount;
        TextView vOrderAmount;

        OrderViewHolder(View v)
        {
            super(v);
            vTitle = v.findViewById(R.id.title);
            vProductTitle = v.findViewById(R.id.txtProductTitle);
            vProductList = v.findViewById(R.id.txtProductList);
            vAmount = v.findViewById(R.id.txtAmount);
            vOrderAmount = v.findViewById(R.id.txtOrderAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Order getItem(int id)
    {
        return orders.get(id);
    }

    public void setClickListener(OrderItemClickListener orderItemClickListener)
    {
        this.mClickListener = orderItemClickListener;
    }

    public interface OrderItemClickListener
    {
        void onItemClick(View view, int position);
    }
}

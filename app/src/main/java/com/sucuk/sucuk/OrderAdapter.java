package com.sucuk.sucuk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;


public class OrderAdapter extends FirebaseListAdapter<OrderItem> {

    public OrderAdapter(Query ref, Activity activity) {
        super(activity, OrderItem.class, R.layout.order_list_item, ref);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void populateView(View view, OrderItem order, int i) {

        ((TextView) view.findViewById(R.id.name)).setText(order.getName());
        ((TextView) view.findViewById(R.id.orderDate)).setText(order.getDate());
    }
}

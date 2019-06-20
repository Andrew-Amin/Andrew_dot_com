package com.example.andrew.andrew_dot_com;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {
    private ArrayList<String> ListItemsNames;
    private ArrayList<Integer> ListItemsPrice;
    private ArrayList<Integer> ListItemsImagesIDs;
    private ArrayList<Integer> Quantity;
    private Activity context;

    public CustomListView(Activity context, ArrayList<String> ListItemsNames,
                          ArrayList<Integer> ListItemsPrice, ArrayList<Integer> ListItemsImagesIDs, ArrayList<Integer> Quantity) {
        super(context, R.layout.listview_xml, ListItemsNames);

        this.context = context;
        this.ListItemsNames = ListItemsNames;
        this.ListItemsPrice = ListItemsPrice;
        this.ListItemsImagesIDs = ListItemsImagesIDs;
        this.Quantity = Quantity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_xml, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) r.getTag();

        viewHolder.ItemImage.setImageResource(ListItemsImagesIDs.get(position));
        viewHolder.itemName.setText(ListItemsNames.get(position));
        viewHolder.itemPrice.setText(String.valueOf(ListItemsPrice.get(position)));
        viewHolder.quantity.setText(String.valueOf(Quantity.get(position)));

        return r;
    }

    class ViewHolder {
        TextView itemName;
        TextView itemPrice;
        TextView quantity;
        ImageView ItemImage;

        ViewHolder(View view) {
            this.itemName = (TextView) view.findViewById(R.id.tv_productName_id);
            this.itemPrice = (TextView) view.findViewById(R.id.tv_productPrice_id);
            this.quantity = (TextView) view.findViewById(R.id.tv_quantity);
            this.ItemImage = (ImageView) view.findViewById(R.id.ItemImage_id);
        }
    }
}

package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 */
public class ProductCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {

        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursorData  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursorData) {
        // Find button
        Button saleButton = view.findViewById(R.id.sale_button);

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView priceTextView = view.findViewById(R.id.price);

        // Find the columns of product attributes that we're interested in
        final String productName = cursorData.getString(cursorData.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        final int productQuantity = cursorData.getInt(cursorData.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        final double productPrice = cursorData.getDouble(cursorData.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(Double.toString(productPrice));

        // If the product quantity is empty string or null, then set it to zero.
        if (productQuantity == 0) {
            quantityTextView.setText(context.getString(R.string.zero));
            // Disable button click
            saleButton.setEnabled(false);
        } else {
            quantityTextView.setText(String.valueOf(productQuantity));
            // Enable button click
            saleButton.setEnabled(true);
        }

    }
}

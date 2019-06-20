package com.example.andrew.andrew_dot_com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SaveOrderDialog extends AppCompatDialogFragment {

    private EditText location;
    private saveOrderDialogListener listener;
    GlobalID customer ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        customer = new GlobalID() ;
        builder.setView(view).setTitle("Save Order")
                .setNegativeButton("discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String address = location.getText().toString();
                if(address.equals(""))
                    address = customer.getLocation();
                listener.getCustomer(address);
            }
        });
        location = view.findViewById(R.id.et_location_dialog);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (saveOrderDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement saveOrderDialogListener");
        }
    }

    public interface saveOrderDialogListener {
        void getCustomer(String location);
    }
}

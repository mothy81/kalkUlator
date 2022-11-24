package com.empro.kalk_u_lator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogWindow2 extends AppCompatDialogFragment {
    private EditText exchangeValue;
    private DialogWindow2.DialogWindowListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.d_change_dialog, null);
        builder.setView(view)
                .setTitle("Podaj nową grubość warstwy")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String thickness = exchangeValue.getText().toString();
                        listener.applyText(thickness);
                    }
                });
        exchangeValue = view.findViewById(R.id.thicknessDialogValue);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogWindowListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() +
                    "Must implement DialogWindowListener");
        }
    }

    public  interface DialogWindowListener{
        void applyText(String thickness);
    }
}

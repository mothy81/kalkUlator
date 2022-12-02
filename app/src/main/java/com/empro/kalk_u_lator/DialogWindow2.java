package com.empro.kalk_u_lator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogWindow2 extends AppCompatDialogFragment {
    private EditText exchangeValue;
    private DialogWindow2.DialogWindowListener listener;
    private Button increaseButton, decreaseButton;
    private int tempo;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.d_change_dialog, null);
        increaseButton = view.findViewById(R.id.increaseButton);
        decreaseButton = view.findViewById(R.id.decreaseButton);

        String dStringTransfer = ((MainActivity) getActivity()).dStringTransfer;
        exchangeValue = view.findViewById(R.id.thicknessDialogValue);
        tempo = (int) Math.round(Double.parseDouble(dStringTransfer));
        exchangeValue.setText(String.valueOf(tempo));

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int temp2 = Integer.parseInt(exchangeValue.getText().toString());
                exchangeValue.setText(String.valueOf(temp2+1));
                if (temp2>=99){
                    exchangeValue.setText(String.valueOf(99));
                }
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int temp2 = Integer.parseInt(exchangeValue.getText().toString());
                exchangeValue.setText(String.valueOf(temp2-1));
                if (temp2<=1){
                    exchangeValue.setText(String.valueOf(1));
                }
            }
        });




        builder.setView(view)
                .setTitle("Podaj nową grubość warstwy:")
                .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (exchangeValue.getText().toString().length()==0){
                            exchangeValue.setText("1");
                        }
                        String thickness = exchangeValue.getText().toString();
                        listener.applyText(thickness);
                    }
                });

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

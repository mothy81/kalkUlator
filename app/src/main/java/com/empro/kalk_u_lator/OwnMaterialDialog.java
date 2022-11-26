package com.empro.kalk_u_lator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class OwnMaterialDialog extends AppCompatDialogFragment {

    private EditText lambdaValue;
    private EditText nameValue;
    private OwnMaterialListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.own_material_layout, null);
        builder.setView(view)
                .setTitle("Wprowadź własny materiał:")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    String lambda = lambdaValue.getText().toString();
                    String name = nameValue.getText().toString();
                    listener.applyData(name,lambda);
                    }
                });

        lambdaValue = view.findViewById(R.id.lambdaValue);
        nameValue = view.findViewById(R.id.nameValue);
        nameValue.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20)
        });
        lambdaValue.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(5)
        });
            return builder.create();
    }
    public interface OwnMaterialListener{
        void applyData(String name, String lambda);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OwnMaterialListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new ClassCastException(context.toString()+"Must implement Dialog Listener");
        }
    }

}

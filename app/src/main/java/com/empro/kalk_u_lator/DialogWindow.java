package com.empro.kalk_u_lator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class DialogWindow extends DialogFragment {

    private EditText thicknesValue;
    private Button okButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_change_dialog, container, false);

        okButton = view.findViewById(R.id.dialog_ok_button);
        cancelButton = view.findViewById(R.id.dialog_cancel_button);
        thicknesValue = view.findViewById(R.id.thicknessDialogValue);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getDialog().dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String dialogInput = thicknesValue.getText().toString();
            if (!dialogInput.equals("")){
               ((MainActivity)getActivity()).exchangeField.setText(dialogInput);
            }
            getDialog().dismiss();
            }
        });

        return view;
    }
}

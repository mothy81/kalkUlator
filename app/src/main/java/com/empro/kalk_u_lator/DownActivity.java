package com.empro.kalk_u_lator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class DownActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ArrayList<SingleItem> mLayerList;
    private ArrayList<SingleItem> savedLayerList;

    private RecyclerView mRecyclerView;
    private LayerAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private Button increaseButton;
    private Button decreaseButton;
    private Button layerPopUpButton;
    private Button newLayerButton;
    private TextView lambdaValue;
    private TextView isItemEditable;
    private Button rBoxValue;
    private EditText thicknessValue;
    private FloatingActionButton fabmenu1, fabmenu2, fabmenu3;

    private int dValue;
    private int dValue2;
    private Double lValue;
    private Double rrValue;
    private String mValue;
    private Double rSum;

    boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        lambdaValue = findViewById(R.id.lambdaBoxValue);
        isItemEditable = findViewById(R.id.isItemEditableBox);
        rBoxValue = findViewById(R.id.rBoxValue);
        thicknessValue = findViewById(R.id.thicknessValueEditText);
        layerPopUpButton = findViewById(R.id.popup_button);
        fabmenu1 = findViewById(R.id.fab_menu1);
        fabmenu2 = findViewById(R.id.fab_menu2);
        fabmenu3 = findViewById(R.id.fab_menu3);

        createLayerList();
        buildRecyclerView();
        setButtons();
        showFabMenu();

        thicknessValue.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    void showToastBlue(String text){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toastText);
        toastText.setText(text);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }
    void showToastRed(String text){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_red, findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toastText);
        toastText.setText(text);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START| ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int targetPosition = target.getAdapterPosition();
            Collections.swap(mLayerList, fromPosition, targetPosition);
            mRecyclerView.getAdapter().notifyItemMoved(fromPosition, targetPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void removeItem(int position)
    {
        mLayerList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text)
    {
        mLayerList.get(position).changeText2(text);
        mAdapter.notifyItemChanged(position);
    }

    public void changeItem4(int position, String text)
    {
        mLayerList.get(position).changeText4(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createLayerList()
    {

        mLayerList = new ArrayList<>();

    }
    public void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new LayerAdapter(mLayerList);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LayerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (Integer.parseInt(mLayerList.get(position).getText5())==0) {
                    showToastRed(getString(R.string.cant_change_thickness_toast));
                } else {

                    Dialog dialog = new Dialog(DownActivity.this);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    View view = getLayoutInflater().inflate(R.layout.d_change_dialog, null);
                    dialog.setContentView(view);
                    dialog.setCancelable(false);
                    Button okButton = dialog.findViewById(R.id.okButton);
                    Button cancelButton = dialog.findViewById(R.id.cancelButton);
                    Button incButton = dialog.findViewById(R.id.increaseButton);
                    Button decButton = dialog.findViewById(R.id.decreaseButton);
                    EditText newD = dialog.findViewById((R.id.thicknessDialogValue));
                    newD.setText(mLayerList.get(position).getText2());
                    newD.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(2)});
                    dialog.show();

                    okButton.setOnClickListener(v -> {

                        if (newD.getText().length() == 0) {
                            newD.setText("1");
                        } else {
                            if (Integer.parseInt(newD.getText().toString()) == 0) {
                                newD.setText("1");
                            }
                        }

                        double newL = Double.parseDouble(mLayerList.get(position).getText3());
                        double newR = Math.round(10 * Integer.parseInt(newD.getText().toString()) / newL);
                        newR = newR / 1000;
                        changeItem(position, newD.getText().toString());
                        changeItem4(position, String.valueOf(newR));

                        uCalc();
                        dialog.dismiss();
                    });

                    cancelButton.setOnClickListener(v -> dialog.dismiss());

                    decButton.setOnClickListener(view1 -> {
                        if (newD.getText().toString().length() == 0) {
                            newD.setText("1");
                        }

                        int tempD = Integer.parseInt(newD.getText().toString());
                        if (tempD > 1) {
                            tempD = tempD - 1;
                        } else {
                            tempD = 1;
                        }
                        newD.setText(String.valueOf(tempD));
                    });

                    incButton.setOnClickListener(view12 -> {
                        if (newD.getText().toString().length() == 0) {
                            newD.setText("1");
                        }
                        int tempD = Integer.parseInt(newD.getText().toString());
                        if (tempD < 99) {
                            tempD = tempD + 1;
                        } else {
                            tempD = 99;
                        }
                        newD.setText(String.valueOf(tempD));

                    });

                    incButton.setOnLongClickListener(v -> {
                        if (newD.getText().toString().length() == 0) {
                            newD.setText("1");
                        }
                        int tempD = Integer.parseInt(newD.getText().toString());
                        if (tempD < 89) {
                            tempD = tempD + 10;
                        } else {
                            tempD = 99;
                        }
                        newD.setText(String.valueOf(tempD));
                        return true;
                    });

                    decButton.setOnLongClickListener(v -> {
                        if (newD.getText().toString().length() == 0) {
                            newD.setText("1");
                        }

                        int tempD = Integer.parseInt(newD.getText().toString());
                        if (tempD > 10) {
                            tempD = tempD - 10;
                        } else {
                            tempD = 1;
                        }
                        newD.setText(String.valueOf(tempD));
                        return true;
                    });
                }
            }

            @Override
            public void onDeleteClick(int position)
            {
                removeItem(position);
                uCalc();
            }
        });
    }
    public void setButtons()
    {

        newLayerButton = findViewById(R.id.goToNewLayerLayout);
        decreaseButton = findViewById(R.id.thickness_decrease_button);
        increaseButton = findViewById(R.id.thickness_increase_button);
        increaseButton.setHapticFeedbackEnabled(false);
        decreaseButton.setHapticFeedbackEnabled(false);

        fabmenu1.setOnClickListener(v -> {
            saveData();
            showFabMenu();
        });

        fabmenu2.setOnClickListener(v -> loadData());

        fabmenu3.setOnClickListener(v -> clearAll());

        newLayerButton.setOnClickListener(v -> {

            int position = mLayerList.size();

            if (lambdaValue.getText().toString().length()==0)
            {
                showToastRed(getString(R.string.lambda_0));
            } else
            {
                if (Double.parseDouble(lambdaValue.getText().toString())==0)
                {
                    showToastRed(getString(R.string.lambda_0));
                } else {

                    if (thicknessValue.getText().length()==0){
                        thicknessValue.setText("1");
                    }
                    dValue = Integer.parseInt(thicknessValue.getText().toString());
                    lValue = Double.parseDouble(lambdaValue.getText().toString());
                    rrValue = ((double) Math.round(10 * dValue / lValue))/1000;
                    mValue = layerPopUpButton.getText().toString();
                    mLayerList.add(position, new SingleItem(R.drawable.ic_baseline_equalizer_24, mValue, String.valueOf(dValue), lValue.toString(), rrValue.toString(), isItemEditable.getText().toString()));
                    mAdapter.notifyItemInserted(position);
                    layerPopUpButton.setText(R.string.add_layer_title);
                    newLayerButton.setVisibility(View.INVISIBLE);
                    thicknessValue.setVisibility(View.INVISIBLE);
                    decreaseButton.setVisibility(View.INVISIBLE);
                    increaseButton.setVisibility(View.INVISIBLE);
                    layerPopUpButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(116, 163, 233)));
                    uCalc();
                }
            }
        });

        decreaseButton.setOnClickListener(v -> {
            dValue2 = Integer.parseInt(thicknessValue.getText().toString())-1;
            thicknessValue.setText(String.valueOf(dValue2));
            if (dValue2<=1)
            {
                thicknessValue.setText(String.valueOf(1));
            }
        });

        increaseButton.setOnClickListener(v -> {
            dValue2 = Integer.parseInt(thicknessValue.getText().toString())+1;
            thicknessValue.setText(String.valueOf(dValue2));
            if (dValue2>=99)
            {
                thicknessValue.setText(String.valueOf(99));
            }
        });

        decreaseButton.setOnLongClickListener(v -> {
            if (Integer.parseInt(thicknessValue.getText().toString())<11){
                thicknessValue.setText(String.valueOf(1));
            }else {
                dValue2 = Integer.parseInt(thicknessValue.getText().toString()) - 10;
                thicknessValue.setText(String.valueOf(dValue2));
            }
            return true;
        });

        increaseButton.setOnLongClickListener(v -> {

            dValue2 = Integer.parseInt(thicknessValue.getText().toString())+10;

            thicknessValue.setText(String.valueOf(dValue2));
            return true;
        });

        rBoxValue.setOnClickListener(v -> {
            if (!isFabOpen) {
                showFabMenu();
            } else {
                closeFabMenu();
            }
        });

    }

    private void clearAll() {
        int tempj = mLayerList.size();
        for (int j=0; j<tempj; j++){
            mLayerList.remove(0);
            mAdapter.notifyItemRemoved(0);
            uCalc();
        }
        showFabMenu();
    }

    private void saveData()
    {
        if (mLayerList.size()==0){
            showToastRed(getString(R.string.no_save_data));
        }else {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mLayerList);
            editor.putString("task list downActivity", json);
            editor.apply();
            showToastBlue(getString(R.string.saved_layers_state));
            savedLayerList = new ArrayList<>();
        }
    }

    private void loadData()
    {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list downActivity", null);
        Type type = new TypeToken<ArrayList<SingleItem>>() {
        }.getType();
        savedLayerList = gson.fromJson(json, type);

        if (savedLayerList == null){
            showToastRed(getString(R.string.no_saved_data));
        } else {

            clearAll();
            int j = savedLayerList.size();
            for (int i = 0; i < j; i++) {
                String text1temp = savedLayerList.get(i).getText1();
                String text2temp = savedLayerList.get(i).getText2();
                String text3temp = savedLayerList.get(i).getText3();
                String text4temp = savedLayerList.get(i).getText4();
                String text5temp = savedLayerList.get(i).getText5();
                mLayerList.add(i, new SingleItem(R.drawable.ic_baseline_equalizer_24, text1temp, text2temp, text3temp, text4temp, text5temp));
                mAdapter.notifyItemInserted(i);
            }
            uCalc();
            showFabMenu();
        }
    }

    private void closeFabMenu()
    {
        isFabOpen = false;
        int x1 = rBoxValue.getWidth();
        fabmenu1.animate().translationX(x1);
        fabmenu2.animate().translationX(x1);
        fabmenu3.animate().translationX(-x1);
    }

    private void showFabMenu()
    {
        isFabOpen = true;
        fabmenu1.animate().translationX(0);
        fabmenu2.animate().translationX(0);
        fabmenu3.animate().translationX(0);
    }

    private void uCalc() {
        int position = mLayerList.size();
        rSum = 0.14;

        for (int i=0; i<position; i++)
        {
            String rString = (mLayerList.get(i).getText4());
            rSum = rSum + Double.parseDouble(rString);
            rSum = (double) Math.round(rSum*1000)/1000;
        }
        rSum = (double)Math.round(1000/rSum)/1000;
        if (mLayerList.size()==0) {
            rBoxValue.setText("U = " + "0.000");
        } else {
            rBoxValue.setText("U = " + rSum);
        }
    }

    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu_down);
        popup.show();
        layerPopUpButton.setText(R.string.choose_material_label);
        layerPopUpButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(116, 163, 233)));
        newLayerButton.setVisibility(View.INVISIBLE);
        thicknessValue.setVisibility(View.INVISIBLE);
        decreaseButton.setVisibility(View.INVISIBLE);
        increaseButton.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.material111:
            case R.id.material112:
            case R.id.material113:
            case R.id.material114:
            case R.id.material115:
            case R.id.material116:
            case R.id.material117:
            case R.id.material121:
            case R.id.material122:
            case R.id.material123:
            case R.id.material124:
            case R.id.material125:
            case R.id.material126:
            case R.id.material127:
            case R.id.material131:
            case R.id.material132:
            case R.id.material133:
            case R.id.material134:
            case R.id.material135:
            case R.id.material136:
            case R.id.material141:
            case R.id.material142:
            case R.id.material15:
            case R.id.material16:
            case R.id.material31:
            case R.id.material33:
            case R.id.material34:
            case R.id.material35:
            case R.id.material36:
            case R.id.material37:
            case R.id.material41:
            case R.id.material42:
            case R.id.material43:
            case R.id.material44:
            case R.id.material45:
            case R.id.material46:
            case R.id.material47:
            case R.id.material51:
            case R.id.material52:
            case R.id.material53:
            case R.id.material54:
            case R.id.material55:
            case R.id.material56:
            case R.id.material57:
            case R.id.material58:
            case R.id.material59:
            case R.id.material510:
            case R.id.material511:
            case R.id.material61:
            case R.id.material62:
            case R.id.material63:
            case R.id.material64:
            case R.id.material65:
            case R.id.material66:
                isItemEditable.setText(item.getContentDescription().toString());
                if (Double.parseDouble(item.getContentDescription().toString())!=1){
                    rrValue = Double.parseDouble(item.getContentDescription().toString());
                    int position = mLayerList.size();
                    mLayerList.add(position, new SingleItem(R.drawable.ic_baseline_equalizer_24, item.getTitle().toString(),
                            item.getTitleCondensed().toString(), item.getTooltipText().toString(), rrValue.toString(), "0"));
                    mAdapter.notifyItemInserted(position);
                    uCalc();
                } else {
                    thicknessValue.setText(item.getTitleCondensed().toString());
                    lambdaValue.setText(item.getTooltipText());
                    layerPopUpButton.setText(item.getTitle());
                    popUpMetodsInit();
                }
                return true;
            case R.id.material7:
                initNewMaterialDialog();
                return true;
            default: return false;
        }
    }

    private void initNewMaterialDialog() {

        Dialog dialog = new Dialog(DownActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = getLayoutInflater().inflate(R.layout.own_material_layout,null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Button okButton = dialog.findViewById(R.id.okButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        EditText newLambda = dialog.findViewById((R.id.lambdaValue));
        TextView newName = dialog.findViewById((R.id.nameValue));

        newLambda.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(5)});
        newName.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20)});
        dialog.show();

        okButton.setOnClickListener(v -> {

            if (newLambda.getText().length()==0) {
                showToastRed(getString(R.string.lambda_0_toast));
            } else {
                if (Double.parseDouble(newLambda.getText().toString()) == 0) {
                    showToastRed(getString(R.string.lambda_0_toast));
                } else {

                    layerPopUpButton.setText(newName.getText() + " λ=" + newLambda.getText());
                    lambdaValue.setText(newLambda.getText());
                    newLayerButton.setVisibility(View.VISIBLE);
                    thicknessValue.setVisibility(View.VISIBLE);
                    decreaseButton.setVisibility(View.VISIBLE);
                    increaseButton.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    private void popUpMetodsInit() {
        newLayerButton.setVisibility(View.VISIBLE);
        thicknessValue.setVisibility(View.VISIBLE);
        decreaseButton.setVisibility(View.VISIBLE);
        increaseButton.setVisibility(View.VISIBLE);
        layerPopUpButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(235, 243, 251)));

    }

}
package com.empro.kalk_u_lator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DialogWindow2.DialogWindowListener {

    private ArrayList<SingleItem> mLayerList;
    private RecyclerView mRecyclerView;
    private LayerAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private Button increaseButton;
    private Button decreaseButton;
    private Button layerPopUpButton;
    private Button newLayerButton;
    private TextView lambdaValue;
    private TextView rBoxValue;
    private EditText thicknessValue;
    private MenuItem materialMenu;
    private Menu menuValues;


    private Double dValue;
    private int dValue2;
    private Double lValue;
    private Double rrValue;
    private String mValue;
    private Double rSum;


    public TextView exchangeField;
    public int ItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createLayerList();
        buildRecyclerView();
        setButtons();

        lambdaValue = findViewById(R.id.lambdaBoxValue);
        rBoxValue = findViewById(R.id.rBoxValue);
        thicknessValue = findViewById(R.id.thicknessValueEditText);
        layerPopUpButton = findViewById(R.id.popup_button);


    }

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
            materialMenu = findViewById(R.id.material11);

            mAdapter.setOnItemClickListener(new LayerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    ItemPosition = position;

                    DialogWindow2 dialog = new DialogWindow2();
                    dialog.show(getSupportFragmentManager(), "dialog ");


                }

                @Override
                public void onDeleteClick(int position) {
                    removeItem(position);
                }
            });
        }
        public void setButtons()
        {

            newLayerButton = findViewById(R.id.goToNewLayerLayout);
            decreaseButton = findViewById(R.id.thickness_decrease_button);
            increaseButton = findViewById(R.id.thickness_increase_button);

            newLayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dValue = Double.parseDouble(thicknessValue.getText().toString());
                    lValue = Double.parseDouble(lambdaValue.getText().toString());
                    rrValue = (Double.valueOf(Math.round(10*dValue/lValue)))/1000;
                    mValue = layerPopUpButton.getText().toString();

                    int position = mLayerList.size();
                    mLayerList.add(position, new SingleItem(R.drawable.ic_android, mValue, "d= "+dValue, "λ= "+lValue, "R= "+rrValue));
                    mAdapter.notifyItemInserted(position);
                    layerPopUpButton.setText("Wybierz materiał warstwy:");
                    newLayerButton.setVisibility(View.INVISIBLE);
                    thicknessValue.setVisibility(View.INVISIBLE);
                    decreaseButton.setVisibility(View.INVISIBLE);
                    increaseButton.setVisibility(View.INVISIBLE);



                    rSum = 0.17;

                    for (int i=0; i<position+1; i++)
                    {
                    String rString = (mLayerList.get(i).getText4());
                    int rStringLenght = rString.length();
                    rString = rString.substring(3, rStringLenght);
                    rSum = rSum + Double.valueOf(rString);
                    rSum = (double) Math.round(rSum*100)/100;
                    }
                    rSum = (double)Math.round(1000/rSum)/1000;
                    rBoxValue.setText("U= "+rSum.toString());

                }
            });

            decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dValue2 = Integer.parseInt(thicknessValue.getText().toString())-1;
                   thicknessValue.setText(String.valueOf(dValue2));
                }
            });

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dValue2 = Integer.parseInt(thicknessValue.getText().toString())+1;
                    thicknessValue.setText(String.valueOf(dValue2));
                }
            });

            decreaseButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dValue2 = Integer.parseInt(thicknessValue.getText().toString())-9;
                    thicknessValue.setText(String.valueOf(dValue2));
                    return false;
                }
            });

            increaseButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dValue2 = Integer.parseInt(thicknessValue.getText().toString())+9;
                    thicknessValue.setText(String.valueOf(dValue2));
                    return false;
                }
            });
        }

    private void uCalc() {
        int position = mLayerList.size();
        rSum = 0.17;

        for (int i=0; i<position; i++)
        {
            String rString = (mLayerList.get(i).getText4());
            int rStringLenght = rString.length();
            rString = rString.substring(3, rStringLenght);
            rSum = rSum + Double.valueOf(rString);
            rSum = (double) Math.round(rSum*100)/100;
        }
        rSum = (double)Math.round(1000/rSum)/1000;
        rBoxValue.setText("U= "+rSum.toString());
    }

    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
        menuValues = popup.getMenu();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.material11:
            case R.id.material12:
            case R.id.material13:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
                return true;
            case R.id.material14:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material15:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material16:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material17:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material18:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material19:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material110:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material111:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material112:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material113:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material114:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material115:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material21:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material22:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material31:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material32:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material33:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material34:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material35:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material41:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material42:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material43:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material44:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material45:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material46:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material47:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material48:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material49:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material410:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material411:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material51:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material52:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material53:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
            case R.id.material54:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
                return true;
            default: return (false);
        }

    }

    private void popUpMetodsInit() {
        newLayerButton.setVisibility(View.VISIBLE);
        thicknessValue.setVisibility(View.VISIBLE);
        decreaseButton.setVisibility(View.VISIBLE);
        increaseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void applyText(String thickness) {

        int position = ItemPosition;
        changeItem(position, "d= "+ thickness);
    }
}


package com.empro.kalk_u_lator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DialogWindow2.DialogWindowListener, OwnMaterialDialog.OwnMaterialListener {

    private ArrayList<SingleItem> mLayerList;
    private RecyclerView mRecyclerView;
    private LayerAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private Button increaseButton;
    private Button decreaseButton;
    private Button layerPopUpButton;
    private Button newLayerButton;
    private TextView lambdaValue;
    private Button rBoxValue;
    private EditText thicknessValue;
    private MenuItem materialMenu;
    private Menu menuValues;

    private int dValue;
    private int dValue2;
    private Double lValue;
    private Double rrValue;
    private String mValue;
    private Double rSum;

    public String dStringTransfer;
    public int ItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        createLayerList();
        buildRecyclerView();
        setButtons();

        lambdaValue = findViewById(R.id.lambdaBoxValue);
        rBoxValue = findViewById(R.id.rBoxValue);
        thicknessValue = findViewById(R.id.thicknessValueEditText);
        layerPopUpButton = findViewById(R.id.popup_button);

        thicknessValue.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)
        });

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
            materialMenu = findViewById(R.id.material11);

            mAdapter.setOnItemClickListener(new LayerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    ItemPosition = position;
                    DialogWindow2 dialog = new DialogWindow2();

                    dStringTransfer = (mLayerList.get(position).getText2());

                    dialog.show(getSupportFragmentManager(), "dialog ");
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

            newLayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = mLayerList.size();

                    if (lambdaValue.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "λ NIE MOŻE BYĆ RÓWNA  0!", Toast.LENGTH_LONG).show();
                    } else
                    {
                        if (Double.valueOf(lambdaValue.getText().toString())==0)
                        {
                            Toast.makeText(getApplicationContext(), "λ NIE MOŻE BYĆ RÓWNA 0!", Toast.LENGTH_LONG).show();
                        } else {
                                dValue = Integer.parseInt(thicknessValue.getText().toString());
                                lValue = Double.parseDouble(lambdaValue.getText().toString());
                                rrValue = ((double) Math.round(10 * dValue / lValue))/1000;
                                mValue = layerPopUpButton.getText().toString();
                                mLayerList.add(position, new SingleItem(R.drawable.ic_baseline_equalizer_24, mValue, String.valueOf(dValue), lValue.toString(), rrValue.toString()));
                                mAdapter.notifyItemInserted(position);
                                layerPopUpButton.setText(R.string.add_layer_title);
                                newLayerButton.setVisibility(View.INVISIBLE);
                                thicknessValue.setVisibility(View.INVISIBLE);
                                decreaseButton.setVisibility(View.INVISIBLE);
                                increaseButton.setVisibility(View.INVISIBLE);
                                uCalc();
                                    }
                        }

                }
            });

            decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dValue2 = Integer.parseInt(thicknessValue.getText().toString())-1;
                   thicknessValue.setText(String.valueOf(dValue2));
                    if (dValue2<=1)
                    {
                        thicknessValue.setText(String.valueOf(1));
                    }
                }
            });

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dValue2 = Integer.parseInt(thicknessValue.getText().toString())+1;
                    thicknessValue.setText(String.valueOf(dValue2));
                    if (dValue2>=99)
                    {
                        thicknessValue.setText(String.valueOf(99));
                    }
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
            rSum = rSum + Double.parseDouble(rString);
            rSum = (double) Math.round(rSum*100)/100;
        }
        rSum = (double)Math.round(100/rSum)/100;
        if (mLayerList.size()==0) {
            rBoxValue.setText("U = " + "0.00");
        } else {
            rBoxValue.setText("U = " + rSum);
        }
    }

    public void showPopUp(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
        menuValues = popup.getMenu();
        layerPopUpButton.setText("WYBIERZ MATERIAŁ:");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.material11:
            case R.id.material12:
            case R.id.material13:
            case R.id.material14:
            case R.id.material15:
            case R.id.material16:
            case R.id.material17:
            case R.id.material18:
            case R.id.material19:
            case R.id.material110:
            case R.id.material111:
            case R.id.material112:
            case R.id.material113:
            case R.id.material114:
            case R.id.material115:
            case R.id.material116:
            case R.id.material117:
            case R.id.material21:
            case R.id.material22:
            case R.id.material23:
            case R.id.material31:
            case R.id.material32:
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
            case R.id.material48:
            case R.id.material49:
            case R.id.material410:
            case R.id.material411:
            case R.id.material51:
            case R.id.material52:
            case R.id.material53:
            case R.id.material54:
            case R.id.material55:
                lambdaValue.setText(item.getTooltipText());
                layerPopUpButton.setText(item.getTitle());
                popUpMetodsInit();
                return true;
            case R.id.material6:
                    initNewMaterialDialog();
                return true;
            default: return false;
        }
    }

    private void initNewMaterialDialog() {

    OwnMaterialDialog ownMaterialDialog = new OwnMaterialDialog();
    ownMaterialDialog.show(getSupportFragmentManager(),"New material dialog");

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
        double ltemp = Double.parseDouble(mLayerList.get(position).getText3());
        double rtemp = Math.round(10*Double.parseDouble(thickness)/ltemp);
        rtemp = rtemp/1000;
        changeItem(position, thickness);
        changeItem4(position, String.valueOf(rtemp));
        uCalc();
    }

    @Override
    public void applyData(String name, String lambda) {

        layerPopUpButton.setText(name+" λ="+lambda);
        lambdaValue.setText(lambda);
        popUpMetodsInit();
    }
}
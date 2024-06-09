package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

import static com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale.PointOfSaleManagementActivity.IS_TODAY_AUDIT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;

import java.util.ArrayList;

public class MoneyCountingActivity extends AppCompatActivity {

    public static final String MONEY_COUNTING = "com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale.MoneyCountingActivity.MONEY_COUNTING";
    ArrayList<EditText> editTextsList;
    ViewGroup editTextViewGroup;
    EditText etTotal;
    Button btRecalculate;

    Button btPointOfSaleClosing;
    Float recount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_counting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextsList = new ArrayList<>();
        editTextViewGroup = (ViewGroup) findViewById(android.R.id.content).getRootView();
        getAllEditText(editTextViewGroup, editTextsList);

        etTotal = findViewById(R.id.etTotal);
        btRecalculate = findViewById(R.id.btRecalculate);
        btPointOfSaleClosing = findViewById(R.id.btPointOfSaleClosing);

        btRecalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText editText : editTextsList) {
                    if(editText.getText().toString().isEmpty()){
                        continue;
                    }
                    ViewGroup viewGroup = (ViewGroup) editText.getParent().getParent();
                    recount += Integer.parseInt(viewGroup.getChildAt(0).getTag().toString()) * Integer.parseInt(editText.getText().toString());
                }
                etTotal.setText(String.valueOf(recount/100f));
            }
        });

        btPointOfSaleClosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTotal.getText().toString().isEmpty()){
                    return;
                }
                Intent intent = new Intent(MoneyCountingActivity.this, PointOfSaleClosingActivity.class);
                intent.putExtra(MONEY_COUNTING, Float.parseFloat(etTotal.getText().toString()));
                startActivity(intent);
            }
        });



    }


    private void getAllEditText(ViewGroup viewGroup, ArrayList<EditText> arrayList) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof EditText) {
                if (viewGroup.getChildAt(i).getTag() != null && viewGroup.getChildAt(i).getTag().equals("quantity_tv")) {
                    arrayList.add((EditText) viewGroup.getChildAt(i));
                }
            } else {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    getAllEditText((ViewGroup) viewGroup.getChildAt(i), arrayList);
                }
            }

        }
    }

    public void increaseUnit(View view) {
        TextView tv = (TextView) ((View) view.getParent()).findViewWithTag("quantity_tv");
        if (tv.getText().toString().isEmpty()) {
            tv.setText("1");
        } else {
            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
        }
    }

    public void decreaseUnit(View view) {
        TextView tv = (TextView) ((View) view.getParent()).findViewWithTag("quantity_tv");
        if (tv.getText().toString().isEmpty() || tv.getText().toString().equals("0")) {
            tv.setText("0");
        } else {
            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) - 1));
        }
    }


}
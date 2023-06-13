package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText unitsEditText;
    private EditText rebateEditText;
    private Button calculateButton;
    private Button clearButton;
    private TextView resultTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            // Handle the About menu item click
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitsEditText = findViewById(R.id.unitsET);
        rebateEditText = findViewById(R.id.rebateET);
        calculateButton = findViewById(R.id.calculateBtn);
        clearButton = findViewById(R.id.clearBtn);
        resultTextView = findViewById(R.id.result);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
            }
        });
    }

    private void calculateBill() {
        String unitsString = unitsEditText.getText().toString();
        String rebateString = rebateEditText.getText().toString();

        // Validate input
        if (unitsString.isEmpty() || rebateString.isEmpty()) {
            resultTextView.setText("Please enter units and rebate%.");
            return;
        }


        double Unit = Double.parseDouble(unitsString);
        double rebate = Double.parseDouble(rebateString);


        double totalCharges  = 0.0;

        if (Unit <= 200) {
            totalCharges  = Unit * 0.218;
        } else if (Unit <= 300) {
            double unitsFirstBlock = 200;
            double unitsNextBlock = Unit - unitsFirstBlock;
            totalCharges  = (unitsFirstBlock * 0.218) + (unitsNextBlock * 0.334);
        } else if (Unit <= 600) {
            double unitsFirstBlock = 200;
            double unitsSecondBlock = 100;
            double unitsNextBlock = Unit - unitsFirstBlock - unitsSecondBlock;
            totalCharges  = (unitsFirstBlock * 0.218) + (unitsSecondBlock * 0.334) + (unitsNextBlock * 0.516);
        } else {
            double unitsFirstBlock = 200;
            double unitsSecondBlock = 100;
            double unitsThirdBlock = 300;
            double unitsNextBlock = Unit - unitsFirstBlock - unitsSecondBlock - unitsThirdBlock;
            totalCharges  = (unitsFirstBlock * 0.218) + (unitsSecondBlock * 0.334) + (unitsThirdBlock * 0.516) + (unitsNextBlock * 0.546);
        }


        totalCharges *= (1 - rebate / 100);


        resultTextView.setText("Final Total Charges Bill: RM " + String.format("%.2f", totalCharges));
    }

    private void clearInput() {
        unitsEditText.setText("");
        rebateEditText.setText("");
        resultTextView.setText("");
    }
}

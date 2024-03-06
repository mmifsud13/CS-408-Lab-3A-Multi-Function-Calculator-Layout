package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CalculatorController controller;
    private TextView displayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        displayTextView = findViewById(R.id.displayTextView);
        controller = new CalculatorController();

        setButtonClickListeners();
    }

    private void setButtonClickListeners() {
        //buttons
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnRoot = findViewById(R.id.btnRoot);
        Button btnClear = findViewById(R.id.btnClear);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnPercent = findViewById(R.id.btnPercent);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnSign = findViewById(R.id.btnSign);
        Button btn0 = findViewById(R.id.btn0);
        Button btnDecimal = findViewById(R.id.btnDecimal);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEquals = findViewById(R.id.btnEquals);

        //listeners
        CalculatorClickListener clickListener = new CalculatorClickListener();
        btn7.setOnClickListener(clickListener);
        btn8.setOnClickListener(clickListener);
        btn9.setOnClickListener(clickListener);
        btnRoot.setOnClickListener(clickListener);
        btnClear.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
        btn6.setOnClickListener(clickListener);
        btnDivide.setOnClickListener(clickListener);
        btnPercent.setOnClickListener(clickListener);
        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btnMultiply.setOnClickListener(clickListener);
        btnSubtract.setOnClickListener(clickListener);
        btnSign.setOnClickListener(clickListener);
        btn0.setOnClickListener(clickListener);
        btnDecimal.setOnClickListener(clickListener);
        btnAdd.setOnClickListener(clickListener);
        btnEquals.setOnClickListener(clickListener);
    }

    private void updateDisplayText() {
        displayTextView.setText(controller.getDisplayText());
    }
    private class CalculatorClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String input = ((Button) view).getText().toString();
            controller.processInput(input);
            updateDisplayText();
        }
    }
}

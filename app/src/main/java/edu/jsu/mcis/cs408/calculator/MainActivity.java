package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int KEYS_HEIGHT = 4;
    private static final int KEYS_WIDTH = 5;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initLayout();
    }

    private void initLayout() {
        ConstraintLayout layout = findViewById(R.id.mainLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        String[] buttonTexts = {"7", "8", "9", getString(R.string.squareRoot), "C", "4", "5", "6", getString(R.string.division), "%", "1", "2", "3", getString(R.string.multiplication), "-", getString(R.string.sign), "0", ".", "+", "="};
        String[] buttonTags = {"btn7", "btn8", "btn9", "btnSqrt", "btnClear", "btn4", "btn5", "btn6", "btnDivide", "btnPercent",
                "btn1", "btn2", "btn3", "btnMultiply", "btnSubtract", "btnSign", "btn0", "btnDecimal", "btnAdd", "btnEquals"};

        Button[] buttons = new Button[buttonTexts.length];

        for (int i = 0; i < buttonTexts.length; i++) {
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(buttonTexts[i]);
            button.setTag(buttonTags[i]);
            buttons[i] = button;
            layout.addView(button);
        }

        for (int i = 0; i < KEYS_HEIGHT; i++) {
            int[] chainButtons = new int[KEYS_WIDTH];
            for (int j = 0; j < KEYS_WIDTH; j++) {
                chainButtons[j] = buttons[i * KEYS_WIDTH + j].getId();
            }
            constraintSet.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                    chainButtons, null, ConstraintSet.CHAIN_PACKED);
        }

        for (int i = 0; i < KEYS_WIDTH; i++) {
            int[] chainButtons = new int[KEYS_HEIGHT];
            for (int j = 0; j < KEYS_HEIGHT; j++) {
                chainButtons[j] = buttons[j * KEYS_WIDTH + i].getId();
            }
            constraintSet.createVerticalChain(ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,
                    chainButtons, null, ConstraintSet.CHAIN_PACKED);
        }

        constraintSet.applyTo(layout);
    }

}

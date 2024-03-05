package edu.jsu.mcis.cs408.calculator;

import android.view.View;

public class ClickHandler implements View.OnClickListener {
    private CalculatorController calculatorController;

    public ClickHandler(CalculatorController controller) {
        calculatorController = controller;
    }

    @Override
    public void onClick(View view) {
        String input = view.getTag().toString();
        calculatorController.processInput(input);
    }
}

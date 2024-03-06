package edu.jsu.mcis.cs408.calculator;
public class CalculatorController {

    private CalculatorModel model;
    private StringBuilder displayText;

    public CalculatorController() {
        model = new CalculatorModel();
        displayText = new StringBuilder();
        updateDisplayText();
    }

    public void processInput(String input) {
        model.processInput(input);
        updateDisplayText();
    }

    private void updateDisplayText() {
        displayText.setLength(0);
        displayText.append(model.getDisplayText());
    }

    public String getDisplayText() {
        return displayText.toString();
    }
}

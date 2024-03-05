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
        // Set the displayText to the string representation of the CalculatorModel
        displayText.setLength(0); // Clear the current display text
        displayText.append(model.getDisplayText()); // Get the display text from the model
    }

    public String getDisplayText() {
        return displayText.toString();
    }
}

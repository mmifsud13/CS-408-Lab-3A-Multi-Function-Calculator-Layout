package edu.jsu.mcis.cs408.calculator;

import java.math.BigDecimal;

public class CalculatorModel {

    private BigDecimal leftOperand;
    private BigDecimal rightOperand;
    private Operator operator;
    private boolean isNewInput;
    private boolean isRightOperandInput;

    public CalculatorModel() {
        clear(); // Initialize operands and operator
        isRightOperandInput = false;
    }

    public String getDisplayText() {
        String expression = "";

        // If there is a right operand and an operator, construct the complete expression
        if (rightOperand != null && operator != null) {
            expression = leftOperand.toString() + " " + operator.symbol + " " + rightOperand.toString();
        } else {
            // Otherwise, display only the left operand
            expression = leftOperand.toString();
        }

        return expression;
    }


    public void processInput(String input) {
        switch (input) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (!isRightOperandInput) {
                    processDigitInput(input);
                } else {
                    processRightOperandInput(input);
                }
                break;
            case ".":
                if (!isRightOperandInput) {
                    processDecimalInput();
                } else {
                    processRightOperandInput(input);
                }
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                processOperatorInput(input);
                break;
            case "=":
                calculateResult();
                break;
            case "C":
                clear();
                break;
            case "±":
                toggleSign();
                break;
            case "√":
                calculateSquareRoot();
                break;
            case "%":
                calculatePercentage();
                break;
            default:
                if (isNewInput || getDisplayText().equals("0")) {
                    // Start a new input if the display shows 0 or after calculation
                    leftOperand = new BigDecimal(input);
                    isNewInput = false;
                } else {
                    // Append input to the existing number
                    leftOperand = leftOperand.multiply(BigDecimal.TEN).add(new BigDecimal(input));
                }
                break;
        }
    }


    private void processRightOperandInput(String input) {
        if (isNewInput) {
            rightOperand = new BigDecimal(input);
            isNewInput = false;
        } else {
            rightOperand = rightOperand.multiply(BigDecimal.TEN).add(new BigDecimal(input));
        }
    }


    private void processDigitInput(String digit) {
        if (isNewInput) {
            leftOperand = new BigDecimal(digit);
            isNewInput = false;
        } else {
            leftOperand = leftOperand.multiply(BigDecimal.TEN).add(new BigDecimal(digit));
        }
    }

    private void processDecimalInput() {
        // Check if decimal point is already present in the current input
        if (!leftOperand.toString().contains(".")) {
            leftOperand = leftOperand.add(BigDecimal.valueOf(0.0));
        }
    }

    private void processOperatorInput(String operatorInput) {
        operator = Operator.fromString(operatorInput);
        isNewInput = true;
        isRightOperandInput = true;
    }

    private void calculateResult() {
        if (operator != null && !isNewInput) {
            BigDecimal result = BigDecimal.ZERO;
            switch (operator) {
                case ADDITION:
                    result = leftOperand.add(rightOperand);
                    break;
                case SUBTRACTION:
                    result = leftOperand.subtract(rightOperand);
                    break;
                case MULTIPLICATION:
                    result = leftOperand.multiply(rightOperand);
                    break;
                case DIVISION:
                    if (rightOperand.equals(BigDecimal.ZERO)) {
                        // Handle division by zero error
                        return;
                    }
                    result = leftOperand.divide(rightOperand, BigDecimal.ROUND_HALF_UP);
                    break;
            }
            leftOperand = result;
            rightOperand = null; // Reset right operand after calculation
        }
        isNewInput = true;
    }
    private void clear() {
        leftOperand = BigDecimal.ZERO;
        rightOperand = BigDecimal.ZERO;
        operator = null;
        isNewInput = true;
    }

    private void toggleSign() {
        leftOperand = leftOperand.negate();
    }

    private void calculateSquareRoot() {
        if (leftOperand.compareTo(BigDecimal.ZERO) >= 0) {
            leftOperand = BigDecimal.valueOf(Math.sqrt(leftOperand.doubleValue()));
        } else {
            // Handle negative number error
        }
    }

    private void calculatePercentage() {
        if (operator != null) {
            rightOperand = leftOperand.multiply(BigDecimal.valueOf(0.01));
        } else {
            // Handle error
        }
    }

    private enum Operator {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public static Operator fromString(String symbol) {
            for (Operator op : Operator.values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            return null;
        }
    }
}

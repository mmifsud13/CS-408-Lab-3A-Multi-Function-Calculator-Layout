package edu.jsu.mcis.cs408.calculator;

import android.widget.TextView;

import java.math.BigDecimal;

public class CalculatorModel {

    private BigDecimal leftOperand;
    private BigDecimal rightOperand;
    private Operator operator;
    private boolean isNewInput;
    private boolean isRightOperandInput;
    private int error = 0;

    public CalculatorModel() {
        clear(); // Initialize operands and operator
        isRightOperandInput = false;
    }

    public String getDisplayText() {
        String expression = "";

        // If there is a right operand and an operator, construct the complete expression
        if (error == 0) {
            if (rightOperand != null && operator != null) {
                expression = leftOperand.toString() + " " + operator.symbol + " " + rightOperand.toString();
            } else {
                // Otherwise, display only the left operand
                expression = leftOperand.toString();
            }
        }
        else {
            if (error == 1)
            {
                expression = "Error: Cannot divide by zero";
                error = 0;
            }
            else if (error == 2)
            {
                expression = "Error: Please choose a positive number";
                error = 0;
            }
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
                    leftOperand = new BigDecimal(input);
                    isNewInput = false;
                } else {
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

    //BROKEN
    private void processDecimalInput() {
        BigDecimal operandToAddDecimal;
        if (!isRightOperandInput) {
            operandToAddDecimal = leftOperand;
        } else {
            if (rightOperand == null) {
                rightOperand = BigDecimal.ZERO;
            }
            operandToAddDecimal = rightOperand;
        }

        if (!operandToAddDecimal.toString().contains(".")) {
            operandToAddDecimal = new BigDecimal(operandToAddDecimal.toString() + ".");
        }

        if (!isRightOperandInput) {
            leftOperand = operandToAddDecimal;
        } else {
            rightOperand = operandToAddDecimal;
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
                        error = 1;
                        return;
                    }
                    result = leftOperand.divide(rightOperand, 10, BigDecimal.ROUND_HALF_UP);
                    break;
            }
            leftOperand = result;
            rightOperand = null;
        }
        isNewInput = true;
    }

    private void clear() {
        leftOperand = BigDecimal.ZERO;
        rightOperand = BigDecimal.ZERO;
        operator = null;
        isNewInput = true;
        isRightOperandInput = false;
    }


    private void toggleSign() {
        leftOperand = leftOperand.negate();
    }

    private void calculateSquareRoot() {
        if (leftOperand.compareTo(BigDecimal.ZERO) >= 0) {
            leftOperand = BigDecimal.valueOf(Math.sqrt(leftOperand.doubleValue()));
        } else {
            error = 2;
        }
    }

    private void calculatePercentage() {
        if (operator != null && rightOperand == null) {
            rightOperand = BigDecimal.valueOf(0.01);
        } else {
            leftOperand = leftOperand.multiply(BigDecimal.valueOf(0.01));
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

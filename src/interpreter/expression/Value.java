package interpreter.expression;

import interpreter.type.OperationType;
import interpreter.type.VariableType;
import interpreter.exceptions.IncompatibilityTypes;
import interpreter.exceptions.UnauthorizedOperation;
import interpreter.exceptions.UnknownType;

import static interpreter.type.OperationType.*;

public class Value {

    private final VariableType type;
    private Number number;
    private String string;

    public Value(VariableType type, Number number) throws UnknownType {
        if (isNotCorrectType(type))
            throw new UnknownType();
        this.type = type;
        this.number = number;
    }

    public Value(VariableType type, String value) throws UnknownType {
        if (isNotCorrectType(type))
            throw new UnknownType();
        this.type = type;
        this.string = value;
    }

    private Value(VariableType type) {
        this.type = type;
        this.number = null;
    }

    public static boolean isNotCorrectType(VariableType type) {
        return type == VariableType.UNDEFINED;
    }

    public Value addition(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, ADDITION);
    }

    public Value subtraction(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, SUBTRACTION);
    }


    public Value multiplication(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, MULTIPLICATION);
    }

    public Value division(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, DIVISION);
    }

    public Value modulo(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, MODULO);
    }

    public Value greater(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, GREATER);
    }

    public Value smaller(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, SMALLER);
    }

    public Value equal(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, EQUAL);
    }

    public Value and(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, AND);
    }

    public Value or(Value value) throws IncompatibilityTypes, UnauthorizedOperation {
        return setValueAfterOperation(value, OR);
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public String getString() {
        return string;
    }

    public boolean isString() {
        return type == VariableType.STRING;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Value setValueAfterOperation(Value value, OperationType operationType) throws IncompatibilityTypes, UnauthorizedOperation {
        checkIfTypeIsCompatibility(value);

        Value newValue = new Value(type);

        if (ifIsStringThenConcatenate(value, operationType, newValue)) {
            return newValue;
        }
        double result = calculateValue(number.intValue(), value.number.intValue(), operationType);
        switch (type) {
            case INTEGER: {
                newValue.setNumber((int) result);
                break;
            }
            case DOUBLE: {
                newValue.setNumber(result);
                break;
            }
            default: {
                throw new UnauthorizedOperation();
            }
        }
        return newValue;
    }

    private void checkIfTypeIsCompatibility(Value value) throws IncompatibilityTypes {
        if (!type.equals(value.type))
            throw new IncompatibilityTypes();
    }

    public boolean ifIsStringThenConcatenate(Value value, OperationType operationType, Value newValue) {
        if (operationType == OperationType.ADDITION && type == VariableType.STRING) {
            newValue.setString(string + value.string);
            return true;
        }
        return false;
    }

    private double calculateValue(double firstValue, double secondValue, OperationType operationType) {
        switch (operationType) {
            case ADDITION:
                return firstValue + secondValue;
            case SUBTRACTION:
                return firstValue - secondValue;
            case MULTIPLICATION:
                return firstValue * secondValue;
            case DIVISION:
                return firstValue / secondValue;
            case MODULO:
                return firstValue % secondValue;
            case EQUAL:
                return firstValue == secondValue ? 1.0 : 0.0;
            case GREATER:
                return firstValue > secondValue ? 1.0 : 0.0;
            case SMALLER:
                return firstValue < secondValue ? 1.0 : 0.0;
            case AND:
                return firstValue != 0 && secondValue != 0 ? 1.0 : 0.0;
            case OR:
                return firstValue != 0 || secondValue != 0 ? 1.0 : 0.0;
            default:
                return 0;
        }
    }
}

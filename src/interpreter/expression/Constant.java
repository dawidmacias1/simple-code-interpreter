package interpreter.expression;

import interpreter.type.VariableType;
import interpreter.exceptions.UnknownType;

import java.util.HashMap;

public class Constant implements Expression {

    private final Value constantValue;

    public Constant(Number number) throws UnknownType {
        this.constantValue = new Value(findType(number), number);
    }

    @Override
    public Value eval(HashMap<String, Value> map) {
        return constantValue;
    }

    private VariableType findType(Number number) {
        if (isDouble(number))
            return VariableType.DOUBLE;
        else
            return VariableType.INTEGER;
    }

    private boolean isDouble(Number number) {
        return number.toString().contains(".");
    }
}


package interpreter.expression;

import interpreter.type.VariableType;
import interpreter.exceptions.UnknownType;

import java.util.HashMap;

public class NewString implements Expression {

    private final Value stringValue;

    public NewString(String value) throws UnknownType {
        this.stringValue = new Value(VariableType.STRING, value);
    }

    @Override
    public Value eval(HashMap<String, Value> map) {
        return stringValue;
    }
}

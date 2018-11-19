package interpreter.expression;

import interpreter.exceptions.VariableNotFound;

import java.util.HashMap;

public class Variable implements Expression {

    private final String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Value eval(HashMap<String, Value> map) throws  VariableNotFound {
        if (isNotVariableExistsInMemory(map))
            throw new VariableNotFound();
        return map.get(variableName);
    }

    public boolean isNotVariableExistsInMemory(HashMap<String, Value> map) {
        return !map.containsKey(variableName);
    }
}

package interpreter.instruction.simple;

import interpreter.exceptions.IncompatibilityTypes;
import interpreter.exceptions.UnauthorizedOperation;
import interpreter.exceptions.UnknownOperator;
import interpreter.exceptions.VariableNotFound;
import interpreter.expression.Expression;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class Assign implements Program {

    private final String variableName;
    private final Expression expression;

    public Assign(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void eval(HashMap<String, Value> map) throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnauthorizedOperation {
        map.put(variableName, expression.eval(map));
    }
}

package interpreter.expression;

import interpreter.exceptions.IncompatibilityTypes;
import interpreter.exceptions.UnauthorizedOperation;
import interpreter.exceptions.UnknownOperator;
import interpreter.exceptions.VariableNotFound;

import java.util.HashMap;

public interface Expression {

    Value eval(HashMap<String, Value> map)
            throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnauthorizedOperation;

}

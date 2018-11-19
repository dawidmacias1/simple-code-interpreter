package interpreter.instruction.complex;

import interpreter.exceptions.IncompatibilityTypes;
import interpreter.exceptions.UnauthorizedOperation;
import interpreter.exceptions.UnknownOperator;
import interpreter.exceptions.VariableNotFound;
import interpreter.expression.Expression;
import interpreter.expression.Value;

import java.util.HashMap;

class Util {

    private Util() {
    }

    static boolean isConditionFulfilled(Expression condition, HashMap<String, Value> map)
            throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnauthorizedOperation {
        return !condition.eval(map).getNumber().equals(0) && !condition.eval(map).getNumber().equals(0.0);
    }
}

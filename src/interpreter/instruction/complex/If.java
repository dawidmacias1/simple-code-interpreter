package interpreter.instruction.complex;

import interpreter.exceptions.*;
import interpreter.expression.Expression;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class If implements Program {

    private final Program branchThen;
    private final Program branchElse;
    private final Expression condition;

    public If(Expression condition, Program branchThen, Program branchElse) {
        this.condition = condition;
        this.branchThen = branchThen;
        this.branchElse = branchElse;
    }

    @Override
    public void eval(HashMap<String, Value> map)
            throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnknownType, UnauthorizedOperation {
        if (Util.isConditionFulfilled(condition, map))
            branchThen.eval(map);
        else
            branchElse.eval(map);
    }
}

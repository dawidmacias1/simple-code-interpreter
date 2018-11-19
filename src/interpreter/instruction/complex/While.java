package interpreter.instruction.complex;

import interpreter.exceptions.*;
import interpreter.expression.*;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class While implements Program {

    private final Program body;
    private final Expression condition;

    public While(Expression condition, Program body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void eval(HashMap<String, Value> map) throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnknownType, UnauthorizedOperation {
        if (Util.isConditionFulfilled(condition, map)) {
            body.eval(map);
            this.eval(map);
        }
    }
}

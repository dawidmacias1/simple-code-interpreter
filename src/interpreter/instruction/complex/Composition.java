package interpreter.instruction.complex;

import interpreter.exceptions.*;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class Composition implements Program {

    private final Program left;
    private final Program right;

    public Composition(Program left, Program right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void eval(HashMap<String, Value> map) throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnknownType, UnauthorizedOperation {
        left.eval(map);
        right.eval(map);
    }
}

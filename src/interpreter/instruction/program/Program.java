package interpreter.instruction.program;

import interpreter.exceptions.*;
import interpreter.expression.Value;

import java.util.HashMap;

public interface Program {

    void eval(HashMap<String, Value> map)
            throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnknownType, UnauthorizedOperation;

}

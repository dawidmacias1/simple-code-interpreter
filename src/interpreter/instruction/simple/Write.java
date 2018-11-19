package interpreter.instruction.simple;

import interpreter.exceptions.VariableNotFound;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class Write implements Program {

    private final String valueToPrint;

    public Write(String valueToPrint) {
        this.valueToPrint = valueToPrint;
    }

    @Override
    public void eval(HashMap<String, Value> map) throws VariableNotFound {
        if (!map.containsKey(valueToPrint)) {
            throw new VariableNotFound();
        }
        if (map.get(valueToPrint).isString()) {
            System.out.println(map.get(valueToPrint).getString());
        } else {
            System.out.println(map.get(valueToPrint).getNumber());
        }
    }
}

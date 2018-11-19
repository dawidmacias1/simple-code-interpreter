package interpreter.instruction.simple;

import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;

public class WriteFromCode implements Program {

    private final String valueToPrint;

    public WriteFromCode(String valueToPrint) {
        this.valueToPrint = valueToPrint;
    }

    @Override
    public void eval(HashMap<String, Value> map) {
        System.out.println(valueToPrint);
    }
}

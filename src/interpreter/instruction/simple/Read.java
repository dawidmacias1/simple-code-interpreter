package interpreter.instruction.simple;

import interpreter.type.VariableType;
import interpreter.exceptions.UnknownType;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;

import java.util.HashMap;
import java.util.Scanner;

public class Read implements Program {

    private final String readValue;

    public Read(String readValue) {
        this.readValue = readValue;
    }

    @Override
    public void eval(HashMap<String, Value> map) throws UnknownType {
        Scanner scanner = new Scanner(System.in);
        map.put(readValue, findType(scanner.nextLine()));
    }

    private Value findType(String number) throws UnknownType {
        if (isNotNumber(number)) {
            return new Value(VariableType.STRING, number);
        } else if (isDouble(number)) {
            return new Value(VariableType.DOUBLE, Double.parseDouble(number));
        } else {
            return new Value(VariableType.INTEGER, Integer.parseInt(number));
        }
    }

    private boolean isDouble(String number) {
        return number.contains(".");
    }

    private boolean isNotNumber(String number) {
        return !number.matches(".*\\d+.*");
    }
}

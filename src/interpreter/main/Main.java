package interpreter.main;

import interpreter.exceptions.*;
import interpreter.expression.Value;
import interpreter.instruction.program.Program;
import interpreter.parser.Parser;

import java.util.HashMap;

public class Main {

    public static void main(String[] args)
            throws UnknownType, UnknownOperator, VariableNotFound, IncompatibilityTypes, NotParsed, UnauthorizedOperation {

        HashMap<String, Value> map = new HashMap<>();

        // Example code designating n prime numbers
        String code = "n=2500 " +
                "lp=0 " +
                "p=2 " +
                "bool=0 " +
                "tmp=0 " +
                "flag=1 " +
                " while (lp<n) {" +
                " d=2 " +
                " bool=1 " +
                " while (d<p)&flag { " +
                "  if (p%d)=0 { " +
                "   bool=0 " +
                "   flag=0 " +
                "  } else { " +
                "   skip " +
                "  } " +
                "  d=d+1 " +
                " } " +
                " flag=1 " +
                " if bool { " +
                "  write p " +
                "  lp=lp+1 " +
                " } else { " +
                "  skip " +
                " } " +
                " p=p+1 " +
                "};";
        Parser parser = new Parser(code);
        Program p = parser.parseProgram();

        p.eval(map);
    }
}

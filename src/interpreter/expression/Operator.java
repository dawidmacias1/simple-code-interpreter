package interpreter.expression;

import interpreter.exceptions.IncompatibilityTypes;
import interpreter.exceptions.UnauthorizedOperation;
import interpreter.exceptions.UnknownOperator;
import interpreter.exceptions.VariableNotFound;

import java.util.HashMap;

public class Operator implements Expression {

    private final char operatorSymbol;
    private final Expression leftBranch;
    private final Expression rightBranch;

    public Operator(char symbol, Expression leftBranch, Expression rightBranch) {
        this.operatorSymbol = symbol;
        this.leftBranch = leftBranch;
        this.rightBranch = rightBranch;
    }

    @Override
    public Value eval(HashMap<String, Value> map) throws UnknownOperator, VariableNotFound, IncompatibilityTypes, UnauthorizedOperation {
        switch (operatorSymbol) {
            case '+':
                return leftBranch.eval(map).addition(rightBranch.eval(map));
            case '-':
                return leftBranch.eval(map).subtraction(rightBranch.eval(map));
            case '*':
                return leftBranch.eval(map).multiplication(rightBranch.eval(map));
            case '/':
                return leftBranch.eval(map).division(rightBranch.eval(map));
            case '%':
                return leftBranch.eval(map).modulo(rightBranch.eval(map));
            case '>':
                return leftBranch.eval(map).greater(rightBranch.eval(map));
            case '<':
                return leftBranch.eval(map).smaller(rightBranch.eval(map));
            case '=':
                return leftBranch.eval(map).equal(rightBranch.eval(map));
            case '&':
                return leftBranch.eval(map).and(rightBranch.eval(map));
            case '|':
                return leftBranch.eval(map).or(rightBranch.eval(map));
            default:
                throw new UnknownOperator();
        }
    }
}

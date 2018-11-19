package interpreter.parser.expression;

import interpreter.exceptions.NotParsed;
import interpreter.exceptions.UnknownType;
import interpreter.expression.*;

public class ParserExpression {

    private final String input;
    private final Integer[] position;

    public ParserExpression(String input, Integer[] position) {
        this.input = input;
        this.position = position;
    }

    private char lookAhead() {
        skipWhitespace();
        return input.charAt(position[0]);
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(input.charAt(position[0])))
            position[0]++;
    }

    public Expression parseExpression() throws NotParsed, UnknownType {
        Expression expression = parseSum();
        if (position[0] == input.length() - 1)
            return expression;
        else
            throw new NotParsed();
    }

    public Expression parseString() throws UnknownType {
        StringBuilder stringBuilder = new StringBuilder();
        char character;
        position[0]++;
        character = input.charAt(position[0]);
        while (character != '\'') {
            stringBuilder.append(character);
            position[0]++;
            character = input.charAt(position[0]);
        }
        position[0]++;
        return new NewString(stringBuilder.toString());
    }

    public Expression parseSum() throws NotParsed, UnknownType {
        Expression expression = parseMult();
        char character = lookAhead();
        while (character == '+' || character == '-') {
            position[0]++;
            expression = new Operator(character, expression, parseMult());
            character = lookAhead();
        }
        return expression;
    }

    private Expression parseMult() throws NotParsed, UnknownType {
        Expression expression = parseLogic();
        char character = lookAhead();
        while (character == '*' || character == '/' || character == '%') {
            position[0]++;
            expression = new Operator(character, expression, parseLogic());
            character = lookAhead();
        }
        return expression;
    }

    public Expression parseLogic() throws NotParsed, UnknownType {
        Expression expression = parseTerm();
        char character = lookAhead();
        while (character == '>' || character == '<' || character == '=' || character == '&' || character == '|') {
            position[0]++;
            expression = new Operator(character, expression, parseTerm());
            character = lookAhead();
        }
        return expression;
    }

    private Expression parseTerm() throws NotParsed, UnknownType {
        char character = lookAhead();
        if (Character.isDigit(character))
            return parseConstant();
        else if (Character.isAlphabetic(character))
            return parseVariable();
        else if (character == '(')
            return parseParen();
        else
            throw new NotParsed();
    }

    private Expression parseConstant() throws UnknownType {
        String number;
        StringBuilder template = new StringBuilder();
        while (Character.isDigit(input.charAt(position[0])) || input.charAt(position[0]) == '.') {
            template.append(input.charAt(position[0]));
            position[0]++;
        }
        number = template.toString();
        if (number.contains(".")) {
            return new Constant(Double.parseDouble(number));
        } else
            return new Constant(Integer.parseInt(number));
    }

    private Expression parseVariable() {
        StringBuilder template = new StringBuilder();
        while (Character.isDigit(input.charAt(position[0])) || Character.isAlphabetic(input.charAt(position[0]))) {
            template.append(input.charAt(position[0]));
            position[0]++;
        }
        return new Variable(template.toString());
    }

    private Expression parseParen() throws NotParsed, UnknownType {
        position[0]++;
        Expression expression = parseSum();
        if (lookAhead() == ')') {
            position[0]++;
            return expression;
        } else
            throw new NotParsed();
    }
}

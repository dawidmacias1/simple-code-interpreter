package interpreter.parser.program;

import interpreter.exceptions.NotParsed;
import interpreter.exceptions.UnknownType;
import interpreter.expression.Expression;
import interpreter.instruction.complex.Composition;
import interpreter.instruction.complex.If;
import interpreter.instruction.complex.While;
import interpreter.instruction.program.Program;
import interpreter.instruction.simple.*;
import interpreter.parser.expression.ParserExpression;

public class ParserProgram {

    private static final char END_OF_LINE = ';';
    private final String input;
    private final Integer[] position;
    private final ParserExpression parserExpression;

    public ParserProgram(String input, Integer[] position) {
        this.input = input;
        this.position = position;
        this.parserExpression = new ParserExpression(this.input, this.position);
    }

    public Program parseProgram() throws NotParsed, UnknownType {
        Program program = parseBlock();
        if (lookAhead() == END_OF_LINE)
            return program;
        else
            throw new NotParsed();
    }

    private String parseIdentifier() {
        StringBuilder template = new StringBuilder();
        while (Character.isDigit(input.charAt(position[0])) || Character.isAlphabetic(input.charAt(position[0]))) {
            template.append(input.charAt(position[0]));
            position[0]++;
        }
        return template.toString();
    }

    private Program parseBlock() throws NotParsed, UnknownType {
        Program program = parseInstruction();
        char character = lookAhead();

        while (character != '}' && character != END_OF_LINE) {
            Program programInBlock = parseInstruction();
            program = new Composition(program, programInBlock);
            character = lookAhead();
        }
        return program;
    }

    private Program parseInstruction() throws NotParsed, UnknownType {
        char character = lookAhead();
        if (character == '{') {
            position[0]++;
            Program program = parseBlock();
            if (lookAhead() == '}') {
                position[0]++;
                return program;
            } else {
                throw new NotParsed();
            }
        } else if (Character.isAlphabetic(character)) {
            return findIdentifier();
        } else
            throw new NotParsed();
    }

    private Program findIdentifier() throws NotParsed, UnknownType {
        String parseIdentifier = parseIdentifier();
        switch (parseIdentifier) {
            case "read":
                return parseRead();
            case "write":
                return parseWrite();
            case "if":
                return parseIf();
            case "while":
                return parseWhile();
            case "skip":
                return new Skip();
            default:
                return parseAssign(parseIdentifier);
        }
    }

    private Program parseRead() throws NotParsed {
        if (Character.isAlphabetic(lookAhead())) {
            String parseIdentifier = parseIdentifier();
            return new Read(parseIdentifier);
        } else
            throw new NotParsed();
    }

    private Program parseWrite() throws NotParsed {
        if (Character.isAlphabetic(lookAhead())) {
            return new Write(parseIdentifier());
        } else if (lookAhead() == '\'') {
            position[0]++;
            String var = parseIdentifier();
            position[0]++;
            return new WriteFromCode(var);
        } else {
            throw new NotParsed();
        }
    }

    private Program parseIf() throws NotParsed, UnknownType {
        Expression expression = parserExpression.parseSum();
        Program programThen = parseInstruction();
        if (Character.isAlphabetic(lookAhead())) {
            if (parseIdentifier().equals("else")) {
                Program programElse = parseInstruction();
                return new If(expression, programThen, programElse);
            } else
                throw new NotParsed();
        } else
            throw new NotParsed();
    }

    private Program parseWhile() throws NotParsed, UnknownType {
        return new While(parserExpression.parseSum(), parseInstruction());
    }

    private Program parseAssign(String input) throws NotParsed, UnknownType {
        char character = lookAhead();
        Expression expression = null;
        if (character == '=') {
            position[0]++;
            if (lookAhead() == '\'') {
                expression = parserExpression.parseString();
            } else {
                expression = parserExpression.parseSum();
            }
            return new Assign(input, expression);
        } else
            throw new NotParsed();
    }

    private char lookAhead() {
        skipWhitespace();
        return input.charAt(position[0]);
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(input.charAt(position[0])))
            position[0]++;
    }
}

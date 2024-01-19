package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Code {
    private final String code;
    private final int length;

    public Code(String string) {
        code = string;
        length = this.code.length();
    }

    public Code(int length, int possibleSymbols) throws RuntimeException {
        if (length > 36 || possibleSymbols > 36) {
            throw new RuntimeException("Error: the maximum number of possible symbols is 36.");
        }
        this.length = length;
        this.code = generateCode(length, possibleSymbols);
    }

    private String generateCode(int length, int possibleSymbols) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        Set<Character> usedSymbols = new HashSet<>();

        for (int i = 0; i < length; i++) {
            char symbol;
            do {
                int randomIndex = random.nextInt(possibleSymbols);
                symbol = getSymbolForIndex(randomIndex);
            } while (usedSymbols.contains(symbol));

            usedSymbols.add(symbol);
            sb.append(symbol);
        }

        return sb.toString();
    }


    private char getSymbolForIndex(int index) {
        if (index < 10) {
            // Digit 0-9
            return (char) ('0' + index);
        } else {
            // Letter a-z
            return (char) ('a' + index - 10);
        }
    }

    public String getCode() {
        return code;
    }
}

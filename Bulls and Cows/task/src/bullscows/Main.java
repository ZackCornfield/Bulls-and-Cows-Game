package bullscows;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int turnCount = 1;
        int codeLength = 0;
        int possibleSymbols = 0;
        Grade grade = new Grade(); // Create a Grade object to track the user's guesses
        Code secretCode = null;

        while(secretCode == null) {
            codeLength = getCodeLength();
            possibleSymbols = getNumPossibleSymbols(codeLength);

            // Generate a secret code using the Code class
            try{
                secretCode = new Code(codeLength, possibleSymbols);
            }catch(RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("The secret is prepared: " + "*".repeat(codeLength) +
                " (" + getSymbolRange(possibleSymbols) + ").");
        System.out.println("Okay, let's start a game!");

        do {
            grade.reset();
            System.out.print("Turn " + (turnCount) + ":\n> ");

            // Get user guess
            String userCode = scanner.next();

            // Grade the user's guess using the Grade class
            grade.grade(secretCode.getCode(), userCode);

            // Display the result
            System.out.println("Turn " + turnCount + ": " + grade.getGrade());

            // Increment turn count
            turnCount++;
        } while (!grade.isSolved());

        System.out.println("Congratulations! You guessed the secret code " + secretCode.getCode() + " in " + (turnCount - 1) + " turns.");
    }

    private static int getCodeLength() {
        int length;

        while (true) {
            try {
                System.out.print("Please, enter the secret code's length: ");
                String input = scanner.next();

                if (!input.matches("\\d+")) {
                    throw new NumberFormatException();
                }

                length = Integer.parseInt(input);

                if (length <= 0) {
                    throw new NumberFormatException();
                }

                break; // If the input is valid, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number.");
            }
        }

        return length;
    }

    private static int getNumPossibleSymbols(int length) {
        int numOfPossibleSymbols = 0;

        while (true) {
            try {
                System.out.println("Input the number of possible symbols in the code: ");
                String input = scanner.next();

                if (!input.matches("\\d+")) {
                    throw new NumberFormatException();
                }

                numOfPossibleSymbols = Integer.parseInt(input);

                if (numOfPossibleSymbols < length) {
                    throw new NumberFormatException("Error: it's not possible to generate a code with a length of " + length + " with " + numOfPossibleSymbols + " unique symbols.");
                }else if(numOfPossibleSymbols > 36) {
                    throw new NumberFormatException("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).\n");
                }
                break; // If the input is valid, exit the loop
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return numOfPossibleSymbols;
    }

    // Method to get the symbol range string for display
    private static String getSymbolRange(int possibleSymbols) {
        if (possibleSymbols <= 10) {
            char lastSymbol = (char) ('0' + possibleSymbols - 1);
            return "0-" + lastSymbol;
        } else {
            char lastSymbol = (char) ('a' + possibleSymbols - 11);
            return "0-9, a-" + lastSymbol;
        }
    }

}

package bullscows;

import java.util.Scanner;

public class Application {

    public static Scanner scanner = new Scanner(System.in);
    private int turnCount;
    private int codeLength;
    private int possibleSymbols;
    private Grade grade;
    Code code;
    String userGuess;

    public Application() {
        turnCount = 1;
        codeLength = 0;
        possibleSymbols = 0;
        grade = new Grade();
        code = null;
    }

    public void startGame() {
        while(code == null) {
            getCodeLength();
            getNumPossibleSymbols();

            // Generate a secret code using the Code class
            try{
                code = new Code(codeLength, possibleSymbols);
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
            getUserGuess();

            // Grade the user's guess using the Grade class
            grade.grade(code.getCode(), userGuess);

            // Display the result
            System.out.println("Turn " + turnCount + ": " + grade.getGrade());

            // Increment turn count
            turnCount++;
        } while (!grade.isSolved());

        System.out.println("Congratulations! You guessed the secret code " + code.getCode() + " in " + (turnCount - 1) + " turns.");
    }

    private void getCodeLength() {

        while (true) {
            try {
                System.out.print("Please, enter the secret code's length: ");
                String input = scanner.next();

                if (!input.matches("\\d+")) {
                    throw new NumberFormatException();
                }

                codeLength = Integer.parseInt(input);

                if (codeLength <= 0) {
                    throw new NumberFormatException();
                }

                break; // If the input is valid, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + scanner.next() + "\" isn't a valid number.");
            }
        }
    }

    private void getNumPossibleSymbols() {

        while (true) {
            try {
                System.out.println("Input the number of possible symbols in the code: ");
                String input = scanner.next();

                if (!input.matches("\\d+")) {
                    throw new NumberFormatException();
                }

                possibleSymbols = Integer.parseInt(input);

                if (possibleSymbols < codeLength) {
                    throw new NumberFormatException("Error: it's not possible to generate a code with a length of " + codeLength + " with " + possibleSymbols + " unique symbols.");
                }else if(possibleSymbols > 36) {
                    throw new NumberFormatException("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).\n");
                }
                break; // If the input is valid, exit the loop
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getUserGuess() {
        while (true) {
            try {
                userGuess = scanner.next();
                int length = userGuess.length();
                if (length < codeLength || length > codeLength) {
                    throw new NumberFormatException("The guess needs to be the same length as the secret code (" + codeLength + "), please try again: ");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
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

    public void endGame() {
        scanner.close();
    }
}

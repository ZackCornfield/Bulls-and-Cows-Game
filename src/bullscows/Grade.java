package bullscows;

public class Grade {
    private int bulls;
    private int cows;
    private String grade;
    private boolean solved;

    public Grade() {
        reset();
    }

    public Grade(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
        this.solved = false;
        generateGradeStatement();
    }

    public void reset() {
        this.bulls = 0;
        this.cows = 0;
        this.grade = "None";
        this.solved = false;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isSolved() {
        return solved;
    }

    public void grade(String code, String userGuess) {
        if (code.equals(userGuess)) {
            solved = true;
            bulls = code.length();
            cows = 0;
        } else {
            for (int i = 0; i < code.length(); i++) {
                char guessSymbol = userGuess.charAt(i);
                char secretSymbol = code.charAt(i);

                if (guessSymbol == secretSymbol) {
                    bulls++;
                } else if (code.contains(String.valueOf(guessSymbol))) {
                    cows++;
                }
            }
        }
        generateGradeStatement();
    }

    private void generateGradeStatement() {
        if (bulls > 0 && cows > 0) {
            grade = "Grade: " + bulls + " bull(s) and " + cows + " cow(s)";
        } else if (bulls > 0) {
            grade = "Grade: " + bulls + " bull(s)";
        } else if (cows > 0) {
            grade = "Grade: " + cows + " cow(s)";
        } else {
            grade = "Grade: None";
        }
    }
}

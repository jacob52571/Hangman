import javax.swing.*;
import java.io.*;

// this code is messy but it works
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileWriter writer = new FileWriter("src/main/java/used.txt", false);
        writer.write("");
        writer.close();
        int wins = 0;
        int losses = 0;
        while (true) {
            if (play()) {
                wins++;
            } else {
                losses++;
            }
            System.out.println("Wins: " + wins + " Losses: " + losses);
            if (JOptionPane.showConfirmDialog(null, "Play again?") != 0) {
                break;
            }
        }
    }

    public static boolean hasDashes(char[] dashes) {
        for (char c : dashes) {
            if (c == '-') return true;
        }
        return false;
    }

    public static boolean play() throws FileNotFoundException, IOException {
        Hangman hangman = new Hangman();
        System.out.println("The category is: " + hangman.getCategory());
        while (hangman.getValidGuesses() != 0) {
            char[] currentDashes = hangman.getDashes();
            if (hangman.getGuessed()) {
                System.out.println("You win!");
                break;
            }
            drawHangman(hangman.getValidGuesses());
            System.out.println(hangman);
            try {
                String guess = JOptionPane.showInputDialog("Guess a letter: ");
                if (guess == null) {
                    JOptionPane.showMessageDialog(null, "You quit the game.");
                    System.exit(0);
                }
                hangman.getGuess(guess);
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }
            hangman.update();
            char[] newDashes = hangman.getDashes();
            boolean same = true;
            for (int i = 0; i < currentDashes.length; i++) {
                if (currentDashes[i] != newDashes[i]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                hangman.useGuess();
                System.out.println("That letter is not in the word.");
            }
            if (!hasDashes(newDashes)) {
                hangman.setGuessed(true);
                System.out.println("You win!");
                break;
            }
            System.out.println("You have " + hangman.getValidGuesses() + " guesses left.");
        }

        FileWriter writer = new FileWriter("src/main/java/used.txt", true);
        writer.write(hangman.getCategory() + ":" + hangman.getSecretWord() + "\n");
        writer.close();
        if (hangman.getGuessed()) {
            System.out.println("You win! The word was " + hangman.getSecretWord());
            return true;
        } else {
            System.out.println("You lose! The word was " + hangman.getSecretWord());
            return false;
        }
    }

    public static void drawHangman(int guessesLeft) {
        System.out.println("  _______");
        if (guessesLeft < 7) {
            System.out.println("  |     |");
        } else {
            System.out.println("  |");
        }
        if (guessesLeft < 6) {
            System.out.println("  |     O");
        } else {
            System.out.println("  |");
        }
        if (guessesLeft < 4) {
            System.out.println("  |    /|\\");
        } else if (guessesLeft < 5) {
            System.out.println("  |    /|");
        } else if (guessesLeft < 6) {
            System.out.println("  |    /");
        } else {
            System.out.println("  |");
        }
        if (guessesLeft < 2) {
            System.out.println("  |    / \\");
        } else if (guessesLeft < 3) {
            System.out.println("  |    /");
        } else {
            System.out.println("  |");
        }
        System.out.println("__|__");
    }
}
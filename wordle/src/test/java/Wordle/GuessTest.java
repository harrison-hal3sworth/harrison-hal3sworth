package Wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isCorrect() {
        Answer answer = new Answer("ABCDE");
        Guess correctGuess = new Guess("ABCDE", answer);
        Guess incorrectGuess = new Guess("BCDEA", answer);

        assertTrue(correctGuess.isCorrect());
        assertFalse(incorrectGuess.isCorrect());
    }

    @Test
    void printGuessResult() {
        Answer answer = new Answer("ABCDE");
        Guess correctGuess = new Guess("ABCDE", answer);
        Guess partialCorrectGuess = new Guess("ABDCE", answer);
        Guess incorrectGuess = new Guess("BCDEA", answer);

        System.out.print("Correct guess: ");
        correctGuess.printGuessResult();

        System.out.print("Partially correct guess: ");
        partialCorrectGuess.printGuessResult();

        System.out.print("Incorrect guess: ");
        incorrectGuess.printGuessResult();
    }

    @Test
    void getGuess() {
        Answer answer = new Answer("ABCDE");
        Guess guess = new Guess("ABDCE", answer);

        assertEquals("ABDCE", guess.getGuess());
    }
}
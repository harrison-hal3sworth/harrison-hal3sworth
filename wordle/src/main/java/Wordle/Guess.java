package Wordle;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 3/6/23
 * Time: 10:17 AM
 *
 * Project: csci205_hw
 * Package: PACKAGE_NAME
 * Class: Wordle.Guess
 *
 * Description:
 *
 * ****************************************
 */

import java.util.Set;
import java.util.TreeMap;

public class Guess {

    private String guess;
    private Answer answer;
    private String aString;

    public Guess(String guess, Answer answer) {
        guess = guess.toUpperCase();
        this.guess = guess;
        this.answer = answer;
        this.aString = answer.getAnswer().toUpperCase();
    }

    public boolean isCorrect() {
        return this.guess.equals(this.aString);
    }

    public void printGuessResult() {
        TreeMap<Integer, String> answerMap = new TreeMap<>();
        for(int i = 0; i < 5; i++) {
            answerMap.put(i, String.valueOf(aString.charAt(i)));
        }
        for(int i = 0; i < 5; i++) {
            if(answerMap.containsValue(String.valueOf(this.getGuess().charAt(i)))) {
                Set<Integer> k = answerMap.keySet();
                for(int j : k) {
                    if(answerMap.get(j).equals(String.valueOf(this.getGuess().charAt(i)))) {
                        if(j == i) {
                            System.out.print("+");
                        }
                        else {
                            System.out.print("-");
                        }
                        answerMap.remove(j,String.valueOf(this.getGuess().charAt(i)));
                        break;
                    }

                }
            }

            else {
                System.out.print("x");
            }
        }
        System.out.println();

    }

    public String getGuess() {return this.guess;}

    public String toString() {
        String s = "'" + this.guess + "'";
        return s;
    }
}

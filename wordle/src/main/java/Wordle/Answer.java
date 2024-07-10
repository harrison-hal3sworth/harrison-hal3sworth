/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 3/6/23
 * Time: 10:47 AM
 *
 * Project: csci205_hw
 * Package: PACKAGE_NAME
 * Class: Wordle.Answer
 *
 * Description:
 *
 * ****************************************
 */

package Wordle;

public class Answer {


    private final String answer;

    public Answer(String answer) {
        this.answer = answer.toUpperCase();
    }

    public String getAnswer() {return this.answer;}

    public String toString() {
            String s = "'" + this.answer + "'";
            return s;
    }


}

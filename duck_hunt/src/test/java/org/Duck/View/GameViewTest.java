package org.Duck.View;

import org.junit.jupiter.api.Test;

import static org.Duck.View.GameView.IncrementScore;
import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

    public class IncrementScoreTest {

        private int countScore = 0;

        @Test
        public void testIncrementScore() {
            // call the method to increment the score
            IncrementScore();

            // assert that the score has been incremented by 50
            assertEquals(50, countScore);
        }
    }

}
/* *****************************************
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
 * Class: Wordle.Main
 *
 * Description:
 *
 * ****************************************
 */

package Wordle;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

enum GAME_STATE {
    PLAYING, NOT_PLAYING
}

public class Main {

    //the main tree map to store words for words.txt
    private static Map<String, Integer> wordMap = new TreeMap<>();
    private static File f = new File("words.txt");
    /**
     * Main Dictionary of words to be scanned in
     */
    private static String mainDic = "https://www.gutenberg.org/cache/epub/29765/pg29765.txt";

    /**
     * An array of novel urls
     * 1. Heart of Darkness
     * 2. War and Peace
     * 3. The Great Gatsby
     * 4. Alice's Adventures in Wonderland
     * 5. The Adventures of sherlock Holmes
     */
    private static String[] urls = {
            "https://www.gutenberg.org/files/219/219-0.txt",
            "https://www.gutenberg.org/cache/epub/2600/pg2600.txt",
            "https://www.gutenberg.org/cache/epub/64317/pg64317.txt",
            "https://www.gutenberg.org/cache/epub/11/pg11.txt",
            "https://www.gutenberg.org/files/1661/1661-0.txt"
    };

    // Stores the amount of guesses so far
    public static int guessCount = 0;

    // Tracks the state of the game: playing or not
    public static GAME_STATE state = GAME_STATE.NOT_PLAYING;

    // Scanner object
    private static Scanner scnr = new Scanner(System.in);

    // Wordle.Answer of the current Wordle game
    private static Answer answer;

    // WordBank is hardcoded and should be the same for each game
    private static final WordBank bank = new WordBank();

    // A tree map of five-letter words from the dictionary
    private static Map<String, Integer>dicMap;

    static {
        try {
            dicMap = readDictionary(mainDic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        state = GAME_STATE.PLAYING;
        if(f.exists()) {
            System.out.println("The file words.txt exists!");
            System.out.println("Would you like to generate a new set of words?");
            String response = scnr.nextLine();
            if (response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES")) {
                createWordSet();
            }
        }
        else {
            System.out.println("The file words.txt was not found!\nGenerating a new set of words...");
            createWordSet();
        }

        //runs game
        checkGuess();

    }

    private static void createWordSet() throws IOException {

        //reads in novels and adds words to wordMap if valid
        readURLS(urls,wordMap,dicMap);

        //creating main list of words
        createFile(wordMap);
    }

    /**
     * Method which takes the randomly generated
     * answer and checks user guesses
     */
    private static void checkGuess() {
        answer = generateAnswer();
        do {
            String tmp;
            System.out.println("INPUT YOUR GUESS FOR THIS ROUND: ");
            do {
                tmp = scnr.nextLine();
                if (!tmp.matches("[a-zA-Z]+")) {
                    System.out.println("GUESS MUST ONLY CONTAIN NUMBERS. TRY AGAIN!");
                } else if (tmp.length() != 5) {
                    System.out.println("GUESS MUST BE 5 LETTERS LONG! TRY AGAIN!");
                } else if(!dicMap.containsKey(tmp)) {
                    System.out.println("WORD NOT FOUND! TRY AGAIN!");
                }

            } while (!tmp.matches("[a-zA-Z]+") || tmp.length() != 5 || !dicMap.containsKey(tmp));
            guessCount++;
            Guess g = new Guess(tmp, Main.answer);

            g.printGuessResult();
            if (g.isCorrect()) {
                System.out.println("YOU ARE CORRECT!!!");
                System.out.println("GUESSES: " + guessCount);
            } else {
                System.out.println("YOUR GUESS WAS INCORRECT.");
                if (6 - guessCount != 0) {
                    System.out.println("REMAINING GUESSES: " + (6 - guessCount));
                } else {
                    System.out.println("YOU LOSE!!!");
                }
            }

            if (guessCount == 6 || g.isCorrect()) {
                System.out.println("The correct word was: " + answer.getAnswer());
                System.out.println("WOULD YOU LIKE TO PLAY AGAIN?");
                String response = scnr.nextLine();
                guessCount = 0;
                if(!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("YES")) {
                    state = GAME_STATE.NOT_PLAYING;
                    System.out.println("THANKS FOR PLAYING!!!");
                }
                else{
                    answer = generateAnswer();
                }

            }

        } while(isPlaying());
    }


    private static boolean isPlaying() {return state == GAME_STATE.PLAYING;}

    /**
     * Counts line count of words.txt then generates a random number between zero and that count
     * in order to take a random word from the file of valid words for the wordle game
     *
     * @return
     */
    private static Answer generateAnswer() {
        Answer a = null;
        try {
            InputStream inputStream = new FileInputStream("words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Count the number of lines in the file
            int numLines = 0;
            while (reader.readLine() != null) {
                numLines++;
            }
            System.out.println("Selected a random word out of a list of " + numLines + " words.");
            reader.close();

            // Generate a random line number
            Random rand = new Random();
            int randomLineNum = rand.nextInt(numLines) + 1;

            // Read the line at the random line number
            InputStream inputstream2 = new FileInputStream("words.txt");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputstream2));
            for (int i = 1; i < randomLineNum; i++) {
                reader2.readLine();
            }
            String randomLine = reader2.readLine();
            a = new Answer(randomLine);

            // prints selected word for testing
//            System.out.println(randomLine);

            reader2.close(); // Remember to close the reader when you're done
        } catch (IOException e) {
            e.printStackTrace();
        }

        return a;
    }

    /**
     * scans the dictionary url and adds all valid five-letter words
     * to a tree map
     * @param mainDic
     * @return dicWords
     * @throws IOException
     */
    public static TreeMap<String, Integer> readDictionary(String mainDic) throws IOException {
        TreeMap<String, Integer> dicWords = new TreeMap<>();

        URL url = new URL(mainDic);
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Check if the line contains only alphabetical characters and has length 5
            if (line.matches("[a-zA-Z]+") && line.length() == 5) {
                String word = line.toLowerCase();
                dicWords.put(word, dicWords.getOrDefault(word, 0) + 1);
            }
        }

        return dicWords;}

    /**
     * returns a valid tree of words by comparing popular five-letter words from the novel
     * to those contained in the dictionary
     * @param urls
     * @param wordMap
     * @param dicMap
     * @return
     * @throws IOException
     */

    public static TreeMap<String, Integer> readURLS(String[] urls, Map<String, Integer> wordMap, Map<String, Integer> dicMap) throws IOException {
        for (String url : urls) {
            URL website = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] words = inputLine.split("[^a-zA-Z]+");
                for (String word : words) {
                    if (word.length() == 5 && word.matches("[a-zA-Z]+")) {
                        if (dicMap.containsKey(word)){
                            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                        }

                    }
                }
            }

            in.close();
        }
        return (TreeMap<String, Integer>) wordMap;
    }

    /**
     * uses the tree map of words to create the file words.txt
     * which is used for the game
     * @param wordMap
     */
    private static void createFile(Map<String, Integer> wordMap) {
        try {
            FileWriter writer = new FileWriter("words.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                String word = entry.getKey();

                bufferedWriter.write(word);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


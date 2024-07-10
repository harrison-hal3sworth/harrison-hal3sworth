/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth, Conor McNichols
 * Section: 02 - 10am
 * Date: 3/6/23
 * Time: 10:22 AM
 *
 * Project: csci205_hw
 * Package: PACKAGE_NAME
 * Class: Wordle.WordBank
 *
 * Description:
 *
 * ****************************************
 */

package Wordle;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class WordBank {
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


    public static void main(String[] args) throws IOException {
        //the main tree map to store words for words.txt
        Map<String, Integer> wordMap = new TreeMap<>();

        // A tree map of five-letter words from the dictionary
        Map<String, Integer>dicMap = readDictionary(mainDic);

        //reads in novels and adds words to wordMap if valid
        readURLS(urls,wordMap,dicMap);

        //creating main list of words
        createFile(wordMap);

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

        URL url = new URL(WordBank.mainDic);
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

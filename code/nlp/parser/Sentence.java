// Aidan Wu : Assignment 4

package nlp.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing a natural language sentence
 * 
 * @author DAidan Wu
 * @version 2/23/2023
 */

public class Sentence {
    private ArrayList<String> words;

    /**
     * Creates a new Sentence object from a natural language string
     * 
     * @param sent the natural language sentence to be split
     */
    public Sentence(String sent) {
        this.words = new ArrayList<String>(Arrays.asList(sent.split(" ")));
    }

    /**
     * @return ArrayList of words
     */
    public ArrayList<String> words() {
        return words;
    }

    /**
     * @return word at of i
     */
    public String getAt(int i) {
        return words.get(i);
    }

    /**
     * @return number of words in this sentence
     */
    public int len() {
        return words.size();
    }

    /**
     * Reads a file of natural language sentences into 
     * an ArrayList of Sentence objects
     * 
     * @param filename the file to read
     * @return an ArrayList of Sentence objects
     */
    public static ArrayList<Sentence> fromFile(String filename) {
        FileReader fr;

        try {
            fr = new FileReader(filename);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }

		BufferedReader rdr = new BufferedReader(fr);

        ArrayList<Sentence> lines = new ArrayList<>();
        String line;
        try {
            while ((line = rdr.readLine()) != null) {
                lines.add(new Sentence(line));
            }
            rdr.close();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

        return lines;
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MorseTranslator {
    private static final Map<String, String> morseAlphabet = new HashMap<>();
    private static List<String> dictionary = new ArrayList<>();

    static {
        morseAlphabet.put(".-", "A"); morseAlphabet.put("-...", "B"); morseAlphabet.put("-.-.", "C");
        morseAlphabet.put("-..", "D"); morseAlphabet.put(".", "E"); morseAlphabet.put("..-.", "F");
        morseAlphabet.put("--.", "G"); morseAlphabet.put("....", "H"); morseAlphabet.put("..", "I");
        morseAlphabet.put(".---", "J"); morseAlphabet.put("-.-", "K"); morseAlphabet.put(".-..", "L");
        morseAlphabet.put("--", "M"); morseAlphabet.put("-.", "N"); morseAlphabet.put("---", "O");
        morseAlphabet.put(".--.", "P"); morseAlphabet.put("--.-", "Q"); morseAlphabet.put(".-.", "R");
        morseAlphabet.put("...", "S"); morseAlphabet.put("-", "T"); morseAlphabet.put("..-", "U");
        morseAlphabet.put("...-", "V"); morseAlphabet.put(".--", "W"); morseAlphabet.put("-..-", "X");
        morseAlphabet.put("-.--", "Y"); morseAlphabet.put("--..", "Z");
    }

    public static void main(String[] args) throws IOException {
        dictionary = loadDictionary("src/main/java/org/example/dictionary.txt");

        String morseCode = ".......-...-.-.....-..-......-..--..-...--.--..-.....-....-.-.-.........";
        List<String> validPhrases = decodeMorse(morseCode);

        if (!validPhrases.isEmpty()) {
            System.out.println("Frases posibles:");
            validPhrases.forEach(System.out::println);
        } else {
            System.out.println("No se encontró una frase válida.");
        }
    }

    public static List<String> loadDictionary(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim().toUpperCase());
        }
        reader.close();
        return words;
    }

    public static List<String> decodeMorse(String morseCode) {
        Set<String> results = new LinkedHashSet<>();
        decodePhraseHelper(morseCode, "", "", results);
        return new ArrayList<>(results);
    }

    private static void decodePhraseHelper(String morseCode, String currentWord, String currentPhrase,
                                           Set<String> results) {
        if (morseCode.isEmpty()) {
            if (dictionary.contains(currentWord.toUpperCase())) {
                results.add((currentPhrase + currentWord).trim());
            }
            return;
        }

        for (int i = 1; i <= morseCode.length(); i++) {
            String morseChar = morseCode.substring(0, i);
            if (morseAlphabet.containsKey(morseChar)) {
                String letter = morseAlphabet.get(morseChar);
                String newWord = currentWord + letter;

                if (dictionary.contains(newWord.toUpperCase())) {
                    decodePhraseHelper(morseCode.substring(i), "", currentPhrase + newWord + " ", results);
                }

                if (isPrefixInDictionary(newWord.toUpperCase())) {
                    decodePhraseHelper(morseCode.substring(i), newWord, currentPhrase, results);
                }
            }
        }
    }

    private static boolean isPrefixInDictionary(String prefix) {
        for (String word : dictionary) {
            if (word.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}

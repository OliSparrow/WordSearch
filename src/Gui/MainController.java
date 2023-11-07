package Gui;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private static MainController instance;
    @FXML
    private TextField inputWord;
    @FXML
    private ListView<String> wordsList;
    @FXML
    private ListView<String> wordsStartingWithAList;
    @FXML
    private ListView<String> wordsStartingWithNList;
    @FXML
    private Label searchResultLabel;
    @FXML
    private Label wordCountLabel;
    @FXML
    private ListView<String> historyList;
    @FXML
    private Button clearHistory;
    private final String historyFileName = "search_history.txt";




    public MainController() {
        //Made to prevent outside instantiation.
    }

    public static MainController getInstance() {
        if(instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    @FXML
    public void initialize() {
        List<String> words = readWordsFromFile(getClass().getResource("/Gui/Words.txt").getPath());
        displayWords(words);
        updateWordCount();
        loadSearchHistory();
        displayWordsStartingWithA();
        displayWordsStartingWithN();
    }


    //Method for Search button:
    public void searchWord(){
        String wordToSearch = inputWord.getText().trim();
        boolean found = searchForWord(wordToSearch);

        if (wordToSearch.isEmpty()) {
            System.out.println("Please enter a word to search.");
            return;
        }

        if (found) {
            searchResultLabel.setText(wordToSearch + " was found!");
        } else {
            searchResultLabel.setText(wordToSearch + " was not found!");
        }

        boolean wordFound = false;
        for (String word : wordsList.getItems()) {
            if (word.equalsIgnoreCase(wordToSearch)) {
                wordFound = true;
                wordsList.scrollTo(word);
                wordsList.getSelectionModel().select(word);
                break;
            }
        }

        if (wordFound) {
            searchResultLabel.setText(wordToSearch + " was found!");
        } else {
            searchResultLabel.setText(wordToSearch + " was not found!");
        }

        updateSearchHistory(wordToSearch, found);
        saveSearchHistory();
    }

    //Code to read words from my Words.txt file.
    private List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //BufferedReader is a class used to read text from a character input stream.
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line); //Will add the words from the file to the list
            }
        } catch (IOException e) {
            e.printStackTrace(); //Prints out the stack trace in the case there is an Input/Output error.
        }
        return words; //Returns my list of words
    } //Method will catch and handle any exceptions that may be thrown within the try block, preventing an IOException.

    //This method creates an observable list which will display the words in the ListView
    private void displayWords(List<String> words) {
        ObservableList<String> observableWords = FXCollections.observableArrayList(words);
        wordsList.setItems(observableWords);
    }

    private void updateWordCount() {
        int count = wordsList.getItems().size();
        wordCountLabel.setText(count + " words");
    }


    private boolean searchForWord(String word) {
        return wordsList.getItems().contains(word);
    }

    private void updateSearchHistory(String word, boolean found) {
        String searchMessage = "Search for '" + word + "' and ";
        if (found) {
            searchMessage += "found results.";
        } else {
            searchMessage += "found no results";
        }

        historyList.getItems().add(searchMessage);
    }
    private void loadSearchHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(historyFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                historyList.getItems().add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSearchHistory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFileName))) {
            for (String history : historyList.getItems()) {
                bw.write(history + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearHistory() {
        historyList.getItems().clear();
        saveSearchHistory();
    }


    public void displayWordsStartingWithA() {
        List<String> wordsStartingWithA = new ArrayList<>();

        for (String word : wordsList.getItems()) {
            if (word.toUpperCase().startsWith("A")) {
                wordsStartingWithA.add(word);
            }
        }

        ObservableList<String> observableListStartingWithA = FXCollections.observableArrayList(wordsStartingWithA);
        wordsStartingWithAList.setItems(observableListStartingWithA);
    }

    public void displayWordsStartingWithN() {
        List<String> wordsStartingWithN = new ArrayList<>();

        for (String word : wordsList.getItems()) {
            if (word.toUpperCase().startsWith("N")) {
                wordsStartingWithN.add(word);
            }
        }

        ObservableList<String> observableListStartingWithA = FXCollections.observableArrayList(wordsStartingWithN);
        wordsStartingWithNList.setItems(observableListStartingWithA);
    }

}




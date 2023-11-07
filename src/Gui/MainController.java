package Gui;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TextField inputWord;
    @FXML
    private ListView<String> wordsList;
    @FXML
    private Label searchResultLabel;


    @FXML
    public void initialize() {
        List<String> words = readWordsFromFile(getClass().getResource("/Gui/Words.txt").getPath());
        displayWords(words);
    }


    //Method for Search button:
    public void searchWord(){
        String wordToSearch = inputWord.getText().trim();

        if (wordToSearch.isEmpty()) {
            System.out.println("Please enter a word to search.");
            return;
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



}




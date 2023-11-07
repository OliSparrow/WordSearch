package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private TextField inputWord;

    //Method for Search button:
    public void searchWord(){
        if(inputWord != null) {
            String wordToSearch = inputWord.getText().trim();
            //Retrieves the word from input
            System.out.println("Searched word: " + wordToSearch);
        } else {
            System.out.println("TextField is not initialized properly.");
        }
    }

}

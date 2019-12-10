package sample.Utils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;


public class FilePicker {
    private static FileChooser instance = null;
    public static SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();
    private static File recordsDir ;

    public FilePicker(String prevPath){
        recordsDir = new File(prevPath);
    }

    private static FileChooser getInstance(){
        if(instance == null) {
            instance = new FileChooser();
            instance.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
            //Set the FileExtensions you want to allo
            if (! recordsDir.exists()) {
                recordsDir = new File(System.getProperty("user.home"), "Desktop");
            }
            instance.setInitialDirectory(recordsDir);
            instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg",".jpeg",".ico",".bitmap"));
        }
        return instance;
    }

    public File showOpenDialog(){
        return showOpenDialog(null);
    }

    public static File showOpenDialog(Window ownerWindow){
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }

    public static File showSaveDialog(){
        return showSaveDialog(null);
    }

    public static File showSaveDialog(Window ownerWindow){
        getInstance().setTitle("Select Entity");
        File chosenFile = getInstance().showSaveDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }
}
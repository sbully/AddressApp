package ch.makery.adress.view;

import java.io.File;

import ch.makery.adress.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootLayoutController {

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);
	}

	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)","*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if(file != null) {
			mainApp.LoadPersonDataFromFile(file);
			
		}
		
	}

	@FXML
	private void handleSave() {

		File personFile= mainApp.getPersonFilePath();
		if(personFile != null) {
			mainApp.SavePersonDataFromFile(personFile);
		}else {
			handleSaveAs();
		}
	}
	
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)","*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if(file!=null) {
			if(!file.getPath().endsWith(".json")) {
				file = new File(file.getPath()+".json");
			}
		}
		mainApp.SavePersonDataFromFile(file);
		
		
	}
	
	@FXML
	private void handleAPropos() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Application Adresse");
		alert.setHeaderText("A Propos");
		alert.setContentText("Auteur : Seb\n20 Mars 2019\nFormation CDA");
		
		alert.showAndWait();
	}
	
	@FXML
	private void handleExit(){
		System.exit(0);
		
	}	
}

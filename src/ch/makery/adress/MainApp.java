package ch.makery.adress;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.*;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.makery.adress.model.Person;
import ch.makery.adress.view.PersonEditDialog;
import ch.makery.adress.view.PersonOverviewController;
import ch.makery.adress.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static ch.makery.adress.util.Json.fromJSON;
import static ch.makery.adress.util.Json.jsonWriteList;
import static ch.makery.adress.util.CompareVersion.compareVersions;

//import org.json.*;

public class MainApp extends Application {

	private static Stage primaryStage;
	private static BorderPane rootLayout;
	private static PersonOverviewController controller;

	private static ObservableList<Person> personData = FXCollections.observableArrayList();

	@Override
	public void start(Stage _primaryStage) {
		primaryStage = _primaryStage;
		
		
		
		
		
		primaryStage.setTitle("AddressApp");
		primaryStage.getIcons().add(new Image("file:resources/images/Address_Book_512px.png"));


		// personData.addListener((ListChangeListener<Person>)change -> refreshList());

		personData.addListener(new ListChangeListener<Person>() {
			@Override
			public void onChanged(Change<? extends Person> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						//System.out.println("Changement detecté");
					}
				}
			}
		});

		initRootLayout();

		showPersonOverview();
	}

	/**
	 * Initialises rootlayout.
	 */
	public void initRootLayout() {
		try {
			// chargement du Layout conteneur.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Affichage de la scene Layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();

			
			
		}

		File file = getPersonFilePath();
		if (file != null) {
			LoadPersonDataFromFile(file);
		}

	}

	/**
	 * Affichage PersonOverview dans le RootLayout.
	 */
	public void showPersonOverview() {
		try {
			// Chargement de PersonOverview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// fixe au centre du Layout conteneur la view PersonOverview
			rootLayout.setCenter(personOverview);

			// PersonOverviewController controller = loader.getController();
			controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourn le Stage primaire.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/*
	 * Retourne l ObservableList
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	/*
	 * Ppint d'entré du programme
	 */
	public static void main(String[] args) {

		/*
		 * personData.add(new Person("Hans", "Muster")); personData.add(new
		 * Person("Ruth", "Mueller")); personData.add(new Person("Heinz", "Kurz"));
		 * personData.add(new Person("Cornelia", "Meier")); personData.add(new
		 * Person("Werner", "Meyer")); personData.add(new Person("Lydia", "Kunz"));
		 * personData.add(new Person("Anna", "Best")); personData.add(new
		 * Person("Stefan", "Meier")); personData.add(new Person("Martin", "Mueller"));
		 */
		
		String JavaVersionMin = "10.0.1";
		
		String JavaActu = System.getProperty("java.version");
		//System.out.println(System.getProperty("java.version"));
		
		
		if(compareVersions(JavaVersionMin,JavaActu)!=0){
			
			JOptionPane.showMessageDialog(null, "Votre version de java est obsolète\n"
					+ "veuillez mettre à jour :\n"
					+ "https://www.oracle.com/technetwork/java/javase/downloads/index.html"
					+ "","Erreur : compatibilité java",JOptionPane.ERROR_MESSAGE);
			
			System.exit(0);
		}
		else {
			launch(args);
		}
		

		
		
		}

	/*
	 * Ouverture d'une fenetre"POP UP" pour editer les informations d'une personne
	 * SI l'utilisateur clicl sur OK return TRUE, si non retourne FALSE
	 * 
	 * @param person la personne à éditer
	 * 
	 * @return true si click sur OK , sinon FALSE
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edition");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			PersonEditDialog controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * Retourne le dossier preference de l'utilisateur. ou le dernier dossier ouvert
	 * retourn null si aucune preference
	 */
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public static void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			primaryStage.setTitle("AddressApp");
		}
	}

	public static void LoadPersonDataFromFile(File file) {

		TypeReference<List<Person>> type = new TypeReference<List<Person>>() {
		};
		try {
			personData.clear();
			personData.addAll(FXCollections.observableList(fromJSON(file, type)));
			System.out.println(personData);
			setPersonFilePath(file);
			// PersonOverviewController ob = new PersonOverviewController();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void SavePersonDataFromFile(File file)
	public static void SavePersonDataFromFile(File file) {

		List<Person> personList = personData.stream().collect(Collectors.toList());
		try {
			jsonWriteList(personList, file);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*
	 * public static void refreshList() {
	 * 
	 * controller.Refreshlist(); }
	 */

}
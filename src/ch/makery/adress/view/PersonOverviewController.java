package ch.makery.adress.view;

import javafx.fxml.FXML;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.Desktop;
import java.net.URI;

import ch.makery.adress.MainApp;
import ch.makery.adress.model.Person;
import ch.makery.adress.util.DateUtil;

public class PersonOverviewController {

	@FXML
	private TableView<Person> personTable;

	@FXML
	private TableColumn<Person, String> firstNameColumn;

	@FXML
	private TableColumn<Person, String> lastNameColumn;

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;

	@FXML
	private Label streetLabel;

	@FXML
	private Label postalCodeLabel;

	@FXML
	private Label cityLabel;

	@FXML
	private Label birthdayLabel;

	@FXML
	private Hyperlink eMailLabel;

	// réference vers l'application main
	private MainApp mainApp;

	/*
	 * Constructeur Appelé avant la methode initialize()
	 */
	public PersonOverviewController() {

	}

	/*
	 * Initialisation de la classe controller fonction appelé automatiquement après
	 * que le fichier FXML ai été chargé
	 */

	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(celleData -> celleData.getValue().lastNameProperty());

		showPersonDetails(null);

		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		personTable.setItems(mainApp.getPersonData());

	}

	/*
	 * Affichage des details sur la person selectionné si la personne spécifié est
	 * "null" tout les labels sont vide
	 * 
	 * @person la personne sélectionné ou null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
			eMailLabel.setText(person.getEMail());

		} else {
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
			eMailLabel.setText("");
		}

	}

	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Aucune Selection");
			alert.setHeaderText("Aucune Personne Sélectionnée");
			alert.setContentText("SVP veuillez sélectionné une personne dans la table");
			alert.showAndWait();
		}

	}

	/*
	 * Appeler quand l'utilisateur clique sur le boutton NEW ouverture d'une boite
	 * de dialog pour ajouter une nouvelle person
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerso = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerso);
		if (okClicked) {
			mainApp.getPersonData().add(tempPerso);
		}

	}

	/*
	 * Appeler quand l'utilisateur clique sur le boutton EDIT ouverture d'une boite
	 * de dialog pour edité les informations de la perosnnes selectionnée
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table to edit");

			alert.showAndWait();
		}

	}

	@FXML
	private void handleSendMail() {
		Person p = personTable.getSelectionModel().getSelectedItem();
		String send = "mailto:" + p.getEMail() + "?subject=contact";
		URI mailtosend = null;
		try {
			mailtosend = new URI(send);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Desktop desktop;
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			try {

				desktop.mail(mailtosend);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

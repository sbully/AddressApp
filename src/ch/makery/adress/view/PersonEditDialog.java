package ch.makery.adress.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import ch.makery.adress.model.Person;
import ch.makery.adress.util.DateUtil;

public class PersonEditDialog {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;
	@FXML
	private TextField eMailField;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	@FXML
	private void initialise() {

	}

	/*
	 * Affichage de la person à éditer
	 * 
	 * @param person : person selectionné dans la TableView
	 */
	public void setPerson(Person _person) {
		this.person = _person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		cityField.setText(person.getCity());
		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("dd.mm.yyyy");
		eMailField.setText(person.getEMail());

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage=dialogStage;
	}
	/*
	 * Retourne TRUE si le bouton OK a été cliquer, FALSE sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/*
	 * Appel quand l'utilisateur clic sur le bouton OK
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setCity(cityField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setEMail(eMailField.getText());
			
			okClicked = true;
			dialogStage.close();
		}
	}
	/*
	 * 	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setCity(cityField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));

			okClicked = true;
			dialogStage.close();
		}
	}
	 */

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name\n";

		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "No valid street\n";
		}
		if (cityField.getText() == null || cityField.getText().length() == 0) {

		}
		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "No Valid postal code\n";
		} else {
			try {
				Integer.parseInt(postalCodeField.getText());

			} catch (Exception e) {
				errorMessage += " No valid postal code. Must be an Integer\n";
			}
		}
		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "No Valid birthday!\n";
		} else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "No valid birthday. Use format dd.mm.yyyy \n";
			}
		}
		if (eMailField.getText() == null || eMailField.getText().length() == 0) {
			errorMessage += "No valid eMail \n";

		}
		
		
		if(errorMessage.length()==0) {
			return true;
		}
		else {
			//Affichage de errorMessage
			JOptionPane.showMessageDialog(null,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	

	}
}

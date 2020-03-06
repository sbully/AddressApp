package ch.makery.adress.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.IntegerProperty;*/

import javafx.beans.property.*;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty street;
	private final IntegerProperty postalCode;
	private final StringProperty city;
	private final ObjectProperty<LocalDate> birthday;
	private final StringProperty eMail;
	
	
	public Person() {
		this(null,null);
	}

	
	
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		
		this.street = new SimpleStringProperty("Rue du paumé solitaire ");
        this.postalCode = new SimpleIntegerProperty(666);
        this.city = new SimpleStringProperty("Satan City");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.eMail = new SimpleStringProperty("toto@gmail.com");
	}


	public Person(String firstName, String lastName, String street, Integer postalCode,
			String city,LocalDate birthday, String eMail) {
		this.firstName =new SimpleStringProperty( firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.street = new SimpleStringProperty(street);
		this.postalCode = new SimpleIntegerProperty(postalCode);
		this.city = new SimpleStringProperty(city);
		this.birthday =  new SimpleObjectProperty<LocalDate>(birthday);
		this.eMail = new SimpleStringProperty(eMail);
	}





	public String getFirstName() {
		return firstName.get();
	}
	
	public void setFirstName(String _name)
	{
		this.firstName.set(_name);
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String _lastname)
	{
	this.lastName.set(_lastname);	
	}

	public StringProperty lastNameProperty()
	{
		return lastName;
	}
	
	public StringProperty getStreetProperty() {
		return street;
	}
	
	public void setStreet(String _street) {
		this.street.set(_street);
	}

	public String getStreet() {
		return street.get();
	}

	public IntegerProperty getPostalCodeProperty() {
		return postalCode;
	}
	
	public void setPostalCode( int _codepostal)
	{
		this.postalCode.set(_codepostal);
	}
	public int getPostalCode()
	{
		return postalCode.get();
	}

	public StringProperty getCityProperty() {
		return city;
	}
	
	public void setCity(String _city) {
		this.city.set(_city);
	}

	public String getCity() {
		return city.get();
	}

	public ObjectProperty<LocalDate> getBirthdayProperty() {
		return birthday;
	}
	
	public void setBirthday(LocalDate _birthday) {
		this.birthday.set(_birthday);
	}
	
    public LocalDate getBirthday() {
        return birthday.get();
    }

	public String getEMail() {
		return eMail.get();
	}
	
	public void setEMail(String _mail)
	{
		this.eMail.set(_mail);
	}
	
	public StringProperty EMailProperty() {
		return eMail;
	}

	

}

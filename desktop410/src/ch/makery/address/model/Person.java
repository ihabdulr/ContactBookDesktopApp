package ch.makery.address.model;





import javafx.beans.property.IntegerProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Model class for a Person.
 *
 * @author Iman Abdul
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final StringProperty postalCode;
    private final StringProperty city;
    private final StringProperty birthday;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final StringProperty url;
    private final StringProperty imagePath;
    
   

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("");
        this.postalCode = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.birthday = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.url = new SimpleStringProperty("");
        this.imagePath = new SimpleStringProperty("resources/images/default.png");
        

      
    }
    
    
  
   
    
   public String getImagePath() {
	   return imagePath.get();
   }
   
   public void setImagePath(String imagePath) {
	   this.imagePath.set(imagePath);
   }
   
   public StringProperty imagePathProperty() {
	   return imagePath;
   }
    
 
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

 
    public String getBirthday() {
        return birthday.get();
    }
    
   

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }
    
    public String getPhoneNumber() {
    		return phoneNumber.get();
    }
    
    public void setPhoneNumber(String phoneNumber) {
    		this.phoneNumber.set(phoneNumber);
    }
    
    public StringProperty phoneNumberProperty() {
    		return phoneNumber;
    }

    public StringProperty emailProperty() {
    		return email;
    }
    
    public void setEmail(String email) {
    		this.email.set(email);
    }
	public String getEmail() {
		
		return email.get();
	}
	
	public String getUrl() {
		return url.get();
	}
	
	public void setUrl(String url) {
		this.url.set(url);
	}
	
	public StringProperty urlProperty() {
		return url;
	}
	
	
}
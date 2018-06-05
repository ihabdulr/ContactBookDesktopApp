package ch.makery.address.view;

import java.io.File;
import java.net.MalformedURLException;

import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PersonEditDialogController {

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
	private TextField phoneNumberField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField urlField;
	

	
	
	
	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;
	
	/*
	 * Initializes the controller class. This method is automatically called after the fxml file is loaded
	 * 
	 */
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	//Sets the person to be edited in the dialog
	 public void setPerson(Person person) {
	        this.person = person;

	        firstNameField.setText(person.getFirstName());
	        firstNameField.setPromptText("What's your first name?");
	        lastNameField.setText(person.getLastName());
	        lastNameField.setPromptText("What's your last name?");
	        streetField.setText(person.getStreet());
	        streetField.setPromptText("What street do you live on?");
	        postalCodeField.setText(person.getPostalCode());
	        postalCodeField.setPromptText("What's your postal code?");
	        cityField.setText(person.getCity());
	        cityField.setPromptText("Where are you located?");
	        birthdayField.setText(person.getBirthday());
	        birthdayField.setPromptText("MM/DD/YYYY");
	        phoneNumberField.setText(person.getPhoneNumber());
	        phoneNumberField.setPromptText("(123) 456-7890");
	        emailField.setText(person.getEmail());
	        emailField.setPromptText("YourEmailHere@somewhere");
	        urlField.setText(person.getUrl());
	        urlField.setPromptText("Link your website here!");
	        
	    }

	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     * 
	     * @return
	     */
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {
	        if (isInputValid()) {
	            person.setFirstName(firstNameField.getText());
	            person.setLastName(lastNameField.getText());
	            person.setStreet(streetField.getText());
	            person.setPostalCode(postalCodeField.getText());
	            person.setCity(cityField.getText());
	            person.setBirthday(birthdayField.getText());
	            String phoneNumber = phoneNumberField.getText().replaceAll(" ", "");
	            System.out.println(phoneNumber);
	            
	            if(!phoneNumberField.getText().isEmpty()) {
	            String normalized = "";
	            boolean plus = false;
	            for (int i=phoneNumber.length()-1; i>=0; --i) {
	            		if((int) phoneNumber.charAt(i) >= 47 && (int) phoneNumber.charAt(i) <= 57 ) {
	            			normalized = phoneNumber.charAt(i) + normalized;
	            		}     		
	            }
	            if (phoneNumber.startsWith("+")) 
	            	plus = true;
	            String normal2 = "";
	            int start = 0;
	            for (int i=0; i< normalized.length(); i++) {
	            	
	            	if (normalized.substring(i, normalized.length()).length() == 10) {
	            		start = i;
	            	}
	            	
	            }
	            
	            normal2 =  normalized.substring(0, start) + " (" + normalized.substring(start,start+3) + ") " + normalized.substring(start+3,start+6) + "-" + normalized.substring(start+6);
        			if (plus)
        				normal2 = "+" +normal2;
        			if (!normal2.startsWith("+")) {
        				normal2 = normal2.substring(1);
        			}
        			person.setPhoneNumber(normal2);
	            }
	            
	            person.setEmail(emailField.getText());
	            person.setUrl(urlField.getText());
	            okClicked = true;
	            dialogStage.close();
	        }
	    }
	    
	    



	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }

	    /**
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     */
	    private boolean isInputValid() {
	        String errorMessage = "";

	        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
	            errorMessage += "No valid first name!\n"; 
	        }
	        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
	            errorMessage += "No valid last name!\n"; 
	        }
	      /*  if (streetField.getText() == null || streetField.getText().length() == 0) {
	            errorMessage += "No valid street!\n"; 
	        } 

	        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
	            errorMessage += "No valid postal code!\n"; 
	        } */if(postalCodeField.getText().length() >0 ){
	           
	            try {
	                Integer.parseInt(postalCodeField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "No valid postal code (must be an integer)!\n"; 
	            }
	        }

	      /*  if (cityField.getText() == null || cityField.getText().length() == 0) {
	            errorMessage += "No valid city!\n"; 
	        } */
	        
	        /*
	        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
	            errorMessage += "No valid birthday!\n";
	        } */
	        	if(birthdayField.getText().length() > 0 && !(birthdayField.getText().matches("[0-9][0-9][//|.|-|,|_][0-9][0-9][//|.|-|,|_][0-9][0-9][0-9][0-9]"))) {
	        			 errorMessage += "No valid birthday. Use the format mm*dd*yyyy!\n";
	 	                errorMessage += "Legal date formats are as follows:\n";
	 	                errorMessage += "mm.dd.yyyy\n"; 
	 	                errorMessage += "mm//dd//yyyy\n";
	 	                errorMessage += "mm-dd-yyyy\n";
	 	                errorMessage += "mm,dd,yyyy\n";
	 	                errorMessage += "mm_dd_yyyy\n";
	        
	        }
	       
	        
	       /* if(phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
	        		errorMessage += "No valid phone number!\n";
	        }else { */
	        		if (phoneNumberField.getText().length() > 0 && !phoneNumberField.getText().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$") && !((phoneNumberField.getText().length()==10) && phoneNumberField.getText().matches("[0-9]{10}"))) {
	        			
	        				errorMessage += "Not a valid phone number!\n";
	        				errorMessage += "Possible input formats:\n";
	        				errorMessage += "1234567890\n";
	        				errorMessage += "123-456-7890\n";
	        				errorMessage += "(123) 456-7890\n";
	        				errorMessage += "123 456 7890\n";
	        				errorMessage += "123.456.7890\n";
	        				errorMessage += "+91 (123) 456-7890\n";
	        				
	        		}
	        		
	        		

	       // }
	        
	        if (emailField.getText().length() != 0 && !emailField.getText().matches("[[a-z]|[._-]]*@[[a-z]|[.]]*")) {
	        		errorMessage += "Enter a valid email address!";
	        }
	        
	        if(urlField.getText().length() > 0 && !urlField.getText().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
	        		errorMessage += "Not a valid URL\n";
	        		errorMessage += 	"URL must include https:// or http://\n";
	        } 
	        		
	       
	       

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	    }
	
	
}

package ch.makery.address.view;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;



public class PersonOverviewController {
	
	/*
	 * Any method inside our controller that is annotated with @FXML (or is public) is accessible by the Scene Builder. 
	 */
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
	    Person tempPerson = new Person();
	    boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
	    if (okClicked) {
	        mainApp.getPersonData().add(tempPerson);
	    }
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
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
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");

	        alert.showAndWait();
	    }
	}
	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
	    int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	        personTable.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Person Selected");
	        alert.setContentText("Please select a person in the table.");

	        alert.showAndWait();
	    }
	}
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
    private Label phoneNumberLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML 
    private Label urlLabel;
    
    ArrayList<String> cheesy = new ArrayList<String>();

   
    	@FXML
    	private ImageView imageView;
    	
    	private Person currentPerson;
    	
  
    	
    	
    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }
    
	@FXML
	public void contactPhotoChooser(){
		personTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> showPersonDetails(newValue));  
		
	    FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = chooser.showOpenDialog(new Stage());
 	    chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                  "*.bmp", "*.png", "*.jpg", "*.gif")); // limit chooser options to image files

	    if(file != null) {
	        String imagepath = null;
			try {
				imagepath = file.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!imagepath.substring(imagepath.length()-4).matches(".bmp|.png|.jpg|.gif")) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Information Dialog\n");
		        alert.setHeaderText("Invalid Image Formats will not render!\n");
		        alert.setContentText("ONLY .bmp, .png, .jpg, .gif files are allowed");
		        alert.showAndWait();
			}
			try {
			currentPerson.setImagePath(imagepath);
	        Image image = new Image(imagepath);
	        imageView.setImage(image);
			}
			catch(NullPointerException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Information Dialog\n");
		        alert.setHeaderText("Person must be created before uploading a photo!\n");
		        alert.showAndWait();
			}
	        
	    }
	    else
	    {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information Dialog");
	        alert.setHeaderText("Please Select a File");
	        alert.setContentText("You didn't select a file!");
	        alert.showAndWait();
	    }
	}
    
    @FXML
    public void handleUrlClick() {
    		try {
    			Desktop d = Desktop.getDesktop();
    			d.browse(new URI(urlLabel.getText()));
    		}
    		catch (IOException e) {
    			Alert invalidUrlAlert = new Alert(Alert.AlertType.ERROR);
    			 invalidUrlAlert.setTitle("AddressApp");
    		      invalidUrlAlert.setHeaderText("Invalid Url Error");
    		        invalidUrlAlert.setContentText("URL must include https:// or http://");
    		        invalidUrlAlert.showAndWait();
    		} catch (URISyntaxException e) {
    			Alert invalidUrlAlert = new Alert(Alert.AlertType.ERROR);
   			 invalidUrlAlert.setTitle("AddressApp");
   		      invalidUrlAlert.setHeaderText("Invalid Url Error");
   		        invalidUrlAlert.setContentText("URL must include https:// or http://");
   		        invalidUrlAlert.showAndWait();
    		}
    }
    
    @FXML
    private void handleExport() throws IOException {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "VCF files (*.vcf)", "*.vcf");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".vcf")) {
                file = new File(file.getPath() + ".vcf");
            }
            savePersonDataToFile(file , currentPerson);
        }
    }
    
    @FXML
 
    private void vcfDragOver(DragEvent event) throws FileNotFoundException {
      Dragboard board = event.getDragboard();

      if (board.hasFiles()) {

        List<File> phil = board.getFiles();
        String path = phil
            .get(0)
            .toPath()
            .toString();

        if (path.endsWith(".vcf")) {
          event
          .acceptTransferModes(
              TransferMode.ANY);
          Person importPerson = new Person();
          
          Scanner sc = new Scanner(phil.get(0));
          String fetch = "";
          while(sc.hasNextLine()) {
        	  	fetch = sc.nextLine();
        	  	if (fetch.startsWith("FN:")) {
        	  		String temp = fetch.substring(3);
        	  		int IndexOfSpace = temp.indexOf(" ");
        	  		importPerson.setFirstName(temp.substring(0,IndexOfSpace));
        	  		importPerson.setLastName(temp.substring(IndexOfSpace+1));
        	  
        	  	}	
        	  	if (fetch.startsWith("EMAIL:")) {
        	  		importPerson.setEmail(fetch.substring(6));
        	  	}	
        	  	if (fetch.startsWith("URL:")) {
        	  		importPerson.setUrl(fetch.substring(4));
        	  	}
        	  	if (fetch.startsWith("BDAY:")) {
        	  		String bday = fetch.substring(5);
        	  		String modifiedBday = bday.substring(4,6)+ "/" + bday.substring(6) + "/" + bday.substring(0,4);
        	  		importPerson.setBirthday(modifiedBday);
        	  	}
        	  	if (fetch.startsWith("TEL:")) {  		
        	  		importPerson.setPhoneNumber(fetch.substring(4));
        	  	}
        	  	if (fetch.equals("END:VCARD")){
        	  		break;
        	  	}
        	  	if (fetch.startsWith("ADR:")) {
        	  		String street = fetch.substring(4);
        	  		
        	  		String[] splited = street.split(";");
        	  		if (splited.length >= 1)
        	  		importPerson.setStreet(splited[0]);
        	  		if (splited.length >= 2)
        	  		importPerson.setPostalCode(splited[1]);
        	  		if (splited.length >= 3)
        	  		importPerson.setCity(splited[2]);
        	  		
        	  		
        	  	}
          }
          sc.close();
          
          
          	if(!cheesy.contains(importPerson.getFirstName()+importPerson.getLastName()))
          		personTable.getItems().add(importPerson);
  				cheesy.add(importPerson.getFirstName()+importPerson.getLastName());
  			
          
        } 
  
      }
    }
    
    private void savePersonDataToFile(File file, Person person) throws IOException {
    		String str = "";
    		Boolean isPerson = false;
    	if (person !=null) {
    		String birthday = person.getBirthday().replaceAll("[//|.|-|,|_]*", "-");
    		String modifiedBirthday = person.getBirthday().substring(6) + person.getBirthday().substring(0, 2) + person.getBirthday().substring(3,5);
    	  str="BEGIN:VCARD\n" + 
    			     "VERSION:4.0\n" +
    			     "N:" + person.getLastName() + ";" + person.getFirstName() + ";;\n" + 
    			     "FN:" + person.getFirstName() + " " + person.getLastName()+ "\n" +
    			     "ADR:"+ person.getStreet() + ";" +  person.getPostalCode() + ";" + person.getCity() + ";" + "\n" +   
    			     "TEL:"+person.getPhoneNumber() + "\n"+
    			     "EMAIL:" + person.getEmail() + "\n" +
    			     "URL:" + person.getUrl()+ "\n" +
    			     "BDAY:" + modifiedBirthday  + "\n" +
    			     "END:VCARD";
    	  
    	  isPerson = true;
    	  System.out.println(modifiedBirthday);
    	  System.out.println(person.getBirthday());
    	}
    	else {
    		  Alert alert = new Alert(Alert.AlertType.ERROR);
  	        alert.setTitle("Information Dialog\n");
  	        alert.setHeaderText("Oops! Choose someone to export!");
  	        alert.showAndWait();
      	    
    	}

    	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    	    writer.write(str);
    	    if (isPerson) {
    	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information Dialog\n");
	        alert.setHeaderText(".VCF File Created Successfully!\n");
	        alert.showAndWait();
    	    }
    	    
    	    writer.close();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    public void setCurrentPerson(Person person) {
    		currentPerson = person;
    		
    }
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        //clear Person Details
        showPersonDetails(null);
        
        //Listen for changes in person details
        /*
         * we get the selectedItemProperty of the person table and add a listener to it. 
         * Whenever the user selects a person in the table, our lambda expression is executed. 
         * We take the newly selected person and pass it to the showPersonDetails(...) method.
         */
        personTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> showPersonDetails(newValue));
        personTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> setCurrentPerson(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    
    
    
    private void showPersonDetails(Person person) {
        if (person != null) {
       
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(person.getPostalCode());
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(person.getBirthday());     
            phoneNumberLabel.setText(person.getPhoneNumber());
            emailLabel.setText(person.getEmail());
            urlLabel.setText(person.getUrl());
            Image image = new Image(person.getImagePath());
            imageView.setImage(image);
            
         
          
        
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
            phoneNumberLabel.setText("");
            emailLabel.setText("");
            urlLabel.setText("");
            imageView.setImage(null);
            
        }
    }
}
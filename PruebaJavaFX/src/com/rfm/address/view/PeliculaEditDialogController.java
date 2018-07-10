package com.rfm.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.rfm.address.model.Pelicula;

/**
 * Dialog to edit details of a pelicula.
 * 
 * @author Marco Jakob
 */
public class PeliculaEditDialogController {

    public ObservableList<Pelicula> dataPelicula = FXCollections.observableArrayList();

    @FXML
    private TextField tituloField;
    @FXML
    private TextField directorField;
    @FXML
    private TextField generoField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField soporteField;

    private Stage dialogStage;
    private Pelicula pelicula;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
	this.dialogStage = dialogStage;
    }

    /**
     * Sets the pelicula to be edited in the dialog.
     * 
     * @param pelicula
     */
    public void setPelicula(Pelicula pelicula) {
	this.pelicula = pelicula;

	tituloField.setText(pelicula.getTitulo());
	directorField.setText(pelicula.getDirector());
	generoField.setText(pelicula.getGenero());
	yearField.setText(Integer.toString(pelicula.getYear()));
	soporteField.setText(pelicula.getSoporte());
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
	    pelicula.setTitulo(tituloField.getText());
	    pelicula.setDirector(directorField.getText());
	    pelicula.setGenero(generoField.getText());
	    pelicula.setYear(Integer.parseInt(yearField.getText()));
	    pelicula.setSoporte(soporteField.getText());

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

	if (tituloField.getText() == null || tituloField.getText().length() == 0) {
	    errorMessage += "Titulo no valido!\n";
	}
	if (directorField.getText() == null || directorField.getText().length() == 0) {
	    errorMessage += "Director no valido!\n";
	}
	if (generoField.getText() == null || generoField.getText().length() == 0) {
	    errorMessage += "Genero no valido!\n";
	}

	if (yearField.getText() == null || yearField.getText().length() == 0) {
	    errorMessage += "Year no valido!\n";
	} else {
	    // try to parse the postal code into an int.
	    try {
		Integer.parseInt(yearField.getText());
	    } catch (NumberFormatException e) {
		errorMessage += "Year no valido (must be an integer)!\n";
	    }
	}

	if (soporteField.getText() == null || soporteField.getText().length() == 0) {
	    errorMessage += "Soporte no valido!\n";
	}

	if (generoField.getText() == null || generoField.getText().length() == 0) {
	    errorMessage += "Genero no valido!\n";
	}

	if (errorMessage.length() == 0) {
	    return true;
	} else {
	    // Show the error message.
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Campos incorrectos");
	    alert.setHeaderText("Vaya, parece que ha habido un error!");
	    alert.setContentText("Por favor, rellena correctamente todos los campos.");

	    alert.showAndWait();
	    return false;
	}
    }

}

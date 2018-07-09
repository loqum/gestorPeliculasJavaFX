package com.rfm.address.view;

import com.rfm.address.MainApp;
import com.rfm.address.model.Pelicula;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PeliculaOverviewController {
    @FXML
    private TableView<Pelicula> peliculaTable;
    @FXML
    private TableColumn<Pelicula, String> tituloColumn;
    @FXML
    private TableColumn<Pelicula, String> directorColumn;

    @FXML
    private Label tituloLabel;
    @FXML
    private Label directorLabel;
    @FXML
    private Label generoLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label soporteLabel;


    // Reference to the main application.
    @SuppressWarnings("unused")
    private MainApp mainApp;

    /**
     * The constructor. The constructor is called before the initialize() method.
     */
    public PeliculaOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	// Initialize the person table with the two columns.
	tituloColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
	directorColumn.setCellValueFactory(cellData -> cellData.getValue().directorProperty());

	// Clear person details.
	showPeliculaDetails(null);

	// Listen for selection changes and show the person details when changed.
	peliculaTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> showPeliculaDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
	this.mainApp = mainApp;

	// Add observable list data to the table
	peliculaTable.setItems(mainApp.getPeliculaData());
    }

    /**
     * Fills all text fields to show details about the person. If the specified
     * person is null, all text fields are cleared.
     * 
     * @param pelicula
     *            the person or null
     */
    private void showPeliculaDetails(Pelicula pelicula) {
	if (pelicula != null) {
	    // Fill the labels with info from the person object.
	    tituloLabel.setText(pelicula.getTitulo());
	    directorLabel.setText(pelicula.getDirector());
	    generoLabel.setText(pelicula.getGenero());
	    yearLabel.setText(Integer.toString(pelicula.getYear()));
	    soporteLabel.setText(pelicula.getSoporte());

	} else {
	    // Person is null, remove all the text.
	    tituloLabel.setText("");
	    directorLabel.setText("");
	    generoLabel.setText("");
	    yearLabel.setText("");
	    soporteLabel.setText("");
	}
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePelicula() {
	int selectedIndex = peliculaTable.getSelectionModel().getSelectedIndex();
	if (selectedIndex >= 0) {
	    peliculaTable.getItems().remove(selectedIndex);
	} else {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("No selection");
	    alert.setHeaderText("No Person Selected");
	    alert.setContentText("Please select a person in the table.");

	    alert.showAndWait();

	}
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit details
     * for a new person.
     */
    @FXML
    private void handleNewPelicula() {
	Pelicula tempPelicula = new Pelicula();
	boolean okClicked = mainApp.showPeliculaEditDialog(tempPelicula);
	if (okClicked) {
	    mainApp.getPeliculaData().add(tempPelicula);
	}
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit details
     * for the selected person.
     */
    @FXML
    private void handleEditPelicula() {
	Pelicula selectedPelicula = peliculaTable.getSelectionModel().getSelectedItem();
	if (selectedPelicula != null) {
	    boolean okClicked = mainApp.showPeliculaEditDialog(selectedPelicula);
	    if (okClicked) {
		showPeliculaDetails(selectedPelicula);
	    }

	} else {
	    // Nothing selected.
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("No selection");
	    alert.setHeaderText("No Person Selected");
	    alert.setContentText("Please select a person in the table.");

	    alert.showAndWait();
	}
    }
}
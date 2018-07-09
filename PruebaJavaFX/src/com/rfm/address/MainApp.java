package com.rfm.address;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rfm.address.model.Pelicula;
import com.rfm.address.model.PeliculaListWrapper;
import com.rfm.address.view.PeliculaEditDialogController;
import com.rfm.address.view.PeliculaOverviewController;
import com.rfm.address.view.RootLayoutController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of peliculas.
     */
    private ObservableList<Pelicula> peliculaData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
	// Add some sample data
	
    }

    /**
     * Returns the data as an observable list of peliculas.
     * 
     * @return
     */
    public ObservableList<Pelicula> getPeliculaData() {
	return peliculaData;
    }

    // ... THE REST OF THE CLASS ...

    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
	this.primaryStage.setTitle("MoviesDataBase");

	this.primaryStage.getIcons()
		.add(new Image("file:resources/images/if_icontexto-aurora-folders-movies_27642.png"));

	initRootLayout();

	showPeliculaOverview();
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * pelicula file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened pelicula file.
        File file = getPeliculaFilePath();
        if (file != null) {
            loadPeliculaDataFromFile(file);
        }
    }

    /**
     * Shows the pelicula overview inside the root layout.
     */
    public void showPeliculaOverview() {
	try {
	    // Load pelicula overview.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("view/PeliculaOverview.fxml"));
	    AnchorPane peliculaOverview = (AnchorPane) loader.load();

	    // Set pelicula overview into the center of root layout.
	    rootLayout.setCenter(peliculaOverview);

	    // Give the controller access to the main app.
	    PeliculaOverviewController controller = loader.getController();
	    controller.setMainApp(this);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Opens a dialog to edit details for the specified pelicula. If the user clicks
     * OK, the changes are saved into the provided pelicula object and true is
     * returned.
     * 
     * @param pelicula
     *            the  object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPeliculaEditDialog(Pelicula pelicula) {
	try {
	    // Load the fxml file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("view/PeliculaEditDialog.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();

	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.setTitle("Edit Pelicula");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);

	    // Set the pelicula into the controller.
	    PeliculaEditDialogController controller = loader.getController();
	    controller.setDialogStage(dialogStage);
	    controller.setPelicula(pelicula);

	    // Show the dialog and wait until the user closes it
	    dialogStage.showAndWait();

	    return controller.isOkClicked();
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * Returns the pelicula file preference, i.e. the file that was last opened. The
     * preference is read from the OS specific registry. If no such preference can
     * be found, null is returned.
     * 
     * @return
     */
    public File getPeliculaFilePath() {
	Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	String filePath = prefs.get("filePath", null);
	if (filePath != null) {
	    return new File(filePath);
	} else {
	    return null;
	}
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in the
     * OS specific registry.
     * 
     * @param file
     *            the file or null to remove the path
     */
    public void setPeliculaFilePath(File file) {
	Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	if (file != null) {
	    prefs.put("filePath", file.getPath());

	    // Update the stage title.
	    primaryStage.setTitle("AddressApp - " + file.getName());
	} else {
	    prefs.remove("filePath");

	    // Update the stage title.
	    primaryStage.setTitle("AddressApp");
	}
    }

    /**
     * Loads pelicula data from the specified file. The current pelicula data will be
     * replaced.
     * 
     * @param file
     */
    public void loadPeliculaDataFromFile(File file) {
	try {
	    JAXBContext context = JAXBContext.newInstance(PeliculaListWrapper.class);
	    Unmarshaller um = context.createUnmarshaller();

	    // Reading XML from the file and unmarshalling.
	    PeliculaListWrapper wrapper = (PeliculaListWrapper) um.unmarshal(file);

	    peliculaData.clear();
	    peliculaData.addAll(wrapper.getPeliculas());

	    // Save the file path to the registry.
	    setPeliculaFilePath(file);

	} catch (Exception e) { // catches ANY exception
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Could not load from file:\n" + file.getPath());
	    alert.showAndWait();
	}
    }

    /**
     * Saves the current pelicula data to the specified file.
     * 
     * @param file
     */
    public void savePeliculaDataToFile(File file) {
	try {
	    JAXBContext context = JAXBContext.newInstance(PeliculaListWrapper.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    // Wrapping our pelicula data.
	    PeliculaListWrapper wrapper = new PeliculaListWrapper();
	    wrapper.setPeliculas(peliculaData);

	    // Marshalling and saving XML to the file.
	    m.marshal(wrapper, file);

	    // Save the file path to the registry.
	    setPeliculaFilePath(file);
	} catch (Exception e) { // catches ANY exception
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Could not save data to file:\n" + file.getPath());
	    alert.showAndWait();
	}
    }

    /**
     * Returns the main stage.
     * 
     * @return
     */
    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public static void main(String[] args) {
	launch(args);
    }
}
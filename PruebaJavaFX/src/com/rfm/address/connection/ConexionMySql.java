package com.rfm.address.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConexionMySql {
    public String database = "Movies";
    public String url = "jdbc:mysql://moviedatabase.cskuigrbplma.us-east-2.rds.amazonaws.com:3306/" + database;
    public String user = "root";
    public String password = "12345678";

    public Connection conectar() {
	Connection link = null;
	try {
	    Class.forName("org.gjt.mm.mysql.Driver");
	    link = DriverManager.getConnection(this.url, this.user, this.password);
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error en la conexión: ");
	    alert.setContentText("Por favor, asegúrese de que el servidor esté operativo.");

	    alert.showAndWait();
	}
	return link;
    }

}

package com.rfm.address.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JdbcHelper {

    // para read
    public ResultSet realizarConsulta(String query) {
	ConexionMySql conexionMySql = new ConexionMySql();
	Connection conn = conexionMySql.conectar();
	ResultSet rs = null;
	Statement stQuery;
	try {
	    stQuery = conn.createStatement();
	    rs = stQuery.executeQuery(query);
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error al ejecutar: ");
	    alert.setContentText(query + ": " + ex);

	    alert.showAndWait();
	}
	return rs;
    }

    // para insert, update y delete
    public boolean ejecutarQuery(String query) {
	ConexionMySql conexionMySql = new ConexionMySql();
	Connection conn = conexionMySql.conectar();
	boolean exito = false;
	try {
	    PreparedStatement ps = conn.prepareStatement(query);
	    int affectedRows = ps.executeUpdate();
	    if (affectedRows != 0)
		exito = true;
	    else
		exito = false;
	    System.out.println("Affected rows: " + affectedRows);
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error al ejecutar: ");
	    alert.setContentText(query + ": " + ex);

	    alert.showAndWait();
	    exito = false;
	}
	return exito;
    }
}

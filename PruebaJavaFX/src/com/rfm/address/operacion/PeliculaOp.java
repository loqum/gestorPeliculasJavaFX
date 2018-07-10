package com.rfm.address.operacion;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
import com.rfm.address.connection.JdbcHelper;
import com.rfm.address.model.Pelicula;

public class PeliculaOp {

    private boolean insertar(Pelicula pelicula) {
	String query = "INSERT INTO peliculas (titulo,director,genero,year,soporte) " + "VALUES ('"
		+ pelicula.getTitulo() + "','" + pelicula.getDirector() + "','" + pelicula.getGenero() + "','"
		+ pelicula.getYear() + "','" + pelicula.getSoporte() + "')";
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    private boolean modificar(Pelicula pelicula) {
	String query = "UPDATE peliculas SET " + "titulo = '" + pelicula.getTitulo() + "'," + "director = '"
		+ pelicula.getDirector() + "'," + "genero = '" + pelicula.getGenero() + "'," + "year = '"
		+ pelicula.getYear() + "'," + "soporte = '" + pelicula.getSoporte() + "' WHERE idpeliculas = " + pelicula.getIdPeliculas();
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    public boolean eliminar(long id) {
	String query = "DELETE FROM peliculas WHERE idpeliculas = " + id;
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    public boolean validar(Pelicula pelicula) {
	StringBuilder sb = new StringBuilder();
	boolean esValido = true;
	if (pelicula == null) {
	    esValido = false;
	    sb.append("*No existe la película\n");
	}
	if (pelicula.getTitulo().trim().equals("")) {
	    esValido = false;
	    sb.append("*Título requerido\n");
	}
	if (pelicula.getTitulo().trim().length() > 100) {
	    esValido = false;
	    sb.append("*El título debe ser menor a 100 caracteres\n");
	}
	if (pelicula.getDirector().trim().equals("")) {
	    esValido = false;
	    sb.append("*Director requerido\n");
	}
	if (pelicula.getDirector().trim().length() > 100) {
	    esValido = false;
	    sb.append("*Autor debe ser menor a 100 caracteres\n");
	}
	if (!esValido) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Se encontraron los siguientes errores: ");
	    alert.setContentText(sb.toString());

	    alert.showAndWait();
	}
	return esValido;
    }

    public boolean guardar(Pelicula pelicula) {
	if (validar(pelicula) == false) {
	    return false;
	}
	boolean exito;
	if (pelicula.getIdPeliculas() == 0)
	    exito = insertar(pelicula);
	else
	    exito = modificar(pelicula);
	return exito;
    }

    public Pelicula buscarPelicula(long idBusqueda) {
	String query = "SELECT * FROM peliculas WHERE id = " + idBusqueda;
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	Pelicula pelicula = null;

	try {
	    if (rs.next()) {
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		int idPeliculas = rs.getInt("idpeliculas");
		pelicula = new Pelicula(titulo, director, genero, year, soporte, idPeliculas);
	    }
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null, "Error al buscar pelicula: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
	}
	return pelicula;
    }

    public ObservableList<Pelicula> buscarTodos() {
	String query = "SELECT * FROM peliculas";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		int idPeliculas = rs.getInt("idpeliculas");
		peliculas.add(new Pelicula(titulo, director, genero, year, soporte, idPeliculas));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error al buscar película: ");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorTitulo(String nombreBusqueda) {
	String query = "SELECT * FROM peliculas WHERE titulo LIKE '%" + nombreBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		int idPeliculas = rs.getInt("idpeliculas");
		peliculas.add(new Pelicula(titulo, director, genero, year, soporte, idPeliculas));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error al buscar película por nombre: ");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorDirector(String autorBusqueda) {
	String query = "SELECT * FROM peliculas WHERE director LIKE '%" + autorBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		int idPeliculas = rs.getInt("idpeliculas");
		peliculas.add(new Pelicula(titulo, director, genero, year, soporte, idPeliculas));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Error al buscar película por director: ");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

}

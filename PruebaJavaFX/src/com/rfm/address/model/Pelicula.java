package com.rfm.address.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * 
 */
public class Pelicula {

    private final StringProperty titulo;
    private final StringProperty director;
    private final StringProperty genero;
    private final IntegerProperty year;
    private final StringProperty soporte;
    private final IntegerProperty idpeliculas;

    /**
     * Default constructor.
     */
    public Pelicula() {
	this(null, null, null, 0, null, 0);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Pelicula(String titulo, String director, String genero, int year, String soporte, int id) {
	this.titulo = new SimpleStringProperty(titulo);
	this.director = new SimpleStringProperty(director);
	this.genero = new SimpleStringProperty(genero);
	this.year = new SimpleIntegerProperty(year);
	this.soporte = new SimpleStringProperty(soporte);
	this.idpeliculas = new SimpleIntegerProperty(id);
    }
    

    public String getTitulo() {
	return titulo.get();
    }

    public void setTitulo(String titulo) {
	this.titulo.set(titulo);
    }

    public StringProperty tituloProperty() {
	return titulo;
    }

    public String getDirector() {
	return director.get();
    }

    public void setDirector(String director) {
	this.director.set(director);
    }

    public StringProperty directorProperty() {
	return director;
    }

    public String getGenero() {
	return genero.get();
    }

    public void setGenero(String genero) {
	this.genero.set(genero);
    }

    public StringProperty generoProperty() {
	return genero;
    }

    public int getYear() {
	return year.get();
    }

    public void setYear(int year) {
	this.year.set(year);
    }

    public IntegerProperty yearProperty() {
	return year;
    }

    public String getSoporte() {
	return soporte.get();
    }

    public void setSoporte(String soporte) {
	this.soporte.set(soporte);
    }

    public StringProperty soporteProperty() {
	return soporte;
    }
    
    public int getIdPeliculas() {
	return idpeliculas.get();
    }
    
    public void setIdPeliculas(int idPeliculas) {
	this.idpeliculas.set(idPeliculas);
    }

    public IntegerProperty idPeliculasProperty() {
	return idpeliculas;
    }

}
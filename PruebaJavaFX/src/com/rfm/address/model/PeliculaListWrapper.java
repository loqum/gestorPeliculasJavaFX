package com.rfm.address.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the list of
 * persons to XML.
 * 
 * @author Marco Jakob
 */
@XmlRootElement(name = "peliculas")
public class PeliculaListWrapper {

    private List<Pelicula> peliculas;

    @XmlElement(name = "pelicula")
    public List<Pelicula> getPeliculas() {
	return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
	this.peliculas = peliculas;
    }
}

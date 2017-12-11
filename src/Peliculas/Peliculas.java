
package Peliculas;


public class Peliculas {
    private int idpeli;
    private String titulo;
    private String director;
    private String actor;
    private String clasificacion;
    private double precve;
    private double precren;
    private int cantidad;
    
    
    public Peliculas(){ 
    }
    
    public Peliculas(int idpeli, String titulo,String director, String actor, String clasificacion,double precve,double precren,int cantidad){
        this.idpeli=idpeli;
        this.titulo=titulo;
        this.director=director;
        this.actor=actor;
        this.clasificacion=clasificacion;
        this.precve=precve;
        this.precren=precren;
        this.cantidad=cantidad;
    }

  
    public int getIdpeli() {
        return idpeli;
    }

    public void setIdpeli(int idpeli) {
        this.idpeli = idpeli;
    }

    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getClasificacion() {
        return clasificacion;
    }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the precve
     */
    public double getPrecve() {
        return precve;
    }

    /**
     * @param precve the precve to set
     */
    public void setPrecve(double precve) {
        this.precve = precve;
    }

    /**
     * @return the precren
     */
    public double getPrecren() {
        return precren;
    }

    /**
     * @param precren the precren to set
     */
    public void setPrecren(double precren) {
        this.precren = precren;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}

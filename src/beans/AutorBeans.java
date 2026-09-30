package beans;
public class AutorBeans {
    private int idAutor;
    private String nombre;
    private String nacionalidad;
    public AutorBeans() { }
    public AutorBeans(int idAutor, String nombre, String nacionalidad) { this.idAutor=idAutor; this.nombre=nombre; this.nacionalidad=nacionalidad; }
    public int getIdAutor(){return idAutor;} public void setIdAutor(int v){idAutor=v;}
    public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getNacionalidad(){return nacionalidad;} public void setNacionalidad(String v){nacionalidad=v;}
    @Override public String toString(){return nombre;}
}

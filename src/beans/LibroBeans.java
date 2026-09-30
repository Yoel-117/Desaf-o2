package beans;
public class LibroBeans {
    private int idLibro;
    private String titulo;
    private int anioPublicacion;
    private int idAutor;
    private int idCategoria;
    private String nombreAutor;
    private String nombreCategoria;
    public LibroBeans() { }
    public LibroBeans(int idLibro,String titulo,int anioPublicacion,int idAutor,int idCategoria){this.idLibro=idLibro;this.titulo=titulo;this.anioPublicacion=anioPublicacion;this.idAutor=idAutor;this.idCategoria=idCategoria;}
    public int getIdLibro(){return idLibro;} public void setIdLibro(int v){idLibro=v;}
    public String getTitulo(){return titulo;} public void setTitulo(String v){titulo=v;}
    public int getAnioPublicacion(){return anioPublicacion;} public void setAnioPublicacion(int v){anioPublicacion=v;}
    public int getIdAutor(){return idAutor;} public void setIdAutor(int v){idAutor=v;}
    public int getIdCategoria(){return idCategoria;} public void setIdCategoria(int v){idCategoria=v;}
    public String getNombreAutor(){return nombreAutor;} public void setNombreAutor(String v){nombreAutor=v;}
    public String getNombreCategoria(){return nombreCategoria;} public void setNombreCategoria(String v){nombreCategoria=v;}
}

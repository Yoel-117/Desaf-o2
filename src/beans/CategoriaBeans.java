package beans;
public class CategoriaBeans {
    private int idCategoria;
    private String nombreCategoria;
    public CategoriaBeans() { }
    public CategoriaBeans(int idCategoria, String nombreCategoria){this.idCategoria=idCategoria;this.nombreCategoria=nombreCategoria;}
    public int getIdCategoria(){return idCategoria;} public void setIdCategoria(int v){idCategoria=v;}
    public String getNombreCategoria(){return nombreCategoria;} public void setNombreCategoria(String v){nombreCategoria=v;}
    @Override public String toString(){return nombreCategoria;}
}

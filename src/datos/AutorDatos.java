package datos;
import beans.AutorBeans; import beans.CategoriaBeans; import java.sql.*; import java.util.ArrayList; import java.util.List; import util.Conexion;
public class AutorDatos {
    public List<AutorBeans> listarAutores() throws SQLException { List<AutorBeans> lista=new ArrayList<>(); String sql="SELECT id_autor,nombre,nacionalidad FROM autor ORDER BY nombre"; try(Connection c=Conexion.obtenerConexion(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){while(rs.next()) lista.add(new AutorBeans(rs.getInt("id_autor"),rs.getString("nombre"),rs.getString("nacionalidad")));} return lista; }
    public List<CategoriaBeans> listarCategorias() throws SQLException { List<CategoriaBeans> lista=new ArrayList<>(); String sql="SELECT id_categoria,nombre_categoria FROM categoria ORDER BY nombre_categoria"; try(Connection c=Conexion.obtenerConexion(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){while(rs.next()) lista.add(new CategoriaBeans(rs.getInt("id_categoria"),rs.getString("nombre_categoria")));} return lista; }
}

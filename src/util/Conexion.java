package util;
import java.io.IOException; import java.io.InputStream; import java.sql.Connection; import java.sql.DriverManager; import java.sql.SQLException; import java.util.Properties;
public class Conexion {
    private static final String ARCHIVO_CONFIG="/config.properties";
    private static String url; private static String usuario; private static String clave;
    static { cargarConfiguracion(); }
    private Conexion() { }
    private static void cargarConfiguracion(){
        Properties p=new Properties();
        try(InputStream entrada=Conexion.class.getResourceAsStream(ARCHIVO_CONFIG)){
            if(entrada==null) throw new IOException("No se encontró config.properties"); p.load(entrada);
            url=p.getProperty("db.url"); usuario=p.getProperty("db.user"); clave=p.getProperty("db.password","");
            if(url==null||usuario==null) throw new IOException("Configuración incompleta");
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(IOException|ClassNotFoundException ex){throw new IllegalStateException("No se pudo cargar la configuración JDBC: "+ex.getMessage(),ex);}
    }
    public static Connection obtenerConexion() throws SQLException { return DriverManager.getConnection(url,usuario,clave); }
}

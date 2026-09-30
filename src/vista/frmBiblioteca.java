package vista;

import beans.AutorBeans;
import beans.CategoriaBeans;
import beans.LibroBeans;
import datos.AutorDatos;
import datos.LibroDatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class frmBiblioteca extends JFrame {
    private final LibroDatos libroDatos;
    private final AutorDatos autorDatos;
    private final JTextField txtTitulo;
    private final JTextField txtAnio;
    private final JComboBox<AutorBeans> cboAutor;
    private final JComboBox<CategoriaBeans> cboCategoria;
    private final JComboBox<String> cboFiltroAutor;
    private final JComboBox<String> cboFiltroCategoria;
    private final JTable tablaLibros;
    private final DefaultTableModel modeloTabla;
    private int idLibroSeleccionado;

    public frmBiblioteca() {
        libroDatos = new LibroDatos(); autorDatos = new AutorDatos();
        txtTitulo = new JTextField(28); txtAnio = new JTextField(8);
        cboAutor = new JComboBox<>(); cboCategoria = new JComboBox<>();
        cboFiltroAutor = new JComboBox<>(); cboFiltroCategoria = new JComboBox<>();
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Título", "Año", "Autor", "Categoría"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaLibros = new JTable(modeloTabla); idLibroSeleccionado = 0;
        configurarVentana(); cargarCombos(); cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Biblioteca Digital | POO404"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 650); setLocationRelativeTo(null); setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel encabezado = new JLabel("ADMINISTRACIÓN DE BIBLIOTECA DIGITAL", SwingConstants.CENTER);
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 22)); encabezado.setForeground(new Color(31, 78, 121));
        add(encabezado, BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout(10, 10)); centro.add(crearFormulario(), BorderLayout.NORTH); centro.add(crearTabla(), BorderLayout.CENTER); add(centro, BorderLayout.CENTER); add(crearBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout()); panel.setBorder(BorderFactory.createTitledBorder("Datos del libro"));
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 8, 5, 8); gbc.anchor = GridBagConstraints.WEST;
        agregarCampo(panel, gbc, 0, "Título:", txtTitulo); agregarCampo(panel, gbc, 1, "Año publicación:", txtAnio); agregarCampo(panel, gbc, 2, "Autor:", cboAutor); agregarCampo(panel, gbc, 3, "Categoría:", cboCategoria); return panel;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, java.awt.Component componente) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panel.add(componente, gbc); gbc.fill = GridBagConstraints.NONE;
    }

    private JPanel crearTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5)); panel.setBorder(BorderFactory.createTitledBorder("Libros registrados"));
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); tablaLibros.setAutoCreateRowSorter(true);
        tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(45); tablaLibros.getColumnModel().getColumn(1).setPreferredWidth(280);
        tablaLibros.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) seleccionarLibro(); });
        panel.add(crearFiltros(), BorderLayout.NORTH); panel.add(new JScrollPane(tablaLibros), BorderLayout.CENTER); return panel;
    }

    private JPanel crearFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT)); filtros.add(new JLabel("Filtrar autor:")); filtros.add(cboFiltroAutor); filtros.add(new JLabel("Filtrar categoría:")); filtros.add(cboFiltroCategoria);
        cboFiltroAutor.addActionListener(e -> cargarTabla()); cboFiltroCategoria.addActionListener(e -> cargarTabla()); return filtros;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5)); JButton guardar = new JButton("Guardar"); JButton editar = new JButton("Editar"); JButton eliminar = new JButton("Eliminar"); JButton limpiar = new JButton("Limpiar");
        guardar.addActionListener(e -> guardar()); editar.addActionListener(e -> editar()); eliminar.addActionListener(e -> eliminar()); limpiar.addActionListener(e -> limpiar()); panel.add(guardar); panel.add(editar); panel.add(eliminar); panel.add(limpiar); return panel;
    }

    private void cargarCombos() {
        try {
            List<AutorBeans> autores = autorDatos.listarAutores(); DefaultComboBoxModel<AutorBeans> ma = new DefaultComboBoxModel<>(); for (AutorBeans autor : autores) ma.addElement(autor); cboAutor.setModel(ma);
            DefaultComboBoxModel<String> mfa = new DefaultComboBoxModel<>(); mfa.addElement("Todos"); for (AutorBeans autor : autores) mfa.addElement(autor.getIdAutor() + " - " + autor.getNombre()); cboFiltroAutor.setModel(mfa);
            List<CategoriaBeans> categorias = autorDatos.listarCategorias(); DefaultComboBoxModel<CategoriaBeans> mc = new DefaultComboBoxModel<>(); for (CategoriaBeans categoria : categorias) mc.addElement(categoria); cboCategoria.setModel(mc);
            DefaultComboBoxModel<String> mfc = new DefaultComboBoxModel<>(); mfc.addElement("Todos"); for (CategoriaBeans categoria : categorias) mfc.addElement(categoria.getIdCategoria() + " - " + categoria.getNombreCategoria()); cboFiltroCategoria.setModel(mfc);
        } catch (SQLException | IllegalStateException ex) { mostrarError("No se pudieron cargar autores/categorías", ex); }
    }

    private void cargarTabla() {
        try { List<LibroBeans> libros = libroDatos.listar(obtenerIdFiltro(cboFiltroAutor), obtenerIdFiltro(cboFiltroCategoria)); modeloTabla.setRowCount(0); for (LibroBeans libro : libros) modeloTabla.addRow(new Object[]{libro.getIdLibro(), libro.getTitulo(), libro.getAnioPublicacion(), libro.getNombreAutor(), libro.getNombreCategoria()}); }
        catch (SQLException | NumberFormatException ex) { mostrarError("No se pudieron consultar los libros", ex); }
    }

    private String obtenerIdFiltro(JComboBox<String> combo) { if (combo.getSelectedIndex() <= 0 || combo.getSelectedItem() == null) return ""; return combo.getSelectedItem().toString().split(" - ", 2)[0]; }

    private LibroBeans validarFormulario() {
        String titulo = txtTitulo.getText().trim(); String anioTexto = txtAnio.getText().trim();
        if (titulo.isEmpty()) { mostrarAviso("El título es obligatorio."); txtTitulo.requestFocus(); return null; }
        if (titulo.length() > 200) { mostrarAviso("El título no puede superar 200 caracteres."); return null; }
        if (anioTexto.isEmpty()) { mostrarAviso("El año de publicación es obligatorio."); txtAnio.requestFocus(); return null; }
        int anio; try { anio = Integer.parseInt(anioTexto); } catch (NumberFormatException ex) { mostrarAviso("El año debe ser un número entero."); txtAnio.requestFocus(); return null; }
        int actual = Year.now().getValue(); if (anio < 1000 || anio > actual) { mostrarAviso("El año debe estar entre 1000 y " + actual + "."); return null; }
        AutorBeans autor = (AutorBeans) cboAutor.getSelectedItem(); CategoriaBeans categoria = (CategoriaBeans) cboCategoria.getSelectedItem(); if (autor == null || categoria == null) { mostrarAviso("Debe seleccionar un autor y una categoría."); return null; }
        return new LibroBeans(idLibroSeleccionado, titulo, anio, autor.getIdAutor(), categoria.getIdCategoria());
    }

    private void guardar() { LibroBeans libro = validarFormulario(); if (libro == null) return; try { libroDatos.insertar(libro); mostrarExito("Libro guardado correctamente."); limpiar(); cargarTabla(); } catch (SQLException ex) { mostrarError("No se pudo guardar el libro", ex); } }
    private void editar() { if (idLibroSeleccionado == 0) { mostrarAviso("Seleccione un libro de la tabla para editar."); return; } LibroBeans libro = validarFormulario(); if (libro == null) return; try { libroDatos.actualizar(libro); mostrarExito("Libro actualizado correctamente."); limpiar(); cargarTabla(); } catch (SQLException ex) { mostrarError("No se pudo actualizar el libro", ex); } }
    private void eliminar() { if (idLibroSeleccionado == 0) { mostrarAviso("Seleccione un libro de la tabla para eliminar."); return; } int respuesta = JOptionPane.showConfirmDialog(this, "¿Eliminar el libro seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); if (respuesta != JOptionPane.YES_OPTION) return; try { libroDatos.eliminar(idLibroSeleccionado); mostrarExito("Libro eliminado correctamente."); limpiar(); cargarTabla(); } catch (SQLException ex) { mostrarError("No se pudo eliminar el libro", ex); } }

    private void seleccionarLibro() { int fila = tablaLibros.getSelectedRow(); if (fila < 0) return; int mf = tablaLibros.convertRowIndexToModel(fila); idLibroSeleccionado = (Integer) modeloTabla.getValueAt(mf, 0); txtTitulo.setText((String) modeloTabla.getValueAt(mf, 1)); txtAnio.setText(String.valueOf(modeloTabla.getValueAt(mf, 2))); seleccionarAutor((String) modeloTabla.getValueAt(mf, 3)); seleccionarCategoria((String) modeloTabla.getValueAt(mf, 4)); }
    private void seleccionarAutor(String nombre) { for (int i = 0; i < cboAutor.getItemCount(); i++) if (cboAutor.getItemAt(i).getNombre().equals(nombre)) { cboAutor.setSelectedIndex(i); break; } }
    private void seleccionarCategoria(String nombre) { for (int i = 0; i < cboCategoria.getItemCount(); i++) if (cboCategoria.getItemAt(i).getNombreCategoria().equals(nombre)) { cboCategoria.setSelectedIndex(i); break; } }
    private void limpiar() { idLibroSeleccionado = 0; txtTitulo.setText(""); txtAnio.setText(""); tablaLibros.clearSelection(); if (cboAutor.getItemCount() > 0) cboAutor.setSelectedIndex(0); if (cboCategoria.getItemCount() > 0) cboCategoria.setSelectedIndex(0); txtTitulo.requestFocus(); }
    private void mostrarAviso(String mensaje) { JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE); }
    private void mostrarExito(String mensaje) { JOptionPane.showMessageDialog(this, mensaje, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE); }
    private void mostrarError(String contexto, Exception ex) { JOptionPane.showMessageDialog(this, contexto + ":\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
}

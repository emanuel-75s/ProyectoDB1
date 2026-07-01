/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package example.proyectodb.formularios;
import example.proyectodb.controlador.ControladorMueble;
import example.proyectodb.modelo.Mueble;
import example.proyectodb.modelo.Usuario;
import example.proyectodb.util.Validaciones;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author emanu
 */
public class FrmMuebles extends javax.swing.JFrame {
    private Usuario usuarioActual;
    private ControladorMueble controlador;
    private int idMuebleSeleccionado = -1;
    /**
     * Creates new form FrmMuebles
     */
    public FrmMuebles(Usuario usuario) {
        initComponents();
        this.setLocationRelativeTo (null);
        this.usuarioActual = usuario;
        this.controlador = new ControladorMueble();
       configurarVentana();
       configurarTabla();
       cargarMuebles();
       //configurarComboBox();////////////////////////////////////////////////////////////////////////////////////////////
        //deshabilitarCampos();
        
    }
    //////////////////////
    public FrmMuebles() {
    initComponents();
    this.setLocationRelativeTo(null);
    this.controlador = new ControladorMueble();
    configurarTabla();
    cargarMuebles();
    deshabilitarCampos();
    //configurarComboBox();///////////////////////////////////////////////////////////////////////////////////////////////
    this.setTitle("Gestión de Muebles");
}
    ///////////////////
 private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Gestión de Muebles - " + usuarioActual.getNombre());
    }
    
    private void configurarTabla() {
        // Configurar modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Código", "Nombre", "Categoría", "Precio", "Stock", "Dimensiones"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        tablaMuebles.setModel(modelo);
        
        // Ancho de columnas
        tablaMuebles.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tablaMuebles.getColumnModel().getColumn(1).setPreferredWidth(80);  // Código
        tablaMuebles.getColumnModel().getColumn(2).setPreferredWidth(200); // Nombre
        tablaMuebles.getColumnModel().getColumn(3).setPreferredWidth(100); // Categoría
        tablaMuebles.getColumnModel().getColumn(4).setPreferredWidth(80);  // Precio
        tablaMuebles.getColumnModel().getColumn(5).setPreferredWidth(60);  // Stock
        tablaMuebles.getColumnModel().getColumn(6).setPreferredWidth(100); // Dimensiones
    }
    
    private void cargarMuebles() {
        DefaultTableModel modelo = (DefaultTableModel) tablaMuebles.getModel();
        modelo.setRowCount(0); // Limpiar tabla
        
        List<Mueble> muebles = controlador.listarTodos();
        
        for (Mueble m : muebles) {
            modelo.addRow(new Object[]{
                m.getIdMueble(),
                m.getCodigoMueble(),
                m.getNombreMueble(),
                m.getCategoria(),
                String.format("Q%.2f", m.getPrecio()),
                m.getCantidadStock(),
                m.getDimensiones()
            });
        }
    }
    
     private void deshabilitarCampos() {
        txtCodigo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtCategoria.setEnabled(false);
        //txtFiltroCategoria.setEnabled(false);
        txtMaterial.setEnabled(false);
        txtColor.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtStock.setEnabled(false);
        txtDimensiones.setEnabled(false);
        
        btnSave.setEnabled(false);
    }
    
    private void habilitarCampos() {
        txtCodigo.setEnabled(true);
        txtNombre.setEnabled(true);
        txtCategoria.setEnabled(true);
        txtMaterial.setEnabled(true);
        //txtFiltroCategoria.setEnabled(true);
        txtColor.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtStock.setEnabled(true);
        txtDimensiones.setEnabled(true);
        
        btnSave.setEnabled(true);
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        //txtFiltroCategoria.setText("");
        txtMaterial.setText("");
        txtColor.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtDimensiones.setText("");
        idMuebleSeleccionado = -1;
    }
    
    private void cargarDatosSeleccionados() {
        int fila = tablaMuebles.getSelectedRow();
        
        if (fila >= 0) {
            idMuebleSeleccionado = (int) tablaMuebles.getValueAt(fila, 0);
            
            Mueble mueble = controlador.buscarPorId(idMuebleSeleccionado);
            
            if (mueble != null) {
                txtCodigo.setText(mueble.getCodigoMueble());
                txtNombre.setText(mueble.getNombreMueble());
                txtCategoria.setText(mueble.getCategoria());
                txtMaterial.setText(mueble.getMaterial());
                txtColor.setText(mueble.getColor());
                txtPrecio.setText(String.valueOf(mueble.getPrecio()));
                txtStock.setText(String.valueOf(mueble.getCantidadStock()));
                txtDimensiones.setText(mueble.getDimensiones());
            }
        }
    }//////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////
    /////////////////////////////////////////////////

 ////////////////////////////////////////////////NOCHE 1:0000000
    /*private void configurarComboBox() {
    // Limpiar ComboBox de categoría del formulario
    cmbCategoria.removeAllItems();
    cmbCategoria.addItem("-- Seleccione --");
    
    // Limpiar ComboBox de filtro de búsqueda
    cmbFiltroCategoria.removeAllItems();
    cmbFiltroCategoria.addItem("Todas");
    
    // Obtener categorías únicas desde el controlador
    List<String> categorias = controlador.obtenerCategorias();
    
    for (String categoria : categorias) {
        cmbCategoria.addItem(categoria);
        cmbFiltroCategoria.addItem(categoria);
    }
}*/
    //////////////////////////////////////////////////////////////////////////
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDimensiones = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        txtCategoria = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMuebles = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel1.setText("Muebles ");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel2.setText("Codigo :");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel3.setText("Nombre :");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel4.setText("Categoria :");

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel5.setText(" Color : ");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel6.setText(" Material :");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel7.setText("Precio :");

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel8.setText("Stock :");

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel9.setText("Dimensiones :");

        btnNuevo.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\134224_add_plus_new_icon.png")); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\326688_floppy_save_guardar_icon.png")); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2931178_change_edit_pencil_creative_design_icon.png")); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\4115230_cancel_close_delete_icon.png")); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\9057100_erase_icon.png")); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo)
                            .addComponent(txtMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(txtPrecio))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStock, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(txtCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtColor)
                            .addComponent(txtDimensiones, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(58, 58, 58)
                .addComponent(btnModificar)
                .addGap(57, 57, 57)
                .addComponent(btnEliminar)
                .addGap(61, 61, 61)
                .addComponent(btnLimpiar)
                .addGap(77, 77, 77))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtDimensiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 102, 0));

        tablaMuebles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "Código", "Nombre", "Categoría ", "Precio ", "Stock ", "Dimension"
            }
        ));
        jScrollPane1.setViewportView(tablaMuebles);

        btnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\211817_search_strong_icon.png")); // NOI18N
        btnBuscar.setText("Buscar ");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnVolver.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2931185_door_enter_exit_leave_out_icon.png")); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 919, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
       
       if (Validaciones.campoVacio(txtCodigo)) {
            Validaciones.mostrarError("Ingrese el código del mueble");
            txtCodigo.requestFocus();
            return;
        }
        
        if (Validaciones.campoVacio(txtNombre)) {
            Validaciones.mostrarError("Ingrese el nombre del mueble");
            txtNombre.requestFocus();
            return;
        }
        
       if (Validaciones.campoVacio(txtCategoria)) {
        Validaciones.mostrarError("Ingrese una categoría");
        txtCategoria.requestFocus();
        return;
    
        }
        
        if (!Validaciones.esNumeroDecimal(txtPrecio.getText())) {
            Validaciones.mostrarError("Ingrese un precio válido");
            txtPrecio.requestFocus();
            return;
        }
        
        if (!Validaciones.esNumeroEntero(txtStock.getText())) {
            Validaciones.mostrarError("Ingrese un stock válido");
            txtStock.requestFocus();
            return;
        }
        
        Mueble mueble = new Mueble();
        mueble.setCodigoMueble(txtCodigo.getText().trim());
        mueble.setNombreMueble(txtNombre.getText().trim());
        //mueble.setCategoria((String) cmbCategoria.getSelectedItem());
        mueble.setCategoria(txtCategoria.getText().trim());
        mueble.setMaterial(txtMaterial.getText().trim());
        mueble.setColor(txtColor.getText().trim());
        mueble.setPrecio(Validaciones.textoADecimal(txtPrecio.getText()));
        mueble.setCantidadStock(Validaciones.textoAEntero(txtStock.getText()));
        mueble.setDimensiones(txtDimensiones.getText().trim());
        
        
        boolean resultado;
        
        if (idMuebleSeleccionado == -1) {
           
            resultado = controlador.guardarMueble(mueble);
            if (resultado) {
                Validaciones.mostrarExito("Mueble guardado exitosamente");
            }
        } else {
           
            mueble.setIdMueble(idMuebleSeleccionado);
            resultado = controlador.actualizarMueble(mueble);
            if (resultado) {
                Validaciones.mostrarExito("Mueble actualizado exitosamente");
            }
        }
        
        if (resultado) {
            limpiarCampos();
            deshabilitarCampos();
            cargarMuebles();
        } else {
            Validaciones.mostrarError("Error al guardar el mueble");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

  private void tablaMueblesMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) { // Doble clic
            cargarDatosSeleccionados();
        }
    }    
    
    
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioActual);
        menu.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
limpiarCampos();
    habilitarCampos();
    txtCodigo.requestFocus();
    idMuebleSeleccionado = -1; 

        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = tablaMuebles.getSelectedRow();
    
    if (fila < 0) {
        Validaciones.mostrarError("Seleccione un mueble de la tabla");
        return;
    }
    cargarDatosSeleccionados();
    habilitarCampos();
    txtCodigo.setEnabled(false); // El código no se puede modificar
    txtNombre.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaMuebles.getSelectedRow();
    
    if (fila < 0) {
        Validaciones.mostrarError("Seleccione un mueble de la tabla para eliminar");
        return;
    }
    
    int id = (int) tablaMuebles.getValueAt(fila, 0);
    String nombre = (String) tablaMuebles.getValueAt(fila, 2);
    
    int respuesta = JOptionPane.showConfirmDialog(
        this,
        "¿Esta seguro de eliminar el mueble: " + nombre + "?",
        "Confirmar Eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );
    
    if (respuesta == JOptionPane.YES_OPTION) {
        boolean resultado = controlador.eliminarMueble(id);
        
        if (resultado) {
            Validaciones.mostrarExito("Mueble eliminado exitosamente");
            limpiarCampos();
            deshabilitarCampos();
            cargarMuebles();
        } else {
            Validaciones.mostrarError("No se pudo eliminar el mueble");
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    deshabilitarCampos();
    tablaMuebles.clearSelection();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String textoBuscar = txtBuscar.getText().trim();
    
    DefaultTableModel modelo = (DefaultTableModel) tablaMuebles.getModel();
    modelo.setRowCount(0);
    
    List<Mueble> muebles;
    
    // Solo buscar por nombre si hay texto
    if (!textoBuscar.isEmpty()) {
        muebles = controlador.buscarPorNombre(textoBuscar);
    } else {
        // Si no hay texto, mostrar todos
        muebles = controlador.listarTodos();
    }
    
    // Cargar resultados en la tabla
    for (Mueble m : muebles) {
        modelo.addRow(new Object[]{
            m.getIdMueble(),
            m.getCodigoMueble(),
            m.getNombreMueble(),
            m.getCategoria(),
            String.format("Q%.2f", m.getPrecio()),
            m.getCantidadStock(),
            m.getDimensiones()
        });
    }
    
    if (muebles.isEmpty()) {
        Validaciones.mostrarAdvertencia("No se encontraron muebles con los criterios de búsqueda");
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMuebles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMuebles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMuebles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMuebles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMuebles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMuebles;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDimensiones;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}

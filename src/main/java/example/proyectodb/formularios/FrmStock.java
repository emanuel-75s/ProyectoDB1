/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package example.proyectodb.formularios;
import example.proyectodb.controlador.ControladorMueble;
import example.proyectodb.formularios.FrmMenuPrincipal;
import example.proyectodb.modelo.Mueble;
import example.proyectodb.modelo.Usuario;
import example.proyectodb.util.GeneradorPDF;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author emanu
 */
public class FrmStock extends javax.swing.JFrame {

     private Usuario usuarioActual;
    private ControladorMueble controlador;
    private static final int STOCK_CRITICO = 1;
    private static final int STOCK_BAJO = 5;
    /**
     * Creates new form FrmStock
     */
    public FrmStock() {
        initComponents();
    }
    public FrmStock(Usuario usuario) {
        initComponents();
        this.usuarioActual = usuario;
        this.controlador = new ControladorMueble();
        configurarVentana();
        configurarTabla();
        cargarTodosDatos();
    }
   
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Control de Stock - " + usuarioActual.getNombre());
    }
    
    private void configurarTabla() {
        // Modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Código", "Nombre", "Categoría", "Stock", "Estado", "Precio"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaStock.setModel(modelo);
        
        // Ajustar anchos de columnas
        tablaStock.getColumnModel().getColumn(0).setPreferredWidth(80);  // Código
        tablaStock.getColumnModel().getColumn(1).setPreferredWidth(250); // Nombre
        tablaStock.getColumnModel().getColumn(2).setPreferredWidth(100); // Categoría
        tablaStock.getColumnModel().getColumn(3).setPreferredWidth(60);  // Stock
        tablaStock.getColumnModel().getColumn(4).setPreferredWidth(100); // Estado
        tablaStock.getColumnModel().getColumn(5).setPreferredWidth(100); // Precio
        
        // Centrar columnas numéricas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaStock.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablaStock.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tablaStock.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tablaStock.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        // Renderer personalizado para la columna Stock (colores según nivel)
        tablaStock.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (value != null) {
                    int stock = Integer.parseInt(value.toString());
                    
                    if (stock <= STOCK_CRITICO) {
                        c.setBackground(new Color(255, 200, 200)); // Rojo claro
                        c.setForeground(new Color(139, 0, 0)); // Rojo oscuro
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if (stock <= STOCK_BAJO) {
                        c.setBackground(new Color(255, 230, 180)); // Naranja claro
                        c.setForeground(new Color(184, 92, 0)); // Naranja oscuro
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        if (!isSelected) {
                            c.setBackground(Color.WHITE);
                            c.setForeground(Color.BLACK);
                        }
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                }
                
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
    tablaStock.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (value != null) {
                    String estado = value.toString();
                    
                    if (estado.contains("CRÍTICO")) {
                        c.setForeground(new Color(220, 20, 60)); // Rojo
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if (estado.contains("BAJO")) {
                        c.setForeground(new Color(255, 140, 0)); // Naranja
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(new Color(34, 139, 34)); // Verde
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                    
                    if (!isSelected) {
                        c.setBackground(Color.WHITE);
                    }
                }
                
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
    }
    
    
    private void cargarTodosDatos() {
        cargarTodosMuebles();
        actualizarResumen();
    }
    
    private void cargarTodosMuebles() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles = controlador.listarTodos();
        cargarMueblesEnTabla(muebles);
    }
    
    private void cargarStockCritico() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles = controlador.listarTodos().stream()
            .filter(m -> m.getCantidadStock() <= STOCK_CRITICO)
            .collect(Collectors.toList());
        
        if (muebles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay muebles con stock crítico",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        cargarMueblesEnTabla(muebles);
    }
    
    private void cargarStockBajo() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles = controlador.obtenerStockBajo();
        
        if (muebles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay muebles con stock bajo",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        cargarMueblesEnTabla(muebles);
    }
    
    private void cargarStockNormal() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles = controlador.listarTodos().stream()
            .filter(m -> m.getCantidadStock() > STOCK_BAJO)
            .collect(Collectors.toList());
        
        cargarMueblesEnTabla(muebles);
    }
    
    private void cargarMueblesEnTabla(List<Mueble> muebles) {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        
        for (Mueble m : muebles) {
            String estado;
            
            if (m.getCantidadStock() <= STOCK_CRITICO) {
                estado = " ! CRÍTICO";
            } else if (m.getCantidadStock() <= STOCK_BAJO) {
                estado = " _ BAJO";
            } else {
                estado = " - NORMAL";
            }
            
            modelo.addRow(new Object[]{
                m.getCodigoMueble(),
                m.getNombreMueble(),
                m.getCategoria(),
                m.getCantidadStock(),
                estado,
                String.format("Q%.2f", m.getPrecio())
            });
        }
        
        actualizarContadorSeleccion();
    }
    
    private void actualizarResumen() {
        List<Mueble> todosMuebles = controlador.listarTodos();
        
        int totalMuebles = todosMuebles.size();
        
        int stockCritico = (int) todosMuebles.stream()
            .filter(m -> m.getCantidadStock() <= STOCK_CRITICO)
            .count();
        
        int stockBajo = (int) todosMuebles.stream()
            .filter(m -> m.getCantidadStock() <= STOCK_BAJO && m.getCantidadStock() > STOCK_CRITICO)
            .count();
        
        txtTotalMuebles.setText(String.valueOf(totalMuebles));
        txtStockBajo.setText(String.valueOf(stockBajo));
        txtStockCritico.setText(String.valueOf(stockCritico));
        
        // Cambiar color según criticidad
        if (stockCritico > 0) {
            txtStockCritico.setForeground(Color.RED);
            txtStockCritico.setFont(new Font("Arial", Font.BOLD, 14));
        } else {
            txtStockCritico.setForeground(Color.BLACK);
        }
        
        if (stockBajo > 0) {
            txtStockBajo.setForeground(new Color(255, 140, 0));
            txtStockBajo.setFont(new Font("Arial", Font.BOLD, 14));
        } else {
            txtStockBajo.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Actualiza el contador de productos seleccionados
     */
    private void actualizarContadorSeleccion() {
        int filas = tablaStock.getRowCount();
        lblSeleccionados.setText("Productos mostrados: " + filas);
    }
    
     private void buscarMueble() {
        String textoBuscar = txtBuscar.getText().trim().toLowerCase();
        
        if (textoBuscar.isEmpty()) {
            cargarTodosMuebles();
            return;
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles = controlador.listarTodos().stream()
            .filter(m -> m.getNombreMueble().toLowerCase().contains(textoBuscar) ||
                        m.getCodigoMueble().toLowerCase().contains(textoBuscar))
            .collect(Collectors.toList());
        
        if (muebles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron muebles con: " + txtBuscar.getText(),
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        cargarMueblesEnTabla(muebles);
    }
     
     private void filtrarPorCategoria() {
        String categoriaBuscada = txtCategoria.getText().trim();
        
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0);
        
        List<Mueble> muebles;
        
        if (categoriaBuscada.isEmpty()) {
            muebles = controlador.listarTodos();
        } else {
            muebles = controlador.listarTodos().stream()
                .filter(m -> m.getCategoria().toLowerCase().contains(categoriaBuscada.toLowerCase()))
                .collect(Collectors.toList());
        }
        
        cargarMueblesEnTabla(muebles);
    }
     
     private void exportarPDF() {
        // Obtener los muebles actualmente mostrados en la tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay datos para exportar",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Determinar qué tipo de reporte es según los datos mostrados
        List<Mueble> muebles = controlador.listarTodos();
        
        // Preguntar al usuario qué tipo de reporte quiere
        String[] opciones = {"Stock Crítico", "Stock Bajo", "Todos los Muebles"};
        int seleccion = JOptionPane.showOptionDialog(this,
            "¿Qué reporte desea exportar?",
            "Exportar a PDF",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);
        
        List<Mueble> mueblesAExportar;
        String tipoReporte;
        
        switch (seleccion) {
            case 0:
                mueblesAExportar = muebles.stream()
                    .filter(m -> m.getCantidadStock() <= STOCK_CRITICO)
                    .collect(Collectors.toList());
                tipoReporte = "Stock_Critico";
                break;
            case 1:
                mueblesAExportar = controlador.obtenerStockBajo();
                tipoReporte = "Stock_Bajo";
                break;
            case 2:
                mueblesAExportar = muebles;
                tipoReporte = "Inventario_Completo";
                break;
            default:
                return;
        }
        
        boolean resultado = GeneradorPDF.generarReporteMuebles(mueblesAExportar, tipoReporte);
        
        if (resultado) {
            JOptionPane.showMessageDialog(this,
                "Reporte exportado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
     
     private void enviarAlerta() {
        List<Mueble> stockCritico = controlador.listarTodos().stream()
            .filter(m -> m.getCantidadStock() <= STOCK_CRITICO)
            .collect(Collectors.toList());
        
        List<Mueble> stockBajo = controlador.obtenerStockBajo();
        
        if (stockCritico.isEmpty() && stockBajo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay productos con stock bajo para alertar",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("️ ALERTA DE STOCK ️\n\n");
        
        if (!stockCritico.isEmpty()) {
            mensaje.append(" STOCK CRÍTICO (≤3 unidades):\n");
            for (Mueble m : stockCritico) {
                mensaje.append(String.format("  • %s (%s): %d unidades\n",
                    m.getNombreMueble(),
                    m.getCodigoMueble(),
                    m.getCantidadStock()));
            }
            mensaje.append("\n");
        }
        
        if (!stockBajo.isEmpty()) {
            mensaje.append("STOCK BAJO (≤5 unidades):\n");
            for (Mueble m : stockBajo) {
                if (m.getCantidadStock() > STOCK_CRITICO) {
                    mensaje.append(String.format("  • %s (%s): %d unidades\n",
                        m.getNombreMueble(),
                        m.getCodigoMueble(),
                        m.getCantidadStock()));
                }
            }
        }
        
        mensaje.append("\n");
        mensaje.append("Se recomienda reabastecer estos productos lo antes posible.");
        
        JOptionPane.showMessageDialog(this,
            mensaje.toString(),
            "Alerta de Stock",
            JOptionPane.WARNING_MESSAGE);
        
        // Aquí podrías agregar funcionalidad para enviar email real
        JOptionPane.showMessageDialog(this,
            "En una versión completa, aquí se enviaría un correo electrónico\n" +
            "al departamento de compras con esta alerta.",
            "Funcionalidad Futura",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
        txtTotalMuebles = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtStockBajo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtStockCritico = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnFiltroCritico = new javax.swing.JButton();
        btnStockBajo = new javax.swing.JButton();
        btnStockNormal = new javax.swing.JButton();
        btnFiltroTodos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaStock = new javax.swing.JTable();
        btnExportarPdf = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEnviarAlerta = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblSeleccionados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel1.setText("Inventario ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Total Muebles");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Stock");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Stock Crítico");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalMuebles, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockBajo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtStockCritico, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotalMuebles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtStockBajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtStockCritico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        btnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\211817_search_strong_icon.png")); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Buscar");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Categoria");

        btnFiltroCritico.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\216514_warning_icon.png")); // NOI18N
        btnFiltroCritico.setText("Stock Critico");
        btnFiltroCritico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroCriticoActionPerformed(evt);
            }
        });

        btnStockBajo.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\352167_down_thumb_icon.png")); // NOI18N
        btnStockBajo.setText("Stock Bajo");
        btnStockBajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockBajoActionPerformed(evt);
            }
        });

        btnStockNormal.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2290849_document_done_excellent_list_note_icon.png")); // NOI18N
        btnStockNormal.setText("Stock Normal");
        btnStockNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockNormalActionPerformed(evt);
            }
        });

        btnFiltroTodos.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\8665111_border_all_icon.png")); // NOI18N
        btnFiltroTodos.setText("Todos ");
        btnFiltroTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnFiltroCritico)
                        .addGap(66, 66, 66)
                        .addComponent(btnStockBajo)
                        .addGap(69, 69, 69)
                        .addComponent(btnStockNormal)
                        .addGap(66, 66, 66)
                        .addComponent(btnFiltroTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFiltroCritico)
                    .addComponent(btnStockBajo)
                    .addComponent(btnStockNormal)
                    .addComponent(btnFiltroTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        jPanel3.setBackground(new java.awt.Color(204, 102, 0));

        tablaStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaStock);

        btnExportarPdf.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\9055322_bxs_file_pdf_icon.png")); // NOI18N
        btnExportarPdf.setText("Exportar");
        btnExportarPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarPdfActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2849815_download_multimedia_file_document_icon.png")); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEnviarAlerta.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\211610_circled_alert_icon.png")); // NOI18N
        btnEnviarAlerta.setText("Enviar Alerta");
        btnEnviarAlerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarAlertaActionPerformed(evt);
            }
        });

        btnVolver.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2931185_door_enter_exit_leave_out_icon.png")); // NOI18N
        btnVolver.setText("Regresar");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Productos Selccionados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnExportarPdf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)
                        .addGap(94, 94, 94)
                        .addComponent(btnEnviarAlerta)
                        .addGap(76, 76, 76)
                        .addComponent(btnVolver))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(lblSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSeleccionados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportarPdf)
                    .addComponent(btnActualizar)
                    .addComponent(btnEnviarAlerta)
                    .addComponent(btnVolver))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        
        buscarMueble();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnFiltroCriticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroCriticoActionPerformed
        // TODO add your handling code here:
        cargarStockCritico();
    }//GEN-LAST:event_btnFiltroCriticoActionPerformed

    private void btnStockBajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockBajoActionPerformed
        // TODO add your handling code here:
         cargarStockBajo();
    }//GEN-LAST:event_btnStockBajoActionPerformed

    private void btnStockNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockNormalActionPerformed
        // TODO add your handling code here:
         cargarStockNormal();
    }//GEN-LAST:event_btnStockNormalActionPerformed

    private void btnFiltroTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroTodosActionPerformed
        // TODO add your handling code here:
        cargarTodosMuebles();
    }//GEN-LAST:event_btnFiltroTodosActionPerformed

    private void btnExportarPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarPdfActionPerformed
        // TODO add your handling code here:
         exportarPDF();
    }//GEN-LAST:event_btnExportarPdfActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarTodosDatos();
        JOptionPane.showMessageDialog(this, "Datos actualizados", "Información", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEnviarAlertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarAlertaActionPerformed
        // TODO add your handling code here:
        enviarAlerta();
    }//GEN-LAST:event_btnEnviarAlertaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        //FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioActual);
        FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioActual);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

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
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEnviarAlerta;
    private javax.swing.JButton btnExportarPdf;
    private javax.swing.JButton btnFiltroCritico;
    private javax.swing.JButton btnFiltroTodos;
    private javax.swing.JButton btnStockBajo;
    private javax.swing.JButton btnStockNormal;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSeleccionados;
    private javax.swing.JTable tablaStock;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtStockBajo;
    private javax.swing.JTextField txtStockCritico;
    private javax.swing.JTextField txtTotalMuebles;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package example.proyectodb.formularios;
import example.proyectodb.controlador.ControladorMueble;
import example.proyectodb.controlador.ControladorVenta;
import example.proyectodb.modelo.Mueble;
import example.proyectodb.modelo.Usuario;
import example.proyectodb.modelo.Venta;
import example.proyectodb.util.Validaciones;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author emanu
 */
public class FrmVentas extends javax.swing.JFrame {

    private Usuario usuarioActual;
    private ControladorVenta controladorVenta;
    private ControladorMueble controladorMueble;
    private Mueble muebleSeleccionado;
    
    /**
     * Creates new form FrmVentas
     */
    public FrmVentas() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

     public FrmVentas(Usuario usuario) {
        initComponents();
        this.usuarioActual = usuario;
        this.controladorVenta = new ControladorVenta();
        this.controladorMueble = new ControladorMueble();
        this.setLocationRelativeTo(null);
     //   configurarVentana();
      //  limpiarFormulario();
     
    }
    
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Registrar Venta - " + usuarioActual.getNombre());
        spnCantidad.setValue(1);
        
        // Configurar spinner de cantidad
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
        spnCantidad.setModel(model);
        
        // Agregar listener al spinner para auto-calcular
        spnCantidad.addChangeListener(e -> calcularTotal());
    }
    
    
     private void limpiarFormulario() {
        txtCodigoMueble.setText("");
        txtNombreMueble.setText("");
        txtPrecioUnitario.setText("Q0.00");
        txtStockDisponible.setText("0");
        txtCliente.setText("");
        txtTelefono.setText("");
        spnCantidad.setValue(1);
        txtTipoPago.setText("");
        txtTotal.setText("Q 0.00");
        muebleSeleccionado = null;
        
        // Habilitar/deshabilitar controles
        spnCantidad.setEnabled(false);
        btnCalcular.setEnabled(false);
        btnRegistrar.setEnabled(false);
        
        txtCodigoMueble.requestFocus();
    }
    
    private void buscarMueble() {
        String codigo = txtCodigoMueble.getText().trim();
        
        if (codigo.isEmpty()) {
            Validaciones.mostrarAdvertencia("Ingrese el código del mueble");
            txtCodigoMueble.requestFocus();
            return;
        }
        
        muebleSeleccionado = controladorMueble.buscarPorCodigo(codigo);
        
        if (muebleSeleccionado != null) {
            // Mostrar datos del mueble
            txtNombreMueble.setText(muebleSeleccionado.getNombreMueble());
            txtPrecioUnitario.setText(String.format("Q%.2f", muebleSeleccionado.getPrecio()));
            txtStockDisponible.setText(String.valueOf(muebleSeleccionado.getCantidadStock()));
              if (muebleSeleccionado.getCantidadStock() == 0) {
                Validaciones.mostrarAdvertencia("Este mueble no tiene stock disponible");
                spnCantidad.setEnabled(false);
                btnCalcular.setEnabled(false);
                btnRegistrar.setEnabled(false);
            } else {
                spnCantidad.setEnabled(true);
                btnCalcular.setEnabled(true);
                btnRegistrar.setEnabled(true);
                
                // Limitar cantidad al stock disponible
                SpinnerNumberModel model = new SpinnerNumberModel(
                    1, 
                    1, 
                    muebleSeleccionado.getCantidadStock(), 
                    1
                );
                spnCantidad.setModel(model);
                calcularTotal();
            }
            
            txtCliente.requestFocus();
            
        } else {
            Validaciones.mostrarError("No se encontró el mueble con código: " + codigo);
            txtNombreMueble.setText("");
            txtPrecioUnitario.setText("Q0.00");
            txtStockDisponible.setText("0");
            spnCantidad.setEnabled(false);
        }
    }   
      
       private void calcularTotal() {
        if (muebleSeleccionado == null) {
            txtTotal.setText("Q 0.00");
            return;
        }
        
        int cantidad = (int) spnCantidad.getValue();
        double precioUnitario = muebleSeleccionado.getPrecio();
        double total = cantidad * precioUnitario;
        
        txtTotal.setText(String.format("Q %.2f", total));
        txtTotal.setFont(new Font("Arial", Font.BOLD, 24));
        txtTotal.setForeground(new Color(0, 128, 0)); // Verde
    }
    
    
    private boolean validarDatos() {
        if (muebleSeleccionado == null) {
            Validaciones.mostrarAdvertencia("Primero busque un mueble");
            txtCodigoMueble.requestFocus();
            return false;
        }
        
        if (Validaciones.campoVacio(txtCliente)) {
            Validaciones.mostrarAdvertencia("Ingrese el nombre del cliente");
            txtCliente.requestFocus();
            return false;
        }
        
        if (Validaciones.campoVacio(txtTipoPago)) {
            Validaciones.mostrarAdvertencia("Ingrese el tipo de pago");
            txtTipoPago.requestFocus();
            return false;
        }
        
        return true;
    }
     private void registrarVenta() {
        if (!validarDatos()) return;
        
        int cantidad = (int) spnCantidad.getValue();
        double precioUnitario = muebleSeleccionado.getPrecio();
        double total = cantidad * precioUnitario;
        
        // Mostrar confirmación
        String mensaje = String.format(
            "¿Confirmar venta?\n\n" +
            "Mueble: %s\n" +
            "Cliente: %s\n" +
            "Cantidad: %d\n" +
            "Precio Unitario: Q%.2f\n" +
            "Tipo Pago: %s\n" +
            "──────────────────────\n" +
            "TOTAL: Q%.2f",
            muebleSeleccionado.getNombreMueble(),
            txtCliente.getText(),
            cantidad,
            precioUnitario,
            txtTipoPago.getText(),
            total
        );
           
     boolean confirmar = Validaciones.confirmar(mensaje);
        if (!confirmar) return;
        
        // Crear objeto Venta
        Venta venta = new Venta();
        venta.setIdUsuario(usuarioActual.getIdUsuario());
        venta.setIdMueble(muebleSeleccionado.getIdMueble());
        venta.setClienteNombre(txtCliente.getText().trim());
        venta.setClienteTelefono(txtTelefono.getText().trim());
        venta.setCantidad(cantidad);
        venta.setPrecioTotal(total);
        venta.setTipoPago(txtTipoPago.getText().trim());
        
        // Registrar venta (esto también actualiza el stock automáticamente)
        boolean resultado = controladorVenta.registrarVenta(venta);
        
        if (resultado) {
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(
                this,
                String.format(
                    "¡Venta registrada exitosamente!\n\n" +
                    "Cliente: %s\n" +
                    "Mueble: %s\n" +
                    "Cantidad: %d\n" +
                    "Tipo Pago: %s\n" +
                    "──────────────────────\n" +
                    "TOTAL: Q%.2f\n\n" +
                    "El stock se ha actualizado automáticamente.",
                    txtCliente.getText(),
                    muebleSeleccionado.getNombreMueble(),
                    cantidad,
                    txtTipoPago.getText(),
                    total
                ),
                "Venta Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            limpiarFormulario();
        } else {
            Validaciones.mostrarError(
                "Error al registrar la venta.\n" +
                "Verifique que haya stock suficiente."
            );
        }
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
        jLabel3 = new javax.swing.JLabel();
        txtCodigoMueble = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNombreMueble = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioUnitario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStockDisponible = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        spnCantidad = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtTipoPago = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnCalcular = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 0, 36)); // NOI18N
        jLabel1.setText("Ventas");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Historic", 0, 24)); // NOI18N
        jLabel2.setText("Información del Mueble : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Código Mueble :");

        btnBuscar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\115695_magnifying glass_zoom_find_search_icon.png")); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Mueble");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Precio Unitario");

        txtPrecioUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioUnitarioActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Stock");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreMueble, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigoMueble, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscar))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCodigoMueble, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtNombreMueble, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 102, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI Historic", 0, 24)); // NOI18N
        jLabel7.setText("Cliente");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Nombre Cliente ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Telefono ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliente)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Cantidad :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Tipo de Pago :");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Total a Pagar :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(250, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));

        btnCalcular.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\9110956_calculator_math_icon.png")); // NOI18N
        btnCalcular.setText("Calcular ");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnRegistrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\8665086_cash_register_icon.png")); // NOI18N
        btnRegistrar.setText("Registrar Venta");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\9057100_erase_icon.png")); // NOI18N
        btnLimpiar.setText("Limpiar ");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnVolver.setIcon(new javax.swing.ImageIcon("C:\\Users\\emanu\\OneDrive\\Escritorio\\ICONOS PROYECTO DB\\2931185_door_enter_exit_leave_out_icon.png")); // NOI18N
        btnVolver.setText("Regresar ");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnCalcular)
                .addGap(73, 73, 73)
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(btnLimpiar)
                .addGap(82, 82, 82)
                .addComponent(btnVolver)
                .addGap(38, 38, 38))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcular)
                    .addComponent(btnRegistrar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnVolver))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
  calcularTotal();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        registrarVenta();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
    FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioActual);
        menu.setVisible(true);
        this.dispose();       
        
// TODO add your handling code here:
        
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarMueble();
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
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCodigoMueble;
    private javax.swing.JTextField txtNombreMueble;
    private javax.swing.JTextField txtPrecioUnitario;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoPago;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}


package Inicio;

import Clientess.ImagenTabla;
import Clientess.LimpiarTexto;
import Peliculas.Peliculas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CarritoPeliculas extends javax.swing.JFrame {
     LimpiarTexto lt = new LimpiarTexto();
    
    private String ruta_txt = "Peliculas.txt";
    Peliculas peli;
    MetodoPelicula metpel;
    int clic_tabla;
    public CarritoPeliculas() {
        initComponents();
        metpel= new MetodoPelicula();
        
        try{
            cargapeliculas();
            listarPeliculas();
        }catch(Exception ex){
            mensaje("No existe el archivo Cliente");
        }
    }
    
    public void cargapeliculas(){
        File ruta = new File(ruta_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                peli = new Peliculas();
                peli.setIdpeli(Integer.parseInt(st.nextToken()));
                peli.setTitulo(st.nextToken());
                peli.setDirector((st.nextToken()));
                peli.setActor(st.nextToken());
                peli.setClasificacion(st.nextToken());
                peli.setPrecve(Double.parseDouble(st.nextToken()));
                peli.setPrecren(Double.parseDouble(st.nextToken()));
                peli.setCantidad(Integer.parseInt(st.nextToken()));
                metpel.agregarPelicula(peli);
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar el archivo pelicula: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
     public void grabarpeliculas(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);
            for(int i = 0; i < metpel.cantidadIDPel(); i++){
                peli= metpel.obtenerPelicula(i);
                pw.println(String.valueOf(","+peli.getIdpeli()+","+peli.getTitulo()+","+peli.getDirector()+","+peli.getActor()+","+peli.getClasificacion()+
                        ","+peli.getPrecve()+","+peli.getPrecren()+","+peli.getCantidad()));
            }
             pw.close();
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
     
   public void ingresarPelicula(File ruta){
        try{
            if(leeridpel() == -666)mensaje("Ingresar id de la pelicula ");
            else if(leertitulo() == null)mensaje("Ingresar Titulo de la Pelicula");
             else if(leerdirector() == null)mensaje("Ingresar Director");
            else if(leeractor() == null)mensaje("Ingresar Actor");
            else if(leerclasificacion() == null)mensaje("Ingresar Clsificación");
             if(leeprecv() == -666)mensaje("Ingresar precio de venta ");
              if(leeprecr() == -666)mensaje("Ingresar precio de renta ");
               if(leercan() == -666)mensaje("Ingresar cantidad ");
            else{
                peli= new Peliculas(leeridpel(),leertitulo(),leerdirector(),leeractor(),leerclasificacion(),leeprecv(),leeprecr(),leercan());
                if(metpel.buscarIDPel(peli.getIdpeli())!= -1)mensaje("Este id de la pelicula ya existe");
                else metpel.agregarPelicula(peli);
                
                grabarpeliculas();
                listarPeliculas();
                lt.limpiar_texto(panel); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
     public void modificarPelicula(File ruta){
        try{
         if(leeridpel() == -666)mensaje("Ingresar id de la pelicula ");
            else if(leertitulo() == null)mensaje("Ingresar Titulo de la Pelicula");
             else if(leerdirector() == null)mensaje("Ingresar Director");
            else if(leeractor() == null)mensaje("Ingresar Actor");
            else if(leerclasificacion() == null)mensaje("Ingresar Clsificación");
               if(leeprecv() == -666)mensaje("Ingresar precio de venta ");
              if(leeprecr() == -666)mensaje("Ingresar precio de renta ");
               if(leercan() == -666)mensaje("Ingresar cantidad ");
            else{
                int ids = metpel.buscarIDPel(leeridpel());
                peli = new Peliculas(leeridpel(),leertitulo(),leerdirector(),leeractor(),leerclasificacion(),leeprecv(),leeprecr(),leercan());
                if(ids == -1)metpel.agregarPelicula(peli);
                else metpel.modificarPelicula(ids,peli);
                
                grabarpeliculas();
                listarPeliculas();
                lt.limpiar_texto(panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
     }

public void eliminarPeliculas(){
        try{
            if(leeridpel() == -666) mensaje("Ingresar id");
            
            else{
                int ids = metpel.buscarIDPel(leeridpel());
                if(ids == -1) mensaje("el id del la pelicula existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                       metpel.eliminarPelicula(ids);
                        
                        grabarpeliculas();
                        listarPeliculas();
                        lt.limpiar_texto(panel);
                    }
                }
}
 }catch(Exception ex){
            mensaje(ex.getMessage());
        }
}

public void listarPeliculas(){
      
    DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("IDPelicula");
        dt.addColumn("Titulo");
        dt.addColumn("Director");
        dt.addColumn("Actor");
        dt.addColumn("Clasificación");
        dt.addColumn("Precio de venta");
        dt.addColumn("Precio de renta");
        dt.addColumn("Cantidad");
        dt.addColumn("Subtotal");
        
        
        tabla.setDefaultRenderer(Object.class, new ImagenTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < metpel.cantidadIDPel(); i++){
            peli = metpel.obtenerPelicula(i);
            fila[0] = peli.getIdpeli();
            fila[1] = peli.getTitulo();
            fila[2] = peli.getDirector();
            fila[3] = peli.getActor();
            fila[4] = peli.getClasificacion();
            fila[5]= peli.getPrecve();
            fila[6]=peli.getPrecren();
            fila[7]=peli.getCantidad();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }
    public int leeridpel(){
        try{
            int id= Integer.parseInt(txtidpel.getText().trim());
            return id;
        }catch(Exception ex){
            return -666;
        }
    }
    
     public double leeprecv(){
        try{
            double venta= Double.parseDouble(txtventa2.getText().trim());
            return venta;
        }catch(Exception ex){
            return -666;
        }
    }
        public double leeprecr(){
        try{
            double renta= Double.parseDouble(txtrenta.getText().trim());
            return renta;
        }catch(Exception ex){
            return -666;
        }
    }
        
       public int leercan(){
        try{
            int cantidad= Integer.parseInt(txtidpel.getText().trim());
            return cantidad;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leertitulo(){
        try{
            String titulo = txtitulo.getText().trim().replace(" ", " ");
            return titulo;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerdirector(){
        try{
            String director = txtdirector.getText().trim().replace(" ", " ");
            return director;
        }catch(Exception ex){
            return null;
        }
    }
    public String leeractor(){
        try{
            String actor = txtactor.getText().trim().replace(" ", " ");
            return actor;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerclasificacion(){
        try{
            String clasificacion = txtclasificacion.getText().trim().replace(" ", " ");
            return clasificacion;
        }catch(Exception ex){
            return null;
        }
    }
    
    
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
     
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtidpel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtitulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtdirector = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtclasificacion = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtrenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_eliminarpel = new javax.swing.JButton();
        btn_modifica = new javax.swing.JButton();
        btn_calculo = new javax.swing.JButton();
        txtsubtotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtiva = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtdescuento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtotal = new javax.swing.JTextField();
        txtpago = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtcambio = new javax.swing.JTextField();
        btn_guarda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        Menu = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        panel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtidpel1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtitulo1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtdirector1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtactor1 = new javax.swing.JTextField();
        txtventa1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtrenta1 = new javax.swing.JTextField();
        checkrenta1 = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        checkventa1 = new javax.swing.JCheckBox();
        btn_eliminarpel1 = new javax.swing.JButton();
        btn_modifica1 = new javax.swing.JButton();
        btn_calculo1 = new javax.swing.JButton();
        txtsubtotal1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtiva1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtdescuento1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtotal1 = new javax.swing.JTextField();
        txtpago1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtcambio1 = new javax.swing.JTextField();
        btn_guarda1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        Menu1 = new javax.swing.JButton();
        btnlimpiar1 = new javax.swing.JButton();
        txtRuta1 = new javax.swing.JTextField();
        txtactor = new javax.swing.JTextField();
        txtventa2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);

        panel.setBackground(java.awt.SystemColor.activeCaption);
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setText("Ingreso al carrito de peliculas");
        panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 240, 42));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setText("ID Pelicula");
        panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 77, -1, -1));

        txtidpel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpelActionPerformed(evt);
            }
        });
        txtidpel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidpelKeyTyped(evt);
            }
        });
        panel.add(txtidpel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 75, 100, -1));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setText("Titulo:");
        panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 113, -1, -1));

        txtitulo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtituloActionPerformed(evt);
            }
        });
        txtitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtituloKeyTyped(evt);
            }
        });
        panel.add(txtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 111, 227, -1));

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setText("Director");
        panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 149, -1, -1));

        txtdirector.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtdirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdirectorActionPerformed(evt);
            }
        });
        txtdirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdirectorKeyTyped(evt);
            }
        });
        panel.add(txtdirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 147, 227, 30));

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setText("Actor principal");
        panel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 192, -1, -1));

        txtclasificacion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtclasificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtclasificacionKeyTyped(evt);
            }
        });
        panel.add(txtclasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 227, -1));

        txtcantidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidadKeyTyped(evt);
            }
        });
        panel.add(txtcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 50, -1));

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setText("Precio Venta");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 281, -1, -1));

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setText("Precio Renta");
        panel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 324, -1, -1));

        txtrenta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtrenta.setText("$");
        txtrenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrentaKeyTyped(evt);
            }
        });
        panel.add(txtrenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 324, 110, -1));

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setText("Clasificación");
        panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 239, -1, -1));

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel11.setText("Cantidad");
        panel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, -1, -1));

        btn_eliminarpel.setText("Eliminar");
        btn_eliminarpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarpelActionPerformed(evt);
            }
        });
        panel.add(btn_eliminarpel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 89, 43));

        btn_modifica.setText("Modificar");
        btn_modifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificaActionPerformed(evt);
            }
        });
        panel.add(btn_modifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 89, 43));

        btn_calculo.setText("Calcular Total");
        btn_calculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculoActionPerformed(evt);
            }
        });
        panel.add(btn_calculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 110, 110, 52));

        txtsubtotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsubtotal.setText("$");
        txtsubtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotalKeyTyped(evt);
            }
        });
        panel.add(txtsubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 39, 90, -1));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setText("SubTotal:");
        panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 39, -1, -1));

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setText("Iva:");
        panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 79, -1, -1));

        txtiva.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtivaKeyTyped(evt);
            }
        });
        panel.add(txtiva, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 79, 50, -1));

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel12.setText("Descuento:");
        panel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 119, -1, -1));

        txtdescuento.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel.add(txtdescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 119, 40, -1));

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel13.setText("Total:");
        panel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 162, -1, -1));

        txtotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtotal.setText("$");
        txtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtotalActionPerformed(evt);
            }
        });
        panel.add(txtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 162, 100, -1));

        txtpago.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpago.setText("$");
        panel.add(txtpago, new org.netbeans.lib.awtextra.AbsoluteConstraints(514, 202, 100, -1));

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel14.setText("Pago:");
        panel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 202, -1, -1));

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel15.setText("Cambio:");
        panel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 242, -1, -1));

        txtcambio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcambio.setText("$");
        panel.add(txtcambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 242, 100, -1));

        btn_guarda.setText("Guardar");
        btn_guarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardaActionPerformed(evt);
            }
        });
        panel.add(btn_guarda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 89, 43));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "ID Pelicula", "Titulo", "Director", "Actor Principal", "Cantidad", "Precio Venta", "Precio Renta", "Total"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 916, 100));

        Menu.setText("Menú");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });
        panel.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 569, 89, 43));

        btnlimpiar.setText("Limpiar");
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });
        panel.add(btnlimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, 89, 43));

        txtRuta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutaActionPerformed(evt);
            }
        });
        txtRuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutaKeyTyped(evt);
            }
        });
        panel.add(txtRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 300, 100, -1));

        panel1.setBackground(java.awt.SystemColor.activeCaption);
        panel1.setLayout(null);

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel16.setText("Ingreso al carrito de peliculas");
        panel1.add(jLabel16);
        jLabel16.setBounds(30, 20, 240, 42);

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel17.setText("ID Pelicula");
        panel1.add(jLabel17);
        jLabel17.setBounds(30, 77, 73, 21);

        txtidpel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidpel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpel1ActionPerformed(evt);
            }
        });
        txtidpel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidpel1KeyTyped(evt);
            }
        });
        panel1.add(txtidpel1);
        txtidpel1.setBounds(150, 75, 100, 25);

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel18.setText("Titulo:");
        panel1.add(jLabel18);
        jLabel18.setBounds(30, 113, 45, 21);

        txtitulo1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtitulo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtitulo1KeyTyped(evt);
            }
        });
        panel1.add(txtitulo1);
        txtitulo1.setBounds(150, 111, 227, 25);

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel19.setText("Director");
        panel1.add(jLabel19);
        jLabel19.setBounds(30, 149, 57, 21);

        txtdirector1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtdirector1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdirector1KeyTyped(evt);
            }
        });
        panel1.add(txtdirector1);
        txtdirector1.setBounds(150, 147, 227, 25);

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel20.setText("Actor principal");
        panel1.add(jLabel20);
        jLabel20.setBounds(20, 192, 99, 21);

        txtactor1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel1.add(txtactor1);
        txtactor1.setBounds(150, 190, 227, 25);

        txtventa1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtventa1.setText("$");
        panel1.add(txtventa1);
        txtventa1.setBounds(136, 279, 110, 25);

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel21.setText("Precio Venta");
        panel1.add(jLabel21);
        jLabel21.setBounds(20, 281, 85, 21);

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel22.setText("Precio Renta");
        panel1.add(jLabel22);
        jLabel22.setBounds(20, 324, 85, 21);

        txtrenta1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtrenta1.setText("$");
        panel1.add(txtrenta1);
        txtrenta1.setBounds(136, 324, 110, 25);

        checkrenta1.setText("Renta");
        checkrenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkrenta1ActionPerformed(evt);
            }
        });
        panel1.add(checkrenta1);
        checkrenta1.setBounds(290, 50, 67, 23);

        jLabel23.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel23.setText("Clasificación");
        panel1.add(jLabel23);
        jLabel23.setBounds(20, 239, 83, 21);

        jLabel24.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel24.setText("Cantidad");
        panel1.add(jLabel24);
        jLabel24.setBounds(270, 280, 59, 21);

        checkventa1.setText("Ventas");
        checkventa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkventa1ActionPerformed(evt);
            }
        });
        panel1.add(checkventa1);
        checkventa1.setBounds(290, 80, 67, 23);

        btn_eliminarpel1.setText("Eliminar");
        btn_eliminarpel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarpel1ActionPerformed(evt);
            }
        });
        panel1.add(btn_eliminarpel1);
        btn_eliminarpel1.setBounds(220, 370, 89, 43);

        btn_modifica1.setText("Modificar");
        btn_modifica1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifica1ActionPerformed(evt);
            }
        });
        panel1.add(btn_modifica1);
        btn_modifica1.setBounds(120, 370, 89, 43);

        btn_calculo1.setText("Calcular Total");
        btn_calculo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculo1ActionPerformed(evt);
            }
        });
        panel1.add(btn_calculo1);
        btn_calculo1.setBounds(751, 110, 110, 52);

        txtsubtotal1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsubtotal1.setText("$");
        txtsubtotal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotal1KeyTyped(evt);
            }
        });
        panel1.add(txtsubtotal1);
        txtsubtotal1.setBounds(524, 39, 90, 25);

        jLabel25.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel25.setText("SubTotal:");
        panel1.add(jLabel25);
        jLabel25.setBounds(454, 39, 67, 21);

        jLabel26.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel26.setText("Iva:");
        panel1.add(jLabel26);
        jLabel26.setBounds(454, 79, 29, 21);

        txtiva1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtiva1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtiva1KeyTyped(evt);
            }
        });
        panel1.add(txtiva1);
        txtiva1.setBounds(524, 79, 50, 25);

        jLabel27.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel27.setText("Descuento:");
        panel1.add(jLabel27);
        jLabel27.setBounds(454, 119, 74, 21);

        txtdescuento1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel1.add(txtdescuento1);
        txtdescuento1.setBounds(534, 119, 40, 25);

        jLabel28.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel28.setText("Total:");
        panel1.add(jLabel28);
        jLabel28.setBounds(454, 162, 42, 21);

        txtotal1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtotal1.setText("$");
        txtotal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtotal1ActionPerformed(evt);
            }
        });
        panel1.add(txtotal1);
        txtotal1.setBounds(514, 162, 100, 25);

        txtpago1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpago1.setText("$");
        panel1.add(txtpago1);
        txtpago1.setBounds(514, 202, 100, 25);

        jLabel29.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel29.setText("Pago:");
        panel1.add(jLabel29);
        jLabel29.setBounds(454, 202, 35, 21);

        jLabel30.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel30.setText("Cambio:");
        panel1.add(jLabel30);
        jLabel30.setBounds(454, 242, 53, 21);

        txtcambio1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcambio1.setText("$");
        panel1.add(txtcambio1);
        txtcambio1.setBounds(524, 242, 100, 25);

        btn_guarda1.setText("Guardar");
        btn_guarda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guarda1ActionPerformed(evt);
            }
        });
        panel1.add(btn_guarda1);
        btn_guarda1.setBounds(10, 370, 89, 43);

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "ID Pelicula", "Titulo", "Director", "Actor Principal", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane2.setViewportView(tabla1);

        panel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 460, 916, 91);

        Menu1.setText("Menú");
        Menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu1ActionPerformed(evt);
            }
        });
        panel1.add(Menu1);
        Menu1.setBounds(187, 569, 89, 43);

        btnlimpiar1.setText("Limpiar");
        btnlimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar1ActionPerformed(evt);
            }
        });
        panel1.add(btnlimpiar1);
        btnlimpiar1.setBounds(340, 370, 89, 43);

        txtRuta1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtRuta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRuta1ActionPerformed(evt);
            }
        });
        txtRuta1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRuta1KeyTyped(evt);
            }
        });
        panel1.add(txtRuta1);
        txtRuta1.setBounds(524, 300, 100, 25);

        panel.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        txtactor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtactor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtactorKeyTyped(evt);
            }
        });
        panel.add(txtactor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 227, -1));

        txtventa2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtventa2.setText("$");
        txtventa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtventa2KeyTyped(evt);
            }
        });
        panel.add(txtventa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 279, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed
        Menus men= new Menus();
        men.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_MenuActionPerformed

    private void txtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtotalActionPerformed

    private void txtivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtivaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtivaKeyTyped

    private void txtsubtotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotalKeyTyped

    private void btn_calculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculoActionPerformed

    }//GEN-LAST:event_btn_calculoActionPerformed

    private void btn_eliminarpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarpelActionPerformed

      this.eliminarPeliculas();
    }//GEN-LAST:event_btn_eliminarpelActionPerformed

    private void txtdirectorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdirectorKeyTyped
char c= evt.getKeyChar();
 if(Character.isDigit(c)){
     getToolkit().beep();
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar letras");
     txtdirector.setCursor(null);
 }
    }//GEN-LAST:event_txtdirectorKeyTyped

    private void txtituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtituloKeyTyped

    }//GEN-LAST:event_txtituloKeyTyped

    private void txtidpelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidpelKeyTyped
         char n= evt.getKeyChar();
 if(Character.isLetter(n)){
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar numeros");
     txtidpel.setCursor(null);
 }
    }//GEN-LAST:event_txtidpelKeyTyped

    private void txtidpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpelActionPerformed

    }//GEN-LAST:event_txtidpelActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        LimpiarTexto lp = new LimpiarTexto();
        lp.limpiar_texto(panel);
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btn_modificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificaActionPerformed
         File ruta = new File(txtRuta.getText());
        this.modificarPelicula(ruta);
    }//GEN-LAST:event_btn_modificaActionPerformed

    private void txtRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaActionPerformed

    private void txtRutaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaKeyTyped

    private void btn_guardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardaActionPerformed
         File ruta = new File(txtRuta.getText());
        this.ingresarPelicula(ruta);
    }//GEN-LAST:event_btn_guardaActionPerformed

    private void txtidpel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpel1ActionPerformed

    private void txtidpel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidpel1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpel1KeyTyped

    private void txtitulo1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitulo1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtitulo1KeyTyped

    private void txtdirector1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdirector1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdirector1KeyTyped

    private void checkrenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkrenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkrenta1ActionPerformed

    private void checkventa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkventa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkventa1ActionPerformed

    private void btn_eliminarpel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarpel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarpel1ActionPerformed

    private void btn_modifica1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifica1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modifica1ActionPerformed

    private void btn_calculo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculo1ActionPerformed

    }//GEN-LAST:event_btn_calculo1ActionPerformed

    private void txtsubtotal1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotal1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotal1KeyTyped

    private void txtiva1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtiva1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtiva1KeyTyped

    private void txtotal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtotal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtotal1ActionPerformed

    private void btn_guarda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guarda1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_guarda1ActionPerformed

    private void Menu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu1ActionPerformed

    private void btnlimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnlimpiar1ActionPerformed

    private void txtRuta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRuta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRuta1ActionPerformed

    private void txtRuta1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuta1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRuta1KeyTyped

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
   clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int id = (int)tabla.getValueAt(clic_tabla, 0);
        String titulo = ""+tabla.getValueAt(clic_tabla,1);
        String director = ""+tabla.getValueAt(clic_tabla,2);
        String actor = ""+tabla.getValueAt(clic_tabla,3);
        String clasificacion= ""+tabla.getValueAt(clic_tabla,4);
        double precioventa =(double)tabla.getValueAt(clic_tabla, 5);
        double preciorenta =(double)tabla.getValueAt(clic_tabla, 6);
        int cantidad= (int)tabla.getValueAt(clic_tabla, 7);

        txtidpel.setText(String.valueOf(id));
        txtitulo.setText(titulo);
        txtdirector.setText(director);
        txtactor.setText(actor);
        txtclasificacion.setText(clasificacion);
        txtventa2.setText(String.valueOf(precioventa));
        txtrenta.setText(String.valueOf(preciorenta));
        txtcantidad.setText(String.valueOf(cantidad));
    }//GEN-LAST:event_tablaMouseClicked

    private void txtdirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdirectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdirectorActionPerformed

    private void txtituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtituloActionPerformed

    private void txtactorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtactorKeyTyped
       char c= evt.getKeyChar();
 if(Character.isDigit(c)){
     getToolkit().beep();
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar letras");
     txtactor.setCursor(null);
    }//GEN-LAST:event_txtactorKeyTyped
    }
    private void txtclasificacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclasificacionKeyTyped
       char c= evt.getKeyChar();
 if(Character.isDigit(c)){
     getToolkit().beep();
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar letras");
     txtclasificacion.setCursor(null);
    }//GEN-LAST:event_txtclasificacionKeyTyped
    }
    private void txtventa2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtventa2KeyTyped
       
 
    }//GEN-LAST:event_txtventa2KeyTyped

    private void txtrentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrentaKeyTyped
           
    }//GEN-LAST:event_txtrentaKeyTyped

    private void txtcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyTyped
                 char n= evt.getKeyChar();
 if(Character.isLetter(n)){
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar numeros");
     txtcantidad.setCursor(null);
 }
    }//GEN-LAST:event_txtcantidadKeyTyped

   
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
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarritoPeliculas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Menu;
    private javax.swing.JButton Menu1;
    private javax.swing.JButton btn_calculo;
    private javax.swing.JButton btn_calculo1;
    private javax.swing.JButton btn_eliminarpel;
    private javax.swing.JButton btn_eliminarpel1;
    private javax.swing.JButton btn_guarda;
    private javax.swing.JButton btn_guarda1;
    private javax.swing.JButton btn_modifica;
    private javax.swing.JButton btn_modifica1;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnlimpiar1;
    private javax.swing.JCheckBox checkrenta1;
    private javax.swing.JCheckBox checkventa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tabla1;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextField txtRuta1;
    private javax.swing.JTextField txtactor;
    private javax.swing.JTextField txtactor1;
    private javax.swing.JTextField txtcambio;
    private javax.swing.JTextField txtcambio1;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtclasificacion;
    private javax.swing.JTextField txtdescuento;
    private javax.swing.JTextField txtdescuento1;
    private javax.swing.JTextField txtdirector;
    private javax.swing.JTextField txtdirector1;
    private javax.swing.JTextField txtidpel;
    private javax.swing.JTextField txtidpel1;
    private javax.swing.JTextField txtitulo;
    private javax.swing.JTextField txtitulo1;
    private javax.swing.JTextField txtiva;
    private javax.swing.JTextField txtiva1;
    private javax.swing.JTextField txtotal;
    private javax.swing.JTextField txtotal1;
    private javax.swing.JTextField txtpago;
    private javax.swing.JTextField txtpago1;
    private javax.swing.JTextField txtrenta;
    private javax.swing.JTextField txtrenta1;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txtsubtotal1;
    private javax.swing.JTextField txtventa1;
    private javax.swing.JTextField txtventa2;
    // End of variables declaration//GEN-END:variables
}

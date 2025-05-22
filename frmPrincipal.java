import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class frmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = true;
    boolean ALLOW_COLUMN_SELECTION = false;
    boolean ALLOW_ROW_SELECTION = true;

    private dataBase myDataBase;

	private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
	private JLabel title;
	JMenuItem menuVerReferencias;
	
    Object[][] data;
    String[] columnNames;
    
    public frmPrincipal(final dataBase myDataBase) {
    	
    	super("NAVY VERSION 1.0");

		this.myDataBase = myDataBase;

		data = myDataBase.listaTabla("Casillas",4,null);
		String[] columnNames = {"Id", "Tipo casilla", "Municipio", "Votos nulos"};
		
		JPanel panelLogo = new JPanel(new BorderLayout());
		ImageIcon icono = gral.crearIcono("acerca.gif");
        JLabel logo = new JLabel(icono, JLabel.CENTER);
		panelLogo.setBackground(Color.white);
		panelLogo.add(logo,BorderLayout.CENTER);
		getContentPane().add(panelLogo, BorderLayout.NORTH);

		JMenuBar menu = new JMenuBar();
		JMenu menuArchivo = new JMenu("Archivo");
			JMenuItem menuArchivoSalir = new JMenuItem("Salir");
		
		JMenu menuVer = new JMenu("Ver");
			JMenuItem menuVerActualizar = new JMenuItem("Actualizar tabla");
			menuVer.add(menuVerActualizar);
			
		JMenu menuBase = new JMenu("Base de datos");
			JMenu menuBaseAgregar = new JMenu("Agregar una nueva");
				JMenuItem menuBaseAgregarCasilla = new JMenuItem("Casilla");
				JMenuItem menuBaseAgregarCaptura = new JMenuItem("Captura");
			JMenu menuBaseEliminar = new JMenu("Eliminar");
				JMenuItem menuBaseEliminarCasilla = new JMenuItem("Casilla");
				JMenuItem menuBaseEliminarCaptura = new JMenuItem("Captura");
			JMenuItem menuBaseVotos = new JMenuItem("Sumar votos");
			JMenuItem menuBaseVotosNulos = new JMenuItem("Actualizar conteo de votos nulos");
			
		JMenu menuReporte = new JMenu("Reporte");
			JMenuItem menuReporteVer = new JMenuItem("Abrir ventana del reporte");
			
		JMenu menuAyuda = new JMenu("Ayuda");
			JMenuItem menuAyudaAcercaDe = new JMenuItem("Acerca de Navy");
		
		menuArchivo.add(menuArchivoSalir);
		menuBaseAgregar.add(menuBaseAgregarCasilla);
		menuBaseAgregar.add(menuBaseAgregarCaptura);
		menuBase.add(menuBaseAgregar);
		menuBaseEliminar.add(menuBaseEliminarCasilla);
		menuBaseEliminar.add(menuBaseEliminarCaptura);
		menuBase.add(menuBaseAgregar);
		menuBase.add(menuBaseEliminar);
		menuBase.addSeparator();
		menuBase.add(menuBaseVotos);
		menuBase.addSeparator();
		menuBase.add(menuBaseVotosNulos);
		menu.add(menuArchivo);
		menu.add(menuVer);
		menu.add(menuBase);
		menuReporte.add(menuReporteVer);
		menu.add(menuReporte);
		menuAyuda.add(menuAyudaAcercaDe);
		menu.add(menuAyuda);
				
		menuArchivoSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});

		menuVerActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizar();
			}
		});

		menuBaseEliminarCasilla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaciones.eliminarCasilla(frmPrincipal.this, myDataBase);
			}
		});
		
		menuBaseEliminarCaptura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaciones.eliminarCaptura(frmPrincipal.this, myDataBase);
			}
		});
		
		menuBaseAgregarCasilla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaciones.agregarCasilla(frmPrincipal.this, myDataBase);
			}
		});
		
		menuBaseAgregarCaptura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaciones.agregarCaptura(frmPrincipal.this, myDataBase);
			}
		});
		
		menuReporteVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new grafico(frmPrincipal.this, myDataBase);
			}
		});
		
		menuBaseVotos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clave = JOptionPane.showInputDialog(frmPrincipal.this,"Introduzca que casilla desea sumarle votos", "Introduza id.", JOptionPane.PLAIN_MESSAGE);
				if(clave != null)
					new sumar_votos(frmPrincipal.this,myDataBase,clave);
			}
		});
		
		menuBaseVotosNulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaciones.agregarVotosNulos(frmPrincipal.this, myDataBase);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		
		menuAyudaAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    java.net.URL url_s = frmPrincipal.class.getResource("acerca.gif");
				String bienvenida = "<html><body><img src=" + url_s + "><br><font style='font-family:Verdana;font-size:14pt'><i>Bienvenido al Sistema de analisis de votos Navy.</i></font>"+
									"<br><br><font style='font-family:Verdana;font-size:9pt;color:gray;font-weight:100'>Programa desarrollado por:<br><b>Aldo Montero<br> "+
									"<br><br>Version: 1.0<br><br>\u00a9 2009</body></html>";
		
				JOptionPane.showMessageDialog(frmPrincipal.this, bienvenida, "Acerca de...", JOptionPane.PLAIN_MESSAGE);
			}
		}); 

		scrollPane = new JScrollPane(tabla(data, columnNames));
		
		JPanel contenedorTabla = new JPanel(new BorderLayout());

		title = new JLabel(" Ventana principal", JLabel.LEFT);
		title.setFont(new Font("Times New Roman", Font.ITALIC, 23));
		
		title.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		contenedorTabla.add(title,BorderLayout.NORTH);
		contenedorTabla.add(scrollPane,BorderLayout.CENTER);

		getContentPane().add(contenedorTabla, BorderLayout.CENTER);
		verValores();
		
		setJMenuBar(menu);
		setSize(new Dimension(700, 550));
		setLocationRelativeTo(null);
		setResizable(true);
    }

	
	private JTable tabla(Object datas[][], String columnNames[]){
		
		tableModel = new DefaultTableModel(datas, columnNames);
		table = new JTable(tableModel){

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){ return false; }
		};
		
		table.setPreferredScrollableViewportSize(new Dimension(100, 70));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(200);

		return table;
	}
		
	// Cierra conexion con la base de datos
	private void salir(){
		myDataBase.cerrarDB();
		System.exit(0);
	}
	

	public void actualizar(){
		mostrarTabla();
		verValores();
	}

	public void mostrarTabla(){

		data = myDataBase.listaTabla("Casillas",4,null);
		String[] columnNames = {"Id", "Tipo casilla", "Municipio", "Votos nulos"};
		
		tableModel.setDataVector(data, columnNames);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
	}

    private void verValores(){
    	
    	for(int n = 0; n < table.getRowCount(); n++){
    		String obtenerCandidato = myDataBase.obtenerValorCelda("Municipios","descripcion","pk_no="+table.getValueAt(n,2));
    		table.setValueAt(obtenerCandidato,n,2);
    	}
    	
		for(int n = 0; n < table.getRowCount(); n++){
    		String obtenerCandidato = myDataBase.obtenerValorCelda("tipoCasilla","tipoCasilla","pk_tipo="+table.getValueAt(n,1));
    		table.setValueAt(obtenerCandidato,n,1);
    	}
	}
}
	
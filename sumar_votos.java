import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class sumar_votos extends JDialog {

	private static final long serialVersionUID = 1L;
	
	boolean DEBUG = true;
    boolean ALLOW_COLUMN_SELECTION = false;
    boolean ALLOW_ROW_SELECTION = false;

	Object[] obtenerCandidato;
	
    private dataBase myDataBase;
    private JTable table;
	private JFrame frame = null;
	private String Clave;
	private DefaultTableModel tableModel;
	
    public sumar_votos(JFrame frame, dataBase myDataBase, String Clave) {
		
		super(frame, "Votos de la casilla id " + Clave, false);
		this.frame = frame;
		this.myDataBase = myDataBase;
		this.Clave = Clave;
		
		JMenuBar menu = new JMenuBar();
		JMenu menuControl = new JMenu("Control");
			JMenuItem menuControlActualizarVotos = new JMenuItem("Actualizar votos");
			JMenuItem menuControlActualizarVotosNulos = new JMenuItem("Actualizar votos nulos");
			JMenuItem menuControlEliminar = new JMenuItem("Eliminar partido de esta casilla");
			JMenuItem menuControlActualizar = new JMenuItem("Actualizar tabla");
			
		menuControl.add(menuControlActualizarVotos);
		menuControl.add(menuControlActualizarVotosNulos);
		menuControl.addSeparator();
		menuControl.add(menuControlEliminar);
		menuControl.addSeparator();
		menuControl.add(menuControlActualizar);
		menu.add(menuControl);

		setJMenuBar(menu);
		
		String[] columnNames = {"Id", "Partido", "Seccion", "Casilla", "Votos"};
		Object[][] data = myDataBase.listaTabla("Captura",5,"fk_casilla="+Clave);
		tableModel = new DefaultTableModel(data, columnNames);
		
		table = new JTable(tableModel){ // Para volver no editable

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){ return false; }
		};

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		menuControlEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		
		menuControlActualizarVotos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		
		menuControlActualizarVotosNulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarNulos();
			}
		});
		
		menuControlActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizar();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		setSize(new Dimension(450, 300));
		setLocationRelativeTo(null);
		setResizable(false);
		
		verValores();
		setVisible(true);
    }
    
    public void verValores(){
    	
    	for(int n = 0; n < table.getRowCount(); n++){
    		String obtenerPartido = myDataBase.obtenerValorCelda("Partidos","partido","pk_partido="+table.getValueAt(n,1));
    		table.setValueAt(obtenerPartido,n,1);
    	}
    	
	}
	
	public void agregarNulos(){
		modificaciones.agregarVotosNulos(frame, myDataBase);
	}
	
	public void agregar(){
		modificaciones.agregarVotos(frame, myDataBase);
	}
	
	public void eliminar(){
		modificaciones.eliminarCaptura(frame, myDataBase);
	}
	
	public void actualizar(){

		String[] columnNames = {"Id", "Partido", "Seccion", "Casilla", "Votos"};
		Object[][] data = myDataBase.listaTabla("Captura",5,"fk_casilla="+Clave);
		
		tableModel.setDataVector(data, columnNames);
		verValores();
	}
}
	
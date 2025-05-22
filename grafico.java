import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class grafico extends JDialog{

	private static final long serialVersionUID = 1L;

	private ChartPanel panel;
    private JFrame frm = null;
    
	public grafico(JFrame frm, dataBase myDataBase){
		
		super(frm,"Reporte:",true);
		this.frm = frm;

		JMenuBar menu = new JMenuBar();
		JMenu menuOpciones = new JMenu("Opciones");
			JMenuItem menuOpcionesExportar = new JMenuItem("Exportar...");

		menuOpciones.add(menuOpcionesExportar);
		menu.add(menuOpciones);

		setJMenuBar(menu);

			menuOpcionesExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarGrafica();
				}
			});
			
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		Object[][] partidos = myDataBase.listaTabla("Partidos",2,null);
		Object[][] info = null;
		String[] columnasTabla = {"Partido","Votos a favor"};
		Object[][] vectorData = new Object[partidos.length+1][2];

		String syntaxSQL = null;
		String getPartido = null;

		int counts = 0;
		Float value = 0F;
		
		for(int n = 0; n < partidos.length; n++){
			
			value = 0F;
			syntaxSQL = "SELECT SUM(voto) FROM Captura WHERE fk_partido=" + (String)partidos[n][0];
			getPartido = myDataBase.obtenerValorCelda("Partidos","partido","pk_partido="+(String)partidos[n][0]);

			info = myDataBase.advancedSQL(syntaxSQL, 1);
			
			try{
			
			value = Float.parseFloat((String)info[0][0]);
			} catch (Exception e){
				value = 0F;
			}
			
			if(value > 0F){
				dataset.setValue(value, getPartido, "Preliminares");

				vectorData[n][0] = getPartido;
				vectorData[n][1] = value;
				counts++;
			} else {
				vectorData[n][0] = getPartido;
				vectorData[n][1] = "0";
			}
		}
		
		syntaxSQL = "SELECT SUM(votos_nulos) FROM Casillas";
		info = myDataBase.advancedSQL(syntaxSQL, 1);
		
		try{
			value = Float.parseFloat((String)info[0][0]);
		} catch(Exception e) {
				value = 0F;
		}
		
		if(value > 0F){
			dataset.setValue(value, "Votos nulos", "Preliminares");
			vectorData[vectorData.length-1][0] = "Votos nulos";
			vectorData[vectorData.length-1][1] = value;
		} else {
			vectorData[vectorData.length-1][0] = "Votos nulos";
			vectorData[vectorData.length-1][1] = 0;
		}
		
		JFreeChart chart = null;
		
		if(counts > 0){
			
			chart = ChartFactory.createBarChart("Reporte", "Votaciones 2009",
		   	"Número de votos totales", dataset, PlotOrientation.VERTICAL, true,
		   	true, false);
			
			JTable tabla = new JTable(vectorData, columnasTabla);
			panel = new ChartPanel(chart);

			setSize(600,400);
			panel.setMouseZoomable(false);
			
			getContentPane().add(panel,BorderLayout.CENTER);
			getContentPane().add(tabla,BorderLayout.EAST);
			
			setResizable(false);
			setLocationRelativeTo(frm);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setVisible(true);
			
		} else {
			JOptionPane.showMessageDialog(frm, "Error al crear la grafica, no existen registros.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void guardarGrafica(){
		try{
			panel.doSaveAs();
		} catch (Exception exceptionError){
			JOptionPane.showMessageDialog(frm,"Ocurrio un error al intentar guardar.","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
	
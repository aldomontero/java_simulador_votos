import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;

class vantanaSplash extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private int frameNumber = -1;
    private Timer timer;
    private JLabel imagen;
    private Label estado;
    private boolean frozen = false;
    private dataBase myDataBase = null;
	frmPrincipal frm = null;
	private JButton iniciar;
	
    @SuppressWarnings({ "deprecation" })
    
	vantanaSplash(int fps, String windowTitle) {
    	
        super(windowTitle);
        int delay = fps;

		iniciar = new JButton("Comenzar");
		iniciar.setEnabled(false);
		ImageIcon icono = gral.crearIcono("fondito.gif");
        imagen = new JLabel(icono, JLabel.CENTER);
        estado = new Label("Espere un momento por favor... Cargando datos...", Label.LEFT);
		
        //Configurar el Timer
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        getContentPane().add(imagen, BorderLayout.CENTER);
        getContentPane().add(estado, BorderLayout.NORTH);
        getContentPane().add(iniciar, BorderLayout.SOUTH);
        
        iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
        		frm.setVisible(true);
			}
		});
		
		setCursor(JFrame.WAIT_CURSOR);
		setUndecorated(false);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
        //Comenzar secuencia
		startAnimation();
        
    }

    //Iniciar secuencia
    public void startAnimation() {
        if (frozen) {
            //Si esta desactivado se ejecuta esta linea
        } else {
            //inicia secuencia
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    //Detiene la animacion
    public void stopAnimation() {
        if (timer.isRunning())
            timer.stop();
    }

    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
        //Avanzada animacion del frame
        frameNumber++;
		
		
        if(frameNumber == 1){
        	myDataBase = new dataBase("ProyectoIFE.mdb");
        } else if(frameNumber == 2){
        	frm = new frmPrincipal(myDataBase);
        } else if(frameNumber == 3){
        	stopAnimation();
        	setCursor(JFrame.DEFAULT_CURSOR);
        	estado.setText("Haga clic en Comenzar para continuar..");
        	iniciar.setEnabled(true);
        }
    }

    public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception x) {
			System.out.println("Error al cambiar el estilo de Windows: " + x);
		}
		
        int fps = 1000;
        new vantanaSplash(fps, " ");
    }
}
	
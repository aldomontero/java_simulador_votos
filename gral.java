import javax.swing.ImageIcon;

public class gral{

	// Retorna una imagen de icono, si es nulo retorna un error en la direccion del archivo. */
    public static ImageIcon crearIcono(String path) {
	java.net.URL imgURL = gral.class.getResource(path);
	if (imgURL != null)
	    return new ImageIcon(imgURL);
	else
	    return null;
    }
}
	
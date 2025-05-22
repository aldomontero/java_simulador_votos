import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class modificaciones{
	
	public static void agregarVotos(Frame frm, dataBase myDataBase){
		
		ImageIcon icono = gral.crearIcono("step_1_2.gif");
		String index = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 1 de 2</h3><p style='width:200px'>"+
						 "Introduzca identificacion de partido en casilla:","Agregar actualizacion de votos", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(index == null)
			return;
		
		icono = gral.crearIcono("step_2_2.gif");
		String nuevoValor = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 2 de 2</h3><p style='width:200px'>"+
						 "Introduzca nuevo conteo:","Agregar actualizacion de votos", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(nuevoValor == null)
			return;
		
		myDataBase.actualizar(frm, "Captura","voto="+nuevoValor, "pk_captura="+index);
	}

	public static void agregarVotosNulos(Frame frm, dataBase myDataBase){
		
		ImageIcon icono = gral.crearIcono("step_1_2.gif");
		String index = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 1 de 2</h3><p style='width:200px'>"+
						 "Introduzca identificacion de la casilla:","Actualizar votos nulos", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(index == null)
			return;
		
		icono = gral.crearIcono("step_2_2.gif");
		String nuevoValor = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 2 de 2</h3><p style='width:200px'>"+
						 "Introduzca nuevo conteo:","Actualizar votos nulos", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(nuevoValor == null)
			return;
		
		myDataBase.actualizar(frm, "Casillas","votos_nulos="+nuevoValor, "pk_casilla="+index);
	}
	
	public static void agregarCaptura(Frame frm, dataBase myDataBase){
		
		Object listaPartidosVal[][] = myDataBase.listaValoresTabla("Partidos");
		Object listaPartidos[] = new Object[listaPartidosVal.length];

		Object listaSeccionesVal[][] = myDataBase.listaValoresTabla("Distritos");
		Object listaSecciones[] = new Object[listaSeccionesVal.length];
		
		int selectPartido = 1;
		int selectSeccion = 1;
		
		for(int n = 0; n < listaPartidos.length; n++)
			listaPartidos[n] = listaPartidosVal[n][1];
		
		for(int n = 0; n < listaSecciones.length; n++)
			listaSecciones[n] = listaSeccionesVal[n][1];
		
		ImageIcon icono = gral.crearIcono("step_1_4.gif");
		Object partido = JOptionPane.showInputDialog(frm,"<html><h3>Paso 1 de 4</h3><p style='width:200px'>"+
						 "Introduzca el partido:","Agregar nueva captura", JOptionPane.PLAIN_MESSAGE,icono,listaPartidos,1);
		
		if(partido == null)
			return;
		
		icono = gral.crearIcono("step_2_4.gif");
		Object seccion = JOptionPane.showInputDialog(frm,"<html><h3>Paso 2 de 4</h3><p style='width:200px'>"+
						 "Introduzca el distrito:","Agregar nueva captura", JOptionPane.PLAIN_MESSAGE,icono,listaSecciones,1);
		
		if(seccion == null)
			return;
		
		icono = gral.crearIcono("step_3_4.gif");
		String casilla = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 3 de 4</h3><p style='width:200px'>"+
						 "Introduzca casilla a la que pertenece","Agregar nueva captura", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(casilla == null)
			return;
		
		icono = gral.crearIcono("step_4_4.gif");
		String votos = (String)JOptionPane.showInputDialog(frm,"<html><h3>Paso 4 de 4</h3><p style='width:200px'>"+
						 "Introduzca votos acumulados","Agregar nueva captura", JOptionPane.PLAIN_MESSAGE, icono, null, "");
		
		if(votos == null)
			return;
		
			for(int n = 0; n < listaPartidos.length; n++)
				if(listaPartidos[n].equals(partido))
					selectPartido = n;
					
			for(int n = 0; n < listaSecciones.length; n++)
				if(listaSecciones[n].equals(seccion))
					selectSeccion = n;
					
		String sp = (String)listaPartidosVal[selectPartido][0];
		String ss = (String)listaSeccionesVal[selectSeccion][0];
		
		myDataBase.nuevoValor(frm,"Captura", sp+ss+casilla+ "," +sp+ "," +ss+ "," +casilla+ "," +votos);
	}
	
	public static void agregarCasilla(Frame frm, dataBase myDataBase){
		
		Object listaTipoCasillaVal[][] = myDataBase.listaValoresTabla("tipoCasilla");
		Object listaTipoCasilla[] = new Object[listaTipoCasillaVal.length];

		Object listaMunicipiosVal[][] = myDataBase.listaValoresTabla("Municipios");
		Object listaMunicipios[] = new Object[listaMunicipiosVal.length];
		
		int selectTipoCasilla = 1;
		int selectMunicipio = 1;
		
		for(int n = 0; n < listaTipoCasilla.length; n++)
			listaTipoCasilla[n] = listaTipoCasillaVal[n][1];
		
		for(int n = 0; n < listaMunicipios.length; n++)
			listaMunicipios[n] = listaMunicipiosVal[n][1];
		
		ImageIcon icono = gral.crearIcono("step_1_4.gif");
		Object index = JOptionPane.showInputDialog(frm,"<html><h3>Paso 1 de 4</h3><p style='width:200px'>"+
					   "Introduzca nueva identificacion:","Agregar nueva Casilla", JOptionPane.PLAIN_MESSAGE,icono,null,0);
		
		if(index == null)
			return;
		
		icono = gral.crearIcono("step_2_4.gif");
		Object tipoCasilla = JOptionPane.showInputDialog(frm,"<html><h3>Paso 2 de 4</h3><p style='width:200px'>"+
					   "Introduzca el tipo de casilla:","Agregar nueva Casilla", JOptionPane.PLAIN_MESSAGE, icono, listaTipoCasilla,1);
		
		if(tipoCasilla == null)
			return;
		
		icono = gral.crearIcono("step_3_4.gif");
		Object municipio = JOptionPane.showInputDialog(frm,"<html><h3>Paso 3 de 4</h3><p style='width:200px'>"+
					   "Introduzca el municipio:","Agregar nueva Casilla", JOptionPane.PLAIN_MESSAGE,icono,listaMunicipios,1);
		
		if(municipio == null)
			return;
		
		icono = gral.crearIcono("step_4_4.gif");
		Object nulos = JOptionPane.showInputDialog(frm,"<html><h3>Paso 4 de 4</h3><p style='width:200px'>"+
					   "Introduzca votos nulos","Agregar nueva Casilla", JOptionPane.PLAIN_MESSAGE, icono,null,0);
		
		if(nulos == null)
			return;
		
			for(int n = 0; n < listaTipoCasilla.length; n++)
				if(listaTipoCasilla[n].equals(tipoCasilla))
					selectTipoCasilla = n;

			for(int n = 0; n < listaMunicipios.length; n++)
				if(listaMunicipios[n].equals(municipio))
					selectMunicipio = n;
		
		myDataBase.nuevoValor(frm,"Casillas",(String)index+ "," +(String)listaTipoCasillaVal[selectTipoCasilla][0]+ "," +(String)listaMunicipiosVal[selectMunicipio][0]+ "," +(String)nulos);
		
	}
	
	
	public static void eliminarCasilla(Frame frm, dataBase myDataBase){
		
		String index = JOptionPane.showInputDialog(frm,"Introduzca la identificacion de casilla eliminar:","Eliminacion", JOptionPane.PLAIN_MESSAGE);
		
		if(index == null)
			return;
		
		myDataBase.eliminar(frm,"Casillas","pk_casilla="+index);
	}
	
	public static void eliminarCaptura(Frame frm, dataBase myDataBase){
		
		String index = JOptionPane.showInputDialog(frm,"Introduzca la identificacion de captura a eliminar:","Eliminacion", JOptionPane.PLAIN_MESSAGE);
		
		if(index == null)
			return;
		
		myDataBase.eliminar(frm,"Captura","pk_captura="+index);
	}
}
	
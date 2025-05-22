import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class dataBase{
	
	String id;
	String tipo;
	String existencia;

	private Connection conexion;
	private Statement sentencia;
	String nombreDBase;
	
	// Crea la conexion a la base de datos
	public dataBase(String nombreDBase){

		this.nombreDBase = nombreDBase;
		
		try{  
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al encontrar el driver de la base de datos", "Error interno", JOptionPane.ERROR_MESSAGE);
			System.err.println(e.getMessage()+"\n\nError interno, no se puede continuar");
			System.exit(0);
		}

		try{
			//conexion  = DriverManager.getConnection("jdbc:odbc:" + nombreDBase);
			conexion  = DriverManager.getConnection("jdbc:ucanaccess://" + nombreDBase);
			sentencia = conexion.createStatement();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Error al encontrar la base de datos.\nSe saldra automaticamente la aplicaci\u00f3n", "Error interno", JOptionPane.ERROR_MESSAGE);
			System.err.println(ex.getMessage()+"\n\nError interno, no se puede continuar");
			System.exit(0);
		}
	}


	// actualiza la base de datos
	public boolean actualizar(Frame frm, String tabla, String setSQL, String whereSQL){
		boolean n = false;
		
		try{
      		sentencia.executeUpdate("UPDATE "+tabla+" SET "+setSQL+" WHERE "+whereSQL);
      		JOptionPane.showMessageDialog(frm, "Datos actualizados exitosamente en:" +tabla,"Guardar",JOptionPane.INFORMATION_MESSAGE);
	     	n = true;

		} catch (SQLException sql) {
			JOptionPane.showMessageDialog(frm, "Los datos no pudieron ser actualizado desde "+tabla,"Eliminar",JOptionPane.WARNING_MESSAGE);
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}

	// Guarda una nuevo valor
	public boolean nuevoValor(Frame frm, String tabla, String valores){
		boolean n = false;

		try{
			
      		sentencia.executeUpdate("INSERT INTO "+tabla+" VALUES("+valores+")");
	     	JOptionPane.showMessageDialog(frm, "Datos guardados exitosamente en:" +tabla,"Guardar",JOptionPane.INFORMATION_MESSAGE);
	     	n = true;
	     	
		} catch (SQLException sql) {
			JOptionPane.showMessageDialog(frm, "Los datos no pudieron ser guardados desde "+tabla,"Eliminar",JOptionPane.WARNING_MESSAGE);
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}

	public boolean eliminar(Frame frm, String tabla, String whereSQL){
		boolean n = false;
		
		try{
			sentencia.executeUpdate("DELETE * FROM " +tabla + " WHERE " + whereSQL);
	     	JOptionPane.showMessageDialog(frm, "Datos eliminados satisfactoriamente desde "+tabla,"Eliminar",JOptionPane.INFORMATION_MESSAGE);
	     	n = true;
	     	
		} catch (SQLException sql) {
			JOptionPane.showMessageDialog(frm, "Los datos no pudieron ser eliminados desde "+tabla,"Eliminar",JOptionPane.WARNING_MESSAGE);
			System.err.println("Error: "+sql.getMessage());
		}
		
		return n;
	}

	public String[][] listaValoresTabla(String tabla){
		
		String s[][] = null;
		
		s = listaTabla(tabla,2,null);
		
		return s;
	}

	public String[][] listaTabla(String tabla,int numeroColumnas, String whereSQL){
		
		String s[][] = null;
		String syntax;
		
		int count = 0;
		
		if(whereSQL == null)
			syntax = tabla;
		else
			syntax = tabla + " WHERE " + whereSQL;
			
		try{
			
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + syntax);
			while (rs.next()){
				count++;
			}
			s = new String[count][numeroColumnas];
			
		} catch(SQLException bbb){
			System.err.print("Error: "+bbb.getMessage());
		}
		
		try{
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + syntax);
			count = -1;
			while (rs.next()){
				count++;
				for(int n = 0 ;n < numeroColumnas; n++)
					s[count][n] = rs.getString(n+1);
			}
		} catch(SQLException bbb){
			System.err.print("Error: "+bbb.getMessage());
		}
		
		return s;
	}

	public String[] listaValoresCelda(String tabla,int numeroColumnas, String whereSQL){
		
		String s[] = new String[numeroColumnas];
		String syntax;

		syntax = tabla + " WHERE " + whereSQL;

		try{
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + syntax);
			while (rs.next()){
				for(int n = 0 ;n < numeroColumnas; n++)
					s[n] = rs.getString(n+1);
			}
		} catch(SQLException bbb){
			System.err.print("Error: "+bbb.getMessage());
		}
		
		return s;
	}

	public String[][] advancedSQL(String syntax, int numeroColumnas){
		
		String s[][] = null;
	
		int count = 0;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			while (rs.next()){
				count++;
			}
			if(rs.wasNull() == false){
				rs = sentencia.executeQuery(syntax);
				s = new String[count][numeroColumnas];
				count = -1;
				while (rs.next()){
					count++;
					for(int n = 0 ;n < numeroColumnas; n++)
						s[count][n] = rs.getString(n+1);
				}
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	
	public String obtenerValorCelda(String tabla, String nombreColumna, String whereSQL){
		String s = new String();
		String syntax;

		syntax = "SELECT "+nombreColumna+" FROM " +tabla+ " WHERE " +whereSQL;

		try{
			ResultSet rs = sentencia.executeQuery(syntax);
			
			while (rs.next()){
				s= rs.getString(1);
			}
		} catch(SQLException bbb){
			System.err.println("Error: "+bbb.getMessage());
		}
		
		return s;
	}
	
	public void cerrarDB(){
		try{
			conexion.close();
		} catch (SQLException ex){
      		System.err.println("Error: " + ex.getMessage());	
      	}
	}
}
	
package com.bonvoyage.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bonvojage.utils.DbConnector;
import com.bonvoyaje.domain.Transfer;

public class TransferDAO {
	
		private static final String INSERT_TRANSFER = "INSERT INTO transfer(Transfer_ID,User_ID,Profile_ID,Class_ID,Reservation_ID,Pool_ID,User_Role,Departure_Address,Arrival_address,Departure_GPS,"
																			+ "Arrival_GPS,Departure_Time,Type,Occupied_seats,Available_seats,Animal,Handicap,Smoke,Luggage,Status,Price,Path)"
																			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		public static void insert(Transfer transfer) throws SQLException
		{
			Connection con = null;
			PreparedStatement pstm = null;
			DbConnector manager = new DbConnector();
			con = manager.connect();
			pstm = con.prepareStatement(INSERT_TRANSFER);
			pstm.setInt(1, transfer.getTran_id());
			pstm.setInt(1, transfer.getUser_id());
			pstm.setInt(2, transfer.getProf_id());
			pstm.setInt(4, transfer.getClass_id());
			pstm.setInt(5, transfer.getReser_id());
			pstm.setInt(5, transfer.getPool_id());
			pstm.setString(6, transfer.getUser_role().toString());
			pstm.setString(7, transfer.getDep_addr());
			pstm.setString(8, transfer.getArr_addr());
			pstm.setString(9, transfer.getDep_gps().toString());
		}
	
	
	
	/*comando SQL per l'inserimento di una nuova istanza di Persona
	/ *ordine degli attributi: CF(STRING),nome(STRING),cognome(STRING),username(STRING),pass(STRING)*/
		private static final String INSERT_PERSONA = "INSERT INTO Persona(CF,nome,cognome,username,pass,sesso) VALUES(?,?,?,?,?,?)";
		
	/*comando SQL per l'inserimento di una nuova istanza di Citta
	 * ordine degli attributi nome(STRING),regione(STRING)
	 */
		private static final String INSERT_CITTA="INSERT IGNORE INTO Citta(nome,regione) VALUES (?,?)";
		
	/*comando SQL per l'inserimento di una nuova istanza di R_Nato,
	 * l'associazione che lega un entità persona a un'entità nato
	 * ordine degli attributi persona(STRING),citta(STRING),regione(STRING),dataNascita(DATE);
	 */
		private static final String INSERT_R_NATO = "INSERT INTO R_Nato(persona,citta,regione,dataNascita) VALUES (?,?,?,?)";
		
	/*comando SQL per l'inserimento di una nuova istanza di Famiglia
	/ * ordine degli attributi: nomeFamiglia(STRING),idFamiglia(INT)*/
		private static final String INSERT_FAMIGLIA= "INSERT INTO Famiglia(nomeFamiglia,idFamiglia) VALUES(?,DEFAULT)";
		
	/*comando SQL per l'inserimento di una nuova istanza di R_Appartiene
	 * l'associazione che lega una persona alla famiglia di appartenenza
	/ * ordine degli attributi: persona(STRING),famiglia(INT)*/
		private static final String INSERT_R_APPARTIENE="INSERT INTO R_Appartiene(persona,famiglia) VALUES(?,?)";
		
		private static final String GET_MAX_FAM="SELECT MAX(idFamiglia) FROM Famiglia";
				
		public static void insert(Persona tizio,Citta cit) throws SQLException, ClassNotFoundException
			{
			Connection con=null;
			PreparedStatement PSpersona=null;
			PreparedStatement PScitta=null;
			PreparedStatement PSrnato=null;
			PreparedStatement PSfamiglia=null;
			PreparedStatement PSappartiene=null;
			PreparedStatement PSidFam=null;
			ResultSet rs=null;
			try{
				ConnectionManagerSER manager = new ConnectionManagerSER();
				con = manager.getConnection();
				
				PSpersona = con.prepareStatement(INSERT_PERSONA);
				PSpersona.setString(1,tizio.getCF());
				PSpersona.setString(2,tizio.getNome());
				PSpersona.setString(3,tizio.getCognome());
				PSpersona.setString(4,tizio.getUser());
				PSpersona.setString(5,tizio.getPass());
				PSpersona.setString(6,tizio.getSesso());
				PSpersona.executeUpdate();
				
				PScitta=con.prepareStatement(INSERT_CITTA);
				PScitta.setString(1,cit.getNome());
				PScitta.setString(2,cit.getRegione());
				PScitta.executeUpdate();
				
				PSrnato=con.prepareStatement(INSERT_R_NATO);
				PSrnato.setString(1,tizio.getCF());
				PSrnato.setString(2, cit.getNome());
				PSrnato.setString(3,cit.getRegione());
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(tizio.getNato());
				PSrnato.setString(4,currentTime);
				PSrnato.executeUpdate();
				
				PSfamiglia=con.prepareStatement(INSERT_FAMIGLIA);
				PSfamiglia.setString(1,tizio.getCognome());
				PSfamiglia.executeUpdate();
				
				PSidFam=con.prepareStatement(GET_MAX_FAM);
				rs=PSidFam.executeQuery();
				rs.next();
				
				PSappartiene=con.prepareStatement(INSERT_R_APPARTIENE);
				PSappartiene.setString(1,tizio.getCF());
				PSappartiene.setInt(2, rs.getInt(1));
				PSappartiene.executeUpdate();
				
				con.commit();
				}catch (SQLException sql)
							{
							sql.printStackTrace();
							if(con!=null)try
										{
										System.err.println("Errore imprevisto durante la transazione, rollback dell'operazione in corso...");
										con.rollback();
										}catch (SQLException sql2)
													{sql2.printStackTrace();
													System.err.println("Errore fatale, impossibile effettuare il rollback, possibile rischio di inconsistenza della base di dati");
													}
							}
			finally
				{
				if(PSpersona!=null) PSpersona.close();
				if(PScitta!=null) PScitta.close();
				if(PSrnato!=null) PSrnato.close();
				if(PSfamiglia!=null) PSfamiglia.close();
				if(PSappartiene!=null) PSappartiene.close();
				if(PSidFam!=null) PSidFam.close();
				if(rs!=null) rs.close();
				if(con!=null) con.close();
				}
			}


}

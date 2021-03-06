package com.bonvoyage.persistance;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import org.postgresql.util.PGobject;
import org.postgresql.geometric.PGpoint;

import com.bonvoyage.domain.Transfer;
import com.bonvoyage.domain.UserProfile;
import com.bonvoyage.utils.DbConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



public class TransferDAO implements Serializable{
	
		/*private static final String INSERT_TRANSFER = "INSERT INTO Transfer(\"Transfer_ID\",\"User_ID\",\"Profile_ID\",\"Class_ID\",\"Reservation_ID\",\"Pool_ID\",\"User_Role\",\"Departure_Address\",\"Arrival_Address\",\"Departure_GPS\","
																			+ "\"Arrival_GPS\",\"Departure_Time\",\"Type\",\"Occupied_Seats\",\"Available_Seats\",\"Animal\",\"Handicap\",\"Smoke\",\"Luggage\",\"Status\",\"Price\",\"Path\")"
																			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";*/
		private static final String INSERT_TRANSFER = "INSERT INTO Transfer(\"User_ID\",\"Profile_ID\",\"Class_ID\",\"Reservation_ID\",\"Pool_ID\",\"User_Role\",\"Departure_Address\",\"Arrival_Address\",\"Departure_GPS\","
				+ "\"Arrival_GPS\",\"Departure_Time\",\"Type\",\"Occupied_Seats\",\"Available_Seats\",\"Animal\",\"Handicap\",\"Smoke\",\"Luggage\",\"Status\",\"Price\",\"Path\")"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		public static void insert(Transfer transfer) throws SQLException
		{
			Connection con = null;
			PreparedStatement pstm = null;
			DbConnector manager = new DbConnector();
			con = manager.connect();
			pstm = con.prepareStatement(INSERT_TRANSFER);
			//pstm.setInt(1, transfer.getTran_id());
			pstm.setInt(1, transfer.getUser_id());
			pstm.setInt(2, transfer.getProf_id());
			pstm.setInt(3, transfer.getClass_id());
			pstm.setInt(4, transfer.getReser_id());
			pstm.setInt(5, transfer.getPool_id());
			//pstm.setString(6, transfer.getUser_role().toString());
			
			PGobject userRole = new PGobject();
			userRole.setType("json");
			userRole.setValue(transfer.getUser_role().toString());
			pstm.setObject(6, userRole);
			
			pstm.setString(7, transfer.getDep_addr());
			pstm.setString(8, transfer.getArr_addr());
			
			PGpoint depGps = new PGpoint(transfer.getDep_gps().getX(),transfer.getDep_gps().getY());
			//pstm.setString(9, transfer.getDep_gps().toString());
			pstm.setObject(9, depGps);
						
			PGpoint arrGps = new PGpoint(transfer.getArr_gps().getX(),transfer.getArr_gps().getY());
			pstm.setObject(10,arrGps);
			Timestamp timestamp = new Timestamp(transfer.getDep_time());
			pstm.setTimestamp(11, timestamp);
			pstm.setString(12, transfer.getType());
			pstm.setInt(13, transfer.getOcc_seats());
			pstm.setInt(14, transfer.getAva_seats());
			pstm.setBoolean(15, transfer.isAnimal());
			pstm.setBoolean(16, transfer.isHandicap());
			pstm.setBoolean(17, transfer.isSmoke());
			pstm.setBoolean(18, transfer.isLuggage());
			pstm.setString(19, transfer.getStatus());
			pstm.setDouble(20, transfer.getPrice());
			
			PGobject path = new PGobject();
			path.setType("json");
			path.setValue(transfer.getPath().toString());
			pstm.setObject(21, path);
			
			pstm.executeUpdate();
			con.commit();
			con.close();
		}
		
		private static String READ_MY_OFFERINGS="Select * from Transfer WHERE \"User_ID\"=?";
		public static LinkedList<Transfer> readMyOfferings(UserProfile user) throws SQLException
			{
			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs=null;
			LinkedList<Transfer> result = null;
			DbConnector manager = new DbConnector();
			con = manager.connect();
			pstm=con.prepareStatement(READ_MY_OFFERINGS);
			pstm.setInt(1, user.getUserID());
			rs=pstm.executeQuery();
			if(rs.isBeforeFirst())
				{
				rs.next();
				result = new LinkedList<Transfer>();
				while(!rs.isAfterLast())
					{
					 Transfer toAdd = new Transfer();
					 toAdd.setTran_id(rs.getInt(1));
					 toAdd.setUser_id(rs.getInt(2));
					 toAdd.setProf_id(rs.getInt(3));
					 toAdd.setClass_id(rs.getShort(4));
					 toAdd.setReser_id(rs.getInt(5));
					 toAdd.setPool_id(rs.getInt(6));
					 
					 String roleString = rs.getString(7);
					 Gson gson = new GsonBuilder().create();
					 JsonObject roleJson = gson.toJsonTree(roleString, String.class).getAsJsonObject();
					 
					 toAdd.setUser_role(roleJson);
					 toAdd.setDep_addr(rs.getString(8));
					 toAdd.setArr_addr(rs.getString(9));
					 PGpoint depGps = new PGpoint(rs.getString(10));
					 Point2D depPoint = new Point2D.Double(depGps.x, depGps.y);
					 toAdd.setDep_gps(depPoint);
					 PGpoint arrGps = new PGpoint(rs.getString(11));
					 Point2D arrPoint = new Point2D.Double(arrGps.x, arrGps.y);
					 toAdd.setArr_gps(arrPoint);
					 toAdd.setDep_time(rs.getLong(12));
					 toAdd.setType(rs.getString(13));
					 toAdd.setOcc_seats(rs.getInt(14));
					 toAdd.setAva_seats(rs.getInt(15));
					 toAdd.setAnimal(rs.getBoolean(16));
					 toAdd.setHandicap(rs.getBoolean(17));
					 toAdd.setSmoke(rs.getBoolean(18));
					 toAdd.setLuggage(rs.getBoolean(19));
					 toAdd.setStatus(rs.getString(20));
					 toAdd.setPrice(rs.getDouble(21));
					 
					 String pathString =rs.getString(22);
					 JsonObject path = gson.toJsonTree(pathString, String.class).getAsJsonObject();
					 
					 toAdd.setPath(path);
					 result.add(toAdd);
					 rs.next();
					 }
				if(rs!=null) rs.close();
				if(pstm!=null) pstm.close();
				if(con!=null) con.close();
				
				}
			return result;
			}
	
	
	
	
	/*			
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

*/
}

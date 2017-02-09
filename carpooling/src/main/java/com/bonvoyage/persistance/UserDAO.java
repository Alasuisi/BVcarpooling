package com.bonvoyage.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bonvoyage.domain.User;
import com.bonvoyage.utils.DaoException;
import com.bonvoyage.utils.DbConnector;

import elemental.json.Json;
import elemental.json.JsonObject;



public class UserDAO {

	
	/*Sql statement for obtaining an instance of User, reading from table user_profile
	 * 
	 */
	private static final String GET_USER_BY_ID = "SELECT * FROM user_profile Where user_id=?";
	public static User load(int userid) throws SQLException, DaoException
		{
		 Connection con=null;
		 PreparedStatement pstm=null;
		 ResultSet rs=null;
		 User result=null;
		 DbConnector manager = new DbConnector();
		 con=manager.connect();
		 pstm=con.prepareStatement(GET_USER_BY_ID);
		 pstm.setInt(1, userid);
		 rs=pstm.executeQuery();
		 if(rs.isBeforeFirst())
		 	{
			 rs.next();
			 int id=rs.getInt("user_id");
			 int profid=rs.getInt("profile_id");
			 JsonObject tnodes = Json.parse(rs.getString("number_of_transit_nodes"));
			 JsonObject tmeans = Json.parse(rs.getString("user_transport_means"));
			 JsonObject privcar = Json.parse(rs.getString("private_service_car"));
			 JsonObject prefclass = Json.parse(rs.getString("preferred_class_category"));
			 JsonObject specneeds = Json.parse(rs.getString("special_travel_needs"));
			 JsonObject tottravel = Json.parse(rs.getString("total_travel_time"));
			 JsonObject conflevel = Json.parse(rs.getString("confort_level"));
			 JsonObject emsens = Json.parse(rs.getString("sensibility_to_emissions"));
			 JsonObject walkdst = Json.parse(rs.getString("walking_distance"));
			 JsonObject rngdep = Json.parse(rs.getString("range_departure_time"));
			 JsonObject pricerng = Json.parse(rs.getString("price_range_travel"));
			 JsonObject scorepl=null;
			 if(rs.getString("score_policy")!=null)
			 	{
				  scorepl = Json.parse(rs.getString("score_policy"));
			 	}
			 
			
			 result= new User();
			 result.setUserID(id);
			 result.setProfileID(profid);
			 result.setNtransit(tnodes);
			 result.setTmeans(tmeans);
			 result.setServicecar(privcar);
			 result.setPrefclass(prefclass);
			 result.setSpecialneeds(specneeds);
			 result.setTottraveltime(tottravel);
			 result.setConfortlevel(conflevel);
			 result.setEmissionsens(emsens);
			 result.setWalkdistance(walkdst);
			 result.setPricerange(pricerng);
			 result.setRangedeptime(rngdep);
			 result.setScorepolicy(scorepl);
			 pstm.close();
			 rs.close();
			 con.close();
			 manager=null;
			 return result;
		 	}
		 else 
		 	{
			 	pstm.close();
				rs.close();
				con.close();
			 throw new DaoException("There is no user with associated ID="+userid+". Empty Result set");
		 	}
		}
	
	
	
	
}

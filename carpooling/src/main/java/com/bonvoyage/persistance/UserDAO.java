package com.bonvoyage.persistance;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.MediaType;

import com.bonvoyage.domain.UserProfile;
import com.bonvoyage.utils.DaoException;
import com.bonvoyage.utils.DbConnector;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import elemental.json.Json;
import elemental.json.JsonObject;



public class UserDAO implements Serializable{

	
	public static UserProfile load(int userid) throws DaoException, JsonParseException, JsonMappingException, IOException
		{
		Client client = Client.create();
		String address = "http://localhost:8080/bvcrplbe/userprofile/"+userid;
		WebResource resource = client.resource(address);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		if(response.getStatus()!=200)
			{
			 throw new DaoException("UserDAO error:"+System.lineSeparator()+response.getStatusInfo());
			}
		ObjectMapper mapper = new ObjectMapper();
		String profString = response.getEntity(String.class);
		UserProfile prof = mapper.readValue(profString, new TypeReference<UserProfile>(){});
		return prof;
		}
	
	
	/*Sql statement for obtaining an instance of User, reading from table user_profile
	 * 
	 */
	/*private static final String GET_USER_BY_ID = "SELECT * FROM user_profile Where user_id=?";
	public static UserProfile load(int userid) throws SQLException, DaoException
		{
		 Connection con=null;
		 PreparedStatement pstm=null;
		 ResultSet rs=null;
		 UserProfile result=null;
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
			 
			
			 result= new UserProfile();
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
		}*/
	
	
	
	
}

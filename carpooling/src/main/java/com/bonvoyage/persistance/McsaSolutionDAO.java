package com.bonvoyage.persistance;

import java.io.IOException;
import java.util.LinkedList;

import javax.ws.rs.core.MediaType;

import com.bonvoyage.domain.McsaSolution;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.utils.DaoException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class McsaSolutionDAO {
	
public static LinkedList<McsaSolution> searchSolutions(Transfer toSearch,long timeFrame,int limitSolution) throws IOException, DaoException
	{
	ObjectMapper mapper = new ObjectMapper();
	LinkedList<McsaSolution> results= new LinkedList<McsaSolution>();
	String transferString = mapper.writeValueAsString(toSearch);
	Client client = Client.create();
	 WebResource searchResource = client.resource("http://localhost:8080/bvcrplbe/SearchRide/"+timeFrame+"/"+limitSolution); //1800000
	 ClientResponse response2 = searchResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,transferString);
	 if(response2.getStatus()!=200)
	 	{
		 //System.out.println(response2.getEntity(String.class));
		 throw new DaoException("Failed : HTTP error code : " + response2.getStatus());
	 	}else
	 		{
	 		String resultString = response2.getEntity(String.class);
	 		 System.out.println(resultString);
	 		 results = mapper.readValue(resultString, new TypeReference<LinkedList<McsaSolution>>(){});
	 		}
	 return results;
	}
public static LinkedList<McsaSolution> readComputedSolutions(int userid,int transferid) throws JsonParseException, JsonMappingException, IOException, ClientHandlerException, UniformInterfaceException, DaoException
	{
	Client client = Client.create();
	ObjectMapper mapper = new ObjectMapper();
	String address = "http://localhost:8080/bvcrplbe/SearchRide/"+userid+"/"+transferid;
	LinkedList<McsaSolution> result = new LinkedList<McsaSolution>();
	 WebResource resource = client.resource(address);
	 ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	 if(response.getStatus()!=200){
			throw new DaoException("Failed : HTTP error code : " + response.getStatus()+response.getEntity(String.class));
		}else
			{
			String output = response.getEntity(String.class);
			result = mapper.readValue(output, new TypeReference<LinkedList<McsaSolution>>(){});
			System.out.println(output);
			}
	 return result;
	}

public static McsaSolution bookRide(int userid,int tranid,int solid) throws ClientHandlerException, UniformInterfaceException, DaoException, JsonParseException, JsonMappingException, IOException
	{
	 Client client = Client.create();
	 String address ="http://localhost:8080/bvcrplbe/BookRide/"+userid+"/"+tranid+"/"+solid;
	 WebResource resource = client.resource(address);
	 ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	 ObjectMapper mapper = new ObjectMapper();
	 McsaSolution result=null;
	 if(response.getStatus()!=200)
	 	{
		 throw new DaoException("Failed : HTTP error code : " + response.getStatus()+response.getEntity(String.class));
	 	}else
	 		{
	 		 String output = response.getEntity(String.class);
	 		 result = mapper.readValue(output, new TypeReference<McsaSolution>(){});
	 		}
	 return result;
	}

}

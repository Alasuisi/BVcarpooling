package com.bonvoyage.persistance;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.bonvoyage.domain.Pool;
import com.bonvoyage.utils.DaoException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PoolDAO {
	
	public static Pool getPool(int userid,int tranid) throws DaoException, JsonParseException, JsonMappingException, IOException
		{
			Client client = Client.create();
			WebResource resource = client.resource("http://localhost:8080/bvcrplbe/OfferRide/pool/"+userid+"/"+tranid);
			ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new DaoException("Failed : HTTP error code : " + response.getStatus());
			}
			ObjectMapper mapper = new ObjectMapper();
			String output = response.getEntity(String.class);
			Pool result = mapper.readValue(output, new TypeReference<Pool>(){});
			return result;
		}

}

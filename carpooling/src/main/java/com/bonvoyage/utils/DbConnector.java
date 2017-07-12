package com.bonvoyage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.util.PSQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.vaadin.ui.UI;

public class DbConnector {
	
	/*private final String url = "jdbc:postgresql://82.223.67.189:5432/bonvoyage";
	private final String user = "alessio";
	private final String password = "bvpsql2016";

	
	 
    public Connection connect() {
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Madonna driver");
			e1.printStackTrace();
		}
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }*/
	
	
	Connection con;
	Session session;
	int nLocalPort = 6000;
	private static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	  {
	    final JSch jsch = new JSch();
	    Session session = jsch.getSession( strSshUser, strSshHost, 22 );
	    session.setPassword( strSshPassword );
	     
	    final Properties config = new Properties();
	    config.put( "StrictHostKeyChecking", "no" );
	    session.setConfig( config );
	    
	    //session.disconnect();
	   //	session.delPortForwardingL(nLocalPort);
	    //session.delPortForwardingL(nLocalPort);
	    session.connect();
	    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	    /*try{
	    	session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	    	System.out.println("SSH tunnel initialized on local port: "+nLocalPort);
	    	}catch(JSchException ex)
	    	{
	    		doSshTunnel(strSshUser,strSshPassword,strSshHost,nSshPort,strRemoteHost,nLocalPort+1,nRemotePort);
	    	}*/
	  }
	   
	  public Connection connect()
	  {
	    try
	    {
	    	System.out.println("tunnel status: "+UI.getCurrent().getSession().getAttribute("tunnel"));
	    	String tunnelUP=(String) UI.getCurrent().getSession().getAttribute("tunnel");
	    	if(tunnelUP==null) tunnelUP="false";
	    	if(!tunnelUP.equals("true"))
	    	{
	    		System.out.println("dentro primo if");
	    		String strSshUser = "bonvoyage";                  // SSH loging username
	    		String strSshPassword = "bnvyg2017";                   // SSH login password
	    		String strSshHost = "82.223.67.189";          // hostname or ip or SSH server
	    		int nSshPort = 2222;                                    // remote SSH host port number
	    		String strRemoteHost = "localhost";  // hostname or ip of your database server
	                                      // local port number use to bind SSH tunnel
	    		int nRemotePort = 5432;                               // remote port number of your database 
	      
	    		try{
	    			doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
	    			}catch(JSchException e)
	    				{
	    				System.out.println(e.getMessage()+"This may result of a still opened connection, if you happen to republish the app without restarting the server, usually you can ignore this message if all seems working normally");
	    				}
	    		UI.getCurrent().getSession().setAttribute("tunnel", true);
	    	}
	    	try{ 
		    	  String strDbUser = "alessio";                    // database loging username
			      String strDbPassword = "bvpsql2016";                    // database login password
			      Class.forName("org.postgresql.Driver");
			      con = DriverManager.getConnection("jdbc:postgresql://localhost:"+nLocalPort+"/bonvoyage", strDbUser, strDbPassword);
			      int isolation = Connection.TRANSACTION_SERIALIZABLE;
			      con.setTransactionIsolation(isolation);
			      con.setReadOnly(false);
				  con.setAutoCommit(false);
			      return con;
		      }catch(PSQLException e)
		      	{
		    	  e.printStackTrace();
		    	  UI.getCurrent().getSession().setAttribute("tunnel", false);
		    	  connect();
		      	}
	      //con.close();
	    }
	    catch( Exception e )
	    {
	      e.printStackTrace();
	    }
		return con;
	  }
	  
	  
	  public void close()	
	  {
		  if(con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
}

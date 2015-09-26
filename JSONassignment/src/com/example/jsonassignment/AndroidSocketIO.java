package com.example.jsonassignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AndroidSocketIO {
	   Socket socket = null;	
	   String dstAddress;
	   int dstPort;
	   String response = "";  
	   public String getDataFromUrl(String addr, int port) {
	   dstAddress = addr;
	   dstPort = port;
	   try {
	    socket = new Socket(dstAddress, dstPort);	    
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
	    byte[] buffer = new byte[1024];	    
	    int bytesRead;
	    InputStream inputStream = socket.getInputStream();
	    while ((bytesRead = inputStream.read(buffer)) != -1){
	    byteArrayOutputStream.write(buffer, 0, bytesRead);
	    response += byteArrayOutputStream.toString("UTF-8");
	    }
	   }catch (UnknownHostException e){   
	    e.printStackTrace();
	    response = "UnknownHostException: " + e.toString();
	   } catch (IOException e) {	  
	    e.printStackTrace();
	    response = "IOException: " + e.toString();
	   }finally{
	    if(socket != null){
	     try {
	      socket.close();
	     } catch (IOException e) {
	      e.printStackTrace();
	     }
	    }
	   }
	   return response;
	  }
	 }
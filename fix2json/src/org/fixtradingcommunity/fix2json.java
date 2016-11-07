package org.fixtradingcommunity;
/**
* The program converts raw FIX message into Json format
* It is not sofesticated to handle the repeating groups
* 
* @author  Krishna Tharnoju
* 
* @version 1.0
* @since   2016-07-11 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class fix2json {
	
   public static void main (String[] args) throws IOException{
	   
	   HashMap<String, String> fieldMap= new HashMap<String, String>();
	   List <String> headerFields = new ArrayList<String>();
	   List <String> trailerFields = new ArrayList<String>();

      try {	
         File inputFile = new File("res/fields.xml");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("field");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               fieldMap.put(eElement.getAttribute("number"), eElement.getAttribute("name"));
           }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
//      System.out.println(fieldMap.toString());

      try {	
          File inputFile = new File("res/header.xml");
          DocumentBuilderFactory dbFactory 
             = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);
          doc.getDocumentElement().normalize();
          NodeList nList = doc.getElementsByTagName("field");
          for (int temp = 0; temp < nList.getLength(); temp++) {
             Node nNode = nList.item(temp);
             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                headerFields.add( eElement.getAttribute("name"));
            }
             
          }
       } catch (Exception e) {
          e.printStackTrace();
       }

//      System.out.println(headerFields.toString());
      try {	
          File inputFile = new File("res/trailer.xml");
          DocumentBuilderFactory dbFactory 
             = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);
          doc.getDocumentElement().normalize();
          NodeList nList = doc.getElementsByTagName("field");
          for (int temp = 0; temp < nList.getLength(); temp++) {
             Node nNode = nList.item(temp);
             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                trailerFields.add( eElement.getAttribute("name"));
            }
             
          }
       } catch (Exception e) {
          e.printStackTrace();
       }
//      System.out.println(trailerFields.toString());
 
      FileReader fileReader = new FileReader("res/SampleMessage.txt"); 
      BufferedReader messageReader= new BufferedReader(fileReader );
      Writer outFile = new FileWriter("logs/JsonOutput.txt");
      BufferedWriter outputWritter= new BufferedWriter(outFile);
     
     
      String line = null; 
      while((line = messageReader.readLine()) != null) { 
    	  
    	  String jsonString = "\n-----------------\n{";
    	  String jsonHeader= "\n\t\"Header\": {";
    	  String jsonBody = "\n\t\"Body\":{";
    	  String jsonTrailer= "\n\t\"Trailer\":{";
    	  

    	  String[] tagPairs = line.split("\001");
          for (int i = 0; i < tagPairs.length; i++) {
        	  
        	  String[] tokens = tagPairs[i].split("=");
              
              String tagNumber = tokens[0];
              String tagName = fieldMap.get(tagNumber);              
              String tagValue= tokens[1];        	  	
              
//              System.out.println(tagName);
              
              if (headerFields.contains(tagName))
              {
            	  jsonHeader = jsonHeader + "\n\t\t\""+tagName+"\""+": \""+tagValue + "\",";
            	  
              }
              else if (trailerFields.contains(tagName))
              {
            	  jsonTrailer= jsonTrailer+ "\n\t\t\""+tagName+"\""+": \""+tagValue + "\",";
            	  
              }
              else
              {
            	  jsonBody= jsonBody+ "\n\t\t\""+tagName+"\""+": \""+tagValue + "\",";
            	  
              }          
          }
          
          
          jsonHeader = trimLastComma(jsonHeader) + "\n\t},";
          jsonBody = trimLastComma(jsonBody) + "\n\t},";
          jsonTrailer = trimLastComma(jsonTrailer) + "\n\t}";
          
          jsonString = jsonString + jsonHeader+ jsonBody + jsonTrailer;
          
          jsonString = jsonString+ "\n}";
    	  System.out.println(jsonString ); 
    	  outputWritter.write(jsonString );    	  
      } 
      outputWritter.close(); 
      messageReader.close(); 
       
   }

   public static String trimLastComma(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length()-1)==',') {
	      str = str.substring(0, str.length()-1);
	    }
	    return str;
	}

}
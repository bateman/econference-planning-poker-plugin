package it.uniba.di.cdg.econference.planningpoker.usimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Trac {

	public static LinkedList<String> note=new LinkedList<String>();
	public static LinkedList<String> storyText=new LinkedList<String>();
	public static LinkedList<String> id=new LinkedList<String>();
	public static LinkedList<String> estimates=new LinkedList<String>();
	public static LinkedList<String> milestone=new LinkedList<String>();
	public static LinkedList<String> milestoneC=new LinkedList<String>();
	public static LinkedList<String> milestoneDate=new LinkedList<String>();
	public static LinkedList<String> milestoneDesc=new LinkedList<String>();
	public static LinkedList<String> milestoneTitle=new LinkedList<String>();
	public static LinkedList<String> clearedMilestone=new LinkedList<String>();
	private static String sub;
	private static FileWriter temp;

	public Trac(String addr, String host,String filename){

		createTempFile(addr);
		File f=new File("temp.xml"); //from createTempFile(addr);
		readFile(f,host);
		CreateStandardXML c2= new CreateStandardXML(storyText,note,id,estimates,milestone,milestoneDesc,milestoneDate,milestoneTitle);
		c2.saveFile(filename);


	}

	public static FileWriter createTempFile(String addr){

		try{
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text;
			StringWriter w = new StringWriter();
			while ((text = reader.readLine()) != null) 
			{
				w.write(text);
			}
			String t=w.toString().replace("&", "");
			int pos = t.toString().indexOf("<div id=\"main\">");
			String parte = t.toString().substring(pos);
			pos=parte.indexOf("<div id=\"main\">");
			int pos2=parte.indexOf("<div id=\"footer\"");
			sub = parte.substring(pos, pos2);
			temp=new FileWriter("temp.xml");
			temp.write(sub);
			temp.close();
			conn.disconnect();
		}catch (IOException ex){}
		return temp;
	}

	public static void readFile(File f,String host){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			XPathFactory factory1 = XPathFactory.newInstance();
			XPath xpath = factory1.newXPath();
			XPathExpression expr;
			Object result;

			//id
			expr = xpath.compile("/div/div[@class='query']/table/tbody/tr/td[@class='id']");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListID = (NodeList) result;
			if (nodeListID.getLength()==0){
				expr = xpath.compile("/div/div[@class='query']/div/table/tbody/tr/td[@class='id']");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				nodeListID = (NodeList) result;
			}
			for (int i = 0; i < nodeListID.getLength(); i++){
				id.offer(nodeListID.item(i).getTextContent().trim().replace("#", ""));
			}

			//summary
			expr = xpath.compile("/div/div[@class='query']/table/tbody/tr/td[@class='summary']");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListSummary = (NodeList) result;
			if (nodeListSummary.getLength()==0){
				expr = xpath.compile("/div/div[@class='query']/div/table/tbody/tr/td[@class='summary']");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				nodeListSummary = (NodeList) result;
			}
			for (int i = 0; i < nodeListSummary.getLength(); i++){ 
				storyText.offer(nodeListSummary.item(i).getTextContent().trim());
				estimates.offer("");
			}

			//milestone
			expr = xpath.compile("/div/div[@class='query']/table/tbody/tr/td[@class='milestone']/span");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListMil = (NodeList) result;
			if (nodeListMil.getLength()==0){
				expr = xpath.compile("/div/div[@class='query']/div/table/tbody/tr/td[@class='milestone']");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				nodeListMil = (NodeList) result;
			}
			for (int i = 0; i < nodeListMil.getLength(); i++){ 
				milestone.offer(nodeListMil.item(i).getTextContent().trim());
				milestoneC.offer(nodeListMil.item(i).getTextContent().trim());
			}

			for(int i=0;i<id.size();i++){
				
				retrieveStoryDescription(host+"/ticket/"+id.get(i));
			}

			if (!milestoneC.isEmpty())
				clearedMilestone=removeDuplicates(milestoneC);

		}
		catch (XPathExpressionException e){}
		catch (ParserConfigurationException e){}
		catch (IOException e){}
		catch (SAXException e){}
	}

	public static void retrieveStoryDescription(String href){
		try{
			URL url = new URL(href);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text;
			StringWriter w = new StringWriter();
			while ((text = reader.readLine()) != null) 
			{
				w.write(text);
			}
			int pos = w.toString().indexOf("<div class=\"description\">");
			String parte = w.toString().substring(pos);
			pos=parte.indexOf("<p>");
			int pos2=parte.indexOf("</p>");
			String sub=parte.substring(pos+3,pos2);
			sub=sub.replace("<br />", "");
			note.offer(sub);
		}
		catch (IOException ex)
		{
			System.err.println(ex);
		}
	}

	public static LinkedList<String> removeDuplicates(LinkedList<String> list)
	{
		for (int i = 0 ; i < list.size() ; i++)
		{
			String curr=(String) list.get(i);
			for (int j=i+1 ; j< list.size() ; j++)
			{
				String tmp=(String)list.get(j);
				if (curr.equals(tmp))
				{
					list.remove(j);
					j--; 
				}
			}
		}
		return list;
	}
}

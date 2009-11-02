package it.uniba.di.cdg.econference.planningpoker.usimport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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


public class Github {
	public static LinkedList<String> note=new LinkedList<String>();
	public static LinkedList<String> storyText=new LinkedList<String>();
	public static LinkedList<String> id=new LinkedList<String>();
	public static LinkedList<String> milestone=new LinkedList<String>();
	public static LinkedList<String> estimates=new LinkedList<String>();
	public static LinkedList<String> milestoneDesc=new LinkedList<String>();
	public static LinkedList<String> milestoneDate=new LinkedList<String>();
	public static LinkedList<String> milestoneTitle=new LinkedList<String>();
	private static int gitID=2;
	private static boolean open=false;
	private static boolean closed=false;
	private static FileWriter temp1;
	private static FileWriter temp2;


	public Github(String addr,String filename){

		String addrV[]=addr.split("/");
		System.out.println(addrV[addrV.length-1]);
		if (addrV[addrV.length-1].equals("open")){
			open=true;
			retrieveOpenTickets(addr);
		}
		else
			if (addrV[addrV.length-1].equals("closed")){
				closed=true;
				retrieveClosedTickets(addr);
			}
			else
				if (addrV[addrV.length-1].equals("@alltickets@")){
					open=true;
					closed=true;
					addr=addr.replace("@alltickets@", "open");
					retrieveOpenTickets(addr);
					addr=addr.replace("open", "closed");
					retrieveClosedTickets(addr);
				}
		File f1=new File("temp.xml");
		File f2=new File("temp2.xml");
		readFile(open,closed,f1,f2);
		CreateStandardXML c2 = new CreateStandardXML(storyText,note,id,estimates,milestone,milestoneDesc,milestoneDate,milestoneTitle);
		c2.saveFile(filename);
	}

	public static FileWriter retrieveOpenTickets(String addr){
		try{
			// open tickets
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text;
			temp1=new FileWriter("temp.xml");
			while ((text = reader.readLine()) != null) 
			{
				temp1.write(text);
			}
			temp1.close();
			conn.disconnect();
			
		}catch(IOException ex){}
		return temp1;
	}

	public static FileWriter retrieveClosedTickets(String addr2){

		try{
			//closed tickets
			addr2 = "http://github.com/api/v2/xml/issues/list/antogrim2/test-uniba/closed";
			URL url2 = new URL(addr2);
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Accept", "application/xml");
			conn2.connect();
			InputStream in2 = conn2.getInputStream();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
			String text2;
			temp2=new FileWriter("temp2.xml");
			while ((text2 = reader2.readLine()) != null) 
			{
				temp2.write(text2);
			}
			temp2.close();
			conn2.disconnect();
		}catch(IOException ex){}
		return temp2;
	}

	public static void readFile(boolean open, boolean closed,File f1, File f2) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			if (open){
				//open tickets
				Document doc = builder.parse(f1);
				XPathFactory factory1 = XPathFactory.newInstance();
				XPath xpath = factory1.newXPath();
				XPathExpression expr;
				Object result;
				expr = xpath.compile("//title");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodeListTitle = (NodeList) result;
				for (int i = 0; i < nodeListTitle.getLength(); i++){ 
					storyText.offer(nodeListTitle.item(i).getTextContent());
					gitID++;
					id.offer(Integer.toString(gitID));
				}
				expr = xpath.compile("//body");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodeListBody = (NodeList) result;
				for (int i = 0; i < nodeListBody.getLength(); i++) 
					note.offer(nodeListBody.item(i).getTextContent());
			}

			if (closed){
				//closed tickets
				Document doc2 = builder.parse(f2);
				XPathFactory factory2 = XPathFactory.newInstance();
				XPath xpath2 = factory2.newXPath();
				XPathExpression expr2;
				Object result2;
				expr2 = xpath2.compile("//title");
				result2 = expr2.evaluate(doc2, XPathConstants.NODESET);
				NodeList nodeListTitle2 = (NodeList) result2;
				for (int i = 0; i < nodeListTitle2.getLength(); i++){ 
					storyText.offer(nodeListTitle2.item(i).getTextContent());
					gitID++;
					id.offer(Integer.toString(gitID));
				}
				expr2 = xpath2.compile("//body");
				result2 = expr2.evaluate(doc2, XPathConstants.NODESET);
				NodeList nodeListBody2 = (NodeList) result2;
				for (int i = 0; i < nodeListBody2.getLength(); i++) 
					note.offer(nodeListBody2.item(i).getTextContent());
			}
		}
		catch (XPathExpressionException e){}
		catch (ParserConfigurationException e){}
		catch (IOException e){}
		catch (SAXException e){}
	}
}

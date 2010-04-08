/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Assembla {
	public static LinkedList<String> note=new LinkedList<String>();
	public static LinkedList<String> storyText=new LinkedList<String>();
	public static LinkedList<String> id=new LinkedList<String>();
	public static LinkedList<String> milestone=new LinkedList<String>();
	public static LinkedList<String> milestoneC=new LinkedList<String>();
	public static LinkedList<String> clearedMilestone=new LinkedList<String>();
	public static LinkedList<String> estimates=new LinkedList<String>();
	public static LinkedList<String> milestoneDate=new LinkedList<String>();
	public static LinkedList<String> milestoneDesc=new LinkedList<String>();
	public static LinkedList<String> milestoneTitle=new LinkedList<String>();
	private static FileWriter temp;

	public Assembla(String addr,String filename){
		createTempFile(addr);
		File f=new File("temp.xml"); //from createTempFile(addr);
		readFile(f);
		retrieveMilestoneInfo(clearedMilestone,addr);
		CreateStandardXML c2=new CreateStandardXML(storyText,note,id,estimates,milestone,milestoneDesc,milestoneDate,milestoneTitle);
		c2.saveFile(filename);
		// deleting temp file: temp.xml and milestone.xml
		f.delete();
		File f2=new File("milestone.xml");
		f2.delete();
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
			temp=new FileWriter("temp.xml");
			while ((text = reader.readLine()) != null) 
			{
				temp.write(text);	 
			}
			temp.close();
			conn.disconnect();
		}catch(IOException ex){
			ex.printStackTrace();
		}

		return temp;
	}

	public static void readFile(File f){

		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			XPathFactory factory1 = XPathFactory.newInstance();
			XPath xpath = factory1.newXPath();
			XPathExpression expr;
			Object result;
			expr = xpath.compile("//ticket");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListTicket = (NodeList) result;
			for (int i = 0; i < nodeListTicket.getLength(); i++) {
				Element ticketElement = (Element) nodeListTicket.item(i);
				NodeList isStoryList = ticketElement.getElementsByTagName("is-story");
				Element isStoryElement = (Element) isStoryList.item(0);
				Text isStoryText = (Text) isStoryElement.getFirstChild();
				String isStory = isStoryText.getNodeValue();
				if (isStory.equals("true")){

					NodeList summaryList = ticketElement.getElementsByTagName("summary");
					Element summaryElement = (Element) summaryList.item(0);
					Text summaryText = (Text) summaryElement.getFirstChild();
					String summary = summaryText.getNodeValue();
					storyText.offer(summary);
					estimates.offer("");

					NodeList descList = ticketElement.getElementsByTagName("description");
					Element descElement = (Element) descList.item(0);
					Text descText = (Text) descElement.getFirstChild();
					String desc = descText.getNodeValue();
					note.offer(desc);

					NodeList idList = ticketElement.getElementsByTagName("id");
					Element idElement = (Element) idList.item(0);
					Text idText = (Text) idElement.getFirstChild();
					String idS = idText.getNodeValue();
					id.offer(idS);

					NodeList milestoneList = ticketElement.getElementsByTagName("milestone-id");
					Element milestoneElement = (Element) milestoneList.item(0);
					if ((Text) milestoneElement.getFirstChild()!=null){
						Text milestoneText = (Text) milestoneElement.getFirstChild();
						String milestoneS = milestoneText.getNodeValue();
						milestone.offer(milestoneS);
						milestoneC.offer(milestoneS);
					}
					else {
						milestone.offer("");
						milestoneC.offer("");
					}
				}
			}

			if (!milestoneC.isEmpty()){
				clearedMilestone=removeDuplicates(milestoneC);
			}

		}
		catch (XPathExpressionException e){}
		catch (ParserConfigurationException e){}
		catch (IOException e){}
		catch (SAXException e){}
	}

	public static void retrieveMilestoneInfo(LinkedList<String> cM,String addr){
		try{
			int pos=addr.indexOf("tickets");
			String addr3=addr.substring(pos, addr.length());
			String addr2=addr.replace(addr3, "milestones");
			URL url = new URL(addr2);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text;
			temp=new FileWriter("milestone.xml");
			while ((text = reader.readLine()) != null) 
			{
				temp.write(text);	 
			}
			temp.close();
			conn.disconnect();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("milestone.xml");
			XPathFactory factory1 = XPathFactory.newInstance();
			XPath xpath = factory1.newXPath();
			XPathExpression expr;
			Object result;
			for (int j=0;j<cM.size();j++){
				expr = xpath.compile("//milestone");
				result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodeListMilestone = (NodeList) result;
				for (int i = 0; i < nodeListMilestone.getLength(); i++) {

					Element milestoneElement = (Element) nodeListMilestone.item(i);
					NodeList idList = milestoneElement.getElementsByTagName("id");
					Element idElement = (Element) idList.item(0);
					Text idText = (Text) idElement.getFirstChild();
					String idT = idText.getNodeValue();
					if (cM.get(j).equals(idT)){

						NodeList descList = milestoneElement.getElementsByTagName("description");
						Element descElement = (Element) descList.item(0);
						Text descText = (Text) descElement.getFirstChild();
						String desc = descText.getNodeValue();
						milestoneDesc.offer(desc);

						NodeList dateList = milestoneElement.getElementsByTagName("created-at");
						Element dateElement = (Element) dateList.item(0);
						Text dateText = (Text) dateElement.getFirstChild();
						String date = dateText.getNodeValue();
						milestoneDate.offer(date);

						NodeList titleList = milestoneElement.getElementsByTagName("title");
						Element titleElement = (Element) titleList.item(0);
						Text titleText = (Text) titleElement.getFirstChild();
						String title = titleText.getNodeValue();
						milestoneTitle.offer(title);
					}
				}
			}
		}
		catch (XPathExpressionException e){}
		catch (ParserConfigurationException e){}
		catch (IOException e){}
		catch (SAXException e){}
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

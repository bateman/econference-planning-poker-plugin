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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Jira {
	public static LinkedList<String> note = new LinkedList<String>();
	public static LinkedList<String> storyText = new LinkedList<String>();
	public static LinkedList<String> id = new LinkedList<String>();
	public static LinkedList<String> idC = new LinkedList<String>();
	public static LinkedList<String> milestone = new LinkedList<String>();
	public static LinkedList<String> milestoneC = new LinkedList<String>();
	public static LinkedList<String> clearedMilestone = new LinkedList<String>();
	public static LinkedList<String> estimates = new LinkedList<String>();
	public static LinkedList<String> milestoneDesc = new LinkedList<String>();
	public static LinkedList<String> milestoneDate = new LinkedList<String>();
	public static LinkedList<String> milestoneTitle = new LinkedList<String>();
	private static FileWriter temp1;

	public Jira(String addr, String filename) {

		retrieveOpenTickets(addr);
		File f3 = new File("temp.xml"); // from createTempFile(addr);
		readFile(f3);
		CreateStandardXML c2 = new CreateStandardXML(storyText, note, id,
				estimates, milestone, milestoneDesc, milestoneDate,
				milestoneTitle);
		c2.saveFile(filename);
		// deleting temp file: temp.xml and milestone.xml
		f3.delete();

	}

	public static FileWriter retrieveOpenTickets(String addr) {
		try {
			// open tickets
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/rss+xml");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String text;
			temp1 = new FileWriter("temp.xml");
			while ((text = reader.readLine()) != null) {
				temp1.write(text);
			}
			temp1.close();
			conn.disconnect();

		} catch (IOException ex) {
		}
		return temp1;
	}

	public static void readFile(File f3) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(f3);
			XPathFactory factory1 = XPathFactory.newInstance();
			XPath xpath = factory1.newXPath();
			XPathExpression expr;
			Object result;

			expr = xpath.compile("//key");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListId = (NodeList) result;
			for (int i = 0; i < nodeListId.getLength(); i++) {

				String s = nodeListId.item(i).getTextContent().toString();
				s = s.replace("TST-", "");
				id.offer(s);
				idC.offer(s);
			}

			expr = xpath.compile("//summary");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListTitle = (NodeList) result;
			for (int i = 0; i < nodeListTitle.getLength(); i++) {
				storyText.offer(nodeListTitle.item(i).getTextContent());
				note.offer(nodeListTitle.item(i).getTextContent());

			}
			expr = xpath.compile("//environment");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListMile = (NodeList) result;
			for (int i = 0; i < nodeListMile.getLength(); i++) {

				String s = nodeListMile.item(i).getTextContent().toString();
				s = s.replace("<p>", "");
				s = s.replace("</p>", "");
				milestone.offer(s);
				milestoneC.offer(nodeListMile.item(i).getTextContent());
				// note.offer(s);

			}

			expr = xpath.compile("//@seconds");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListBody = (NodeList) result;
			for (int i = 0; i < nodeListBody.getLength(); i++) {
				estimates.offer(nodeListBody.item(i).getTextContent());

				// idC.offer(nodeListBody.item(i).getTextContent());

			}
			if (!milestoneC.isEmpty()) {
				clearedMilestone = eliminateDuplicates(milestoneC);
			}
		} catch (XPathExpressionException e) {
		} catch (ParserConfigurationException e) {
		} catch (IOException e) {
		} catch (SAXException e) {
		}
	}

	public static LinkedList<String> eliminateDuplicates(
			LinkedList<String> lista) {
		for (int i = 0; i < lista.size(); i++) {
			String curr = (String) lista.get(i);
			for (int j = i + 1; j < lista.size(); j++) {
				String tmp = (String) lista.get(j);
				if (curr.equals(tmp)) {
					lista.remove(j);
					j--;
				}
			}
		}
		return lista;
	}
}
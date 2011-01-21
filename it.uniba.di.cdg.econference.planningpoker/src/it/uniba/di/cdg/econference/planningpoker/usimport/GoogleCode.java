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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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

public class GoogleCode {

	public static LinkedList<String> note = new LinkedList<String>();
	public static LinkedList<String> storyText = new LinkedList<String>();
	public static LinkedList<String> id = new LinkedList<String>();
	public static LinkedList<String> idC = new LinkedList<String>();
	public static LinkedList<String> milestone = new LinkedList<String>();
	public static LinkedList<String> milestoneC = new LinkedList<String>();
	public static LinkedList<String> milestoneDesc = new LinkedList<String>();
	public static LinkedList<String> milestoneDate = new LinkedList<String>();
	public static LinkedList<String> milestoneTitle = new LinkedList<String>();
	public static LinkedList<String> clearedMilestone = new LinkedList<String>();
	public static LinkedList<String> estimates = new LinkedList<String>();
	private static FileReader in;
	private static FileWriter temp;
	private static String sub;
	private static FileWriter csv;

	public GoogleCode(String addr, String filename) {
		downloadCSV(addr); // temp.csv
		try {
			in = new FileReader("temp.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		csv2xml(in); // temp.xml
		File f = new File("temp.xml");
		readFile(f);
		retrieveStoryDescription(addr, idC);
		CreateStandardXML c2 = new CreateStandardXML(storyText, note, id,
				estimates, milestone, milestoneDesc, milestoneDate,
				milestoneTitle);
		c2.saveFile(filename);

		// deleting temp file: temp.xml and temp.csv
		f.delete();
		File f2 = new File("temp.csv");
		f2.delete();
	}

	public static FileWriter downloadCSV(String addr) {
		try {
			addr = addr.replace(" ", "+");
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/csv");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String text;
			StringWriter w = new StringWriter();
			while ((text = reader.readLine()) != null) {
				w.write(text);
			}
			String s = w.toString();
			s = s.replace(", ", "");
			s = s.replace(",\"\",", ",*,");
			s = s.replace("\"\"", "\n");
			s = s.replace("*", "");
			s = s.replace("\"", "");
			s = s.replace(",,", ",\"\",");

			csv = new FileWriter("temp.csv");
			csv.write(s);
			csv.close();
			conn.disconnect();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return csv;
	}

	public static FileWriter csv2xml(FileReader in) {

		BufferedReader reader = new BufferedReader(in);
		StringBuilder xml = new StringBuilder();
		String lineBreak = System.getProperty("line.separator");
		String line = null;
		List<String> headers = new ArrayList<String>();
		boolean isHeader = true;
		int count = 0;
		int entryCount = 1;
		xml.append("<root>");
		xml.append(lineBreak);
		try {
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				if (isHeader) {
					isHeader = false;
					while (tokenizer.hasMoreTokens()) {
						headers.add(tokenizer.nextToken());
					}
				} else {
					count = 0;
					xml.append("\t<entry");
					xml.append(">");
					xml.append(lineBreak);
					while (tokenizer.hasMoreTokens()) {
						xml.append("\t\t<");
						xml.append(headers.get(count));
						xml.append(">");
						xml.append(escapeXML(tokenizer.nextToken()));
						xml.append("</");
						xml.append(headers.get(count));
						xml.append(">");
						xml.append(lineBreak);
						count++;
					}
					xml.append("\t</entry>");
					xml.append(lineBreak);
					entryCount++;
				}
			}
		} catch (IOException e) {
		}
		xml.append("</root>");
		String text2 = xml.toString().replace("\"", "");
		try {
			temp = new FileWriter("temp.xml"); // from csv2xml;
			temp.write(text2);
			temp.close();
		} catch (IOException e) {
		}
		return temp;
	}

	public static void readFile(File f) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			XPathFactory factory1 = XPathFactory.newInstance();
			XPath xpath = factory1.newXPath();
			XPathExpression expr;
			Object result;

			expr = xpath.compile("//Milestone");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListMilestone = (NodeList) result;
			for (int i = 0; i < nodeListMilestone.getLength(); i++) {
				if (nodeListMilestone.item(i).getTextContent() != null) {
					milestone.offer(nodeListMilestone.item(i).getTextContent());
					milestoneC
							.offer(nodeListMilestone.item(i).getTextContent());
					estimates.offer("");
				}
			}

			expr = xpath.compile("//Summary");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListSummary = (NodeList) result;
			for (int i = 0; i < nodeListSummary.getLength(); i++) {
				if (nodeListSummary.item(i).getTextContent() != null) {
					storyText.offer(nodeListSummary.item(i).getTextContent());
				}
			}

			expr = xpath.compile("//ID");
			result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodeListID = (NodeList) result;
			for (int i = 0; i < nodeListID.getLength(); i++) {
				if (nodeListID.item(i).getTextContent() != null) {
					id.offer(nodeListID.item(i).getTextContent());
					idC.offer(nodeListID.item(i).getTextContent());
				}
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

	public static void retrieveStoryDescription(String addr,
			LinkedList<String> idC) {
		String addrV[] = addr.split("/");
		String pn = addrV[4];
		try {
			while (!idC.isEmpty()) {
				String url = "http://code.google.com/p/" + pn
						+ "/issues/detail?id=" + idC.poll().toString()
						+ "&can=1&q=type%3DStory&colspec=ID%20Type%20Summary";
				scrapeUrl(url);
			}
		} catch (java.lang.NullPointerException ec) {
		}
	}

	public static void scrapeUrl(String addr) {
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String text;
			StringWriter w = new StringWriter();
			while ((text = reader.readLine()) != null) {
				w.write(text);
			}
			int pos = w.toString().indexOf("<div class=\"author\">");
			String parte = w.toString().substring(pos);
			pos = parte.indexOf("</div>");
			int pos2 = parte.indexOf("</td>");
			sub = parte.substring(pos + 11, pos2 - 7);
			note.offer(sub.toString());
			conn.disconnect();
		} catch (IOException ex) {
		}
	}

	private static String escapeXML(String aText) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(
				aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '\"') {
				result.append("&quot;");
			} else if (character == '\'') {
				result.append("&#039;");
			} else if (character == '&') {
				result.append("&amp;");
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}

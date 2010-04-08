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
package it.uniba.di.cdg.econference.planningpoker.utils;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertToAgilePlannerXMLFormat {

	private Element t;
	private Element i;
	private static final int xloc=100;
	private static final int yloc=100;
	private static final int xlocI=100;
	private static final int ylocI=100;
	private Document doc;
	private static final String STATUS = "Defined";


	public ConvertToAgilePlannerXMLFormat(LinkedList<String> storyText,LinkedList<String> note,
			LinkedList<String> id, LinkedList<String> estimates, LinkedList<String> milestone,
			LinkedList<String> milestoneDesc, LinkedList<String> milestoneDate, LinkedList<String> milestoneTitle){

		LinkedList<String> clearedMilestone=new LinkedList<String>();
		LinkedList<String> milestoneCopy=new LinkedList<String>();

		for(int i=0;i<milestone.size();i++){
			if (milestone.get(i).equals("2"))
				milestone.set(i, "");
				milestoneCopy.offer(milestone.get(i));
		}

		if (!milestone.isEmpty()) {
			clearedMilestone = removeDuplicates(milestoneCopy);
		}

		if (milestoneDate!=null && !milestoneDate.isEmpty()) 
			convertMilestoneDate(milestoneDate);

		for(int i=0;i<milestoneTitle.size();i++){
			if (milestoneTitle.get(i).equals(""))
				milestoneTitle.remove(i);
		}

		for(int i=0;i<milestoneDate.size();i++){
			if (milestoneDate.get(i).equals(""))
				milestoneDate.remove(i);
		}
		milestoneTitle=removeDuplicates(milestoneTitle);
		milestoneDate=removeDuplicates(milestoneDate);

		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			doc= factory.newDocumentBuilder().newDocument();
			Element root= doc.createElement("Project");
			root.setAttribute("ID", "1");
			root.setAttribute("Name", "Import");
			doc.appendChild(root);
			Element backlog = doc.createElement("Backlog");
			backlog.setAttribute("Height","0");
			backlog.setAttribute("Width","0");
			backlog.setAttribute("ID","2");
			backlog.setAttribute("Name","Project Backlog");
			backlog.setAttribute("Parent","1");
			backlog.setAttribute("XLocation","0");
			backlog.setAttribute("YLocation","0");
			root.appendChild(backlog);
			
			
			if (milestone.isEmpty()){
				while (!storyText.isEmpty()){
					t = createStoryElement( storyText.poll(), note.poll(), id.poll(), estimates.poll());
					backlog.appendChild(t);					
					//this.xloc=this.xloc+50;
					//this.yloc=this.yloc+50;
				}
			}else {

				while(!clearedMilestone.isEmpty()){

					i= doc.createElement("Iteration");
					i.setAttribute("AvailableEffort","0.0");
					String idM =clearedMilestone.poll().toString();
					
					if (milestoneDesc!=null && !milestoneDesc.isEmpty() && (!idM.equals("")))
						i.setAttribute("Description",milestoneDesc.poll());
					else 
						i.setAttribute("Description",idM);
					
					
					i.setAttribute("EndDate","2010-10-27 00:00:00.0");
					i.setAttribute("Height","250");

					if (!idM.equals("")){
						
						i.setAttribute("ID",idM);
						
						if (milestoneTitle!=null && !milestoneTitle.isEmpty())
							i.setAttribute("Name",milestoneTitle.poll());
						else i.setAttribute("Name",idM);
						i.setAttribute("Parent","1");

						if (milestoneDate!=null  && !milestoneDate.isEmpty())
							i.setAttribute("StartDate",milestoneDate.poll());
						else 
							i.setAttribute("StartDate", createActualDate());
						
						i.setAttribute("Status","Defined");
						i.setAttribute("Width","400");
						i.setAttribute("XLocation",Integer.toString(xlocI));
						i.setAttribute("YLocation",Integer.toString(ylocI));
						root.appendChild(i);
					}
					for (int j=0;j<milestone.size();j++){
						if (idM.equals(milestone.get(j).toString()) && !idM.equals("")){
							//System.out.println("trovato"+idM);
							t=createStoryElement(storyText.get(j), note.get(j), id.get(j), estimates.get(j), idM);

							i.appendChild(t);

							/*this.xloc=this.xloc+50;
    		    		 this.yloc=this.yloc+50;
    		    		 this.xlocI=this.xlocI+220;
    		    		 this.ylocI=this.ylocI+200;*/
						}

						else
							if (idM.equals(milestone.get(j).toString()) && idM.equals("")){
								//System.out.println("trovato"+idM);
								t = createStoryElement((storyText.get(j)), (note.get(j)), (id.get(j)), (estimates.get(j)));
								backlog.appendChild(t);

								/*this.xloc=this.xloc+50;
        		    		 this.yloc=this.yloc+50;
        		    		 this.xlocI=this.xlocI+220;
        		    		 this.ylocI=this.ylocI+200;*/

							}
					}

				}
			}
				Element legend= doc.createElement("Legend");
				legend.setAttribute("aqua","aqua3");
				legend.setAttribute("blue","Feature");
				legend.setAttribute("gray","gray1");
				legend.setAttribute("green","Rally");
				legend.setAttribute("khaki","khaki1");
				legend.setAttribute("peach","peach2");
				legend.setAttribute("pink","pink1");
				legend.setAttribute("red","Bug");
				legend.setAttribute("white","UserStory");
				legend.setAttribute("yellow","yellow1");
				root.appendChild(legend); 
			
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private Element createStoryElement(	String storyText, String note,
			String id, String estimates, String idM) {

		Element t = doc.createElement("StoryCard");
		t.setAttribute("Actual","0.0");
		t.setAttribute("BestCase","0.0");
		t.setAttribute("CardOwner","TeamMember");
		t.setAttribute("Color","white");
		t.setAttribute("CurrentSideUp","0");
		t.setAttribute("Description",(note));
		t.setAttribute("Height","150");

		t.setAttribute("ID",id);

		if (estimates!=null && estimates!="")
			t.setAttribute("MostLikely", estimates);
		else 
			t.setAttribute("MostLikely", "0.0");

		t.setAttribute("Name",(storyText));

		if(idM!=null && idM!=""){
			
			t.setAttribute("Parent",idM);
		}else{
			t.setAttribute("Parent","2");			
		}

		t.setAttribute("RotationAngle","0.0");
		t.setAttribute("Status",STATUS);
		t.setAttribute("TestText","");
		t.setAttribute("TestURL","http://localhost/?something_else_is_new");
		t.setAttribute("TestText","");
		t.setAttribute("Width","320");
		t.setAttribute("WorstCase","0.0");
		t.setAttribute("XLocation",Integer.toString(xloc));
		t.setAttribute("YLocation",Integer.toString(yloc));
		t.setAttribute("RallyID","false");
		t.setAttribute("FitID","TestStorage.DefaultTest");
		return t;
	}


	private Element createStoryElement(	String storyText, String note,
			String id, String estimates) {
		return createStoryElement(storyText, note, id, estimates, "");
	}

	public String getXMLString(){ 	   
		String xmlString = "";
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			xmlString = result.getWriter().toString();

		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xmlString;	    	   
	}
	
	
	public LinkedList<String> removeDuplicates(LinkedList<String> list)
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



	public void convertMilestoneDate(LinkedList<String> milestoneDate){
		try{

			for (int i=0;i<milestoneDate.size();i++){
				String m=milestoneDate.poll().replace("T", " ");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss+mm:mm");
				Date d = format.parse(m);
				GregorianCalendar calendar=new GregorianCalendar();
				calendar.setTime(d);
				int day=calendar.get(Calendar.DATE);
				int mounth = calendar.get(Calendar.MONTH);
				mounth++;
				String mo=Integer.toString(mounth);
				String da=Integer.toString(day);
				if (mounth<10){
					mo=Integer.toString(mounth);
					mo="0"+mo;
				}

				if (day<10){
					da=Integer.toString(day);
					da="0"+da;
				}
				String date = calendar.get(Calendar.YEAR)+"-"+mo+"-"+da+" 00:00:00.0";
				milestoneDate.offer(date);
			}
		}catch (ParseException e){}
	}

	public String createActualDate(){
		Calendar calendar = new GregorianCalendar();
		calendar.getTime();
		int day=calendar.get(Calendar.DATE);
		int mounth = calendar.get(Calendar.MONTH);
		mounth++;
		String m=Integer.toString(mounth);
		String d=Integer.toString(day);

		if (mounth<10){
			m=Integer.toString(mounth);
			m="0"+m;
		}

		if (day<10){
			d=Integer.toString(day);
			d="0"+d;
		}
		String date=calendar.get(Calendar.YEAR)+"-"+m+"-"+d+" 00:00:00.0";
		return date;
	}
}

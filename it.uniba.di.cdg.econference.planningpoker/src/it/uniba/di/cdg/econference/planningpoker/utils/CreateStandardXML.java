package it.uniba.di.cdg.econference.planningpoker.utils;

import java.io.StringWriter;
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


public class CreateStandardXML {
	
	public Element t;
	public int id=6;
	public int xloc=100;
	public int yloc=100;
	private Document doc;

     public CreateStandardXML (LinkedList<String> storyText,LinkedList<String> note,
    		                   LinkedList<String> id,LinkedList<String> status,LinkedList<String> estimates){
    	 try {
    		 
//    		 // per ora lo chiamo standard XML poi metto una 
//    		 // sentinella per identificare la fonte
//    	 FileWriter f=new FileWriter("Standard.xml");
    	 
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
    	 
    	 for (int i=0;i<storyText.size();i++){
    		 t=doc.createElement("StoryCard");
    		 t.setAttribute("Actual","0.0");
       		 t.setAttribute("BestCase","0.0");
       		 t.setAttribute("CardOwner","TeamMember");
       		 t.setAttribute("Color","white");
       		 t.setAttribute("CurrentSideUp","0");
       		 t.setAttribute("Description",(note.poll()));
       		 t.setAttribute("Height","150");
       		 t.setAttribute("ID",(id.poll()));
       		 t.setAttribute("MostLikely", estimates.poll());
       		 t.setAttribute("Name",(storyText.poll()));
       		 t.setAttribute("Parent","2");
       		 t.setAttribute("RotationAngle","0.0");
       		 t.setAttribute("Status",(status.poll()));
       		 t.setAttribute("TestText","");
       		 t.setAttribute("TestURL","http://localhost/?something_else_is_new");
       		 t.setAttribute("TestText","");
       		 t.setAttribute("Width","320");
       		 t.setAttribute("WorstCase","0.0");
       		 t.setAttribute("XLocation",Integer.toString(this.xloc));
       		 t.setAttribute("YLocation",Integer.toString(this.yloc));
       		 t.setAttribute("RallyID","false");
       		 t.setAttribute("FitID","TestStorage.DefaultTest");

    		 backlog.appendChild(t);
    		 this.id=this.id+2;
    		 this.xloc=this.xloc+50;
    		 this.yloc=this.yloc+50;
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
    	 
    	    }
    	 
    	    catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;	    	   
     }
}

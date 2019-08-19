package kr.ac.halla.research;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EPCISConverter_cowslaughter {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		
		/**
		 * generate an EPCISDocument from a source(XMLFilePath) and store it to a destination(newXMLFilePath)
		 * 
		 * @param newXMLFilePath
		 * @param XMLFilePath
		 */
		
		// Read CowHistoryXml file use Dom parser.
		DocumentBuilderFactory factory_read = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder_read = factory_read.newDocumentBuilder();
		Document doc_read = builder_read.parse(XMLFilePath);
		
		/**There are ArrayLists that saved data of parsed xml **/
		/*  The following is a description of the data that will be stored in the ArrayList;
		 * idArr 	  : Save the data of identification number for cow (String)
		 * compArr 	  : Save the name of the company	(String)
		 * locaArr 	  : Save the company's location (String)
		 * dateArr 	  : Save the date the cow was slaughtered (String)
		 * inspectArr : Save the data passed status of sanitation test; (boolean)
		 *				If is passed="true", or not="false"  */
		ArrayList<String> idArr = new ArrayList<String>();
		ArrayList<String> compArr = new ArrayList<String>();
		ArrayList<String> locaArr = new ArrayList<String>();
		ArrayList<String> dateArr = new ArrayList<String>();
		ArrayList<String> rdateArr = new ArrayList<String>();
		ArrayList<Boolean> inspectArr = new ArrayList<Boolean>();
		
		Element root = doc_read.getDocumentElement();
		NodeList list = root.getElementsByTagName("row");
		
		// Loops by the size of the list and stores objects in the ArrayList.
		/* The following is a description of the xml's element name that will be parsed;
		 * ENTTY_IDNTFC_NO 	: Identification number of cow
		 * SLAU_PLC_NM		: Company's name
		 * SLAU_YMD			: Slaughtered date
		 * ADDR				: Company's location
		 * SNTT_PRSEC_PASS_ENNC : Passed status
		*/
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element) node;
				
				idArr.add(ele.getElementsByTagName("ENTTY_IDNTFC_NO").item(0).getTextContent());
				compArr.add(ele.getElementsByTagName("SLAU_PLC_NM").item(0).getTextContent());
				dateArr.add(ele.getElementsByTagName("SLAU_YMD").item(0).getTextContent());
				locaArr.add(ele.getElementsByTagName("ADDR").item(0).getTextContent());
				String a = ele.getElementsByTagName("SNTT_PRSEC_PASS_ENNC").item(0).getTextContent();
				String y = "Y";
				if(a.equals(y)) {
					inspectArr.add(true);
				}else {
					inspectArr.add(false);
				}
			}
		}
		for(int i = 0; i < dateArr.size(); i++) {
			String date = dateArr.get(i);
			String realDate = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8)+"T00:00:00.000+09:00";
			rdateArr.add(realDate);
		}
		
		
		// Write new xml file 
		DocumentBuilderFactory factory_write = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder_write = factory_write.newDocumentBuilder();
		Document doc_write = builder_write.newDocument();
		doc_write.setXmlStandalone(true);
		
		//Root Element's attribute part
				Element rootElement = doc_write.createElement("epcis:EPCISDocument");
				doc_write.appendChild(rootElement);
				
				// Set namespaces in root element
				rootElement.setAttribute("xmlns:epcis", "urn:epcglobal:epcis:xsd:1");
				rootElement.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
				rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
				rootElement.setAttribute("xmlns:cow", "http://210.93.116.66/epcis/schema/cow.xsd");
				rootElement.setAttribute("schemaVersion","1.2");
				Attr dateattr = doc_write.createAttribute("creationDate");
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.0Z'");
				dateattr.setValue(sdf.format(date));
				rootElement.setAttributeNode(dateattr);

				Element epcisBody = doc_write.createElement("EPCISBody");
				Element eventList = doc_write.createElement("EventList");
				rootElement.appendChild(epcisBody);
				epcisBody.appendChild(eventList);
				
				// Loops by the size of the list write the object's event datas.
				for(int i = 0; i<idArr.size(); i++) {
					Element objectEvent = doc_write.createElement("ObjectEvent");
					Element eventTime = doc_write.createElement("eventTime");
					Element eventTimeZoneOffset = doc_write.createElement("eventTimeZoneOffset");
					Element epcList = doc_write.createElement("epcList");
					Element epc = doc_write.createElement("epc");
					Element action = doc_write.createElement("action");
					Element bizStep = doc_write.createElement("bizStep");
					Element comp = doc_write.createElement("cow:company");
					Element test = doc_write.createElement("cow:passInspection");
					Element compAddr = doc_write.createElement("cow:companyAddress");
					
					eventList.appendChild(objectEvent);
					objectEvent.appendChild(eventTime);
					objectEvent.appendChild(eventTimeZoneOffset);
					objectEvent.appendChild(epcList);
					epcList.appendChild(epc);
					objectEvent.appendChild(action);
					objectEvent.appendChild(bizStep);
					objectEvent.appendChild(comp);
					objectEvent.appendChild(compAddr);
					objectEvent.appendChild(test);
					
					eventTime.setTextContent(rdateArr.get(i));
					eventTimeZoneOffset.setTextContent("+09:00");
					epc.setTextContent(idArr.get(i));
					action.setTextContent("OBSERVE");
					bizStep.setTextContent("urn:epcglobal:cbv:bizstep:killing");
					comp.setTextContent(compArr.get(i));
					compAddr.setTextContent(locaArr.get(i));
					test.setTextContent(inspectArr.get(i).toString());
				}
				
				// Can output the xml file use Transformer & TransformerFactory class.
				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "8");
				
				DOMSource source = new DOMSource(doc_write);
				FileWriter fileWriter = new FileWriter(newXMLFilePath);
				BufferedWriter bw =new BufferedWriter(fileWriter);
				StreamResult result = new StreamResult(bw);
				
				transformer.transform(source, result);
				
				StreamResult consoleResult = new StreamResult(System.out);
		        transformer.transform(source, consoleResult);
	}
}
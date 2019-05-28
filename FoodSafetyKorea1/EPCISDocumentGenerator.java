package org.dfpl.opendata.foodsafetykorea1;

import java.io.BufferedWriter;
import java.io.File;
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


public class EPCISDocumentGenerator {
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	/**
	 * generate an EPCISDocument from a source and store it to a destination
	 * 
	 * @param source
	 * @param destination
	 */
	
	public void generate(String source, String destination) {
		try {
			File fXmlFile = new File(source);
			BufferedWriter bf = new BufferedWriter(new FileWriter(destination));

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			Document doc2 = dBuilder.newDocument();
			
			doc2.setXmlStandalone(true);

			Element epcismd = doc2.createElement("epcismd:EPCISMasterDataDocument");
			doc2.appendChild(epcismd);
			
			Attr usesdf = doc2.createAttribute("creationDate");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
			usesdf.setValue(sdf.format(date));
			
			epcismd.setAttribute("xmlns:epcismd","urn:epcglobal:epcis-masterdata:xsd:1");
			epcismd.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
			epcismd.setAttribute("schemaVersion","1.0");
			epcismd.setAttributeNode(usesdf);
			
			Element EPCISBody = doc2.createElement("EPCISBody");
			epcismd.appendChild(EPCISBody);
			
			Element VocabularyList = doc2.createElement("VocabularyList");
			EPCISBody.appendChild(VocabularyList);
			
			Element Vocabulary = doc2.createElement("Vocabulary");
			VocabularyList.appendChild(Vocabulary);
			Vocabulary.setAttribute("type", "urn:epcglobal:epcis:vtype:EPCClass");
			
			Element VocabularyElementList = doc2.createElement("VocabularyElementList");
			Vocabulary.appendChild(VocabularyElementList);

			NodeList nList = doc.getElementsByTagName("row");

			ArrayList<Product> list = new ArrayList<Product>();

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					list.add(new Product(getTagValue("BAR_CD", eElement), getTagValue("PRDLST_NM", eElement),
							getTagValue("PRMS_DT", eElement), getTagValue("SITE_ADDR", eElement)));
					
					Element VocabularyElement = doc2.createElement("VocabularyElement");
					VocabularyElementList.appendChild(VocabularyElement);
					
					Attr VocabularyElementId = doc2.createAttribute("id");
					VocabularyElement.setAttributeNode(VocabularyElementId);
					VocabularyElementId.setValue(list.get(temp).getBarcode());
					
					Element BAR_CD = doc2.createElement("attribute");
					BAR_CD.setAttribute("id","BAR_CD");
					BAR_CD.setTextContent( list.get(temp).getBarcode());
					VocabularyElement.appendChild(BAR_CD);
			         
			        Element PRDLST_NM = doc2.createElement("attribute");
			        PRDLST_NM.setAttribute("id","PRDLST_NM");
			        PRDLST_NM.setTextContent( list.get(temp).getName());
			        VocabularyElement.appendChild(PRDLST_NM);
			         
			        Element PRMS_DT = doc2.createElement("attribute");
			        PRMS_DT.setAttribute("id","PRMS_DT");
			        PRMS_DT.setTextContent( list.get(temp).getDate());
			        VocabularyElement.appendChild(PRMS_DT);
			         
			        Element SITE_ADDR = doc2.createElement("attribute");
			        SITE_ADDR.setAttribute("id","SITE_ADDR");
			        SITE_ADDR.setTextContent( list.get(temp).getAddress());
			        VocabularyElement.appendChild(SITE_ADDR);
				}
			}
			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "8");
			
			DOMSource source2 = new DOMSource(doc2);
			StreamResult result = new StreamResult(bf);
			transformer.transform(source2, result);
			
			StreamResult consoleResult = new StreamResult(System.out);
	        transformer.transform(source2, consoleResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		EPCISDocumentGenerator EPCISgenerate = new EPCISDocumentGenerator();
		EPCISgenerate.generate(args[0], args[1]);
	}
}
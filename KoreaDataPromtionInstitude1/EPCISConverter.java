package KoreaDataPromtionInstitude1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class EPCISConverter {

	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {

		/**
		 * generate an EPCISDocument from a source and store it to a destination
		 * 
		 * @param YourXMLFileName
		 * @param YourCSVFilePath
		 */

		FileReader filereader = new FileReader(YourCSVFilePath);
		BufferedReader br = new BufferedReader(filereader);
		ArrayList<Values> arraylist = new ArrayList<Values>();

		int check = 0;

		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			Values value = new Values(line.split(",")[0], line.split(",")[1], line.split(",")[2], line.split(",")[3],
					line.split(",")[4], line.split(",")[5], line.split(",")[6], line.split(",")[7], line.split(",")[8],
					line.split(",")[9], line.split(",")[10], line.split(",")[11], line.split(",")[12],
					line.split(",")[13], line.split(",")[14], line.split(",")[15], line.split(",")[16],
					line.split(",")[17], line.split(",")[18], line.split(",")[19], line.split(",")[20]);

			for (int i = 0; i <= 20; i++) {
				if (line.split(",")[i].equals(""))
					check++;
			}
			if (check == 1) {
				arraylist.add(value);
				check = 0;
			}
			check = 0;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();

		Namespace namespace1 = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace2 = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespace3 = Namespace.getNamespace("demo", "http://ns.example.com/epcis");
		Namespace namespace4 = Namespace.getNamespace("epcis", "urn:epcglobal:epcis:xsd:1");
		Namespace namespace5 = Namespace.getNamespace("airplane", "http://210.93.116.66/epcis/airplane.xsd");

		Element element = new Element("EPCISDocument", namespace4);
		element.setAttribute("schemaVersion", "1.2");
		element.addNamespaceDeclaration(namespace1);
		element.addNamespaceDeclaration(namespace2);
		element.addNamespaceDeclaration(namespace3);
		element.addNamespaceDeclaration(namespace5);

		Element epcisBody = new Element("EPCISBody");
		element.addContent(epcisBody);

		Element airHistory = new Element("EventList");
		epcisBody.addContent(airHistory);

		for (int i = 0; i < arraylist.size(); i++) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

				String realtime = arraylist.get(i).getDate().substring(0, 11)
						+ arraylist.get(i).getChangedtime().substring(0, 2) + ":"
						+ arraylist.get(i).getChangedtime().substring(2, 4) + ":00.0";

				Element history = new Element("ObjectEvent");
				airHistory.addContent(history);

				Element date = new Element("eventTime");
				date.setText(sdf2.format(sdf.parse(realtime)));
				history.addContent(date);

				Element timezone = new Element("eventTimeZoneOffset");
				timezone.setText("+9:00");
				history.addContent(timezone);

				Element airplanemodel = new Element("epcList");
				history.addContent(airplanemodel);

				Element epc = new Element("epc");
				epc.setText(arraylist.get(i).getAirplanemodel());
				airplanemodel.addContent(epc);

				Element action = new Element("action");
				action.setText("OBSERVE");
				history.addContent(action);

				Element bizStep = new Element("bizStep");
				if (arraylist.get(i).getStateKR().equals("수속중"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:processing");

				else if (arraylist.get(i).getStateKR().equals("변경출구") && arraylist.get(i).getStateKR().equals("탑승구 변경"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:gate_change");

				else if (arraylist.get(i).getStateKR().equals("미정"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:none");

				else if (arraylist.get(i).getStateKR().equals("출발"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:departing");

				else if (arraylist.get(i).getStateKR().equals("지연"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:delay");

				else if (arraylist.get(i).getStateKR().equals("회항"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:diverted");

				else if (arraylist.get(i).getStateKR().equals("탑승중") && arraylist.get(i).getStateKR().equals("탑승최종"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:boarding");

				else if (arraylist.get(i).getStateKR().equals("탑승준비"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:preparing_board");

				else if (arraylist.get(i).getStateKR().equals("체크인오픈"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:check_in_open");

				else if (arraylist.get(i).getStateKR().equals("마감예정"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:due_to_close");

				else if (arraylist.get(i).getStateKR().equals("체크인마감"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:check_in_close");

				else if (arraylist.get(i).getStateKR().equals("도착"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:arriving");

				else if (arraylist.get(i).getStateKR().equals("대기"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:stand_by");

				else if (arraylist.get(i).getStateKR().equals("탑승장입장"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:go_to_gate");

				else if (arraylist.get(i).getStateKR().equals("결항"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:canceled");

				else if (arraylist.get(i).getStateKR().equals("탑승마감"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:gate_close");

				else if (arraylist.get(i).getStateKR().equals("수속중단"))
					bizStep.setText("urn:epcglobal:cbv:bizstep:hold");
				history.addContent(bizStep);

				Element bizLocation = new Element("bizLocation");
				history.addContent(bizLocation);

				Element bizLocationId = new Element("id");
				bizLocationId.setText(arraylist.get(i).getDepartedairportIATA());
				bizLocation.addContent(bizLocationId);

				Element extension = new Element("extension");
				history.addContent(extension);

				Element sourceList = new Element("sourceList");
				extension.addContent(sourceList);

				Element source = new Element("source");
				source.setText(arraylist.get(i).getDepartedairportIATA());
				source.setAttribute("type", "urn:epcglobal:cbv:sdt:location");
				sourceList.addContent(source);

				Element destinationList = new Element("destinationList");
				extension.addContent(destinationList);

				Element destination = new Element("destination");
				destination.setText(arraylist.get(i).getArrivedairprotIATA());
				destination.setAttribute("type", "urn:epcglobal:cbv:sdt:location");
				destinationList.addContent(destination);

				Element gate = new Element("gate");
				gate.setNamespace(namespace5);
				gate.setText(arraylist.get(i).getGatenumber());
				history.addContent(gate);

				Element isInternational = new Element("isInternational");
				isInternational.setNamespace(namespace5);
				if (arraylist.get(i).get국내국제().equals("국제"))
					isInternational.setText("true");
				else
					isInternational.setText("false");
				history.addContent(isInternational);

				Element isFerry = new Element("isFerry");
				isFerry.setNamespace(namespace5);
				if (arraylist.get(i).getType().equals("여객선"))
					isFerry.setText("true");
				else
					isFerry.setText("false");
				history.addContent(isFerry);

				Element company = new Element("company");
				company.setNamespace(namespace5);
				company.setText(arraylist.get(i).getCompanyIATA());
				history.addContent(company);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		Document doc = new Document(element);
		doc.setDocType(new DocType("project"));

		XMLOutputter xout = new XMLOutputter();
		Format fo = xout.getFormat();
		fo.setEncoding("UTF-8");
		fo.setIndent("         ");
		fo.setLineSeparator("\r\n");
		fo.setTextMode(Format.TextMode.TRIM);

		try {
			xout.setFormat(fo);
			xout.output(doc, new FileWriter(YourXMLFileName + ".xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Saved Success!!");

	}
}

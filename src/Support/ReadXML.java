package Support;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//deze klasse wordt gebruikt voor het inlezen van de xml files
public class ReadXML {

    public Queue<Process> createProcess(int i) throws SAXException, IOException, ParserConfigurationException {
        Queue<Process> processen = new LinkedList<>();
        File file = null;

        switch (i) {
            case(10000):
                file = new File("processen10000.xml");
                break;
            case(20000):
                file = new File("processen20000.xml");
                break;
            case(50000):
                file = new File("processen50000.xml");
                break;
        }

        DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuild = docfac.newDocumentBuilder();
        Document doc= docBuild.parse(file);
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("process");
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                int id =Integer.parseInt(element.getElementsByTagName("pid").item(0).getTextContent());
                int arrivaltime=Integer.parseInt(element.getElementsByTagName("arrivaltime").item(0).getTextContent());
                int servicetime=Integer.parseInt(element.getElementsByTagName("servicetime").item(0).getTextContent());
                Process p = new Process(id,arrivaltime,servicetime);
                processen.add(p);
            }
        }
        return processen;
    }
}
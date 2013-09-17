package org.telcomp.logic;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author enfasis2
 */
public class JobSearch {
    public static String url = "http://www.authenticjobs.com/api/?";
    public static final String apiKey = "82f8010418d07f1d0952020613e474b9";

    public static String getJobOffers(String keyword) {
        HttpClient client = new DefaultHttpClient();
        //keyword = getRandomKeyword();
        String responseBody = null;

        HttpGet request = new HttpGet(url + "api_key=" + apiKey + "&method=aj.jobs.search&keywords=" + keyword + "&perpage=3");
        try {
            HttpResponse response = client.execute(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException ex) {
            Logger.getLogger(JobSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

        return processXML(responseBody);
    }

    public static String processXML(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        String jobOffer = null;
        String companyName = null;
        Random rand = new Random();
        int value = rand.nextInt(3);
        
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            doc.getDocumentElement().normalize();
            
            Element rootElement = doc.getDocumentElement();
            NodeList listings = rootElement.getElementsByTagName("listings");
            Element e = (Element) listings.item(0);
            NodeList listing = e.getElementsByTagName("listing");
            Element e1 = (Element) listing.item(value);
            jobOffer = e1.getAttribute("description");
            jobOffer = jobOffer.replaceAll("\\<.*?>","");
            jobOffer = jobOffer.replaceAll("&.*?;","");
            NodeList company = e1.getElementsByTagName("company");
            if(company.getLength() != 0){
                Element e2 = (Element) company.item(0);
                companyName = e2.getAttribute("name");
            }
        } catch (SAXException ex) {
            Logger.getLogger(JobSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JobSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JobSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return companyName + " " + jobOffer;
    }
}

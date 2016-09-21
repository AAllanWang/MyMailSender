/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.mymailsender.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Allan
 */
public class ContactList {
    public ContactList(){
        contactList = new HashMap<String, Contact>();
        checkXmlFile();
        initFromXml();
    }
    private void checkXmlFile(){
        try {
          File xmlFile = new File(XML_FILE_PATH);
            if(!xmlFile.exists()){
                xmlFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(xmlFile, true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

		bw.flush();
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Root></Root>");
                bw.close();
		fileOutputStream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initFromXml(){
        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder  builder = builderFactory.newDocumentBuilder();
            docXml = builder.parse(new File(XML_FILE_PATH));
            docXml.getDocumentElement().normalize();
            Element elemRoot = docXml.getDocumentElement();
            if(null == elemRoot){
                return;
            }
            NodeList contactNodes = elemRoot.getChildNodes();
            if(null == contactNodes){
                return;
            }
            for(int i = 0 ; i<contactNodes.getLength();i++){
                Node nodeContact = contactNodes.item(i);
                if(nodeContact instanceof Element){
                    if("Contact".equals(nodeContact.getNodeName())){
                        Contact contact = getContactFromXmlNode(nodeContact);
                        contactList.put(contact.getKey(), contact);
                    }
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContactList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Contact getContactFromXmlNode(Node nodeContact){
        String title = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        NodeList elemNodes = nodeContact.getChildNodes();
        if(null == elemNodes){
            return null;
        }
        for(int i = 0 ; i<elemNodes.getLength();i++){
            Node node = elemNodes.item(i);
            if(node instanceof Element){
                if("Title".equals(node.getNodeName())){
                    title = node.getTextContent();
                }
                if("FirstName".equals(node.getNodeName())){
                    firstName = node.getTextContent();
                }
                if("LastName".equals(node.getNodeName())){
                    lastName = node.getTextContent();
                }
                if("Email".equals(node.getNodeName())){
                    email = node.getTextContent();
                }
            }
        }

        Contact contact = new Contact(firstName, lastName, title, email);
        return contact;

    }

    public void addContact(Contact contact){
        if(null != docXml){
            Element elemContact = docXml.createElement("Contact");
            Element elem = docXml.createElement("Title");
            elem.setTextContent(contact.getTitle());
            elemContact.appendChild(elem);
            elem = docXml.createElement("FirstName");
            elem.setTextContent(contact.getFirstName());
            elemContact.appendChild(elem);
            elem = docXml.createElement("LastName");
            elem.setTextContent(contact.getLastName());
            elemContact.appendChild(elem);
            elem = docXml.createElement("Email");
            elem.setTextContent(contact.getEmail());
            elemContact.appendChild(elem);
            docXml.getDocumentElement().appendChild(elemContact);
            doc2XmlFile(docXml,XML_FILE_PATH);
        }
        contactList.put(contact.getKey(), contact);
    }

    public void removeContact(Contact contact){
        boolean isFound = false;
        contactList.remove(contact.getKey());
        if(null == docXml){
            return;
        }

        Element elemRoot = docXml.getDocumentElement();
        if(null == elemRoot){
            return;
        }
        NodeList contactNodes = elemRoot.getChildNodes();
        if(null == contactNodes){
            return;
        }
        for(int i = 0 ; i<contactNodes.getLength();i++){
            Node nodeContact = contactNodes.item(i);
            if(nodeContact instanceof Element){
                if("Contact".equals(nodeContact.getNodeName())){
                    Contact tmpContact = getContactFromXmlNode(nodeContact);
                    if(tmpContact.equals(contact)){
                        elemRoot.removeChild(nodeContact);
                        isFound = true;
                        break;
                    }
                }
            }
        }
        if(isFound){
            doc2XmlFile(docXml,XML_FILE_PATH);
        }
    }

    private boolean doc2XmlFile(Document document, String filename){
        boolean ret = true;
        try{
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            }
        catch(Exception ex) {
            ret = false;
            ex.printStackTrace();
        }
        return ret;
    }

    private static void printElements(ContactList cl){
        HashMap<String,Contact> map = cl.getContactList();
        Set set = map.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    public HashMap<String,Contact> getContactList(){
        return contactList;
    }

    public static void main(String args[]) {
        ContactList cl = new ContactList();
        Contact c = new Contact("aa","ab","ac","ad");
        cl.addContact(c);


        printElements(cl);
        System.out.println(cl.getContactList().size());
        cl.removeContact(c);
        printElements(cl);
        System.out.println(cl.getContactList().size());
    }
    private final String XML_FILE_PATH = "contact_list.xml";
    private HashMap<String,Contact> contactList;
    private Document docXml;
}

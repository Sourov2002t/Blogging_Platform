package com.example.model;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class PostDAO {
    private String xmlFilePath;
    
    public PostDAO(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        initializeXMLFileIfNeeded();
    }
    
    private void initializeXMLFileIfNeeded() {
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            try {
                xmlFile.getParentFile().mkdirs();
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                
                // Create root element
                Element rootElement = doc.createElement("posts");
                doc.appendChild(rootElement);
                
                // Save the XML file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList nodeList = doc.getElementsByTagName("post");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Post post = new Post();
                    post.setId(element.getAttribute("id"));
                    post.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                    post.setContent(element.getElementsByTagName("content").item(0).getTextContent());
                    post.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
                    
                    // Parse date
                    String dateStr = element.getElementsByTagName("createdDate").item(0).getTextContent();
                    try {
                        post.setCreatedDate(dateFormat.parse(dateStr));
                    } catch (ParseException e) {
                        post.setCreatedDate(new Date());
                    }
                    
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return posts;
    }
    
    public Post getPostById(String id) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList nodeList = doc.getElementsByTagName("post");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("id").equals(id)) {
                        Post post = new Post();
                        post.setId(id);
                        post.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                        post.setContent(element.getElementsByTagName("content").item(0).getTextContent());
                        post.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
                        
                        // Parse date
                        String dateStr = element.getElementsByTagName("createdDate").item(0).getTextContent();
                        try {
                            post.setCreatedDate(dateFormat.parse(dateStr));
                        } catch (ParseException e) {
                            post.setCreatedDate(new Date());
                        }
                        
                        return post;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void addPost(Post post) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            // Generate a unique ID if not provided
            if (post.getId() == null || post.getId().isEmpty()) {
                post.setId(UUID.randomUUID().toString());
            }
            
            // Create post element
            Element postElement = doc.createElement("post");
            postElement.setAttribute("id", post.getId());
            
            // Create and add title element
            Element titleElement = doc.createElement("title");
            titleElement.setTextContent(post.getTitle());
            postElement.appendChild(titleElement);
            
            // Create and add content element
            Element contentElement = doc.createElement("content");
            contentElement.setTextContent(post.getContent());
            postElement.appendChild(contentElement);
            
            // Create and add author element
            Element authorElement = doc.createElement("author");
            authorElement.setTextContent(post.getAuthor());
            postElement.appendChild(authorElement);
            
            // Create and add date element
            Element dateElement = doc.createElement("createdDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateElement.setTextContent(dateFormat.format(post.getCreatedDate()));
            postElement.appendChild(dateElement);
            
            // Append the post to the root element
            doc.getDocumentElement().appendChild(postElement);
            
            // Save the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updatePost(Post post) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            NodeList nodeList = doc.getElementsByTagName("post");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("id").equals(post.getId())) {
                        // Update title
                        Node titleNode = element.getElementsByTagName("title").item(0);
                        titleNode.setTextContent(post.getTitle());
                        
                        // Update content
                        Node contentNode = element.getElementsByTagName("content").item(0);
                        contentNode.setTextContent(post.getContent());
                        
                        // Update author
                        Node authorNode = element.getElementsByTagName("author").item(0);
                        authorNode.setTextContent(post.getAuthor());
                        
                        // Update date
                        Node dateNode = element.getElementsByTagName("createdDate").item(0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateNode.setTextContent(dateFormat.format(post.getCreatedDate()));
                        
                        break;
                    }
                }
            }
            
            // Save the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deletePost(String id) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            NodeList nodeList = doc.getElementsByTagName("post");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("id").equals(id)) {
                        element.getParentNode().removeChild(element);
                        break;
                    }
                }
            }
            
            // Save the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
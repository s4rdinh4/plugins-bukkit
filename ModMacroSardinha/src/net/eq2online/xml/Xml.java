/*     */ package net.eq2online.xml;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import net.eq2online.console.Log;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Xml
/*     */ {
/*     */   private static XPath xpath;
/*  71 */   private static Xmlns staticNamespaceContext = new Xmlns();
/*     */   
/*     */   static {
/*  74 */     xpath = XPathFactory.newInstance().newXPath();
/*  75 */     xpath.setNamespaceContext(staticNamespaceContext);
/*     */ 
/*     */     
/*  78 */     documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  79 */     documentBuilderFactory.setNamespaceAware(true);
/*  80 */     createDocumentBuilder(documentBuilderFactory);
/*     */ 
/*     */     
/*  83 */     transformerFactory = TransformerFactory.newInstance();
/*  84 */     createTransformer(transformerFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   private static DocumentBuilderFactory documentBuilderFactory;
/*     */   
/*     */   private static DocumentBuilder documentBuilder;
/*     */   
/*     */   private static TransformerFactory transformerFactory;
/*     */   private static Transformer transformer;
/*     */   
/*     */   protected static boolean createDocumentBuilder(DocumentBuilderFactory factory) {
/*  96 */     if (documentBuilder == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 101 */         documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */       } catch (ParserConfigurationException e) {
/* 103 */         return false;
/*     */       } 
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean createTransformer(TransformerFactory factory) {
/* 118 */     if (transformer == null) {
/*     */ 
/*     */       
/*     */       try {
/* 122 */         transformer = transformerFactory.newTransformer();
/*     */       } catch (TransformerConfigurationException e) {
/* 124 */         return false;
/*     */       } 
/*     */       
/* 127 */       transformer.setOutputProperty("omit-xml-declaration", "no");
/* 128 */       transformer.setOutputProperty("indent", "yes");
/* 129 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document createDocument() {
/* 141 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.newDocument() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document getDocument(String uri) throws SAXException, IOException {
/* 151 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(uri) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document getDocument(File file) throws SAXException, IOException {
/* 161 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(file) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Document getDocument(InputStream stream) throws SAXException, IOException {
/* 166 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(stream) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean saveDocument(File file, Document document) {
/* 178 */     if (createTransformer(transformerFactory)) {
/*     */       
/*     */       try {
/*     */         
/* 182 */         transformer.transform(new DOMSource(document), new StreamResult(file));
/* 183 */         return true;
/*     */       }
/* 185 */       catch (TransformerException transformerException) {}
/*     */     }
/*     */     
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addNS(String prefix, String namespaceURI) {
/* 199 */     staticNamespaceContext.addPrefix(prefix, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearNS() {
/* 207 */     staticNamespaceContext.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setNamespaceContext(Xmlns namespaceContext) {
/* 217 */     if (namespaceContext != null)
/*     */     {
/* 219 */       staticNamespaceContext = namespaceContext;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList query(Node xml, String xPath) {
/* 231 */     return query(xml, xPath, staticNamespaceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList query(Node xml, String xPath, NamespaceContext namespaceContext) {
/*     */     try {
/* 238 */       xpath.setNamespaceContext(namespaceContext);
/*     */ 
/*     */       
/* 241 */       return (NodeList)xpath.evaluate(xPath, xml, XPathConstants.NODESET);
/*     */     }
/* 243 */     catch (XPathExpressionException ex) {
/*     */       
/* 245 */       Log.printStackTrace(ex);
/* 246 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Node> queryAsArray(Node xml, String xPath) {
/* 252 */     return queryAsArray(xml, xPath, staticNamespaceContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<Node> queryAsArray(Node xml, String xPath, NamespaceContext namespaceContext) {
/* 257 */     ArrayList<Node> nodes = new ArrayList<Node>();
/* 258 */     NodeList result = query(xml, xPath, namespaceContext);
/*     */     
/* 260 */     if (result != null)
/*     */     {
/* 262 */       for (int nodeIndex = 0; nodeIndex < result.getLength(); nodeIndex++) {
/* 263 */         nodes.add(result.item(nodeIndex));
/*     */       }
/*     */     }
/* 266 */     return nodes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Node getNode(Node node, String nodeName) {
/* 271 */     return getNode(node, nodeName, staticNamespaceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Node getNode(Node node, String nodeName, NamespaceContext namespaceContext) {
/*     */     try {
/* 278 */       xpath.setNamespaceContext(namespaceContext);
/* 279 */       return (Node)xpath.evaluate(nodeName, node, XPathConstants.NODE);
/*     */     }
/* 281 */     catch (XPathExpressionException xPathExpressionException) {
/*     */       
/* 283 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getNodeValue(Node node, String nodeName, String defaultValue) {
/* 288 */     return getNodeValue(node, nodeName, defaultValue, staticNamespaceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getNodeValue(Node node, String nodeName, String defaultValue, NamespaceContext namespaceContext) {
/*     */     try {
/* 295 */       xpath.setNamespaceContext(namespaceContext);
/* 296 */       NodeList nodes = (NodeList)xpath.evaluate(nodeName, node, XPathConstants.NODESET);
/*     */       
/* 298 */       if (nodes.getLength() > 0) {
/* 299 */         return nodes.item(0).getTextContent();
/*     */       }
/* 301 */     } catch (XPathExpressionException ex) {
/*     */       
/* 303 */       Log.printStackTrace(ex);
/*     */     } 
/*     */     
/* 306 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getNodeValue(Node node, String nodeName, int defaultValue) {
/* 311 */     return getNodeValue(node, nodeName, defaultValue, staticNamespaceContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getNodeValue(Node node, String nodeName, int defaultValue, NamespaceContext namespaceContext) {
/* 316 */     String nodeValue = getNodeValue(node, nodeName, "" + defaultValue, namespaceContext);
/*     */ 
/*     */     
/*     */     try {
/* 320 */       return Integer.parseInt(nodeValue);
/*     */     }
/* 322 */     catch (NumberFormatException ex) {
/*     */       
/* 324 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getAttributeValue(Node node, String attributeName, String defaultValue) {
/* 330 */     Node attribute = node.getAttributes().getNamedItem(attributeName);
/*     */     
/* 332 */     if (attribute != null)
/*     */     {
/* 334 */       return attribute.getTextContent();
/*     */     }
/*     */     
/* 337 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAttributeValue(Node node, String attributeName, int defaultValue) {
/* 342 */     String attributeValue = getAttributeValue(node, attributeName, "" + defaultValue);
/*     */ 
/*     */     
/*     */     try {
/* 346 */       return Integer.parseInt(attributeValue);
/*     */     }
/* 348 */     catch (NumberFormatException ex) {
/*     */       
/* 350 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\xml\Xml.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
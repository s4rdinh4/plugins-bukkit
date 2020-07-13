/*     */ package net.eq2online.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.InvalidPropertiesFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import net.eq2online.console.Log;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesXMLUtils
/*     */ {
/*     */   private static final String PROPS_DTD_URI = "http://eq2online.net/dtd/properties.dtd";
/*     */   private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, (entry|array)* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry key CDATA #REQUIRED><!ELEMENT array (element*) ><!ATTLIST array key CDATA #REQUIRED><!ATTLIST array type (boolean|int|string) #REQUIRED ><!ELEMENT element (#PCDATA) ><!ATTLIST element pos CDATA \"0\">";
/*     */   private static final String EXTERNAL_XML_VERSION = "1.0";
/*     */   
/*     */   public static boolean load(Properties props, IArrayStorageBundle arrayStorage, InputStream in) throws IOException, InvalidPropertiesFormatException {
/*  87 */     Document doc = null;
/*     */     
/*     */     try {
/*  90 */       in.mark(1048576);
/*  91 */       doc = getLoadingDoc(in);
/*     */     }
/*  93 */     catch (SAXException saxe) {
/*     */       
/*  95 */       Log.info("Outdated variable store found, updating to new format");
/*  96 */       in.reset();
/*  97 */       props.loadFromXML(in);
/*  98 */       return true;
/*     */     } 
/* 100 */     Element propertiesElement = (Element)doc.getChildNodes().item(1);
/* 101 */     String xmlVersion = propertiesElement.getAttribute("version");
/* 102 */     if (xmlVersion.compareTo("1.0") > 0) {
/* 103 */       throw new InvalidPropertiesFormatException("Exported Properties file format version " + xmlVersion + " is not supported. This java installation can read" + " versions " + "1.0" + " or older. You" + " may need to install a newer version of JDK.");
/*     */     }
/* 105 */     importProperties(props, arrayStorage, propertiesElement);
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Document getLoadingDoc(InputStream in) throws SAXException, IOException {
/* 113 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 114 */     dbf.setIgnoringElementContentWhitespace(true);
/* 115 */     dbf.setValidating(true);
/* 116 */     dbf.setCoalescing(true);
/* 117 */     dbf.setIgnoringComments(true);
/*     */     
/*     */     try {
/* 120 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 121 */       db.setEntityResolver(new Resolver());
/* 122 */       db.setErrorHandler(new EH());
/* 123 */       InputSource is = new InputSource(in);
/* 124 */       return db.parse(is);
/*     */     }
/* 126 */     catch (ParserConfigurationException x) {
/*     */       
/* 128 */       throw new Error(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void importProperties(IArrayStorageBundle arrayStorage, Node propertiesNode) {
/* 134 */     if (propertiesNode instanceof Element)
/*     */     {
/* 136 */       importProperties(null, arrayStorage, (Element)propertiesNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void importProperties(Properties props, IArrayStorageBundle arrayStorage, Element propertiesElement) {
/* 142 */     arrayStorage.preDeserialise();
/*     */     
/* 144 */     NodeList entries = propertiesElement.getChildNodes();
/* 145 */     int numEntries = entries.getLength();
/* 146 */     int start = (numEntries > 0 && entries.item(0).getNodeName().equals("comment")) ? 1 : 0;
/* 147 */     for (int i = start; i < numEntries; i++) {
/*     */       
/* 149 */       if (entries.item(i) != null && entries.item(i) instanceof Element) {
/*     */         
/* 151 */         Element entry = (Element)entries.item(i);
/* 152 */         if (entry.hasAttribute("key")) {
/*     */           
/* 154 */           String key = entry.getAttribute("key");
/* 155 */           if (entry.getTagName().equals("array") && entry.hasAttribute("type")) {
/*     */             
/* 157 */             String type = entry.getAttribute("type");
/* 158 */             Map<Integer, ?> arrayMap = deserialiseArray(entry, type);
/* 159 */             Map<String, Map<Integer, ?>> arrays = arrayStorage.getStorage(type);
/* 160 */             if (arrays != null)
/*     */             {
/* 162 */               arrays.put(key, arrayMap);
/*     */             }
/*     */             else
/*     */             {
/* 166 */               Log.info("Couldn't find array storage for type \"" + type + "\"!");
/*     */             }
/*     */           
/* 169 */           } else if (props != null) {
/*     */             
/* 171 */             Node n = entry.getFirstChild();
/* 172 */             String val = (n == null) ? "" : n.getNodeValue();
/* 173 */             props.setProperty(key, val);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 178 */     arrayStorage.postDeserialise();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Integer, ?> deserialiseArray(Element entry, String arrayType) {
/* 183 */     TreeMap<Integer, Object> arrayMap = new TreeMap<Integer, Object>();
/*     */     
/* 185 */     NodeList entries = entry.getChildNodes();
/* 186 */     int numEntries = entries.getLength();
/* 187 */     for (int i = 0; i < numEntries; i++) {
/*     */       
/* 189 */       if (entries.item(i) instanceof Element) {
/*     */         
/* 191 */         Element arrayElement = (Element)entries.item(i);
/* 192 */         if (arrayElement.hasAttribute("pos")) {
/* 193 */           Integer pos = Integer.valueOf(Integer.parseInt(arrayElement.getAttribute("pos")));
/* 194 */           Node node = arrayElement.getFirstChild();
/*     */           
/* 196 */           if (node != null && node.getNodeValue().length() > 0)
/*     */           {
/* 198 */             arrayMap.put(pos, getNodeValue(arrayType, node));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 203 */     return arrayMap;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object getNodeValue(String arrayType, Node node) throws DOMException, NumberFormatException {
/* 208 */     if (arrayType.equals("boolean"))
/*     */     {
/* 210 */       return Boolean.valueOf(Boolean.parseBoolean(node.getNodeValue()));
/*     */     }
/* 212 */     if (arrayType.equals("int"))
/*     */     {
/* 214 */       return Integer.valueOf(Integer.parseInt(node.getNodeValue()));
/*     */     }
/*     */     
/* 217 */     return node.getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void save(Properties props, IArrayStorageBundle arrayStorage, OutputStream os, String comment, String encoding) throws IOException {
/* 223 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 224 */     DocumentBuilder documentBuilder = null;
/*     */     
/*     */     try {
/* 227 */       documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */     }
/* 229 */     catch (ParserConfigurationException pce) {
/*     */       assert false;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (documentBuilder != null) {
/*     */       
/* 236 */       Document doc = documentBuilder.newDocument();
/* 237 */       Element properties = (Element)doc.appendChild(doc.createElement("properties"));
/*     */       
/* 239 */       if (comment != null) {
/*     */         
/* 241 */         Element comments = (Element)properties.appendChild(doc.createElement("comment"));
/* 242 */         comments.appendChild(doc.createTextNode(comment));
/*     */       } 
/*     */       
/* 245 */       Iterator<String> propertiesIterator = props.keySet().iterator();
/* 246 */       while (propertiesIterator.hasNext()) {
/*     */         
/* 248 */         String key = propertiesIterator.next();
/* 249 */         Element entry = (Element)properties.appendChild(doc.createElement("entry"));
/* 250 */         entry.setAttribute("key", key);
/* 251 */         entry.appendChild(doc.createTextNode(props.getProperty(key)));
/*     */       } 
/*     */       
/* 254 */       arrayStorage.preSerialise();
/*     */       
/* 256 */       for (String storageType : arrayStorage.getStorageTypes())
/*     */       {
/* 258 */         serialiseArrays(storageType, arrayStorage.getStorage(storageType), doc, properties);
/*     */       }
/*     */       
/* 261 */       arrayStorage.postSerialise();
/*     */       
/* 263 */       emitDocument(doc, os, encoding);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void serialiseArrays(String type, Map<String, Map<Integer, ?>> arrays, Document doc, Element properties) throws DOMException {
/* 269 */     for (Map.Entry<String, Map<Integer, ?>> arrayEntry : arrays.entrySet()) {
/*     */       
/* 271 */       Element entry = (Element)properties.appendChild(doc.createElement("array"));
/* 272 */       entry.setAttribute("key", arrayEntry.getKey());
/* 273 */       entry.setAttribute("type", type);
/*     */       
/* 275 */       for (Map.Entry<Integer, ?> mapEntry : (Iterable<Map.Entry<Integer, ?>>)((Map)arrayEntry.getValue()).entrySet()) {
/*     */         
/* 277 */         Element arrayElement = (Element)entry.appendChild(doc.createElement("element"));
/* 278 */         arrayElement.setAttribute("pos", String.valueOf(mapEntry.getKey()));
/* 279 */         arrayElement.appendChild(doc.createTextNode(String.valueOf(mapEntry.getValue())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void emitDocument(Document doc, OutputStream os, String encoding) throws IOException {
/* 286 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 287 */     Transformer transformer = null;
/*     */     
/*     */     try {
/* 290 */       transformer = transformerFactory.newTransformer();
/* 291 */       transformer.setOutputProperty("doctype-system", "http://eq2online.net/dtd/properties.dtd");
/* 292 */       transformer.setOutputProperty("indent", "yes");
/* 293 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
/* 294 */       transformer.setOutputProperty("method", "xml");
/* 295 */       transformer.setOutputProperty("encoding", encoding);
/* 296 */       transformer.setOutputProperty("omit-xml-declaration", "no");
/*     */     }
/* 298 */     catch (TransformerConfigurationException tce) {
/*     */       assert false;
/*     */     } 
/*     */     
/* 302 */     DOMSource doms = new DOMSource(doc);
/* 303 */     StreamResult sr = new StreamResult(os);
/*     */     
/*     */     try {
/* 306 */       if (transformer != null)
/*     */       {
/* 308 */         transformer.transform(doms, sr);
/*     */       }
/*     */     }
/* 311 */     catch (TransformerException te) {
/*     */       
/* 313 */       IOException ioe = new IOException();
/* 314 */       ioe.initCause(te);
/* 315 */       throw ioe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Resolver
/*     */     implements EntityResolver {
/*     */     private Resolver() {}
/*     */     
/*     */     public InputSource resolveEntity(String pid, String sid) throws SAXException {
/* 324 */       if (sid.equals("http://eq2online.net/dtd/properties.dtd")) {
/*     */ 
/*     */         
/* 327 */         InputSource is = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, (entry|array)* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry key CDATA #REQUIRED><!ELEMENT array (element*) ><!ATTLIST array key CDATA #REQUIRED><!ATTLIST array type (boolean|int|string) #REQUIRED ><!ELEMENT element (#PCDATA) ><!ATTLIST element pos CDATA \"0\">"));
/* 328 */         is.setSystemId("http://eq2online.net/dtd/properties.dtd");
/* 329 */         return is;
/*     */       } 
/* 331 */       throw new SAXException("Invalid system identifier: " + sid);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EH
/*     */     implements ErrorHandler {
/*     */     private EH() {}
/*     */     
/*     */     public void error(SAXParseException x) throws SAXException {
/* 340 */       throw x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fatalError(SAXParseException x) throws SAXException {
/* 346 */       throw x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void warning(SAXParseException x) throws SAXException {
/* 352 */       throw x;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\xml\PropertiesXMLUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.xml.Xml;
/*     */ import net.eq2online.xml.Xmlns;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ public class Documentor
/*     */   implements IDocumentor
/*     */ {
/*     */   public static Documentor instance;
/*  22 */   private Xmlns namespaceContext = new Xmlns();
/*     */ 
/*     */   
/*     */   private Document xml;
/*     */ 
/*     */   
/*     */   private Element root;
/*     */ 
/*     */   
/*  31 */   private Map<String, IDocumentationEntry> actionDoc = new TreeMap<String, IDocumentationEntry>();
/*     */ 
/*     */   
/*     */   public static Documentor getInstance() {
/*  35 */     if (instance == null)
/*     */     {
/*  37 */       instance = new Documentor();
/*     */     }
/*     */     
/*  40 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private Documentor() {
/*  45 */     Xml.addNS("sa", "http://eq2online.net/macros/scriptaction");
/*  46 */     this.xml = Xml.createDocument();
/*     */     
/*  48 */     this.root = this.xml.createElement("scriptactions");
/*  49 */     this.root.setAttribute("xmlns:sa", "http://eq2online.net/macros/scriptaction");
/*  50 */     this.xml.appendChild(this.root);
/*     */     
/*  52 */     this.namespaceContext.addPrefix("sa", "http://eq2online.net/macros/scriptaction");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentationEntry getDocumentation(String scriptActionName) {
/*  58 */     return this.actionDoc.get(scriptActionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentation(IScriptAction scriptAction) {
/*  64 */     this.actionDoc.put(scriptAction.toString(), getDocumentation(scriptAction));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentationEntry getDocumentation(IScriptAction scriptAction) {
/*  70 */     Xml.addNS("sa", "http://eq2online.net/macros/scriptaction");
/*  71 */     String xPath = "/scriptactions/sa:" + scriptAction.getClass().getName().toLowerCase().replace(".", "/sa:");
/*  72 */     NodeList nodes = Xml.query(this.xml, xPath, (NamespaceContext)this.namespaceContext);
/*     */     
/*  74 */     if (nodes.getLength() > 0)
/*     */     {
/*  76 */       return new DocumentationEntry(nodes.item(0));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendScriptActionNode(IScriptAction scriptAction) {
/*  87 */     String[] parts = scriptAction.getClass().getName().toLowerCase().split("\\.");
/*  88 */     Element parent = this.root;
/*     */     
/*  90 */     for (int i = 0; i < parts.length; i++) {
/*     */       
/*  92 */       Element subNode = createOrGetElement(parent, "sa:" + parts[i]);
/*  93 */       parent = subNode;
/*     */       
/*  95 */       if (i == parts.length - 1) {
/*     */         
/*  97 */         parent.setAttribute("hidden", "false");
/*     */         
/*  99 */         Element nameElement = this.xml.createElement("sa:name");
/* 100 */         nameElement.setTextContent(scriptAction.toString().toUpperCase());
/* 101 */         parent.appendChild(nameElement);
/*     */         
/* 103 */         Element usageElement = this.xml.createElement("sa:usage");
/* 104 */         usageElement.setTextContent(scriptAction.toString().toUpperCase() + "()");
/* 105 */         parent.appendChild(usageElement);
/*     */         
/* 107 */         Element descriptionElement = this.xml.createElement("sa:description");
/* 108 */         descriptionElement.setTextContent("desc");
/* 109 */         parent.appendChild(descriptionElement);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Element createOrGetElement(Element parent, String nodeName) throws DOMException {
/* 116 */     NodeList childNodes = parent.getChildNodes();
/*     */     
/* 118 */     for (int j = 0; j < childNodes.getLength(); j++) {
/*     */       
/* 120 */       if (nodeName.equals(childNodes.item(j).getNodeName())) {
/* 121 */         return (Element)childNodes.item(j);
/*     */       }
/*     */     } 
/* 124 */     Element subNode = this.xml.createElement(nodeName);
/* 125 */     parent.appendChild(subNode);
/* 126 */     return subNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentor loadXml(String language) {
/*     */     try {
/* 134 */       InputStream is = Documentor.class.getResourceAsStream("/lang/macros/scripting/" + language + ".xml");
/* 135 */       if (is == null) Documentor.class.getResourceAsStream("/lang/macros/scripting/en_GB.xml");
/*     */       
/* 137 */       if (is != null)
/*     */       {
/* 139 */         Xml.addNS("sa", "http://eq2online.net/macros/scriptaction");
/* 140 */         this.xml = Xml.getDocument(is);
/*     */       }
/*     */     
/* 143 */     } catch (Exception ex) {
/*     */       
/* 145 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeXml(File xmlFile) {
/* 154 */     Xml.saveDocument(xmlFile, this.xml);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getEntries() {
/* 159 */     return this.actionDoc.keySet();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\Documentor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
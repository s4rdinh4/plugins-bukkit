/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import cvf;
/*    */ import cwi;
/*    */ import cwk;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import oa;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlaySoundResourcePack
/*    */   extends cvf
/*    */ {
/*    */   public static final String CUSTOM_SOUND_PATH = "sounds/custom/";
/*    */   public static final String CUSTOM_SOUND_EXTENSION = ".ogg";
/*    */   private final String namespace;
/*    */   private final Set<String> availableNamespaces;
/*    */   
/*    */   public PlaySoundResourcePack(String namespace, File soundsPath) {
/* 28 */     super(soundsPath);
/*    */     
/* 30 */     this.namespace = namespace;
/* 31 */     this.availableNamespaces = new HashSet<String>(Arrays.asList(new String[] { this.namespace }));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean b(oa location) {
/* 37 */     if (location.b().equals(this.namespace) && location.a().startsWith("sounds/custom/") && location.a().endsWith(".ogg")) {
/*    */       
/* 39 */       File targetFile = new File(this.a, location.a().substring("sounds/custom/".length()));
/* 40 */       return targetFile.exists();
/*    */     } 
/*    */     
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream a(oa location) throws IOException {
/* 49 */     if (location.b().equals(this.namespace) && location.a().startsWith("sounds/custom/") && location.a().endsWith(".ogg")) {
/*    */       
/* 51 */       File targetFile = new File(this.a, location.a().substring("sounds/custom/".length()));
/* 52 */       return new BufferedInputStream(new FileInputStream(targetFile));
/*    */     } 
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public cwi a(cwk par1MetadataSerializer, String par2Str) throws IOException {
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> c() {
/* 67 */     return this.availableNamespaces;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String b() {
/* 73 */     return "PLAYSOUND resource pool";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\PlaySoundResourcePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
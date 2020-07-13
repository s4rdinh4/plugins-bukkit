/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.APIVersion;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacrosAPIModule;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleLoader
/*     */ {
/*     */   private static final int API_VERSION_MIN = 17;
/*     */   private static final int API_VERSION = 18;
/*     */   private final File modulesDir;
/*     */   
/*     */   public ModuleLoader(File macrosPath) {
/*  32 */     this.modulesDir = new File(macrosPath, "/modules");
/*     */ 
/*     */     
/*     */     try {
/*  36 */       this.modulesDir.mkdirs();
/*     */     }
/*  38 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadModules(IErrorLogger logger) {
/*  46 */     if (this.modulesDir.exists() && this.modulesDir.isDirectory()) {
/*     */       
/*     */       try {
/*     */         
/*  50 */         LaunchClassLoader classLoader = (LaunchClassLoader)ScriptCore.class.getClassLoader();
/*     */ 
/*     */         
/*  53 */         ArrayList<File> moduleFiles = new ArrayList<File>();
/*     */         
/*  55 */         for (File file : this.modulesDir.listFiles(new FilenameFilter() {
/*     */               public boolean accept(File dir, String name) {
/*  57 */                 return (name.startsWith("module_") && (name.endsWith(".zip") || name.endsWith(".jar")));
/*     */               }
/*     */             }))
/*     */         {
/*  61 */           moduleFiles.add(file);
/*     */         }
/*     */ 
/*     */         
/*  65 */         for (File module : moduleFiles) {
/*     */           
/*  67 */           if (module.isFile())
/*     */           {
/*  69 */             classLoader.addURL(module.toURI().toURL());
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  74 */         for (File module : moduleFiles)
/*     */         {
/*  76 */           LoadedModuleInfo lmi = new LoadedModuleInfo(module);
/*     */           
/*  78 */           if (!module.isFile() || module.getName().startsWith("module_"));
/*     */           
/*  80 */           ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(module));
/*     */ 
/*     */           
/*     */           while (true) {
/*  84 */             ZipEntry zipentry = zipinputstream.getNextEntry();
/*  85 */             if (zipentry == null)
/*     */               break; 
/*  87 */             String className = (new File(zipentry.getName())).getName();
/*     */             
/*  89 */             if (!zipentry.isDirectory() && className.endsWith(".class") && !className.contains("$")) {
/*     */               IMacroEventProvider iMacroEventProvider;
/*  91 */               String fullClassName = zipentry.getName().split("\\.")[0];
/*  92 */               IMacrosAPIModule newModule = null;
/*  93 */               boolean ignoreNull = false;
/*     */               
/*  95 */               if (className.startsWith("ScriptAction")) {
/*     */                 
/*  97 */                 IScriptAction iScriptAction = lmi.addAction(addModule((ClassLoader)classLoader, IScriptAction.class, "action", fullClassName, logger));
/*     */               }
/*  99 */               else if (className.startsWith("VariableProvider")) {
/*     */                 
/* 101 */                 IVariableProvider iVariableProvider = lmi.addProvider(addModule((ClassLoader)classLoader, IVariableProvider.class, "variable provider", fullClassName, logger));
/*     */               }
/* 103 */               else if (className.startsWith("ScriptedIterator")) {
/*     */                 
/* 105 */                 IScriptedIterator iScriptedIterator = lmi.addIterator(addModule((ClassLoader)classLoader, IScriptedIterator.class, "iterator", fullClassName, logger));
/* 106 */                 ignoreNull = true;
/*     */               }
/* 108 */               else if (className.startsWith("EventProvider")) {
/*     */                 
/* 110 */                 iMacroEventProvider = lmi.addEventProvider(addModule((ClassLoader)classLoader, IMacroEventProvider.class, "event provider", fullClassName, logger));
/*     */               } else {
/*     */                 continue;
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 117 */               if (iMacroEventProvider == null && !ignoreNull) {
/*     */                 
/* 119 */                 if (logger != null) logger.logError("API: Error initialising " + module.getName());
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 126 */           zipinputstream.close();
/*     */ 
/*     */           
/* 129 */           lmi.printStatus();
/*     */         }
/*     */       
/* 132 */       } catch (Throwable throwable) {}
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
/*     */ 
/*     */ 
/*     */   
/*     */   private <ModuleType extends IMacrosAPIModule> ModuleType addModule(ClassLoader classLoader, Class<ModuleType> moduleClassType, String moduleType, String className, IErrorLogger logger) {
/*     */     try {
/* 148 */       className = className.replace('/', '.');
/* 149 */       Class<?> moduleClass = classLoader.loadClass(className);
/*     */       
/* 151 */       if (moduleClassType.isAssignableFrom(moduleClass))
/*     */       {
/* 153 */         if (!checkAPIVersion(moduleClass)) {
/*     */           
/* 155 */           Log.info("Macros: API Error. Not loading custom {0} in {1}, bad API version.", new Object[] { moduleType, className });
/* 156 */           if (logger != null) logger.logError("API: Not loading " + moduleClass.getSimpleName() + ", bad API version"); 
/* 157 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 163 */           IMacrosAPIModule iMacrosAPIModule = (IMacrosAPIModule)moduleClass.newInstance();
/*     */           
/* 165 */           if (iMacrosAPIModule != null)
/*     */           {
/* 167 */             iMacrosAPIModule.onInit();
/* 168 */             return (ModuleType)iMacrosAPIModule;
/*     */           }
/*     */         
/* 171 */         } catch (Exception exception) {}
/*     */       }
/*     */     
/* 174 */     } catch (Throwable ex) {
/*     */       
/* 176 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean checkAPIVersion(Class<?> moduleClass) {
/* 190 */     APIVersion versionAnnotation = moduleClass.<APIVersion>getAnnotation(APIVersion.class);
/* 191 */     if (versionAnnotation != null) {
/*     */       
/* 193 */       int version = versionAnnotation.value();
/* 194 */       return (version >= 17 && version <= 18);
/*     */     } 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\ModuleLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.lang.reflect.Constructor;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
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
/*     */ public class MacroEvent
/*     */   implements IMacroEvent
/*     */ {
/*     */   protected IMacroEventProvider provider;
/*     */   protected String name;
/*     */   protected Constructor<IMacroEventVariableProvider> providerConstructor;
/*     */   protected boolean permissible = false;
/*  32 */   protected String permissionGroup = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroEvent(IMacroEventProvider provider, String name, boolean permissible, String permissionGroup, Icon icon) {
/*  44 */     this.provider = provider;
/*  45 */     this.name = name;
/*  46 */     this.permissible = permissible;
/*  47 */     this.permissionGroup = permissionGroup;
/*  48 */     this.icon = icon;
/*     */     
/*  50 */     initProviderClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProviderClass() {
/*  59 */     String eventClassName = this.name;
/*     */     
/*  61 */     if (eventClassName.startsWith("on"))
/*     */     {
/*  63 */       eventClassName = "O" + eventClassName.substring(1);
/*     */     }
/*     */     
/*  66 */     if (eventClassName.startsWith("On")) {
/*     */       
/*     */       try {
/*     */         
/*  70 */         String packageName = "net.eq2online.macros.event";
/*  71 */         Class<? extends MacroEvent> eventClass = (Class)getClass();
/*     */ 
/*     */         
/*     */         try {
/*  75 */           Package eventPackage = eventClass.getPackage();
/*     */           
/*  77 */           if (eventPackage == null)
/*     */           {
/*  79 */             String className = eventClass.getName();
/*  80 */             int classNameLength = eventClass.getSimpleName().length();
/*  81 */             packageName = eventClass.getName().substring(0, className.length() - classNameLength - 1);
/*     */           }
/*     */           else
/*     */           {
/*  85 */             packageName = eventPackage.getName();
/*     */           }
/*     */         
/*  88 */         } catch (Exception exception) {}
/*     */         
/*  90 */         String providerClassName = packageName + ".providers." + eventClassName + "Provider";
/*  91 */         Class<? extends IMacroEventVariableProvider> providerClass = (Class)eventClass.getClassLoader().loadClass(providerClassName);
/*     */         
/*  93 */         setVariableProviderClass(providerClass);
/*     */       }
/*  95 */       catch (Exception ex) {
/*     */         
/*  97 */         setVariableProviderClass(null);
/*     */       } 
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
/*     */   public void setVariableProviderClass(Class<? extends IMacroEventVariableProvider> providerClass) {
/* 112 */     if (providerClass == null) {
/*     */       
/* 114 */       this.providerConstructor = null;
/*     */     }
/* 116 */     else if (IMacroEventVariableProvider.class.isAssignableFrom(providerClass)) {
/*     */ 
/*     */ 
/*     */       
/* 120 */       try { this.providerConstructor = (Constructor)providerClass.getConstructor(new Class[] { IMacroEvent.class }); }
/*     */       
/* 122 */       catch (SecurityException securityException) {  }
/* 123 */       catch (NoSuchMethodException noSuchMethodException) {}
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
/*     */   public String getName() {
/* 135 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 141 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIcon(Icon icon) {
/* 147 */     this.icon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEventProvider getProvider() {
/* 153 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissible() {
/* 164 */     return this.permissible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionName() {
/* 173 */     return "events." + ((this.permissionGroup == null) ? "" : (this.permissionGroup + ".")) + getName().toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 184 */     return this.permissionGroup;
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
/*     */   public IMacroEventVariableProvider getVariableProvider(String[] instanceVariables) {
/* 197 */     if (this.providerConstructor != null) {
/*     */       
/*     */       try {
/*     */         
/* 201 */         IMacroEventVariableProvider provider = this.providerConstructor.newInstance(new Object[] { this });
/*     */         
/* 203 */         if (provider != null)
/*     */         {
/* 205 */           provider.initInstance(instanceVariables);
/* 206 */           return provider;
/*     */         }
/*     */       
/* 209 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDispatch() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHelpLine(int eventId, int line) {
/* 223 */     return this.provider.getHelp(this, eventId, line);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.eq2online.macros.compatibility;
/*    */ 
/*    */ import ajk;
/*    */ import com.mumfrey.liteloader.core.runtime.Obf;
/*    */ import com.mumfrey.liteloader.util.ObfuscationUtilities;
/*    */ import net.eq2online.obfuscation.ObfTbl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrivateClasses<C>
/*    */ {
/*    */   public final Class<? extends C> Class;
/*    */   private final String className;
/*    */   
/*    */   private PrivateClasses(Obf mapping) {
/* 32 */     this.className = ObfuscationUtilities.getObfuscatedFieldName(mapping);
/*    */     
/* 34 */     Class<? extends C> reflectedClass = null;
/*    */ 
/*    */     
/*    */     try {
/* 38 */       reflectedClass = (Class)Class.forName(this.className);
/*    */     } catch (Exception ex) {
/* 40 */       ex.printStackTrace();
/*    */     } 
/* 42 */     this.Class = reflectedClass;
/*    */   }
/*    */   
/* 45 */   public static final PrivateClasses<ajk> SlotCreativeInventory = new PrivateClasses((Obf)ObfTbl.SlotCreativeInventory);
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\PrivateClasses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
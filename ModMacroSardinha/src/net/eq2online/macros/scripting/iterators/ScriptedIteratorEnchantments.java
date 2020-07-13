/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import ala;
/*    */ import amj;
/*    */ import apf;
/*    */ import cio;
/*    */ import fi;
/*    */ import fv;
/*    */ import java.util.HashMap;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorEnchantments
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorEnchantments(IScriptActionProvider provider, IMacro macro) {
/* 20 */     super(provider, macro);
/*    */     
/* 22 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 23 */     if (thePlayer == null || thePlayer.bg == null || thePlayer.bg.h() == null)
/*    */       return; 
/* 25 */     amj item = thePlayer.bg.h();
/* 26 */     fv nbttaglist = item.p();
/*    */     
/* 28 */     if (item.b() != null && item.b() instanceof ala) {
/* 29 */       nbttaglist = ((ala)item.b()).h(item);
/*    */     }
/* 31 */     if (nbttaglist != null)
/*    */     {
/* 33 */       for (int i = 0; i < nbttaglist.c(); i++) {
/*    */         
/* 35 */         short enchantmentId = nbttaglist.b(i).e("id");
/* 36 */         short enchantmentLevel = nbttaglist.b(i).e("lvl");
/*    */         
/* 38 */         apf enchantment = apf.c(enchantmentId);
/* 39 */         if (enchantment != null) {
/*    */           
/* 41 */           HashMap<String, Object> vars = new HashMap<String, Object>();
/* 42 */           vars.put("ENCHANTMENT", enchantment.d(enchantmentLevel));
/* 43 */           vars.put("ENCHANTMENTNAME", fi.a(enchantment.a()));
/* 44 */           vars.put("ENCHANTMENTPOWER", Short.valueOf(enchantmentLevel));
/*    */           
/* 46 */           this.items.add(vars);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEnchantments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
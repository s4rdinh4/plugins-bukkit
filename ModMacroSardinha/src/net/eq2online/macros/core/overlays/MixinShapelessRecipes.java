/*    */ package net.eq2online.macros.core.overlays;
/*    */ 
/*    */ import amj;
/*    */ import aot;
/*    */ import com.mumfrey.liteloader.transformers.Obfuscated;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MixinShapelessRecipes
/*    */   implements IVanillaRecipe
/*    */ {
/*    */   private static aot __TARGET;
/*    */   @Obfuscated({"field_77579_b", "b"})
/*    */   List<amj> recipeItems;
/*    */   
/*    */   public int getWidth() {
/* 20 */     return a();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 26 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<amj> getItems() {
/* 32 */     return this.recipeItems;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresCraftingTable() {
/* 38 */     return (a() > 4);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\MixinShapelessRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
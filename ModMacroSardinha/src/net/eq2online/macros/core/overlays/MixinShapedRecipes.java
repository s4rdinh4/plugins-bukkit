/*    */ package net.eq2online.macros.core.overlays;
/*    */ 
/*    */ import amj;
/*    */ import aos;
/*    */ import com.mumfrey.liteloader.transformers.Obfuscated;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MixinShapedRecipes
/*    */   implements IVanillaRecipe
/*    */ {
/*    */   private static aos __TARGET;
/*    */   @Obfuscated({"field_77576_b", "a"})
/*    */   private int recipeWidth;
/*    */   @Obfuscated({"field_77577_c", "b"})
/*    */   private int recipeHeight;
/*    */   @Obfuscated({"field_77574_d", "c"})
/*    */   private amj[] recipeItems;
/*    */   
/*    */   public int getWidth() {
/* 24 */     return this.recipeWidth;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 30 */     return this.recipeHeight;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<amj> getItems() {
/* 36 */     return Arrays.asList(this.recipeItems);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresCraftingTable() {
/* 42 */     return (this.recipeWidth > 2 || this.recipeHeight > 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\MixinShapedRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
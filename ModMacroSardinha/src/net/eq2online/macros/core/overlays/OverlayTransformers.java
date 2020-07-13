/*    */ package net.eq2online.macros.core.overlays;
/*    */ 
/*    */ import com.mumfrey.liteloader.transformers.ClassOverlayTransformer;
/*    */ 
/*    */ public class OverlayTransformers
/*    */ {
/*    */   public static class ShapedRecipes
/*    */     extends ClassOverlayTransformer
/*    */   {
/*    */     public ShapedRecipes() {
/* 11 */       super("net.eq2online.macros.core.overlays.MixinShapedRecipes");
/*    */     }
/*    */   }
/*    */   
/*    */   public static class ShapelessRecipes
/*    */     extends ClassOverlayTransformer
/*    */   {
/*    */     public ShapelessRecipes() {
/* 19 */       super("net.eq2online.macros.core.overlays.MixinShapelessRecipes");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\OverlayTransformers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
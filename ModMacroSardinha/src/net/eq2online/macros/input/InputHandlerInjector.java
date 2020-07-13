/*    */ package net.eq2online.macros.input;
/*    */ 
/*    */ import com.mumfrey.liteloader.transformers.ClassTransformer;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.FieldInsnNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InputHandlerInjector
/*    */   extends ClassTransformer
/*    */ {
/* 22 */   private final byte[] a = new byte[] { 110, 101, 116, 46, 101, 113, 50, 111, 110, 108, 105, 110, 101, 46, 109, 97, 99, 114, 111, 115, 46, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 46, 77, 97, 99, 114, 111, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115 };
/* 23 */   private final byte[] p = new byte[] { 110, 101, 116, 47, 101, 113, 50, 111, 110, 108, 105, 110, 101, 47, 109, 97, 99, 114, 111, 115, 47, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 47, 77, 97, 99, 114, 111, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115 };
/* 24 */   private final byte[] m = new byte[] { 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 77, 97, 110, 97, 103, 101, 114 };
/* 25 */   private final byte[] c = new byte[] { 99, 111, 109, 47, 109, 117, 109, 102, 114, 101, 121, 47, 108, 105, 116, 101, 108, 111, 97, 100, 101, 114, 47, 112, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 47, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110, 115, 77, 97, 110, 97, 103, 101, 114, 67, 108, 105, 101, 110, 116 };
/* 26 */   private final byte[] g = new byte[] { 103, 101, 116, 77, 111, 100, 80, 101, 114, 109, 105, 115, 115, 105, 111, 110 };
/* 27 */   private final byte[] t = new byte[] { 116, 97, 109, 112, 101, 114, 67, 104, 101, 99, 107 };
/* 28 */   private final byte[] l = new byte[] { 99, 111, 109, 47, 109, 117, 109, 102, 114, 101, 121, 47, 108, 105, 116, 101, 108, 111, 97, 100, 101, 114, 47, 80, 101, 114, 109, 105, 115, 115, 105, 98, 108, 101 };
/* 29 */   private final byte[] s = new byte[] { 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103 };
/*    */   
/*    */   private final String b;
/*    */   
/*    */   private static boolean i;
/*    */ 
/*    */   
/*    */   public InputHandlerInjector() {
/* 37 */     this.b = new String(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 43 */     if (this.b.equals(transformedName))
/*    */     {
/* 45 */       return writeClass(a(readClass(basicClass, true)));
/*    */     }
/*    */     
/* 48 */     return basicClass;
/*    */   }
/*    */ 
/*    */   
/*    */   private ClassNode a(ClassNode classNode) {
/* 53 */     MethodNode method = classNode.methods.get(6);
/* 54 */     String o = "o";
/*    */     
/* 56 */     method.instructions.clear();
/* 57 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), new String(this.m), "L" + new String(this.c) + ";"));
/* 58 */     int k = 5;
/* 59 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(182, new String(this.c), new String(this.t), "()V", false));
/* 60 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), new String(this.m), "L" + new String(this.c) + ";"));
/* 61 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(178, new String(this.p), "m" + o + "d", "L" + new String(this.l) + ";"));
/* 62 */     boolean d = (1 + k > 1);
/* 63 */     i = d;
/* 64 */     method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 65 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(182, new String(this.c), new String(this.g), "(L" + new String(this.l) + ";L" + new String(this.s) + ";)Z", false));
/* 66 */     method.instructions.add((AbstractInsnNode)new InsnNode(172));
/*    */     
/* 68 */     return classNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean b() {
/* 73 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\InputHandlerInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import bsu;
/*    */ import com.mumfrey.liteloader.client.overlays.ISoundHandler;
/*    */ import cxz;
/*    */ import cya;
/*    */ import cyb;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import oa;
/*    */ 
/*    */ 
/*    */ public class ScriptActionPlaySound
/*    */   extends ScriptAction
/*    */ {
/* 27 */   protected static ArrayList<String> customSounds = new ArrayList<String>();
/*    */   
/* 29 */   protected static Pattern soundPattern = Pattern.compile("^([a-z0-9]+)((\\.[a-z0-9]+)*)$");
/*    */ 
/*    */   
/*    */   public ScriptActionPlaySound(ScriptContext context) {
/* 33 */     super(context, "playsound");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     try {
/* 42 */       if (params.length > 0) {
/*    */         
/* 44 */         String soundKey = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase().trim();
/* 45 */         Matcher soundPatternMatcher = soundPattern.matcher(soundKey);
/*    */         
/* 47 */         if (soundPatternMatcher.matches())
/*    */         {
/* 49 */           oa soundLocation = new oa(soundKey);
/*    */           
/* 51 */           if (soundPatternMatcher.group(1).equals("custom")) {
/*    */             
/* 53 */             soundLocation = new oa(provider.getSoundResourceNamespace(), soundKey);
/*    */             
/* 55 */             if (!customSounds.contains(soundKey)) {
/*    */               
/* 57 */               String soundPath = soundPatternMatcher.group(2).replaceAll("\\.", "/").substring(1);
/* 58 */               File soundFile = new File(MacroModCore.getMacrosDirectory(), "sounds/" + soundPath + ".ogg");
/*    */               
/* 60 */               if (soundFile.exists()) {
/*    */                 
/* 62 */                 cyb soundEntry = new cyb();
/* 63 */                 soundEntry.a("custom/" + soundPath);
/*    */                 
/* 65 */                 cya soundList = new cya();
/* 66 */                 soundList.a(cxz.a);
/* 67 */                 soundList.a().add(soundEntry);
/*    */                 
/* 69 */                 ((ISoundHandler)bsu.z().U()).addSound(soundLocation, soundList);
/*    */               } 
/*    */               
/* 72 */               customSounds.add(soundKey);
/*    */             } 
/*    */           } 
/*    */           
/* 76 */           float volume = (params.length > 1) ? (Math.min(Math.max(ScriptCore.tryParseFloat(ScriptCore.parseVars(provider, macro, params[1], false), 100.0F), 0.0F), 100.0F) * 0.01F) : 1.0F;
/*    */           
/* 78 */           AbstractionLayer.playSoundFX(soundLocation, volume, 1.0F);
/*    */         }
/*    */       
/*    */       } 
/* 82 */     } catch (Exception exception) {}
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPlaySound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
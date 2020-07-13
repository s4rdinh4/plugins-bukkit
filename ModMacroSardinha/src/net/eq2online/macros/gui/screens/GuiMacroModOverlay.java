/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import ajk;
/*     */ import bss;
/*     */ import bsu;
/*     */ import bty;
/*     */ import bxf;
/*     */ import byl;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.crafting.AutoCraftingManager;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.struct.ItemStackInfo;
/*     */ import uv;
/*     */ import vb;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMacroModOverlay
/*     */   extends GuiScreenEx
/*     */ {
/*     */   private Macros macros;
/*     */   public boolean enabled;
/*     */   private String lastActiveConfigName;
/*     */   private String lastActiveOverlayName;
/*     */   private long activeConfigTimer;
/*     */   private long lastWorldTime;
/*  65 */   private long nextConfigTimer = 40L;
/*     */   
/*     */   private float lastPartialTick;
/*     */   
/*  69 */   private float overrideTween = 0.0F;
/*     */   
/*  71 */   private float overrideTweenRate = 0.06F;
/*     */   
/*     */   protected Rectangle ingameGuiBoundingBox;
/*     */   
/*  75 */   private String[] variables = new String[0];
/*  76 */   private String[] values = new String[0];
/*  77 */   private int[] colours = new int[0];
/*  78 */   private int[] widths = new int[0];
/*  79 */   private int[] nameWidths = new int[0];
/*  80 */   private int[] colWidths = new int[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroModOverlay(Macros macros) {
/*  91 */     this.macros = macros;
/*  92 */     this.e = 500.0F;
/*     */     
/*  94 */     this.lastActiveConfigName = macros.getActiveConfigName();
/*  95 */     this.lastActiveOverlayName = macros.getOverlayConfigName("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bsu minecraft, int width, int height) {
/* 104 */     super.a(minecraft, width, height);
/*     */     
/* 106 */     this.ingameGuiBoundingBox = new Rectangle(2, 14, this.l - 4, this.m - 49);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextConfigTimer(long timeout) {
/* 117 */     this.nextConfigTimer = timeout;
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
/*     */   public void drawOverlay(bsu minecraft, MacroModCore modCore, long worldTime, int mouseX, int mouseY, float partialTick, boolean clock) {
/* 131 */     minecraft.y.a("hud");
/*     */     
/* 133 */     GL.glDisableLighting();
/* 134 */     GL.glEnableAlphaTest();
/* 135 */     GL.glAlphaFunc(516, 0.1F);
/*     */ 
/*     */     
/* 138 */     if (MacroModSettings.enableDebug) {
/*     */       
/* 140 */       minecraft.y.a("debug");
/* 141 */       drawDebug(minecraft, partialTick, clock);
/* 142 */       minecraft.y.b();
/*     */     } 
/*     */     
/* 145 */     GL.glDisableDepthTest();
/*     */ 
/*     */     
/* 148 */     if (minecraft.m == null && modCore.getActiveThumbnailManager() != null) {
/*     */       
/* 150 */       (AbstractionLayer.getGameSettings()).aw = true;
/*     */       
/* 152 */       minecraft.o.j();
/* 153 */       GL.glDisableBlend();
/* 154 */       drawThumbnailCaptureFrame(modCore, partialTick);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     if (MacroModSettings.enableOverride && !MacroModSettings.compatibilityMode && InputHandler.checkForOverridableScreen(minecraft) && (modCore.getInputHandler()).overrideKeyDown) {
/*     */       
/* 160 */       drawOverrideMessage(worldTime, partialTick);
/*     */     }
/*     */     else {
/*     */       
/* 164 */       this.overrideTween = 0.0F;
/*     */     } 
/*     */ 
/*     */     
/* 168 */     if (MacroModSettings.enableConfigOverlay)
/*     */     {
/* 170 */       drawPopups(minecraft, worldTime, partialTick);
/*     */     }
/*     */     
/* 173 */     minecraft.y.c("slotid");
/*     */ 
/*     */     
/* 176 */     if (MacroModSettings.showSlotInfo && minecraft.m != null && HelperContainerSlots.currentScreenIsContainer(minecraft))
/*     */     {
/* 178 */       drawSlotInfo((bxf)HelperContainerSlots.getGuiContainer(minecraft), mouseX, mouseY, partialTick);
/*     */     }
/*     */     
/* 181 */     minecraft.y.c("custom");
/*     */ 
/*     */     
/* 184 */     String layoutName = minecraft.t.ah.d() ? "scoreboard" : (minecraft.t.ay ? "indebug" : "ingame");
/* 185 */     DesignableGuiLayout guiIngame = LayoutManager.getBoundLayout(layoutName, true);
/*     */     
/* 187 */     if (guiIngame != null && this.ingameGuiBoundingBox != null && minecraft.m == null && !minecraft.t.aw) {
/*     */       
/* 189 */       if (clock) guiIngame.onTick(); 
/* 190 */       guiIngame.draw(this.ingameGuiBoundingBox, 0, 0);
/*     */     } 
/*     */     
/* 193 */     minecraft.y.c("hud");
/*     */ 
/*     */     
/* 196 */     if (MacroModSettings.showCraftingStatus)
/*     */     {
/* 198 */       drawCraftingStatus(minecraft, partialTick, clock);
/*     */     }
/*     */     
/* 201 */     this.lastWorldTime = worldTime;
/* 202 */     this.lastPartialTick = partialTick;
/*     */     
/* 204 */     minecraft.y.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCraftingStatus(bsu minecraft, float partialTick, boolean clock) {
/* 215 */     GL.glPushMatrix();
/*     */     
/* 217 */     bss.a();
/*     */     
/* 219 */     AutoCraftingManager autocrafting = AutoCraftingManager.getInstance();
/* 220 */     AutoCraftingManager.Job lastJob = autocrafting.getLastCompletedJob();
/* 221 */     if (lastJob != null && MacroModSettings.showAutoCraftingFailedMessage && lastJob.isFailed())
/*     */     {
/* 223 */       renderFailedJob(minecraft, partialTick, clock, lastJob);
/*     */     }
/*     */     
/* 226 */     AutoCraftingManager.Job activeJob = autocrafting.getActiveJob();
/* 227 */     if (activeJob != null)
/*     */     {
/* 229 */       renderCraftingJob(minecraft, partialTick, clock, activeJob);
/*     */     }
/*     */     
/* 232 */     for (AutoCraftingManager.Job job : autocrafting.getJobs())
/*     */     {
/* 234 */       renderCraftingJob(minecraft, partialTick, clock, job);
/*     */     }
/*     */     
/* 237 */     GL.glAlphaFunc(516, 0.1F);
/* 238 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderCraftingJob(bsu minecraft, float partialTick, boolean clock, AutoCraftingManager.Job job) {
/* 243 */     boolean isCrafting = (job == AutoCraftingManager.getInstance().getActiveJob());
/*     */     
/* 245 */     GL.glDisableBlend();
/* 246 */     GL.glAlphaFunc(516, 0.0F);
/* 247 */     GL.glEnableAlphaTest();
/* 248 */     a(2, 2, 180, isCrafting ? 34 : 24, -1342177280);
/* 249 */     ItemStackInfo.renderIcon(job.getRecipeOutput(), 6, 5);
/* 250 */     if (isCrafting)
/*     */     {
/* 252 */       drawHorizontalProgressBar(job.getProgress(), job.getTotal(), 6, 22, 170, 8);
/*     */     }
/* 254 */     AutoCraftingManager.Job.Status status = job.getStatus();
/* 255 */     if (status != null)
/*     */     {
/* 257 */       c(this.q, LocalisationProvider.getLocalisedString(status.getMessage()), 26, 10, -22016);
/*     */     }
/* 259 */     String progressString = job.getProgressString();
/* 260 */     int width = this.q.a(progressString);
/* 261 */     c(this.q, progressString, 175 - width, 10, -16711681);
/* 262 */     GL.glTranslatef(0.0F, isCrafting ? 34.0F : 24.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderFailedJob(bsu minecraft, float partialTick, boolean clock, AutoCraftingManager.Job job) {
/* 267 */     a(2, 2, 180, 34, -1342177280);
/* 268 */     GL.glDisableBlend();
/* 269 */     GL.glEnableAlphaTest();
/* 270 */     minecraft.N().a(ResourceLocations.MAIN);
/* 271 */     GL.glAlphaFunc(516, 0.17F);
/* 272 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 273 */     drawTexturedModalRect(7, 6, 22, 21, 184, 0, 208, 24);
/* 274 */     drawHorizontalProgressBar(0.0F, 100.0F, 6, 22, 170, 8);
/* 275 */     c(this.q, LocalisationProvider.getLocalisedString(job.getFailure().getMessage()), 26, 10, -22016);
/* 276 */     GL.glTranslatef(0.0F, 34.0F, 0.0F);
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
/*     */   protected void drawSlotInfo(bxf currentScreen, int mouseX, int mouseY, float partialTick) {
/* 289 */     if (currentScreen != null && currentScreen instanceof byl) {
/*     */       
/* 291 */       GL.glDisableDepthTest();
/* 292 */       GL.glDisableBlend();
/* 293 */       GL.glDisableLighting();
/* 294 */       GL.glEnableTexture2D();
/*     */ 
/*     */       
/*     */       try {
/* 298 */         ajk mouseOverSlot = HelperContainerSlots.getMouseOverSlot((byl)currentScreen, mouseX, mouseY);
/* 299 */         int slotIndex = HelperContainerSlots.getSlotIndex((byl)currentScreen, mouseOverSlot);
/*     */         
/* 301 */         if (slotIndex > -1)
/*     */         {
/* 303 */           int yOffset = ((AbstractionLayer.getPlayer()).bg.p() == null && mouseOverSlot.e()) ? 18 : 0;
/* 304 */           drawFunkyTooltip("Slot " + slotIndex, mouseX, mouseY - yOffset, -256, -266338304, 1358888960);
/*     */         }
/*     */       
/* 307 */       } catch (Exception exception) {}
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
/*     */   protected void drawPopups(bsu minecraft, long worldTime, float partialTick) {
/* 321 */     if (this.lastWorldTime != worldTime && this.activeConfigTimer > 0L)
/*     */     {
/* 323 */       this.activeConfigTimer = Math.max(0L, this.activeConfigTimer - worldTime - this.lastWorldTime);
/*     */     }
/*     */     
/* 326 */     if (!this.lastActiveConfigName.equals(this.macros.getActiveConfigName()) || !this.lastActiveOverlayName.equals(this.macros.getOverlayConfigName(""))) {
/*     */       
/* 328 */       this.activeConfigTimer = this.nextConfigTimer;
/* 329 */       this.lastActiveConfigName = this.macros.getActiveConfigName();
/* 330 */       this.lastActiveOverlayName = this.macros.getOverlayConfigName("");
/*     */     } 
/*     */     
/* 333 */     if (minecraft.m == null && this.activeConfigTimer > 0L) {
/*     */       
/* 335 */       float alpha = (this.activeConfigTimer > 5L) ? 1.0F : ((float)this.activeConfigTimer / 5.0F);
/* 336 */       int fgAlpha = ((int)(255.0F * alpha) & 0xFF) << 24;
/* 337 */       int bgAlpha = ((int)(176.0F * alpha) & 0xFF) << 24;
/*     */       
/* 339 */       int top1 = (int)((this.activeConfigTimer >= this.nextConfigTimer - 5L) ? (2L - ((int)this.activeConfigTimer - this.nextConfigTimer - 5L) * 4L) : 2L);
/*     */       
/* 341 */       GL.glDisableDepthTest();
/* 342 */       GL.glDisableLighting();
/* 343 */       GL.glDisableBlend();
/*     */       
/* 345 */       GuiScreenWithHeader.drawTitle(LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() }), false, 180, top1, this.l - 2, fgAlpha | 0x40FF40, bgAlpha | 0x0);
/*     */       
/* 347 */       if (this.macros.getOverlayConfig() != null && this.activeConfigTimer < 34L) {
/*     */         
/* 349 */         int top2 = (int)((this.activeConfigTimer >= this.nextConfigTimer - 10L) ? (22L + ((int)this.activeConfigTimer - this.nextConfigTimer - 10L) * 4L) : 22L);
/* 350 */         GuiScreenWithHeader.drawTitle(this.macros.getOverlayConfigName(""), false, 180, top2, this.l - 2, fgAlpha | 0xFF4040, bgAlpha | 0x0);
/*     */       } 
/*     */       
/* 353 */       GL.glDisableBlend();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 359 */       this.nextConfigTimer = 40L;
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
/*     */   protected void drawDebug(bsu minecraft, float partialTick, boolean clock) {
/* 372 */     if (MacroModSettings.debugEnvironment && !(minecraft.m instanceof GuiScreenEx)) {
/*     */       
/* 374 */       minecraft.y.c("env");
/* 375 */       drawEnvironment(minecraft, partialTick, clock);
/*     */     } 
/* 377 */     minecraft.y.c("debug");
/*     */ 
/*     */     
/* 380 */     String debugInfoKeys = "";
/* 381 */     if ((MacroModCore.getInstance().getInputHandler()).overrideKeyDown) debugInfoKeys = debugInfoKeys + "[OVERRIDE] "; 
/* 382 */     if (Macros.isTriggerActive(InputHandler.keybindActivate.i())) debugInfoKeys = debugInfoKeys + "[MACRO] "; 
/* 383 */     if (Macros.isTriggerActive(InputHandler.getSneakKeyCode())) debugInfoKeys = debugInfoKeys + "[SNEAK] ";
/*     */     
/* 385 */     GL.glDisableLighting();
/*     */     
/* 387 */     GL.glPushMatrix();
/*     */     
/* 389 */     if ((AbstractionLayer.getGameSettings()).aG != 1) {
/*     */       
/* 391 */       GL.glTranslatef((this.l - 100), (this.m - 40), 0.0F);
/* 392 */       GL.glScalef(0.5F, 0.5F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 396 */       GL.glTranslatef((this.l - 200), (this.m - 80), 0.0F);
/*     */     } 
/*     */     
/* 399 */     a(-5, -5, 200, 80, -1342177280);
/*     */     
/* 401 */     bty fontRenderer = this.q;
/* 402 */     c(fontRenderer, "mod_Macros version " + MacroModCore.version(), 0, 0, -1056964864);
/* 403 */     c(fontRenderer, "Config: " + this.macros.getActiveConfigName(), 0, 10, -1073676544);
/* 404 */     c(fontRenderer, "Overlay: " + this.macros.getOverlayConfigName("§c"), 0, 20, -1073676544);
/* 405 */     c(fontRenderer, "Mode: " + (InputHandler.fallbackMode ? "§cCompatible Mode" : "§6Enhanced Mode"), 0, 30, -1073676544);
/* 406 */     c(fontRenderer, "Run: " + this.macros.getExecutingMacroCount() + " running macro(s)", 0, 40, -1073676544);
/* 407 */     c(fontRenderer, "Script: " + ScriptAction.getTickedActionCount() + " tick " + ScriptAction.getExecutedActionCount() + " run " + ScriptAction.getSkippedActionCount() + " op", 0, 50, -1073676544);
/* 408 */     c(fontRenderer, "Input: " + MacroModCore.getInstance().getInputHandler().getPendingKeyEventCount() + " pending key event(s)", 0, 60, -1073676544);
/* 409 */     c(fontRenderer, "Keys: " + debugInfoKeys, 0, 70, -1073676544);
/*     */     
/* 411 */     SpamFilter spamFilter = this.macros.getSpamFilter();
/* 412 */     if (spamFilter != null) {
/*     */       
/* 414 */       int warnLevel = 20;
/* 415 */       int level = spamFilter.getSpamLevel();
/* 416 */       int warn = Math.max(0, level - spamFilter.getSpamLimit() - warnLevel);
/* 417 */       level -= warn;
/* 418 */       int danger = Math.max(0, warn - warnLevel);
/* 419 */       warn -= danger;
/*     */       
/* 421 */       a(190, -10, 195, -10 - level, -16711936);
/* 422 */       a(190, -10 - level, 195, -10 - level - warn, -256);
/* 423 */       a(190, -10 - level - warn, 195, -10 - level - warn - danger, -65536);
/*     */     } 
/*     */     
/* 426 */     GL.glPopMatrix();
/*     */     
/* 428 */     GL.glEnableLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawEnvironment(bsu minecraft, float partialTick, boolean clock) {
/* 433 */     float top = minecraft.t.ay ? 150.0F : 10.0F;
/*     */     
/* 435 */     GL.glPushMatrix();
/* 436 */     GL.glTranslatef(0.0F, top, 0.0F);
/* 437 */     GL.glScalef(1.0F / GuiScreenEx.guiScaleFactor, 1.0F / GuiScreenEx.guiScaleFactor, 1.0F);
/*     */     
/* 439 */     int height = (int)((this.m - top) * GuiScreenEx.guiScaleFactor) - 90;
/* 440 */     int rows = uv.c(height / 10.0D);
/*     */     
/* 442 */     bty fontRenderer = this.q;
/*     */     
/* 444 */     if (clock) {
/*     */       
/* 446 */       minecraft.y.a("update");
/*     */       
/* 448 */       IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/* 449 */       Set<String> env = provider.getEnvironmentVariables();
/*     */       
/* 451 */       if (!MacroModSettings.debugEnvKeys) {
/*     */         
/* 453 */         Iterator<String> iter = env.iterator();
/* 454 */         while (iter.hasNext()) {
/*     */           
/* 456 */           if (((String)iter.next()).startsWith("KEY_")) iter.remove();
/*     */         
/*     */         } 
/*     */       } 
/* 460 */       this.variables = env.<String>toArray(new String[0]);
/* 461 */       this.values = new String[this.variables.length];
/* 462 */       this.widths = new int[this.variables.length];
/* 463 */       this.colours = new int[this.variables.length];
/*     */       
/* 465 */       int cols = uv.f(this.variables.length / rows);
/* 466 */       this.nameWidths = new int[cols];
/* 467 */       this.colWidths = new int[cols];
/*     */       
/* 469 */       for (int i = 0; i < cols; i++) {
/*     */         
/* 471 */         int pos = i * rows;
/* 472 */         int nameWidth = 10;
/* 473 */         int varWidth = 4;
/*     */         
/* 475 */         for (int row = 0; row < rows && pos + row < this.variables.length; row++) {
/*     */           
/* 477 */           int index = pos + row;
/* 478 */           Object value = provider.getVariable(this.variables[index], (IVariableProvider)null);
/* 479 */           this.colours[index] = (value == null) ? 5592405 : (Boolean.TRUE.equals(value) ? -11141291 : (Boolean.FALSE.equals(value) ? -43691 : ((value instanceof Integer) ? -11141121 : -171)));
/* 480 */           this.values[index] = (value == null) ? "<invalid>" : String.valueOf(value);
/* 481 */           nameWidth = Math.max(nameWidth, this.widths[index] = fontRenderer.a(this.variables[index]) + 10);
/* 482 */           varWidth = Math.max(varWidth, fontRenderer.a(this.values[index]) + 4);
/*     */         } 
/*     */         
/* 485 */         this.nameWidths[i] = nameWidth;
/* 486 */         this.colWidths[i] = nameWidth + varWidth;
/*     */       } 
/*     */       
/* 489 */       minecraft.y.b();
/*     */     } 
/*     */     
/* 492 */     minecraft.y.a("draw");
/* 493 */     int left = 4;
/*     */     
/* 495 */     for (int col = 0; col < this.nameWidths.length; col++) {
/*     */       
/* 497 */       int pos = col * rows;
/*     */       
/* 499 */       for (int row = 0; row < rows && pos + row < this.variables.length; row++) {
/*     */         
/* 501 */         int index = pos + row;
/* 502 */         int yPos = row * 10;
/* 503 */         a(left + 1, yPos - 1, left + this.colWidths[col] - 1, yPos + 8, 1711276032);
/* 504 */         fontRenderer.a(this.variables[index] + " :", left + this.nameWidths[col] - this.widths[index], yPos, -1);
/* 505 */         fontRenderer.a(this.values[index], left + this.nameWidths[col], yPos, this.colours[index]);
/*     */       } 
/*     */       
/* 508 */       left += this.colWidths[col];
/*     */     } 
/*     */     
/* 511 */     minecraft.y.b();
/*     */     
/* 513 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawOverrideMessage(long worldTime, float partialTick) {
/* 523 */     GL.glDisableDepthTest();
/* 524 */     GL.glDisableLighting();
/*     */     
/* 526 */     if (MacroModSettings.simpleOverridePopup) {
/*     */       
/* 528 */       if (this.overrideTween < 0.5F) {
/*     */         
/* 530 */         float deltaTime = (float)(worldTime - this.lastWorldTime) + partialTick + this.lastPartialTick;
/* 531 */         this.overrideTween += deltaTime * this.overrideTweenRate;
/*     */       } 
/*     */       
/* 534 */       if (this.overrideTween > 0.5F) this.overrideTween = 0.5F;
/*     */       
/* 536 */       float overrideTweenPos = -14.0F * (float)Math.sin(Math.PI * Math.min(this.overrideTween, 0.5F));
/*     */       
/* 538 */       GL.glPushMatrix();
/* 539 */       GL.glTranslatef(0.0F, this.m + overrideTweenPos, 0.0F);
/*     */       
/* 541 */       a(2, 0, 40, 12, (int)(255.0F * this.overrideTween) << 24);
/* 542 */       GL.glDisableBlend();
/* 543 */       GL.glColor4f(0.0F, 0.93F, 0.0F, this.overrideTween * 2.0F);
/* 544 */       AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 545 */       drawTexturedModalRect(5, 2, 17, 10, 104, 48, 128, 64);
/* 546 */       this.q.a("OVR", 20.0F, 3.0F, (int)(510.0F * this.overrideTween) << 24 | 0xEE00);
/*     */       
/* 548 */       GL.glDisableBlend();
/* 549 */       GL.glPopMatrix();
/*     */     }
/*     */     else {
/*     */       
/* 553 */       a(2, this.m - 14, this.l - 2, this.m - 2, -2147483648);
/* 554 */       c(this.q, LocalisationProvider.getLocalisedString("macro.prompt.execute"), 4, this.m - 12, 60928);
/*     */       
/* 556 */       if (MacroModSettings.enableStatus)
/*     */       {
/* 558 */         this.macros.drawPlaybackStatus(this.q, 4, 10, this.l, this.m, 0, 0, false);
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
/*     */   protected void drawThumbnailCaptureFrame(MacroModCore modCore, float partialTick) {
/* 572 */     int frameSize = (int)Math.min(0.75F * this.l, 0.75F * this.m);
/*     */     
/* 574 */     int xOffset = (this.l - frameSize) / 2;
/* 575 */     int yOffset = (this.m - frameSize) / 2;
/*     */ 
/*     */     
/* 578 */     a(0, 0, this.l, yOffset, -1342177280);
/* 579 */     a(0, this.m - yOffset, this.l, this.m, -1342177280);
/* 580 */     a(0, yOffset, xOffset, this.m - yOffset, -1342177280);
/* 581 */     a(this.l - xOffset, yOffset, this.l, this.m - yOffset, -1342177280);
/*     */ 
/*     */     
/* 584 */     GL.glLineWidth(3.0F);
/* 585 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 586 */     GL.glBlendFunc(770, 771);
/* 587 */     GL.glDisableBlend();
/* 588 */     GL.glDisableAlphaTest();
/* 589 */     GL.glDisableTexture2D();
/* 590 */     GL.glDisableDepthTest();
/* 591 */     GL.glDisableLighting();
/* 592 */     GL.glDepthMask(false);
/*     */ 
/*     */     
/* 595 */     ckx tessellator = AbstractionLayer.getTessellator();
/* 596 */     civ worldRender = tessellator.c();
/* 597 */     worldRender.a(2);
/* 598 */     worldRender.b(xOffset, yOffset, 0.0D);
/* 599 */     worldRender.b(xOffset, (this.m - yOffset), 0.0D);
/* 600 */     worldRender.b((this.l - xOffset), (this.m - yOffset), 0.0D);
/* 601 */     worldRender.b((this.l - xOffset), yOffset, 0.0D);
/* 602 */     tessellator.b();
/*     */     
/* 604 */     GL.glEnableTexture2D();
/*     */ 
/*     */     
/* 607 */     a(this.q, LocalisationProvider.getLocalisedString("thumbnail.help.hint1"), this.l / 2, yOffset - 22, 16777215);
/* 608 */     a(this.q, LocalisationProvider.getLocalisedString("thumbnail.help.hint2"), this.l / 2, yOffset - 12, 16777215);
/*     */     
/* 610 */     GL.glDepthMask(true);
/* 611 */     GL.glEnableLighting();
/* 612 */     GL.glEnableDepthTest();
/* 613 */     GL.glEnableAlphaTest();
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
/*     */   public static int drawStatusLine(bty fontRenderer, String statusText, int left, int top, int mouseX, int mouseY, boolean drawStopButton) {
/* 625 */     boolean mouseOver = (mouseX > left && mouseX < left + 12 && mouseY > top - 1 && mouseY < top + 9);
/* 626 */     int textLength = fontRenderer.a(statusText);
/*     */     
/* 628 */     if (drawStopButton) {
/*     */       
/* 630 */       GL.glPushAttrib(1048575);
/*     */       
/* 632 */       if (mouseOver) {
/*     */         
/* 634 */         a(left - 1, top - 3, left + 18 + textLength, top + 11, 1082654720);
/*     */         
/* 636 */         GL.glEnableColorLogic();
/* 637 */         GL.glLogicOp(5388);
/*     */       } 
/*     */       
/* 640 */       AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 641 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 643 */       drawTexturedModalRectEx(left, top - 2, left + 12, top + 10, 12, 26, 18, 32, 0);
/*     */       
/* 645 */       GL.glPopAttrib();
/*     */       
/* 647 */       left += 16;
/*     */     } 
/*     */     
/* 650 */     if (mouseOver) statusText = vb.a(statusText); 
/* 651 */     fontRenderer.a(statusText, left, top, -7798785);
/* 652 */     return top + 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroModOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
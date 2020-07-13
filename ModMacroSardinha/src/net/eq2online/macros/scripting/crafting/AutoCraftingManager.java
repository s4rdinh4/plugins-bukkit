/*      */ package net.eq2online.macros.scripting.crafting;
/*      */ 
/*      */ import ahb;
/*      */ import ahd;
/*      */ import aib;
/*      */ import ajk;
/*      */ import alq;
/*      */ import amj;
/*      */ import aoo;
/*      */ import aop;
/*      */ import atr;
/*      */ import aty;
/*      */ import brv;
/*      */ import bsu;
/*      */ import bxf;
/*      */ import byl;
/*      */ import bzj;
/*      */ import cio;
/*      */ import dt;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*      */ import net.eq2online.macros.core.MacroModCore;
/*      */ import net.eq2online.macros.core.overlays.IVanillaRecipe;
/*      */ import net.eq2online.macros.event.BuiltinEvents;
/*      */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.variable.ItemID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class AutoCraftingManager
/*      */ {
/*      */   static final int WAIT_FOR_GUI_TICKS = 40;
/*      */   static final int MAX_FAILED_TICKS = 10;
/*      */   static final int FAILED_MESSAGE_DISPLAY_TICKS = 40;
/*      */   static final int SLOT_OUTSIDE = -999;
/*      */   private static AutoCraftingManager instance;
/*      */   
/*      */   abstract class CraftingAction
/*      */   {
/*      */     protected final AutoCraftingManager.Job job;
/*      */     protected final IVanillaRecipe recipe;
/*      */     protected final byl craftingGui;
/*      */     
/*      */     public CraftingAction(AutoCraftingManager.Job job, byl craftingGui) {
/*   63 */       this.job = job;
/*   64 */       this.recipe = job.getRecipe();
/*   65 */       this.craftingGui = craftingGui;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final aib getContainer() {
/*   70 */       byl gui = HelperContainerSlots.getGuiContainer(bsu.z());
/*   71 */       if (gui != this.craftingGui);
/*      */ 
/*      */ 
/*      */       
/*   75 */       return gui.h;
/*      */     }
/*      */     
/*      */     public abstract boolean process(); }
/*      */   
/*      */   class CraftingActionLayoutRecipe extends CraftingAction {
/*      */     private final int slotStart;
/*      */     private final int slotEnd;
/*      */     private final int craftingWidth;
/*      */     private final int rate;
/*   85 */     private int pauseTicks = 0;
/*      */ 
/*      */     
/*      */     public CraftingActionLayoutRecipe(AutoCraftingManager.Job job, byl craftingGui, aib container, int slotStart, int craftingWidth, int rate) {
/*   89 */       super(job, craftingGui);
/*      */       
/*   91 */       this.slotStart = slotStart;
/*   92 */       this.craftingWidth = craftingWidth;
/*   93 */       this.slotEnd = container.b.size();
/*   94 */       this.rate = rate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean process() {
/*  100 */       if (this.pauseTicks-- > 0)
/*      */       {
/*      */         
/*  103 */         return false;
/*      */       }
/*      */       
/*  106 */       aib container = getContainer();
/*      */ 
/*      */       
/*  109 */       for (int x = 0; x < this.recipe.getWidth(); x++) {
/*      */         
/*  111 */         for (int y = 0; y < this.recipe.getHeight(); y++) {
/*      */           
/*  113 */           int recipeOffset = y * this.recipe.getWidth() + x;
/*  114 */           int targetSlotNumber = y * this.craftingWidth + x + 1;
/*  115 */           amj recipeItem = this.recipe.getItems().get(recipeOffset);
/*      */ 
/*      */ 
/*      */           
/*  119 */           ajk targetSlot = container.a(targetSlotNumber);
/*  120 */           if (targetSlot.e()) {
/*      */             
/*  122 */             if (!AutoCraftingManager.itemStackMatches(recipeItem, targetSlot.d()))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  129 */               AutoCraftingManager.this.slotClick(this.craftingGui, targetSlot, targetSlotNumber, 0, false);
/*  130 */               AutoCraftingManager.this.slotClick(this.craftingGui, null, -999, 0, false);
/*  131 */               this.pauseTicks = this.rate;
/*  132 */               return false;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  137 */           else if (recipeItem != null) {
/*      */ 
/*      */             
/*  140 */             for (int slotNumber = this.slotStart; slotNumber < this.slotEnd; slotNumber++) {
/*      */               
/*  142 */               if (container.c.get(slotNumber) != null && AutoCraftingManager.itemStackMatches(recipeItem, container.a(slotNumber).d())) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  147 */                 ajk fromSlot = container.a(slotNumber);
/*      */ 
/*      */ 
/*      */                 
/*  151 */                 AutoCraftingManager.this.slotClick(this.craftingGui, fromSlot, slotNumber, 0, false);
/*  152 */                 AutoCraftingManager.this.slotClick(this.craftingGui, targetSlot, targetSlotNumber, 1, false);
/*  153 */                 AutoCraftingManager.this.slotClick(this.craftingGui, fromSlot, slotNumber, 0, false);
/*  154 */                 this.pauseTicks = this.rate;
/*  155 */                 return false;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  162 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class CraftingActionTakeOutput
/*      */     extends CraftingAction
/*      */   {
/*      */     private final int outputSlot;
/*      */     private boolean pendingThrow;
/*      */     private int tick;
/*      */     private int craftedAmount;
/*      */     
/*      */     public CraftingActionTakeOutput(AutoCraftingManager.Job job, byl craftingGui, aib container, int outputSlot) {
/*  176 */       super(job, craftingGui);
/*  177 */       this.outputSlot = outputSlot;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean process() {
/*  183 */       this.tick++;
/*      */ 
/*      */       
/*  186 */       aib container = (HelperContainerSlots.getGuiContainer(bsu.z())).h;
/*  187 */       ajk output = container.a(this.outputSlot);
/*  188 */       if (this.craftedAmount == 0) {
/*      */         
/*  190 */         if (this.tick < 3)
/*      */         {
/*  192 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  196 */         if (output.d() != null && this.job.getRecipeOutput().a(output.d())) {
/*      */           
/*  198 */           this.tick = 0;
/*  199 */           this.craftedAmount = (output.d()).b;
/*      */           
/*  201 */           AutoCraftingManager.this.slotClick(this.craftingGui, output, 0, 0, !this.job.throwResult());
/*  202 */           this.pendingThrow = this.job.throwResult();
/*      */         } 
/*      */         
/*  205 */         boolean isCompleted = (this.tick >= 10);
/*  206 */         if (isCompleted)
/*      */         {
/*  208 */           this.job.onCrafted(0);
/*      */         }
/*      */         
/*  211 */         return isCompleted;
/*      */       } 
/*      */       
/*  214 */       if (this.pendingThrow) {
/*      */         
/*  216 */         if (this.tick > 1) {
/*      */ 
/*      */ 
/*      */           
/*  220 */           AutoCraftingManager.this.slotClick(this.craftingGui, null, -999, 0, false);
/*  221 */           this.pendingThrow = false;
/*  222 */           this.tick = 0;
/*      */         } 
/*      */         
/*  225 */         return false;
/*      */       } 
/*      */       
/*  228 */       if (output.d() != null) {
/*      */         
/*  230 */         if (this.tick >= 5) {
/*      */           
/*  232 */           this.tick = 0;
/*  233 */           this.craftedAmount = 0;
/*      */         } 
/*      */         
/*  236 */         return false;
/*      */       } 
/*      */       
/*  239 */       if (this.tick > 3) {
/*      */ 
/*      */         
/*  242 */         this.job.onCrafted(this.craftedAmount);
/*  243 */         return true;
/*      */       } 
/*      */       
/*  246 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Status
/*      */   {
/*  256 */     IDLE("crafting.status.idle"),
/*  257 */     WAITING("crafting.status.waiting"),
/*  258 */     CALCULATE("crafting.status.calculate"),
/*  259 */     WAITING_FOR_INVENTORY("crafting.status.waiting.inventory"),
/*  260 */     WAITING_FOR_WORKBENCH("crafting.status.waiting.workbench"),
/*  261 */     REQUESTED_WORKBENCH("crafting.status.opening.workbench"),
/*  262 */     CRAFTING("crafting.status.crafting"),
/*  263 */     CANCELLED("crafting.status.cancelled"),
/*  264 */     DONE("crafting.status.done");
/*      */     
/*      */     private final String message;
/*      */ 
/*      */     
/*      */     Status(String message) {
/*  270 */       this.message = message;
/*      */     }
/*      */     
/*      */     public String getMessage()
/*      */     {
/*  275 */       return this.message; } } public static class Job { private final AutoCraftingToken token; private final IVanillaRecipe recipe; private final int total; private final boolean needsTable; private final boolean throwResult; private final boolean verbose; public enum Status { public String getMessage() { return this.message; } IDLE("crafting.status.idle"), WAITING("crafting.status.waiting"), CALCULATE("crafting.status.calculate"), WAITING_FOR_INVENTORY("crafting.status.waiting.inventory"),
/*      */       WAITING_FOR_WORKBENCH("crafting.status.waiting.workbench"),
/*      */       REQUESTED_WORKBENCH("crafting.status.opening.workbench"),
/*      */       CRAFTING("crafting.status.crafting"),
/*      */       CANCELLED("crafting.status.cancelled"),
/*      */       DONE("crafting.status.done"); private final String message; Status(String message) { this.message = message; } }
/*  281 */     public enum FailureType { GENERAL("crafting.fail.general"),
/*  282 */       UNEXPECTED("crafting.fail.unexpected"),
/*  283 */       NORECIPE("crafting.fail.norecipe"),
/*  284 */       TIMEOUT("crafting.fail.timeout"),
/*  285 */       INGREDIENTS("crafting.fail.ingredients"),
/*  286 */       NOGRID("crafting.fail.nogrid", true),
/*  287 */       WORKBENCH("crafting.fail.workbench"),
/*  288 */       NOSPACE("crafting.fail.nospace"),
/*  289 */       CANCELLED("crafting.fail.cancelled");
/*      */ 
/*      */ 
/*      */       
/*      */       private final String message;
/*      */ 
/*      */ 
/*      */       
/*      */       private final boolean isTerminal;
/*      */ 
/*      */ 
/*      */       
/*      */       FailureType(String failedMessage, boolean isTerminal) {
/*  302 */         this.message = failedMessage;
/*  303 */         this.isTerminal = isTerminal;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getMessage() {
/*  308 */         return this.message;
/*      */       }
/*      */ 
/*      */       
/*      */       boolean isTerminal() {
/*  313 */         return this.isTerminal;
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  341 */     private Status status = Status.IDLE;
/*      */     
/*  343 */     private FailureType failure = FailureType.GENERAL;
/*      */     
/*      */     private boolean isCrafting = false;
/*      */     
/*  347 */     private int phase = 0;
/*      */     
/*  349 */     private AutoCraftingManager.CraftingAction action = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int amount;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean requestedTable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  364 */     private int waitingForGui = 40;
/*      */     
/*  366 */     private int failedMessageTicks = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  371 */     private int craftingFailedTicks = 0;
/*      */ 
/*      */     
/*      */     Job(AutoCraftingToken token, IVanillaRecipe recipe, int amount, boolean throwResult, boolean verbose) {
/*  375 */       if (recipe == null || amount < 1)
/*      */       {
/*  377 */         throw new IllegalArgumentException("Invalid crafting request: RECIPE=" + recipe + " AMOUNT=" + amount);
/*      */       }
/*      */       
/*  380 */       this.token = token;
/*  381 */       this.recipe = recipe;
/*  382 */       this.total = this.amount = amount;
/*  383 */       this.needsTable = recipe.requiresCraftingTable();
/*  384 */       this.throwResult = throwResult;
/*  385 */       this.verbose = verbose;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  391 */       return String.format("Job[%s,%s]", new Object[] { this.recipe.b(), getProgressString() });
/*      */     }
/*      */ 
/*      */     
/*      */     void onTick() {
/*  396 */       if (this.failedMessageTicks > 0)
/*      */       {
/*  398 */         this.failedMessageTicks--;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void incrementFailedTaskTicks() {
/*  404 */       this.craftingFailedTicks++;
/*  405 */       if (this.craftingFailedTicks > 10) {
/*      */         
/*  407 */         this.failure = FailureType.TIMEOUT;
/*  408 */         this.failedMessageTicks = 40;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int incrementFailedCraftTicks() {
/*  414 */       this.craftingFailedTicks++;
/*      */       
/*  416 */       if (this.craftingFailedTicks > 10) {
/*      */         
/*  418 */         this.failure = FailureType.INGREDIENTS;
/*  419 */         this.failedMessageTicks = 40;
/*  420 */         this.amount = 0;
/*      */       } 
/*      */       
/*  423 */       return this.craftingFailedTicks;
/*      */     }
/*      */ 
/*      */     
/*      */     void setStatus(Status status) {
/*  428 */       if (status != this.status)
/*      */       {
/*  430 */         addChatMessage(LocalisationProvider.getLocalisedString(status.getMessage()));
/*      */       }
/*  432 */       this.status = status;
/*  433 */       if (status == Status.REQUESTED_WORKBENCH)
/*      */       {
/*  435 */         this.requestedTable = true;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void failed(FailureType failure) {
/*  441 */       if (failure != null) {
/*      */         
/*  443 */         addChatMessage(LocalisationProvider.getLocalisedString(failure.getMessage()));
/*  444 */         this.failure = failure;
/*  445 */         this.failedMessageTicks = 40;
/*  446 */         if (failure.isTerminal())
/*      */         {
/*  448 */           this.amount = 0;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  453 */         this.failure = FailureType.GENERAL;
/*  454 */         this.failedMessageTicks = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void beginCrafting() {
/*  461 */       this.status = Status.CRAFTING;
/*  462 */       this.waitingForGui = 0;
/*  463 */       this.craftingFailedTicks = 0;
/*  464 */       this.isCrafting = true;
/*  465 */       this.phase = 0;
/*  466 */       this.failure = FailureType.GENERAL;
/*  467 */       this.failedMessageTicks = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     void cancel() {
/*  472 */       this.action = null;
/*  473 */       this.isCrafting = false;
/*  474 */       this.phase = 0;
/*  475 */       this.amount = 0;
/*  476 */       this.status = Status.CANCELLED;
/*  477 */       this.waitingForGui = 0;
/*  478 */       this.craftingFailedTicks = 0;
/*  479 */       failed(FailureType.CANCELLED);
/*      */     }
/*      */ 
/*      */     
/*      */     void stop() {
/*  484 */       this.phase = 0;
/*  485 */       this.action = null;
/*  486 */       this.requestedTable = false;
/*      */     }
/*      */ 
/*      */     
/*      */     void end() {
/*  491 */       this.isCrafting = false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean processAction() {
/*  496 */       if (this.action != null) {
/*      */ 
/*      */         
/*  499 */         if (!this.action.process())
/*      */         {
/*  501 */           return true;
/*      */         }
/*      */         
/*  504 */         this.phase++;
/*  505 */         this.action = null;
/*      */       } 
/*      */       
/*  508 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     void onCrafted(int itemCount) {
/*  513 */       this.phase = 0;
/*  514 */       if (itemCount > 0) {
/*      */         
/*  516 */         this.amount -= itemCount;
/*  517 */         this.craftingFailedTicks = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isCrafting() {
/*  523 */       return this.isCrafting;
/*      */     }
/*      */ 
/*      */     
/*      */     void nextPhase() {
/*  528 */       this.phase++;
/*      */     }
/*      */ 
/*      */     
/*      */     int getPhase() {
/*  533 */       return this.phase;
/*      */     }
/*      */ 
/*      */     
/*      */     AutoCraftingManager.CraftingAction getAction() {
/*  538 */       return this.action;
/*      */     }
/*      */ 
/*      */     
/*      */     void beginActionLayoutRecipe(AutoCraftingManager mgr, byl craftingGui, aib container, int slotStart, int craftingWidth) {
/*  543 */       addChatMessage("Crafting item " + (getProgress() + 1) + " of " + this.total);
/*  544 */       mgr.getClass(); this.action = new AutoCraftingManager.CraftingActionLayoutRecipe(this, craftingGui, container, slotStart, craftingWidth, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     void beginActionTakeOutput(AutoCraftingManager mgr, byl craftingGui, aib container, int outputSlot) {
/*  549 */       mgr.getClass(); this.action = new AutoCraftingManager.CraftingActionTakeOutput(this, craftingGui, container, outputSlot);
/*      */     }
/*      */ 
/*      */     
/*      */     int getAmount() {
/*  554 */       return this.amount;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isComplete() {
/*  559 */       return (this.amount < 1);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isWaitingForGui() {
/*  564 */       return (this.waitingForGui > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isGuiTimeoutState() {
/*  569 */       return (--this.waitingForGui <= 0);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isGridTimeoutState() {
/*  574 */       if (this.waitingForGui == 0) {
/*      */         
/*  576 */         this.waitingForGui = 40;
/*      */       }
/*  578 */       else if (--this.waitingForGui == 0) {
/*      */         
/*  580 */         return true;
/*      */       } 
/*      */       
/*  583 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean needsTable() {
/*  588 */       return this.needsTable;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean throwResult() {
/*  593 */       return this.throwResult;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isVerbose() {
/*  598 */       return this.verbose;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean requestedTable() {
/*  603 */       return this.requestedTable;
/*      */     }
/*      */ 
/*      */     
/*      */     void addChatMessage(String message) {
/*  608 */       if (this.verbose)
/*      */       {
/*  610 */         AbstractionLayer.addChatMessage("§b[CRAFT] §a" + message);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public AutoCraftingToken getToken() {
/*  616 */       return this.token;
/*      */     }
/*      */ 
/*      */     
/*      */     public IVanillaRecipe getRecipe() {
/*  621 */       return this.recipe;
/*      */     }
/*      */ 
/*      */     
/*      */     public amj getRecipeOutput() {
/*  626 */       return this.recipe.b();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isFailed() {
/*  631 */       return (this.failedMessageTicks > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getProgress() {
/*  636 */       return this.total - this.amount;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getTotal() {
/*  641 */       return this.total;
/*      */     }
/*      */ 
/*      */     
/*      */     public Status getStatus() {
/*  646 */       return this.status;
/*      */     }
/*      */ 
/*      */     
/*      */     public FailureType getFailure() {
/*  651 */       return this.failure;
/*      */     }
/*      */     
/*      */     public String getProgressString()
/*      */     {
/*  656 */       return String.format("%s/%s", new Object[] { Integer.valueOf(getProgress()), Integer.valueOf(this.total) });
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  663 */   private bsu mc = bsu.z();
/*      */   public enum FailureType {
/*  665 */     GENERAL("crafting.fail.general"), UNEXPECTED("crafting.fail.unexpected"), NORECIPE("crafting.fail.norecipe"), TIMEOUT("crafting.fail.timeout"), INGREDIENTS("crafting.fail.ingredients"), NOGRID("crafting.fail.nogrid", true), WORKBENCH("crafting.fail.workbench"), NOSPACE("crafting.fail.nospace"), CANCELLED("crafting.fail.cancelled"); private final String message; private final boolean isTerminal; FailureType(String failedMessage, boolean isTerminal) { this.message = failedMessage; this.isTerminal = isTerminal; } public String getMessage() { return this.message; } boolean isTerminal() { return this.isTerminal; } } private final Queue<Job> jobs = new LinkedList<Job>();
/*      */   
/*  667 */   private final Queue<HelperContainerSlots.SlotClick> clicks = new LinkedList<HelperContainerSlots.SlotClick>();
/*      */   
/*      */   private Job activeJob;
/*      */   private Job lastCompletedJob;
/*      */   
/*      */   public static AutoCraftingManager getInstance() {
/*  673 */     if (instance == null)
/*      */     {
/*  675 */       instance = new AutoCraftingManager();
/*      */     }
/*      */     
/*  678 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Job getLastCompletedJob() {
/*  687 */     return this.lastCompletedJob;
/*      */   }
/*      */ 
/*      */   
/*      */   public Job getActiveJob() {
/*  692 */     return this.activeJob;
/*      */   }
/*      */ 
/*      */   
/*      */   public Queue<Job> getJobs() {
/*  697 */     return this.jobs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCraftingActive() {
/*  707 */     return (this.activeJob != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  712 */     if (this.activeJob != null) {
/*      */       
/*  714 */       this.activeJob.cancel();
/*  715 */       endJob(this.activeJob);
/*      */     } 
/*      */     
/*  718 */     this.jobs.clear();
/*  719 */     this.clicks.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoCraftingToken craft(IScriptActionProvider provider, IAutoCraftingInitiator initiator, cio thePlayer, String itemId, int damageValue, int amount, boolean shouldThrowResult, boolean verbose) {
/*  735 */     AutoCraftingToken token = new AutoCraftingToken(initiator);
/*      */ 
/*      */     
/*  738 */     if (itemId == null || thePlayer == null)
/*      */     {
/*  740 */       return token.notifyCompleted("INVALID");
/*      */     }
/*      */ 
/*      */     
/*  744 */     ahb inventory = thePlayer.bg;
/*  745 */     if (inventory == null)
/*      */     {
/*  747 */       return token.notifyCompleted("INVALID");
/*      */     }
/*      */     
/*  750 */     ItemID itemID = new ItemID(itemId, damageValue);
/*      */ 
/*      */     
/*  753 */     if (this.mc.c.h() && creativeInventoryContains(itemID.item, damageValue))
/*      */     {
/*  755 */       return craftCreative(token, (ahd)thePlayer, inventory, shouldThrowResult, damageValue, itemID, amount);
/*      */     }
/*      */     
/*  758 */     return craftSurvival(token, (ahd)thePlayer, inventory, shouldThrowResult, damageValue, itemID, amount, verbose);
/*      */   }
/*      */ 
/*      */   
/*      */   private AutoCraftingToken craftCreative(AutoCraftingToken token, ahd thePlayer, ahb inventory, boolean shouldThrowResult, int damageValue, ItemID itemID, int amount) {
/*  763 */     if (shouldThrowResult) {
/*      */       
/*  765 */       if (damageValue < 0) damageValue = 0; 
/*  766 */       amj stack = new amj(itemID.item, amount, damageValue);
/*  767 */       thePlayer.a(stack, true, false);
/*  768 */       this.mc.c.a(stack);
/*      */     }
/*      */     else {
/*      */       
/*  772 */       inventory.a(itemID.item, Math.max(0, damageValue), (damageValue != -1), true);
/*  773 */       int l = thePlayer.bh.c.size() - 9 + thePlayer.bg.c;
/*  774 */       AbstractionLayer.getPlayerController().a(inventory.a(inventory.c), l);
/*      */     } 
/*      */     
/*  777 */     sendCraftingCompletedEvent(null, "CREATIVE", null);
/*  778 */     return token.notifyCompleted("CREATIVE");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AutoCraftingToken craftSurvival(AutoCraftingToken token, ahd thePlayer, ahb inventory, boolean shouldThrowResult, int damageValue, ItemID itemID, int amount, boolean verbose) {
/*  784 */     aop crafting = aop.a();
/*      */     
/*  786 */     List<aoo> recipes = crafting.b();
/*      */ 
/*      */     
/*  789 */     boolean foundItemsForRecipe = false;
/*  790 */     boolean foundRecipe = false;
/*      */ 
/*      */     
/*  793 */     for (aoo recipe : recipes) {
/*      */       
/*  795 */       amj recipeStack = recipe.b();
/*      */       
/*  797 */       if (recipeStack != null && recipeStack.b() == itemID.item && (damageValue == -1 || recipeStack.i() == damageValue)) {
/*      */         
/*  799 */         foundRecipe = true;
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  804 */           if (beginCrafting(token, inventory, recipe, amount, shouldThrowResult, verbose)) {
/*      */             
/*  806 */             foundItemsForRecipe = true;
/*      */             
/*      */             break;
/*      */           } 
/*  810 */         } catch (Exception ex) {
/*      */           
/*  812 */           Log.printStackTrace(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  817 */     if (!foundItemsForRecipe) {
/*      */       
/*  819 */       String reason = foundRecipe ? "NOTSTARTED" : "NORECIPE";
/*  820 */       Job.FailureType failure = foundRecipe ? Job.FailureType.INGREDIENTS : Job.FailureType.NORECIPE;
/*  821 */       sendCraftingCompletedEvent(null, reason, failure);
/*  822 */       return token.notifyCompleted(reason);
/*      */     } 
/*      */     
/*  825 */     return token;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean beginCrafting(AutoCraftingToken token, ahb inventory, aoo recipe, int amount, boolean throwResult, boolean verbose) throws IllegalArgumentException, SecurityException {
/*  841 */     if (!(recipe instanceof IVanillaRecipe))
/*      */     {
/*  843 */       return false;
/*      */     }
/*      */     
/*  846 */     IVanillaRecipe vanillaRecipe = (IVanillaRecipe)recipe;
/*  847 */     if (getRecipeItems(vanillaRecipe, inventory, verbose) == null)
/*      */     {
/*  849 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  853 */     return addJob(token, vanillaRecipe, amount, throwResult, verbose);
/*      */   }
/*      */ 
/*      */   
/*      */   void slotClick(byl craftingGui, ajk slot, int slotNumber, int button, boolean shift) {
/*  858 */     HelperContainerSlots.SlotClick click = new HelperContainerSlots.SlotClick(craftingGui, slot, slotNumber, button, shift);
/*      */     
/*  860 */     this.clicks.add(click);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addJob(AutoCraftingToken token, IVanillaRecipe recipe, int amount, boolean throwResult, boolean verbose) {
/*      */     try {
/*  867 */       this.jobs.add(new Job(token, recipe, amount, throwResult, verbose));
/*  868 */       return true;
/*      */     }
/*  870 */     catch (Exception ex) {
/*      */       
/*  872 */       ex.printStackTrace();
/*      */ 
/*      */       
/*  875 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void endJob(Job job) {
/*  880 */     job.end();
/*  881 */     this.lastCompletedJob = job;
/*  882 */     if (job == this.activeJob)
/*      */     {
/*  884 */       this.activeJob = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int onTick(IScriptActionProvider provider) {
/*  897 */     if (this.lastCompletedJob != null)
/*      */     {
/*  899 */       this.lastCompletedJob.onTick();
/*      */     }
/*      */     
/*  902 */     HelperContainerSlots.SlotClick click = this.clicks.poll();
/*  903 */     if (click != null) {
/*      */ 
/*      */       
/*  906 */       click.execute();
/*  907 */       return 1;
/*      */     } 
/*      */     
/*  910 */     if (this.activeJob != null)
/*      */     {
/*      */       
/*  913 */       return processJob(provider, this.activeJob);
/*      */     }
/*      */     
/*  916 */     Job nextJob = this.jobs.poll();
/*  917 */     if (nextJob != null) {
/*      */       
/*  919 */       this.activeJob = nextJob;
/*      */       
/*  921 */       return 1;
/*      */     } 
/*      */     
/*  924 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private int processJob(IScriptActionProvider provider, Job job) {
/*  929 */     if (job.isWaitingForGui())
/*      */     {
/*      */       
/*  932 */       if (!openGui(provider, job))
/*      */       {
/*      */         
/*  935 */         return 1;
/*      */       }
/*      */     }
/*      */     
/*  939 */     if (!job.isCrafting())
/*      */     {
/*      */       
/*  942 */       return 1;
/*      */     }
/*      */     
/*  945 */     boolean craftingOpen = this.mc.m instanceof byx;
/*  946 */     boolean inventoryOpen = HelperContainerSlots.currentScreenIsInventory(this.mc);
/*      */     
/*  948 */     if ((!inventoryOpen && !craftingOpen) || (job.needsTable() && !craftingOpen)) {
/*      */       
/*  950 */       if (job.isGridTimeoutState()) {
/*      */ 
/*      */         
/*  953 */         job.failed(Job.FailureType.NOGRID);
/*  954 */         sendCraftingCompletedEvent(job, "TIMEOUT", Job.FailureType.NOGRID);
/*  955 */         return 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  960 */       job.stop();
/*  961 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  966 */     if (job.processAction())
/*      */     {
/*  968 */       return 1;
/*      */     }
/*      */     
/*  971 */     cio player = this.mc.h;
/*  972 */     ahb inventory = player.bg;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  978 */       List<amj> recipeItems = getRecipeItems(job.getRecipe(), inventory, false);
/*  979 */       if (recipeItems == null)
/*      */       {
/*      */         
/*  982 */         if (job.incrementFailedCraftTicks() > 10)
/*      */         {
/*  984 */           clearCraftingGrid(HelperContainerSlots.getGuiContainer(this.mc));
/*  985 */           if (job.isVerbose())
/*      */           {
/*  987 */             getRecipeItems(job.getRecipe(), inventory, true);
/*      */           }
/*  989 */           sendCraftingCompletedEvent(job, "NOITEMS", Job.FailureType.INGREDIENTS);
/*  990 */           return 1;
/*      */         }
/*      */       
/*      */       }
/*  994 */     } catch (Exception ex) {
/*      */       
/*  996 */       Log.printStackTrace(ex);
/*  997 */       sendCraftingCompletedEvent(job, "ERROR", Job.FailureType.UNEXPECTED);
/*  998 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1004 */     if (job.getAmount() > 0 && (craftingOpen || (inventoryOpen && !job.needsTable())))
/*      */     {
/*      */       
/* 1007 */       if (!craftRecipe(provider, job))
/*      */       {
/*      */         
/* 1010 */         if (job.incrementFailedCraftTicks() > 10)
/*      */         {
/* 1012 */           sendCraftingCompletedEvent(job, "NOSPACE", Job.FailureType.NOSPACE);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1018 */     if (job.isComplete()) {
/*      */       
/* 1020 */       clearCraftingGrid(HelperContainerSlots.getGuiContainer(this.mc));
/* 1021 */       job.setStatus(Job.Status.DONE);
/* 1022 */       sendCraftingCompletedEvent(job, "DONE", null);
/*      */     } 
/*      */     
/* 1025 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean openGui(IScriptActionProvider provider, Job job) {
/* 1031 */     if (this.mc.c.h()) {
/*      */       
/* 1033 */       sendCraftingCompletedEvent(job, "CREATIVE", null);
/* 1034 */       return false;
/*      */     } 
/*      */     
/* 1037 */     boolean craftingOpen = this.mc.m instanceof byx;
/* 1038 */     boolean inventoryOpen = HelperContainerSlots.currentScreenIsInventory(this.mc);
/*      */ 
/*      */     
/* 1041 */     if (!job.needsTable()) {
/*      */       
/* 1043 */       if (!craftingOpen && !inventoryOpen) {
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 1048 */           this.mc.a((bxf)new bzj((ahd)this.mc.h));
/* 1049 */           job.setStatus(Job.Status.WAITING_FOR_INVENTORY);
/*      */         }
/* 1051 */         catch (Exception ex) {
/*      */           
/* 1053 */           ex.printStackTrace();
/* 1054 */           return false;
/*      */         } 
/*      */       }
/*      */       
/* 1058 */       if (craftingOpen || inventoryOpen) {
/*      */         
/* 1060 */         job.beginCrafting();
/* 1061 */         return true;
/*      */       } 
/* 1063 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1067 */     if (!job.requestedTable()) {
/*      */ 
/*      */       
/* 1070 */       job.setStatus(Job.Status.WAITING_FOR_WORKBENCH);
/*      */       
/* 1072 */       if (this.mc.s != null && this.mc.s.a == brv.b) {
/*      */         
/* 1074 */         dt blockPos = this.mc.s.a();
/* 1075 */         atr block = this.mc.f.p(blockPos).c();
/*      */ 
/*      */         
/* 1078 */         if (block == aty.ai) {
/*      */           
/* 1080 */           if (inventoryOpen) {
/*      */ 
/*      */             
/* 1083 */             this.mc.a(null);
/* 1084 */             return false;
/*      */           } 
/*      */ 
/*      */           
/* 1088 */           provider.actionUseItem(this.mc, this.mc.h, null, this.mc.h.bg.c);
/* 1089 */           job.setStatus(Job.Status.REQUESTED_WORKBENCH);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1095 */     if (job.isGuiTimeoutState()) {
/*      */       
/* 1097 */       sendCraftingCompletedEvent(job, "TIMEOUT", Job.FailureType.WORKBENCH);
/* 1098 */       return false;
/*      */     } 
/*      */     
/* 1101 */     if (craftingOpen) {
/*      */       
/* 1103 */       job.beginCrafting();
/* 1104 */       return true;
/*      */     } 
/*      */     
/* 1107 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendCraftingCompletedEvent(Job job, String reason, Job.FailureType failureType) {
/* 1117 */     if (job != null) {
/*      */       
/* 1119 */       if (job.getToken() != null)
/*      */       {
/* 1121 */         job.getToken().notifyCompleted(reason);
/*      */       }
/*      */       
/* 1124 */       job.failed(failureType);
/* 1125 */       endJob(job);
/*      */     } 
/*      */     
/* 1128 */     MacroModCore.sendEvent(BuiltinEvents.onAutoCraftingComplete.getName(), 50, new String[] { reason });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<amj> getRecipeItems(IVanillaRecipe recipe, ahb inventory, boolean verbose) throws IllegalArgumentException, SecurityException {
/* 1145 */     List<amj> items = new ArrayList<amj>();
/* 1146 */     List<amj> requiredItems = new ArrayList<amj>();
/*      */ 
/*      */     
/* 1149 */     for (amj recipeItem : recipe.getItems()) {
/*      */       
/* 1151 */       items.add(recipeItem);
/* 1152 */       if (recipeItem != null)
/*      */       {
/* 1154 */         requiredItems.add(recipeItem);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1159 */     while (requiredItems.size() > 0) {
/*      */       
/* 1161 */       Iterator<amj> iterator = requiredItems.iterator();
/* 1162 */       amj requiredItem = iterator.next();
/* 1163 */       iterator.remove();
/* 1164 */       int requiredCount = 1;
/*      */       
/* 1166 */       while (iterator.hasNext()) {
/*      */         
/* 1168 */         if (((amj)iterator.next()).a(requiredItem)) {
/*      */           
/* 1170 */           iterator.remove();
/* 1171 */           requiredCount++;
/*      */         } 
/*      */       } 
/*      */       
/* 1175 */       for (int i = 0; i < inventory.a.length; i++) {
/*      */         
/* 1177 */         if (inventory.a[i] != null && itemStackMatches(requiredItem, inventory.a[i])) {
/*      */           
/* 1179 */           requiredCount -= (inventory.a[i]).b;
/*      */ 
/*      */ 
/*      */           
/* 1183 */           if (requiredCount <= 0) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1191 */       if (requiredCount > 0) {
/*      */         
/* 1193 */         String message = "Not enough " + requiredItem.q() + " for recipe. Missing " + requiredCount;
/* 1194 */         Log.info(message);
/* 1195 */         if (verbose)
/*      */         {
/* 1197 */           AbstractionLayer.addChatMessage("§b[CRAFT] §a" + message);
/*      */         }
/* 1199 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/* 1203 */     return items;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean craftRecipe(IScriptActionProvider provider, Job job) {
/*      */     try {
/* 1216 */       return nextAction(provider, job);
/*      */     }
/* 1218 */     catch (Exception ex) {
/*      */       
/* 1220 */       Log.printStackTrace(ex);
/*      */ 
/*      */       
/* 1223 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean nextAction(IScriptActionProvider provider, Job job) {
/* 1235 */     byl craftingGui = HelperContainerSlots.getGuiContainer(this.mc);
/* 1236 */     aib container = craftingGui.h;
/*      */ 
/*      */     
/* 1239 */     boolean craftingTable = craftingGui instanceof byx;
/* 1240 */     int slotStart = craftingTable ? 10 : 9;
/* 1241 */     int craftingWidth = craftingTable ? 3 : 2;
/*      */     
/* 1243 */     if (job.getPhase() == 0) {
/*      */ 
/*      */       
/* 1246 */       amj is = this.mc.h.bg.p();
/* 1247 */       if (is != null) {
/*      */ 
/*      */         
/* 1250 */         slotClick(craftingGui, null, -999, 0, false);
/* 1251 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1255 */       if (!clearCraftingGrid(craftingGui))
/*      */       {
/* 1257 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1261 */       if (container.a(0).d() != null)
/*      */       {
/* 1263 */         return false;
/*      */       }
/*      */       
/* 1266 */       job.nextPhase();
/* 1267 */       return true;
/*      */     } 
/*      */     
/* 1270 */     if (job.getPhase() == 1) {
/*      */ 
/*      */       
/* 1273 */       job.beginActionLayoutRecipe(this, craftingGui, container, slotStart, craftingWidth);
/* 1274 */       return true;
/*      */     } 
/*      */     
/* 1277 */     if (job.getPhase() % 5 != 0) {
/*      */ 
/*      */       
/* 1280 */       job.nextPhase();
/* 1281 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1286 */     job.beginActionTakeOutput(this, craftingGui, container, 0);
/* 1287 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean clearCraftingGrid(byl craftingGui) {
/* 1295 */     int craftingLength = (craftingGui instanceof byx) ? 9 : 4;
/* 1296 */     boolean cleared = true;
/*      */     
/* 1298 */     for (int pass = 0; pass < 2; pass++) {
/*      */       
/* 1300 */       cleared = true;
/* 1301 */       for (int slotNumber = 0; slotNumber < craftingLength; slotNumber++) {
/*      */         
/* 1303 */         ajk slot = craftingGui.h.a(slotNumber + 1);
/* 1304 */         if (slot.e()) {
/*      */ 
/*      */           
/* 1307 */           HelperContainerSlots.survivalInventorySlotClick(craftingGui, slot, slotNumber + 1, 0, true);
/* 1308 */           cleared = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1313 */     return cleared;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean itemStackMatches(amj recipeItemStack, amj itemStack) {
/* 1325 */     if (itemStack == null) return (recipeItemStack == null); 
/* 1326 */     if (recipeItemStack.b() != itemStack.b()) return false; 
/* 1327 */     if ((short)recipeItemStack.i() != Short.MAX_VALUE && recipeItemStack.i() != itemStack.i()) return false;
/*      */     
/* 1329 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean creativeInventoryContains(alq item, int damageValue) {
/* 1334 */     if (item != null && item.c() != null) {
/*      */       
/* 1336 */       List<amj> subItems = new ArrayList<amj>();
/* 1337 */       item.a(item, null, subItems);
/*      */       
/* 1339 */       for (amj subItem : subItems) {
/*      */         
/* 1341 */         if (subItem.i() == damageValue || damageValue == -1) {
/* 1342 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1346 */     return false;
/*      */   }
/*      */   
/*      */   static void debug(String format, Object... args) {}
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\crafting\AutoCraftingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
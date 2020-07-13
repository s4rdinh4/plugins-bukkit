/*    */ package net.eq2online.macros.gui.layout;
/*    */ 
/*    */ import bsu;
/*    */ import bty;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.MacroType;
/*    */ import net.eq2online.macros.event.MacroEventManager;
/*    */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
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
/*    */ public class LayoutPanelEvents
/*    */   extends LayoutPanelStandard
/*    */ {
/*    */   private static final int BUTTONS_PER_COLUMN = 9;
/*    */   private static final int COLUMN_WIDTH = 139;
/*    */   private static final int ROW_HEIGHT = 20;
/*    */   private static final int LEFT_POS = 6;
/*    */   private static final int TOP_POS = 4;
/*    */   
/*    */   public LayoutPanelEvents(bsu minecraft, int controlId) {
/* 34 */     super(minecraft, controlId, "events.layout", MacroType.Event);
/*    */ 
/*    */     
/* 37 */     MacroModCore.registerSettingsProvider(this);
/*    */     
/* 39 */     this.layoutSettingDescription = "Serialised version of the event layout, each param is {MappingID,X,Y}";
/* 40 */     this.defaultLayout = "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1016,6,164}{1008,145,4}{1009,145,24}{1010,145,44}{1011,145,64}{1012,145,84}{1013,145,104}{1014,145,124}{1015,145,144}{1017,145,164}{1018,284,4}";
/*    */ 
/*    */ 
/*    */     
/* 44 */     this.widgetWidth = 135;
/* 45 */     this.widgetHeight = 16;
/* 46 */     this.centreAlign = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadPanelLayout(String panelLayout) {
/* 53 */     this.widgets = new ILayoutWidget[10000];
/* 54 */     MacroEventManager eventManager = MacroModCore.getMacroManager().getEventManager();
/* 55 */     List<IMacroEvent> events = eventManager.getEvents();
/* 56 */     int nextAvailablePos = 0;
/*    */     
/* 58 */     for (IMacroEvent event : events) {
/*    */       
/* 60 */       int row = nextAvailablePos % 9;
/* 61 */       int col = nextAvailablePos / 9;
/* 62 */       nextAvailablePos++;
/*    */       
/* 64 */       int xPosition = 6 + 139 * col;
/* 65 */       int yPosition = 4 + 20 * row;
/*    */       
/* 67 */       ILayoutWidget<LayoutPanelStandard> widget = getWidget(eventManager.getEventID(event.getName()), true);
/* 68 */       if (widget.getWidgetPosition(this).equals(new Point(0, 0))) widget.setWidgetPosition(this, xPosition, yPosition);
/*    */     
/*    */     } 
/* 71 */     Matcher layoutPatternMatcher = this.layoutPattern.matcher(panelLayout);
/*    */     
/* 73 */     while (layoutPatternMatcher.find()) {
/*    */       
/* 75 */       int widgetId = Integer.parseInt(layoutPatternMatcher.group(1));
/*    */       
/* 77 */       if (eventManager.getEvent(widgetId) != null) {
/*    */         
/* 79 */         int xCoord = Integer.parseInt(layoutPatternMatcher.group(2));
/* 80 */         int yCoord = Integer.parseInt(layoutPatternMatcher.group(3));
/* 81 */         ILayoutWidget<LayoutPanelStandard> widget = getWidget(widgetId, true);
/* 82 */         if (widget != null) widget.setWidgetPosition(this, xCoord, yCoord);
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ILayoutWidget createWidget(int widgetId) {
/* 94 */     return new LayoutButtonEvent((bty)MacroModCore.getInstance().getLegacyFontRenderer(), widgetId, this.macroType.getName(widgetId), this.widgetWidth, this.widgetHeight, this.centreAlign);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutPanelEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
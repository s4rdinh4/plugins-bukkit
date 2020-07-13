/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.Reflection;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*     */ import net.eq2online.macros.gui.layout.LayoutWidget;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DesignableGuiControl
/*     */   extends DesignableGui
/*     */   implements ILayoutWidget<DesignableGuiLayout>
/*     */ {
/*     */   public static final int NORTH = 0;
/*     */   public static final int SOUTH = 1;
/*     */   public static final int EAST = 2;
/*     */   public static final int WEST = 3;
/*  42 */   private static final Map<String, Class<? extends DesignableGuiControl>> controlTypes = new TreeMap<String, Class<? extends DesignableGuiControl>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final DesignableGuiControl[] controls = new DesignableGuiControl[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public int rowSpan = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public int colSpan = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public int zIndex;
/*     */ 
/*     */   
/*  84 */   protected Rectangle lastDrawnBoundingBox = new Rectangle();
/*     */   
/*  86 */   protected Point lastDrawnLocation = new Point();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   protected int foreColour = -16711936;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   protected int backColour = -1342177280;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   protected int padding = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean visible = true;
/*     */ 
/*     */   
/* 108 */   private TreeMap<String, String> properties = new TreeMap<String, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bty fontRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int dragOffsetX;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int dragOffsetY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DesignableGuiControl(int id) {
/* 129 */     this.id = id;
/* 130 */     this.zIndex = id;
/* 131 */     this.fontRenderer = AbstractionLayer.getFontRenderer();
/*     */     
/* 133 */     initProperties();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDefaultControlName() {
/* 155 */     return getControlType().toUpperCase() + " " + this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 160 */     return getProperty("visible", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCloseGuiOnClick() {
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 170 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 175 */     if (name.length() < 1) {
/* 176 */       name = getDefaultControlName();
/*     */     }
/* 178 */     this.name = name;
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
/*     */   public void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, LayoutPanelEditMode mode, boolean selected, boolean denied) {
/* 209 */     this.lastDrawnBoundingBox = boundingBox;
/* 210 */     this.lastDrawnLocation = new Point(boundingBox.x + boundingBox.width / 2, boundingBox.y);
/*     */     
/* 212 */     boolean bound = getWidgetIsBound();
/* 213 */     boolean special = MacroModCore.getMacroManager().isReservedKey(this.id);
/* 214 */     boolean global = MacroModCore.getMacroManager().isMacroGlobal(this.id, false);
/*     */ 
/*     */     
/* 217 */     int drawColour = bound ? LayoutWidget.COLOR_BOUND : LayoutWidget.COLOR_UNBOUND;
/* 218 */     if (special) drawColour = bound ? LayoutWidget.COLOR_BOUNDSPECIAL : LayoutWidget.COLOR_SPECIAL; 
/* 219 */     if (global) drawColour = LayoutWidget.COLOR_BOUNDGLOBAL; 
/* 220 */     if (selected) drawColour = LayoutWidget.COLOR_SELECTED; 
/* 221 */     if (denied) drawColour = LayoutWidget.COLOR_DENIED;
/*     */ 
/*     */     
/* 224 */     int borderColour = -8355712;
/* 225 */     int backgroundColour = -16777216;
/*     */     
/* 227 */     if (mouseOver(boundingBox, mouseX, mouseY, false)) {
/*     */ 
/*     */       
/* 230 */       borderColour = -1;
/*     */     }
/* 232 */     else if (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly) {
/*     */ 
/*     */       
/* 235 */       borderColour = selected ? -103 : -256;
/*     */     }
/* 237 */     else if (mode == LayoutPanelEditMode.Delete) {
/*     */       
/* 239 */       borderColour = bound ? -65536 : -22016;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 244 */     else if (bound) {
/*     */       
/* 246 */       switch (mode) {
/*     */         case Copy:
/* 248 */           borderColour = -16711936; break;
/* 249 */         case Move: borderColour = -16711681;
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 255 */     drawRect(boundingBox, borderColour);
/* 256 */     drawRect(boundingBox, 1711276032, 1, 1, 0, 0);
/* 257 */     drawRect(boundingBox, mouseOver(boundingBox, mouseX, mouseY, false) ? -13421773 : backgroundColour, 1);
/*     */     
/* 259 */     drawWidget(parent, boundingBox, mouseX, mouseY, drawColour);
/*     */     
/* 261 */     if (mode != LayoutPanelEditMode.Reserve) {
/*     */       
/* 263 */       if (MacroModCore.getMacroManager().isKeyAlwaysOverridden(this.id, false, true)) {
/*     */         
/* 265 */         AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 266 */         GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 267 */         drawTexturedModalRect(boundingBox.x + boundingBox.width - 11, boundingBox.y - 1, boundingBox.x + boundingBox.width - 1, boundingBox.y + 11, 72, 104, 96, 128, 0.00390625F);
/*     */       } 
/*     */       
/* 270 */       if (MacroModCore.getMacroManager().isKeyOverlaid(this.id))
/*     */       {
/* 272 */         drawRect(boundingBox, 1610612991);
/*     */       }
/*     */     } 
/*     */     
/* 276 */     if (selected) {
/* 277 */       drawDragHandles(boundingBox, mouseX, mouseY, mode, selected);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void drawDragHandles(Rectangle boundingBox, int mouseX, int mouseY, LayoutPanelEditMode mode, boolean selected) {
/* 282 */     int handleColour = -256;
/*     */     
/* 284 */     Rectangle handleNorth = getDragHandle(0, boundingBox);
/* 285 */     drawRectOutline(handleNorth, handleColour, 1);
/*     */     
/* 287 */     Rectangle handleSouth = getDragHandle(1, boundingBox);
/* 288 */     drawRectOutline(handleSouth, handleColour, 1);
/*     */     
/* 290 */     Rectangle handleEast = getDragHandle(2, boundingBox);
/* 291 */     drawRectOutline(handleEast, handleColour, 1);
/*     */     
/* 293 */     Rectangle handleWest = getDragHandle(3, boundingBox);
/* 294 */     drawRectOutline(handleWest, handleColour, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int mouseOverHandle(Rectangle boundingBox, int mouseX, int mouseY) {
/* 299 */     if (getDragHandle(0, boundingBox).contains(mouseX, mouseY)) return 0; 
/* 300 */     if (getDragHandle(1, boundingBox).contains(mouseX, mouseY)) return 1; 
/* 301 */     if (getDragHandle(2, boundingBox).contains(mouseX, mouseY)) return 2; 
/* 302 */     if (getDragHandle(3, boundingBox).contains(mouseX, mouseY)) return 3; 
/* 303 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getDragHandle(int direction, Rectangle boundingBox) {
/* 308 */     int size = 2;
/*     */     
/* 310 */     if (direction == 0) return new Rectangle(boundingBox.x + boundingBox.width / 2 - size, boundingBox.y - size * 2 - 1, size * 2, size * 2); 
/* 311 */     if (direction == 1) return new Rectangle(boundingBox.x + boundingBox.width / 2 - size, boundingBox.y + boundingBox.height + 1, size * 2, size * 2); 
/* 312 */     if (direction == 2) return new Rectangle(boundingBox.x + boundingBox.width + 1, boundingBox.y + boundingBox.height / 2 - size, size * 2, size * 2); 
/* 313 */     if (direction == 3) return new Rectangle(boundingBox.x - size * 2 - 1, boundingBox.y + boundingBox.height / 2 - size, size * 2, size * 2);
/*     */     
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasProperty(String property) {
/* 320 */     return this.properties.containsKey(property);
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
/*     */   public final String getProperty(String property, String defaultValue) {
/* 332 */     if (property.equalsIgnoreCase("name")) return getName();
/*     */     
/* 334 */     return this.properties.containsKey(property) ? this.properties.get(property) : defaultValue;
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
/*     */   public final int getProperty(String property, int defaultValue) {
/*     */     try {
/* 348 */       return this.properties.containsKey(property) ? Integer.parseInt(this.properties.get(property)) : defaultValue;
/*     */     }
/* 350 */     catch (NumberFormatException ex) {
/*     */       
/* 352 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getProperty(String property, boolean defaultValue) {
/* 363 */     return getProperty(property, defaultValue ? "1" : "0").equals("1");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/* 368 */     if (property.equals("visible")) setProperty(property, boolValue);
/*     */   
/*     */   }
/*     */   
/*     */   public final void setProperty(String property, String value) {
/* 373 */     if (property.equalsIgnoreCase("name")) {
/*     */       
/* 375 */       setName(value);
/*     */       
/*     */       return;
/*     */     } 
/* 379 */     this.properties.put(property, value);
/* 380 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setProperty(String property, int value) {
/* 385 */     this.properties.put(property, String.valueOf(value));
/* 386 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setProperty(String property, boolean value) {
/* 391 */     this.properties.put(property, value ? "1" : "0");
/* 392 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 397 */     return new GuiDialogBoxControlProperties(parentScreen, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyChanges(GuiDialogBoxControlProperties guiDialogBoxControlProperties) {
/* 402 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void swapZ(DesignableGuiControl other) {
/* 407 */     int zIndex = other.zIndex;
/* 408 */     other.zIndex = this.zIndex;
/* 409 */     this.zIndex = zIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void bringToFront() {
/* 414 */     for (int id = MacroType.Control.getMinId(); id <= MacroType.Control.getAbsoluteMaxId(); id++) {
/*     */       
/* 416 */       if (id != this.id && controls[id] != null && (controls[id]).zIndex > this.zIndex)
/*     */       {
/* 418 */         swapZ(controls[id]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setWidgetPosition(DesignableGuiLayout parent, int column, int row) {
/* 429 */     if (canPlaceAt(row, column, parent) && (this.xPosition != column || this.yPosition != row)) {
/*     */       
/* 431 */       this.xPosition = column;
/* 432 */       this.yPosition = row;
/* 433 */       return true;
/*     */     } 
/*     */     
/* 436 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidgetPositionSnapped(DesignableGuiLayout parent, int x, int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getWidgetPosition(DesignableGuiLayout parent) {
/* 454 */     return this.lastDrawnLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetId() {
/* 463 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetWidth(DesignableGuiLayout parent) {
/* 472 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBound() {
/* 481 */     return MacroModCore.getMacroManager().isMacroBound(this.id, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsDenied() {
/* 490 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidgetZIndex() {
/* 499 */     return this.zIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWidgetDisplayText() {
/* 508 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWidgetDeniedText() {
/* 517 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleReservedState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(bsu minecraft, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {
/* 542 */     this.dragOffsetX = 0;
/* 543 */     this.dragOffsetY = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(bsu minecraft, int dragOffsetX, int dragOffsetY) {
/* 552 */     this.dragOffsetX = dragOffsetX;
/* 553 */     this.dragOffsetY = dragOffsetY;
/*     */     
/* 555 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseOver(Rectangle boundingBox, int mouseX, int mouseY, boolean selected) {
/* 564 */     return (boundingBox.contains(mouseX, mouseY) || (selected && mouseOverHandle(boundingBox, mouseX, mouseY) > -1));
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
/*     */   public boolean cccupies(int row, int col) {
/* 576 */     return (row >= this.yPosition && row < this.yPosition + this.rowSpan && col >= this.xPosition && col < this.xPosition + this.colSpan);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceAt(int row, int col, DesignableGuiLayout layout) {
/* 581 */     return canPlaceAt(row, col, this.rowSpan, this.colSpan, layout);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceAt(int row, int col, int rowSpan, int colSpan, DesignableGuiLayout layout) {
/* 586 */     for (int x = col; x < col + colSpan; x++) {
/*     */       
/* 588 */       for (int y = row; y < row + rowSpan; y++) {
/*     */         
/* 590 */         if (layout.isCellOccupied(y, x, this)) {
/* 591 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 595 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element controlNode) {
/* 606 */     controlNode.setAttribute("id", String.valueOf(this.id));
/* 607 */     controlNode.setAttribute("name", getName());
/* 608 */     controlNode.setAttribute("x", String.valueOf(this.xPosition));
/* 609 */     controlNode.setAttribute("y", String.valueOf(this.yPosition));
/* 610 */     if (this.rowSpan != 1) controlNode.setAttribute("rowspan", String.valueOf(this.rowSpan)); 
/* 611 */     if (this.colSpan != 1) controlNode.setAttribute("colspan", String.valueOf(this.colSpan));
/*     */     
/* 613 */     for (Map.Entry<String, String> property : this.properties.entrySet()) {
/*     */       
/* 615 */       Element propertyNode = xml.createElement(property.getKey());
/* 616 */       propertyNode.setTextContent(property.getValue());
/* 617 */       controlNode.appendChild(propertyNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiControl fromXml(Node xml) {
/* 623 */     int id = Xml.getAttributeValue(xml, "id", 0);
/*     */     
/* 625 */     if (isValidControlId(id)) {
/*     */       
/* 627 */       DesignableGuiControl newControl = createControl(xml.getLocalName(), id);
/*     */       
/* 629 */       if (newControl != null) {
/*     */         
/* 631 */         controls[id].setName(Xml.getAttributeValue(xml, "name", newControl.getDefaultControlName()));
/* 632 */         (controls[id]).xPosition = Xml.getAttributeValue(xml, "x", 0);
/* 633 */         (controls[id]).yPosition = Xml.getAttributeValue(xml, "y", 0);
/* 634 */         (controls[id]).rowSpan = Math.max(1, Xml.getAttributeValue(xml, "rowspan", 1));
/* 635 */         (controls[id]).colSpan = Math.max(1, Xml.getAttributeValue(xml, "colspan", 1));
/*     */         
/* 637 */         for (Node propertyNode : Xml.queryAsArray(xml, "*"))
/*     */         {
/* 639 */           (controls[id]).properties.put(propertyNode.getLocalName(), propertyNode.getTextContent());
/*     */         }
/*     */         
/* 642 */         controls[id].update();
/*     */         
/* 644 */         return controls[id];
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidControlId(int id) {
/* 657 */     return MacroType.Control.supportsId(id);
/*     */   }
/*     */   
/*     */   public static final int getFreeControlId() {
/*     */     int id;
/* 662 */     for (id = MacroType.Control.getMinId(); id <= MacroType.Control.getMaxId(); id++) {
/* 663 */       if (controls[id] == null) return id; 
/*     */     } 
/* 665 */     for (id = MacroType.Control.getMinExtId(); id <= MacroType.Control.getMaxExtId(); id++) {
/* 666 */       if (controls[id] == null) return id; 
/*     */     } 
/* 668 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiControl createControl(String controlType) {
/* 673 */     int freeControlId = getFreeControlId();
/*     */     
/* 675 */     if (freeControlId > -1) {
/* 676 */       return createControl(controlType, freeControlId);
/*     */     }
/* 678 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiControl createControl(String controlType, int id) {
/* 683 */     DesignableGuiControl newControl = null;
/*     */     
/* 685 */     if (controlTypes.containsKey(controlType)) {
/*     */       
/*     */       try {
/*     */         
/* 689 */         Constructor<? extends DesignableGuiControl> controlConstructor = ((Class<? extends DesignableGuiControl>)controlTypes.get(controlType)).getDeclaredConstructor(new Class[] { int.class });
/* 690 */         newControl = controlConstructor.newInstance(new Object[] { Integer.valueOf(id) });
/*     */       } catch (Exception ex) {
/* 692 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 700 */     controls[id] = newControl;
/*     */     
/* 702 */     return newControl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final DesignableGuiControl getControl(int id) {
/* 707 */     if (!isValidControlId(id)) return null; 
/* 708 */     return controls[id];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends DesignableGuiControl> T getControl(String name) {
/* 719 */     for (DesignableGuiControl control : controls) {
/*     */       
/* 721 */       if (control != null && control.getName().equals(name)) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 726 */           return (T)control;
/*     */         
/*     */         }
/* 729 */         catch (ClassCastException ex) {
/*     */           
/* 731 */           return null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 736 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final String getControlName(int id) {
/* 741 */     if (!isValidControlId(id) || controls[id] == null) {
/* 742 */       return "CONTROL " + id;
/*     */     }
/* 744 */     return controls[id].getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeControl(int controlId) {
/* 749 */     if (isValidControlId(controlId)) {
/* 750 */       controls[controlId] = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void registerControlClass(String controlTypeName, Class<? extends DesignableGuiControl> controlClass) {
/* 755 */     controlTypes.put(controlTypeName.toLowerCase(), controlClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getAvailableControlTypes() {
/* 760 */     return controlTypes.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 765 */     LinkedList<Class<? extends DesignableGuiControl>> controls = Reflection.getSubclassesFor(DesignableGuiControl.class, DesignableGuiControl.class, "DesignableGuiControl", null);
/* 766 */     for (Class<?> control : controls)
/*     */     {
/* 768 */       registerControlClass(control.getSimpleName().substring(20).toLowerCase(), (Class)control);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void initProperties();
/*     */   
/*     */   protected abstract void onTick();
/*     */   
/*     */   protected abstract String getControlType();
/*     */   
/*     */   protected abstract void update();
/*     */   
/*     */   protected abstract void draw(DesignableGuiLayout paramDesignableGuiLayout, Rectangle paramRectangle, int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract void drawWidget(DesignableGuiLayout paramDesignableGuiLayout, Rectangle paramRectangle, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
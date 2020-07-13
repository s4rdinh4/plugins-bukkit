/*      */ package net.eq2online.macros.gui.designable;
/*      */ 
/*      */ import bsu;
/*      */ import bty;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.util.ArrayList;
/*      */ import java.util.TreeMap;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.gui.layout.LayoutPanelEditMode;
/*      */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import net.eq2online.xml.Xml;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
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
/*      */ public class DesignableGuiLayout
/*      */   extends DesignableGui
/*      */ {
/*      */   public final String name;
/*   36 */   public String displayName = "Layout";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   41 */   protected int rows = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   46 */   protected int columns = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   51 */   protected int padding = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean drawGrid = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean dotGrid = false;
/*      */ 
/*      */ 
/*      */   
/*   63 */   protected TreeMap<Integer, DesignableGuiControl> controls = new TreeMap<Integer, DesignableGuiControl>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   protected int[] columnMetrics = new int[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   protected int[] fixedWidthTo = new int[this.columnMetrics.length];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   protected int[] dynamicWidthTo = new int[this.columnMetrics.length];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   protected int fixedWidthAllocation = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   89 */   protected int dynamicColumns = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   protected String placingControlType = null;
/*      */   
/*   96 */   protected int mouseOverColumn = -1;
/*      */   
/*   98 */   protected int editingColumn = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DesignableGuiLayout(String name) {
/*  107 */     this.name = name;
/*  108 */     this.displayName = name;
/*      */     
/*  110 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DesignableGuiLayout(Node xml) {
/*  120 */     this.name = Xml.getAttributeValue(xml, "name", "default");
/*  121 */     this.displayName = Xml.getAttributeValue(xml, "display", this.name);
/*  122 */     this.rows = Math.max(1, Xml.getAttributeValue(xml, "rows", 10));
/*  123 */     this.columns = Math.max(1, Xml.getAttributeValue(xml, "columns", 6));
/*  124 */     this.padding = Math.max(0, Xml.getAttributeValue(xml, "padding", this.name.equalsIgnoreCase("ingame") ? 1 : 2));
/*  125 */     this.drawGrid = Xml.getAttributeValue(xml, "grid", "false").equalsIgnoreCase("true");
/*  126 */     this.dotGrid = Xml.getAttributeValue(xml, "dots", "false").equalsIgnoreCase("true");
/*      */     
/*  128 */     for (Node controlNode : Xml.queryAsArray(xml, "gc:controls/gc:*")) {
/*      */       
/*  130 */       DesignableGuiControl newControl = DesignableGuiControl.fromXml(controlNode);
/*  131 */       if (newControl != null) {
/*      */         
/*  133 */         this.controls.put(Integer.valueOf(newControl.id), newControl);
/*  134 */         this.columns = Math.max(this.columns, newControl.xPosition + newControl.colSpan);
/*  135 */         this.rows = Math.max(this.rows, newControl.yPosition + newControl.rowSpan);
/*      */       } 
/*      */     } 
/*      */     
/*  139 */     for (Node metricNode : Xml.queryAsArray(xml, "gc:metrics/gc:column")) {
/*      */       
/*  141 */       int id = Xml.getAttributeValue(metricNode, "id", -1);
/*  142 */       int width = Math.max(0, Xml.getAttributeValue(metricNode, "width", 0));
/*  143 */       if (id > -1 && id < this.columns && width > 0) {
/*  144 */         this.columnMetrics[id] = width;
/*      */       }
/*      */     } 
/*  147 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Document xml, Element node) {
/*  158 */     node.setAttribute("name", this.name);
/*  159 */     node.setAttribute("display", this.displayName);
/*  160 */     node.setAttribute("rows", String.valueOf(this.rows));
/*  161 */     node.setAttribute("columns", String.valueOf(this.columns));
/*  162 */     node.setAttribute("padding", String.valueOf(this.padding));
/*  163 */     node.setAttribute("grid", this.drawGrid ? "true" : "false");
/*  164 */     node.setAttribute("dots", this.dotGrid ? "true" : "false");
/*      */     
/*  166 */     Element metricsNode = xml.createElement("gc:metrics");
/*  167 */     boolean hasMetrics = false;
/*      */     
/*  169 */     for (int i = 0; i < this.columnMetrics.length; i++) {
/*      */       
/*  171 */       if (this.columnMetrics[i] > 0) {
/*      */         
/*  173 */         hasMetrics = true;
/*  174 */         Element metricNode = xml.createElement("gc:column");
/*  175 */         metricNode.setAttribute("id", String.valueOf(i));
/*  176 */         metricNode.setAttribute("width", String.valueOf(this.columnMetrics[i]));
/*  177 */         metricsNode.appendChild(metricNode);
/*      */       } 
/*      */     } 
/*      */     
/*  181 */     if (hasMetrics)
/*      */     {
/*  183 */       node.appendChild(metricsNode);
/*      */     }
/*      */     
/*  186 */     Element controlsNode = xml.createElement("gc:controls");
/*      */     
/*  188 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  190 */       Element controlNode = xml.createElement("gc:" + control.getControlType());
/*  191 */       control.save(xml, controlNode);
/*  192 */       controlsNode.appendChild(controlNode);
/*      */     } 
/*      */     
/*  195 */     node.appendChild(controlsNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateColumnMetrics() {
/*  203 */     this.placingControlType = null;
/*      */     
/*  205 */     int dynamicColumnsAt = 0;
/*  206 */     this.dynamicColumns = this.columns;
/*  207 */     this.fixedWidthAllocation = 0;
/*      */     
/*  209 */     for (int i = 0; i < this.columnMetrics.length; i++) {
/*      */       
/*  211 */       this.fixedWidthTo[i] = this.fixedWidthAllocation;
/*  212 */       this.dynamicWidthTo[i] = dynamicColumnsAt;
/*      */       
/*  214 */       if (i < this.columns) {
/*      */         
/*  216 */         this.fixedWidthAllocation += this.columnMetrics[i];
/*  217 */         if (this.columnMetrics[i] > 0) { this.dynamicColumns--; } else { dynamicColumnsAt++; }
/*      */       
/*      */       } 
/*      */     } 
/*  221 */     if (this.dynamicColumns < 1) {
/*      */       
/*  223 */       this.columnMetrics[0] = 0;
/*  224 */       updateColumnMetrics();
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
/*      */   public DesignableGuiControl getControl(int controlId) {
/*  236 */     return this.controls.get(Integer.valueOf(controlId));
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
/*      */   public <T extends DesignableGuiControl> T getControl(String name) {
/*  248 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  250 */       if (control.getName().equals(name)) {
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  255 */           return (T)control;
/*      */         
/*      */         }
/*  258 */         catch (ClassCastException ex) {
/*      */           
/*  260 */           return null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  265 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<DesignableGuiControl> getControls() {
/*  275 */     return new ArrayList<DesignableGuiControl>(this.controls.values());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  283 */     for (DesignableGuiControl control : this.controls.values()) {
/*  284 */       control.onTick();
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
/*      */   public void draw(Rectangle bounds, int mouseX, int mouseY) {
/*  296 */     draw(bounds, mouseX, mouseY, false, (LayoutPanelEditMode)null, -1);
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
/*      */   public void drawWidgets(Rectangle bounds, int mouseX, int mouseY, LayoutPanelEditMode mode, int selectedControlIndex) {
/*  310 */     draw(bounds, mouseX, mouseY, true, mode, selectedControlIndex);
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
/*      */   protected void draw(Rectangle bounds, int mouseX, int mouseY, boolean drawGrid, LayoutPanelEditMode mode, int selectedControlIndex) {
/*  326 */     int originalMouseY = mouseY;
/*      */     
/*  328 */     boolean editingGrid = (bsu.z()).m instanceof net.eq2online.macros.gui.designable.editor.GuiDialogBoxSetGridSize;
/*      */     
/*  330 */     int width = bounds.width;
/*  331 */     if (width > this.fixedWidthAllocation) {
/*  332 */       width -= this.fixedWidthAllocation;
/*      */     } else {
/*  334 */       width = 0;
/*      */     } 
/*  336 */     int cellWidth = Math.max(0, width / this.dynamicColumns - this.padding * 2);
/*  337 */     int cellHeight = bounds.height / this.rows - this.padding * 2;
/*  338 */     int widthRemainder = width - (cellWidth + this.padding * 2) * this.dynamicColumns;
/*  339 */     float totalHeight = (bounds.height - bounds.height % this.rows);
/*      */     
/*  341 */     GL.glPushMatrix();
/*      */ 
/*      */     
/*  344 */     if (totalHeight != bounds.height) {
/*      */       
/*  346 */       float scale = 1.0F / totalHeight / bounds.height;
/*  347 */       mouseY = (int)((mouseY - bounds.y) / scale + bounds.y);
/*  348 */       GL.glScalef(1.0F, scale, 1.0F);
/*  349 */       GL.glTranslatef(0.0F, (1.0F - scale) * bounds.y, 0.0F);
/*      */     } 
/*      */ 
/*      */     
/*  353 */     if (!editingGrid) drawGrid(bounds, mouseX, mouseY, drawGrid, editingGrid, cellWidth, cellHeight, widthRemainder);
/*      */ 
/*      */     
/*  356 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  358 */       if (mode != null) {
/*  359 */         control.drawWidget(this, calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, control), mouseX, mouseY, mode, (control.id == selectedControlIndex && (mode == LayoutPanelEditMode.EditAll || mode == LayoutPanelEditMode.EditButtonsOnly)), false); continue;
/*      */       } 
/*  361 */       control.draw(this, calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, control), mouseX, mouseY);
/*      */     } 
/*      */ 
/*      */     
/*  365 */     if (editingGrid) drawGrid(bounds, mouseX, mouseY, drawGrid, editingGrid, cellWidth, cellHeight, widthRemainder);
/*      */ 
/*      */     
/*  368 */     drawPlacementCursor(bounds, mouseX, originalMouseY);
/*      */     
/*  370 */     GL.glPopMatrix();
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
/*      */   protected void drawGrid(Rectangle bounds, int mouseX, int mouseY, boolean drawGrid, boolean editingGrid, int cellWidth, int cellHeight, int widthRemainder) {
/*  382 */     this.mouseOverColumn = -1;
/*      */     
/*  384 */     if (!editingGrid || this.editingColumn >= this.columns)
/*      */     {
/*  386 */       this.editingColumn = -1;
/*      */     }
/*      */     
/*  389 */     if (drawGrid || this.drawGrid) {
/*      */       
/*  391 */       int gridColour = editingGrid ? -1433892864 : (this.dotGrid ? -286379264 : -1719140352);
/*  392 */       int fixedColColour = -5614336;
/*  393 */       int dynamicColColour = -16733696;
/*  394 */       bty fontRenderer = AbstractionLayer.getFontRenderer();
/*      */       
/*  396 */       int xPos = bounds.x;
/*  397 */       for (int col = 0; col <= this.columns; col++) {
/*      */         
/*  399 */         if (this.dotGrid && !editingGrid) {
/*      */           
/*  401 */           int yPos = bounds.y;
/*  402 */           for (int row = 0; row <= this.rows; row++)
/*      */           {
/*  404 */             a(xPos, yPos, xPos + 1, yPos + 1, gridColour);
/*  405 */             yPos += cellHeight + this.padding * 2;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  410 */           drawLine(xPos, bounds.y, xPos, (bounds.y + (cellHeight + this.padding * 2) * this.rows), GuiScreenEx.guiScaleFactor, (this.editingColumn > -1 && (this.editingColumn == col || this.editingColumn + 1 == col)) ? -1 : gridColour);
/*      */         } 
/*      */         
/*  413 */         int lastXPos = xPos;
/*  414 */         xPos += ((this.columnMetrics[col] > 0) ? this.columnMetrics[col] : (cellWidth + this.padding * 2)) + ((col == this.columns - 1) ? widthRemainder : 0);
/*      */ 
/*      */         
/*  417 */         if (col < this.columns && editingGrid) {
/*      */ 
/*      */           
/*  420 */           if (mouseX >= lastXPos && mouseX < xPos)
/*      */           {
/*  422 */             this.mouseOverColumn = col;
/*      */           }
/*      */ 
/*      */           
/*  426 */           int arrowColour = (this.editingColumn == col) ? -1 : ((this.columnMetrics[col] > 0) ? fixedColColour : dynamicColColour);
/*  427 */           drawDoubleEndedArrowH(lastXPos, xPos, (bounds.y + 5), (2 * GuiScreenEx.guiScaleFactor), 6.0F, arrowColour);
/*      */ 
/*      */           
/*  430 */           String colText = (this.columnMetrics[col] > 0) ? String.valueOf(this.columnMetrics[col]) : "Auto";
/*  431 */           int xMid = lastXPos + (xPos - lastXPos) / 2;
/*  432 */           int textWidth = fontRenderer.a(colText) / 2;
/*  433 */           c(fontRenderer, colText, xMid - textWidth, bounds.y + 7, arrowColour);
/*      */         } 
/*      */       } 
/*      */       
/*  437 */       if (!this.dotGrid || editingGrid) {
/*      */         
/*  439 */         int yPos = bounds.y;
/*  440 */         for (int row = 0; row <= this.rows; row++) {
/*      */           
/*  442 */           drawLine(bounds.x, yPos, (bounds.x + bounds.width), yPos, GuiScreenEx.guiScaleFactor, gridColour);
/*  443 */           yPos += cellHeight + this.padding * 2;
/*      */         } 
/*      */       } 
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
/*      */   
/*      */   protected int scaleMouseY(Rectangle bounds, int mouseY) {
/*  459 */     return scaleMouseY(bounds, mouseY, bounds.y);
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
/*      */   protected int scaleMouseY(Rectangle bounds, int mouseY, int top) {
/*  474 */     float totalHeight = (bounds.height - bounds.height % this.rows);
/*  475 */     if (totalHeight != bounds.height) {
/*      */       
/*  477 */       float scale = 1.0F / totalHeight / bounds.height;
/*  478 */       mouseY = (int)((mouseY - top) / scale + top);
/*      */     } 
/*      */     
/*  481 */     return mouseY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawPlacementCursor(Rectangle bounds, int mouseX, int mouseY) {
/*  492 */     if (this.placingControlType != null) {
/*      */       
/*  494 */       Point pos = getRowAndColumnAt(bounds, mouseX, mouseY);
/*  495 */       Rectangle boundingBox = calcBoundingBox(bounds, pos.x, pos.y, 1, 1);
/*      */       
/*  497 */       if (!isCellOccupied(pos)) {
/*      */         
/*  499 */         drawRectOutline(boundingBox, -256, 2);
/*      */         
/*  501 */         AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/*  502 */         drawTexturedModalIcon(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, 118, 48, 128, 58, 0.0078125F);
/*      */       }
/*  504 */       else if (pos.x > -1 && pos.y > -1) {
/*      */         
/*  506 */         drawRectOutline(boundingBox, -65536, 2);
/*      */       } 
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
/*      */   
/*      */   public Point getRowAndColumnAt(Rectangle bounds, int mouseX, int mouseY) {
/*  521 */     return new Point(getColumnAt(bounds, mouseX, -1), getRowAt(bounds, mouseY, -1));
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
/*      */   public Point getLogicalRowAndColumnAt(Rectangle bounds, int mouseX, int mouseY) {
/*  534 */     return new Point(getColumnAt(bounds, mouseX, this.columns), getRowAt(bounds, mouseY, this.rows));
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
/*      */   public int getRowAt(Rectangle bounds, int mouseY, int outOfBoundsRow) {
/*  546 */     if (mouseY < bounds.y) return -1; 
/*  547 */     if (mouseY >= bounds.y + bounds.height) return outOfBoundsRow; 
/*  548 */     return (int)((mouseY - bounds.y) / bounds.height * this.rows);
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
/*      */   public int getColumnAt(Rectangle bounds, int mouseX, int outOfBoundsCol) {
/*  560 */     if (mouseX < bounds.x) return -1; 
/*  561 */     if (mouseX >= bounds.x + bounds.width) return outOfBoundsCol; 
/*  562 */     mouseX -= bounds.x;
/*      */     
/*  564 */     int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*      */     
/*  566 */     for (int i = 0; i <= this.columns; i++) {
/*  567 */       if (mouseX <= dynamicWidth * this.dynamicWidthTo[i] + this.fixedWidthTo[i]) return i - 1; 
/*      */     } 
/*  569 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public Point getNearestGridPoint(Rectangle bounds, int mouseX, int mouseY) {
/*  574 */     int yGrid = getRowAt(bounds, mouseY, -1);
/*  575 */     int yPos = (yGrid > -1) ? (bounds.y + bounds.height / this.rows * yGrid) : bounds.y;
/*      */     
/*  577 */     int xPos = bounds.x;
/*      */     
/*  579 */     if (mouseX > bounds.x && mouseX <= bounds.x + bounds.width) {
/*      */       
/*  581 */       mouseX -= bounds.x;
/*      */       
/*  583 */       int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*      */       
/*  585 */       for (int i = 1; i <= this.columns; i++) {
/*      */         
/*  587 */         if (mouseX <= dynamicWidth * this.dynamicWidthTo[i] + this.fixedWidthTo[i]) {
/*      */           
/*  589 */           xPos = bounds.x + dynamicWidth * this.dynamicWidthTo[i - 1] + this.fixedWidthTo[i - 1];
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  595 */     return new Point(xPos, yPos);
/*      */   }
/*      */ 
/*      */   
/*      */   public Point getDragPoint(Rectangle bounds, DesignableGuiControl control) {
/*  600 */     float yGrid = control.yPosition + 0.5F;
/*  601 */     int yPos = (int)((yGrid > -1.0F) ? (bounds.y + (bounds.height / this.rows) * yGrid) : bounds.y);
/*      */     
/*  603 */     int xPos = bounds.x;
/*      */     
/*  605 */     int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*  606 */     int columnWidth = (this.columnMetrics[control.xPosition] == 0) ? dynamicWidth : this.columnMetrics[control.xPosition];
/*  607 */     xPos = bounds.x + dynamicWidth * this.dynamicWidthTo[control.xPosition] + this.fixedWidthTo[control.xPosition] + columnWidth / 2;
/*      */     
/*  609 */     return new Point(xPos, yPos);
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
/*      */   public boolean isCellOccupied(Point pos) {
/*  621 */     return isCellOccupied(pos.y, pos.x, (DesignableGuiControl)null);
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
/*      */   public boolean isCellOccupied(Point pos, DesignableGuiControl sourceControl) {
/*  633 */     return isCellOccupied(pos.y, pos.x, sourceControl);
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
/*      */   public boolean isCellOccupied(int row, int col, DesignableGuiControl sourceControl) {
/*  647 */     if (row < 0 || col < 0 || row >= this.rows || col >= this.columns) return true;
/*      */     
/*  649 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  651 */       if (sourceControl != control && control.cccupies(row, col)) {
/*  652 */         return true;
/*      */       }
/*      */     } 
/*  655 */     return false;
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
/*      */   public DesignableGuiControl getControlAt(Rectangle bounds, int mouseX, int mouseY, int selectedControlIndex) {
/*  668 */     mouseY = scaleMouseY(bounds, mouseY);
/*      */     
/*  670 */     int width = bounds.width;
/*  671 */     if (width > this.fixedWidthAllocation) {
/*  672 */       width -= this.fixedWidthAllocation;
/*      */     } else {
/*  674 */       width = 0;
/*      */     } 
/*  676 */     int cellWidth = Math.max(0, width / this.dynamicColumns - this.padding * 2);
/*  677 */     int cellHeight = bounds.height / this.rows - this.padding * 2;
/*  678 */     int widthRemainder = width - (cellWidth + this.padding * 2) * this.dynamicColumns;
/*      */     
/*  680 */     DesignableGuiControl topMost = null;
/*      */     
/*  682 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  684 */       Rectangle boundingBox = calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, control);
/*      */       
/*  686 */       if (control.mouseOver(boundingBox, mouseX, mouseY, (control.id == selectedControlIndex)) && (topMost == null || control.zIndex > topMost.zIndex)) {
/*  687 */         topMost = control;
/*      */       }
/*      */     } 
/*  690 */     return topMost;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getControlHandleAt(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/*  695 */     mouseY = scaleMouseY(bounds, mouseY);
/*      */     
/*  697 */     int width = bounds.width;
/*  698 */     if (width > this.fixedWidthAllocation) {
/*  699 */       width -= this.fixedWidthAllocation;
/*      */     } else {
/*  701 */       width = 0;
/*      */     } 
/*  703 */     int cellWidth = Math.max(0, width / this.dynamicColumns - this.padding * 2);
/*  704 */     int cellHeight = bounds.height / this.rows - this.padding * 2;
/*  705 */     int widthRemainder = width - (cellWidth + this.padding * 2) * this.dynamicColumns;
/*      */     
/*  707 */     Rectangle boundingBox = calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, control);
/*  708 */     return control.mouseOverHandle(boundingBox, mouseX, mouseY);
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
/*      */   public Rectangle calcBoundingBox(Rectangle bounds, DesignableGuiControl control) {
/*  720 */     return calcBoundingBox(bounds, control.xPosition, control.yPosition, control.rowSpan, control.colSpan);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle calcBoundingBox(Rectangle bounds, int xPosition, int yPosition, int rowSpan, int colSpan) {
/*  725 */     int width = (bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0;
/*      */     
/*  727 */     int cellWidth = Math.max(0, width / this.dynamicColumns - this.padding * 2);
/*  728 */     int cellHeight = bounds.height / this.rows - this.padding * 2;
/*  729 */     int widthRemainder = width - (cellWidth + this.padding * 2) * this.dynamicColumns;
/*      */     
/*  731 */     return calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, xPosition, yPosition, rowSpan, colSpan);
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
/*      */   protected Rectangle calcBoundingBox(Rectangle bounds, int cellWidth, int cellHeight, int widthRemainder, DesignableGuiControl control) {
/*  746 */     return calcBoundingBox(bounds, cellWidth, cellHeight, widthRemainder, control.xPosition, control.yPosition, control.rowSpan, control.colSpan);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rectangle calcBoundingBox(Rectangle bounds, int cellWidth, int cellHeight, int widthRemainder, int xPosition, int yPosition, int rowSpan, int colSpan) {
/*  751 */     int cellPadding = this.padding * 2;
/*      */ 
/*      */     
/*  754 */     colSpan = Math.max(Math.min(colSpan, this.columns - xPosition), 1);
/*  755 */     rowSpan = Math.max(Math.min(rowSpan, this.rows - yPosition), 1);
/*      */ 
/*      */     
/*  758 */     int width = colSpan * cellWidth + (colSpan - 1) * cellPadding + ((xPosition + colSpan >= this.columns) ? widthRemainder : 0);
/*  759 */     int height = rowSpan * cellHeight + (rowSpan - 1) * cellPadding;
/*      */ 
/*      */     
/*  762 */     int x = bounds.x + this.padding;
/*  763 */     int y = bounds.y + this.padding + yPosition * cellHeight + yPosition * cellPadding;
/*  764 */     for (int col = 0; col < xPosition + colSpan; col++) {
/*      */       
/*  766 */       if (col < xPosition) x += (this.columnMetrics[col] > 0) ? this.columnMetrics[col] : (cellWidth + cellPadding); 
/*  767 */       if (col >= xPosition && this.columnMetrics[col] > 0) width += this.columnMetrics[col] - cellPadding + -cellWidth;
/*      */     
/*      */     } 
/*  770 */     return new Rectangle(x, y, width, height);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setWidgetPosition(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/*  775 */     int scaledDragOffset = scaleMouseY(bounds, control.dragOffsetY, 0);
/*  776 */     Point pos = getRowAndColumnAt(bounds, mouseX - control.dragOffsetX + this.padding, mouseY - scaledDragOffset + this.padding);
/*  777 */     if (pos.x < 0 || pos.y < 0) return false; 
/*  778 */     return control.setWidgetPosition(this, pos.x, pos.y);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setControlSpan(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control, int handle) {
/*  783 */     Point pos = getLogicalRowAndColumnAt(bounds, mouseX, mouseY);
/*      */     
/*  785 */     if (handle == 2) {
/*      */       
/*  787 */       if (pos.x < 0 || pos.y < 0)
/*  788 */         return;  int newColspan = Math.max(1, pos.x - control.xPosition);
/*  789 */       if (control.canPlaceAt(control.yPosition, control.xPosition, control.rowSpan, newColspan, this)) {
/*  790 */         control.colSpan = newColspan;
/*      */       }
/*  792 */     } else if (handle == 3) {
/*      */       
/*  794 */       int newColspan = Math.max(0, control.colSpan - pos.x - control.xPosition);
/*  795 */       if (control.canPlaceAt(control.yPosition, pos.x, control.rowSpan, newColspan, this))
/*      */       {
/*  797 */         control.xPosition = pos.x;
/*  798 */         control.colSpan = newColspan;
/*      */       }
/*      */     
/*  801 */     } else if (handle == 1) {
/*      */       
/*  803 */       if (pos.x < 0 || pos.y < 0)
/*  804 */         return;  int newRowspan = Math.max(1, pos.y - control.yPosition);
/*  805 */       if (control.canPlaceAt(control.yPosition, control.xPosition, newRowspan, control.colSpan, this)) {
/*  806 */         control.rowSpan = newRowspan;
/*      */       }
/*  808 */     } else if (handle == 0) {
/*      */       
/*  810 */       int newRowspan = Math.max(0, control.rowSpan - pos.y - control.yPosition);
/*  811 */       if (control.canPlaceAt(pos.y, control.xPosition, newRowspan, control.colSpan, this)) {
/*      */         
/*  813 */         control.yPosition = pos.y;
/*  814 */         control.rowSpan = newRowspan;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRows() {
/*  824 */     return this.rows;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinRows() {
/*  829 */     int minRows = 1;
/*      */     
/*  831 */     for (DesignableGuiControl control : this.controls.values()) {
/*  832 */       minRows = Math.max(minRows, control.yPosition + control.rowSpan);
/*      */     }
/*  834 */     return minRows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRows(int rows) {
/*  842 */     this.rows = Math.min(Math.max(getMinRows(), rows), 255);
/*  843 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addRow() {
/*  848 */     if (this.rows < 32) setRows(this.rows + 1);
/*      */   
/*      */   }
/*      */   
/*      */   public void removeRow() {
/*  853 */     setRows(this.rows - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumns() {
/*  861 */     return this.columns;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinColumns() {
/*  866 */     int minColumns = 1;
/*      */     
/*  868 */     for (DesignableGuiControl control : this.controls.values()) {
/*  869 */       minColumns = Math.max(minColumns, control.xPosition + control.colSpan);
/*      */     }
/*  871 */     return minColumns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumns(int columns) {
/*  879 */     this.columns = Math.min(Math.max(getMinColumns(), columns), 256);
/*  880 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setColumnWidth(int column, int width) {
/*  885 */     if (column > -1 && column < this.columns) {
/*      */       
/*  887 */       this.columnMetrics[column] = width;
/*  888 */       updateColumnMetrics();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addColumn() {
/*  894 */     if (this.columns < 32) setColumns(this.columns + 1);
/*      */   
/*      */   }
/*      */   
/*      */   public void removeColumn() {
/*  899 */     setColumns(this.columns - 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void selectColumn() {
/*  904 */     this.editingColumn = this.mouseOverColumn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSelectedColumn() {
/*  909 */     return this.editingColumn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSelectedColumnWidth() {
/*  914 */     return (this.editingColumn > -1) ? this.columnMetrics[this.editingColumn] : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSelectedColumnWidth(int width) {
/*  919 */     setColumnWidth(this.editingColumn, width);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSelectedColumnWidthText() {
/*  924 */     if (this.editingColumn < 0) return "";
/*      */     
/*  926 */     int columnWidth = getSelectedColumnWidth();
/*  927 */     return (columnWidth > 0) ? String.valueOf(columnWidth) : "§8Auto";
/*      */   }
/*      */ 
/*      */   
/*      */   public void beginPlacingControl(String controlType) {
/*  932 */     this.placingControlType = controlType;
/*      */     
/*  934 */     if (controlType != null) {
/*      */       
/*  936 */       boolean freeCell = false;
/*      */       
/*  938 */       for (int row = 0; row < this.rows && !freeCell; row++) {
/*      */         
/*  940 */         for (int col = 0; col < this.columns && !freeCell; col++) {
/*      */           
/*  942 */           if (!isCellOccupied(row, col, (DesignableGuiControl)null)) {
/*  943 */             freeCell = true;
/*      */           }
/*      */         } 
/*      */       } 
/*  947 */       if (!freeCell) {
/*      */         
/*  949 */         Log.info("No free cells");
/*  950 */         this.placingControlType = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPlacingControl() {
/*  957 */     return (this.placingControlType != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiControl placeControl(Rectangle bounds, int mouseX, int mouseY) {
/*  962 */     if (this.placingControlType != null) {
/*      */       
/*  964 */       String controlType = this.placingControlType;
/*  965 */       this.placingControlType = null;
/*      */       
/*  967 */       Point pos = getRowAndColumnAt(bounds, mouseX, mouseY);
/*      */       
/*  969 */       return addControl(controlType, pos.y, pos.x);
/*      */     } 
/*      */     
/*  972 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiControl addControl(String controlType, int row, int column) {
/*  977 */     if (!isCellOccupied(row, column, (DesignableGuiControl)null)) {
/*      */       
/*  979 */       DesignableGuiControl control = DesignableGuiControl.createControl(controlType);
/*      */       
/*  981 */       control.setName(control.getDefaultControlName());
/*  982 */       control.xPosition = column;
/*  983 */       control.yPosition = row;
/*  984 */       control.update();
/*      */       
/*  986 */       this.controls.put(Integer.valueOf(control.id), control);
/*      */       
/*  988 */       return control;
/*      */     } 
/*      */     
/*  991 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeControl(int controlId) {
/*  996 */     this.controls.remove(Integer.valueOf(controlId));
/*      */     
/*  998 */     if (!LayoutManager.checkControlExistsInLayouts(controlId))
/*      */     {
/* 1000 */       DesignableGuiControl.removeControl(controlId);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
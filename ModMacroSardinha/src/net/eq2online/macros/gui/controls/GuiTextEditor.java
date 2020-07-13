/*      */ package net.eq2online.macros.gui.controls;
/*      */ 
/*      */ import bsu;
/*      */ import bxf;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*      */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*      */ import net.eq2online.macros.interfaces.IHighlighter;
/*      */ import net.eq2online.macros.rendering.FontRendererTextEditor;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import org.lwjgl.input.Keyboard;
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
/*      */ public class GuiTextEditor
/*      */   extends GuiControlEx
/*      */ {
/*      */   protected static final int CHARWIDTH = 7;
/*   40 */   protected static FontRendererTextEditor editorFontRenderer = new FontRendererTextEditor(ResourceLocations.FIXEDWIDTHFONT, 7);
/*      */ 
/*      */ 
/*      */   
/*      */   protected IHighlighter highlighter;
/*      */ 
/*      */ 
/*      */   
/*      */   protected GuiScrollBar vScrollBar;
/*      */ 
/*      */   
/*      */   protected GuiScrollBar hScrollBar;
/*      */ 
/*      */   
/*      */   protected File file;
/*      */ 
/*      */   
/*      */   protected boolean fileChanged = false;
/*      */ 
/*      */   
/*   60 */   protected ArrayList<String> text = new ArrayList<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   65 */   protected int rowHeight = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   70 */   protected int charWidth = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   protected int displayRows = 0, displayCols = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   protected int cursorRow = 0; protected int cursorCol = 0; protected boolean hasSelection = false;
/*      */   protected boolean useSelection = false;
/*      */   protected boolean dragging = false;
/*      */   protected int selStartRow;
/*      */   protected int selStartCol;
/*      */   protected int selEndRow;
/*      */   protected int selEndCol;
/*      */   protected int selRow;
/*      */   protected int selCol;
/*      */   protected boolean draggingSelection;
/*      */   public boolean highlight = true;
/*      */   public boolean showCommandHelp = true;
/*   92 */   protected IDocumentationEntry lastDocumentationEntry = null;
/*      */   
/*   94 */   protected int documentationDelayCounter = 0;
/*      */   
/*   96 */   protected static Pattern scriptActionBeforeCursor = Pattern.compile("([a-z\\_]+)\\([^\\)]*$", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ScriptContext context;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiTextEditor(bsu minecraft, IHighlighter highlighter, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, boolean highlight, boolean showCommandHelp, ScriptContext context) {
/*  112 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, null);
/*      */     
/*  114 */     this.highlighter = highlighter;
/*  115 */     this.context = context;
/*      */     
/*  117 */     this.charWidth = editorFontRenderer.getFixedCharWidth();
/*      */     
/*  119 */     this.displayRows = (getHeight() - 20) / this.rowHeight;
/*  120 */     this.displayCols = (getWidth() - 20) / this.charWidth;
/*      */     
/*  122 */     this.vScrollBar = new GuiScrollBar(minecraft, controlId, getXPosition() + getWidth() - 20, getYPosition() - 1, 20, controlHeight + 2, 0, 0, GuiScrollBar.ScrollBarOrientation.Vertical);
/*  123 */     this.hScrollBar = new GuiScrollBar(minecraft, controlId, getXPosition() - 2, getYPosition() + getHeight() - 19, controlWidth - 18, 20, 0, 0, GuiScrollBar.ScrollBarOrientation.Horizontal);
/*      */     
/*  125 */     this.highlight = highlight;
/*  126 */     this.showCommandHelp = showCommandHelp;
/*      */     
/*  128 */     update(false);
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
/*      */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/*  141 */     setXPosition(left);
/*  142 */     setYPosition(top);
/*  143 */     a(controlWidth);
/*  144 */     getHeight(controlHeight);
/*      */     
/*  146 */     this.displayRows = (getHeight() - 20) / this.rowHeight;
/*  147 */     this.displayCols = (getWidth() - 20) / this.charWidth;
/*      */     
/*  149 */     this.vScrollBar.setSizeAndPosition(getXPosition() + getWidth() - 20, getYPosition() - 1, 20, controlHeight + 2);
/*  150 */     this.hScrollBar.setSizeAndPosition(getXPosition() - 2, getYPosition() + getHeight() - 19, controlWidth - 18, 20);
/*      */     
/*  152 */     update(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isChanged() {
/*  157 */     return this.fileChanged;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void update(boolean canWrap) {
/*  166 */     this.updateCounter = 0;
/*  167 */     int totalLines = this.text.size();
/*      */     
/*  169 */     if (totalLines == 0) {
/*      */       
/*  171 */       this.text.add("");
/*  172 */       totalLines = 1;
/*  173 */       this.cursorRow = 0;
/*  174 */       this.cursorCol = 0;
/*      */     } 
/*      */ 
/*      */     
/*  178 */     if (this.cursorRow < 0) { this.cursorRow = 0; }
/*  179 */     else if (this.cursorRow >= totalLines) { this.cursorRow = totalLines - 1; }
/*      */     
/*  181 */     if (canWrap) {
/*      */       
/*  183 */       if (this.cursorCol == ((String)this.text.get(this.cursorRow)).length() + 1) {
/*      */         
/*  185 */         this.cursorRow++;
/*  186 */         this.cursorCol = 0;
/*  187 */         update(false);
/*      */         return;
/*      */       } 
/*  190 */       if (this.cursorCol == -1) {
/*      */         
/*  192 */         this.cursorRow--;
/*  193 */         this.cursorCol = Integer.MAX_VALUE;
/*  194 */         update(false);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  200 */     if (this.cursorCol < 0) { this.cursorCol = 0; }
/*  201 */     else if (this.cursorCol > ((String)this.text.get(this.cursorRow)).length()) { this.cursorCol = ((String)this.text.get(this.cursorRow)).length(); }
/*      */     
/*  203 */     if (this.cursorRow >= this.vScrollBar.getValue() + this.displayRows) {
/*  204 */       this.vScrollBar.setValue(this.cursorRow - this.displayRows + 1);
/*      */     }
/*  206 */     if (this.cursorRow < this.vScrollBar.getValue()) {
/*  207 */       this.vScrollBar.setValue(this.cursorRow);
/*      */     }
/*  209 */     if (this.cursorCol >= this.hScrollBar.getValue() + this.displayCols) {
/*  210 */       this.hScrollBar.setValue(this.cursorCol - this.displayCols + 1);
/*      */     }
/*  212 */     if (this.cursorCol < this.hScrollBar.getValue()) {
/*  213 */       this.hScrollBar.setValue(this.cursorCol);
/*      */     }
/*  215 */     int maxLineLength = 0;
/*      */     
/*  217 */     for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++) {
/*  218 */       maxLineLength = Math.max(maxLineLength, ((String)this.text.get(lineNumber)).length());
/*      */     }
/*  220 */     this.vScrollBar.setMax(Math.max(totalLines - this.displayRows, 0));
/*  221 */     this.hScrollBar.setMax(Math.max(maxLineLength - this.displayCols + 1, 0));
/*      */     
/*  223 */     if (this.selStartCol == this.selEndCol && this.selStartRow == this.selEndRow)
/*      */     {
/*  225 */       this.useSelection = false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  234 */     this.file = null;
/*  235 */     this.fileChanged = false;
/*  236 */     this.text.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean load(File textFile) {
/*  247 */     this.file = textFile;
/*  248 */     this.fileChanged = false;
/*      */     
/*  250 */     this.text.clear();
/*      */     
/*  252 */     if (this.file != null && this.file.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  256 */         BufferedReader bufferedreader = new BufferedReader(new FileReader(this.file));
/*      */         
/*  258 */         for (String fileLine = ""; (fileLine = bufferedreader.readLine()) != null; ) {
/*      */           
/*  260 */           while (fileLine.indexOf('\t') > -1) {
/*      */             
/*  262 */             String head = fileLine.substring(0, fileLine.indexOf('\t'));
/*  263 */             String tail = fileLine.substring(fileLine.indexOf('\t') + 1);
/*  264 */             fileLine = head + getTabCharsForColumn(head.length()) + tail;
/*      */           } 
/*      */           
/*  267 */           this.text.add(fileLine);
/*      */         } 
/*      */         
/*  270 */         bufferedreader.close();
/*      */       }
/*  272 */       catch (Exception e) {
/*      */         
/*  274 */         this.text.clear();
/*  275 */         update(false);
/*  276 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*  280 */     update(false);
/*  281 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setText(String newText) {
/*  286 */     this.text.clear();
/*  287 */     this.fileChanged = false;
/*      */     
/*  289 */     if (newText != null) {
/*      */ 
/*      */       
/*  292 */       String[] lines = newText.split("\\r?\\n");
/*      */       
/*  294 */       for (String line : lines) {
/*      */         
/*  296 */         while (line.indexOf('\t') > -1) {
/*      */           
/*  298 */           String head = line.substring(0, line.indexOf('\t'));
/*  299 */           String tail = line.substring(line.indexOf('\t') + 1);
/*  300 */           line = head + getTabCharsForColumn(head.length()) + tail;
/*      */         } 
/*      */         
/*  303 */         this.text.add(line);
/*      */       } 
/*      */     } 
/*      */     
/*  307 */     update(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  318 */     StringBuilder textBuilder = new StringBuilder();
/*      */     
/*  320 */     for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++) {
/*  321 */       textBuilder.append(this.text.get(lineNumber)).append("\n");
/*      */     }
/*  323 */     this.fileChanged = false;
/*  324 */     return textBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean save() {
/*  334 */     this.fileChanged = false;
/*      */     
/*  336 */     if (this.file != null)
/*      */     {
/*  338 */       return save(this.file);
/*      */     }
/*      */     
/*  341 */     return false;
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
/*      */   public boolean save(File textFile) {
/*      */     try {
/*  354 */       PrintWriter printwriter = new PrintWriter(new FileWriter(textFile));
/*      */       
/*  356 */       for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++) {
/*  357 */         printwriter.println(this.text.get(lineNumber));
/*      */       }
/*  359 */       printwriter.close();
/*  360 */       this.fileChanged = false;
/*  361 */       return true;
/*      */     }
/*  363 */     catch (Exception exception) {
/*      */       
/*  365 */       Log.info("Failed to save file: {0}", new Object[] { textFile.getName() });
/*  366 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void alterSelection() {
/*  372 */     if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
/*      */       
/*  374 */       alterSelection(this.cursorRow, this.cursorCol);
/*      */     }
/*      */     else {
/*      */       
/*  378 */       clearSelection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void alterSelection(int row, int col) {
/*  384 */     if (this.hasSelection) {
/*      */       
/*  386 */       if (row < this.selRow || (row == this.selRow && col < this.selCol))
/*      */       {
/*  388 */         this.useSelection = true;
/*  389 */         dragSelectionStart(row, col);
/*      */       }
/*  391 */       else if (row > this.selRow || (row == this.selRow && col >= this.selCol))
/*      */       {
/*  393 */         this.useSelection = true;
/*  394 */         dragSelectionEnd(row, col);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  399 */       setSelectionPoint(row, col);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void clearSelection() {
/*  405 */     this.hasSelection = false;
/*  406 */     this.useSelection = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setSelectionPoint(int row, int col) {
/*  411 */     this.hasSelection = true;
/*  412 */     this.selRow = row;
/*  413 */     this.selCol = col;
/*  414 */     setSelection(row, col, row, col);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setSelection(int startRow, int startCol, int endRow, int endCol) {
/*  419 */     this.selStartRow = startRow;
/*  420 */     this.selStartCol = startCol;
/*  421 */     this.selEndRow = endRow;
/*  422 */     this.selEndCol = endCol;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dragSelectionStart(int row, int col) {
/*  427 */     this.selStartRow = row;
/*  428 */     this.selStartCol = col;
/*  429 */     this.selEndRow = this.selRow;
/*  430 */     this.selEndCol = this.selCol;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dragSelectionEnd(int row, int col) {
/*  435 */     this.selStartRow = this.selRow;
/*  436 */     this.selStartCol = this.selCol;
/*  437 */     this.selEndRow = row;
/*  438 */     this.selEndCol = col;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setSelectionStart(int row, int col) {
/*  443 */     this.selStartRow = row;
/*  444 */     this.selStartCol = col;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setSelectionEnd(int row, int col) {
/*  449 */     this.selEndRow = row;
/*  450 */     this.selEndCol = col;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void deleteSelection() {
/*  455 */     if (!this.useSelection)
/*      */       return; 
/*  457 */     int startRow = this.selStartRow;
/*  458 */     int startCol = this.selStartCol;
/*      */     
/*  460 */     if (startRow < this.selEndRow && startCol > 0) {
/*      */       
/*  462 */       this.text.set(startRow, ((String)this.text.get(startRow)).substring(0, startCol));
/*  463 */       startCol = 0;
/*  464 */       startRow++;
/*      */     } 
/*      */ 
/*      */     
/*  468 */     while (startRow < this.selEndRow) {
/*      */       
/*  470 */       this.text.remove(startRow);
/*  471 */       this.selEndRow--;
/*      */     } 
/*      */     
/*  474 */     String rowText = this.text.get(this.selEndRow);
/*      */     
/*  476 */     if (this.selEndCol > -1)
/*      */     {
/*  478 */       if (startRow > this.selStartRow) {
/*      */         
/*  480 */         this.text.set(this.selStartRow, (String)this.text.get(this.selStartRow) + rowText.substring(this.selEndCol));
/*  481 */         this.text.remove(this.selEndRow);
/*      */       }
/*      */       else {
/*      */         
/*  485 */         String prepend = (startCol > 0) ? rowText.substring(0, startCol) : "";
/*  486 */         this.text.set(this.selEndRow, prepend + rowText.substring(this.selEndCol));
/*      */       } 
/*      */     }
/*      */     
/*  490 */     moveCursorTo(this.selStartRow, this.selStartCol);
/*  491 */     update(false);
/*  492 */     clearSelection();
/*      */   }
/*      */   
/*      */   protected void indentSelection(boolean shift) {
/*      */     int i, j, k;
/*  497 */     if (!this.useSelection)
/*      */       return; 
/*  499 */     boolean shiftedTop = false;
/*  500 */     boolean shiftedBottom = false;
/*  501 */     boolean shiftedCurrent = false;
/*      */     
/*  503 */     for (int rowIndex = this.selStartRow; rowIndex <= this.selEndRow; rowIndex++) {
/*      */       
/*  505 */       String rowText = this.text.get(rowIndex);
/*      */       
/*  507 */       if (shift) {
/*      */         
/*  509 */         if (rowText.startsWith("    "))
/*      */         {
/*  511 */           i = shiftedTop | ((rowIndex == this.selStartRow) ? 1 : 0);
/*  512 */           j = shiftedBottom | ((rowIndex == this.selEndRow) ? 1 : 0);
/*  513 */           k = shiftedCurrent | ((rowIndex == this.cursorRow) ? 1 : 0);
/*  514 */           this.text.set(rowIndex, rowText.substring(4));
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  519 */       else if (rowIndex < this.selEndRow || this.selEndCol > 0) {
/*  520 */         this.text.set(rowIndex, "    " + rowText);
/*      */       } 
/*      */     } 
/*      */     
/*  524 */     if (shift) {
/*      */       
/*  526 */       if (k != 0) this.cursorCol -= 4; 
/*  527 */       if (i != 0) this.selStartCol -= 4; 
/*  528 */       if (j != 0) this.selEndCol -= 4;
/*      */       
/*  530 */       if (this.cursorCol < 0) this.cursorCol = 0; 
/*  531 */       if (this.selStartCol < 0) this.selStartCol = 0; 
/*  532 */       if (this.selEndCol < 0) this.selEndCol = 0;
/*      */     
/*      */     } else {
/*      */       
/*  536 */       this.cursorCol += 4;
/*  537 */       this.selStartCol += 4;
/*  538 */       if (this.selEndCol > 0) this.selEndCol += 4;
/*      */     
/*      */     } 
/*  541 */     update(false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void copySelection() {
/*  546 */     if (!this.useSelection)
/*      */       return; 
/*  548 */     bxf.e(getSelectedText());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void cutSelection() {
/*  553 */     if (!this.useSelection)
/*      */       return; 
/*  555 */     bxf.e(getSelectedText());
/*  556 */     deleteSelection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void paste() {
/*  561 */     String pasteText = bxf.o();
/*      */     
/*  563 */     if (pasteText.length() > 0) {
/*      */       
/*  565 */       deleteSelection();
/*      */       
/*  567 */       String currentLine = this.text.get(this.cursorRow);
/*  568 */       String tail = currentLine.substring(this.cursorCol);
/*  569 */       String head = currentLine.substring(0, this.cursorCol);
/*  570 */       this.text.remove(this.cursorRow);
/*      */       
/*  572 */       if (pasteText.endsWith("\n")) {
/*  573 */         pasteText = pasteText + "§";
/*      */       }
/*  575 */       String[] pasteLines = pasteText.split("\\r?\\n");
/*      */       
/*  577 */       if (pasteText.endsWith("\n§")) {
/*  578 */         pasteLines[pasteLines.length - 1] = "";
/*      */       }
/*  580 */       pasteLines[0] = head + pasteLines[0];
/*  581 */       pasteLines[pasteLines.length - 1] = pasteLines[pasteLines.length - 1] + tail;
/*      */       
/*  583 */       for (String pasteLine : pasteLines) {
/*      */         
/*  585 */         this.text.add(this.cursorRow, tabsToSpaces(pasteLine));
/*  586 */         this.cursorRow++;
/*      */       } 
/*      */       
/*  589 */       this.cursorRow--;
/*  590 */       this.cursorCol = ((String)this.text.get(this.cursorRow)).length() - tail.length();
/*  591 */       update(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSelectedText() {
/*  597 */     String selectedText = "";
/*      */     
/*  599 */     if (this.selStartRow == this.selEndRow) {
/*      */       
/*  601 */       selectedText = selectedText + ((String)this.text.get(this.selStartRow)).substring(this.selStartCol, this.selEndCol);
/*      */     }
/*      */     else {
/*      */       
/*  605 */       selectedText = selectedText + ((String)this.text.get(this.selStartRow)).substring(this.selStartCol) + "\n";
/*      */       
/*  607 */       for (int rowIndex = this.selStartRow + 1; rowIndex < this.selEndRow; rowIndex++) {
/*  608 */         selectedText = selectedText + (String)this.text.get(rowIndex) + "\n";
/*      */       }
/*  610 */       if (this.selEndCol > -1) {
/*  611 */         selectedText = selectedText + ((String)this.text.get(this.selEndRow)).substring(0, this.selEndCol);
/*      */       }
/*      */     } 
/*  614 */     return selectedText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveCursor(int deltaRows, int deltaColumns, boolean updateSelection) {
/*  625 */     if (updateSelection) { alterSelection(); } else { clearSelection(); }
/*  626 */      this.cursorRow += deltaRows;
/*  627 */     this.cursorCol += deltaColumns;
/*  628 */     update(true);
/*  629 */     if (updateSelection) alterSelection();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveCursorTo(int row, int column) {
/*  640 */     alterSelection();
/*  641 */     if (row > -1) this.cursorRow = row; 
/*  642 */     if (column > -1) this.cursorCol = column; 
/*  643 */     update(false);
/*  644 */     alterSelection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scroll(int deltaRows) {
/*  654 */     this.vScrollBar.setValue(this.vScrollBar.getValue() + deltaRows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void keyTyped(char keyChar, int keyCode) {
/*  665 */     if (!handleCursorKey(keyCode))
/*      */     {
/*  667 */       handleCharacterKey(keyChar, keyCode);
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
/*      */   protected boolean handleCursorKey(int keyCode) {
/*  679 */     switch (keyCode) {
/*      */       case 1:
/*  681 */         return true;
/*  682 */       case 200: moveCursor(-1, 0, true); return true;
/*  683 */       case 208: moveCursor(1, 0, true); return true;
/*  684 */       case 203: moveCursor(0, -1, true); return true;
/*  685 */       case 205: moveCursor(0, 1, true); return true;
/*  686 */       case 199: moveCursorTo(-1, 0); return true;
/*  687 */       case 207: moveCursorTo(-1, ((String)this.text.get(this.cursorRow)).length()); return true;
/*  688 */       case 201: scroll(-1 * this.displayRows); moveCursor(-1 * this.displayRows, 0, true); return true;
/*  689 */       case 209: scroll(this.displayRows); moveCursor(this.displayRows, 0, true); return true;
/*      */     } 
/*      */     
/*  692 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleCharacterKey(char keyChar, int keyCode) {
/*  703 */     if (keyChar > '\000' || keyCode == 211) {
/*      */       
/*  705 */       this.fileChanged = true;
/*      */       
/*  707 */       if (this.selStartCol == this.selEndCol && this.selStartRow == this.selEndRow)
/*      */       {
/*  709 */         this.useSelection = false;
/*      */       }
/*      */       
/*  712 */       if (keyChar == '\001') {
/*      */         
/*  714 */         moveCursorTo(this.text.size(), 2147483647);
/*  715 */         setSelection(0, 0, this.cursorRow, this.cursorCol);
/*  716 */         this.hasSelection = true;
/*  717 */         this.useSelection = true;
/*      */         return;
/*      */       } 
/*  720 */       if (keyChar == '\026') {
/*      */         
/*  722 */         paste();
/*      */         return;
/*      */       } 
/*  725 */       if (keyChar == '\013')
/*      */       {
/*  727 */         keyChar = '§';
/*      */       }
/*      */       
/*  730 */       if (this.useSelection) {
/*      */         
/*  732 */         if (keyChar == '\003') {
/*      */           
/*  734 */           copySelection();
/*      */           return;
/*      */         } 
/*  737 */         if (keyChar == '\030') {
/*      */           
/*  739 */           cutSelection();
/*      */           
/*      */           return;
/*      */         } 
/*  743 */         if (keyCode == 14 || keyCode == 211)
/*      */         {
/*  745 */           deleteSelection();
/*      */         }
/*  747 */         else if (keyCode == 15)
/*      */         {
/*  749 */           indentSelection((Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*      */         }
/*  751 */         else if (keyChar > '\037')
/*      */         {
/*  753 */           deleteSelection();
/*  754 */           handleCharacterKey(keyChar, keyCode);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  759 */         String currentLine = this.text.get(this.cursorRow);
/*  760 */         String tail = currentLine.substring(this.cursorCol);
/*  761 */         String head = currentLine.substring(0, this.cursorCol);
/*      */         
/*  763 */         if (keyCode == 28 || keyCode == 156) {
/*      */           
/*  765 */           this.text.set(this.cursorRow, head);
/*  766 */           this.text.add(this.cursorRow + 1, tail);
/*  767 */           moveCursorTo(this.cursorRow + 1, 0);
/*      */         }
/*  769 */         else if (keyCode == 14) {
/*      */           
/*  771 */           if (head.length() > 0)
/*      */           {
/*  773 */             this.text.set(this.cursorRow, head.substring(0, head.length() - 1) + tail);
/*  774 */             moveCursor(0, -1, false);
/*      */           }
/*  776 */           else if (this.cursorRow > 0)
/*      */           {
/*  778 */             String oldText = this.text.get(this.cursorRow - 1);
/*  779 */             this.text.set(this.cursorRow - 1, oldText + tail);
/*  780 */             this.text.remove(this.cursorRow);
/*  781 */             this.cursorCol = 0;
/*  782 */             moveCursor(-1, oldText.length(), false);
/*      */           }
/*      */         
/*  785 */         } else if (keyCode == 15) {
/*      */           
/*  787 */           String tabChars = getTabCharsForColumn(this.cursorCol);
/*  788 */           this.text.set(this.cursorRow, head + tabChars + tail);
/*  789 */           moveCursor(0, tabChars.length(), false);
/*      */         }
/*  791 */         else if (keyCode == 211) {
/*      */           
/*  793 */           if (tail.length() > 0)
/*      */           {
/*  795 */             this.text.set(this.cursorRow, head + tail.substring(1));
/*  796 */             update(false);
/*      */           }
/*  798 */           else if (this.cursorRow < this.text.size() - 1)
/*      */           {
/*  800 */             this.text.set(this.cursorRow, head + (String)this.text.get(this.cursorRow + 1));
/*  801 */             this.text.remove(this.cursorRow + 1);
/*      */           }
/*      */         
/*  804 */         } else if (keyChar > '\037') {
/*      */           
/*  806 */           this.text.set(this.cursorRow, head + keyChar + tail);
/*  807 */           moveCursor(0, 1, false);
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
/*      */   protected void b(bsu minecraft, int mouseX, int mouseY) {
/*  823 */     if (this.dragging) {
/*      */       
/*  825 */       this.cursorRow = (mouseY - getYPosition()) / this.rowHeight + this.vScrollBar.getValue();
/*  826 */       this.cursorCol = (mouseX - getXPosition()) / this.charWidth + this.hScrollBar.getValue();
/*  827 */       update(false);
/*  828 */       alterSelection(this.cursorRow, this.cursorCol);
/*      */     } 
/*      */     
/*  831 */     super.b(minecraft, mouseX, mouseY);
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
/*      */   public void a(int mouseX, int mouseY) {
/*  843 */     this.dragging = false;
/*      */     
/*  845 */     this.vScrollBar.a(mouseX, mouseY);
/*  846 */     this.hScrollBar.a(mouseX, mouseY);
/*      */     
/*  848 */     super.a(mouseX, mouseY);
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
/*      */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/*  862 */     if (super.c(minecraft, mouseX, mouseY)) {
/*      */       
/*  864 */       if (!this.vScrollBar.c(minecraft, mouseX, mouseY) && !this.hScrollBar.c(minecraft, mouseX, mouseY)) {
/*      */         
/*  866 */         this.cursorRow = (mouseY - getYPosition()) / this.rowHeight + this.vScrollBar.getValue();
/*  867 */         this.cursorCol = (mouseX - getXPosition()) / this.charWidth + this.hScrollBar.getValue();
/*  868 */         this.dragging = true;
/*  869 */         update(false);
/*  870 */         setSelectionPoint(this.cursorRow, this.cursorCol);
/*      */       } 
/*      */       
/*  873 */       return true;
/*      */     } 
/*      */     
/*  876 */     return false;
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
/*      */   public void drawControl(bsu minecraft, int mouseX, int mouseY) {
/*  889 */     b(minecraft, mouseX, mouseY);
/*      */ 
/*      */     
/*  892 */     AbstractionLayer.bindTexture(ResourceLocations.GUI_SLOT);
/*  893 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  894 */     setTexMapSize(128);
/*  895 */     drawTessellatedModalBorderRect(getXPosition() - 2, getYPosition() - 1, getXPosition() + getWidth() - 20, getYPosition() + getHeight() - 20, 0, 0, 18, 18, 8);
/*      */     
/*  897 */     int documentationOffset = 0;
/*  898 */     int documentationYPos = getYPosition();
/*  899 */     IDocumentationEntry documentation = null;
/*      */     
/*  901 */     int rowY = getYPosition() + 1;
/*      */ 
/*      */     
/*  904 */     for (int rowNumber = this.vScrollBar.getValue(); rowNumber < this.text.size() && rowNumber < this.vScrollBar.getValue() + this.displayRows; rowNumber++) {
/*      */       
/*  906 */       String rowText = this.text.get(rowNumber);
/*      */       
/*  908 */       if (this.hasSelection && this.selEndRow == rowNumber) {
/*      */         
/*  910 */         if (this.selEndCol > rowText.length()) this.selEndCol = rowText.length(); 
/*  911 */         rowText = rowText.substring(0, this.selEndCol) + '￾' + rowText.substring(this.selEndCol);
/*      */       } 
/*      */       
/*  914 */       if (this.hasSelection && this.selStartRow == rowNumber) {
/*      */         
/*  916 */         if (this.selStartCol > rowText.length()) this.selStartCol = rowText.length(); 
/*  917 */         rowText = (this.selStartCol > -1) ? (rowText.substring(0, this.selStartCol) + Character.MAX_VALUE + rowText.substring(this.selStartCol)) : (Character.MAX_VALUE + rowText);
/*      */       } 
/*      */       
/*  920 */       if (this.hasSelection && this.selStartRow < rowNumber && this.selEndRow >= rowNumber)
/*      */       {
/*  922 */         rowText = Character.MAX_VALUE + rowText;
/*      */       }
/*      */       
/*  925 */       if (this.showCommandHelp && rowNumber == this.cursorRow && this.cursorCol > 1) {
/*      */         
/*  927 */         String beforeCursor = rowText.substring(0, this.cursorCol);
/*  928 */         Matcher beforeCursorMatch = scriptActionBeforeCursor.matcher(beforeCursor);
/*      */         
/*  930 */         if (beforeCursorMatch.find()) {
/*      */           
/*  932 */           String actionName = beforeCursorMatch.group(1);
/*  933 */           if (this.context.getAction(actionName) != null) {
/*      */             
/*  935 */             documentation = this.context.getDocumentor().getDocumentation(actionName);
/*  936 */             documentationYPos = rowY + 10;
/*  937 */             documentationOffset = beforeCursorMatch.group().length() * 7;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  942 */       if (rowText.length() >= this.hScrollBar.getValue()) {
/*      */         
/*  944 */         int colour = -16777216;
/*      */         
/*  946 */         if (this.highlight && this.highlighter != null)
/*      */         {
/*  948 */           rowText = this.highlighter.highlight(rowText);
/*      */         }
/*      */         
/*  951 */         boolean isComment = (this.highlight && rowText.trim().startsWith("//"));
/*  952 */         if (this.highlight && isComment) colour = -16760832; 
/*  953 */         editorFontRenderer.renderFixedWidthString(rowText, getXPosition(), rowY, colour, !isComment, this.hScrollBar.getValue(), this.displayCols);
/*      */       } 
/*      */       
/*  956 */       rowY += this.rowHeight;
/*      */     } 
/*      */     
/*  959 */     int cursorX = getXPosition() + (this.cursorCol - this.hScrollBar.getValue()) * this.charWidth;
/*      */ 
/*      */     
/*  962 */     if (this.updateCounter / 6 % 2 == 0 && this.cursorCol >= this.hScrollBar.getValue() && this.cursorCol < this.hScrollBar.getValue() + this.displayCols && this.cursorRow >= this.vScrollBar
/*  963 */       .getValue() && this.cursorRow < this.vScrollBar.getValue() + this.displayRows) {
/*      */       
/*  965 */       int cursorY = getYPosition() + (this.cursorRow - this.vScrollBar.getValue()) * this.rowHeight;
/*      */       
/*  967 */       GL.glEnableColorLogic();
/*  968 */       GL.glLogicOp(5382);
/*  969 */       a(cursorX, cursorY, cursorX + this.charWidth, cursorY + this.rowHeight, -5592406);
/*  970 */       GL.glDisableColorLogic();
/*      */     } 
/*      */     
/*  973 */     this.vScrollBar.drawControl(minecraft, mouseX, mouseY);
/*  974 */     this.hScrollBar.drawControl(minecraft, mouseX, mouseY);
/*      */     
/*  976 */     if (this.showCommandHelp) {
/*      */       
/*  978 */       if (documentation != this.lastDocumentationEntry)
/*      */       {
/*  980 */         this.documentationDelayCounter = 10;
/*      */       }
/*      */       
/*  983 */       this.lastDocumentationEntry = documentation;
/*      */       
/*  985 */       if (documentation != null && this.documentationDelayCounter == 0)
/*      */       {
/*  987 */         documentation.drawAt(this.mc.k, cursorX - documentationOffset, documentationYPos);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static String tabsToSpaces(String text) {
/*  994 */     while (text.indexOf('\t') > -1) {
/*      */       
/*  996 */       int col = text.indexOf('\t');
/*  997 */       text = text.substring(0, col) + getTabCharsForColumn(col) + text.substring(col + 1);
/*      */     } 
/*      */     
/* 1000 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getTabCharsForColumn(int col) {
/* 1010 */     int tabChars = col % 4;
/* 1011 */     return (tabChars == 0) ? "    " : ((tabChars == 1) ? "   " : ((tabChars == 2) ? "  " : " "));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1016 */     if (this.documentationDelayCounter > 0)
/* 1017 */       this.documentationDelayCounter--; 
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiTextEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
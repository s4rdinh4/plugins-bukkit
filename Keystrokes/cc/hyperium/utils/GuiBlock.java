package cc.hyperium.utils;

import net.minecraft.client.gui.*;
import java.util.*;

public class GuiBlock
{
    private int left;
    private int right;
    private int top;
    private int bottom;
    private boolean expandRight;
    private boolean printRight;
    
    public GuiBlock(final int left, final int right, final int top, final int bottom) {
        this.expandRight = true;
        this.printRight = false;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
    
    public GuiBlock multiply(final double scale) {
        return new GuiBlock((int)(this.left * scale), (int)(this.right * scale), (int)(this.top * scale), (int)(this.bottom * scale));
    }
    
    @Override
    public String toString() {
        return "GuiBlock{left=" + this.left + ", right=" + this.right + ", top=" + this.top + ", bottom=" + this.bottom + '}';
    }
    
    public int getWidth() {
        return this.right - this.left;
    }
    
    public int getHeight() {
        return this.bottom - this.top;
    }
    
    public void ensureWidth(final int width, final boolean scaleRight) {
        if (this.getWidth() < width) {
            if (scaleRight) {
                this.right = this.left + width;
            }
            else {
                this.left = this.right - width;
            }
        }
    }
    
    public void ensureHeight(final int height, final boolean scaleBottom) {
        if (this.getHeight() < height) {
            if (scaleBottom) {
                this.bottom = this.top + height;
            }
            else {
                this.top = this.bottom - height;
            }
        }
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public void setLeft(final int left) {
        this.left = left;
    }
    
    public int getRight() {
        return this.right;
    }
    
    public void setRight(final int right) {
        this.right = right;
    }
    
    public int getTop() {
        return this.top;
    }
    
    public void setTop(final int top) {
        this.top = top;
    }
    
    public boolean isMouseOver(final int x, final int y) {
        return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
    }
    
    public int getBottom() {
        return this.bottom;
    }
    
    public void setBottom(final int bottom) {
        this.bottom = bottom;
    }
    
    public boolean drawString(final List<String> strings, final FontRenderer fontRenderer, final boolean shadow, final boolean center, final int xOffset, final int yOffset, final boolean scaleToFitX, final boolean scaleToFixY, final int color, final boolean sideLeft) {
        boolean suc = true;
        for (final String string : strings) {
            suc = (suc && this.drawString(string, fontRenderer, shadow, center, xOffset, yOffset, scaleToFitX, scaleToFixY, color, sideLeft));
        }
        return suc;
    }
    
    public void translate(final int x, final int y) {
        this.left += x;
        this.right += x;
        this.top += y;
        this.bottom += y;
    }
    
    public void scalePosition(final float amount) {
        this.left *= (int)amount;
        this.right *= (int)amount;
        this.top *= (int)amount;
        this.bottom *= (int)amount;
    }
    
    public boolean drawString(final String string, final FontRenderer fontRenderer, final boolean shadow, final boolean center, final int xOffset, final int yOffset, final boolean scaleToFitX, final boolean scaleToFixY, final int color, final boolean sideLeft) {
        final int stringWidth = fontRenderer.func_78256_a(string);
        int x;
        if (sideLeft) {
            x = this.left + xOffset;
        }
        else {
            x = this.right - stringWidth - xOffset;
        }
        final int y = this.top + yOffset;
        if (center) {
            x -= stringWidth / 2;
        }
        if (sideLeft) {
            if (x + stringWidth > this.right) {
                if (!scaleToFitX) {
                    return false;
                }
                if (this.expandRight) {
                    this.right = x + stringWidth + xOffset;
                }
                else {
                    this.left = this.right - stringWidth - xOffset;
                    x = this.left;
                }
            }
        }
        else if (this.right - stringWidth < this.left) {
            if (!scaleToFitX) {
                return false;
            }
            if (this.expandRight) {
                this.right = x + stringWidth + xOffset;
                x = this.right;
            }
            else {
                this.left = this.right - stringWidth - xOffset;
            }
        }
        if (y + 10 > this.bottom) {
            if (!scaleToFixY) {
                return false;
            }
            this.bottom = y + 10;
        }
        if (y < this.top) {
            if (!scaleToFixY) {
                return false;
            }
            this.top = y;
        }
        fontRenderer.func_175065_a(string, (float)x, (float)y, color, shadow);
        return true;
    }
    
    public boolean isExpandRight() {
        return this.expandRight;
    }
    
    public void setExpandRight(final boolean expandRight) {
        this.expandRight = expandRight;
    }
    
    public boolean isPrintRight() {
        return this.printRight;
    }
    
    public void setPrintRight(final boolean printRight) {
        this.printRight = printRight;
    }
}

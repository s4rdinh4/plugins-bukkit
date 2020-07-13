package keystrokes.keys;

import net.minecraft.client.*;
import keystrokes.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;

public abstract class AbstractKey extends Gui
{
    protected final Minecraft mc;
    protected final KeystrokesMod mod;
    protected final int xOffset;
    protected final int yOffset;
    
    public AbstractKey(final KeystrokesMod mod, final int xOffset, final int yOffset) {
        this.mc = Minecraft.func_71410_x();
        this.mod = mod;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    protected Color getChromaColor(final double x, final double y, final double offsetScale) {
        final float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 * offsetScale - y * 10.0 * offsetScale) % v) / v, 0.8f, 0.8f));
    }
    
    protected void drawChromaString(final String text, int x, final int y, final double offsetScale) {
        final FontRenderer renderer = Minecraft.func_71410_x().field_71466_p;
        for (final char c : text.toCharArray()) {
            final int i = this.getChromaColor(x, y, offsetScale).getRGB();
            final String tmp = String.valueOf(c);
            renderer.func_78276_b(tmp, x, y, i);
            x += renderer.func_78256_a(tmp);
        }
    }
    
    protected abstract void renderKey(final int p0, final int p1);
    
    protected final int getColor() {
        return this.mod.getSettings().isChroma() ? Color.HSBtoRGB((System.currentTimeMillis() - this.xOffset * 10 - this.yOffset * 10) % 2000L / 2000.0f, 0.8f, 0.8f) : new Color(this.mod.getSettings().getRed(), this.mod.getSettings().getGreen(), this.mod.getSettings().getBlue()).getRGB();
    }
    
    protected final int getPressedColor() {
        return this.mod.getSettings().isChroma() ? new Color(0, 0, 0).getRGB() : new Color(this.mod.getSettings().getPressedRed(), this.mod.getSettings().getPressedGreen(), this.mod.getSettings().getPressedBlue()).getRGB();
    }
    
    protected final void drawCenteredString(final String text, final int x, final int y, final int color) {
        this.mc.field_71466_p.func_175065_a(text, (float)(x - this.mc.field_71466_p.func_78256_a(text) / 2), (float)y, color, false);
    }
    
    protected String getKeyOrMouseName(final int keyCode) {
        if (keyCode < 0) {
            final String openglName = Mouse.getButtonName(keyCode + 100);
            if (openglName != null) {
                if (openglName.equalsIgnoreCase("button0")) {
                    return "LMB";
                }
                if (openglName.equalsIgnoreCase("button1")) {
                    return "RMB";
                }
            }
            return openglName;
        }
        if (this.mod.getSettings().isUsingLiteralKeys()) {
            switch (keyCode) {
                case 41: {
                    return "~";
                }
                case 12:
                case 74: {
                    return "-";
                }
                case 40: {
                    return "'";
                }
                case 26: {
                    return "[";
                }
                case 27: {
                    return "]";
                }
                case 43: {
                    return "\\";
                }
                case 53:
                case 181: {
                    return "/";
                }
                case 51: {
                    return ",";
                }
                case 52: {
                    return ".";
                }
                case 39: {
                    return ";";
                }
                case 13: {
                    return "=";
                }
                case 200: {
                    return "\u25b2";
                }
                case 208: {
                    return "\u25bc";
                }
                case 203: {
                    return "\u25c0";
                }
                case 205: {
                    return "\u25b6";
                }
                case 55: {
                    return "*";
                }
                case 78: {
                    return "+";
                }
            }
        }
        return Keyboard.getKeyName(keyCode);
    }
}

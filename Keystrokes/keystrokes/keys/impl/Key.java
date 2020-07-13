package keystrokes.keys.impl;

import keystrokes.keys.*;
import net.minecraft.client.settings.*;
import keystrokes.*;
import org.lwjgl.input.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class Key extends AbstractKey
{
    private KeyBinding key;
    private boolean wasPressed;
    private long lastPress;
    
    public Key(final KeystrokesMod mod, final KeyBinding key, final int xOffset, final int yOffset) {
        super(mod, xOffset, yOffset);
        this.wasPressed = true;
        this.lastPress = 0L;
        this.key = key;
    }
    
    private boolean isKeyOrMouseDown(final int keyCode) {
        return (keyCode < 0) ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
    }
    
    public void renderKey(final int x, final int y) {
        Keyboard.poll();
        final boolean pressed = this.isKeyOrMouseDown(this.key.func_151463_i());
        final String name = this.getKeyOrMouseName(this.key.func_151463_i());
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
        }
        final int textColor = this.getColor();
        final int pressedColor = this.getPressedColor();
        int color;
        double textBrightness;
        if (pressed) {
            color = Math.min(255, (int)(this.mod.getSettings().getFadeTime() * 5.0 * (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.lastPress) / (this.mod.getSettings().getFadeTime() * 5.0));
        }
        else {
            color = Math.max(0, 255 - (int)(this.mod.getSettings().getFadeTime() * 5.0 * (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.min(1.0, (System.currentTimeMillis() - this.lastPress) / (this.mod.getSettings().getFadeTime() * 5.0));
        }
        if (this.mod.getSettings().isKeyBackground()) {
            if (this.mod.getSettings().getKeyBackgroundRed() == 0 && this.mod.getSettings().getKeyBackgroundGreen() == 0 && this.mod.getSettings().getKeyBackgroundBlue() == 0) {
                Gui.func_73734_a(x + this.xOffset, y + this.yOffset, x + this.xOffset + 22, y + this.yOffset + 22, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB() + (color << 16) + (color << 8) + color);
            }
            else {
                Gui.func_73734_a(x + this.xOffset, y + this.yOffset, x + this.xOffset + 22, y + this.yOffset + 22, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
            }
        }
        final int keyWidth = 22;
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        final int colorN = new Color(0, 0, 0).getRGB() + ((int)(red * textBrightness) << 16) + ((int)(green * textBrightness) << 8) + (int)(blue * textBrightness);
        final FontRenderer fontRenderer = this.mc.field_71466_p;
        final int stringWidth = fontRenderer.func_78256_a(name);
        float scaleFactor = 1.0f;
        if (stringWidth > keyWidth) {
            scaleFactor = keyWidth / (float)stringWidth;
        }
        GlStateManager.func_179094_E();
        float xPos = (float)(x + this.xOffset + 8);
        float yPos = (float)(y + this.yOffset + 8);
        GlStateManager.func_179152_a(scaleFactor, scaleFactor, 1.0f);
        if (scaleFactor != 1.0f) {
            final float scaleFactorRec = 1.0f / scaleFactor;
            xPos = (x + this.xOffset) * scaleFactorRec + 1.0f;
            yPos *= scaleFactorRec;
        }
        else if (name.length() > 1) {
            xPos -= stringWidth >> 2;
        }
        if (this.mod.getSettings().isUsingArrowKeys()) {
            final double padding = 5.0;
            double bottom = y + this.yOffset + keyWidth - padding;
            double right = x + this.xOffset + keyWidth - padding;
            double left = x + this.xOffset + padding;
            double top = y + this.yOffset + padding;
            double centerX = (left + right) / 2.0;
            final double centerY = (top + bottom) / 2.0;
            GlStateManager.func_179137_b(centerX, centerY, 0.0);
            Color topColor;
            Color bottomRightColor;
            Color bottomLeftColor = bottomRightColor = (topColor = new Color(pressed ? pressedColor : colorN));
            if (this.mod.getSettings().isChroma()) {
                topColor = this.getChromaColor(centerX, top, 1.0);
                bottomLeftColor = this.getChromaColor(left, bottom, 1.0);
                bottomRightColor = this.getChromaColor(right, bottom, 1.0);
            }
            int angle = 0;
            if (this.key == this.mc.field_71474_y.field_74370_x) {
                angle = -90;
                if (this.mod.getSettings().isChroma()) {
                    topColor = this.getChromaColor(centerX, centerY, 1.0);
                    bottomLeftColor = this.getChromaColor(left, bottom, 1.0);
                    bottomRightColor = this.getChromaColor(right, top, 1.0);
                }
            }
            if (this.key == this.mc.field_71474_y.field_74368_y) {
                angle = -180;
                if (this.mod.getSettings().isChroma()) {
                    topColor = this.getChromaColor(centerX, bottom, 1.0);
                    bottomLeftColor = this.getChromaColor(right, top, 1.0);
                    bottomRightColor = this.getChromaColor(left, top, 1.0);
                }
            }
            if (this.key == this.mc.field_71474_y.field_74366_z) {
                angle = 90;
                if (this.mod.getSettings().isChroma()) {
                    topColor = this.getChromaColor(right, centerY, 1.0);
                    bottomLeftColor = this.getChromaColor(left, top, 1.0);
                    bottomRightColor = this.getChromaColor(left, bottom, 1.0);
                }
            }
            GlStateManager.func_179114_b((float)angle, 0.0f, 0.0f, 1.0f);
            left -= centerX;
            right -= centerX;
            centerX = 0.0;
            top -= centerY;
            bottom -= centerY;
            final Tessellator tessellator = Tessellator.func_178181_a();
            final WorldRenderer worldrenderer = tessellator.func_178180_c();
            GlStateManager.func_179147_l();
            GlStateManager.func_179090_x();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179103_j(7425);
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
            worldrenderer.func_181662_b(centerX, top, 0.0).func_181669_b(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), 255).func_181675_d();
            worldrenderer.func_181662_b(centerX, top, 0.0).func_181669_b(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), 255).func_181675_d();
            worldrenderer.func_181662_b(left, bottom, 0.0).func_181669_b(bottomLeftColor.getRed(), bottomLeftColor.getGreen(), bottomLeftColor.getBlue(), 255).func_181675_d();
            worldrenderer.func_181662_b(right, bottom, 0.0).func_181669_b(bottomRightColor.getRed(), bottomRightColor.getGreen(), bottomLeftColor.getBlue(), 255).func_181675_d();
            tessellator.func_78381_a();
            GlStateManager.func_179103_j(7424);
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
        }
        else if (this.mod.getSettings().isChroma()) {
            this.drawChromaString(name, (int)xPos, (int)yPos, 1.0);
        }
        else {
            this.mc.field_71466_p.func_78276_b(name, (int)xPos, (int)yPos, pressed ? pressedColor : colorN);
        }
        GlStateManager.func_179121_F();
    }
}

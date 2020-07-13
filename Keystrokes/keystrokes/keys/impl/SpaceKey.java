package keystrokes.keys.impl;

import keystrokes.keys.*;
import net.minecraft.client.settings.*;
import keystrokes.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class SpaceKey extends AbstractKey
{
    private final KeyBinding key;
    private boolean wasPressed;
    private long lastPress;
    private final String name;
    
    public SpaceKey(final KeystrokesMod mod, final KeyBinding key, final int xOffset, final int yOffset, final String name) {
        super(mod, xOffset, yOffset);
        this.wasPressed = true;
        this.lastPress = 0L;
        this.key = key;
        this.name = name;
    }
    
    private boolean isButtonDown(final int buttonCode) {
        if (buttonCode < 0) {
            return Mouse.isButtonDown(buttonCode + 100);
        }
        return buttonCode > 0 && Keyboard.isKeyDown(buttonCode);
    }
    
    public void renderKey(final int x, final int y) {
        int yOffset = this.yOffset;
        if (!this.mod.getSettings().isShowingMouseButtons()) {
            yOffset -= 24;
        }
        if (!this.mod.getSettings().isShowingSneak()) {
            yOffset -= 18;
        }
        if (!this.mod.getSettings().isShowingWASD()) {
            yOffset -= 48;
        }
        Keyboard.poll();
        final boolean pressed = this.isButtonDown(this.key.func_151463_i());
        final String name = this.name.equalsIgnoreCase("space") ? (this.mod.getSettings().isChroma() ? "------" : (EnumChatFormatting.STRIKETHROUGH.toString() + "-----")) : "Sneak";
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
            textBrightness = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.lastPress) / (this.mod.getSettings().getFadeTime() * 2.0));
        }
        else {
            color = Math.max(0, 255 - (int)(this.mod.getSettings().getFadeTime() * 5.0 * (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.min(1.0, (System.currentTimeMillis() - this.lastPress) / (this.mod.getSettings().getFadeTime() * 2.0));
        }
        if (this.mod.getSettings().isKeyBackground()) {
            if (this.mod.getSettings().getKeyBackgroundRed() == 0 && this.mod.getSettings().getKeyBackgroundGreen() == 0 && this.mod.getSettings().getKeyBackgroundBlue() == 0) {
                Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB() + (color << 16) + (color << 8) + color);
            }
            else {
                Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
            }
        }
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        final int colorN = new Color(0, 0, 0).getRGB() + ((int)(red * textBrightness) << 16) + ((int)(green * textBrightness) << 8) + (int)(blue * textBrightness);
        if (this.mod.getSettings().isChroma()) {
            if (this.name.equalsIgnoreCase("space")) {
                final int xIn = x + (this.xOffset + 76) / 4;
                final int y2 = y + yOffset + 9;
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)xIn, (float)y2, 0.0f);
                GlStateManager.func_179114_b(-90.0f, 0.0f, 0.0f, 1.0f);
                this.func_73733_a(0, 0, 2, 35, Color.HSBtoRGB((System.currentTimeMillis() - xIn * 10 - y2 * 10) % 2000L / 2000.0f, 0.8f, 0.8f), Color.HSBtoRGB((System.currentTimeMillis() - (xIn + 35) * 10 - y2 * 10) % 2000L / 2000.0f, 0.8f, 0.8f));
                GlStateManager.func_179121_F();
            }
            else {
                this.drawChromaString(name, x + (this.xOffset + 70) / 2 - Minecraft.func_71410_x().field_71466_p.func_78256_a(name) / 2, y + yOffset + 5, 1.0);
            }
        }
        else {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + yOffset + 5, pressed ? pressedColor : colorN);
        }
    }
}

package keystrokes.keys.impl;

import keystrokes.keys.*;
import cc.hyperium.utils.*;
import keystrokes.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class CustomKey extends AbstractKey
{
    private int key;
    private boolean wasPressed;
    private long lastPress;
    private int type;
    private final GuiBlock hitbox;
    
    public CustomKey(final KeystrokesMod mod, final int key, final int type) {
        super(mod, 0, 0);
        this.wasPressed = true;
        this.lastPress = 0L;
        this.hitbox = new GuiBlock(0, 0, 0, 0);
        this.key = key;
        this.type = type;
    }
    
    public GuiBlock getHitbox() {
        return this.hitbox;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    private boolean isButtonDown(final int buttonCode) {
        if (buttonCode < 0) {
            return Mouse.isButtonDown(buttonCode + 100);
        }
        return buttonCode > 0 && Keyboard.isKeyDown(buttonCode);
    }
    
    public void renderKey(final int x, final int y) {
        Keyboard.poll();
        final boolean pressed = this.isButtonDown(this.key);
        final String name = (this.type == 0) ? (this.mod.getSettings().isChroma() ? "------" : (EnumChatFormatting.STRIKETHROUGH.toString() + "-----")) : this.getKeyOrMouseName(this.key);
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
        final int left = x + this.xOffset;
        final int top = y + this.yOffset;
        int right;
        int bottom;
        if (this.type == 0 || this.type == 1) {
            right = x + this.xOffset + 70;
            bottom = y + this.yOffset + 16;
        }
        else {
            right = x + this.xOffset + 22;
            bottom = y + this.yOffset + 22;
        }
        if (this.mod.getSettings().isKeyBackground()) {
            if (this.mod.getSettings().getKeyBackgroundRed() == 0 && this.mod.getSettings().getKeyBackgroundGreen() == 0 && this.mod.getSettings().getKeyBackgroundBlue() == 0) {
                Gui.func_73734_a(left, top, right, bottom, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB() + (color << 16) + (color << 8) + color);
            }
            else {
                Gui.func_73734_a(left, top, right, bottom, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
            }
        }
        this.hitbox.setLeft(left);
        this.hitbox.setTop(top);
        this.hitbox.setRight(right);
        this.hitbox.setBottom(bottom);
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        final int colorN = new Color(0, 0, 0).getRGB() + ((int)(red * textBrightness) << 16) + ((int)(green * textBrightness) << 8) + (int)(blue * textBrightness);
        final float yPos = (float)(y + this.yOffset + 8);
        final FontRenderer fontRendererObj = Minecraft.func_71410_x().field_71466_p;
        if (this.mod.getSettings().isChroma()) {
            if (this.type == 0) {
                final int xIn = x + (this.xOffset + 76) / 4;
                final int y2 = y + this.yOffset + 9;
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)xIn, (float)y2, 0.0f);
                GlStateManager.func_179114_b(-90.0f, 0.0f, 0.0f, 1.0f);
                this.func_73733_a(0, 0, 2, 35, Color.HSBtoRGB((System.currentTimeMillis() - xIn * 10 - y2 * 10) % 2000L / 2000.0f, 0.8f, 0.8f), Color.HSBtoRGB((System.currentTimeMillis() - (xIn + 35) * 10 - y2 * 10) % 2000L / 2000.0f, 0.8f, 0.8f));
                GlStateManager.func_179121_F();
            }
            else if (this.type == 1) {
                this.drawChromaString(name, x + (this.xOffset + 70) / 2 - fontRendererObj.func_78256_a(name) / 2, y + this.yOffset + 5, 1.0);
            }
            else {
                this.drawChromaString(name, (left + right) / 2 - fontRendererObj.func_78256_a(name) / 2, (int)yPos, 1.0);
            }
        }
        else if (this.type == 0 || this.type == 1) {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + this.yOffset + 5, pressed ? pressedColor : colorN);
        }
        else {
            this.mc.field_71466_p.func_78276_b(name, (left + right) / 2 - fontRendererObj.func_78256_a(name) / 2, (int)yPos, pressed ? pressedColor : colorN);
        }
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
}

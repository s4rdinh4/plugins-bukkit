package keystrokes.keys.impl;

import keystrokes.keys.*;
import keystrokes.*;
import org.lwjgl.input.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class MouseButton extends AbstractKey
{
    private static final String[] BUTTONS;
    private final int button;
    private boolean wasPressed;
    private long lastPress;
    
    public MouseButton(final KeystrokesMod mod, final int button, final int xOffset, final int yOffset) {
        super(mod, xOffset, yOffset);
        this.wasPressed = true;
        this.lastPress = 0L;
        this.button = button;
    }
    
    int getButton() {
        return this.button;
    }
    
    public void renderKey(final int x, final int y) {
        int yOffset = this.yOffset;
        Mouse.poll();
        final boolean pressed = Mouse.isButtonDown(this.button);
        if (!this.mod.getSettings().isShowingWASD()) {
            yOffset -= 48;
        }
        final String name = MouseButton.BUTTONS[this.button];
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
                Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 34, y + yOffset + 22, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB() + (color << 16) + (color << 8) + color);
            }
            else {
                Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 34, y + yOffset + 22, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
            }
        }
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        final int colorN = new Color(0, 0, 0).getRGB() + ((int)(red * textBrightness) << 16) + ((int)(green * textBrightness) << 8) + (int)(blue * textBrightness);
        if (this.mod.getSettings().isShowingCPSOnButtons() && this.mod.getSettings().isShowingCPS()) {
            final int round = Math.round(y / 0.5f + yOffset / 0.5f + 28.0f);
            if (this.mod.getSettings().isChroma()) {
                this.drawChromaString(name, x + this.xOffset + 8, y + yOffset + 4, 1.0);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.0f);
                this.drawChromaString((name.equals(MouseButton.BUTTONS[0]) ? this.mod.getRenderer().getCPSKeys()[0].getLeftCPS() : this.mod.getRenderer().getCPSKeys()[0].getRightCPS()) + " CPS", Math.round(x / 0.5f + this.xOffset / 0.5f + 20.0f), round, 0.5);
            }
            else {
                this.mc.field_71466_p.func_78276_b(name, x + this.xOffset + 8, y + yOffset + 4, pressed ? pressedColor : colorN);
                GlStateManager.func_179094_E();
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.0f);
                this.mc.field_71466_p.func_78276_b((name.equals(MouseButton.BUTTONS[0]) ? this.mod.getRenderer().getCPSKeys()[0].getLeftCPS() : this.mod.getRenderer().getCPSKeys()[0].getRightCPS()) + " CPS", Math.round(x / 0.5f + this.xOffset / 0.5f + 20.0f), round, pressed ? pressedColor : colorN);
            }
            GlStateManager.func_179121_F();
        }
        else if (this.mod.getSettings().isChroma()) {
            this.drawChromaString(name, x + this.xOffset + 8, y + yOffset + 8, 1.0);
        }
        else {
            this.mc.field_71466_p.func_78276_b(name, x + this.xOffset + 8, y + yOffset + 8, pressed ? pressedColor : colorN);
        }
    }
    
    static {
        BUTTONS = new String[] { "LMB", "RMB" };
    }
}

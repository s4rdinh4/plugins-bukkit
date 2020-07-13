package keystrokes.keys.impl;

import keystrokes.keys.*;
import keystrokes.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class FPSKey extends AbstractKey
{
    public FPSKey(final KeystrokesMod mod, final int xOffset, final int yOffset) {
        super(mod, xOffset, yOffset);
    }
    
    public void renderKey(final int x, final int y) {
        int yOffset = this.yOffset;
        if (this.mod.getSettings().isShowingCPSOnButtons() || !this.mod.getSettings().isShowingCPS()) {
            yOffset -= 18;
        }
        if (!this.mod.getSettings().isShowingSpacebar()) {
            yOffset -= 19;
        }
        if (!this.mod.getSettings().isShowingSneak()) {
            yOffset -= 18;
        }
        if (!this.mod.getSettings().isShowingMouseButtons()) {
            yOffset -= 24;
        }
        if (!this.mod.getSettings().isShowingWASD()) {
            yOffset -= 48;
        }
        final int textColor = this.getColor();
        if (this.mod.getSettings().isKeyBackground()) {
            Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
        }
        final String name = Minecraft.func_175610_ah() + " FPS";
        if (this.mod.getSettings().isChroma()) {
            this.drawChromaString(name, x + (this.xOffset + 70) / 2 - this.mc.field_71466_p.func_78256_a(name) / 2, y + (yOffset + 4), 1.0);
        }
        else {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + (yOffset + 4), textColor);
        }
    }
}

package keystrokes.keys.impl;

import keystrokes.keys.*;
import keystrokes.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;

public class PingKey extends AbstractKey
{
    public PingKey(final KeystrokesMod mod, final int xOffset, final int yOffset) {
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
        if (!this.mod.getSettings().isShowingFPS()) {
            yOffset -= 18;
        }
        final int textColor = this.getColor();
        if (this.mod.getSettings().isKeyBackground()) {
            Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
        }
        final NetHandlerPlayClient netHandler = Minecraft.func_71410_x().func_147114_u();
        String ping = null;
        if (netHandler != null) {
            final NetworkPlayerInfo playerInfo = netHandler.func_175102_a(Minecraft.func_71410_x().func_110432_I().func_148256_e().getId());
            if (playerInfo != null) {
                ping = Integer.toString(playerInfo.func_178853_c());
            }
        }
        final String text = (ping == null) ? "Unknown" : (ping + "ms");
        if (this.mod.getSettings().isChroma()) {
            this.drawChromaString(text, x + (this.xOffset + 70) / 2 - this.mc.field_71466_p.func_78256_a(text) / 2, y + (yOffset + 4), 1.0);
        }
        else {
            this.drawCenteredString(text, x + (this.xOffset + 70) / 2, y + (yOffset + 4), textColor);
        }
    }
}

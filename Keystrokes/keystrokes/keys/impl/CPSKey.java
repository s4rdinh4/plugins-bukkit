package keystrokes.keys.impl;

import keystrokes.keys.*;
import keystrokes.*;
import java.util.*;
import net.minecraftforge.common.*;
import org.lwjgl.input.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class CPSKey extends AbstractKey
{
    private final List<Long> leftClicks;
    private final List<Long> rightClicks;
    private boolean leftWasDown;
    private boolean rightWasDown;
    
    public CPSKey(final KeystrokesMod mod, final int xOffset, final int yOffset) {
        super(mod, xOffset, yOffset);
        this.leftClicks = new ArrayList<Long>();
        this.rightClicks = new ArrayList<Long>();
        this.leftWasDown = false;
        this.rightWasDown = false;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void renderKey(final int x, final int y) {
        int yOffset = this.yOffset;
        if (!this.mod.getSettings().isShowingMouseButtons()) {
            yOffset -= 24;
        }
        if (!this.mod.getSettings().isShowingSpacebar()) {
            yOffset -= 18;
        }
        if (!this.mod.getSettings().isShowingSneak()) {
            yOffset -= 18;
        }
        if (!this.mod.getSettings().isShowingWASD()) {
            yOffset -= 48;
        }
        Mouse.poll();
        final int textColor = this.getColor();
        if (this.mod.getSettings().isKeyBackground()) {
            Gui.func_73734_a(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, new Color(this.mod.getSettings().getKeyBackgroundRed(), this.mod.getSettings().getKeyBackgroundGreen(), this.mod.getSettings().getKeyBackgroundBlue(), this.mod.getSettings().getKeyBackgroundOpacity()).getRGB());
        }
        final String name = (this.mod.getSettings().isLeftClick() ? this.getLeftCPS() : this.getRightCPS()) + " CPS";
        if (this.mod.getSettings().isChroma()) {
            this.drawChromaString(name, x + (this.xOffset + 70) / 2 - this.mc.field_71466_p.func_78256_a(name) / 2, y + (yOffset + 4), 1.0);
        }
        else {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + (yOffset + 4), textColor);
        }
    }
    
    int getLeftCPS() {
        final long time = System.currentTimeMillis();
        this.leftClicks.removeIf(o -> o + 1000L < time);
        return this.leftClicks.size();
    }
    
    int getRightCPS() {
        final long time = System.currentTimeMillis();
        this.rightClicks.removeIf(o -> o + 1000L < time);
        return this.rightClicks.size();
    }
    
    @SubscribeEvent
    public void onRender(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        Mouse.poll();
        boolean downNow = Mouse.isButtonDown(this.mod.getRenderer().getMouseButtons()[0].getButton());
        if (downNow != this.leftWasDown && downNow) {
            this.leftClicks.add(System.currentTimeMillis());
        }
        this.leftWasDown = downNow;
        downNow = Mouse.isButtonDown(this.mod.getRenderer().getMouseButtons()[1].getButton());
        if (downNow != this.rightWasDown && downNow) {
            this.rightClicks.add(System.currentTimeMillis());
        }
        this.rightWasDown = downNow;
    }
}

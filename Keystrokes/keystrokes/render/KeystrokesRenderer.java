package keystrokes.render;

import net.minecraft.client.*;
import keystrokes.*;
import keystrokes.keys.impl.*;
import net.minecraftforge.fml.common.gameevent.*;
import keystrokes.screen.*;
import java.io.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class KeystrokesRenderer
{
    private final Minecraft mc;
    private final KeystrokesMod mod;
    private final Key[] movementKeys;
    private final CPSKey[] cpsKeys;
    private final FPSKey[] fpsKeys;
    private final PingKey[] pingKeys;
    private final SpaceKey[] spaceKey;
    private final MouseButton[] mouseButtons;
    private final SpaceKey[] sneakKeys;
    private final List<CustomKeyWrapper> customKeys;
    
    public KeystrokesRenderer(final KeystrokesMod mod) {
        this.mc = Minecraft.func_71410_x();
        this.movementKeys = new Key[4];
        this.cpsKeys = new CPSKey[1];
        this.fpsKeys = new FPSKey[1];
        this.pingKeys = new PingKey[1];
        this.spaceKey = new SpaceKey[1];
        this.mouseButtons = new MouseButton[2];
        this.sneakKeys = new SpaceKey[1];
        this.customKeys = new ArrayList<CustomKeyWrapper>();
        this.mod = mod;
        this.movementKeys[0] = new Key(mod, this.mc.field_71474_y.field_74351_w, 26, 2);
        this.movementKeys[1] = new Key(mod, this.mc.field_71474_y.field_74368_y, 26, 26);
        this.movementKeys[2] = new Key(mod, this.mc.field_71474_y.field_74370_x, 2, 26);
        this.movementKeys[3] = new Key(mod, this.mc.field_71474_y.field_74366_z, 50, 26);
        this.cpsKeys[0] = new CPSKey(mod, 2, 110);
        this.fpsKeys[0] = new FPSKey(mod, 2, 128);
        this.pingKeys[0] = new PingKey(mod, 2, 146);
        this.spaceKey[0] = new SpaceKey(mod, this.mc.field_71474_y.field_74314_A, 2, 92, "Space");
        this.mouseButtons[0] = new MouseButton(mod, 0, 2, 50);
        this.mouseButtons[1] = new MouseButton(mod, 1, 38, 50);
        this.sneakKeys[0] = new SpaceKey(mod, this.mc.field_71474_y.field_74311_E, 2, 74, "Sneak");
        this.customKeys.addAll(this.mod.getSettings().getConfigWrappers());
    }
    
    public List<CustomKeyWrapper> getCustomKeys() {
        return this.customKeys;
    }
    
    public MouseButton[] getMouseButtons() {
        return this.mouseButtons;
    }
    
    public CPSKey[] getCPSKeys() {
        return this.cpsKeys;
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (this.mc.field_71462_r != null) {
            if (!(this.mc.field_71462_r instanceof GuiScreenKeystrokes)) {
                if (!(this.mc.field_71462_r instanceof GuiScreenColor)) {
                    return;
                }
            }
            try {
                this.mc.field_71462_r.func_146269_k();
            }
            catch (IOException ex) {}
        }
        else if (this.mc.field_71415_G && !this.mc.field_71474_y.field_74330_P) {
            this.renderKeystrokes();
        }
    }
    
    public void renderKeystrokes() {
        if (this.mod.getSettings().isEnabled()) {
            final int x = this.mod.getSettings().getRenderX();
            int y = this.mod.getSettings().getRenderY();
            final boolean showingMouseButtons = this.mod.getSettings().isShowingMouseButtons();
            final boolean showingSpacebar = this.mod.getSettings().isShowingSpacebar();
            final boolean showingCPS = this.mod.getSettings().isShowingCPS();
            final boolean showingCPSOnButtons = this.mod.getSettings().isShowingCPSOnButtons();
            final boolean showingSneak = this.mod.getSettings().isShowingSneak();
            final boolean showingFPS = this.mod.getSettings().isShowingFPS();
            final boolean showingPing = this.mod.getSettings().isShowPing();
            final boolean showingWASD = this.mod.getSettings().isShowingWASD();
            if (this.mod.getSettings().getScale() != 1.0) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179139_a(this.mod.getSettings().getScale(), this.mod.getSettings().getScale(), 1.0);
            }
            if (showingMouseButtons) {
                this.drawMouseButtons(x, y);
            }
            if (showingCPS && !showingCPSOnButtons) {
                this.drawCPSKeys(x, y);
            }
            if (showingSneak) {
                this.drawSneak(x, y);
            }
            if (showingSpacebar) {
                this.drawSpacebar(x, y);
            }
            if (showingFPS) {
                this.drawFPS(x, y);
            }
            if (showingPing) {
                this.drawPing(x, y);
            }
            if (showingWASD) {
                this.drawMovementKeys(x, y);
            }
            y += 148;
            if (!this.mod.getSettings().isShowingMouseButtons()) {
                y -= 24;
            }
            if (!showingSneak) {
                y -= 18;
            }
            if (!showingSpacebar) {
                y -= 18;
            }
            if (!showingCPS || showingCPSOnButtons) {
                y -= 18;
            }
            if (!showingFPS) {
                y -= 18;
            }
            if (!showingPing) {
                y -= 18;
            }
            if (!showingWASD) {
                y -= 18;
            }
            for (final CustomKeyWrapper customKey : this.customKeys) {
                final int xOffset = (int)customKey.getXOffset();
                final int yOffset = (int)customKey.getyOffset();
                customKey.getTheKey().renderKey(x + xOffset, y + yOffset);
            }
            if (this.mod.getSettings().getScale() != 1.0) {
                GlStateManager.func_179121_F();
            }
        }
    }
    
    private void drawSneak(final int x, final int y) {
        for (final SpaceKey sneakKey : this.sneakKeys) {
            sneakKey.renderKey(x, y);
        }
    }
    
    private void drawFPS(final int x, final int y) {
        for (final FPSKey fpsKey : this.fpsKeys) {
            fpsKey.renderKey(x, y);
        }
    }
    
    private void drawPing(final int x, final int y) {
        for (final PingKey pingKey : this.pingKeys) {
            pingKey.renderKey(x, y);
        }
    }
    
    private void drawMovementKeys(final int x, final int y) {
        for (final Key key : this.movementKeys) {
            key.renderKey(x, y);
        }
    }
    
    private void drawCPSKeys(final int x, final int y) {
        for (final CPSKey key : this.cpsKeys) {
            key.renderKey(x, y);
        }
    }
    
    private void drawSpacebar(final int x, final int y) {
        for (final SpaceKey key : this.spaceKey) {
            key.renderKey(x, y);
        }
    }
    
    private void drawMouseButtons(final int x, final int y) {
        for (final MouseButton button : this.mouseButtons) {
            button.renderKey(x, y);
        }
    }
}

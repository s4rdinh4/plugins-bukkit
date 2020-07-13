package keystrokes.screen;

import keystrokes.*;
import keystrokes.render.*;
import net.minecraft.client.gui.*;
import keystrokes.keys.impl.*;
import java.io.*;
import org.lwjgl.input.*;
import java.awt.*;
import cc.hyperium.utils.*;
import keystrokes.config.*;
import java.util.*;

public class GuiScreenEditKeys extends GuiScreen
{
    private final KeystrokesMod mod;
    private CustomKeyWrapper selected;
    private CustomKeyWrapper currentlyDragging;
    private int lastMouseX;
    private int lastMouseY;
    private boolean updated;
    private GuiButton changeKey;
    private GuiButton changeType;
    private GuiButton delete;
    private boolean listeningForNewKey;
    private final int previousOpacity;
    
    GuiScreenEditKeys(final KeystrokesMod mod) {
        this.selected = null;
        this.currentlyDragging = null;
        this.listeningForNewKey = false;
        this.mod = mod;
        this.previousOpacity = mod.getSettings().getKeyBackgroundOpacity();
    }
    
    public void func_73866_w_() {
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 44, "Add key"));
        this.field_146292_n.add(this.changeKey = new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 22, "Change key"));
        this.field_146292_n.add(this.changeType = new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m / 2, "Change type"));
        this.field_146292_n.add(this.delete = new GuiButton(4, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 22, "Delete"));
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        super.func_146284_a(button);
        switch (button.field_146127_k) {
            case 1: {
                final CustomKeyWrapper e = new CustomKeyWrapper(new CustomKey(this.mod, 30, 1), 10, 10);
                this.selected = e;
                this.mod.getRenderer().getCustomKeys().add(e);
                break;
            }
            case 2: {
                this.listeningForNewKey = true;
                this.changeKey.field_146126_j = "Listening...";
                break;
            }
            case 3: {
                final CustomKey theKey = this.selected.getTheKey();
                theKey.setType(theKey.getType() + 1);
                if (theKey.getType() > 2) {
                    theKey.setType(0);
                    break;
                }
                break;
            }
            case 4: {
                this.mod.getRenderer().getCustomKeys().remove(this.selected);
                this.selected = null;
                break;
            }
        }
    }
    
    public void func_146269_k() throws IOException {
        if (this.listeningForNewKey) {
            if (Mouse.next()) {
                final int eventButton = Mouse.getEventButton();
                if (Mouse.isButtonDown(eventButton)) {
                    this.selected.getTheKey().setKey(eventButton - 100);
                    this.listeningForNewKey = false;
                    this.changeKey.field_146126_j = "Change key";
                    return;
                }
            }
            if (Keyboard.next()) {
                final int eventKey = Keyboard.getEventKey();
                if (Keyboard.isKeyDown(eventKey)) {
                    this.selected.getTheKey().setKey(eventKey);
                    this.listeningForNewKey = false;
                    this.changeKey.field_146126_j = "Change key";
                    return;
                }
            }
        }
        super.func_146269_k();
    }
    
    public void func_146281_b() {
        if (this.updated) {
            this.mod.getSettings().save();
        }
        new GuiScreenKeystrokes(this.mod).display();
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.delete.field_146124_l = (this.selected != null);
        this.changeType.field_146124_l = (this.selected != null);
        this.changeKey.field_146124_l = (this.selected != null);
        if (this.selected != null) {
            final GuiBlock hitbox = this.selected.getTheKey().getHitbox().multiply(this.mod.getSettings().getScale());
            func_73734_a(hitbox.getLeft(), hitbox.getTop(), hitbox.getRight(), hitbox.getBottom(), Color.WHITE.getRGB());
            this.mod.getSettings().setKeyBackgroundOpacity(120);
        }
        this.mod.getRenderer().renderKeystrokes();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (this.selected != null && Mouse.isButtonDown(0) && this.currentlyDragging == null && this.selected.getTheKey().getHitbox().multiply(this.mod.getSettings().getScale()).isMouseOver(mouseX, mouseY)) {
            this.currentlyDragging = this.selected;
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        boolean h = false;
        for (final GuiButton guiButton : this.field_146292_n) {
            if (guiButton.func_146115_a()) {
                h = true;
            }
        }
        if (!h) {
            this.selected = null;
        }
        for (final CustomKeyWrapper customKeyWrapper : this.getKeys()) {
            if (customKeyWrapper.getTheKey().getHitbox().multiply(this.mod.getSettings().getScale()).isMouseOver(mouseX, mouseY)) {
                this.selected = customKeyWrapper;
                this.lastMouseX = mouseX;
                this.lastMouseY = mouseY;
            }
        }
    }
    
    protected void func_146273_a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        if (this.currentlyDragging != null) {
            final KeystrokesSettings settings = this.mod.getSettings();
            this.currentlyDragging.setxOffset(this.currentlyDragging.getXOffset() + (mouseX - this.lastMouseX) / settings.getScale());
            this.currentlyDragging.setyOffset(this.currentlyDragging.getyOffset() + (mouseY - this.lastMouseY) / settings.getScale());
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.updated = true;
        }
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int action) {
        super.func_146286_b(mouseX, mouseY, action);
        this.currentlyDragging = null;
        this.mod.getSettings().setKeyBackgroundOpacity(this.previousOpacity);
    }
    
    public List<CustomKeyWrapper> getKeys() {
        return this.mod.getRenderer().getCustomKeys();
    }
}

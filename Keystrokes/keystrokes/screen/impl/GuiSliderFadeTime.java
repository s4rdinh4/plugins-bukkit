package keystrokes.screen.impl;

import net.minecraftforge.fml.client.config.*;
import keystrokes.config.*;
import keystrokes.screen.*;
import keystrokes.*;
import net.minecraft.client.*;

public class GuiSliderFadeTime extends GuiSlider
{
    private final KeystrokesSettings settings;
    private final GuiScreenKeystrokes keystrokesGui;
    
    public GuiSliderFadeTime(final KeystrokesMod mod, final int id, final int xPos, final int yPos, final int width, final int height, final GuiScreenKeystrokes keystrokes) {
        super(id, xPos, yPos, width, height, "Fade Time: ", "%", 0.0, 30.0, mod.getSettings().getFadeTime() * 100.0, false, true);
        this.settings = mod.getSettings();
        this.keystrokesGui = keystrokes;
    }
    
    public void updateSlider() {
        super.updateSlider();
        this.settings.setFadeTime((float)(this.getValue() / 100.0));
        this.keystrokesGui.setUpdated();
        this.updateDisplayString();
    }
    
    public void func_146112_a(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.getValue() > this.maxValue) {
            this.setValue(this.maxValue);
        }
        else if (this.getValue() < this.minValue) {
            this.setValue(this.minValue);
        }
        this.updateDisplayString();
        super.func_146112_a(mc, mouseX, mouseY);
    }
    
    private void updateDisplayString() {
        if (this.getValue() < 10.0) {
            this.field_146126_j = this.dispString + "Slow";
        }
        else if (this.getValue() > 20.0) {
            this.field_146126_j = this.dispString + "Fast";
        }
        else {
            this.field_146126_j = this.dispString + "Normal";
        }
    }
}

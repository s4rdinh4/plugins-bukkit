package keystrokes.screen.impl;

import net.minecraftforge.fml.client.config.*;
import keystrokes.config.*;
import keystrokes.screen.*;
import keystrokes.*;

public class GuiSliderOpacity extends GuiSlider
{
    private final KeystrokesSettings settings;
    private final GuiScreenKeystrokes keystrokesGui;
    
    public GuiSliderOpacity(final KeystrokesMod mod, final int id, final int xPos, final int yPos, final int width, final int height, final GuiScreenKeystrokes keystrokes) {
        super(id, xPos, yPos, width, height, "Key Opacity: ", "", 0.0, 255.0, (double)mod.getSettings().getKeyBackgroundOpacity(), false, true);
        this.settings = mod.getSettings();
        this.keystrokesGui = keystrokes;
    }
    
    public void updateSlider() {
        super.updateSlider();
        this.settings.setKeyBackgroundOpacity(this.getValueInt());
        this.keystrokesGui.setUpdated();
    }
}

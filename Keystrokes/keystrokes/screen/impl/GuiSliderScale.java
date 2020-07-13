package keystrokes.screen.impl;

import net.minecraftforge.fml.client.config.*;
import keystrokes.config.*;
import keystrokes.screen.*;
import keystrokes.*;

public class GuiSliderScale extends GuiSlider
{
    private final KeystrokesSettings settings;
    private final GuiScreenKeystrokes keystrokesGui;
    
    public GuiSliderScale(final KeystrokesMod mod, final int id, final int xPos, final int yPos, final int width, final int height, final GuiScreenKeystrokes keystrokes) {
        super(id, xPos, yPos, width, height, "Scale: ", "%", 50.0, 150.0, mod.getSettings().getScale() * 100.0, false, true);
        this.settings = mod.getSettings();
        this.keystrokesGui = keystrokes;
    }
    
    public void updateSlider() {
        super.updateSlider();
        this.settings.setX(0);
        this.settings.setY(0);
        this.settings.setScale((float)(this.getValue() / 100.0));
        this.keystrokesGui.setUpdated();
    }
}

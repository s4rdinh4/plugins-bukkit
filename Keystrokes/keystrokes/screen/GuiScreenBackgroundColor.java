package keystrokes.screen;

import keystrokes.*;
import net.minecraftforge.fml.client.config.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.io.*;

public class GuiScreenBackgroundColor extends GuiScreen implements IScreen
{
    private final KeystrokesMod mod;
    private final IScrollable red;
    private final IScrollable green;
    private final IScrollable blue;
    private boolean updated;
    
    GuiScreenBackgroundColor(final KeystrokesMod mod, final IScrollable red, final IScrollable green, final IScrollable blue) {
        this.mod = mod;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public void func_73866_w_() {
        this.field_146292_n.add(new GuiSlider(0, this.field_146294_l / 2 - 80, this.calculateHeight(3), 150, 20, "Red: ", "", 0.0, 255.0, this.red.getAmount(), false, true) {
            public void updateSlider() {
                super.updateSlider();
                GuiScreenBackgroundColor.this.red.onScroll(this.getValue(), this.getValueInt());
                GuiScreenBackgroundColor.this.updated = true;
            }
        });
        this.field_146292_n.add(new GuiSlider(1, this.field_146294_l / 2 - 80, this.calculateHeight(4), 150, 20, "Green: ", "", 0.0, 255.0, this.green.getAmount(), false, true) {
            public void updateSlider() {
                super.updateSlider();
                GuiScreenBackgroundColor.this.green.onScroll(this.getValue(), this.getValueInt());
                GuiScreenBackgroundColor.this.updated = true;
            }
        });
        this.field_146292_n.add(new GuiSlider(2, this.field_146294_l / 2 - 80, this.calculateHeight(5), 150, 20, "Blue: ", "", 0.0, 255.0, this.blue.getAmount(), false, true) {
            public void updateSlider() {
                super.updateSlider();
                GuiScreenBackgroundColor.this.blue.onScroll(this.getValue(), this.getValueInt());
                GuiScreenBackgroundColor.this.updated = true;
            }
        });
        this.field_146292_n.add(new GuiButton(3, 5, this.field_146295_m - 25, 100, 20, "Back"));
    }
    
    protected void func_146284_a(final GuiButton button) {
        if (button.field_146127_k == 3) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiScreenKeystrokes(this.mod));
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiScreenKeystrokes(this.mod));
        }
        else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.mod.getRenderer().renderKeystrokes();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    public void func_146281_b() {
        if (this.updated) {
            this.mod.getSettings().save();
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
}

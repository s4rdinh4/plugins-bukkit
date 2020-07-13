package keystrokes.screen;

import keystrokes.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import keystrokes.screen.impl.*;
import keystrokes.config.*;
import java.io.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class GuiScreenKeystrokes extends GuiScreen implements IScreen
{
    private final KeystrokesMod mod;
    private final Minecraft mc;
    private GuiButton buttonEnabled;
    private GuiButton buttonShowMouseButtons;
    private GuiButton buttonShowSpacebar;
    private GuiButton buttonToggleChroma;
    private GuiButton buttonShowCPS;
    private GuiButton buttonShowCPSOnButton;
    private GuiButton buttonColors;
    private GuiButton buttonRightClick;
    private GuiButton buttonSneak;
    private GuiButton buttonFPS;
    private GuiButton buttonKeyBackground;
    private GuiButton buttonShowWASD;
    private GuiButton buttonLiteralKeys;
    private GuiButton buttonArrowKeys;
    private GuiButton buttonShowPing;
    private boolean dragging;
    private boolean updated;
    private int lastMouseX;
    private int lastMouseY;
    private boolean show;
    
    public GuiScreenKeystrokes(final KeystrokesMod mod) {
        this.dragging = false;
        this.updated = false;
        this.show = true;
        this.mod = mod;
        this.mc = Minecraft.func_71410_x();
    }
    
    public void func_73866_w_() {
        this.field_146292_n.clear();
        final KeystrokesSettings settings = this.mod.getSettings();
        this.field_146292_n.add(this.buttonEnabled = new GuiButton(0, this.field_146294_l / 2 - 155, this.calculateHeight(-1), 150, 20, "Keystrokes: " + (settings.isEnabled() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonShowMouseButtons = new GuiButton(1, this.field_146294_l / 2 + 5, this.calculateHeight(-1), 150, 20, "Show mouse buttons: " + (settings.isShowingMouseButtons() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonShowSpacebar = new GuiButton(2, this.field_146294_l / 2 - 155, this.calculateHeight(0), 150, 20, "Show spacebar: " + (settings.isShowingSpacebar() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonShowCPS = new GuiButton(3, this.field_146294_l / 2 + 5, this.calculateHeight(0), 150, 20, "Show CPS counter: " + (settings.isShowingCPS() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonShowCPSOnButton = new GuiButton(4, this.field_146294_l / 2 - 155, this.calculateHeight(1), 150, 20, "Show CPS on buttons: " + (settings.isShowingCPSOnButtons() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonToggleChroma = new GuiButton(5, this.field_146294_l / 2 + 5, this.calculateHeight(1), 150, 20, "Chroma: " + (settings.isChroma() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonRightClick = new GuiButton(6, this.field_146294_l / 2 - 155, this.calculateHeight(2), 150, 20, "Click Counter: " + (settings.isLeftClick() ? "Left" : "Right")));
        this.field_146292_n.add(this.buttonSneak = new GuiButton(7, this.field_146294_l / 2 + 5, this.calculateHeight(2), 150, 20, "Show sneak: " + (settings.isShowingSneak() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonFPS = new GuiButton(8, this.field_146294_l / 2 - 155, this.calculateHeight(3), 150, 20, "Show FPS: " + (settings.isShowingFPS() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonKeyBackground = new GuiButton(9, this.field_146294_l / 2 + 5, this.calculateHeight(3), 150, 20, "Key background: " + (settings.isKeyBackground() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonColors = new GuiButton(10, this.field_146294_l / 2 - 155, this.calculateHeight(4), 150, 20, "Edit key colors"));
        this.field_146292_n.add(new GuiButton(11, this.field_146294_l / 2 + 5, this.calculateHeight(4), 150, 20, "Edit key background colors"));
        this.field_146292_n.add(new GuiSliderScale(this.mod, 12, this.field_146294_l / 2 - 155, this.calculateHeight(5), 150, 20, this));
        this.field_146292_n.add(new GuiSliderFadeTime(this.mod, 13, this.field_146294_l / 2 + 5, this.calculateHeight(5), 150, 20, this));
        this.field_146292_n.add(this.buttonShowWASD = new GuiButton(14, this.field_146294_l / 2 - 155, this.calculateHeight(6), 150, 20, "Show WASD: " + (settings.isShowingWASD() ? "On" : "Off")));
        this.field_146292_n.add(new GuiSliderOpacity(this.mod, 15, this.field_146294_l / 2 + 5, this.calculateHeight(6), 150, 20, this));
        this.field_146292_n.add(new GuiButton(16, this.field_146294_l / 2 - 155, this.calculateHeight(7), 150, 20, "Edit Custom Keys"));
        this.field_146292_n.add(this.buttonLiteralKeys = new GuiButton(17, this.field_146294_l / 2 + 5, this.calculateHeight(7), 150, 20, "Literal Keys: " + (settings.isUsingLiteralKeys() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonArrowKeys = new GuiButton(18, this.field_146294_l / 2 - 155, this.calculateHeight(8), 150, 20, "Arrow Keys: " + (settings.isUsingArrowKeys() ? "On" : "Off")));
        this.field_146292_n.add(this.buttonShowPing = new GuiButton(19, this.field_146294_l / 2 + 5, this.calculateHeight(8), 150, 20, "Show Ping: " + (settings.isShowPing() ? "On" : "Off")));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.mod.getRenderer().renderKeystrokes();
        this.func_73732_a(this.mc.field_71466_p, "Keystrokes v" + KeystrokesMod.getVersion() + " - Created by Sk1er LLC", this.field_146294_l / 2, 5, 16777215);
        this.buttonColors.field_146124_l = !this.mod.getSettings().isChroma();
        this.buttonRightClick.field_146124_l = !this.mod.getSettings().isShowingCPSOnButtons();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    protected void func_146284_a(final GuiButton button) {
        final KeystrokesSettings settings = this.mod.getSettings();
        switch (button.field_146127_k) {
            case 0: {
                settings.setEnabled(!settings.isEnabled());
                this.buttonEnabled.field_146126_j = "Keystrokes: " + (settings.isEnabled() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 1: {
                settings.setShowingMouseButtons(!settings.isShowingMouseButtons());
                this.buttonShowMouseButtons.field_146126_j = "Show mouse buttons: " + (settings.isShowingMouseButtons() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 2: {
                settings.setShowingSpacebar(!settings.isShowingSpacebar());
                this.buttonShowSpacebar.field_146126_j = "Show spacebar: " + (settings.isShowingSpacebar() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 3: {
                settings.setShowingCPS(!settings.isShowingCPS());
                this.buttonShowCPS.field_146126_j = "Show CPS counter: " + (settings.isShowingCPS() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 4: {
                settings.setShowingCPSOnButtons(!settings.isShowingCPSOnButtons());
                this.buttonShowCPSOnButton.field_146126_j = "Show CPS on buttons: " + (settings.isShowingCPSOnButtons() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 5: {
                settings.setChroma(!settings.isChroma());
                this.buttonToggleChroma.field_146126_j = "Chroma: " + (settings.isChroma() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 6: {
                settings.setLeftClick(!settings.isLeftClick());
                this.buttonRightClick.field_146126_j = "Click Counter: " + (settings.isLeftClick() ? "Left" : "Right");
                this.updated = true;
                break;
            }
            case 7: {
                settings.setShowingSneak(!settings.isShowingSneak());
                this.buttonSneak.field_146126_j = "Show sneak: " + (settings.isShowingSneak() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 8: {
                settings.setShowingFPS(!settings.isShowingFPS());
                this.buttonFPS.field_146126_j = "Show FPS: " + (settings.isShowingFPS() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 9: {
                settings.setKeyBackground(!settings.isKeyBackground());
                this.buttonKeyBackground.field_146126_j = "Key background: " + (settings.isKeyBackground() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 10: {
                this.mc.func_147108_a((GuiScreen)new GuiScreenColor(this.mod, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getRed();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setRed(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getGreen();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setGreen(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getBlue();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setBlue(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getPressedRed();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setPressedRed(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getPressedGreen();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setPressedGreen(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getPressedBlue();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setPressedBlue(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }));
                break;
            }
            case 11: {
                this.mc.func_147108_a((GuiScreen)new GuiScreenBackgroundColor(this.mod, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getKeyBackgroundRed();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setKeyBackgroundRed(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getKeyBackgroundGreen();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setKeyBackgroundGreen(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }, new IScrollable() {
                    @Override
                    public double getAmount() {
                        return settings.getKeyBackgroundBlue();
                    }
                    
                    @Override
                    public void onScroll(final double doubleAmount, final int intAmount) {
                        settings.setKeyBackgroundBlue(intAmount);
                        GuiScreenKeystrokes.this.updated = true;
                    }
                }));
                break;
            }
            case 14: {
                settings.setShowingWASD(!settings.isShowingWASD());
                this.buttonShowWASD.field_146126_j = "Show WASD: " + (settings.isShowingWASD() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 16: {
                this.mc.func_147108_a((GuiScreen)new GuiScreenEditKeys(this.mod));
                break;
            }
            case 17: {
                settings.setUsingLiteralKeys(!settings.isUsingLiteralKeys());
                this.buttonLiteralKeys.field_146126_j = "Literal Keys: " + (settings.isUsingLiteralKeys() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 18: {
                settings.setUsingArrowKeys(!settings.isUsingArrowKeys());
                this.buttonArrowKeys.field_146126_j = "Arrow Keys: " + (settings.isUsingArrowKeys() ? "On" : "Off");
                this.updated = true;
                break;
            }
            case 19: {
                settings.setShowPing(!settings.isShowPing());
                this.buttonShowPing.field_146126_j = "Show Ping: " + (settings.isShowPing() ? "On" : "Off");
                this.updated = true;
                break;
            }
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int button) {
        try {
            super.func_73864_a(mouseX, mouseY, button);
        }
        catch (IOException ex) {}
        if (button == 0) {
            final KeystrokesSettings settings = this.mod.getSettings();
            if (!settings.isEnabled()) {
                return;
            }
            final int x = settings.getRenderX();
            final int y = settings.getRenderY();
            final int startX = (int)((x - 4) * settings.getScale());
            final int startY = (int)((y - 4) * settings.getScale());
            final int endX = (int)(startX + settings.getWidth() * settings.getScale());
            final int endY = (int)(startY + settings.getHeight() * settings.getScale());
            if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
                this.dragging = true;
                this.lastMouseX = mouseX;
                this.lastMouseY = mouseY;
            }
        }
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int action) {
        super.func_146286_b(mouseX, mouseY, action);
        this.dragging = false;
    }
    
    protected void func_146273_a(final int mouseX, final int mouseY, final int lastButtonClicked, final long timeSinceMouseClick) {
        if (this.dragging) {
            final KeystrokesSettings settings = this.mod.getSettings();
            settings.setX((int)(settings.getRenderX() + (mouseX - this.lastMouseX) / settings.getScale()));
            settings.setY((int)(settings.getRenderY() + (mouseY - this.lastMouseY) / settings.getScale()));
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.updated = true;
        }
    }
    
    public void func_146281_b() {
        if (this.updated) {
            this.mod.getSettings().save();
        }
    }
    
    public void display() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void tick(final TickEvent e) {
        if (this.show) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)this);
        }
        this.show = false;
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void setUpdated() {
        this.updated = true;
    }
    
    public KeystrokesMod getMod() {
        return this.mod;
    }
}

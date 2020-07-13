package keystrokes.config;

import keystrokes.*;
import keystrokes.render.*;
import java.io.*;
import java.util.stream.*;
import cc.hyperium.utils.*;
import club.sk1er.mods.core.util.*;
import java.util.*;
import keystrokes.keys.impl.*;
import com.google.gson.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class KeystrokesSettings
{
    public static final int SNEAK_HEIGHT = 18;
    private final KeystrokesMod theMod;
    private final File configFile;
    private int x;
    private int y;
    private boolean enabled;
    private boolean chroma;
    private boolean mouseButtons;
    private boolean showCPS;
    private boolean showCPSOnButtons;
    private boolean showSpacebar;
    private double scale;
    private double fadeTime;
    private int red;
    private int green;
    private int blue;
    private int pressedRed;
    private int pressedGreen;
    private int pressedBlue;
    private boolean showingFPS;
    private boolean keyBackground;
    private boolean leftClick;
    private boolean showingSneak;
    private boolean showWASD;
    private boolean literalKeys;
    private int keyBackgroundOpacity;
    private int keyBackgroundRed;
    private int keyBackgroundGreen;
    private int keyBackgroundBlue;
    private boolean arrowKeys;
    private final List<CustomKeyWrapper> configWrappers;
    private boolean showPing;
    
    public KeystrokesSettings(final KeystrokesMod mod, final File directory) {
        this.x = 0;
        this.y = 0;
        this.enabled = true;
        this.chroma = false;
        this.mouseButtons = false;
        this.showCPS = false;
        this.showCPSOnButtons = false;
        this.showSpacebar = false;
        this.scale = 1.0;
        this.fadeTime = 1.0;
        this.red = 255;
        this.green = 255;
        this.blue = 255;
        this.pressedRed = 0;
        this.pressedGreen = 0;
        this.pressedBlue = 0;
        this.showingFPS = false;
        this.keyBackground = true;
        this.leftClick = true;
        this.showWASD = true;
        this.keyBackgroundOpacity = 120;
        this.configWrappers = new ArrayList<CustomKeyWrapper>();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        this.theMod = mod;
        this.configFile = new File(directory, "keystrokes.json");
    }
    
    public void load() {
        try {
            if (!this.configFile.getParentFile().exists() || !this.configFile.exists()) {
                this.save();
                return;
            }
            final BufferedReader f = new BufferedReader(new FileReader(this.configFile));
            final List<String> options = f.lines().collect((Collector<? super String, ?, List<String>>)Collectors.toList());
            if (options.isEmpty()) {
                return;
            }
            final String builder = String.join("", options);
            if (builder.trim().length() > 0) {
                this.parseSettings(new BetterJsonObject(builder.trim()));
            }
            f.close();
        }
        catch (Exception ex) {
            System.out.println(String.format("Could not load config file! (\"%s\")", this.configFile.getName()));
            this.save();
        }
    }
    
    public boolean isShowPing() {
        return this.showPing;
    }
    
    public void setShowPing(final boolean showPing) {
        this.showPing = showPing;
    }
    
    public void save() {
        try {
            if (!this.configFile.getParentFile().exists()) {
                this.configFile.getParentFile().mkdirs();
            }
            if (!this.configFile.exists() && !this.configFile.createNewFile()) {
                return;
            }
            final BetterJsonObject object = new BetterJsonObject();
            object.addProperty("x", this.x);
            object.addProperty("y", this.y);
            object.addProperty("leftClick", this.leftClick);
            object.addProperty("red", this.red);
            object.addProperty("green", this.green);
            object.addProperty("blue", this.blue);
            object.addProperty("pressedRed", this.pressedRed);
            object.addProperty("pressedGreen", this.pressedGreen);
            object.addProperty("pressedBlue", this.pressedBlue);
            object.addProperty("scale", this.getScale());
            object.addProperty("fadeTime", this.getFadeTime());
            object.addProperty("enabled", this.enabled);
            object.addProperty("chroma", this.chroma);
            object.addProperty("mouseButtons", this.mouseButtons);
            object.addProperty("showCPS", this.showCPS);
            object.addProperty("showCPSOnButtons", this.showCPSOnButtons);
            object.addProperty("showSpacebar", this.showSpacebar);
            object.addProperty("fps", this.showingFPS);
            object.addProperty("showSneak", this.showingSneak);
            object.addProperty("keyBackground", this.keyBackground);
            object.addProperty("showWASD", this.showWASD);
            object.addProperty("literalKeys", this.literalKeys);
            object.addProperty("keyBackgroundOpacity", this.keyBackgroundOpacity);
            object.addProperty("keyBackgroundRed", this.keyBackgroundRed);
            object.addProperty("keyBackgroundGreen", this.keyBackgroundGreen);
            object.addProperty("keyBackgroundBlue", this.keyBackgroundBlue);
            object.addProperty("arrowKeys", this.arrowKeys);
            object.addProperty("ping", this.showPing);
            final JsonArray keys = new JsonArray();
            for (final CustomKeyWrapper customKeyWrapper : this.theMod.getRenderer().getCustomKeys()) {
                final JsonHolder holder = new JsonHolder();
                holder.put("key", customKeyWrapper.getTheKey().getKey());
                holder.put("type", customKeyWrapper.getTheKey().getType());
                holder.put("xOffset", customKeyWrapper.getXOffset());
                holder.put("yOffset", customKeyWrapper.getyOffset());
                keys.add((JsonElement)holder.getObject());
            }
            object.getData().add("custom", (JsonElement)keys);
            object.writeToFile(this.configFile);
        }
        catch (Exception ex) {
            System.out.println(String.format("Could not save config file! (\"%s\")", this.configFile.getName()));
        }
    }
    
    public boolean isShowingSneak() {
        return this.showingSneak;
    }
    
    public void setShowingSneak(final boolean showingSneak) {
        this.showingSneak = showingSneak;
    }
    
    public List<CustomKeyWrapper> getConfigWrappers() {
        return this.configWrappers;
    }
    
    public boolean isShowingFPS() {
        return this.showingFPS;
    }
    
    public void setShowingFPS(final boolean showingFPS) {
        this.showingFPS = showingFPS;
    }
    
    private void parseSettings(final BetterJsonObject object) {
        this.x = object.optInt("x");
        this.y = object.optInt("y");
        this.red = object.optInt("red", 255);
        this.green = object.optInt("green", 255);
        this.blue = object.optInt("blue", 255);
        this.pressedRed = object.optInt("pressedRed");
        this.pressedGreen = object.optInt("pressedGreen");
        this.pressedBlue = object.optInt("pressedBlue");
        this.setScale(object.optDouble("scale", 1.0));
        this.setFadeTime(object.optDouble("fadeTime", 1.0));
        this.enabled = object.optBoolean("enabled", true);
        this.chroma = object.optBoolean("chroma");
        this.leftClick = object.optBoolean("leftClick", true);
        this.mouseButtons = object.optBoolean("mouseButtons");
        this.showCPS = object.optBoolean("showCPS");
        this.showCPSOnButtons = object.optBoolean("showCPSOnButtons");
        this.showSpacebar = object.optBoolean("showSpacebar");
        this.showingFPS = object.optBoolean("fps");
        this.showingSneak = object.optBoolean("showSneak");
        this.keyBackground = object.optBoolean("keyBackground", true);
        this.showWASD = object.optBoolean("showWASD", true);
        this.literalKeys = object.optBoolean("literalKeys");
        this.keyBackgroundOpacity = object.optInt("keyBackgroundOpacity", 120);
        this.keyBackgroundRed = object.optInt("keyBackgroundRed");
        this.keyBackgroundGreen = object.optInt("keyBackgroundGreen");
        this.keyBackgroundBlue = object.optInt("keyBackgroundBlue");
        this.arrowKeys = object.optBoolean("arrowKeys");
        this.showPing = object.optBoolean("ping");
        final JsonObject data = object.getData();
        if (data.has("custom")) {
            final JsonArray custom = data.getAsJsonArray("custom");
            for (final JsonElement element : custom) {
                final JsonHolder holder = new JsonHolder(element.getAsJsonObject());
                final CustomKeyWrapper wrapper = new CustomKeyWrapper(new CustomKey(this.theMod, holder.optInt("key"), holder.optInt("type")), holder.optInt("xOffset"), holder.optInt("yOffset"));
                this.configWrappers.add(wrapper);
            }
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getRed() {
        return this.red;
    }
    
    public void setRed(final int red) {
        this.red = red;
    }
    
    public int getGreen() {
        return this.green;
    }
    
    public void setGreen(final int green) {
        this.green = green;
    }
    
    public int getBlue() {
        return this.blue;
    }
    
    public void setBlue(final int blue) {
        this.blue = blue;
    }
    
    public int getPressedRed() {
        return this.pressedRed;
    }
    
    public void setPressedRed(final int red) {
        this.pressedRed = red;
    }
    
    public int getPressedGreen() {
        return this.pressedGreen;
    }
    
    public void setPressedGreen(final int green) {
        this.pressedGreen = green;
    }
    
    public int getPressedBlue() {
        return this.pressedBlue;
    }
    
    public void setPressedBlue(final int blue) {
        this.pressedBlue = blue;
    }
    
    public double getScale() {
        return this.capDouble(this.scale, 0.5, 1.5);
    }
    
    public void setScale(final double scale) {
        this.scale = this.capDouble(scale, 0.5, 1.5);
    }
    
    public double getFadeTime() {
        return this.capDouble(this.fadeTime, 0.10000000149011612, 3.0);
    }
    
    public void setFadeTime(final double scale) {
        this.fadeTime = this.capDouble(scale, 0.10000000149011612, 3.0);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isShowingMouseButtons() {
        return this.mouseButtons;
    }
    
    public void setShowingMouseButtons(final boolean showingMouseButtons) {
        this.mouseButtons = showingMouseButtons;
    }
    
    public boolean isShowingSpacebar() {
        return this.showSpacebar;
    }
    
    public void setShowingSpacebar(final boolean showSpacebar) {
        this.showSpacebar = showSpacebar;
    }
    
    public boolean isShowingCPS() {
        return this.showCPS;
    }
    
    public void setShowingCPS(final boolean showingCPS) {
        this.showCPS = showingCPS;
    }
    
    public boolean isShowingCPSOnButtons() {
        return this.showCPSOnButtons;
    }
    
    public void setShowingCPSOnButtons(final boolean showCPSOnButtons) {
        this.showCPSOnButtons = showCPSOnButtons;
    }
    
    public boolean isChroma() {
        return this.chroma;
    }
    
    public void setChroma(final boolean showingChroma) {
        this.chroma = showingChroma;
    }
    
    public boolean isLeftClick() {
        return this.leftClick;
    }
    
    public void setLeftClick(final boolean leftClick) {
        this.leftClick = leftClick;
    }
    
    public boolean isKeyBackground() {
        return this.keyBackground;
    }
    
    public void setKeyBackground(final boolean keyBackground) {
        this.keyBackground = keyBackground;
    }
    
    public boolean isShowingWASD() {
        return this.showWASD;
    }
    
    public void setShowingWASD(final boolean showWASD) {
        this.showWASD = showWASD;
    }
    
    public boolean isUsingLiteralKeys() {
        return this.literalKeys;
    }
    
    public void setUsingLiteralKeys(final boolean literalKeys) {
        this.literalKeys = literalKeys;
    }
    
    public int getKeyBackgroundOpacity() {
        return this.keyBackgroundOpacity;
    }
    
    public void setKeyBackgroundOpacity(final int keyBackgroundOpacity) {
        this.keyBackgroundOpacity = keyBackgroundOpacity;
    }
    
    public int getKeyBackgroundRed() {
        return this.keyBackgroundRed;
    }
    
    public void setKeyBackgroundRed(final int keyBackgroundRed) {
        this.keyBackgroundRed = keyBackgroundRed;
    }
    
    public int getKeyBackgroundGreen() {
        return this.keyBackgroundGreen;
    }
    
    public void setKeyBackgroundGreen(final int keyBackgroundGreen) {
        this.keyBackgroundGreen = keyBackgroundGreen;
    }
    
    public int getKeyBackgroundBlue() {
        return this.keyBackgroundBlue;
    }
    
    public void setKeyBackgroundBlue(final int keyBackgroundBlue) {
        this.keyBackgroundBlue = keyBackgroundBlue;
    }
    
    public boolean isUsingArrowKeys() {
        return this.arrowKeys;
    }
    
    public void setUsingArrowKeys(final boolean arrowKeys) {
        this.arrowKeys = arrowKeys;
    }
    
    public int getHeight() {
        int height = 50;
        if (this.showCPS || this.showSpacebar || this.showingFPS) {
            height += 24;
        }
        if (this.mouseButtons) {
            height += 24;
        }
        if (this.showWASD) {
            height += 48;
        }
        if (!this.showingFPS) {
            height -= 18;
        }
        if (!this.showingSneak) {
            height -= 18;
        }
        if (!this.showCPS) {
            height -= 18;
        }
        if (this.showCPSOnButtons) {
            height -= 18;
        }
        if (!this.showPing) {
            height -= 18;
        }
        return height;
    }
    
    public int getWidth() {
        return 74;
    }
    
    public KeystrokesMod getMod() {
        return this.theMod;
    }
    
    private double capDouble(final double valueIn, final double minValue, final double maxValue) {
        return (valueIn < minValue) ? minValue : Math.min(valueIn, maxValue);
    }
    
    public int getRenderX() {
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        final int width = this.getWidth();
        int x = this.getX();
        if (x < 0) {
            x = 0;
        }
        else if (x * this.getScale() > res.func_78326_a() - width * this.getScale()) {
            x = (int)((res.func_78326_a() - width * this.getScale()) / this.getScale());
        }
        return x;
    }
    
    public int getRenderY() {
        final ScaledResolution res = new ScaledResolution(Minecraft.func_71410_x());
        final int height = this.getHeight();
        int y = this.getY();
        if (y < 0) {
            y = 0;
        }
        else if (y * this.getScale() > res.func_78328_b() - height * this.getScale()) {
            y = (int)((res.func_78328_b() - height * this.getScale()) / this.getScale());
        }
        return y;
    }
}

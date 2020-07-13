package keystrokes;

import net.minecraftforge.fml.common.*;
import keystrokes.config.*;
import keystrokes.render.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;

@Mod(modid = "keystrokesmod", name = "KeystrokesMod", version = "8.0.2", clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class KeystrokesMod
{
    static final String MODID = "keystrokesmod";
    static final String VERSION = "8.0.2";
    static final String MOD_NAME = "KeystrokesMod";
    private KeystrokesSettings config;
    private KeystrokesRenderer renderer;
    
    public static String getVersion() {
        return "8.0.2";
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.func_71410_x().field_71412_D);
        final File mcDataDir = Minecraft.func_71410_x().field_71412_D;
        (this.config = new KeystrokesSettings(this, new File(mcDataDir, "config"))).load();
        this.renderer = new KeystrokesRenderer(this);
        MinecraftForge.EVENT_BUS.register((Object)this.renderer);
        ClientCommandHandler.instance.func_71560_a((ICommand)new CommandKeystrokes(this));
    }
    
    public KeystrokesSettings getSettings() {
        return this.config;
    }
    
    public KeystrokesRenderer getRenderer() {
        return this.renderer;
    }
}

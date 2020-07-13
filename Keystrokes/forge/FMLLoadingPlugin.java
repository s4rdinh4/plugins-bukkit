package forge;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.launchwrapper.*;
import keystrokes.*;
import java.util.*;

public class FMLLoadingPlugin implements IFMLLoadingPlugin
{
    public String[] getASMTransformerClass() {
        final int initialize = ModCoreInstaller.initialize(Launch.minecraftHome, "1.8.9");
        if (ModCoreInstaller.isErrored() || (initialize != 0 && initialize != -1)) {
            System.out.println("Failed to load Sk1er Modcore - " + initialize + " - " + ModCoreInstaller.getError());
        }
        if (ModCoreInstaller.isIsRunningModCore()) {
            return new String[] { "club.sk1er.mods.core.forge.ClassTransformer" };
        }
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}

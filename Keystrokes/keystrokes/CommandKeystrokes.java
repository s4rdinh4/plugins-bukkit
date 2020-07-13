package keystrokes;

import net.minecraft.command.*;
import keystrokes.screen.*;
import java.util.*;

public class CommandKeystrokes extends CommandBase
{
    private final KeystrokesMod mod;
    
    public CommandKeystrokes(final KeystrokesMod mod) {
        this.mod = mod;
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return "keystrokes";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return null;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) {
        new GuiScreenKeystrokes(this.mod).display();
    }
    
    public List<String> func_71514_a() {
        return Collections.singletonList("keystrokesmod");
    }
}

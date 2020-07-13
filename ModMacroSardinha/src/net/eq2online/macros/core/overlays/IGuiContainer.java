package net.eq2online.macros.core.overlays;

import ajk;
import com.mumfrey.liteloader.transformers.access.Accessor;
import com.mumfrey.liteloader.transformers.access.Invoker;
import com.mumfrey.liteloader.transformers.access.ObfTableClass;
import net.eq2online.obfuscation.ObfTbl;

@ObfTableClass(ObfTbl.class)
@Accessor({"GuiContainer"})
public interface IGuiContainer {
  @Invoker({"getSlotAtPosition"})
  ajk getSlot(int paramInt1, int paramInt2);
  
  @Invoker({"handleMouseClick"})
  void mouseClick(ajk paramajk, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\IGuiContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
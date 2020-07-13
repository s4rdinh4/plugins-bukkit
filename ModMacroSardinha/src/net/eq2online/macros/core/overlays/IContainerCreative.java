package net.eq2online.macros.core.overlays;

import amj;
import com.mumfrey.liteloader.transformers.access.Accessor;
import com.mumfrey.liteloader.transformers.access.Invoker;
import com.mumfrey.liteloader.transformers.access.ObfTableClass;
import java.util.List;
import net.eq2online.obfuscation.ObfTbl;

@ObfTableClass(ObfTbl.class)
@Accessor({"ContainerCreative"})
public interface IContainerCreative {
  @Accessor({"itemsList"})
  List<amj> getItemsList();
  
  @Invoker({"scrollTo"})
  void scrollToPosition(float paramFloat);
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\IContainerCreative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package net.eq2online.macros.core.overlays;

import amj;
import aoo;
import java.util.List;

public interface IVanillaRecipe extends aoo {
  int getWidth();
  
  int getHeight();
  
  List<amj> getItems();
  
  boolean requiresCraftingTable();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\overlays\IVanillaRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
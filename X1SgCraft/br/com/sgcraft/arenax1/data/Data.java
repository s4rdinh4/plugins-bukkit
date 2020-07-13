package br.com.sgcraft.arenax1.data;

import java.util.List;
import org.bukkit.Location;

import br.com.sgcraft.arenax1.arena.Arena;

public interface Data {
  Arena loadArena(String paramString);
  
  List<Arena> loadAllArena();
  
  Location loadLobby();
  
  void saveLobby(Location paramLocation);
  
  void saveArena(Arena paramArena);
  
  void saveAllArena(List<Arena> paramList);
  
  void saveToBase() throws Exception;
}


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\data\Data.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
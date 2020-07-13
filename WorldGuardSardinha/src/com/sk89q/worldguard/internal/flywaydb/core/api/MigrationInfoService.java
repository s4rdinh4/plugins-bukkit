package com.sk89q.worldguard.internal.flywaydb.core.api;

public interface MigrationInfoService {
  MigrationInfo[] all();
  
  MigrationInfo current();
  
  MigrationInfo[] pending();
  
  MigrationInfo[] applied();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\MigrationInfoService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
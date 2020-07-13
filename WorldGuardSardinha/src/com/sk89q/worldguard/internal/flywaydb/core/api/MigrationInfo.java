package com.sk89q.worldguard.internal.flywaydb.core.api;

import java.util.Date;

public interface MigrationInfo extends Comparable<MigrationInfo> {
  MigrationType getType();
  
  Integer getChecksum();
  
  MigrationVersion getVersion();
  
  String getDescription();
  
  String getScript();
  
  MigrationState getState();
  
  Date getInstalledOn();
  
  Integer getExecutionTime();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\MigrationInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
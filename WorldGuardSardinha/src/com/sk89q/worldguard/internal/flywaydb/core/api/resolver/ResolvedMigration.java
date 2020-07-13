package com.sk89q.worldguard.internal.flywaydb.core.api.resolver;

import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationType;
import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;

public interface ResolvedMigration {
  MigrationVersion getVersion();
  
  String getDescription();
  
  String getScript();
  
  Integer getChecksum();
  
  MigrationType getType();
  
  String getPhysicalLocation();
  
  MigrationExecutor getExecutor();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\resolver\ResolvedMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
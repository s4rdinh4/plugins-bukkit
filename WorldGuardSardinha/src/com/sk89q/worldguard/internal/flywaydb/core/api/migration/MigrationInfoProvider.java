package com.sk89q.worldguard.internal.flywaydb.core.api.migration;

import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;

public interface MigrationInfoProvider {
  MigrationVersion getVersion();
  
  String getDescription();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\migration\MigrationInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
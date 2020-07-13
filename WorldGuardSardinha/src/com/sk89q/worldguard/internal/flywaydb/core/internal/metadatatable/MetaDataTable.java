package com.sk89q.worldguard.internal.flywaydb.core.internal.metadatatable;

import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationVersion;
import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.Schema;
import java.util.List;

public interface MetaDataTable {
  void lock();
  
  void addAppliedMigration(AppliedMigration paramAppliedMigration);
  
  boolean hasAppliedMigrations();
  
  List<AppliedMigration> allAppliedMigrations();
  
  void addInitMarker(MigrationVersion paramMigrationVersion, String paramString);
  
  boolean hasInitMarker();
  
  AppliedMigration getInitMarker();
  
  void removeFailedMigrations();
  
  void addSchemasMarker(Schema[] paramArrayOfSchema);
  
  boolean hasSchemasMarker();
  
  void updateChecksum(MigrationVersion paramMigrationVersion, Integer paramInteger);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\metadatatable\MetaDataTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
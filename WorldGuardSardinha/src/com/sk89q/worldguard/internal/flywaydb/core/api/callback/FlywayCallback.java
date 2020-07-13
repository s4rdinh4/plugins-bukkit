package com.sk89q.worldguard.internal.flywaydb.core.api.callback;

import com.sk89q.worldguard.internal.flywaydb.core.api.MigrationInfo;
import java.sql.Connection;

public interface FlywayCallback {
  void beforeClean(Connection paramConnection);
  
  void afterClean(Connection paramConnection);
  
  void beforeMigrate(Connection paramConnection);
  
  void afterMigrate(Connection paramConnection);
  
  void beforeEachMigrate(Connection paramConnection, MigrationInfo paramMigrationInfo);
  
  void afterEachMigrate(Connection paramConnection, MigrationInfo paramMigrationInfo);
  
  void beforeValidate(Connection paramConnection);
  
  void afterValidate(Connection paramConnection);
  
  void beforeInit(Connection paramConnection);
  
  void afterInit(Connection paramConnection);
  
  void beforeRepair(Connection paramConnection);
  
  void afterRepair(Connection paramConnection);
  
  void beforeInfo(Connection paramConnection);
  
  void afterInfo(Connection paramConnection);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\callback\FlywayCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
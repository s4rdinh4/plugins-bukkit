package com.sk89q.worldguard.internal.flywaydb.core.api.resolver;

import java.sql.Connection;
import java.sql.SQLException;

public interface MigrationExecutor {
  void execute(Connection paramConnection) throws SQLException;
  
  boolean executeInTransaction();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\resolver\MigrationExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
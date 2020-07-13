package com.sk89q.worldguard.internal.flywaydb.core.api.migration.jdbc;

import java.sql.Connection;

public interface JdbcMigration {
  void migrate(Connection paramConnection) throws Exception;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\migration\jdbc\JdbcMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
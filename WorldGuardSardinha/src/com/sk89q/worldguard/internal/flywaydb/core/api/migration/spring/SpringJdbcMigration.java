package com.sk89q.worldguard.internal.flywaydb.core.api.migration.spring;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SpringJdbcMigration {
  void migrate(JdbcTemplate paramJdbcTemplate) throws Exception;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\migration\spring\SpringJdbcMigration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
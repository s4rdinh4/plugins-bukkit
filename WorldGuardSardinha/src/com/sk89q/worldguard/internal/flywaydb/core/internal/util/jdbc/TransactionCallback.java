package com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc;

import java.sql.SQLException;

public interface TransactionCallback<T> {
  T doInTransaction() throws SQLException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\jdbc\TransactionCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
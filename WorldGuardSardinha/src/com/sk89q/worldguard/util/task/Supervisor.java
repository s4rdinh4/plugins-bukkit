package com.sk89q.worldguard.util.task;

import java.util.List;

public interface Supervisor {
  List<Task<?>> getTasks();
  
  void monitor(Task<?> paramTask);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\task\Supervisor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
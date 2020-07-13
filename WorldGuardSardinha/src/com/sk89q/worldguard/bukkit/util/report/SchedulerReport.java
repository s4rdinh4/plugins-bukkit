/*    */ package com.sk89q.worldguard.bukkit.util.report;
/*    */ 
/*    */ import com.google.common.base.Optional;
/*    */ import com.google.common.reflect.TypeToken;
/*    */ import com.sk89q.worldguard.internal.guava.cache.CacheBuilder;
/*    */ import com.sk89q.worldguard.internal.guava.cache.CacheLoader;
/*    */ import com.sk89q.worldguard.internal.guava.cache.LoadingCache;
/*    */ import com.sk89q.worldguard.util.report.DataReport;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchedulerReport
/*    */   extends DataReport
/*    */ {
/* 38 */   private LoadingCache<Class<?>, Optional<Field>> taskFieldCache = CacheBuilder.newBuilder()
/* 39 */     .build(new CacheLoader<Class<?>, Optional<Field>>()
/*    */       {
/*    */         public Optional<Field> load(Class<?> clazz) throws Exception {
/*    */           try {
/* 43 */             Field field = clazz.getDeclaredField("task");
/* 44 */             field.setAccessible(true);
/* 45 */             return Optional.fromNullable(field);
/* 46 */           } catch (NoSuchFieldException ignored) {
/* 47 */             return Optional.absent();
/*    */           } 
/*    */         }
/*    */       });
/*    */   
/*    */   public SchedulerReport() {
/* 53 */     super("Scheduler");
/*    */     
/* 55 */     List<BukkitTask> tasks = Bukkit.getServer().getScheduler().getPendingTasks();
/*    */     
/* 57 */     append("Pending Task Count", tasks.size());
/*    */     
/* 59 */     for (BukkitTask task : tasks) {
/* 60 */       Class<?> taskClass = getTaskClass(task);
/*    */       
/* 62 */       DataReport report = new DataReport("Task: #" + task.getTaskId());
/* 63 */       report.append("Owner", task.getOwner().getName());
/* 64 */       report.append("Runnable", (taskClass != null) ? taskClass.getName() : "<Unknown>");
/* 65 */       report.append("Synchronous?", task.isSync());
/* 66 */       append(report.getTitle(), report);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private Class<?> getTaskClass(BukkitTask task) {
/*    */     
/* 74 */     try { Class<?> clazz = task.getClass();
/* 75 */       Set<Class<?>> classes = TypeToken.of(clazz).getTypes().rawTypes();
/*    */       
/* 77 */       for (Class<?> type : classes) {
/* 78 */         Optional<Field> field = (Optional<Field>)this.taskFieldCache.getUnchecked(type);
/* 79 */         if (field.isPresent()) {
/* 80 */           return ((Field)field.get()).get(task).getClass();
/*    */         }
/*    */       }  }
/* 83 */     catch (IllegalAccessException ignored) {  }
/* 84 */     catch (NoClassDefFoundError ignored) {}
/*    */ 
/*    */     
/* 87 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukki\\util\report\SchedulerReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
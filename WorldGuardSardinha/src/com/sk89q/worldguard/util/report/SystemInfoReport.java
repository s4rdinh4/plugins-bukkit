/*    */ package com.sk89q.worldguard.util.report;
/*    */ 
/*    */ import java.lang.management.ClassLoadingMXBean;
/*    */ import java.lang.management.GarbageCollectorMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.OperatingSystemMXBean;
/*    */ import java.lang.management.RuntimeMXBean;
/*    */ import java.lang.management.ThreadInfo;
/*    */ import java.lang.management.ThreadMXBean;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ public class SystemInfoReport
/*    */   extends DataReport
/*    */ {
/*    */   public SystemInfoReport() {
/* 29 */     super("System Information");
/*    */     
/* 31 */     Runtime runtime = Runtime.getRuntime();
/* 32 */     RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
/* 33 */     ClassLoadingMXBean classLoadingBean = ManagementFactory.getClassLoadingMXBean();
/* 34 */     List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
/* 35 */     OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
/* 36 */     ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
/*    */     
/* 38 */     append("Java", "%s %s (%s)", new Object[] {
/* 39 */           System.getProperty("java.vendor"), 
/* 40 */           System.getProperty("java.version"), 
/* 41 */           System.getProperty("java.vendor.url") });
/* 42 */     append("Operating System", "%s %s (%s)", new Object[] {
/* 43 */           System.getProperty("os.name"), 
/* 44 */           System.getProperty("os.version"), 
/* 45 */           System.getProperty("os.arch") });
/* 46 */     append("Available Processors", runtime.availableProcessors());
/* 47 */     append("Free Memory", (runtime.freeMemory() / 1024L / 1024L) + " MB");
/* 48 */     append("Max Memory", (runtime.maxMemory() / 1024L / 1024L) + " MB");
/* 49 */     append("Total Memory", (runtime.totalMemory() / 1024L / 1024L) + " MB");
/* 50 */     append("System Load Average", osBean.getSystemLoadAverage());
/* 51 */     append("Java Uptime", TimeUnit.MINUTES.convert(runtimeBean.getUptime(), TimeUnit.MILLISECONDS) + " minutes");
/*    */     
/* 53 */     DataReport startup = new DataReport("Startup");
/* 54 */     startup.append("Input Arguments", runtimeBean.getInputArguments());
/* 55 */     append(startup.getTitle(), startup);
/*    */     
/* 57 */     DataReport vm = new DataReport("Virtual Machine");
/* 58 */     vm.append("Name", runtimeBean.getVmName());
/* 59 */     vm.append("Vendor", runtimeBean.getVmVendor());
/* 60 */     vm.append("Version", runtimeBean.getVmVendor());
/* 61 */     append(vm.getTitle(), vm);
/*    */     
/* 63 */     DataReport spec = new DataReport("Specification");
/* 64 */     spec.append("Name", runtimeBean.getSpecName());
/* 65 */     spec.append("Vendor", runtimeBean.getSpecVendor());
/* 66 */     spec.append("Version", runtimeBean.getSpecVersion());
/* 67 */     append(spec.getTitle(), spec);
/*    */     
/* 69 */     DataReport classLoader = new DataReport("Class Loader");
/* 70 */     classLoader.append("Loaded Class Count", classLoadingBean.getLoadedClassCount());
/* 71 */     classLoader.append("Total Loaded Class Count", classLoadingBean.getTotalLoadedClassCount());
/* 72 */     classLoader.append("Unloaded Class Count", classLoadingBean.getUnloadedClassCount());
/* 73 */     append(classLoader.getTitle(), classLoader);
/*    */     
/* 75 */     DataReport gc = new DataReport("Garbage Collectors");
/* 76 */     for (GarbageCollectorMXBean bean : gcBeans) {
/* 77 */       DataReport thisGC = new DataReport(bean.getName());
/* 78 */       thisGC.append("Collection Count", bean.getCollectionCount());
/* 79 */       thisGC.append("Collection Time", bean.getCollectionTime() + "ms");
/* 80 */       gc.append(thisGC.getTitle(), thisGC);
/*    */     } 
/* 82 */     append(gc.getTitle(), gc);
/*    */     
/* 84 */     DataReport threads = new DataReport("Threads");
/* 85 */     for (ThreadInfo threadInfo : threadBean.dumpAllThreads(false, false)) {
/* 86 */       threads.append("#" + threadInfo.getThreadId() + " " + threadInfo.getThreadName(), threadInfo.getThreadState());
/*    */     }
/* 88 */     append(threads.getTitle(), threads);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\SystemInfoReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
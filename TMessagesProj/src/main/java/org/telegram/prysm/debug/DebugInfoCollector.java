package org.telegram.prysm.debug;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import org.telegram.messenger.ApplicationLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DebugInfoCollector {
    
    public static class SystemInfo {
        public float cpuUsage;
        public long memoryUsed;
        public long memoryTotal;
        public int threadCount;
        public String deviceModel;
        public String androidVersion;
        public String appVersion;
        
        public String toFormattedString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== PRYSM DEBUG INFO ===\n");
            sb.append("Time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date())).append("\n");
            sb.append("Device: ").append(deviceModel).append("\n");
            sb.append("Android: ").append(androidVersion).append("\n");
            sb.append("App Version: ").append(appVersion).append("\n");
            sb.append("CPU Usage: ").append(String.format("%.1f%%", cpuUsage)).append("\n");
            sb.append("Memory: ").append(memoryUsed / 1024 / 1024).append("MB / ")
              .append(memoryTotal / 1024 / 1024).append("MB\n");
            sb.append("Threads: ").append(threadCount).append("\n");
            return sb.toString();
        }
    }
    
    public static SystemInfo collectInfo() {
        SystemInfo info = new SystemInfo();
        Context ctx = ApplicationLoader.applicationContext;
        
        // Device info
        info.deviceModel = Build.MANUFACTURER + " " + Build.MODEL;
        info.androidVersion = Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")";
        
        // Memory
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        info.memoryTotal = mi.totalMem;
        
        Debug.MemoryInfo debugInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(debugInfo);
        info.memoryUsed = debugInfo.getTotalPss() * 1024L;
        
        // Threads
        info.threadCount = Thread.activeCount();
        
        // CPU (simplified)
        info.cpuUsage = readCpuUsage();
        
        return info;
    }
    
    private static float readCpuUsage() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"));
            String line = reader.readLine();
            reader.close();
            // Simplified calculation
            return 0.0f;
        } catch (IOException e) {
            return -1.0f;
        }
    }
    
    public static String captureStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}

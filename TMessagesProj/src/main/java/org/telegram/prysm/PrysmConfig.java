package org.telegram.prysm;

import android.content.Context;
import android.content.SharedPreferences;
import org.telegram.messenger.ApplicationLoader;

public class PrysmConfig {
    public static final String PREFS_NAME = "prysm_prefs";
    
    // Spy settings
    public static final String KEY_SHOW_DELETED = "show_deleted_messages";
    public static final String KEY_SHOW_EDIT_HISTORY = "show_edit_history";
    public static final String KEY_TRANSPARENT_MODE = "transparent_mode";
    
    // Personalization
    public static final String KEY_DELETED_TRANSPARENT = "deleted_transparent";
    public static final String KEY_DOWNLOAD_SPEED = "download_speed"; // 0=off, 1=fast, 2=ultra
    public static final String KEY_UPLOAD_SPEED = "upload_speed_boost";
    
    // Instance
    private static PrysmConfig instance;
    private SharedPreferences prefs;
    
    private PrysmConfig() {
        prefs = ApplicationLoader.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public static PrysmConfig getInstance() {
        if (instance == null) instance = new PrysmConfig();
        return instance;
    }
    
    // Getters/Setters
    public boolean showDeletedMessages() { return prefs.getBoolean(KEY_SHOW_DELETED, true); }
    public void setShowDeletedMessages(boolean val) { prefs.edit().putBoolean(KEY_SHOW_DELETED, val).apply(); }
    
    public boolean showEditHistory() { return prefs.getBoolean(KEY_SHOW_EDIT_HISTORY, true); }
    public void setShowEditHistory(boolean val) { prefs.edit().putBoolean(KEY_SHOW_EDIT_HISTORY, val).apply(); }
    
    public boolean isTransparentMode() { return prefs.getBoolean(KEY_TRANSPARENT_MODE, false); }
    public void setTransparentMode(boolean val) { prefs.edit().putBoolean(KEY_TRANSPARENT_MODE, val).apply(); }
    
    public boolean isDeletedTransparent() { return prefs.getBoolean(KEY_DELETED_TRANSPARENT, false); }
    public void setDeletedTransparent(boolean val) { prefs.edit().putBoolean(KEY_DELETED_TRANSPARENT, val).apply(); }
    
    public int getDownloadSpeed() { return prefs.getInt(KEY_DOWNLOAD_SPEED, 1); }
    public void setDownloadSpeed(int val) { prefs.edit().putInt(KEY_DOWNLOAD_SPEED, val).apply(); }
    
    public boolean isUploadSpeedBoost() { return prefs.getBoolean(KEY_UPLOAD_SPEED, false); }
    public void setUploadSpeedBoost(boolean val) { prefs.edit().putBoolean(KEY_UPLOAD_SPEED, val).apply(); }
}

package org.telegram.prysm.spy;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import org.telegram.prysm.PrysmConfig;

public class TransparentModeManager {
    private static TransparentModeManager instance;
    private boolean isActive = false;
    
    public static TransparentModeManager getInstance() {
        if (instance == null) instance = new TransparentModeManager();
        return instance;
    }
    
    public void applyToActivity(Activity activity) {
        if (!PrysmConfig.getInstance().isTransparentMode()) return;
        
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        
        // Set transparency (0.0 = fully transparent, 1.0 = opaque)
        params.alpha = 0.75f; // 75% visible
        window.setAttributes(params);
        
        // Make background transparent
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public boolean isActive() {
        return isActive && PrysmConfig.getInstance().isTransparentMode();
    }
}

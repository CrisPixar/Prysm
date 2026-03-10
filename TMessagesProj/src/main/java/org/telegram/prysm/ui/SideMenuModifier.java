package org.telegram.prysm.ui;

import android.view.View;
import org.telegram.prysm.PrysmConfig;
import org.telegram.prysm.spy.TransparentModeManager;

public class SideMenuModifier {
    
    public static void addTransparentToggle(DrawerLayoutAdapter adapter) {
        // Add toggle item to side menu
        // This would be called during menu creation
    }
    
    public static void onTransparentToggle() {
        PrysmConfig cfg = PrysmConfig.getInstance();
        cfg.setTransparentMode(!cfg.isTransparentMode());
        // Apply to current activity
    }
}

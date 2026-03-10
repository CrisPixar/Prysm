package org.telegram.prysm.personalization;

import org.telegram.messenger.FileLoader;
import org.telegram.prysm.PrysmConfig;

public class SpeedBoostManager {
    
    public static void applyDownloadBoost() {
        int mode = PrysmConfig.getInstance().getDownloadSpeed();
        
        switch (mode) {
            case 0: // Off
                FileLoader.PRIORITY = 1;
                break;
            case 1: // Fast
                FileLoader.PRIORITY = 3;
                FileLoader.MAX_DOWNLOADS = 5;
                break;
            case 2: // Ultra
                FileLoader.PRIORITY = 5;
                FileLoader.MAX_DOWNLOADS = 10;
                // Enable aggressive prefetch
                break;
        }
    }
    
    public static void applyUploadBoost() {
        if (PrysmConfig.getInstance().isUploadSpeedBoost()) {
            // Increase upload threads
            FileLoader.MAX_UPLOADS = 5;
            // Enable compression bypass for speed
        }
    }
}

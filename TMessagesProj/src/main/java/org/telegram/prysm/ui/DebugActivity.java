package org.telegram.prysm.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.telegram.messenger.LocaleController;
import org.telegram.prysm.debug.DebugInfoCollector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DebugActivity extends BaseFragment {
    
    private TextView debugText;
    private ScheduledExecutorService scheduler;
    
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle("Debug");
        
        ScrollView scrollView = new ScrollView(context);
        FrameLayout layout = new FrameLayout(context);
        
        debugText = new TextView(context);
        debugText.setTextColor(0xFFFFFFFF);
        debugText.setTextSize(12);
        debugText.setPadding(16, 16, 16, 16);
        debugText.setTypeface(android.graphics.Typeface.MONOSPACE);
        
        layout.addView(debugText);
        scrollView.addView(layout);
        
        // Start monitoring
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateInfo, 0, 2, TimeUnit.SECONDS);
        
        return scrollView;
    }
    
    private void updateInfo() {
        DebugInfoCollector.SystemInfo info = DebugInfoCollector.collectInfo();
        AndroidUtilities.runOnUIThread(() -> {
            if (debugText != null) {
                debugText.setText(info.toFormattedString());
            }
        });
    }
    
    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (scheduler != null) scheduler.shutdown();
    }
}

package org.telegram.prysm.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.telegram.prysm.spy.EditHistoryManager;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditHistoryDialog {
    
    public static void show(Context context, int messageId) {
        List<EditHistoryManager.EditRecord> history = 
            EditHistoryManager.getInstance().getHistory(messageId);
        
        if (history.isEmpty()) return;
        
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);
        
        TextView title = new TextView(context);
        title.setText("История изменений");
        title.setTextSize(20);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        title.setPadding(0, 0, 0, 16);
        layout.addView(title);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm", Locale.getDefault());
        
        for (int i = history.size() - 1; i >= 0; i--) {
            EditHistoryManager.EditRecord record = history.get(i);
            
            TextView tv = new TextView(context);
            tv.setTextSize(14);
            
            if (record.editNumber == 0) {
                tv.setText("Текущая версия:\n" + record.text);
                tv.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText));
            } else {
                tv.setText("[" + sdf.format(new Date(record.editTime)) + "] Версия " + record.editNumber + ":\n" + record.text);
                tv.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
            }
            
            tv.setPadding(0, 16, 0, 16);
            layout.addView(tv);
            
            // Add divider
            if (i > 0) {
                View divider = new View(context);
                divider.setBackgroundColor(Theme.getColor(Theme.key_divider));
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
                layout.addView(divider);
            }
        }
        
        ScrollView scroll = new ScrollView(context);
        scroll.addView(layout);
        
        Dialog dialog = new Dialog(context);
        dialog.setContentView(scroll);
        dialog.show();
    }
}

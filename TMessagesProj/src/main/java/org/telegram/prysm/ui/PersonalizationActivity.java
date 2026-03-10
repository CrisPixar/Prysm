package org.telegram.prysm.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.prysm.PrysmConfig;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

public class PersonalizationActivity extends BaseFragment {
    
    private RecyclerListView listView;
    
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle(LocaleController.getString("PrysmPersonalization", R.string.PrysmPersonalization));
        
        fragmentView = new FrameLayout(context);
        listView = new RecyclerListView(context);
        listView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(context));
        listView.setAdapter(new ListAdapter(context));
        
        ((FrameLayout) fragmentView).addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        
        listView.setOnItemClickListener((view, position) -> {
            PrysmConfig cfg = PrysmConfig.getInstance();
            if (position == 1) {
                cfg.setDeletedTransparent(!cfg.isDeletedTransparent());
                ((TextCheckCell) view).setChecked(cfg.isDeletedTransparent());
            } else if (position == 2) {
                // Download speed dialog
                showDownloadSpeedDialog();
            } else if (position == 3) {
                cfg.setUploadSpeedBoost(!cfg.isUploadSpeedBoost());
                ((TextCheckCell) view).setChecked(cfg.isUploadSpeedBoost());
            }
        });
        
        return fragmentView;
    }
    
    private void showDownloadSpeedDialog() {
        String[] options = {"Выключено", "Быстро", "Ультра"};
        PrysmConfig cfg = PrysmConfig.getInstance();
        
        // Show alert dialog with options
        // Implementation depends on Telegram's AlertDialog
    }
    
    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        
        public ListAdapter(Context context) {
            mContext = context;
        }
        
        @Override
        public int getItemCount() {
            return 5; // Header + 4 options
        }
        
        @Override
        public void onBindViewHolder(RecyclerListView.Holder holder, int position) {
            PrysmConfig cfg = PrysmConfig.getInstance();
            
            if (position == 0) {
                ((HeaderCell) holder.itemView).setText("Внешний вид удалённых");
            } else if (position == 1) {
                TextCheckCell cell = (TextCheckCell) holder.itemView;
                cell.setTextAndCheck("Полупрозрачные удалённые", cfg.isDeletedTransparent(), true);
            } else if (position == 2) {
                TextDetailSettingsCell cell = (TextDetailSettingsCell) holder.itemView;
                String[] speeds = {"Выключено", "Быстро", "Ультра"};
                cell.setTextAndValue("Ускорение загрузки", speeds[cfg.getDownloadSpeed()], true);
            } else if (position == 3) {
                TextCheckCell cell = (TextCheckCell) holder.itemView;
                cell.setTextAndCheck("Ускорение выгрузки", cfg.isUploadSpeedBoost(), false);
            }
        }
        
        @Override
        public RecyclerListView.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) view = new HeaderCell(mContext);
            else if (viewType == 1) view = new TextCheckCell(mContext);
            else view = new TextDetailSettingsCell(mContext);
            return new RecyclerListView.Holder(view);
        }
        
        @Override
        public int getItemViewType(int position) {
            if (position == 0) return 0;
            if (position == 2) return 2;
            return 1;
        }
        
        @Override
        public boolean isEnabled(RecyclerListView.ViewHolder holder) {
            return holder.getItemViewType() != 0;
        }
    }
}

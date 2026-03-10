package org.telegram.prysm.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.prysm.PrysmConfig;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

public class SpySettingsActivity extends BaseFragment {
    
    private RecyclerListView listView;
    private int showDeletedRow;
    private int editHistoryRow;
    private int transparentRow;
    
    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        showDeletedRow = 0;
        editHistoryRow = 1;
        transparentRow = 2;
        return true;
    }
    
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle(LocaleController.getString("PrysmSpy", R.string.PrysmSpy));
        
        fragmentView = new FrameLayout(context);
        
        listView = new RecyclerListView(context);
        listView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(context));
        listView.setAdapter(new ListAdapter(context));
        
        ((FrameLayout) fragmentView).addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        
        listView.setOnItemClickListener((view, position) -> {
            PrysmConfig cfg = PrysmConfig.getInstance();
            if (position == showDeletedRow) {
                cfg.setShowDeletedMessages(!cfg.showDeletedMessages());
                ((TextCheckCell) view).setChecked(cfg.showDeletedMessages());
            } else if (position == editHistoryRow) {
                cfg.setShowEditHistory(!cfg.showEditHistory());
                ((TextCheckCell) view).setChecked(cfg.showEditHistory());
            } else if (position == transparentRow) {
                cfg.setTransparentMode(!cfg.isTransparentMode());
                ((TextCheckCell) view).setChecked(cfg.isTransparentMode());
            }
        });
        
        return fragmentView;
    }
    
    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        
        public ListAdapter(Context context) {
            mContext = context;
        }
        
        @Override
        public int getItemCount() {
            return 3;
        }
        
        @Override
        public void onBindViewHolder(RecyclerListView.Holder holder, int position) {
            TextCheckCell cell = (TextCheckCell) holder.itemView;
            PrysmConfig cfg = PrysmConfig.getInstance();
            
            if (position == showDeletedRow) {
                cell.setTextAndCheck("Отображать удалённые сообщения", cfg.showDeletedMessages(), true);
                cell.setSubtitle("Удалённые сообщения будут отображаться серым цветом");
            } else if (position == editHistoryRow) {
                cell.setTextAndCheck("История изменений", cfg.showEditHistory(), true);
                cell.setSubtitle("Показывать историю редактирования сообщений");
            } else if (position == transparentRow) {
                cell.setTextAndCheck("Прозрачный режим", cfg.isTransparentMode(), false);
                cell.setSubtitle("Сделать интерфейс частично прозрачным");
            }
        }
        
        @Override
        public RecyclerListView.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerListView.Holder(new TextCheckCell(mContext));
        }
        
        @Override
        public boolean isEnabled(RecyclerListView.ViewHolder holder) {
            return true;
        }
    }
}

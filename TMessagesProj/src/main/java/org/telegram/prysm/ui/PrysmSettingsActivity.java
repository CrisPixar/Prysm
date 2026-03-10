package org.telegram.prysm.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.prysm.PrysmConfig;
import org.telegram.prysm.spy.TransparentModeManager;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

import java.util.Random;

public class PrysmSettingsActivity extends BaseFragment {
    
    private RecyclerListView listView;
    private ListAdapter adapter;
    
    private int rowPrismHeader;
    private int rowSpy;
    private int rowPersonalization;
    private int rowDebug;
    private int rowCount;
    
    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        rowPrismHeader = 0;
        rowSpy = 1;
        rowPersonalization = 2;
        rowDebug = 3;
        rowCount = 4;
        return true;
    }
    
    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle(getRainbowText("Prysm"));
        actionBar.setAllowOverlayTitle(true);
        
        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        
        // Create rainbow subtitle
        TextCell subtitle = new TextCell(context);
        subtitle.setTextAndValue(getRainbowText("Prism"), "A client based only on pure rainbow!", true);
        subtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        
        listView = new RecyclerListView(context);
        listView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(context));
        adapter = new ListAdapter(context);
        listView.setAdapter(adapter);
        
        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        
        listView.setOnItemClickListener((view, position) -> {
            if (position == rowSpy) {
                presentFragment(new SpySettingsActivity());
            } else if (position == rowPersonalization) {
                presentFragment(new PersonalizationActivity());
            } else if (position == rowDebug) {
                presentFragment(new DebugActivity());
            }
        });
        
        return fragmentView;
    }
    
    private CharSequence getRainbowText(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        Random random = new Random();
        int[] colors = {
            Color.RED, Color.parseColor("#FF7F00"), Color.YELLOW, 
            Color.GREEN, Color.BLUE, Color.parseColor("#4B0082"), Color.parseColor("#9400D3")
        };
        
        for (int i = 0; i < text.length(); i++) {
            SpannableString s = new SpannableString(String.valueOf(text.charAt(i)));
            s.setSpan(new ForegroundColorSpan(colors[random.nextInt(colors.length)]), 
                     0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(s);
        }
        return builder;
    }
    
    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        
        public ListAdapter(Context context) {
            mContext = context;
        }
        
        @Override
        public int getItemCount() {
            return rowCount;
        }
        
        @Override
        public void onBindViewHolder(RecyclerListView.Holder holder, int position) {
            if (position == rowPrismHeader) {
                HeaderCell cell = (HeaderCell) holder.itemView;
                cell.setText(getRainbowText("Prism"));
            } else if (position == rowSpy) {
                TextCell cell = (TextCell) holder.itemView;
                cell.setTextAndIcon(LocaleController.getString("PrysmSpy", R.string.PrysmSpy), R.drawable.msg_secret, true);
            } else if (position == rowPersonalization) {
                TextCell cell = (TextCell) holder.itemView;
                cell.setTextAndIcon(LocaleController.getString("PrysmPersonalization", R.string.PrysmPersonalization), R.drawable.msg_theme, true);
            } else if (position == rowDebug) {
                TextCell cell = (TextCell) holder.itemView;
                cell.setTextAndIcon(LocaleController.getString("PrysmDebug", R.string.PrysmDebug), R.drawable.msg_log, false);
            }
        }
        
        @Override
        public RecyclerListView.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = new HeaderCell(mContext);
            } else {
                view = new TextCell(mContext);
            }
            return new RecyclerListView.Holder(view);
        }
        
        @Override
        public int getItemViewType(int position) {
            return position == rowPrismHeader ? 0 : 1;
        }
        
        @Override
        public boolean isEnabled(RecyclerListView.ViewHolder holder) {
            return holder.getAdapterPosition() != rowPrismHeader;
        }
    }
}

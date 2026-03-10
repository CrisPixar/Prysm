package org.telegram.prysm.spy;

import org.telegram.messenger.MessageObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditHistoryManager {
    private static EditHistoryManager instance;
    private Map<Integer, List<EditRecord>> editHistory = new HashMap<>();
    
    public static class EditRecord {
        public String text;
        public long editTime;
        public int editNumber;
        
        public EditRecord(String text, long time, int num) {
            this.text = text;
            this.editTime = time;
            this.editNumber = num;
        }
    }
    
    public static EditHistoryManager getInstance() {
        if (instance == null) instance = new EditHistoryManager();
        return instance;
    }
    
    public void recordEdit(int msgId, String oldText, String newText) {
        List<EditRecord> history = editHistory.computeIfAbsent(msgId, k -> new ArrayList<>());
        int editNum = history.size() + 1;
        history.add(new EditRecord(oldText, System.currentTimeMillis(), editNum));
        
        // Also store current
        if (history.stream().noneMatch(r -> r.editNumber == 0)) {
            history.add(0, new EditRecord(newText, System.currentTimeMillis(), 0));
        }
    }
    
    public List<EditRecord> getHistory(int msgId) {
        return editHistory.getOrDefault(msgId, new ArrayList<>());
    }
    
    public boolean hasHistory(int msgId) {
        List<EditRecord> h = editHistory.get(msgId);
        return h != null && h.size() > 1;
    }
}

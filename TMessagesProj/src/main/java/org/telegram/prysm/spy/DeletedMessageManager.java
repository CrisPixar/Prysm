package org.telegram.prysm.spy;

import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;
import java.util.HashMap;
import java.util.Map;

public class DeletedMessageManager {
    private static DeletedMessageManager instance;
    private Map<Integer, DeletedMessageInfo> deletedMessages = new HashMap<>();
    
    public static class DeletedMessageInfo {
        public MessageObject originalMessage;
        public String originalText;
        public TLRPC.MessageMedia originalMedia;
        public boolean wasReply;
        public int replyToMsgId;
        public long deletedTime;
        
        public boolean isMedia() {
            return originalMedia != null;
        }
        
        public String getContentPreview() {
            if (isMedia()) return "📷 Photo";
            return originalText != null ? originalText.substring(0, Math.min(50, originalText.length())) : "Empty";
        }
    }
    
    public static DeletedMessageManager getInstance() {
        if (instance == null) instance = new DeletedMessageManager();
        return instance;
    }
    
    public void markAsDeleted(MessageObject msg) {
        DeletedMessageInfo info = new DeletedMessageInfo();
        info.originalMessage = msg;
        info.originalText = msg.messageText != null ? msg.messageText.toString() : null;
        info.originalMedia = msg.messageOwner.media;
        info.wasReply = msg.messageOwner.reply_to != null;
        info.replyToMsgId = msg.messageOwner.reply_to != null ? msg.messageOwner.reply_to.reply_to_msg_id : 0;
        info.deletedTime = System.currentTimeMillis();
        
        deletedMessages.put(msg.getId(), info);
    }
    
    public DeletedMessageInfo getDeletedInfo(int msgId) {
        return deletedMessages.get(msgId);
    }
    
    public boolean isDeleted(int msgId) {
        return deletedMessages.containsKey(msgId);
    }
}

package org.telegram.prysm.spy;

import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;

public class MessageDeleteInterceptor {
    
    private static MessageDeleteInterceptor instance;
    
    public static MessageDeleteInterceptor getInstance() {
        if (instance == null) instance = new MessageDeleteInterceptor();
        return instance;
    }
    
    // Call this from MessagesController.deleteMessages before actual delete
    public boolean interceptDelete(long dialogId, ArrayList<Integer> msgIds) {
        if (!org.telegram.prysm.PrysmConfig.getInstance().showDeletedMessages()) {
            return false; // Don't intercept, allow normal delete
        }
        
        MessagesController ctrl = MessagesController.getInstance(org.telegram.messenger.UserConfig.selectedAccount);
        
        for (Integer msgId : msgIds) {
            MessageObject msg = ctrl.dialogMessagesByIds.get(msgId);
            if (msg != null) {
                // Store in our manager instead of deleting
                DeletedMessageManager.getInstance().markAsDeleted(msg);
                
                // Notify UI to refresh with "deleted" styling
                NotificationCenter.getInstance(org.telegram.messenger.UserConfig.selectedAccount)
                    .postNotificationName(NotificationCenter.messageDeletedByUser, dialogId, msgId);
            }
        }
        
        // Return true to prevent actual deletion from server (we keep local copy)
        // Or false if we want to allow server delete but keep local ghost
        return false;
    }
    
    // Format deleted message text
    public static CharSequence formatDeletedText(DeletedMessageManager.DeletedMessageInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("🗑️ ");
        
        if (info.wasReply) {
            sb.append("Ответ на Цитату\n");
        }
        
        if (info.isMedia()) {
            sb.append("📷 Photo");
        } else {
            sb.append(info.getContentPreview());
        }
        
        return sb.toString();
    }
}

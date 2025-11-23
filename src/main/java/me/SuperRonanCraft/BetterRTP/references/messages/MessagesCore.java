package me.SuperRonanCraft.BetterRTP.references.messages;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public enum MessagesCore {

    SUCCESS_PAID("Success.Paid"),
    SUCCESS_BYPASS("Success.Bypass"),
    SUCCESS_LOADING("Success.Loading"),
    SUCCESS_TELEPORT("Success.Teleport"),
    FAILED_NOTSAFE("Failed.NotSafe"),
    FAILED_PRICE("Failed.Price"),
    FAILED_HUNGER("Failed.Hunger"),
    OTHER_NOTSAFE("Other.NotSafe"),
    OTHER_SUCCESS("Other.Success"),
    OTHER_BIOME("Other.Biome"),
    NOTEXIST("NotExist"),
    RELOAD("Reload"),
    UPDATE("Update"),
    NOPERMISSION("NoPermission.Basic"),
    NOPERMISSION_WORLD("NoPermission.World"),
    DISABLED_WORLD("DisabledWorld"),
    COOLDOWN("Cooldown"),
    INVALID("Invalid"),
    NOTONLINE("NotOnline"),
    DELAY("Delay"),
    SIGN("Sign"),
    MOVED("Moved"),
    ALREADY("Already"),
    //EDIT
    EDIT_ERROR("Edit.Error"),
    EDIT_SET("Edit.Set"),
    EDIT_REMOVE("Edit.Remove"),
    ;

    final String section;

    MessagesCore(String section) {
        this.section = section;
    }

    private static final String pre = "Messages.";

    /**
     * Send message without placeholders - NOW WITH MINIMESSAGE SUPPORT
     */
    public void send(CommandSender sendi) {
        String message = Message_RTP.getLang().getString(pre + section);
        if (message != null && !message.isEmpty()) {
            // Use MessageUtil for MiniMessage support
            MessageUtil.send(sendi, message);
        }
    }

    /**
     * Send message with single placeholder object - NOW WITH MINIMESSAGE SUPPORT
     */
    public void send(CommandSender sendi, Object placeholderInfo) {
        String message = Message_RTP.getLang().getString(pre + section);
        if (message != null && !message.isEmpty()) {
            // Apply placeholders first
            message = Message.placeholder(sendi, message, placeholderInfo);
            // Then send with MiniMessage support
            MessageUtil.send(sendi, message);
        }
    }

    /**
     * Send message with list of placeholder objects - NOW WITH MINIMESSAGE SUPPORT
     */
    public void send(CommandSender sendi, List<Object> placeholderInfo) {
        String message = Message_RTP.getLang().getString(pre + section);
        if (message != null && !message.isEmpty()) {
            // Apply placeholders first
            message = Message.placeholder(sendi, message, placeholderInfo);
            // Then send with MiniMessage support
            MessageUtil.send(sendi, message);
        }
    }

    /**
     * Get message with placeholder - returns raw string
     */
    public String get(CommandSender p, Object placeholderInfo) {
        return Message.placeholder(p, Message_RTP.getLang().getString(pre + section), placeholderInfo);
    }

    /**
     * Send message with HashMap of placeholder values - NOW WITH MINIMESSAGE SUPPORT
     */
    public void send(CommandSender sendi, HashMap<String, String> placeholder_values) {
        String msg = Message_RTP.getLang().getString(pre + section);
        if (msg != null && !msg.isEmpty()) {
            // Replace all placeholders
            for (String ph : placeholder_values.keySet()) {
                msg = msg.replace(ph, placeholder_values.get(ph));
            }
            // Send with MiniMessage support
            MessageUtil.send(sendi, msg);
        }
    }
}
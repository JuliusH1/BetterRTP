package me.SuperRonanCraft.BetterRTP.references.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MessageUtil {
    private static BukkitAudiences adventure;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer legacySerializer =
            LegacyComponentSerializer.legacyAmpersand();

    /**
     * Initialize the Adventure platform
     */
    public static void init(Plugin plugin) {
        adventure = BukkitAudiences.create(plugin);
    }

    /**
     * Shutdown the Adventure platform
     */
    public static void close() {
        if (adventure != null) {
            adventure.close();
        }
    }

    /**
     * Parse a message supporting both MiniMessage and legacy color codes
     * Also handles <newline> tag for line breaks
     *
     * @param message The message to parse
     * @return Parsed Component
     */
    public static Component parseMessage(String message) {
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }

        // Replace <newline> with actual newlines first
        message = message.replace("<newline>", "\n");

        // Check if message contains MiniMessage tags
        if (containsMiniMessageTags(message)) {
            try {
                // First, convert legacy codes to MiniMessage format
                String converted = convertLegacyToMiniMessage(message);
                return miniMessage.deserialize(converted);
            } catch (Exception e) {
                // Fallback to legacy if MiniMessage parsing fails
                return legacySerializer.deserialize(
                        ChatColor.translateAlternateColorCodes('&', message)
                );
            }
        } else {
            // Only legacy codes, use legacy serializer
            return legacySerializer.deserialize(
                    ChatColor.translateAlternateColorCodes('&', message)
            );
        }
    }

    /**
     * Convert legacy color codes to MiniMessage format
     */
    private static String convertLegacyToMiniMessage(String message) {
        // Convert common legacy codes to MiniMessage
        message = message.replace("&0", "<black>")
                .replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>")
                .replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>")
                .replace("&5", "<dark_purple>")
                .replace("&6", "<gold>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&9", "<blue>")
                .replace("&a", "<green>")
                .replace("&b", "<aqua>")
                .replace("&c", "<red>")
                .replace("&d", "<light_purple>")
                .replace("&e", "<yellow>")
                .replace("&f", "<white>")
                .replace("&k", "<obfuscated>")
                .replace("&l", "<bold>")
                .replace("&m", "<strikethrough>")
                .replace("&n", "<underlined>")
                .replace("&o", "<italic>")
                .replace("&r", "<reset>");

        return message;
    }

    /**
     * Check if message contains MiniMessage tags
     */
    private static boolean containsMiniMessageTags(String message) {
        return message.contains("<") && message.contains(">") &&
                (message.contains("</") ||
                        message.matches(".*<(color|gradient|rainbow|hover|click|key|insert|" +
                                "font|decoration|newline|transition|selector).*>.*"));
    }

    /**
     * Send a message to a CommandSender
     */
    public static void send(CommandSender sender, String message) {
        if (adventure == null || sender == null || message == null) {
            return;
        }

        Component component = parseMessage(message);

        if (sender instanceof Player) {
            adventure.player((Player) sender).sendMessage(component);
        } else {
            adventure.sender(sender).sendMessage(component);
        }
    }

    /**
     * Send a message to a CommandSender with placeholder replacements
     */
    public static void send(CommandSender sender, String message,
                            String... replacements) {
        if (message == null || replacements.length % 2 != 0) {
            return;
        }

        // Replace placeholders
        for (int i = 0; i < replacements.length; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }

        send(sender, message);
    }
}
package me.lordmefloun.partysystem;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class Utils {

    private Utils(){
        throw new RuntimeException("You can not create instance of Util class");
    }

    public static void sendMessage(ProxiedPlayer p, String msg){
        p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public static String getPrefix(){
        return "&d[&5Party&d]";
    }

}

package me.lordmefloun.partysystem;

import net.md_5.bungee.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command {

    public PartyCommand(){
        super("party");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0){
                Utils.sendMessage(p, "");
                Utils.sendMessage(p,  "        &5&lParty System");
                Utils.sendMessage(p, "");
                Utils.sendMessage(p, "&d/party&5 create - vytvoří novou party");
                Utils.sendMessage(p, "&d/party&5 invite (hráč)");
                Utils.sendMessage(p, "&d/party&5 join (hráč)");
                Utils.sendMessage(p, "");
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("create")){
                    if (!PartyObject.isInParty(p)){
                        PartyObject newParty = new PartyObject(p, 4);
                    }
                    else{
                        Utils.sendMessage(p, "&cJiž jsi v party!");
                    }
                }
            }
        }
        else{
            sender.sendMessage(new TextComponent(ChatColor.RED + "Tento příkaz může použít jen hráč"));
        }
    }
}

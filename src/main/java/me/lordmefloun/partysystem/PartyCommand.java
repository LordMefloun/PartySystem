package me.lordmefloun.partysystem;

import net.md_5.bungee.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class PartyCommand extends Command {

    PartySystem plugin;
    public PartyCommand(PartySystem plugin){
        super("party");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
                if (!PartyObject.isInParty(p)) {
                    Utils.sendMessage(p, "");
                    Utils.sendMessage(p, "        &5&lParty System");
                    Utils.sendMessage(p, "");
                    Utils.sendMessage(p, "&d/party&5 create - vytvoří novou party");
                    Utils.sendMessage(p, "&d/party&5 invite (hráč)");
                    Utils.sendMessage(p, "&d/party&5 leave");
                    Utils.sendMessage(p, "");
                }
                else{
                    PartyObject party = PartyObject.getPlayerParty(p);
                    Utils.sendMessage(p, "");
                    Utils.sendMessage(p, "        &5&lParty System");
                    Utils.sendMessage(p , "");
                    Utils.sendMessage(p,"  &aHráči ve tvé aktualní party");
                    for (Map.Entry<ProxiedPlayer, PartyObject.Roles> entry : PartyObject.getPlayerParty(p).players.entrySet()) {
                        String prefix = (party.getLeader() == entry.getKey() ? "&6" : "&d");
                        Utils.sendMessage(p, "    " + prefix + entry.getKey());
                    }
                    Utils.sendMessage(p , "");
                }
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("create")){
                    if (!PartyObject.isInParty(p)){
                        PartyObject newParty = new PartyObject(p, 4);
                        Utils.sendMessage(p, Utils.getPrefix() +  " &aÚspešně sis založil party");
                    }
                    else{
                        Utils.sendMessage(p, "&cJiž jsi v party!");
                    }
                }
                if (args[0].equalsIgnoreCase("leave")){
                    PartyObject party = PartyObject.getPlayerParty(p);
                    if (party != null) {
                        party.leave(p);
                    } else Utils.sendMessage(p, "&cNejsi v žádné party!");
                }
            }
            else if (args.length >= 1){
                if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length == 2) {
                        ProxiedPlayer pSelected = plugin.getProxy().getPlayer(args[1]);
                        PartyObject party = PartyObject.getPlayerParty(p);
                        if (party != null) {
                            if (pSelected != null) {
                                if (party.getLeader() == p) {
                                    Utils.sendMessage(pSelected, Utils.getPrefix() + " &aByl jsi pozvan do party hrače " + p.getDisplayName());
                                    Utils.sendMessage(pSelected, Utils.getPrefix() + " &anapiš příkaz &d/party accept " + p.getDisplayName() + "&a pro přijetí");
                                    party.invite(pSelected, p);
                                } else Utils.sendMessage(p, "&cNejsi leader party");
                            } else Utils.sendMessage(p, "&cNenašel jsem tohoto hráče!");
                        } else Utils.sendMessage(p, "&cNejsi aktualně v žádné party!");
                    }
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (args.length == 2) {
                        ProxiedPlayer pSelected = plugin.getProxy().getPlayer(args[1]);
                        PartyObject party = PartyObject.getPlayerParty(pSelected);
                        if (party != null) {
                            if (pSelected != null) {
                                party.join(p);
                            } else Utils.sendMessage(p, "&cNenašel jsem tohoto hráče!");
                        } else Utils.sendMessage(p, "&cTato party neexistuje");
                    }
                }
            }
        }
        else sender.sendMessage(new TextComponent(ChatColor.RED + "Tento příkaz může použít jen hráč"));
    }
}

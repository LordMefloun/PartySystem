package me.lordmefloun.partysystem;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listeners implements Listener {

    @EventHandler
    public void Disconnect(PlayerDisconnectEvent e){
        ProxiedPlayer  p = e.getPlayer();
        PartyObject party = PartyObject.getPlayerParty(p);
        if (party != null) {
            party.broadcastParty(Utils.getPrefix() + " &a" + e.getPlayer().getDisplayName() + "&c se odpojil ze serveru");
            if (party.getLeader() == p)
                party.destroy();
            else party.players.remove(p);
        }
    }


    @EventHandler
    public void ServerJoin(ServerConnectedEvent e){
        ProxiedPlayer p  = e.getPlayer();
        PartyObject party  = PartyObject.getPlayerParty(p);
        Server s = e.getServer();
        if (party != null){
            if (party.getLeader() == p) {
                party.joinServer(s.getInfo());
                party.broadcastParty(Utils.getPrefix() + " &aParty byla přepojena na server " + s.getInfo().getName());
            }
        }
    }

    @EventHandler
    public void Chat(ChatEvent e){
        String message = e.getMessage();
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        PartyObject party = PartyObject.getPlayerParty(p);
        if (party != null){
            if (message.startsWith("@")){
                String prefix = (party.getLeader() == p ? "&6" : "&d");
                party.broadcastParty(Utils.getPrefix() + " " + prefix + p.getDisplayName() + " &5&l> &r" + message.replaceFirst("@", ""));
                e.setCancelled(true);
            }
        }
    }
}

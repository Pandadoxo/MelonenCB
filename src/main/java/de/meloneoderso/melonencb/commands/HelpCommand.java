package de.meloneoderso.melonencb.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        ComponentBuilder discordBuilder = new ComponentBuilder();
        discordBuilder.append("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Discord: ");
        discordBuilder.append("§7[§aMELONEODERSO#4830§7]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discordapp.com/users/498035802379386881"));

        ComponentBuilder dcServerBuilder = new ComponentBuilder();
        dcServerBuilder.append("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Dc-Server: ");
        dcServerBuilder.append("§7[§bDiscord§7]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/vRPKNzvumw"));

        ComponentBuilder twitchBuilder = new ComponentBuilder();
        twitchBuilder.append("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Twitch: ");
        twitchBuilder.append("§7[§dmelone_oderso§7]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/melone_oderso"));

        ComponentBuilder youtubeBuilder = new ComponentBuilder();
        youtubeBuilder.append("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Youtube: ");
        youtubeBuilder.append("§7[§4MELONEODERSO§7]").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/channel/UCXJClTCHMzOV6sDrvIfHEqg"));

        player.sendMessage("");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §f● Help §r§f●");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Du brauchst Hilfe,");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7hast einen Vorschlag,");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7willst dein Grundstück reseten,");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7hast eine Permissions nicht,");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7oder hast einen Bug gefunden?");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l");
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l≫ §7Dann schreib mir gerne auf Discord.");
        player.spigot().sendMessage(discordBuilder.create());
        player.spigot().sendMessage(dcServerBuilder.create());
        player.sendMessage("§f|§2 §lMELONEN§f§lCB§r §8§l");
        player.spigot().sendMessage(twitchBuilder.create());
        player.spigot().sendMessage(youtubeBuilder.create());
        player.sendMessage("");
        return false;
    }
}

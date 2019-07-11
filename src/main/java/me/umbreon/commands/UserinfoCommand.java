package me.umbreon.me.umbreon.commands;

import me.umbreon.API.MySQL;
import me.umbreon.main.Main;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UserinfoCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().toLowerCase().split(" ");
        if(args[0].equalsIgnoreCase(Main.prefix + "userinfo")){
            if(MySQL.checkCommand(e.getGuild().getId(), args[1]) == "1"){
                e.getChannel().sendMessage("s");
            }
        }
    }
}

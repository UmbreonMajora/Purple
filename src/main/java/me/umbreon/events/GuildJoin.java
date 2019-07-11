package me.umbreon.events;

import me.umbreon.API.MySQL;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter {
    public void onGuildJoin(GuildJoinEvent e){
        String GuildID = e.getGuild().getId();
        e.getGuild().getDefaultChannel().sendMessage("Heyo, thanks for adding me to " + e.getGuild().getName() + ".\n" +
                "If there are any issue please send me a message. The URL's are on the end of this message.\n" +
                "Discord: +\n" +
                "Twitter: \n" +
                "");
        if(MySQL.checkGuild(GuildID) == null){
            MySQL.setGuild(GuildID, "eng");
        }
    }

    private void welcomeMessage() {

    }
}

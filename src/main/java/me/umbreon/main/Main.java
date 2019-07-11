package me.umbreon.main;

import me.umbreon.API.MySQL;
import me.umbreon.events.GuildJoin;
import me.umbreon.me.umbreon.commands.DisableCommand;
import me.umbreon.me.umbreon.commands.MuteCommand;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;

import javax.security.auth.login.LoginException;

public class Main {
    public static String prefix = "!";
    public static String token = "NDk2NzAyNDU4MzEyMTMwNTYx.D3rDcQ.lU2Y9GdztuF3vUxbVZ76pdTIX30";
    public static void main(String args[]) throws LoginException {

        JDA jda = new JDABuilder(Main.token)
                .addEventListener(new MuteCommand())
                .addEventListener(new GuildJoin())
                .addEventListener(new DisableCommand())
                .build();
    }
    public void onReady(ReadyEvent e){
        MySQL.connect();
    }
    public void onStop(ShutdownEvent e){
        MySQL.close();
    }

}

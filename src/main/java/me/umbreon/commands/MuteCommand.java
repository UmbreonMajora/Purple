package me.umbreon.me.umbreon.commands;

import me.umbreon.main.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MuteCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");
        if(e.getGuild().getMember(e.getAuthor()).hasPermission(net.dv8tion.jda.core.Permission.VOICE_MUTE_OTHERS)){
            if(args[0].equalsIgnoreCase(Main.prefix + "mute")){

            }
            if(args.length <= 2){
                System.out.println("ERROR MESSAGE PLEASE ADD!");
            } else if (args.length >= 4) {
                Member target = e.getMessage().getMentionedMembers().get(0);
                String[] cleanArgs = makeDoubleWhitespacesToSingleWhitespace(args);
                tempmute(target, parseTimeAmount(cleanArgs[2]), parseTimeUnit(cleanArgs[2]));
                if(args.length  >= 4) {
                    String reason = "";
                    for(int i = 3; i < cleanArgs.length; i++){
                        reason += cleanArgs[i] + " ";
                        log(target, e.getMember(), reason, e.getGuild().getTextChannelById(Main.log));
                    }
                }
                log(target, e.getMember(), "", e.getGuild().getTextChannelById(Main.log));
            } else {
                System.out.println("User failed running the command correctly.");
            }
        } else {
            e.getMessage().getChannel().sendMessage("You can't mute this user.");
        }

    }


    private String[] makeDoubleWhitespacesToSingleWhitespace(String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String arg : args) {
            builder.append(arg + " ");
        }
        String str = builder.toString();
        str = str.replaceAll("\\s+", " ");
        return str.split(" ");
    }

    private void log(Member muted, Member muter, String reason, TextChannel channel){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("Muted User", muted.getAsMention(), false);
        builder.addField("Muter", muter.getAsMention(), false);
        builder.addField("Reason", reason, false);
        builder.addField("Date", sdf.format(date), false);
        builder.addField("Time", stf.format(date), false);
        channel.sendMessage(builder.build()).queue();
    }

    private int parseTimeAmount(String time) {
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toLowerCase().toCharArray();
        int muteTime = 0;
        String amount = "";
        int parsedAmount = 0;

        if(t[t.length - 1] == 's' || t[t.length - 1] == 'm' || t[t.length - 1] == 'h') {
            for(int i = 0; i < t.length; i++){
                if(t[i] == 's'){
                    unit = TimeUnit.SECONDS;
                    muteTime = i;
                    break;
                } else if(t[i] == 'm') {
                    unit = TimeUnit.MINUTES;
                    muteTime = i;
                    break;
                } else if(t[i] == 'h') {
                    unit = TimeUnit.HOURS;
                    muteTime = i;
                    break;
                }
            }
        } else {
            unit = TimeUnit.SECONDS;
            muteTime = t.length;
        }

        for(int i = 0; i < muteTime; i++) {
            amount += t[i];
        }
        parsedAmount = Integer.parseInt(amount);
        return parsedAmount;
    }

    private TimeUnit parseTimeUnit(String time){
        TimeUnit unit = TimeUnit.SECONDS;
        char[] t = time.toLowerCase().toCharArray();
        int breakpoint = 0;
        String amount = "";
        int parsedAmount = 0;
        for(int i = 0; i < t.length; i++){
            if(t[i] == 's'){
                unit = TimeUnit.SECONDS;
                breakpoint = i;
                break;
            } else if(t[i] == 'm') {
                unit = TimeUnit.MINUTES;
                breakpoint = i;
                break;
            } else if(t[i] == 'h') {
                unit = TimeUnit.HOURS;
                breakpoint = i;
                break;
            } else {
                unit = TimeUnit.SECONDS;
                breakpoint = i;
                break;
            }
        }
        return unit;
    }

    private void tempmute(final Member target, int time, TimeUnit unit){
        final Role muted = target.getGuild().getRolesByName("Muted", true).get(0);
        target.getGuild().getController().addSingleRoleToMember(target, muted).queue();

        Timer unmuteTargettimer = new Timer();
        TimerTask unmuteTargetTask = new TimerTask() {

            @Override
            public void run() {
                target.getGuild().getController().removeSingleRoleFromMember(target, muted).queue();
                this.cancel();
            }
        };

        switch (unit){
            case SECONDS:
                unmuteTargettimer.schedule(unmuteTargetTask, time * 1000);
                break;
            case MINUTES:
                unmuteTargettimer.schedule(unmuteTargetTask, time * 60 * 1000);
                break;
            case HOURS:
                unmuteTargettimer.schedule(unmuteTargetTask, time * 60 * 60 * 1000);
                break;
        }
    }
}

package net.vesperia.vescord.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.vesperia.vescord.util.VesperUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        final String name = event.getName();
        switch (name.toLowerCase()) {
            case "vesper" -> {
                if (event.getSubcommandName() == null) {
                    break;
                }
                switch (event.getSubcommandName().toLowerCase()) {
                    case "help" -> event.deferReply().queue(hook -> {
                        try {
                            JSONObject responseBody = VesperUtil.sendPrompt(event.getChannel().getId(), event.getUser().getName(), event.getOption("message").getAsString());
                            if (!responseBody.getString("status").equalsIgnoreCase("success")) {
                                hook.editOriginal("Something went wrong! Please try again or contact an administrator.").queue();
                                return;
                            }
                            hook.editOriginal(responseBody.getString("response")).queue();
                        } catch (IOException e) {
                            hook.editOriginal("Something went wrong! Please try again or contact an administrator.").queue();
                            e.printStackTrace();
                        }
                    });
                    case "train" -> event.deferReply().queue(hook -> {
                        try {
                            JSONObject responseBody = VesperUtil.sendDevPrompt(event.getChannel().getId(), event.getUser().getName(), event.getOption("dev_prompt").getAsString());
                            if (!responseBody.getString("status").equalsIgnoreCase("success")) {
                                hook.editOriginal("Something went wrong! Please try again or contact an administrator.").queue();
                                return;
                            }
                            hook.editOriginal(responseBody.getString("response")).queue();
                        } catch (IOException e) {
                            hook.editOriginal("Something went wrong! Please try again or contact an administrator.").queue();
                            e.printStackTrace();
                        }
                    });
                }
            }
            default -> {
                event.reply("This command has no usage.").setEphemeral(true).queue();
            }
        }
    }
}

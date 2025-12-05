package net.vesperia.vescord.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.vesperia.vescord.util.VesperUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        Message referencedMessage = message.getReferencedMessage();
        if (referencedMessage != null &&
                referencedMessage.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            event.getChannel().sendTyping().queue();
            try {
                JSONObject responseBody = VesperUtil.sendPrompt(event.getChannel().getId(), event.getAuthor().getName(), event.getMessage().getContentRaw());
                if (responseBody.getString("status").equalsIgnoreCase("success")) {
                    message.reply(responseBody.getString("response")).queue();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

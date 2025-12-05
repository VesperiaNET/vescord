package net.vesperia.vescord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.vesperia.vescord.config.Config;
import net.vesperia.vescord.listener.MessageListener;
import net.vesperia.vescord.listener.SlashCommandListener;

import java.io.IOException;
import java.nio.file.Path;

public final class Vescord {
    private static JDA jda;
    private static Config config;

    static void main() throws IOException {
        config = new Config(Path.of("data"), "config.json");
        config.load();
        jda = JDABuilder.create(config.getJson().getString("token"), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .disableCache(CacheFlag.EMOJI, CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.STICKER)
                .addEventListeners(new MessageListener(), new SlashCommandListener())
                .build();
        final CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                Commands.slash("vesper", "Helping ai")
                        .addSubcommands(
                                new SubcommandData("help", "Ask for help")
                                        .addOption(OptionType.STRING, "message", "Message our ai."),
                                new SubcommandData("train", "Train the ai model.")
                                        .addOption(OptionType.STRING, "dev_prompt", "Developer learn prompt.")
                        ).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))
        ).queue();
    }

    public static Config getConfig() {
        return config;
    }

    public static JDA getJDA() {
        return jda;
    }
}

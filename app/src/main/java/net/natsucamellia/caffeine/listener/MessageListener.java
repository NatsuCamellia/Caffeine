package net.natsucamellia.caffeine.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener extends ListenerAdapter {

    private final Logger logger;

    public MessageListener() {
        this.logger = LoggerFactory.getLogger(MessageListener.class);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        logMessage(message);
    }

    private void logMessage(Message message) {
        // Ignore messages from bot
        if (message.getAuthor().isBot()) {
            return;
        }

        if (message.isFromType(ChannelType.TEXT)) {
            Guild guild = message.getGuild();
            Channel channel = message.getChannel();
            Member member = message.getMember();
            String content = message.getContentRaw();

            // The message is from a guild
            assert member != null;

            logger.info("[{}] [{}] {}: {}", guild.getName(), channel.getName(), member.getEffectiveName(), content);
        }
    }
}

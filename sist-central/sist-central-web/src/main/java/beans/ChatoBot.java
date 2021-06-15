package beans;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import org.jboss.vfs.VirtualFile;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Named("chatoBot")
@ApplicationScoped()
public class ChatoBot {
    private final Bot bot;

    public ChatoBot() throws IOException {
        VirtualFile jbossVirtualFile = (VirtualFile) this.getClass().getProtectionDomain().getCodeSource().getLocation()
                .getContent();
        String absolutePathToFile = jbossVirtualFile.getPhysicalFile().getPath();
        this.bot = new Bot(BotConfiguration.builder()
                .name("reserva")
                .path(absolutePathToFile +"/META-INF")
                .build());
    }

    public Chat getChatSession() {
        return new Chat(bot);
    }
}

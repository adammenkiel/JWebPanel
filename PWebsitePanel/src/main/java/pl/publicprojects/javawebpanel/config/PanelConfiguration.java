package pl.publicprojects.javawebpanel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.publicprojects.pnettyclient.basic.NettyClient;

@Configuration
public class PanelConfiguration {
    @Bean
    public NettyClient getNettyClient() {
        return new NettyClient();
    }
}

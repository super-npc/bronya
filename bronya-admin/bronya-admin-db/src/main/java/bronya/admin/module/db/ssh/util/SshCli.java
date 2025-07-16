package bronya.admin.module.db.ssh.util;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.hutool.core.net.ProxySocketFactory;
import org.dromara.hutool.core.thread.ThreadUtil;

import bronya.admin.module.db.ssh.domain.SshDo;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.userauth.keyprovider.OpenSSHKeyFile;

@Data
@Slf4j
public class SshCli {
    private final SshDo sshCfg;
    private Proxy proxy;
    private SSHClient cli;
    private boolean running;

    public SshCli(SshDo sshCfg) throws IOException {
        this.sshCfg = sshCfg;
        this.create();
    }

    public SshCli(SshDo sshCfg, Proxy proxy) throws IOException {
        this.sshCfg = sshCfg;
        this.proxy = proxy;
        this.create();
    }

    @SneakyThrows
    public void close() {
        this.running = false;
        this.cli.close();
    }

    private void holdConnection() {
        log.info("开启一个线程保持连接:{}", sshCfg);
        this.running = true;
        ThreadUtil.execute(() -> {
            while (true) {
                try {
                    if (!running) {
                        log.info("关闭连接:{}", sshCfg);
                        break;
                    }
                    ThreadUtil.sleep(2, TimeUnit.SECONDS);
                    if (cli.isConnected()) {
                        // 如果连接长时间闲置，服务器可能会自动关闭连接。可以通过定期发送一些简单的命令（如 echo）来保持连接活跃。
                        try (Session session = cli.startSession()) {
                            session.exec("echo 1");
                        }
                    } else {
                        log.info("SSH连接断开，尝试重连,:{}", sshCfg);
                        this.connect();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void connect() throws IOException {
        log.info("连接ssh:{}", sshCfg);
        String privateKeyPath = sshCfg.getKeyFile();
        String sshHost = sshCfg.getHost();
        int sshPort = sshCfg.getPort();

        if (!cli.isConnected()) {
            log.info("校验连接:{}", sshCfg);
            cli.connect(sshHost, sshPort);
        }

        if (!cli.isAuthenticated()) {
            log.info("鉴权:{}", sshCfg);
            switch (sshCfg.getAuthType()) {
                case NONE -> {
                }
                case AUTH_PUBLIC_KEY -> {
                    // 使用私钥进行认证
                    OpenSSHKeyFile keyFile = new OpenSSHKeyFile();
                    keyFile.init(new File(privateKeyPath));
                    cli.authPublickey(sshCfg.getUserName(), keyFile);
                }
                case AUTH_PASSWORD -> {
                    cli.authPassword(sshCfg.getUserName(), sshCfg.getPassword());
                }
            }
        }
    }

    private void create() throws IOException {
        cli = new SSHClient();
        // 超时机制,防止一直阻塞
        cli.setConnectTimeout(2000);
        cli.setTimeout(2000);
        cli.getConnection().getKeepAlive().setKeepAliveInterval(5);
        // 加载已知主机文件（可选）// 如果没有校验本地指纹,需要将校验放开HostKeyVerifier
        // sshClient.loadKnownHosts();
        cli.addHostKeyVerifier(new HostKeyVerifier() {
            public boolean verify(String arg0, int arg1, PublicKey arg2) {
                return true; // don't bother verifying
            }

            @Override
            public List<String> findExistingAlgorithms(String s, int i) {
                return List.of();
            }
        });
        if (proxy != null) {
            cli.setSocketFactory(new ProxySocketFactory(proxy));
        }
        this.connect();
        this.holdConnection();
    }
}

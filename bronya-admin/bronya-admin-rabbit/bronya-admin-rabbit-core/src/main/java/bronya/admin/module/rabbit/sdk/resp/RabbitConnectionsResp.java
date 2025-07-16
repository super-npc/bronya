package bronya.admin.module.rabbit.sdk.resp;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RabbitConnectionsResp {
    private String authMechanism;
    private int channelMax;
    private int channels;
    private Map<String, Object> clientProperties;
    private long connectedAt;
    private int frameMax;
    private GarbageCollection garbageCollection;
    private String host;
    private String name;
    private String node;
    private String peerCertIssuer;
    private String peerCertSubject;
    private String peerCertValidity;
    private String peerHost;
    private int peerPort;
    private int port;
    private String protocol;
    private int recvCnt;
    private int recvOct;
    private RateDetails recvOctDetails;
    private int reductions;
    private RateDetails reductionsDetails;
    private int sendCnt;
    private int sendOct;
    private RateDetails sendOctDetails;
    private int sendPend;
    private boolean ssl;
    private String sslCipher;
    private String sslHash;
    private String sslKeyExchange;
    private String sslProtocol;
    private String state;
    private int timeout;
    private String type;
    private String user;
    private String userProvidedName;
    private String userWhoPerformedAction;
    private String vhost;

    @NoArgsConstructor
    @Data
    public static class ClientProperties {
        private Capabilities capabilities;
        private String connectionName;
        private String copyright;
        private String information;
        private String platform;
        private String product;
        private String version;

    }

    @NoArgsConstructor
    @Data
    public static class Capabilities {
        private boolean authenticationFailureClose;
        private boolean basicNack;
        private boolean connectionBlocked;
        private boolean consumerCancelNotify;
        private boolean exchangeExchangeBindings;
        private boolean publisherConfirms;

    }

    @NoArgsConstructor
    @Data
    public static class GarbageCollection {
        private int fullsweepAfter;
        private int maxHeapSize;
        private int minBinVheapSize;
        private int minHeapSize;
        private int minorGcs;

    }

    @NoArgsConstructor
    @Data
    public static class RateDetails {
        private double rate;

    }
}

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.net.InetAddress;
import java.util.List;

public class DnsTest {

    @SneakyThrows
    public void d() {
        SimpleResolver resolver = new SimpleResolver();
        resolver.setAddress(InetAddress.getByName("localhost"));
        resolver.setPort(53);
    }


    @SneakyThrows
    @Test
    public void a() {
        InetAddress[] addresses = InetAddress.getAllByName("npc.com.");
        for (InetAddress address : addresses) {
            System.out.println(address.getHostAddress());
        }
    }

    /**
     * 获得域名服务器
     */
    @SneakyThrows
    @Test
    public void parseNs() {
        // 查询域名的NS记录
        Name name = Name.fromString("google.com.");
        Record[] records = new Lookup(name, Type.NS).run();

        // 输出域名的服务器
        for (Record record : records) {
            if (record instanceof NSRecord nsRecord) {
                System.out.println(STR."域名服务器：\{nsRecord.getTarget()}");
            }
        }
    }

    /**
     * DNS记录查询
     * @formatter:on
     * DNS记录类型定义了资源记录（RR）的数据格式及其在DNS响应中的使用方式。以下是一些常见的DNS记录类型：
     * A记录 ：将域名映射到IPv4地址。
     * AAAA记录 ：将域名映射到IPv6地址。
     * CNAME记录 ：为域名创建别名。
     * MX记录 ：指定哪个主机负责接收发送到该域名的电子邮件。
     * NS记录 ：指定负责解析域名的权威DNS服务器的地址。
     * TXT记录 ：允许对一个域名添加任意文本说明，通常用于反垃圾邮件服务验证。
     * @formatter:off
     */
    @SneakyThrows
    @Test
    public void queryATest() {
        // 查询域名google.com的A记录
        Name name = Name.fromString("google.com.");
        Record[] records = new Lookup(name, Type.A).run();
        for (Record record : records) {
            if (record instanceof ARecord arecord) {
                System.out.println(STR."A record: \{arecord.getAddress().getHostAddress()}");
            }
        }
    }

    @SneakyThrows
    public void queryATest2(){
        // 创建查询器
        Resolver resolver = new ExtendedResolver();
        // 设置查询类型和域名
        int type = Type.A; // 例如，查询A记录
        Name name = Name.fromString("example.com."); // 查询的域名

//        // 执行自定义查询请求
//        Message response = resolver.send(Message.newQuery(Section.QUESTION, name, type));
//        // 解析查询结果
//        List<Record> answers = response.getSection(Section.ANSWER);
//        for (Record answer : answers) {
//            if (answer instanceof ARecord) {
//                ARecord arecord = (ARecord) answer;
//                System.out.println("Resolved IP address: " + arecord.getAddress().getHostAddress());
//            }
//        }
    }
}


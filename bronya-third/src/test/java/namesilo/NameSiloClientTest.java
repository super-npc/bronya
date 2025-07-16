package namesilo;

import java.util.List;

import org.junit.jupiter.api.Test;

import npc.bulinke.external.module.namesilo.NameSiloClient;
import npc.bulinke.external.module.namesilo.req.DnsAddRecordReq;
import npc.bulinke.external.module.namesilo.req.DnsBaseReq;
import npc.bulinke.external.module.namesilo.req.DnsDeleteRecordReq;
import npc.bulinke.external.module.namesilo.req.DnsUpdateRecordReq;
import npc.bulinke.external.module.namesilo.resp.DnsAddRecordResp;
import npc.bulinke.external.module.namesilo.resp.DnsDeleteRecordResp;
import npc.bulinke.external.module.namesilo.resp.DnsListRecordsResp;
import npc.bulinke.external.module.namesilo.resp.DnsUpdateRecordResp;
import npc.bulinke.external.module.namesilo.type.RrType;

public class NameSiloClientTest {
    private final String key = "fcec52df26a4609af757bb18";
    private final String domain = "weoxssfsjkxllkwjnsdfhaoio.top";
    NameSiloClient client = new NameSiloClient();

    @Test
    public void testDnsListRecords() {
        DnsListRecordsResp dnsListRecordsResp = client.dnsListRecords(new DnsBaseReq(key, domain));
        List<DnsListRecordsResp.Namesilo.Reply.ResourceRecord> resourceRecord =
            dnsListRecordsResp.getNamesilo().getReply().getResource_record();
        resourceRecord.forEach(System.out::println);
    }

    @Test
    public void testDnsAddRecord() {
        DnsAddRecordReq dnsAddRecordReq = new DnsAddRecordReq();
        dnsAddRecordReq.setDnsBase(new DnsBaseReq(key, domain));
        dnsAddRecordReq.setRrtype(RrType.A);
        dnsAddRecordReq.setRrhost("test");
        dnsAddRecordReq.setRrvalue("110.40.111.132");

        DnsAddRecordResp dnsAddRecordResp = client.dnsAddRecord(dnsAddRecordReq);
        System.out.println("dnsAddRecordResp = " + dnsAddRecordResp);
    }

    @Test
    public void testDnsUpdateRecord() {
        DnsListRecordsResp.Namesilo.Reply.ResourceRecord testRecord = this.getRecord();
        DnsUpdateRecordReq dnsUpdateRecordReq = new DnsUpdateRecordReq();
        dnsUpdateRecordReq.setDnsBase(new DnsBaseReq(key, domain));
        dnsUpdateRecordReq.setRrid(testRecord.getRecord_id());
        dnsUpdateRecordReq.setRrhost("test");
        dnsUpdateRecordReq.setRrvalue("110.40.222.144");

        DnsUpdateRecordResp dnsUpdateRecordResp = client.dnsUpdateRecord(dnsUpdateRecordReq);
        System.out.println("dnsUpdateRecordResp = " + dnsUpdateRecordResp);
    }

    @Test
    public void testDnsDeleteRecord() {
        DnsListRecordsResp.Namesilo.Reply.ResourceRecord testRecord = this.getRecord();

        DnsDeleteRecordReq dnsDeleteRecordReq = new DnsDeleteRecordReq();
        dnsDeleteRecordReq.setDnsBase(new DnsBaseReq(key, domain));
        dnsDeleteRecordReq.setRrid(testRecord.getRecord_id());

        DnsDeleteRecordResp resp = client.dnsDeleteRecord(dnsDeleteRecordReq);
        System.out.println("dnsAddRecordResp = " + resp);
    }

    private DnsListRecordsResp.Namesilo.Reply.ResourceRecord getRecord() {
        DnsListRecordsResp dnsListRecordsResp = client.dnsListRecords(new DnsBaseReq(key, domain));
        List<DnsListRecordsResp.Namesilo.Reply.ResourceRecord> resourceRecord =
            dnsListRecordsResp.getNamesilo().getReply().getResource_record();

        DnsListRecordsResp.Namesilo.Reply.ResourceRecord testRecord = resourceRecord.stream()
            .filter(resourceRecord1 -> resourceRecord1.getHost().equals("test.weoxssfsjkxllkwjnsdfhaoio.top"))
            .findFirst().get();
        System.out.println("要修改的记录 = " + testRecord);
        return testRecord;
    }
}
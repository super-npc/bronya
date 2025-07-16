package npc.bulinke.external.module.namesilo;

import bronya.shared.module.rpc.ApiClient;
import npc.bulinke.external.module.namesilo.req.DnsAddRecordReq;
import npc.bulinke.external.module.namesilo.req.DnsBaseReq;
import npc.bulinke.external.module.namesilo.req.DnsDeleteRecordReq;
import npc.bulinke.external.module.namesilo.req.DnsUpdateRecordReq;
import npc.bulinke.external.module.namesilo.resp.DnsAddRecordResp;
import npc.bulinke.external.module.namesilo.resp.DnsDeleteRecordResp;
import npc.bulinke.external.module.namesilo.resp.DnsListRecordsResp;
import npc.bulinke.external.module.namesilo.resp.DnsUpdateRecordResp;
import npc.bulinke.external.module.namesilo.type.RrType;
import okhttp3.ResponseBody;
import org.dromara.hutool.json.JSONUtil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NameSiloClient {

    // https://www.namesilo.com/api/dnsListRecords?version=1&type=xml&key=fcec52df26a4609af757bb18&domain=weoxssfsjkxllkwjnsdfhaoio.top

    private final NameSiloApi api;

    public NameSiloClient() {
        this.api = new ApiClient("https://www.namesilo.com/").createService(NameSiloApi.class);
    }

    public interface NameSiloApi {
        @GET("/api/dnsListRecords?version=1&type=xml")
        Call<ResponseBody> dnsListRecords(@Query("key") String key, @Query("domain") String domain);

        @GET("/api/dnsAddRecord?version=1&type=xml")
        Call<ResponseBody> dnsAddRecord(@Query("key") String key, @Query("domain") String domain,
            @Query("rrtype") RrType rrtype, @Query("rrhost") String rrhost, @Query("rrvalue") String rrvalue);

        @GET("/api/dnsUpdateRecord?version=1&type=xml")
        Call<ResponseBody> dnsUpdateRecord(@Query("key") String key, @Query("domain") String domain,
            @Query("rrid") String rrid, @Query("rrhost") String rrhost, @Query("rrvalue") String rrvalue);

        @GET("/api/dnsDeleteRecord?version=1&type=xml")
        Call<ResponseBody> dnsDeleteRecord(@Query("key") String key, @Query("domain") String domain,
            @Query("rrid") String rrid);

    }

    public DnsListRecordsResp dnsListRecords(DnsBaseReq req) {
        String xml = ApiClient.executeSync(api.dnsListRecords(req.getKey(), req.getDomain()), String.class);
        return JSONUtil.parseFromXml(xml).toBean(DnsListRecordsResp.class);
    }

    public DnsAddRecordResp dnsAddRecord(DnsAddRecordReq req) {
        DnsBaseReq dnsBase = req.getDnsBase();
        String xml = ApiClient.executeSync(
            api.dnsAddRecord(dnsBase.getKey(), dnsBase.getDomain(), req.getRrtype(), req.getRrhost(), req.getRrvalue()),
            String.class);
        return JSONUtil.parseFromXml(xml).toBean(DnsAddRecordResp.class);
    }

    public DnsUpdateRecordResp dnsUpdateRecord(DnsUpdateRecordReq req) {
        DnsBaseReq dnsBase = req.getDnsBase();
        String xml = ApiClient.executeSync(api.dnsUpdateRecord(dnsBase.getKey(), dnsBase.getDomain(), req.getRrid(),
            req.getRrhost(), req.getRrvalue()), String.class);
        return JSONUtil.parseFromXml(xml).toBean(DnsUpdateRecordResp.class);
    }

    public DnsDeleteRecordResp dnsDeleteRecord(DnsDeleteRecordReq req) {
        DnsBaseReq dnsBase = req.getDnsBase();
        String xml = ApiClient.executeSync(api.dnsDeleteRecord(dnsBase.getKey(), dnsBase.getDomain(), req.getRrid()),
            String.class);
        return JSONUtil.parseFromXml(xml).toBean(DnsDeleteRecordResp.class);
    }

}

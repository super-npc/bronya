package module.feishu;

import com.google.common.collect.Lists;
import npc.bulinke.external.module.feishu.FeishuResp;
import npc.bulinke.external.module.feishu.FeishuRotClient;
import npc.bulinke.external.module.feishu.req.MarkdownReq;
import npc.bulinke.external.module.feishu.req.TextReq;
import org.dromara.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;

public class FeishuRotClientTest {
    FeishuRotClient feishuRotClient =
        new FeishuRotClient("2924ad1b-28f9-4792-a1db-4198cd5fbe92", "LnfMY3gHRoZJcZsfhgpNLg");

    @Test
    public void sendText() {
        TextReq textReq = new TextReq(true,STR."调试 -  \{DateUtil.formatDateTime(new java.util.Date())}");
        FeishuResp feishuResp = feishuRotClient.sendText(textReq);
        System.out.println("feishuResp = " + feishuResp);
    }

    @Test
    public void markdown() {
        MarkdownReq markdownReq = new MarkdownReq("标题1", Lists.newArrayList(new MarkdownReq.CardDTO.Elements("- 多发点")));
        FeishuResp feishuResp = feishuRotClient.sendMarkdownReq(markdownReq);
        System.out.println("feishuResp = " + feishuResp);
    }
}
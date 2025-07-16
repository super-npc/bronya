package bronya.admin.rabbit.mqtt.core.req.server;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;

import bronya.admin.rabbit.mqtt.core.req.MsgBody;
import bronya.admin.rabbit.mqtt.core.util.MsgBodyUtil;

class SubscribesUpdateTest {

    @Test
    public void testJson() {
        MsgBody<SubscribesUpdate> subscribesUpdateMsgBody = new MsgBody<>();
        subscribesUpdateMsgBody.setDeviceId("1111");
        subscribesUpdateMsgBody.setData(new SubscribesUpdate(Lists.newArrayList("topic1", "topic2")));
        String jsonString = JSONObject.toJSONString(subscribesUpdateMsgBody);
        System.out.println("jsonString = " + jsonString);

        // JSON 转回对象
        MsgBody<SubscribesUpdate> subscribesUpdateMsgBody2 = MsgBodyUtil.parse(jsonString, SubscribesUpdate.class);
        System.out.println("subscribesUpdateMsgBody2 = " + subscribesUpdateMsgBody2.getDeviceId());
        SubscribesUpdate data = subscribesUpdateMsgBody2.getData();
        System.out.println("data.getTopics() = " + data.getTopics().getFirst());
    }
}
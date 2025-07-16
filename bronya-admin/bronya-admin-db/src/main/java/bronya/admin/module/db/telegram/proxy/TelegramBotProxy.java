package bronya.admin.module.db.telegram.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.telegram.domain.TelegramBot;
import bronya.admin.module.db.telegram.domain.TelegramBot.TelegramBotExt;
import bronya.admin.module.db.telegram.repository.TelegramBotRepository;
import bronya.admin.module.db.telegram.service.TelegramBotService;
import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import bronya.core.base.dto.DataProxy;
import bronya.shared.module.util.Md;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.text.quote.Quote;
import npc.bulinke.external.module.telegram.TelegramBotCli;
import npc.bulinke.external.module.telegram.resp.GetMeResp;
import npc.bulinke.external.module.telegram.resp.TelegramRespBase;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotProxy extends DataProxy<TelegramBot> {
    private final TelegramBotRepository telegramBotRepository;
    private final ProxyDoRepository proxyDoRepository;
    private final TelegramBotService telegramBotService;

    @Override
    public TelegramBot beforeAdd(TelegramBot telegramBot) {
        TelegramRespBase<GetMeResp> getMeResp = this.call(telegramBot);
        Assert.notNull(getMeResp, "外网网络异常");
        GetMeResp result = getMeResp.getResult();
        telegramBot.setId(result.getId());
        telegramBot.setFirstName(result.getFirstName());
        telegramBot.setUsername(result.getUsername());
        return super.beforeAdd(telegramBot);
    }

    @Override
    public void table(Map<String, Object> map) {
        TelegramBot telegramBot = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), TelegramBot.class);
        TelegramBotExt telegramBotExt = new TelegramBotExt();
        telegramBotExt.setBotGetMeInfo(this.printGetMe(telegramBot));
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(TelegramBot.class, telegramBotExt));
    }

    private TelegramRespBase<GetMeResp> call(TelegramBot telegramBot) {
        TelegramRespBase<GetMeResp> getMeResp = null;
        try {
            TelegramBotCli telegramBotCli = telegramBotService.getCli(telegramBot);
            getMeResp = telegramBotCli.getMe(telegramBot.getToken());
        } catch (Exception e) {
            return null;
        }
        return getMeResp;
    }


    private String printGetMe(TelegramBot telegramBot) {
        Md md = new Md();
        md.appendLn(new Quote("Get Me"));
        TelegramRespBase<GetMeResp> getMeResp = this.call(telegramBot);
        if (getMeResp == null) {
            md.appendLn(" - 访问异常");
            return md.toString();
        }

        md.appendLn(STR." - 状态:\{Md.emojiBool(getMeResp.getOk())}");
        if (getMeResp.getOk()) {
            GetMeResp result = getMeResp.getResult();
            md.appendLn(STR." - 是否bot: \{Md.emojiBool(result.getIsBot())}");
            md.appendLn(STR." - id: \{result.getId()}");
            md.appendLn(STR." - firstName: \{result.getFirstName()}");
            md.appendLn(STR." - username: \{result.getUsername()}");
            md.appendLn(STR." - 允许加群: \{Md.emojiBool(result.getCanJoinGroups())}");
            md.appendLn(STR." - 隐私模式: \{Md.emojiBool(result.getCanReadAllGroupMessages())}");
            md.appendLn(STR." - 支持内联查询: \{Md.emojiBool(result.getSupportsInlineQueries())}");
            md.appendLn(STR." - canConnectToBusiness: \{Md.emojiBool(result.getCanConnectToBusiness())}");
            md.appendLn(STR." - hasMainWebApp: \{Md.emojiBool(result.getHasMainWebApp())}");
        } else {
            md.appendLn(STR." - code:\{getMeResp.getErrorCode()}");
            md.appendLn(STR." - 备注:\{getMeResp.getDescription()}");
        }
        return md.toString();
    }
}

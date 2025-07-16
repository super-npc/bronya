//package bronya.admin.module.scheduled.service.biz;
//
//import java.util.Date;
//
//import npc.bulinke.commons.type.StatusType;
//import npc.bulinke.module.cron.domian.Cron;
//import npc.bulinke.module.cron.domian.CronLog;
//import npc.bulinke.module.cron.dto.CronHandler;
//import npc.bulinke.module.cron.repository.CronLogRepository;
//import npc.bulinke.module.cron.repository.CronRepository;
//import org.springframework.stereotype.Component;
//
//import cn.hutool.core.date.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 定时器增强
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//// @Conditional(value = JobCondition.class)
//public class TaskStrong {
//    private final CronLogRepository cronLogRepo;
//    private final CronRepository cronRepo;
//
//    public CronLog before(boolean recordLog, String name, CronHandler handler) {
//        if (recordLog) {
//            Cron cron = cronRepo.findByName(name);
//            CronLog cronLog = new CronLog();
//            cronLog.setStatus(StatusType.CREATED);
//            cronLog.setScheduledName(name);
//            cronLog.setCron(cron);
//            return cronLogRepo.save(cronLog);
//        }
//        return null;
//    }
//
//    public void after(CronLog cronLog, String execRes) {
//        this.updateCronLog(cronLog, StatusType.SUCCESS);
//    }
//
//    public void exception(String name, CronLog cronLog, Exception e) {
//        log.error(STR."任务执行异常:\{name}", e);
//        this.updateCronLog(cronLog, StatusType.FAIL);
//    }
//
//    public void afterFinally(CronLog cronLog) {
//
//    }
//
//    private void updateCronLog(CronLog cronLog, StatusType statusProgress) {
//        if (cronLog != null) {
//            Date createTime = cronLog.getCreateTime();
//            String interval = DateUtil.formatBetween(createTime, new Date());
//            cronLog.setIntervalTime(interval);
//            cronLog.setStatus(statusProgress);
//            cronLogRepo.save(cronLog);
//        }
//    }
//}

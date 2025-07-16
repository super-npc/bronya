package bronya.admin.redis;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/**
 * 最后送上血泪教训： 永远不要在生产环境用RTransaction！ （除非你想体验"转账转一半系统挂了，钱去哪了"的哲学问题）
 */
public class BronyaAdminRedisBaseTest {
    RedissonClient client;

    @BeforeEach
    public void start() {
        Config config = new Config();
        // 单机模式配置
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0); // 如果需要指定数据库
        client = Redisson.create(config);
    }

    @AfterEach
    public void end() {
        client.shutdown();
    }

    @Test
    public void limiter() {
        RRateLimiter limiter = client.getRateLimiter("防喷限流器");
        limiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS); // 每秒最多10次请求
        if (limiter.tryAcquire()) {
            // 允许进入，否则弹回"你太快了"警告
        }

    }

    @Test
    public void bloom() {
        RBloomFilter<String> bloomFilter = client.getBloomFilter("海王识别器");
        bloomFilter.tryInit(100000L, 0.03); // 能识别10万海王，误差率3%
        bloomFilter.add("18888888888"); // 自动同步到所有Redis节点

        boolean contains = bloomFilter.contains("18888888881");
        System.out.println("contains = " + contains);
    }

    @Test
    public void map() {
        RMap<String, Object> map = client.getMap("舔狗日记");
        map.put("2023-08-10", "她今天又没回我微信，一定是手机没电了"); // 自动分布式存储

        // map.put("user", new User()); // 报错！Redis不是你家硬盘！
        // 正确姿势：请配置Codec或使用JSON序列化
    }

    @Test
    public void lock() {
        RLock lock = client.getLock("厕所排队锁"); // 分布式锁の奥义
        lock.lock(10, TimeUnit.SECONDS);
        try {
            // 此处应有带薪拉屎代码
            System.out.println("lock = " + lock);
        } finally {
            lock.unlock(); // 占坑不拉小心便秘警告！
        }

    }

    @Test
    public void bucket() {
        RBucket<String> bucket = client.getBucket("肥宅快乐餐");
        bucket.set("麦乐鸡块+大薯+冰阔落"); // 这操作比外卖App下单还丝滑

        String s = bucket.get();
        System.out.println("s = " + s);
    }

    @Test
    public void delAll() {
        client.getKeys().flushdb(); // 这一行能让你从程序员转行送外卖
    }
}
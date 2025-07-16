package bronya.shared.module.common.constant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminBaseConstant {
    Map< String, Class<?>> AMIS_TABLES = Maps.newHashMap();
    String TRACE_ID = "TRACE_ID";
    String BODY_TIME = "BODY_TIME";
    String BODY_SIGN = "BODY_SIGN";
    String LOGIN_REQUIRED = "LOGIN_REQUIRED";

    Set<Class<?>> CLASSES_THREAD_POOLS = Sets.newHashSet();

    Set<Class<?>> CLASSES_AMIS_ENV = Sets.newHashSet();

    Set<Class<?>> CLASSES_RABBIT_RPC = Sets.newHashSet();

    Map<Class<?>,Integer> BADGE_COUNT = Maps.newHashMap();



    List<Class<? extends Number>> numberClass =
            Lists.newArrayList(Long.class, Integer.class, Float.class, Double.class, BigDecimal.class);

    List<Class<?>> dateClass = Lists.newArrayList(LocalDateTime.class, Date.class, LocalDate.class);
}

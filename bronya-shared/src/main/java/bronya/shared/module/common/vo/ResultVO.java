package bronya.shared.module.common.vo;

import bronya.shared.module.common.type.CodeEnum;
import com.google.common.collect.Maps;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Collection;
import java.util.Map;

/**
 * 统一返回结果
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@FieldNameConstants
public class ResultVO<T> {
    /**
     * 请求状态
     */
    private Integer status;

    /**
     * 请求返回信息描述或异常信息
     */
    private String msg;
    /**
     * 接口请求返回业务对象数据
     */
    private T data;

    private DataDetail dataDetail;

    private Map<String, Object> cookies = Maps.newHashMap();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataDetail {
        private String beanType;
        private Boolean isArray;
    }

    public ResultVO() {}

    /**
     * 请求成功，对返回结果进行封装
     */
    public static ResultVO<?> success(Object data) {
        return build(CodeEnum.SUCCESS.status, CodeEnum.SUCCESS.msg, data);
    }

    /**
     * 请求失败，对返回结果进行封装
     */
    public static ResultVO<?> failed(String message) {
        return build(CodeEnum.FAIL.status, message, null);
    }

    /**
     * 返回结果统一封装
     */
    private static ResultVO<?> build(int code, String message, Object data) {
        DataDetail dataDetail = null;
        if (data != null) {
            Class<?> aClass = data.getClass();
            dataDetail = new DataDetail(aClass.getName(), data instanceof Collection);
        }
        return ResultVO.builder().status(code).msg(message).data(data).dataDetail(dataDetail).build();
    }
}

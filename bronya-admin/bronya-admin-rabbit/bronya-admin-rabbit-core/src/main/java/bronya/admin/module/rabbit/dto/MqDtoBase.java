package bronya.admin.module.rabbit.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MqDtoBase<T> {
    private T data;
    // 收到才设置
    private Date receiveTime;
}

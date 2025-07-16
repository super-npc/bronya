package bronya.admin.module.media.controller.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResp {
    private String value; // 跟url一致
    private String url; //
    private String filename;
}

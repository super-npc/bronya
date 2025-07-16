package bronya.admin.module.db.amis.service.field.converter.impl;

import java.util.Date;

import org.dromara.hutool.core.convert.ConvertUtil;

import bronya.admin.module.db.amis.service.field.converter.IAmisFiledConvert;

public class AmisFieldDateConvert implements IAmisFiledConvert {

    @Override
    public Object request(Object value){
        if(value == null){
            return null;
        }
        long timestamp = ConvertUtil.toLong(value) * 1000L;
        return new Date(timestamp);
    }

    public void response(){

    }
}

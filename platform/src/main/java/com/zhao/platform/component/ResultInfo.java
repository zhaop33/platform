package com.zhao.platform.component;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyanjiang-pc
 */
@NoArgsConstructor
@Data
public class ResultInfo {

    private Integer code;

    private Map<String,Object> data = new HashMap<>(2);

    public ResultInfo setCode(Integer code){
        this.code = code;
        return this;
    }

    public ResultInfo setSingleData(Object data){
        this.data.put("result",data);
        return this;
    }

    public ResultInfo addMutileData(String key,Object data){
        this.data.put(key,data);
        return this;
    }

    public static ResultInfo getInstance(){
        ResultInfo info = new ResultInfo();
        info.setCode(HttpStatus.OK.value());
        return info;
    }
}

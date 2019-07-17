package com.zhao.platform.cache;

import com.zhao.platform.configuration.ThreadConfig;
import com.zhao.platform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author zhaoyanjiang-pc
 */
@Slf4j
public class InvalidTokenCache {

    private final static Set<String> invalidTokens = new CopyOnWriteArraySet<>();
    public static void invalid(String token){
        if(JwtUtils.verifyJwt(token).getExpiration().after(new Date())){
            ThreadConfig.getInstance().execute(InvalidTokenCache::removeExperToken);
            invalidTokens.add(token);
            log.info("大小是：{}",invalidTokens.size());
        }
    }

    public static boolean isInvalid(String token){
        return invalidTokens.contains(token);
    }

    private static void removeExperToken(){
        Date date = new Date();
        List<String> inviteTokens = invalidTokens.stream().filter(s -> JwtUtils.verifyJwt(s).getExpiration().before(date)).collect(Collectors.toList());
        log.info("将要删除：" + inviteTokens);
        invalidTokens.removeAll(inviteTokens);
    }

    public static void test() throws InterruptedException {
        int i = 0;
        while (true){
            String token = JwtUtils.createJWT("user" + i++);
            log.info("token:{}",token);
            invalid(token);
        }
    }
}

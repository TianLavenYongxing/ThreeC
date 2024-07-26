package com.threec.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.threec.constant.RedisConstant;
import com.threec.dao.SysUserDao;
import com.threec.dto.AuthenticationUserDTO;
import com.threec.dto.SmsAuthenticationRequestDTO;
import com.threec.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);
    @Value("${application.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${application.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${application.sms.regionId}")
    private String regionId;

    private final SysUserDao sysUserDao;

    public boolean getSMSCaptcha(SmsAuthenticationRequestDTO dto){
        // todo 验证码登陆 首先判断手机号是否存在，然后在执行 校验 5分钟内只能发送3次, 查询登陆错误次数，连续验证码输入错误3次 锁定账号1小时
        AuthenticationUserDTO byPhoneNumber = sysUserDao.findByPhoneNumber(dto.getPhoneNumber());
        if(Objects.nonNull(byPhoneNumber)){
            SendSMSCaptcha(byPhoneNumber);
            return true;
        }
        return false;
    }

    public boolean SendSMSCaptcha(AuthenticationUserDTO dto) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        Random random = new Random();
        String code = Integer.toString(random.nextInt(10)) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("ThreeC");
        request.setTemplateCode("SMS_20240101");
        request.setPhoneNumbers(dto.getPhoneNumber());
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            if ("OK".equals(response.getCode())) {
                RedisUtils.StringOps.setEx(RedisConstant.USER_SMS + dto.getPhoneNumber(), code, 2, TimeUnit.MINUTES);
                return true;
            }
        } catch (ServerException e) {
            log.error("发送短信出现异常:{},{}", "服务端异常", e.getMessage());
            return false;
        } catch (ClientException e) {
            log.error("发送短信出现异常:{},{}", "客户端异常", e.getMessage());
            return false;
        }
        return false;
    }
}

package com.threec.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);
    @Value("${application.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${application.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${application.sms.regionId}")
    private String regionId;

    public boolean SendSMSCaptcha() {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        Random random = new Random();
        String code = Integer.toString(random.nextInt(10)) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("ThreeC");
        request.setTemplateCode("SMS_20240101");
        request.setPhoneNumbers("13300000063");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            if ("OK".equals(response.getCode())) {
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

package com.keller.common.util;

import com.keller.common.config.Constants;

import com.keller.common.http.RequestUtil;
import com.keller.common.http.ResponseUtils;
import com.keller.common.http.ResultData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ErrorLogUtils {

    private  RabbitTemplate rabbitTemplate;

    private ErrorLogUtils(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    private static ErrorLogUtils instance = null;
    public static ErrorLogUtils getInstance(RabbitTemplate rabbitTemplate){
        if(instance == null){
            synchronized (ErrorLogUtils.class){
                if(instance == null){
                    instance = new ErrorLogUtils(rabbitTemplate);
                }
            }
        }
        return instance;
    }

    /**
     * 请求处理异常
     */
    public static final String REQUEST_ERROR = "Request";
    /**
     * 系统异常错误
     */
    public static final String SYSTEM_ERROR = "System";
    /**
     * 非法访问
     */
    public static final String ACCESS_ERROR = "Access";


    /**
     * 记录错误请求日志
     *
     * @param ip
     * @param method
     * @param url
     * @param params
     * @param response
     */
    public void requestLog(String ip, String method, String url, Map<String,Object> params, ResponseEntity response){
        ResultData resultData = ResponseUtils.getResultData(response);
        if(resultData == null || resultData.getSuccess() == 0){
            Console.info("http requestSuccessLog",ip,method,url,params,response.getBody());
            return;
        }
        Console.info("http requestErrorLog",ip,method,url,params,response.getBody());
    }

    /**
     * 记录系统错误日志
     * 1. 打印错误堆栈
     * 2. 异常日志入库
     *      只记录项目包中的异常堆栈，不记录JVM 的异常堆栈
     *
     * @param e
     */
    public void sendErrorLog(Exception e){
        e.printStackTrace();
        // 获取项目包名，只记录项目代码中的异常信息，不记录JVM的异常
        String packageName = Constants.packageName;
        // 遍历异常
        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder builder = new StringBuilder();

        builder.append(e.toString()).append(":");
        for(StackTraceElement element:elements){
            if(element.toString().startsWith(packageName)){
                builder.append(element.toString()).append(",");
            }
        }
        Console.info("sendErrorLog",builder.toString());
    }

    /**
     *
     * @param request
     */
    public static void getAccessErrorLog(HttpServletRequest request){
        Console.info("getAccessErrorLog",RequestUtil.getRealIp(request),request.getRequestURI(),request.getMethod());
    }

}

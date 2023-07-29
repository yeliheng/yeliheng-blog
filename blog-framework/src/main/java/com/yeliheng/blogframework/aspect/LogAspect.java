package com.yeliheng.blogframework.aspect;

import com.alibaba.fastjson.JSON;
import com.yeliheng.blogcommon.exception.ApiException;
import com.yeliheng.blogcommon.utils.BeanUtils;
import com.yeliheng.blogcommon.utils.IpAddressUtils;
import com.yeliheng.blogcommon.utils.ServletUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogsystem.domain.LoginUser;
import com.yeliheng.blogsystem.domain.OperateLog;
import com.yeliheng.blogsystem.service.IOperateLogService;
import com.yeliheng.blogsystem.utils.TokenUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Component
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.yeliheng.blogcommon.annotation.Log)")
    public void pointcut() {}


    @AfterReturning(pointcut = "pointcut()", returning = "jsonResponse")
    public void doReturningLogRecord(JoinPoint joinPoint, Object jsonResponse) {
        recordLog(joinPoint,null,jsonResponse);
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void doThrowingLogRecord(JoinPoint joinPoint, ApiException e) {
        recordLog(joinPoint,e,null);
    }

    private void recordLog(JoinPoint joinPoint, ApiException e, Object jsonResponse) {
        Log log = getAnnotation(joinPoint);
        if(log == null) {
            return;
        }
        HttpServletRequest request = ServletUtils.getRequest();
        LoginUser loginUser = BeanUtils.getBean(TokenUtils.class).getLoginUser(request);
        OperateLog operateLog = new OperateLog();
        //用户Id
        operateLog.setUserId(loginUser.getUser().getId());
        //用户名
        operateLog.setUsername(loginUser.getUsername());
        //模块名称
        operateLog.setModuleName(log.moduleName());
        //操作类型
        operateLog.setOperateType(log.operateType());
        //Ip地址
        operateLog.setIp(IpAddressUtils.getIpAddr(request));
        //操作的地址
        operateLog.setUrl(request.getRequestURI());
        //请求方法
        operateLog.setMethod(request.getMethod());
        //具体函数名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operateLog.setFunction(className + "." + methodName + "()");
        //请求参数
        String params = parseParams(joinPoint.getArgs());
        operateLog.setParam(StringUtils.substring(params,0,254));
        operateLog.setResult(JSON.toJSONString(jsonResponse));
        if(e != null) {
            operateLog.setStatus("0");
            operateLog.setErrorDetail(StringEscapeUtils.unescapeJson(e.toString()));
        }else {
            operateLog.setStatus("1");
        }

        //写入数据库
        BeanUtils.getBean(IOperateLogService.class).insertLog(operateLog);


    }

    /**
     * 通过反射获取注解
     */
    private Log getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }


    /**
     * 解析传过来的参数并返回字符串
     * @param paramsArray 参数数组
     * @return json字符串
     */
    private String parseParams(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (int i = 0; i < paramsArray.length; i++)
            {
                if (StringUtils.isNotNull(paramsArray[i]) && !isMultipartFileObject(paramsArray[i]))
                {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }


    /**
     * 过滤文件请求
     *
     * @param o 对象信息
     * @return 如果是文件，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isMultipartFileObject(final Object o)
    {
        Class<?> cls = o.getClass();
        if (cls.isArray())
        {
            return cls.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(cls))
        {
            Collection collection = (Collection) o;
            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                return iter.next() instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(cls))
        {
            Map map = (Map) o;
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();)
            {
                Map.Entry entry = (Map.Entry) iter.next();
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}

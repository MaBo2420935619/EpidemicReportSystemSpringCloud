package com.mabo.xxljob.handler;

import com.mabo.config.XXLJobConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XXLJobHandler {
    private static   ReturnT<String>  success = ReturnT.SUCCESS;
    private static   ReturnT<String>  fails = ReturnT.FAIL;
    private Logger log = LoggerFactory.getLogger(XXLJobHandler.class);

    public void start() {
        log.info("start");
    }

    public void destroy() {
        log.info("destroy");
    }
    /**
     * @Author mabo
     * @Description   查看当前学生是否已经上报，否则发送邮件
     */

    @XxlJob(value = "isReport",init = "start",destroy = "destroy")
    public void isReport()throws Exception{
        log.info("执行成功");
        // 获取xxjob传入的参数
        String param = XxlJobHelper.getJobParam();
        XxlJobHelper.handleSuccess("调用的参数为"+param);
        log.info("定时任务执行成功"+param);
    }
}


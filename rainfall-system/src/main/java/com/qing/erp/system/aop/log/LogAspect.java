package com.qing.erp.system.aop.log;

import com.qing.erp.common.data.E;
import com.qing.erp.common.data.R;
import com.qing.erp.common.json.JsonUtil;
import com.qing.erp.common.web.ServletUtil;
import com.qing.erp.common.time.Timer;
import com.qing.erp.common.web.SpringMVCUtil;
import com.qing.erp.common.web.UserAgentParser;
import com.qing.erp.system.pojo.LogsEntity;
import com.qing.erp.system.service.LogsImpl;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
/*
通知（Advice）          包含了需要用于多个应用对象的横切行为,完全听不懂,没关系,通俗一点说就是定义了“什么时候”和“做什么”。
连接点（Join Point）     是程序执行过程中能够应用通知的所有点。
切点（Poincut）         是定义了在“什么地方”进行切入,哪些连接点会得到通知。显然,切点一定是连接点。
切面（Aspect）          是通知和切点的结合。通知和切点共同定义了切面的全部内容——是什么,何时,何地完成功能。
引入（Introduction）    允许我们向现有的类中添加新方法或者属性。
织入（Weaving）         是把切面应用到目标对象并创建新的代理对象的过程,分为编译期织入、类加载期织入和运行期织入。

由于Spring切面粒度最小是达到方法级别,而execution表达式可以用于明确指定方法返回类型,类名,方法名和参数名等与方法相关的部件
并且实际中,大部分需要使用AOP的业务场景也只需要达到方法级别即可
因而execution表达式的使用是最为广泛的, 语法:
    execution ( * com.qing.example.test.*.*(..))
    execution = 在方法执行时触发
    * = 返回任意类型
    com.qing.example.test = 包路径
    .* = 任意类
    .* = 任意方法
    (..) = 任意参数


@Pointcut        切入点
@Before          前置通知
@AfterReturning  后置通知
@AfterThrowing   后置异常通知
@After           后置最终通知
@Around          环绕通知

执行顺序:
    环绕通知(前)
    前置通知
    方法本体
    环绕通知(后)
    后置通知


JoinPoint
    joinPoint.getTarget();  // 被代理的对象
    joinPoint.getArgs();    // 参数
    joinPoint.getKind();    // 切入的类型

// ProceedingJoinPoint 继承了 joinPoint


 */

@Aspect
@Component
public class LogAspect {
    @Autowired
    LogsImpl logsImpl;

    // execution([可见性] [返回类型] [方法名].*.*(参数))
    @Pointcut("execution(public * com.qing.erp.system.controller.*.*(..))")
    public void pointCutAll(){}

    // 设置操作日志切入点(在注解的位置切入代码)
    @Pointcut("@annotation(com.qing.erp.system.aop.log.RLog)")
    public void pointCut() {}

    // 方法执行前
    @Before("pointCut()")
    public void test(JoinPoint joinPoint){}

    // 方法执行后
    @AfterReturning(returning = "result", value = "pointCut()")
    public void saveLog(JoinPoint joinPoint, Object result) throws Throwable {}

    // 方法执行后报错
    @AfterThrowing("pointCut()")
    public void testAfterThrowing() {}

    // 方法最终执行后
    @After("pointCut()")
    public void testAfter() {}

    @Around("pointCut()")
    public Object testAround(ProceedingJoinPoint pjp) {
        // 获取request
        HttpServletRequest request = SpringMVCUtil.getReq();

        // 开始计时
        val timer = Timer.timer();

        // 执行结果并强转为方法返回值类型 (也可以用Map, 被切入方法返回的是什么, 这里就强转成什么)
        R res = null;
        try {
            res = (R) pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // 结束计时
        val runTime = timer.stop();

        // 从切面织入点处, 通过反射机制获取Signature(修饰符 + 包名 + 类名 + 方法名)
        // 再从中获取织入点处的方法/方法的注解信息 (也就是被切入的方法)
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        String methodName = signature.getName();
        String className = signature.getDeclaringType().getSimpleName();
        String packageName = signature.getDeclaringType().getPackage().getName();
        String methodChain = packageName + "." + className + "." + methodName;

        Method method = signature.getMethod();
        RLog annotation = method.getAnnotation(RLog.class);
        if (annotation == null) { throw new E("事故原因: 切面失败"); }

        String userAgent = request.getHeader("User-Agent");

        val log = new LogsEntity();
        log.setUserId(null);

        log.setReqUri(request.getRequestURI());

        log.setReqMethod(request.getMethod());

        val parameter = ServletUtil.parameterToString(request);
        log.setReqParameter(parameter.length() > 200 ? "参数过长" : parameter);

        val ip = ServletUtil.getIp(request);
        log.setIp(ip);
        log.setCity(null);
        log.setEquipment(UserAgentParser.parse(userAgent).toString());

        log.setModule(annotation.module());
        log.setNotes(annotation.notes());
        log.setMethod(methodChain);
        log.setSpendTime(runTime);
        log.setStatus(res != null);

        log.setCreatedAt(new Date());
        log.setUpdatedAt(log.getCreatedAt());

        log.setResult(res == null ? "null" : res.toString());

        logsImpl.add(log);
        return res;
    }

}
/*
简单来说就是unity官方对vr有两套东西

openvr          openxr（新的）
这俩玩意不能同时用。

之前用的是openvr, 可以通过读设备列表读到人身上的追踪器（tracker）
现在用的是openxr

openxr不支持从设备列表读tracker, 只能通过一系列很麻烦的操作从steamvr拉到它的htcvive
(通过在unity里调cpp的接口)

测试的设备用了另一套基于openvr协议, 导致读不到。

也就是现在得用unity调xr的cpp接口, 或者改它放出来的cpp源码再编译成unity官方插件

然后就导致又回归到给unity改底层这个阶段了, 但是cpp我又不熟。

或者我得想办法调用起来并且成功拿到它的cpp方法, 但是这玩意的文档页数和代码是以千为单位的, 眼花缭乱。
 */
package org.datacollector.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.datacollector.db.model.ResponseListModel;
import org.datacollector.db.model.ResponseModel;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseWrapperAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void anyRestControllerPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void anyMethodPointcut() {}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Around(value = "anyRestControllerPointcut() && anyMethodPointcut()")
    public Object wrapResponse(ProceedingJoinPoint response) throws Throwable {
    	System.out.println(response.getSignature());
    	System.out.println("Wrapping response...");
    	Object obj = null;
    	try {
    		obj = response.proceed();
    	} catch (Exception e) {
    		return new ResponseModel<String>(false, "", e.getMessage());
    	}

    	if(obj instanceof List) {
    		return new ResponseListModel(true, (List)obj, "");
    	} else {
    		return new ResponseModel<Object>(true, obj, "");
    	}        
    }
}

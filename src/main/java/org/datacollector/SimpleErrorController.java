package org.datacollector;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {

  private final ErrorAttributes errorAttributes;

  @Autowired
  public SimpleErrorController(ErrorAttributes errorAttributes) {
    Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping
  public Object error(HttpServletRequest aRequest) throws Exception {
    Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
    throw new Exception(body.get("message").toString());
  }

  private boolean getTraceParameter(HttpServletRequest request) {
    String parameter = request.getParameter("trace");
    if (parameter == null) {
        return false;
    }
    return !"false".equals(parameter.toLowerCase());
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
    RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
    return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
  }
}
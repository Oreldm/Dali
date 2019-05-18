package Controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class RequestCountMonitoringConfiguration  extends WebMvcConfigurerAdapter {
	  @Autowired
	  private SystemInfoController sys;
	  
	  private static int i=0; //used to not do the same response twice
	  
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorAdapter() {
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				if(response.getStatus()!=200 && i%2==0) {
					sys.updateHttpResponses(1, 1);
					i++;
				}else if(response.getStatus()!=200 && i%2==1){
					System.out.println("Heres i value : "+i);
					i++;
				}else {
					sys.updateHttpResponses(0, 1);
				}
			}
		});
	}
}

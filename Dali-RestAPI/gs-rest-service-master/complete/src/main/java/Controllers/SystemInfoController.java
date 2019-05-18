package Controllers;

import java.math.BigInteger;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dal_layer.DALService;

@RestController
@RequestMapping("/system/")
public class SystemInfoController {

	private class SystemInfo{
		int id;
		int Response404;
		int TotalResponse;
		int BootFail;
		int TaskCompleted;
		int TaskUndertaken;

		public SystemInfo(ResultSet rs) {
			try {
				rs.next();
				this.id=rs.getInt("id");
				this.Response404=rs.getInt("Response404");
				this.TotalResponse=rs.getInt("TotalResponse");
				this.BootFail=rs.getInt("BootFail");
				this.TaskCompleted=rs.getInt("TaskCompleted");
				this.TaskUndertaken=rs.getInt("TaskUndertaken");
				
				
			}catch(Exception e) {}
		}
	}
	
	@RequestMapping("/systemInfo")
	public SystemInfo getSystemInfo() {
		String command="Select * from SystemInfo where id=1";
		ResultSet rs= DALService.sendCommand(command);
		
		return new SystemInfo(rs);
	}
	
	@RequestMapping("/updateHttpResponses")
	public boolean updateBio(@RequestParam(value = "fail") int failedResponses, @RequestParam(value = "total") int totalResponses) {
		SystemInfo sys=getSystemInfo();
		failedResponses=failedResponses+sys.Response404;
		totalResponses=totalResponses+sys.TotalResponse;
		String command = "UPDATE SystemInfo SET Responses404=" + failedResponses + " AND TotalResponse="+totalResponses+" WHERE id=" + 1;
		DALService.sendCommandDataManipulation(command);
		return true;
	}
	
	
	@RequestMapping("/updateBootFailed")
	public boolean updateBoot() {
		SystemInfo sys=getSystemInfo();
		int count=sys.BootFail+1;
		String command = "UPDATE SystemInfo SET BootFail=" + count +" WHERE id=" + 1;
		DALService.sendCommandDataManipulation(command);
		return true;
	}
	
	@RequestMapping("/updateTask")
	public boolean updateTask(@RequestParam(value = "taskCompleted") int taskCompleted, @RequestParam(value = "taskUndertaken") int taskUndertaken) {
		SystemInfo sys=getSystemInfo();
		taskCompleted=taskCompleted+sys.TaskCompleted;
		taskUndertaken=taskUndertaken+sys.TaskUndertaken;
		String command = "UPDATE SystemInfo SET TaskCompleted=" + taskCompleted + " AND TaskUndertaken="+taskUndertaken+" WHERE id=" + 1;
		DALService.sendCommandDataManipulation(command);
		return true;
	}
	
	@RequestMapping("/getEffectiveness")
	public float getEffectiveness() {
		SystemInfo sys=getSystemInfo();
		float effectiveness=(sys.TaskCompleted/sys.TaskUndertaken)*100;
		return effectiveness;
	}
	
	@RequestMapping("/getBootFail")
	public int getBootFail() {
		SystemInfo sys=getSystemInfo();
		return sys.BootFail;
	}
	
	@RequestMapping("/getErrorRate")
	public float getErrorRate() {
		SystemInfo sys=getSystemInfo();
		float errorRate=sys.Response404/sys.TotalResponse;
		return errorRate;
	}
	
	
}

package Controllers;

import java.math.BigInteger;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dal_layer.DALService;

@RestController
@RequestMapping("/system/")
public class SystemInfoController {

	private class SystemInfo{

		private int id;
		private int Response404;
		private int TotalResponse;
		private int BootFail;
		private int TaskCompleted;
		private int TaskUndertaken;
		
		//Calculations
		private float effectiveness;
		private float errorRate;
		

		public SystemInfo(ResultSet rs) {
			try {
				rs.next();
				this.id=rs.getInt("id");
				this.Response404=rs.getInt("Response404");
				this.TotalResponse=rs.getInt("TotalResponse");
				this.BootFail=rs.getInt("BootFail");
				this.TaskCompleted=rs.getInt("TaskCompleted");
				this.TaskUndertaken=rs.getInt("TaskUndertaken");
				
				this.effectiveness=(this.TaskCompleted/this.TaskUndertaken)*100;
				this.errorRate=this.Response404/this.TotalResponse;
			}catch(Exception e) {}
		}
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getResponse404() {
			return Response404;
		}

		public void setResponse404(int response404) {
			Response404 = response404;
		}

		public int getTotalResponse() {
			return TotalResponse;
		}

		public void setTotalResponse(int totalResponse) {
			TotalResponse = totalResponse;
		}

		public int getBootFail() {
			return BootFail;
		}

		public void setBootFail(int bootFail) {
			BootFail = bootFail;
		}

		public int getTaskCompleted() {
			return TaskCompleted;
		}

		public void setTaskCompleted(int taskCompleted) {
			TaskCompleted = taskCompleted;
		}

		public int getTaskUndertaken() {
			return TaskUndertaken;
		}

		public void setTaskUndertaken(int taskUndertaken) {
			TaskUndertaken = taskUndertaken;
		}

		public float getEffectiveness() {
			return effectiveness;
		}

		public void setEffectiveness(float effectiveness) {
			this.effectiveness = effectiveness;
		}

		public float getErrorRate() {
			return errorRate;
		}

		public void setErrorRate(float errorRate) {
			this.errorRate = errorRate;
		}
	}
	
	@RequestMapping("/systemInfo")
	public SystemInfo getSystemInfo() {
		String command="Select * from SystemInfo where id=1";
		ResultSet rs= DALService.sendCommand(command);
		SystemInfo sys=new SystemInfo(rs);
		
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
	
}

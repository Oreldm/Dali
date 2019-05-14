package Controllers;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dal_layer.DALService;

@RestController
@RequestMapping("/ML/")
public class MachineLearningController {
	@RequestMapping("/postScore")
	public boolean postScore(@RequestParam(value = "viewerId") int viewerId,
			@RequestParam(value = "genereId") int genereId,
			@RequestParam(value = "score") int score) throws SQLException {
		String command = "UPDATE ML_Viewer_Tag_Score SET score = score + "+score+" WHERE tagId = "+genereId+" AND viewerId="+viewerId;
		DALService.sendCommandDataManipulation(command);
		
		return true;
	}
	
	@RequestMapping("/runML")
	public boolean start() throws SQLException {
		return true;
	}
	
	
}

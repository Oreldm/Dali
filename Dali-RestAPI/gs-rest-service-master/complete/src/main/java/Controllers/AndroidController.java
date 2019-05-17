package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import Helpers.ArtistHelper;
import Helpers.ArtworkHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Helpers.ViewerHelper;
import Objects.Artist;
import Objects.Artwork;
import Objects.Viewer;
import dal_layer.DALService;

@RestController
@RequestMapping("/android/")
public class AndroidController implements QueryHelper, TableNames {

	@RequestMapping("/recommendArtwork")
	public Artwork recommendArtwork() throws Exception{
		
		//TODO: to finish after the recommendation system
		
		Artwork artwork = new Artwork();
		//for highest score (2/3 of the time)
		String selectHighestScore = "SELECT tagId,score FROM ML_Viewer_Tag_Score WHERE ViewerId=1 AND score=(SELECT MAX(score) "
				+ "FROM (SELECT * FROM ML_Viewer_Tag_Score WHERE ViewerId=1) AS T)";
		
		//for closest genere- 1/3 of the time
		String command="SELECT tagId,score FROM ML_Viewer_Tag_Score WHERE ViewerId=1";
		
		
		
		return artwork;
	}
	
	


}

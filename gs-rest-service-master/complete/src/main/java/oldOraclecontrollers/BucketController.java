package oldOraclecontrollers;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dal_layer.DALService;

@RestController
@RequestMapping("/storage/")
@MultipartConfig(maxFileSize = 3048576, maxRequestSize = 3048576)
public class BucketController {

    private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public boolean uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestPart String artid,
    		@RequestPart String artistid, @RequestPart String tags, @RequestPart String xPosition , @RequestPart String yPosition) {
    	String response= this.amazonClient.uploadFile(file);
    	response=addBrackets(response);
    	artid=addBrackets(artid);
    	tags=addBrackets(tags);
    	artistid=addBrackets(artistid);
    	Double xPos = Double.parseDouble(xPosition);
    	Double yPos = Double.parseDouble(yPosition);
    	//INSERT INTO ART (ARTID, URL, ARTISTID, TAGS) VALUES ('name','name','orel','name')
    	String command="insert into ART (ARTID, URL, ARTISTID, TAGS, xPosition, yPosition) VALUES ("+artid+","+response+
    			","+artistid+","+tags+ ",+"+xPos+","+yPos+")";
    	System.out.println(command);
    	
    	return DALService.sendCommandDontRecieve(command);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
    
	private static String addBrackets(String str) {
		return ("'" + str + "'");
	}
}

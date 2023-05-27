package testClassPackage;



import java.io.IOException;
import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonFunctionPackage.API_commonFunction;
import commonFunctionPackage.Utilitycommon_function;
import io.restassured.path.json.JsonPath;
import requestRepositoryPackage.Postreq_repository;


public class Post_tc3 {
	@Test



	public static void execute() throws IOException {
		
			for(int i=0;i<5;i++)
			{
				 String baseURI=Postreq_repository.base_URI();
				  String requestBody=Postreq_repository.post_req_tc3();
				  String resource=Postreq_repository.post_resource();
				  
				  int statusCode=API_commonFunction.response_statuscode(baseURI, requestBody, resource);
				  if(statusCode==201)
					 
				  {
					  String responseBody=API_commonFunction.res_responseBody(baseURI, requestBody, resource);
					  System.out.println(responseBody);
					  Post_tc3.validator(responseBody, statusCode, requestBody);
					  Utilitycommon_function.evidencefilecreator("Post_tc3", requestBody, responseBody);
					  
					  break;
				  }
				  else 
				  {
					  System.out.println("correct status code not found hence retrying");
				  }
			}
		
		}
		
		public static void validator(String responseBody,int statusCode,String requestBody) {
		  
		//Parse response body and its parameters
			JsonPath jspres=new JsonPath(responseBody);
			String res_name=jspres.getString("name");
			String res_job=jspres.getString("job");
			String res_id=jspres.getString("id");
			String res_createdAt=jspres.getString("createdAt");
			String currentdate=LocalDate.now().toString();
			
		  //parse request body and its parameters
		   JsonPath jspreq=new JsonPath ( requestBody);
			String req_name=jspreq.getString("name");
			String req_job=jspreq.getString("job");
		
	  	//Validate the response
		   
		   Assert.assertEquals(statusCode,201);
		   Assert.assertEquals(req_name, res_name);
		   Assert.assertEquals(req_job, res_job);
		   Assert.assertNotNull(res_id);
		   Assert.assertEquals(res_createdAt.substring(0,10), currentdate);
		 
		   
		
		}

	}
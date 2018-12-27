package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import com.example.demo.model.User;



@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("DemoApplication.main()");
		//Test();
	}
	
	 //https://stackoverflow.com/questions/21723183/spring-resttemplate-to-post-request-with-custom-headers-and-a-request-object/26882099
	//https://github.com/spring-projects/spring-framework/blob/master/spring-web/src/test/java/org/springframework/web/client/AsyncRestTemplateIntegrationTests.java
	   public static void Test(){
		   AsyncRestTemplate  restTemplate = new AsyncRestTemplate();
		  
		    HttpHeaders headers = new HttpHeaders();
		  
		    HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		  
	    	List<ListenableFuture<ResponseEntity<User>>> responseFutures = new ArrayList<>();
	    	List<Integer> userIds = Arrays.asList(3,4,5);
	    	for (Integer userId : userIds) {
	    	    // FIXME studentId is not used
	    	    ListenableFuture<ResponseEntity<User>> responseEntityFuture = restTemplate.exchange("http://localhost:8080/tx/users/"+userId, HttpMethod.GET, requestEntity, User.class);
	    	    responseFutures.add(responseEntityFuture);
	    	}
	    	// now all requests were send, so we can process the responses
	    	List<User> listOfResponses = new ArrayList<>();
	    	for (ListenableFuture<ResponseEntity<User>> future: responseFutures) {
	    	    try {
	    	        User respBody = future.get().getBody();
	    	        listOfResponses.add(respBody);
	    	    } catch (Exception ex) {
	    	        throw new RuntimeException("Exception while making Rest call.", ex);
	    	    }
	    	}
	    }
}

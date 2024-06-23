package com.zosh.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.Plan;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Subscription;
import com.zosh.exception.UserException;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.response.PaymentLinkResponse;
import com.zosh.service.UserService;

@RestController
@RequestMapping("/api/plan")
public class PaymentController {
	
	  @Value("${razorpay.api.key}")
	    private String razorpayApiKey;

	    @Value("${razorpay.api.secret}")
	    private String razorpayApiSecret;
	    
	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private UserRepository userRepository;
	
	    @PostMapping("/subscribe/{planType}")
	    public ResponseEntity<PaymentLinkResponse> createSubscription(@PathVariable String planType,
	    		@RequestHeader("Authorization") String jwt)
	    		throws RazorpayException, UserException {
	    	
	    	RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
	    	
	        try {
	        	
	        	User user=userService.findUserProfileByJwt(jwt);
	        	
	        	JSONObject paymentLinkRequest = new JSONObject();
	        	paymentLinkRequest.put("currency","INR");
	        	paymentLinkRequest.put("description","Twitter Verification");
	        	
	        	JSONObject customer = new JSONObject();
	        	customer.put("name",user.getFullName());
	        	customer.put("email",user.getEmail());
	        	paymentLinkRequest.put("customer",customer);
	        	
	        	JSONObject notify = new JSONObject();
	        	notify.put("sms",true);
	        	notify.put("email",true);
	        	paymentLinkRequest.put("notify",notify);
	        	paymentLinkRequest.put("reminder_enable",true);
	        	
	        	JSONObject notes = new JSONObject();
	        	
	        	notes.put("user_id", user.getId().toString());
	        	paymentLinkRequest.put("notes",notes);
	        	
	        	paymentLinkRequest.put("callback_url","http://localhost:3000/verified");
	        	paymentLinkRequest.put("callback_method","get");
	        	
	         	if(planType.equals("monthly")) {
	        		paymentLinkRequest.put("amount",650*100);
	        		notes.put("plan","monthly");
	        	}
	         	else {
	         		paymentLinkRequest.put("amount",6800*100);
	        		notes.put("plan","monthly");
	         	}
	        	              
	        	PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
	        	
	        	System.out.println("plan : yearly"+payment);
	        	
			    String paymentLinkId = payment.get("id");
			    String paymentLinkUrl = payment.get("short_url");
			      
			    PaymentLinkResponse res=new PaymentLinkResponse();
			    res.setPaymentLink(paymentLinkUrl);
	        	
	            return new ResponseEntity<>(res,HttpStatus.CREATED);
	            
	        } catch (RazorpayException e) {
	            throw new RazorpayException(e.getMessage());
	        }
	    }
	    
	    
	    @GetMapping("/{paymentLinkId}")
	    public ResponseEntity<String> fetchPaymetn(@PathVariable String paymentLinkId) throws RazorpayException {
	    	
	    	RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

	        try {

	        	PaymentLink payment = razorpay.paymentLink.fetch(paymentLinkId);
	        	
	            String customerJsonString = payment.get("customer").toString();

	            JSONObject customerObject = new JSONObject(customerJsonString);

	            String email = customerObject.getString("email");
	            
	            User user =userRepository.findByEmail(email);
	            
	            String notesJsonString=payment.get("notes").toString();
	            
	            JSONObject notesObject=new JSONObject(notesJsonString);
	            
	            String plan=notesObject.getString("plan");
	            
	            if(payment.get("status").equals("paid")) {
	            	user.getVerification().setStartedAt(LocalDateTime.now());
	            	user.getVerification().setPlanType(plan);

	                if (plan.equals("yearly")) {
	                    LocalDateTime endsAt = user.getVerification().getStartedAt().plusYears(1);
	                    user.getVerification().setEndsAt(endsAt);
	                }
	                else if (plan.equals("monthly")) {
	                    LocalDateTime endsAt = user.getVerification().getStartedAt().plusMonths(1);
	                    user.getVerification().setEndsAt(endsAt);
	                }
	                
	                userRepository.save(user);
	                
	            }
	        	
	            return new ResponseEntity<>(email,HttpStatus.CREATED);
	            
	        } catch (RazorpayException e) {
	            throw new RazorpayException(e.getMessage());
	        }
	    }
	    
	    
}

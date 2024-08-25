package com.identify.identify.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.identify.identify.dto.ReturnResponse;
import com.identify.identify.error.ApiRequestException;

import org.springframework.stereotype.Component;


@Component
public class PremblyManager {
        
        private final String baseUrl = "https://api.prembly.com/identitypass/verification";

        private final String premblyKey = "sandbox_sk_5iSW0VAxWF5uVLF5xwqXulJ4iuElu7IBoQPP7H4";

        private final String premblyAppId = "7f36a394-3b80-4b9d-a512-eff161a8fcba";


        @Autowired
        private RestTemplate restTemplate;


        private HttpHeaders requestHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("x-api-key", premblyKey);
            headers.add("app-id", premblyAppId);
            return headers; 
        }
    

    
    
        // @SuppressWarnings("unchecked")
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public ReturnResponse verifyPhoneNumber(String ninNumber, String country) {
            try {
                System.out.println("*******************************************");
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("number", ninNumber);
                HttpEntity<Object> requestEntity = new HttpEntity<>(requestData, requestHeaders());
                
                // Log URL and request details
                String url = baseUrl + "/phone_number";
                System.out.println("Request URL: " + url);
                System.out.println("Request Entity: " + requestEntity);
        
                ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        
                // Check if the response has a body
                if (response.hasBody()) {
                    Map responseBody = response.getBody();
                    System.out.println("Response Body: " + responseBody);
        
                    Boolean status = (Boolean) responseBody.get("status");
                    System.out.println("Response status: " + status);
        
                    String response_code = (String) responseBody.get("response_code");
                    System.out.println("Response response_code: " + response_code);
        
                    if (status != null && status && "00".equals(response_code)) {
                        // save record to db
                        Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                        System.out.println("Data: " + data);
                        return new ReturnResponse(status, response_code, data);
                    } else if (status != null && !status) {
                        if ("01".equals(response_code)) {
                            return new ReturnResponse(false, "Record not found for the provided number", null);
                        } else if ("02".equals(response_code)) {
                            return new ReturnResponse(false, "Service is currently not available", null);
                        } else {
                            System.out.println("Response response_code: " + response_code);
                            return new ReturnResponse(false, "Verification can't be completed at the moment", null);
                        }
                    }
                }
        
                return new ReturnResponse(false, "Verification can't be completed at the moment", null);
            } catch (HttpClientErrorException ex) {
                System.out.println("HttpClientErrorException: " + ex.getMessage());
                return new ReturnResponse(false, "Service is currently not available", null);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
                return new ReturnResponse(false, "An unexpected error occurred", null);
            }
        }
        

    }
    
    
    

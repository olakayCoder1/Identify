package com.identify.identify.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.identify.identify.dto.ReturnResponse;

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


        public ReturnResponse verifyPhoneNumber(String phoneNumber, String country) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("number", phoneNumber);
            return verifyData(payload, "/phone_number");
        }
    
        public ReturnResponse verifyBVN(String bvn, String country) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("number", bvn);
            return verifyData(payload, "/bvn");
        }

        public ReturnResponse verifyNIN(String bvn, String country) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("number", bvn);
            return verifyData(payload, "/nin");
        }


        public ReturnResponse verifyDriversLicense(String bvn, String country) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("number", bvn);
            return verifyData(payload, "/drivers_license");
        }
    
        private ReturnResponse verifyData(Object payload, String endpoint) {
            try {
                System.out.println("*******************************************");
                HttpEntity<Object> requestEntity = new HttpEntity<>(payload, requestHeaders());
    
                // Log URL and request details
                String url = baseUrl + endpoint;
    
                ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
    
                // Check if the response has a body
                if (response.hasBody()) {
                    Map responseBody = response.getBody();
    
                    Boolean status = (Boolean) responseBody.get("status");
                    String response_code = (String) responseBody.get("response_code");

                    System.out.println(response_code);
    
                    if (status != null && status && "00".equals(response_code)) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                        return new ReturnResponse(status, response_code, data);
                    } else if (status != null && !status) {
                        return handleErrorResponse(response_code);
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
    
        private ReturnResponse handleErrorResponse(String responseCode) {
            if ("01".equals(responseCode)) {
                return new ReturnResponse(false, "Record not found for the provided number", null);
            } else if ("02".equals(responseCode)) {
                return new ReturnResponse(false, "Service is currently not available", null);
            } else {
                System.out.println("Response response_code: " + responseCode);
                return new ReturnResponse(false, "Verification can't be completed at the moment", null);
            }
        }
    
    }
    
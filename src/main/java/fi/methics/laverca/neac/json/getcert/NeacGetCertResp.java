//
//  (c) Copyright 2003-2023 Methics Technologies Oy. All rights reserved. 
//

package fi.methics.laverca.neac.json.getcert;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import fi.methics.laverca.neac.json.GsonMessage;

/**
 * <pre> 
{
  "status_code": 200,
  "message": "Lay chung thu thanh cong",
  "data": {
      "user_certificates": [
        {
          "cert_id": "123abc456",
          "cert_data": "MII..==",
          "chain_data": [
            "MII..=",
            "MII..=",
            "MII..=",
          ],
          "serial_number": "51sahj5",
          "transaction_id": "CA101003"
        }
      ]
  }
}
 * </pre>
 */
public class NeacGetCertResp extends GsonMessage {

    @SerializedName("status_code")
    public Integer status_code;
    
    @SerializedName("message")
    public String message;

    @SerializedName("transaction_id")
    public String transaction_id;
    
    @SerializedName("data")
    public List<UserCertificate> data;
    
    public static NeacGetCertResp makeError(int code, String message) {
        NeacGetCertResp resp = new NeacGetCertResp();
        resp.status_code = Integer.valueOf(code);
        resp.message     = message;
        return resp;
    }
    
    /**
     * User certificate JSON
     */
    public static class UserCertificate extends GsonMessage {
        
        @SerializedName("cert_id")
        public String cert_id;
        
        @SerializedName("cert_data")
        public String cert_data;
        
        @SerializedName("chain_data")
        public List<String> chain_data = new ArrayList<>();
        
        @SerializedName("serial_number")
        public String serial_number;

        @SerializedName("transaction_id")
        public String transaction_id;
        
    }
    
}

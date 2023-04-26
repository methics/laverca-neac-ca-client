//
//  (c) Copyright 2003-2023 Methics Technologies Oy. All rights reserved. 
//

package fi.methics.laverca.neac.json.sign;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import fi.methics.laverca.neac.json.GsonMessage;

/**
 * <pre> 
{
  "status_code": 200,
  "message": "Lay chung thu thanh cong",
  "data": {
      "transaction_id":  "CA101003",
      "signed_files": [
        {
          "doc_id": "123abc456",
          "signature_value": "MII..==",
          "timestamp_signature": "20211123070000Z",
        }
      ]
  }
}
 * </pre>
 */
public class NeacSignResp extends GsonMessage {

    @SerializedName("status_code")
    public Integer status_code;
    
    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data = new Data();
    
    
    /**
     * Data class may be SYNCH or ASYNCH.
     * In ASYNCH mode, signed_files will be null.
     */
    public static class Data extends GsonMessage {
        
        @SerializedName("transaction_id")
        public String transaction_id;
        
        @SerializedName("signed_files")
        public List<SignedFile> signed_files;

    }
    
    public static class SignedFile extends GsonMessage {
        
        @SerializedName("doc_id")
        public String doc_id;
        
        @SerializedName("signature_value")
        public String signature_value;
        
        @SerializedName("timestamp_signature")
        public String timestamp_signature;
        
    }

    public static GsonMessage makeError(int code, String message) {
        NeacSignResp resp = new NeacSignResp();
        resp.status_code = Integer.valueOf(code);
        resp.message     = message;
        return resp;
    }
    
}

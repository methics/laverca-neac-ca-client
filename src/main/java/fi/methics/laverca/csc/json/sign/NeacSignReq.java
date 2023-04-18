//
//  (c) Copyright 2003-2023 Methics Technologies Oy. All rights reserved. 
//

package fi.methics.laverca.csc.json.sign;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import fi.methics.laverca.csc.json.GsonMessage;

/**
 * <pre> 
{
  "sp_id": "DVCQG",
  "sp_password": "12345678",
  "user_id": "987654321",
  "transaction_id": "CA101003",
  "serial_number": "5Chsdfggh",
  "time_stamp": "202111230970000Z",
  "sign_files": [
    {
      "data_to_be_signed": "AyAsdX=",
      "doc_id": "AAA-BBB-CCC",
      "file_options": {
        "file_type": "xml",
        "sign_type": "hash"
      }
    }
  ]
}
 * </pre>
 */
public class NeacSignReq extends GsonMessage {

    
    // SP's account name provided by CA
    @SerializedName("sp_id")
    public String sp_id;
    
    // Password provided by CA to SP
    @SerializedName("sp_password")
    public String sp_password;

    // CCCD/ID number/Passport/tax identification number/ of
    // the individual/organization who wants to log in
    @SerializedName("user_id")
    public String user_id;

    // Serial number of the digital mailer
    @SerializedName("serial_number")
    public String serial_number;
    
    // Transaction code initiated by SP
    @SerializedName("transaction_id")
    public String transaction_id;
    
    // The time the user submits the digital signature
    // request. Format: YYYYMMddHHmmSS
    @SerializedName("time_stamp")
    public String time_stamp;
    
    @SerializedName("sign_files")
    public List<FileToSign> sign_files = new ArrayList<>();
    
    public static class FileToSign extends GsonMessage {
        
        // String representation of the document required to be
        // digitally signed ( base64 string for file, hex for hash)
        @SerializedName("data_to_be_signed")
        public String data_to_be_signed;
        
        // Document code that requires digital signature
        // This code needs to be displayed at the same time at
        // the SP's interface and at the CA's application interface
        // when the user performs the digital signature request
        // authentication
        @SerializedName("doc_id")
        public String doc_id;
     
        @SerializedName("file_options")
        public FileOptions file_options;
        
        public FileToSign(String dtbs, String docid, FileOptions opts) {
            this.data_to_be_signed = dtbs;
            this.doc_id            = docid;
            this.file_options      = opts;
        }
        
        public FileToSign(String dtbs, String docid) {
            this(dtbs, docid, null);
        }
        
    }
    
    public static class FileOptions extends GsonMessage {
        
        // File type: xml/json/word/pdf/excel/...
        @SerializedName("file_type")
        public String file_type;
        
        // Digital signature type: hash/file
        @SerializedName("sign_type")
        public String sign_type = "hash";
        
        public FileOptions(String fileType, String signType) {
            this.file_type = fileType;
            this.sign_type = signType;
        }
        
    }
}

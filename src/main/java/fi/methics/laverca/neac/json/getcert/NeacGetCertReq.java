//
//  (c) Copyright 2003-2023 Methics Technologies Oy. All rights reserved. 
//

package fi.methics.laverca.neac.json.getcert;

import com.google.gson.annotations.SerializedName;

import fi.methics.laverca.neac.json.GsonMessage;

/**
 * <pre> 
{
  "sp_id": "DVCQG",
  "sp_password": "12345678",
  "user_id": "987654321",
  "transaction_id": "CA101003"
}
 * </pre>
 */
public class NeacGetCertReq extends GsonMessage {

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
    
}

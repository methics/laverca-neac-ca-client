package fi.methics.laverca.csc.json;

import java.io.IOException;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Response;

/**
 * <pre>
{
  "code": 400,
  "message": "That thing there was not right"
}
 * </pre>
 */
public class NeacErrorResp extends GsonMessage {

    @SerializedName("code")
    public Integer code;
    
    @SerializedName("message")
    public String message;
    
    public static NeacErrorResp fromResponse(final Response response) throws IOException {
        if (response == null) return null;
        String json = response.body().string();
        return fromJson(json, NeacErrorResp.class);
    }
    
}
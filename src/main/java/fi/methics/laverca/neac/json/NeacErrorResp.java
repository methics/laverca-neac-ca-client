package fi.methics.laverca.neac.json;

import java.io.IOException;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

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
    
    public NeacErrorResp() {}
    public NeacErrorResp(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public static NeacErrorResp fromResponse(final Response response) throws IOException {
        if (response == null) return null;
        try (ResponseBody body = response.body()) {
            System.out.println(response.code() + ": " + response.message());
            String json = body.string();
            return fromJson(json, NeacErrorResp.class);
        } catch (Exception e) {
            return new NeacErrorResp(Integer.valueOf(500), e.getMessage());
        }
    }
    
}
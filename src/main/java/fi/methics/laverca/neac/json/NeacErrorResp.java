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
    
    public static final Integer SUCCESS             = Integer.valueOf(200);
    public static final Integer INVALID_DOCUMENT    = Integer.valueOf(400);
    public static final Integer INVALID_CREDENTIALS = Integer.valueOf(401);
    public static final Integer CERTIFICATE_REVOKED = Integer.valueOf(403);
    public static final Integer SYSTEM_ERROR        = Integer.valueOf(500);
    
    @SerializedName("code")
    public Integer code;
    
    @SerializedName("message")
    public String message;
    
    public NeacErrorResp() {}
    public NeacErrorResp(Integer code, String message) {
        this.code    = code;
        this.message = message;
    }
    
    public static NeacErrorResp fromResponse(final Response response) throws IOException {
        if (response == null) return null;
        try (ResponseBody body = response.body()) {
            String json = body.string();
            return fromJson(json, NeacErrorResp.class);
        } catch (Exception e) {
            return new NeacErrorResp(Integer.valueOf(500), e.getMessage());
        }
    }
    
}
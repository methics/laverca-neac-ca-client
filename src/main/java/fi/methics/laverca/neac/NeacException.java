package fi.methics.laverca.neac;

import com.squareup.okhttp.Response;

import fi.methics.laverca.neac.json.NeacErrorResp;

public class NeacException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private NeacErrorResp error;
    private int httpcode = -1;
    
    public NeacException(Response response) {
        try {
            this.httpcode = response.code();
            this.error    = NeacErrorResp.fromResponse(response);
        } catch (Exception e) {
            this.error = new NeacErrorResp();
            error.code    = NeacErrorResp.SYSTEM_ERROR;
            error.message = "Failed to parse error:" + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public NeacException(Exception e) {
        this.error    = new NeacErrorResp();
        error.code    = NeacErrorResp.SYSTEM_ERROR;
        error.message = "Request failed: " + e.getMessage();
    }

    /**
     * Get error JSON
     * @return error JSON if available
     */
    public NeacErrorResp getError() {
        return this.error;
    }
    
    /**
     * Get HTTP statuscode of the error
     * @return HTTP statuscode or -1 if not available
     */
    public int getHttpStatusCode() {
        return this.httpcode;
    }
    
}

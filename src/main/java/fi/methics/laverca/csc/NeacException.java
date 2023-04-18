package fi.methics.laverca.csc;

import com.squareup.okhttp.Response;

import fi.methics.laverca.csc.json.NeacErrorResp;

public class NeacException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private NeacErrorResp error;
    private int httpcode = -1;
    
    /**
     * Create an exception that highlights that the {@link NeacCaClient#authLogin()}
     * must be called first
     * @return Exception
     */
    public static NeacException createNotLoggedInException() {
        NeacException ex = new NeacException();
        ex.error = new NeacErrorResp();
        ex.error.code    = 400;
        ex.error.message = "Client is not logged in";
        return ex;
    }
    /**
     * Create an exception that highlights that the request is missing a parameter
     * @param param Name of missing parameter
     * @return Exception
     */
    public static NeacException createMissingParamException(String param) {
        NeacException ex = new NeacException();
        ex.error = new NeacErrorResp();
        ex.error.code    = 400;
        ex.error.message = "Missing parameter " + param;
        return ex;
    }
    
    public NeacException(Response response) {
        try {
            this.httpcode = response.code();
            this.error    = NeacErrorResp.fromResponse(response);
        } catch (Exception e) {
            this.error = new NeacErrorResp();
            error.code    = 500;
            error.message = "Failed to parse error:" + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public NeacException(Exception e) {
        this.error = new NeacErrorResp();
        error.code    = 500;
        error.message = "Request failed: " + e.getMessage();
    }

    private NeacException() {
        
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

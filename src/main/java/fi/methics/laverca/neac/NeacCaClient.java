package fi.methics.laverca.neac;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import fi.methics.laverca.neac.json.getcert.NeacGetCertReq;
import fi.methics.laverca.neac.json.getcert.NeacGetCertResp;
import fi.methics.laverca.neac.json.sign.NeacSignReq;
import fi.methics.laverca.neac.json.sign.NeacSignReq.FileToSign;
import fi.methics.laverca.neac.json.sign.NeacSignResp;
import fi.methics.laverca.neac.util.AllTrustingHostnameVerifier;
import fi.methics.laverca.neac.util.AllTrustingTrustManager;

/**
 * NEAC CA API Client class. This is used to communicate with a CA.
 * 
 * <p>Usage example:
 * <pre>{@code 
 * NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(BASE_URL)
 *                                                 .withSpId("ABC-SP")
 *                                                 .withSpPassword("SecurePassword!")
 *                                                 .build();
 * NeacGetCertResp certs = client.getCertificate("35847001001");
 * FileToSign file = new FileToSign("de020bd5b1b6aa9a7a4d0af3b89ef883378cc254fae49c8c509254bbb496f2e5", "test.pdf");
 * NeacSignResp sigresp = client.sign("35847001001", file);
 * String signature = sigresp.data.signed_files.get(0).signature_value;
 * }</pre>
 */
public class NeacCaClient {

    private String baseUrl;
    private String secondaryUrl;
    private String sp_id;
    private String sp_password;
    
    private OkHttpClient client;
    
    protected NeacCaClient(String baseUrl,
                           String secondaryUrl,
                           String username, 
                           String password,
                           boolean trustall)
    {
        this.baseUrl      = baseUrl;
        this.secondaryUrl = secondaryUrl;
        this.sp_id        = username;
        this.sp_password  = password;
        
        this.client = new OkHttpClient();
        this.client.setConnectTimeout(60, TimeUnit.SECONDS);
        this.client.setReadTimeout(60,    TimeUnit.SECONDS);
        this.client.setWriteTimeout(60,   TimeUnit.SECONDS);

        if (trustall) {
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[] { new AllTrustingTrustManager() }, new java.security.SecureRandom());
                this.client.setSslSocketFactory(sslContext.getSocketFactory());
                this.client.setHostnameVerifier(new AllTrustingHostnameVerifier());
            } catch (Exception e) {
                throw new NeacException(e);
            }
        }
    }
    
    /**
     * Get certificate of given user
     * @param user_id User identifier (e.g. MSISDN)
     * @return certificate response
     */
    public NeacGetCertResp getCertificate(String user_id) {

        NeacGetCertReq req = new NeacGetCertReq();
        req.sp_id          = this.sp_id;
        req.sp_password    = this.sp_password;
        req.transaction_id = this.generateTransId();
        req.user_id        = user_id;
        
        try {
            String service = "/get_certificate";
            Request.Builder request = new Request.Builder().post(req.toRequestBody());
            Response response = sendRequest(request, service);
            return NeacGetCertResp.fromResponse(response, NeacGetCertResp.class);
        } catch (IOException e) {
            throw new NeacException(e);
        } catch (NeacException e) {
            throw e;
        }
        
    }
    
    /**
     * Request signature
     * @param user_id User identifier (e.g. MSISDN)
     * @return signature response
     */
    public NeacSignResp sign(String user_id, FileToSign ... files) {
        NeacSignReq req    = new NeacSignReq();
        req.sp_id          = this.sp_id;
        req.sp_password    = this.sp_password;
        req.transaction_id = this.generateTransId();
        req.time_stamp     = Instant.now().toString().replace("T", "").replace("-", "").replace(":", "").replace(".", "");
        req.user_id        = user_id;
        req.sign_files     = new ArrayList<>();
        
        for (FileToSign file : files) {
            req.sign_files.add(file);
        }
        
        try {
            String service = "/sign";
            Request.Builder request = new Request.Builder().post(req.toRequestBody());
            Response response = sendRequest(request, service);
            return NeacSignResp.fromResponse(response, NeacSignResp.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NeacException(e);
        } catch (NeacException e) {
            throw e;
        }
    }
    
    /**
     * Send a request to primary URL.
     * If the request fails, and secondary is defined, retry to secondary URL.
     * @param reqBuilder Request Builder
     * @param service    Service part of the URL (e.g. /neac/info)
     * @return
     * @throws IOException
     */
    private Response sendRequest(Request.Builder reqBuilder, String service) throws IOException {
        try {
            Request  request  = reqBuilder.url(this.baseUrl+service).build();
            System.out.println("Sending to " + this.baseUrl+service);
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            if (this.secondaryUrl != null) {
                System.out.println("Sending to " + this.secondaryUrl+service);
                Request request   = reqBuilder.url(this.secondaryUrl+service).build();
                Response response = client.newCall(request).execute();
                return response;
            }
            throw e;
        }
    }
    
    /**
     * Generate a random transaction_id
     * @return transaction_id
     */
    private String generateTransId() {
        return UUID.randomUUID().toString();
    }
    

    public static class Builder {

        private String baseUrl;
        private String secondaryUrl;
        private String sp_id;
        private String sp_password;
        private boolean trustall;
        
        /**
         * Build a new {@link NeacCaClient}
         * @return CA client
         * @throws NeacException if client building fails (e.g. TLS init issues)
         */
        public NeacCaClient build() {
            return new NeacCaClient(this.baseUrl, this.secondaryUrl, this.sp_id, this.sp_password, this.trustall);
        }
        
        /**
         * Set a primary CA URL
         * @param secondaryUrl
         * @return this builder
         */
        public Builder withBaseUrl(String baseurl) {
            this.baseUrl = baseurl;
            return this;
        }
        
        /**
         * Set a secondary CA URL (used if primary fails)
         * @param secondaryUrl
         * @return this builder
         */
        public Builder withSecondaryUrl(String secondaryUrl) {
            this.secondaryUrl = secondaryUrl;
            return this;
        }
        
        public Builder withSpPassword(String sp_password) {
            this.sp_password = sp_password;
            return this;
        }
        
        public Builder withTrustInsecureConnections(boolean trust) {
            this.trustall = trust;
            return this;
        }
        
        public Builder withSpId(String sp_id) {
            this.sp_id = sp_id;
            return this;
        }
        
    }
    
}

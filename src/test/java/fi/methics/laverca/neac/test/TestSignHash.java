package fi.methics.laverca.neac.test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fi.methics.laverca.neac.NeacCaClient;
import fi.methics.laverca.neac.json.sign.NeacSignReq.FileToSign;
import fi.methics.laverca.neac.json.sign.NeacSignResp;

public class TestSignHash {

    public static final String SHA_256_HASH = "yT8dSBW55jBPpLP4qc72IQg1h8YWX8fKmsAx6B84Y4w=";
    
    /**
     * Test signing a single hash with explicit authorize call
     */
    @Test
    public void testSignHash() {
        NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(TestGetCertificate.BASE_URL)
                                                  .withTrustInsecureConnections(true)
                                                  .withSpId(TestGetCertificate.SP_ID)
                                                  .withSpPassword(TestGetCertificate.SP_PASSWORD)
                                                  .build();

        FileToSign dtbs = new FileToSign(SHA_256_HASH, "Test SHA256 hash");
        NeacSignResp resp = client.sign(TestGetCertificate.USER_ID, dtbs);
        
        Assertions.assertNotNull(resp.data,    "Data");
        Assertions.assertNotNull(resp.message, "Message");
    }
    
}

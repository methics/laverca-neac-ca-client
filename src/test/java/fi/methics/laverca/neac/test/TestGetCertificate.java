package fi.methics.laverca.neac.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fi.methics.laverca.neac.NeacCaClient;
import fi.methics.laverca.neac.NeacException;
import fi.methics.laverca.neac.json.getcert.NeacGetCertResp;

public class TestGetCertificate {
    
    public static final String BASE_URL    = "https://demo.methics.fi/neac";
    public static final String USER_ID     = "35847001001";
    public static final String SP_ID       = "http://neac-ap";
    public static final String SP_PASSWORD = "neacTEST321";
    
    @Test
    public void testAuthLogin() {
        NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(TestGetCertificate.BASE_URL)
                                                        .withSpId(SP_ID)
                                                        .withSpPassword(SP_PASSWORD)
                                                        .build();
        
        NeacGetCertResp resp = client.getCertificate(USER_ID);
        Assertions.assertNotNull(resp.data, "certificates");
    }
    
    @Test
    public void testInvalidCredentials() {
        NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(BASE_URL)
                                                        .withTrustInsecureConnections(true)
                                                        .withSpId(SP_ID)
                                                        .withSpPassword("WRONG_PASSWORD")
                                                        .build();
        
        NeacException exception = Assertions.assertThrows(NeacException.class, () -> {
            client.getCertificate(USER_ID);
        });
        Assertions.assertNotNull(exception.getError(), "Error");
    }

}

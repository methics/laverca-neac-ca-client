package fi.methics.laverca.csc.test;

public class TestGetCertificate {

    public static final String BASE_URL = "https://demo.methics.fi";
    public static final String USERNAME = "35847001001";
    public static final String API_KEY  = "E6v31rAxWoY7";
    
    //@Test
    //public void testAuthLogin() {
    //    NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(BASE_URL)
    //                                              .withTrustInsecureConnections(true)
    //                                              .withUsername(USERNAME)
    //                                              .withPassword(API_KEY)
    //                                              .build();
    //    CscLoginResp resp = client.authLogin();
    //    Assertions.assertNotNull(resp.access_token, "access_token");
    //}
    //
    //@Test
    //public void testRefreshToken() {
    //    NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(BASE_URL)
    //                                              .withTrustInsecureConnections(true)
    //                                              .withUsername(USERNAME)
    //                                              .withPassword(API_KEY)
    //                                              .build();
    //    CscLoginResp resp1 = client.authLogin();
    //    CscLoginResp resp2 = client.refreshLogin();
    //    Assertions.assertNotNull(resp1.access_token, "access_token");
    //    Assertions.assertNotNull(resp2.access_token, "access_token");
    //}
    //
    //@Test
    //public void testInvalidCredentials() {
    //    NeacCaClient client = new NeacCaClient.Builder().withBaseUrl(BASE_URL)
    //                                              .withTrustInsecureConnections(true)
    //                                              .withUsername(USERNAME)
    //                                              .withPassword("abc123123")
    //                                              .build();
    //    NeacException exception = Assertions.assertThrows(NeacException.class, () -> {
    //        client.authLogin();
    //    });
    //    Assertions.assertEquals(exception.getError().error, "authentication_error");
    //}

}

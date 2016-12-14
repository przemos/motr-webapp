package uk.gov.dvsa.motr.web.handler;

import com.amazonaws.serverless.proxy.internal.model.ApiGatewayRequestContext;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;

import org.junit.Ignore;
import org.junit.Test;

public class HandlerTest {

    @Ignore
    @Test
    public void aaa() throws Exception {
        AwsProxyRequest r = new AwsProxyRequest();
        r.setPath("/");
        r.setHttpMethod("GET");
        r.setRequestContext(new ApiGatewayRequestContext());
        r.getRequestContext().setHttpMethod("GET");
        System.out.println(new MotrWebHandler().handleRequest(r, null).getBody());
    }
}

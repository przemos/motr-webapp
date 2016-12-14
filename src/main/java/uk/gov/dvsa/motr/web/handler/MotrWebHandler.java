package uk.gov.dvsa.motr.web.handler;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import uk.gov.dvsa.motr.web.config.Config;
import uk.gov.dvsa.motr.web.system.MotrApplication;

import static uk.gov.dvsa.motr.web.system.SystemVariable.LOG_LEVEL;

public class MotrWebHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private final static String DEFAULT_LOG_LEVEL = "INFO";

    private JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    public MotrWebHandler() {

        MotrApplication motrApplication = new MotrApplication();
        handler = JerseyLambdaContainerHandler.getAwsProxyHandler(motrApplication);
        configureLogLevel(handler);
    }

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {

        return handler.proxy(awsProxyRequest, context);
    }

    private void configureLogLevel(JerseyLambdaContainerHandler handler) {

        Config configuration = handler.getApplicationHandler().getServiceLocator().getService(Config.class);
        String logLevel = configuration.getValue(LOG_LEVEL).orElse(DEFAULT_LOG_LEVEL);
        Logger.getRootLogger().setLevel(Level.toLevel(logLevel));
    }
}
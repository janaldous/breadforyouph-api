package com.janaldous.breadforyouph.cdctests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

@Tag("CDC")
@Consumer("breadforyouph-spa")
@Provider("breadforyouph-service")
@PactFolder("src/test/resources/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PactVerificationCDCIT {

	@LocalServerPort
	private int port;

	@BeforeEach
	void before(PactVerificationContext context) {
		context.setTarget(new HttpTestTarget("localhost", port));
	}

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void testTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}

}
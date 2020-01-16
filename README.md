# oauth-flow
The oauth-flow library handles the process flow of OAuth 1.0a. It decouples the process flow and the concrete communication.
The implementation has been done in regard to the [official documentation](https://oauth.net/core/1.0a/).

## Abstract
The library guides you in an object oriented way threw the process of the OAuth 1.0a authentication process. It generates the required authentication header fields which are required to be sent to the service. By decoupling the process flow from the communication it is up to the user to implement the communication with the service. Therefore this library can be used for any service which requires the OAuth 1.0a authentication.
The architecture of the library is build in an immutable fashion. Therefore it can be considered threadsafe.

## Usage

### Full flow
To create the full flows process entry point, you need to create an instance of **de.msi.oauth.flow.OAuthFlow**. To to so, an instance of **RequestTokenFlowParam** is required. Use **de.msi.oauth.flow.RequestTokenFlowParam.RequestTokenFlowParamBuilder** to create a parameter instance.

An process flow example can be seen at [ComplexFlowTest](https://github.com/msiegemund/oauth-flow/blob/master/oauth-flow/src/test/java/de/msi/oauth/flow/ComplexFlowTest.java). This Unit test runs a whole flow by using the official documentations examples.

### Intermediate flow
Aditionally, it is possible to enter the flow at several process relevant points. The intermediate entry points are

* de.msi.oauth.flow.UserAuthorizationIntermediateFactory
* de.msi.oauth.flow.AccessTokenIntermediateFactory
* de.msi.oauth.flow.ProtectedAccessIntermediateFactory

This comes in handy if the process has not been able to complete or some mandatory authentication information is already present.

### Signature
Currently, only *PLAINTEXT* and *HMAC-SHA1* signature methods are supported.

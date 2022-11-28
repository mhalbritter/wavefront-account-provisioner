# Wavefront Account Provisioner

The [Wavefront Spring Boot starter](https://github.com/wavefrontHQ/wavefront-spring-boot/) has a feature which
automatically provisions a Wavefront instance. Unfortunately this starter is not yet Spring Boot 3 compatible.

This application contains the provisioning part as a standalone application.

## Running

* `gradle build`
* `java -jar build/libs/wavefront-account-provisioner-1.0.0.jar --application=<application name> --service=<service name>`

It will print something like this:

```
Running Wavefront provision, please wait ...
Provision successful. Put these properties in your application.properties:

management.wavefront.api-token=<token>
management.wavefront.uri=https://wavefront.surf
management.wavefront.application.service-name=my-service
management.wavefront.application.name=my-application

You can login via this url: <login url>
```

If you run it again, it will reuse the stored account. Pass `--force` to force the provisioning of a new account.

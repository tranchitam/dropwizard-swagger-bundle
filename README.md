dropwizard-swagger-bundle
==================

A Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints.

Current version has been tested with Dropwizard 2.1.4, Swagger Jaxrs2 2.2.7, Swagger UI 4.15.5 that support Swagger 3 specification.

How to use it
-------------

* Add the Maven dependency (available in Maven Central)

```xml
<dependency>
    <groupId>io.github.tranchitam</groupId>
    <artifactId>dropwizard-swagger-bundle</artifactId>
    <version>1.0.0</version>
</dependency>
```


* Add the following to your Configuration class:

```java
public class YourConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
```

* Add the following your configuration yaml (this is the minimal configuration you need):

```yaml
prop1: value1
prop2: value2

# the only required property is resourcePackages, for more config options see below
swagger:
  resourcePackages: <a set of separated string where each string is a package of dropwizard resources classes>
```

* In your Application class:

```java
@Override
public void initialize(Bootstrap<YourConfiguration> bootstrap) {
    bootstrap.addBundle(new SwaggerBundle<YourConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(YourConfiguration configuration) {
            return configuration.swaggerBundleConfiguration;
        }
    });
}
```

* As usual, add Swagger annotations to your resource classes and methods

* Open a browser and hit `http://localhost:<your_port>/swagger`

Additional Swagger configuration
--------------------------------

To see all the properties that can be used to customize Swagger see [SwaggerBundleConfiguration.java](src/main/java/io/github/tranchitam/dropwizard/swagger/configurations/SwaggerBundleConfiguration.java)


# Java-TypeScript interop

Disclaimer: This proof of concept project is created in preparation for the [Java WTB Online conference](https://java.withthebest.com/).

This example project demonstrates how a Java back-end can safely interoperate with a JavaScript front-end in a typed way.

In this project, we use a standard JAX-RS backend powered by Jetty and Jersey. Then we use JSweet to transpile DTOs to TypeScript, and standard Java Annotation Processing (see the javatsinterop-processors sub-project) to generate well-typed TypeScript REST stub.

As a result, the JavaScript client can access the JAX-RS Java API in a fully safe/typed way.

## Sub-projects

This root Maven project contains two child projects:
- javatsinterop-processors: contains the annotation processor to generate TypeScript stubs from JAX-RS server API definitions.
- javatsinterop-examples: contains examples of JAX-RS based client-server interactions.

## Requirements

- Java 8 JDK (not JRE)
- Maven (configured to use the JDK)
- Node.js

For instance, under Ubuntu:

```bash
% sudo apt-get install default-jdk
% sudo apt-get install maven
% sudo apt-get install nodejs-legacy
```

## Usage

Build and install:

```bash
% mvn clean install
```

Run the example:

```bash
% java -cp examples/target/javatsinterop-examples-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.amaris.javatsinterop.server.RestServer
% open http://localhost:2222/index.html

```

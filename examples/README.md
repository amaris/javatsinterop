# Java-TypeScript interop - Examples (javatsinterop-examples)

This example project demonstrates how a Java back-end can safely interoperate with a JavaScript front-end in a typed way

In this project, we use a standard JAX-RS backend powered by Jetty and Jersey. Then we use JSweet to transpile DTOs to TypeScript, and standard Java Annotation Processing (see the javatsinterop-processors sub-project) to generate well-typed TypeScript REST stub.

As a result, the JavaScript client can access the JAX-RS Java API in a fully safe/typed way.

NOTE: this proof of concept project is created in preparation for the [Java WTB Online conference](https://java.withthebest.com/).

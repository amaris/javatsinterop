# Java-TypeScript interop

An example to demonstrate how a Java back-end can safely interoperate with a JavaScript front-end in a typed way

In this example, we use a standard JAX-RS backend powered by Jetty and Jersey.

Then we use JSweet to transpile DTOs to TypeScript, and standard Java Annotation Processing to generate well-typed TypeScript REST stub.

As a result, the JavaScript client can access the JAX-RS Java API in a fully safe/typed way.

NOTE: this proof of concept project is created in preparation for the [Java WTB Online conference](https://java.withthebest.com/).

Copyright 2018 - Amaris

This code is Open Source licenced with the AGPL

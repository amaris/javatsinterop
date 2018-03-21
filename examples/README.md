# Java-TypeScript interop - Examples (javatsinterop-examples)

This is part of the `javatsinterop` project. Please refer to the `javatsinterop` parent project for full documentation.

## Usage

Build the javatsinterop project will build this project. For partial build of this project only, you can follow the instructions below.

Generate the DTO from Java (`src/main/java/com/amaris/javatsinterop/dto`) to TypeScript (`www/js/generated`):

```bash
% mvn generate-sources
```

Build the project (includes DTO generation, requires the javatsinterop-processors project to be installed):

```bash
% mvn clean install
```

Run the example:

```bash
% java -cp target/javatsinterop-examples-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.amaris.javatsinterop.server.RestServer
% open http://localhost:2222/index.html

```


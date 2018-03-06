package com.amaris.javatsinterop.processing;

import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.ws.rs.Path;

@SupportedAnnotationTypes("javax.ws.rs.Path")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StubGenerator extends AbstractProcessor {

	static final String STUB_FILE_NAME = "stubs.ts";

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

		roundEnv.getElementsAnnotatedWith(Path.class).stream().filter(e -> (e instanceof TypeElement))
				.map(e -> (TypeElement) e).forEach(e -> {
					PrintWriter out = getStubFileWriter();

					if (out != null) {

						printIndent().println("namespace " + e.getEnclosingElement().toString() + " {");
						startIntent();
						printIndent().println("class " + e.getSimpleName() + " {\n");
						startIntent();
						printIndent().println("constructor(private baseUrl : string) {}\n");

						for (final Element memberElement : e.getEnclosedElements()) {
							if (memberElement instanceof ExecutableElement
									&& memberElement.getAnnotation(Path.class) != null) {

								printIndent().println(memberElement.getSimpleName() + "(onSuccess : (data : "
										+ java2TS(((ExecutableElement) memberElement).getReturnType().toString())
										+ ") => void) : void {");
								startIntent();
								printIndent().println("$.ajax({");
								startIntent();
								printIndent().println("url: this.baseUrl + \"" + e.getAnnotation(Path.class).value() + "/"
										+ memberElement.getAnnotation(Path.class).value() + "\"");
								endIndent();
								printIndent().println("}).then(onSuccess);");
								endIndent();
								printIndent().println("}\n");
							}
						}
						endIndent();
						printIndent().println("}\n");
						endIndent();
						printIndent().println("}\n");

					}
				});

		if (builderFileWriter != null) {
			builderFileWriter.close();
		}

		return true;
	}

	private PrintWriter builderFileWriter;

	private PrintWriter getStubFileWriter() {
		if (builderFileWriter != null) {
			return builderFileWriter;
		}
		try {
			FileObject f = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", "stubs.ts");
			return builderFileWriter = new PrintWriter(f.openWriter());
		} catch (Exception e1) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
					"cannot create stub file: " + STUB_FILE_NAME + " - " + e1.getMessage());
			return null;
		}
	}

	private int indent = 0;

	private void startIntent() {
		indent += 2;
	}

	private void endIndent() {
		indent -= 2;
	}

	private PrintWriter printIndent() {
		PrintWriter out = getStubFileWriter();
		for (int i = 0; i < indent; i++) {
			out.print(" ");
		}
		return out;
	}

	private String java2TS(String typeName) {
		switch (typeName) {
		case "java.lang.String":
			return "string";
		case "java.lang.Number":
		case "java.lang.Integer":
		case "java.lang.Long":
		case "java.lang.Float":
		case "java.lang.Double":
		case "int":
		case "long":
		case "float":
		case "double":
			return "number";
		case "java.lang.Date":
			return "Date";
		default:
			return typeName;
		}
	}

}
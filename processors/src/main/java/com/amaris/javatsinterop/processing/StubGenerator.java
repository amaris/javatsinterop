/* 
 * MIT License
 * 
 * Copyright (c) 2018 Amaris <rpawlak@amaris.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.amaris.javatsinterop.processing;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.ws.rs.Path;

/**
 * This annotation processor processes JAX-RS annotations to automatically
 * generate well-typed TypeScript stubs for invoking REST APIs from a JavaScript
 * client.
 * 
 * @author Renaud Pawlak
 */
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

						printIndent()
								.println("// This file was generated automatically with JavaTsInterop stub generator");
						printIndent().println("namespace " + e.getEnclosingElement().toString() + " {");
						startIntent();
						printIndent().println("export class " + e.getSimpleName() + " {\n");
						startIntent();

						printIndent().println("constructor(private baseUrl : string = '') {}\n");

						printIndent().println("private _xhr(method: string, url: string, callback: any): void {");
						startIntent();
						printIndent().println("var xhr = new XMLHttpRequest();");
						printIndent().println("xhr.open(method, url, true);");
						printIndent().println("xhr.setRequestHeader('Content-type', 'application/json');");
						printIndent().println("xhr.onload = () => { callback(JSON.parse(xhr.responseText)); };");
						printIndent().println("xhr.send();");
						endIndent();
						printIndent().println("}\n");

						for (final Element memberElement : e.getEnclosedElements()) {
							if (memberElement instanceof ExecutableElement
									&& memberElement.getAnnotation(Path.class) != null) {

								printIndent().print(memberElement.getSimpleName() + "(");
								List<? extends VariableElement> parameters = ((ExecutableElement) memberElement)
										.getParameters();

								for (VariableElement param : parameters) {
									out.print(param.getSimpleName() + " : " + java2TS(param.asType().toString()) + ", ");
								}

								out.println("callback : (data : "
										+ java2TS(((ExecutableElement) memberElement).getReturnType().toString())
										+ ") => void) : void {");
								startIntent();
								String url = "this.baseUrl + '" + e.getAnnotation(Path.class).value() + "/"
										+ memberElement.getAnnotation(Path.class).value() + "'";
								// we only support GET protocol for this example
								if (!parameters.isEmpty()) {
									printIndent().println("let url = " + url + ";");
									printIndent().println("url += '?" + parameters.stream()
											.map(param -> (param.getSimpleName() + "='+(" + param.getSimpleName()
													+ "==null?'':encodeURIComponent(" + param.getSimpleName() + "))"))
											.collect(Collectors.joining("+'&")) + ";");
									url = "url";
								}
								printIndent().println("this._xhr('GET', " + url + ", callback);");
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
			if (typeName.startsWith("java.util.List<")) {
				return java2TS(typeName.substring(15, typeName.length() - 1)) + "[]";
			}
			return typeName;
		}
	}

}
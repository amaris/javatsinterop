/* 
 * javatsinterop - https://github.com/amaris/javatsinterop
 * Copyright (C) 2018 Amaris <rpawlak@amaris.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
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

						printIndent().println("// This file was generated automatically with JavaTsInterop stub generator");
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

								printIndent().println(memberElement.getSimpleName() + "(callback : (data : "
										+ java2TS(((ExecutableElement) memberElement).getReturnType().toString())
										+ ") => void) : void {");
								startIntent();
								printIndent().println("this._xhr('GET', this.baseUrl + '"+e.getAnnotation(Path.class).value()
										+ "/" + memberElement.getAnnotation(Path.class).value() +"', callback);");
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
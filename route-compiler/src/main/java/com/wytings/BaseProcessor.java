package com.wytings;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public abstract class BaseProcessor extends AbstractProcessor {

    public abstract Class<?> supportedAnnotationType();

    public abstract boolean process(TypeElement typeElement, RoundEnvironment roundEnv);

    @Override
    public final Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        Class clazz = supportedAnnotationType();
        if (clazz != null) {
            types.add(clazz.getCanonicalName());
        }
        return types;
    }

    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        for (TypeElement typeElement : annotations) {
            return process(typeElement, roundEnv);
        }
        return true;
    }


    protected void printLog(CharSequence msg) {
        printLog(Diagnostic.Kind.NOTE, msg);
    }

    protected void printLog(Diagnostic.Kind kind, CharSequence msg) {
        processingEnv.getMessager().printMessage(kind, msg);
    }


}

package com.wytings;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.wytings.annotation.RouteModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static com.wytings.CConst.PACKAGE_NAME_GENERATE;
import static com.wytings.CConst.ROUTE_LOADER_INTERFACE;
import static com.wytings.CConst.WARN_GENERATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


@AutoService(Processor.class)
public class RouteProcessor extends BaseProcessor {

    @Override
    public Class<?> supportedAnnotationType() {
        return RouteModule.class;
    }

    @Override
    public boolean process(TypeElement typeElement, RoundEnvironment roundEnv) {

        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(typeElement);
        printLog("process in RouteProcessor -> typeElement = " + typeElement + ", elementSize = " + elementSet.size());

        ParameterizedTypeName returnMapTypes = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(Object.class)));

        MethodSpec.Builder mapLoaderMethodBuilder = MethodSpec.methodBuilder("load")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(returnMapTypes)
                .addStatement("Map<String,Class<?>> map = new $T<>()", HashMap.class);

        for (Element element : elementSet) {
            Element enclosingElement = element.getEnclosingElement();
            RouteModule routeAnnotation = element.getAnnotation(RouteModule.class);
            mapLoaderMethodBuilder.addStatement("map.put($S, $T.class)", routeAnnotation.value(), ClassName.get(element.asType()));
            printLog("enclosingElement = " + enclosingElement.toString() + ", routeAnnotation = " + routeAnnotation);
        }
        mapLoaderMethodBuilder.addStatement("return map");

        try {
            TypeName interfaceType = ClassName.get(processingEnv.getElementUtils().getTypeElement(ROUTE_LOADER_INTERFACE));

            String rootFileName = CConst.ROUTE_MAP_LOADER;
            JavaFile.builder(PACKAGE_NAME_GENERATE,
                    TypeSpec.classBuilder(rootFileName)
                            .addJavadoc(WARN_GENERATE)
                            .addSuperinterface(interfaceType)
                            .addModifiers(PUBLIC)
                            .addMethod(mapLoaderMethodBuilder.build())
                            .build()
            ).build().writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            printLog(Diagnostic.Kind.ERROR, "there is error when generating java -> " + e);
        }
        return true;
    }

}

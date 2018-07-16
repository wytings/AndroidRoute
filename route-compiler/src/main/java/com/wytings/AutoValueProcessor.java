package com.wytings;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wytings.annotation.AutoValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static com.wytings.CConst.ACTIVITY_CLASS;
import static com.wytings.CConst.AUTO_VALUE_HELPER;
import static com.wytings.CConst.AUTO_VALUE_INTERFACE;
import static com.wytings.CConst.BOOLEAN;
import static com.wytings.CConst.BOOLEAN_PRIMITIVE;
import static com.wytings.CConst.BYTE;
import static com.wytings.CConst.BYTE_PRIMITIVE;
import static com.wytings.CConst.DOUBLE;
import static com.wytings.CConst.DOUBLE_PRIMITIVE;
import static com.wytings.CConst.FLOAT;
import static com.wytings.CConst.FLOAT_PRIMITIVE;
import static com.wytings.CConst.INTEGER;
import static com.wytings.CConst.INTEGER_PRIMITIVE;
import static com.wytings.CConst.LONG;
import static com.wytings.CConst.LONG_PRIMITIVE;
import static com.wytings.CConst.PARCELABLE;
import static com.wytings.CConst.SERIALIZABLE;
import static com.wytings.CConst.SHORT;
import static com.wytings.CConst.SHORT_PRIMITIVE;
import static com.wytings.CConst.STRING;
import static com.wytings.CConst.WARN_GENERATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


@AutoService(Processor.class)
public class AutoValueProcessor extends BaseProcessor {


    @Override
    public Class<?> supportedAnnotationType() {
        return AutoValue.class;
    }

    @Override
    public boolean process(TypeElement typeElement, RoundEnvironment roundEnv) {

        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(typeElement);
        printLog("process in AutoValueProcessor -> typeElement = " + typeElement + ", elementSize = " + elementSet.size());
        Map<TypeElement, List<Element>> map = new HashMap<>();
        for (Element element : elementSet) {

            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            List<Element> list = map.get(classElement);
            if (list == null) {
                list = new ArrayList<>();
                map.put(classElement, list);
            }
            list.add(element);

            printLog(element.getEnclosingElement() + "," + element.toString());
        }

        if (map.isEmpty()) {
            return false;
        }
        TypeMirror activityTypeMirror = processingEnv.getElementUtils().getTypeElement(ACTIVITY_CLASS).asType();
        Set<Map.Entry<TypeElement, List<Element>>> entrySet = map.entrySet();
        for (Map.Entry<TypeElement, List<Element>> entry : entrySet) {

            MethodSpec.Builder autoMethodBuilder = MethodSpec.methodBuilder("autoValue")
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .addParameter(ParameterSpec.builder(TypeName.OBJECT, "target").build());

            TypeElement classElement = entry.getKey();
            if (!processingEnv.getTypeUtils().isSubtype(classElement.asType(), activityTypeMirror)) {
                printLog(Diagnostic.Kind.ERROR, "@AutoValue only support Activity, but " + classElement + " is not!");
                continue;
            }

            List<Element> fieldList = entry.getValue();
            String fullCLassName = classElement.getQualifiedName().toString();
            String packageName = fullCLassName.replaceAll("." + classElement.getSimpleName().toString(), "");
            printLog("packageName=" + packageName + ",fullCLassName=" + fullCLassName);

            ClassName classType = ClassName.get(classElement);
            autoMethodBuilder.addStatement("$T activity = ($T) target", classType, classType);

            for (Element fieldElement : fieldList) {
                if (fieldElement.getModifiers().contains(Modifier.PRIVATE)) {
                    printLog(Diagnostic.Kind.ERROR, "The autoValue field CAN NOT BE 'private'!!! please check field ["
                            + fieldElement.getSimpleName() + "] in class [" + fieldElement.getEnclosingElement().toString() + "]");
                    return false;
                }
                AutoValue auto = fieldElement.getAnnotation(AutoValue.class);
                String keyName = fieldElement.getSimpleName().toString();
                String value = auto.value();
                if (value.length() != 0) {
                    keyName = value;
                }

                addAutoValueStatement(autoMethodBuilder, keyName, fieldElement);
            }


            try {
                TypeName interfaceType = ClassName.get(processingEnv.getElementUtils().getTypeElement(AUTO_VALUE_INTERFACE));

                String rootFileName = classElement.getSimpleName() + AUTO_VALUE_HELPER;
                JavaFile.builder(packageName,
                        TypeSpec.classBuilder(rootFileName)
                                .addJavadoc(WARN_GENERATE)
                                .addSuperinterface(interfaceType)
                                .addModifiers(PUBLIC)
                                .addMethod(autoMethodBuilder.build())
                                .build()
                ).build().writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                printLog(Diagnostic.Kind.ERROR, "there is error when generating java -> " + e);
            }


        }

        return true;
    }

    private void addAutoValueStatement(MethodSpec.Builder methodBuilder, String keyName, Element fieldElement) {
        String fieldName = fieldElement.getSimpleName().toString();
        String tempFiledName = "auto_" + fieldName;
        TypeMirror elementType = fieldElement.asType();
        String getExtraName = "getStringExtra";
        String parseMethodName = "";
        switch (elementType.toString()) {
            case BYTE:
            case BYTE_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Byte.parseByte";
                break;
            case SHORT:
            case SHORT_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Short.parseShort";
                break;
            case INTEGER:
            case INTEGER_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Integer.parseInt";
                break;
            case LONG:
            case LONG_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Long.parseLong";
                break;
            case FLOAT:
            case FLOAT_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Float.parseFloat";
                break;
            case DOUBLE:
            case DOUBLE_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Double.parseDouble";
                break;
            case BOOLEAN:
            case BOOLEAN_PRIMITIVE:
                getExtraName = "getStringExtra";
                parseMethodName = "Boolean.parseBoolean";
                break;
            case STRING:
                getExtraName = "getStringExtra";
                break;
            default:
                TypeMirror parcelableType = processingEnv.getElementUtils().getTypeElement(PARCELABLE).asType();
                TypeMirror serializableType = processingEnv.getElementUtils().getTypeElement(SERIALIZABLE).asType();
                if (processingEnv.getTypeUtils().isSubtype(elementType, parcelableType)) {
                    getExtraName = "getParcelableExtra";
                    break;
                } else if (processingEnv.getTypeUtils().isSubtype(elementType, serializableType)) {
                    getExtraName = "getSerializableExtra";
                    break;
                } else {
                    printLog(Diagnostic.Kind.ERROR, "type = " + elementType + " cannot be transferred in Intent");
                }
        }

        if ("".equals(parseMethodName)) {
            TypeName fieldClass = ClassName.get(elementType);
            methodBuilder.addComment("")
                    .addStatement("$T " + tempFiledName + " = activity." + fieldName, fieldClass)
                    .beginControlFlow("try")
                    .addStatement(tempFiledName + " = ($T) activity.getIntent()." + getExtraName + "($S)", fieldClass, keyName)
                    .beginControlFlow("if(" + tempFiledName + " != null)")
                    .addStatement("activity." + fieldName + " = " + tempFiledName)
                    .endControlFlow()
                    .endControlFlow()
                    .beginControlFlow("catch ($T e)", Exception.class)
                    .addStatement("e.printStackTrace()")
                    .endControlFlow();
        } else {
            methodBuilder.addComment("")
                    .addStatement("$T " + tempFiledName + " = activity.getIntent()." + getExtraName + "($S)", String.class, keyName)
                    .beginControlFlow("if(" + tempFiledName + "!=null)")
                    .beginControlFlow("try")
                    .addStatement("activity." + fieldName + " =  " + parseMethodName + "(" + tempFiledName + ")")
                    .endControlFlow()
                    .beginControlFlow("catch ($T e)", Exception.class)
                    .addStatement("e.printStackTrace()")
                    .endControlFlow()
                    .endControlFlow();
        }

    }

}

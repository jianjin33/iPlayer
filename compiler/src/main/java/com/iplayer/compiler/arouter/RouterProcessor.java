package com.iplayer.compiler.arouter;

import com.google.auto.service.AutoService;
import com.iplayer.annotation.router.AutoRouter;
import com.iplayer.annotation.router.Component;
import com.iplayer.annotation.router.Components;
import com.iplayer.annotation.router.StaticRouter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Administrator on 2017/7/25.
 */

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Messager mMessager;

    private Map<String, String> mStaticRouterMap = new HashMap<>();
    private List<String> mAutoRouterList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(AutoRouter.class.getCanonicalName());
        set.add(StaticRouter.class.getCanonicalName());
        set.add(Component.class.getCanonicalName());
        set.add(Components.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mStaticRouterMap.clear();
        mAutoRouterList.clear();

        try {
            Set<? extends Element> mainAppElement = roundEnvironment.getElementsAnnotatedWith(Components.class);
            if (!mainAppElement.isEmpty()) {
                processInstaller(mainAppElement);
                return true;
            }
            processComponent(roundEnvironment);
        } catch (Exception e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }

        return true;
    }

    private void processInstaller(Set<? extends Element> mainAppElement) throws IOException {
        TypeElement typeElement = (TypeElement) mainAppElement.iterator().next();
        JavaFileObject javaFileObject = mFiler.createSourceFile(RouterConfig.ROUTER_MANAGER, typeElement);
        PrintWriter writer = new PrintWriter(javaFileObject.openWriter());

        writer.println("package " + RouterConfig.PACKAGE_NAME + ";");
        writer.println("public class " + RouterConfig.ROUTER_MANAGER + " {");
        writer.println("public static void " + RouterConfig.ROUTER_MANAGER_METHOD + "() {");

        Components componentsAnnotation = typeElement.getAnnotation(Components.class);
        String[] components = componentsAnnotation.value();
        for (String item : components) {
            writer.println(RouterConfig.FILE_PREFIX + item + ".router();");
        }

        writer.println("}");
        writer.println("}");

        writer.flush();
        writer.close();
    }

    private void processComponent(RoundEnvironment roundEnvironment) throws Exception {
        Set<? extends Element> compElements = roundEnvironment.getElementsAnnotatedWith(Component.class);
        if (compElements.isEmpty()) { return;}

        Element item = compElements.iterator().next();
        String componentName = item.getAnnotation(Component.class).value();

        Set<? extends Element> routerElements = roundEnvironment.getElementsAnnotatedWith(StaticRouter.class);
        for (Element e : routerElements) {
            if (! (e instanceof TypeElement)) { continue;}
            TypeElement typeElement = (TypeElement) e;
            String pattern = typeElement.getAnnotation(StaticRouter.class).value();
            mStaticRouterMap.put(pattern, typeElement.getQualifiedName().toString());
        }

        Set<? extends Element> autoRouterElements = roundEnvironment.getElementsAnnotatedWith(AutoRouter.class);
        for (Element e : autoRouterElements) {
            if (!(e instanceof TypeElement)) { continue;}
            TypeElement typeElement = (TypeElement) e;
            mAutoRouterList.add(typeElement.getQualifiedName().toString());
        }

        writeComponentFile(componentName);
    }

    private void writeComponentFile(String componentName) throws Exception {
        String className = RouterConfig.FILE_PREFIX + componentName;
        JavaFileObject javaFileObject = mFiler.createSourceFile(className);
//        javaFileObject.delete();

        PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter());

        printWriter.println("package " + RouterConfig.PACKAGE_NAME + ";");

        printWriter.println("import android.app.Activity;");
        printWriter.println("import android.app.Service;");
        printWriter.println("import android.content.BroadcastReceiver;");

        printWriter.println("public class " + className + " {");
        printWriter.println("public static void router() {");

        // // Router.router(ActivityRule.ACTIVITY_SCHEME + "shop.main", ShopActivity.class);
        for(Map.Entry<String, String> entry : mStaticRouterMap.entrySet()) {
            printWriter.println("com.iplayer.basiclib.arouter.ARouter.router(\"" + entry.getKey()
                    +"\", "+entry.getValue()+".class);");
        }

        for (String klass : mAutoRouterList) {
            printWriter.println("if (Activity.class.isAssignableFrom(" + klass + ".class)) {");
            printWriter.println("com.iplayer.basiclib.arouter.ARouter.router(com.iplayer.basiclib.arouter.rule.ActivityRule.ACTIVITY_SCHEME + \""
                    +klass+"\", " + klass + ".class);");
            printWriter.println("}");

            printWriter.println("else if (Service.class.isAssignableFrom(" + klass + ".class)) {");
            printWriter.println("com.iplayer.basiclib.arouter.ARouter.router(com.iplayer.basiclib.arouter.rule.ServiceRule.SERVICE_SCHEME + \""
                    +klass+"\", " + klass + ".class);");
            printWriter.println("}");

            printWriter.println("else if (BroadcastReceiver.class.isAssignableFrom(" + klass + ".class)) {");
            printWriter.println("com.iplayer.basiclib.arouter.ARouter.router(com.iplayer.basiclib.arouter.rule.ReceiverRule.RECEIVER_SCHEME + \""
                    +klass+"\", "+klass+".class);");
            printWriter.println("}");
        }

        printWriter.println("}");
        printWriter.println("}");
        printWriter.flush();
        printWriter.close();
    }
}
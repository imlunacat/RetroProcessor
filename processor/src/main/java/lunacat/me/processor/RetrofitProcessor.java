package lunacat.me.processor;

import com.google.auto.service.AutoService;

import com.sun.tools.javac.code.Symbol;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import retrofit2.http.GET;
import retrofit2.http.POST;

public class RetrofitProcessor extends AbstractProcessor {
    private String host = "http://www.random.host/";
    private Filer filer;

//    @GET
    private Messager messager;
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer    = processingEnv.getFiler();    // File instance
        messager = processingEnv.getMessager(); // Log
        host = env.getOptions().get("host");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<Element> s = new HashSet<>();
        s.addAll(roundEnv.getElementsAnnotatedWith(GET.class));
        s.addAll(roundEnv.getElementsAnnotatedWith(POST.class));

        try {
            printMethod(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void printMethod(Set<? extends Element> elements) throws IOException {
        StringBuilder functions = new StringBuilder();
        StringBuilder func      = new StringBuilder();
        StringBuilder invoke    = new StringBuilder();
        for (Element element : elements) {
            Symbol.MethodSymbol symbol = (Symbol.MethodSymbol) element;

            String retrofitServiceClass = symbol.getEnclosingElement().toString();
            String retrofitBuilder = String.format("\t\tRetrofit retrofit = new Retrofit.Builder().baseUrl(\"%s\").build();\n", host);
            String getService = String.format("\t\t%s service = retrofit.create(%s.class);\n", retrofitServiceClass, retrofitServiceClass);

            String returnType = symbol.getReturnType().toString();
            String functionName = symbol.name.toString();
            func.append("\tpublic static ");
            func.append(returnType);
            func.append(functionName);
            func.append("(");

            invoke.append(functionName);
            invoke.append("(");
            for(Symbol.VarSymbol var: symbol.getParameters()){
                String parType = var.asType().toString();
                String parName = var.toString();
                func.append(parType);
                func.append(" ");
                func.append(parName);
                func.append(",");

                invoke.append(parName);
                invoke.append(",");
            }
            invoke.deleteCharAt(invoke.length()-1);
            invoke.append(")");

            func.deleteCharAt(func.length()-1);
            func.append(") {\n");
            func.append(retrofitBuilder);
            func.append(getService);
            func.append("\t\treturn service.");
            func.append(invoke);
            func.append(".observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());\n");
            func.append("\t}\n");

            functions.append(func);

            func.delete(0, func.length());
            invoke.delete(0, invoke.length());
        }
        generateRetrofit(functions);
    }

    private void generateRetrofit(StringBuilder functions) throws IOException {
        JavaFileObject source = filer.createSourceFile("in.test.ApiClient");
        Writer writer = source.openWriter();
        try {
            PrintWriter pw = new PrintWriter(writer);
            pw.println("package in.test;");
            pw.println("import retrofit2.Retrofit;");
            pw.println("import rx.android.schedulers.AndroidSchedulers;");
            pw.println("import rx.schedulers.Schedulers;");
            pw.println("public class ApiClient {");
            pw.println(functions);
            pw.println("}");
            pw.flush();
            pw.close();
        } finally {
            writer.close();
        }
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(GET.class.getCanonicalName());
        set.add(POST.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

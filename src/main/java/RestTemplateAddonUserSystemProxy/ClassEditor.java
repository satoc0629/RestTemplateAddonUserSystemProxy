package RestTemplateAddonUserSystemProxy;

import javassist.CannotCompileException;
import javassist.CtConstructor;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ClassEditor extends ExprEditor {
    @Override
    public void edit(ConstructorCall c) throws CannotCompileException {
        super.edit(c);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("bodySource.txt")))) {

            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                sb.append(br.readLine());
            }            CtConstructor ctConstructor = c.getConstructor();

            ctConstructor.insertBeforeBody(
                    sb.toString()
            );

        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        super.edit(m);

        if (!m.getClassName().equals("org.springframework.web.client.RestTemplate")) {
            return;
        }
        if (!m.getMethodName().equals("validateConverters")) {
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("bodySource.txt")))) {

            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                sb.append(br.readLine());
            }

            m.getMethod().insertBefore(
                    sb.toString()
            );
        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}

package RestTemplateAddonUserSystemProxy;

import javassist.CannotCompileException;
import javassist.CtConstructor;
import javassist.NotFoundException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;

public class ClassEditor extends ExprEditor {
    @Override
    public void edit(ConstructorCall c) throws CannotCompileException {
        super.edit(c);
        try {
            CtConstructor ctConstructor = c.getConstructor();

            ctConstructor.insertBeforeBody(
                    "this.setRequestFactory(new org.springframework.http.client.HttpComponentsClientHttpRequestFactory(" +
                    "                org.apache.http.impl.client.HttpClientBuilder.create()" +
                    "                        .useSystemProperties()" +
                    "                        .build()" +
                    "        ));"
            );

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}

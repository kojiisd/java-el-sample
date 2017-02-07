import com.sun.el.lang.FunctionMapperImpl;
import com.sun.el.lang.VariableMapperImpl;

import javax.el.*;
import java.util.HashMap;
import java.util.Map;


public class JavaElSample {
    public static void main(String... args) {
        final CompositeELResolver resolver;
        resolver = new CompositeELResolver();
        resolver.add(new ResourceBundleELResolver());
        resolver.add(new MapELResolver());

        final VariableMapper varMapper = new VariableMapperImpl(); // 借用
        final FunctionMapper funcMapper = new FunctionMapperImpl(); // 借用

        ELContext elContext = new ELContext() {
            @Override
            public ELResolver getELResolver() {
                return resolver;
            }

            @Override
            public FunctionMapper getFunctionMapper() {
                return funcMapper;
            }

            @Override
            public VariableMapper getVariableMapper() {
                return varMapper;
            }
        };


        ExpressionFactory ef = ExpressionFactory.newInstance();

        Map<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("test", "123");
        varMapper.setVariable("value", ef.createValueExpression(valueMap, valueMap.getClass()));

        String expression = "${value.test}";
        ValueExpression ve = ef.createValueExpression(elContext, expression, String.class);
        String value = (String) ve.getValue(elContext);

        System.out.println("result=" + value);
    }
}

package net.eq2online.macros.scripting.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIVersion {
  int value();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\APIVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
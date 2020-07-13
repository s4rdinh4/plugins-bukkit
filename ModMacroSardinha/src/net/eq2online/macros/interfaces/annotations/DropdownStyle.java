package net.eq2online.macros.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DropdownStyle {
  boolean hideInDropdown() default false;
  
  String dropDownText();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\annotations\DropdownStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
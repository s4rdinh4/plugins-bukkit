package br.com.sgcraft.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CommandArena {
  String permission() default "arenax1.user";
  
  String permissionMessage() default "§cYou dont have permission for this";
  
  boolean defaultCommand() default false;
  
  int args() default 1;
  
  String command() default "default";
  
  String superCommand() default "x1";
  
  String usage() default "";
}


/* Location:              C:\Users\igors\Desktop\ArenaX1-0.3.2.jar!\br\com\tinycraft\arenax1\commands\annotation\CommandArena.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
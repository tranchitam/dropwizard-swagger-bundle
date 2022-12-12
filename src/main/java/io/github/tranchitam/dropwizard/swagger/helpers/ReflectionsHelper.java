package io.github.tranchitam.dropwizard.swagger.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionsHelper {

  private static final String MEMBER_VALUES = "memberValues";

  public static Object modifyAnnotation(Annotation annotation, String key, Object newValue) {
    Object handler = Proxy.getInvocationHandler(annotation);

    Field field;
    try {
      field = handler.getClass().getDeclaredField(MEMBER_VALUES);
    } catch (NoSuchFieldException | SecurityException e) {
      throw new IllegalStateException(e);
    }
    field.setAccessible(true);

    Map<String, Object> memberValues;
    try {
      memberValues = (Map<String, Object>) field.get(handler);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
    Object oldValue = memberValues.get(key);
    if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
      throw new IllegalArgumentException();
    }
    memberValues.put(key, newValue);

    return oldValue;
  }
}

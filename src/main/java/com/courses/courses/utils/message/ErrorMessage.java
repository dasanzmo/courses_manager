package com.courses.courses.utils.message;

public class ErrorMessage {

  public static String idNotFound(String entity) {
      final String message = "No hay %s registrado con este id";
       return String.format(message, entity);
  }

  public static String idNotFound(String entity, String role) {
    final String message = "%s id is not valid for the role %s";
     return String.format(message, entity, role);
}

}

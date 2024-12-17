package com.plantilla.apiestudiantes.exception;

import lombok.Getter;

@Getter
public class DataBaseException extends RuntimeException {
  private final Long entityId;      // ID de la entidad (Curso, Tema, etc.)
  private final String entityType;  // Tipo de la entidad (Curso, Tema, etc.)
  private final String entityName;  // Nombre de la entidad (por ejemplo, "Curso de Java")
  private final String Operation; // Operación (Ejemplo: Save, update)
  private final String rootCause;   // Causa raíz del error (mensaje de excepción original)

  // Constructor con detalles de la entidad y causa raíz
  public DataBaseException(String message, String entityType, Long entityId, String entityName, String operation, String rootCause) {
    super(message);
    this.entityId = entityId;
    this.entityType = entityType;
    this.entityName = entityName;
    this.Operation = operation;
    this.rootCause = rootCause;
  }

}

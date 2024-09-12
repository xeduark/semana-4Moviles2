# semana-4Moviles2

## Ejercicio 1: Configuración de SQLite Browser y Creación de Base de Datos

**Enunciado:**

Configura SQLite Browser y crea una nueva base de datos llamada `contactos.db`. Crea una tabla llamada `Contactos` con los siguientes campos:

- **id** (entero, clave primaria)
- **nombre** (texto)
- **telefono** (texto)
```sql

-- CREATE DATABASE contactos.db;

-- Crear la tabla contactos
CREATE TABLE contactos (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  nombre TEXT,
  telefono TEXT
);
```
# Ejercicio 1B: Ampliación de la Tabla Contactos, Inserción de Múltiples Registros y Consultas en SQLite Browser

**Enunciado:**

Amplía la tabla `Contactos` creada en el ejercicio 1 agregando los siguientes campos:

- **email** (texto)
- **direccion** (texto)
- **fecha_nacimiento** (texto)
```sql
 ALTER TABLE contactos ADD COLUMN
  email TEXT;
  ALTER TABLE contactos ADD COLUMN
  dirección TEXT;
  ALTER TABLE contactos ADD COLUMN
  fecha_nacimiento TEXT;
```
Después de agregar los nuevos campos, inserta al menos 5 registros en la tabla `Contactos` con diferentes datos. Asegúrate de incluir los siguientes campos:

- **nombre**
- **telefono**
- **email**
- **direccion**
- **fecha_nacimiento**

## Código SQL para Ampliar la Tabla y Insertar Registros

### 1. Ampliar la Tabla `Contactos`

```sql
-- Agregar nuevos campos a la tabla contactos
ALTER TABLE contactos ADD COLUMN email TEXT;
ALTER TABLE contactos ADD COLUMN direccion TEXT;
ALTER TABLE contactos ADD COLUMN fecha_nacimiento TEXT;
```
## Registros a Insertar en la Tabla Contactos

1. **Registro 1:**
    - **nombre:** John Doe
    - **telefono:** 1234567890
    - **email:** john.doe@example.com
    - **direccion:** 123 Main Street
    - **fecha_nacimiento:** 1990-05-15

2. **Registro 2:**
    - **nombre:** Jane Smith
    - **telefono:** 9876543210
    - **email:** jane.smith@example.com
    - **direccion:** 456 Elm Street
    - **fecha_nacimiento:** 1985-09-20

3. **Registro 3:**
    - **nombre:** Bob Johnson
    - **telefono:** 5551234567
    - **email:** bob.johnson@example.com
    - **direccion:** 789 Oak Street
    - **fecha_nacimiento:** 1978-12-01

4. **Registro 4:**
    - **nombre:** Alice Williams
    - **telefono:** 3216549870
    - **email:** alice.williams@example.com
    - **direccion:** 101 Maple Street
    - **fecha_nacimiento:** 1992-07-11

5. **Registro 5:**
    - **nombre:** Michael Brown
    - **telefono:** 6543217890
    - **email:** michael.brown@example.com
    - **direccion:** 202 Pine Street
    - **fecha_nacimiento:** 1988-03-25
```sql
-- Insertar registros en la tabla contactos
INSERT INTO contactos (nombre, telefono, email, dirección, fecha_nacimiento) VALUES
('John Doe', '1234567890', 'john.doe@example.com', '123 Main Street', '1990-05-15'),
('Jane Smith', '9876543210', 'jane.smith@example.com', '456 Elm Street', '1985-09-20'),
('Bob Johnson', '5551234567', 'bob.johnson@example.com', '789 Oak Street', '1978-12-01'),
('Alice Williams', '3216549870', 'alice.williams@example.com', '101 Maple Street', '1992-07-11'),
('Michael Brown', '6543217890', 'michael.brown@example.com', '202 Pine Street', '1988-03-25');
```
## Consultas SQL para Ejecutar en SQLite Browser

1. **Consulta 1:** Selecciona todos los contactos que tengan el nombre "John Doe".

    ```sql
    SELECT * FROM contactos
    WHERE nombre = 'John Doe';
    ```

2. **Consulta 2:** Selecciona todos los contactos cuyo teléfono empiece con "123".

    ```sql
    SELECT * FROM contactos
    WHERE telefono LIKE '123%';
    ```

3. **Consulta 3:** Selecciona todos los contactos ordenados por `fecha_nacimiento` de más reciente a más antiguo.

    ```sql
    SELECT * FROM contactos
    ORDER BY fecha_nacimiento DESC;
    ```

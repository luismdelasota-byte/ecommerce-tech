#  Ecommerce Tech - Plataforma de Comercio Electrónico

¡Bienvenido/a a Ecommerce Tech Este es un proyecto de comercio electrónico Fullstack diseñado bajo una arquitectura robusta, modular y escalable, aplicando las mejores prácticas de desarrollo.

Estado del proyecto: En desarrollo activo.

---

##  Características del Proyecto

* Módulo de Productos (`product`): Catálogo de productos, categorías y stock.
* Módulo de Usuarios (`user` y `profileUser`): Cuentas de usuario y perfiles de contacto.
* Módulo de Carrito (`car`): Gestión de carrito de compras por usuario.
* Módulo de Órdenes (`order` y `orderDetail`): Procesamiento de compras y desglose de artículos.
* Módulo de Pagos (`pay`): Simulación e integración de pasarelas de pago.
* Seguridad y Autenticación (`auth` y `security`): Registro, inicio de sesión y protección con JWT.

---

##  Tecnologías Utilizadas

##  Backend (Carpeta `/backend`)
* Lenguaje: Java 21
* Framework Principal: Spring Boot
* Acceso a Datos: Spring Data JPA & Hibernate
* Seguridad: Spring Security (Autenticación y Autorización basada en Roles)
* Gestor de Dependencias: Maven
* Bases de Datos:
  * MySQL (Base de datos real para Producción/Desarrollo persistente)
  * H2 Database (Base de datos en memoria para Desarrollo Rápido y Testing)
* Herramientas de productividad: Lombok

##  Frontend (Carpeta `/frontend`)
* Proximamente en desarrollo (React, Node.js, TypeScript), se creará carpeta en VSCode*

---

##  Arquitectura y Estructura Modular

Este proyecto utiliza una Arquitectura Híbrida orientada a Características y estructurada internamente en Capas (Layer).

##  Estructura de Paquetes en el Backend

A continuación se muestra el esquema del proyecto. Usamos el módulo `product` como ejemplo para ilustrar la estructura interna de las tablas de negocio, mientras que detallamos las carpetas de autenticación y seguridad por su naturaleza especial:

```text
com.ecommercetech/
│
├── product/                      # ─── EJEMPLO DE MÓDULO CRUD (Ejemplo de tabla) ───
│   ├── controller/               # Endpoints de la API REST (@RestController)
│   ├── service/                  # Lógica de negocio e implementaciones (@Service)
│   ├── repository/               # Consultas y acceso a base de datos (JpaRepository)
│   ├── model/                    # Entidades JPA que representan tablas (@Entity)
│   └── dto/                      # Objetos para transferencia de datos (Request/Response)
│
├── auth/                         # ─── MÓDULO DE AUTENTICACIÓN ───
│   ├── controller/               # Endpoints de login y registro (/api/auth)
│   ├── service/                  # Lógica de autenticación de credenciales
│   └── dto/                      # DTOs de credenciales (UserRequest, AuthResponse)
│
├── security/                     # ─── CONFIGURACIÓN DE SEGURIDAD GENERAL ───
│   ├── jwt/                      # Filtro JWT, generación y validación de tokens
│   ├── config/                   # Configuración web, CORS y encriptación de claves
│   └── service/                  # Servicios de seguridad personalizados
│
# Los siguientes módulos siguen EXACTAMENTE la misma estructura que "product":
├── car/                          # Módulo para el Carrito de compras
├── order/                        # Módulo para las Órdenes de compra (Cabeceras)
├── orderDetail/                  # Módulo para el Detalle de órdenes (Items)
├── pay/                          # Módulo para la Pasarela de pagos
├── profileUser/                  # Módulo para los Datos adicionales de perfiles
└── user/                         # Módulo para la gestión básica de cuentas de usuario
```

##  Flujo de Datos

```
[Cliente / Frontend] 
       │ (Petición HTTP con JSON)
       ▼
[Controller] (Recibe el RequestDTO y valida la estructura de datos)
       │
       ▼
[Service] (Aplica lógica de negocio: stock, descuentos, etc. Mapea DTO ⇄ Entidad)
       │
       ▼
[Repository] (Se comunica con la Base de Datos mediante JPA)
       │
       ▼
  [Base de Datos] (MySQL en Prod / H2 en Dev y Tests)
```

---

##  Configuración de Entornos (Perfiles de Spring Boot)

El backend está configurado con Spring Profiles para facilitar el desarrollo local sin obligar a tener configuraciones externas listas:

| Perfil           | Ubicación        | Base de Datos | ¿Cuándo se usa?                                                     |
| `dev` (Default)  | `main/resources` | H2 (En RAM)   | Para desarrollo diario en tu computadora local.                     |
| `prod`           | `main/resources` | MySQL (Disco) | Para despliegues reales y bases de datos permanentes.               |
| `test`           | `test/resources` | H2 (En RAM)   | Se activa automáticamente al correr pruebas unitarias (`mvn test`)  |       

---

##  Cómo Empezar en Local (Modo Desarrollo)

### Prerrequisitos
* Tener instalado Java 17 o superior, este caso se esta usando Java 21.
* Tener instalado Maven (o usar el wrapper `./mvnw` incluido).

## Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/luisdelasota-byte/ecommerce-tech.git
   cd ecommerce-tech/ecommerce-system
   ```
2. Ejecuta la aplicación en modo desarrollo (H2):
   ```bash
   ./mvnw spring-boot:run
   ```
3. La aplicación estará corriendo en: http://localhost:8087
4. Puedes acceder a la consola interactiva de la base de datos H2 en: http://localhost:8087/h2-console
   * JDBC URL: `jdbc:h2:mem:ecommerce_tech`
   * Usuario: `sa`
   * Contraseña: `123`

---

## Ejecución de Pruebas

Para garantizar que el código no se rompa al realizar cambios, puedes ejecutar las pruebas automatizadas de forma segura:

```bash
./mvnw test
```

# CacaoSelva - Prueba de Concepto

Proyecto Maven multimódulo basado en la Sesión 2 del PDF: Arquitectura Limpia, SOLID, API REST, Desktop JavaFX y Monitor.

## Requisitos
- JDK 21
- Maven 3.9+
- Visual Studio Code + Extension Pack for Java
- Postman (opcional)

## Abrir en VS Code
1. Descomprime `cacaoselva.zip`.
2. Abre la carpeta `cacaoselva` en VS Code.
3. Espera a que Maven/JAVA cargue los módulos.

## Ejecutar API
Desde la raíz:
`mvn clean install`
`mvn -pl api spring-boot:run`

API: `http://localhost:5080/lotes`

## Ejecutar Desktop
Con la API funcionando, en otra terminal:
`mvn -pl desktop javafx:run`

## Ejecutar Monitor
Con la API funcionando, en otra terminal:
`mvn -pl monitor exec:java`

Cada 10 segundos mostrará `Pendientes: 2`.

## Cliente web
Abre `web/index.html` con Live Server de VS Code o un navegador. CORS está habilitado para esta PoC.

## Endpoints
- GET /lotes -> 3 lotes
- GET /lotes/1 -> lote de Ana
- GET /lotes/999 -> 404
- GET /lotes/abc -> 400
- GET /lotes/0 -> 400

## Datos
Ana: 120.5 kg, PENDIENTE
Luis: 80 kg, LIQUIDADO
Rosa: 95.25 kg, PENDIENTE
Peso total: 295.75 kg. Pendientes: 2.

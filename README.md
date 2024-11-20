# Diario_dam_back
Repositorio del Backend del Proyecto DAM 2024-2025

API que conecta con la BBDD.

- Version Java: 17
- Las dependencias para Oracle, sirven a partir de Java 8 (ojdbc8)
- Usa lombok, no hace falta generar getters y setter (no hace falta que hagais nada, ya lo carga el programa)
- Si necesitais cambiar usuario y contraseña, se hace desde application-properties:
    - spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xepdb1
    - spring.datasource.username=PRUEBADAM4
    - spring.datasource.password=PRUEBADAM4

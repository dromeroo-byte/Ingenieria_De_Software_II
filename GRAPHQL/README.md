## ¿Qué diferencia encontré vs REST?

| Característica | REST | GraphQL |
|---|---|---|
| Endpoints | Uno por recurso (/character, /episode) | Un solo endpoint para todo |
| Datos recibidos | Los que la API decide | Los que yo pido |
| Código de error | 404, 500, etc. | Siempre 200, errores en el body |
| Overfetching | Recibes campos que no necesitas | Recibes solo lo que pediste |

## ¿Cuántos requests REST necesitaría para mi query más compleja?

Mi query más compleja fue `PaisConRelaciones`, que en un solo request trajo:
- Datos del país → 1 request REST
- Su continente → 1 request REST adicional  
- Sus idiomas → 1 request REST adicional
- Sus estados/departamentos → 1 request REST adicional

**Total: 4 requests REST = 1 query GraphQL**

## ¿En qué proyecto real usaría GraphQL?

Una app móvil de viajes donde el usuario consulta países,
sus idiomas, moneda y clima. En mobile, cada request extra
consume batería y datos. GraphQL reduciría 4 llamadas a 1,
mejorando el rendimiento y la experiencia del usuario.

## Capturas

**Query - Todos los países**

![Image 1](media/Query_Todos_los_paises.png)

**Tests Query - Todos los países**

![Image 2](media/Tests_Query_Todos_los_paises.png)

**Query - País por código**

![Image 3](media/Query_Pais_por_codigo.png)

**Tests Query - País por código**

![Image 4](media/Tests_Query_Pais_por_codigo.png)

**Query - Continentes con países**

![Image 5](media/Query_Continentes_con_paises.png)

**Tests Query - Continentes con países**

![Image 6](media/Tests_Query_Continentes_con_paises.png)

**Query - Anidada País con continente e idiomas**

![Image 7](media/Query_anidada_Pais_con_contiente_e_idiomas.png)

**Tests Query - Anidada País con continente e idiomas**

![Image 8](media/Tests_Query_anidada_Pais_con_contiente_e_idiomas.png)

**Query - Con variables País dinámico**

![Image 9](media/Query_con_variables_Pais_dinamico.png)

**Tests Query - Con variables País dinámico**

![Image 10](media/Tests_Query_con_variables_Pais_dinamico.png)
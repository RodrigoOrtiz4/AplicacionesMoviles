package com.example.miaplicacionotravez

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// =====================================================================
// Punto de entrada de la app (esto es lo que ya llama tu MainActivity /
// MainViewController / main.kt de cada plataforma).
// =====================================================================
@Composable
fun App() {
    val modoOscuro = isSystemInDarkTheme()
    val esquemaColores = if (modoOscuro) esquemaOscuro() else esquemaClaro()

    MaterialTheme(colorScheme = esquemaColores) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PantallaFormulario(modoOscuro = modoOscuro)
        }
    }
}

// =====================================================================
// Modelo de datos + serialización manual a JSON (sin dependencias extra:
// no requiere kotlinx.serialization, solo construimos el texto a mano).
// =====================================================================
private data class RegistroEntrega(
    val nombre: String,
    val matricula: String,
    val asignatura: String,
    val hora: String,
    val fechaEntrega: String
)

private fun escaparJson(texto: String): String = buildString {
    for (c in texto) {
        when (c) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> append(c)
        }
    }
}

private fun RegistroEntrega.aJson(): String = """{
  "nombre": "${escaparJson(nombre)}",
  "matricula": "${escaparJson(matricula)}",
  "asignatura": "${escaparJson(asignatura)}",
  "hora": "${escaparJson(hora)}",
  "fechaEntrega": "${escaparJson(fechaEntrega)}"
}"""

/** Serializa todo el historial guardado en la sesión como un arreglo JSON. */
private fun List<RegistroEntrega>.aJsonArray(): String {
    if (isEmpty()) return "[]"
    val cuerpo = joinToString(",\n") { registro ->
        registro.aJson().lines().joinToString("\n") { linea -> "  $linea" }
    }
    return "[\n$cuerpo\n]"
}

// =====================================================================
// Utilidades de fecha/hora sin dependencias externas (100% Kotlin común,
// funciona en Android, iOS, Desktop y Web igual).
// =====================================================================
private fun dosDigitos(n: Int): String = n.toString().padStart(2, '0')

private fun formatoFecha(dia: Int, mes: Int, anio: Int): String =
    "${dosDigitos(dia)}/${dosDigitos(mes)}/${anio.toString().padStart(4, '0')}"

/**
 * Día de hoy expresado como "epoch day" (días desde 1970-01-01), en UTC.
 * Usa kotlin.time.Clock, disponible en la stdlib desde Kotlin 2.1 sin
 * dependencias adicionales. Si tu proyecto usa una versión de Kotlin
 * anterior y esto no compila, reemplaza el import por
 * "import kotlinx.datetime.Clock" (agregando la dependencia
 * "org.jetbrains.kotlinx:kotlinx-datetime") — el resto del código no cambia.
 */
@OptIn(ExperimentalTime::class)
private fun hoyEpochDay(): Long = Clock.System.now().toEpochMilliseconds() / 86_400_000L

/**
 * Convierte un "epoch day" a (año, mes, día). Algoritmo de dominio público
 * de Howard Hinnant (civil_from_days), puro cálculo entero, sin librerías.
 */
private fun fechaCivilDesdeEpochDay(epochDay: Long): Triple<Int, Int, Int> {
    val z = epochDay + 719468
    val era = (if (z >= 0) z else z - 146096) / 146097
    val doe = z - era * 146097
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    val y = yoe + era * 400
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val d = doy - (153 * mp + 2) / 5 + 1
    val m = if (mp < 10) mp + 3 else mp - 9
    val anio = if (m <= 2) y + 1 else y
    return Triple(anio.toInt(), m.toInt(), d.toInt())
}

// =====================================================================
// Colores (equivalentes a los que usamos en la versión Android nativa)
// =====================================================================
private fun esquemaClaro() = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF00897B),
    background = Color(0xFFF5F7FA),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1F)
)

private fun esquemaOscuro() = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF0D1B2A),
    secondary = Color(0xFF4DB6AC),
    background = Color(0xFF121316),
    surface = Color(0xFF1D1F23),
    onSurface = Color(0xFFE3E2E6)
)

/** Colores de la tarjeta de resultado (error en rojo, éxito en verde). */
private fun coloresResultado(esError: Boolean, modoOscuro: Boolean): Pair<Color, Color> = when {
    esError && !modoOscuro -> Color(0xFFFDECEA) to Color(0xFFB71C1C)
    esError && modoOscuro -> Color(0xFF3A1414) to Color(0xFFFFCDD2)
    !esError && !modoOscuro -> Color(0xFFE8F5E9) to Color(0xFF1B5E20)
    else -> Color(0xFF0F2A12) to Color(0xFFC8E6C9)
}

private val ASIGNATURAS = listOf(
    "Programación Móvil",
    "Bases de Datos",
    "Estructuras de Datos",
    "Redes de Computadoras",
    "Matemáticas Discretas",
    "Inglés Técnico"
)

// =====================================================================
// Pantalla principal: formulario + validación + tarjeta de resultado
// + historial en JSON (guardado en memoria durante la sesión).
// =====================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantallaFormulario(modoOscuro: Boolean) {

    // --- Estado de los campos ---
    var nombre by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf<String?>(null) }

    var matricula by remember { mutableStateOf("") }
    var errorMatricula by remember { mutableStateOf<String?>(null) }

    var asignatura by remember { mutableStateOf("") }
    var errorAsignatura by remember { mutableStateOf<String?>(null) }

    var hora by remember { mutableStateOf("") }
    var errorHora by remember { mutableStateOf<String?>(null) }
    var mostrarSelectorHora by remember { mutableStateOf(false) }
    val estadoHora = rememberTimePickerState(initialHour = 8, initialMinute = 0, is24Hour = true)

    var fecha by remember { mutableStateOf("") }
    var fechaEpochDay by remember { mutableStateOf<Long?>(null) }
    var errorFecha by remember { mutableStateOf<String?>(null) }
    var mostrarSelectorFecha by remember { mutableStateOf(false) }
    val estadoFecha = rememberDatePickerState(
        selectableDates = remember {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    utcTimeMillis / 86_400_000L >= hoyEpochDay()
            }
        }
    )

    // --- Estado del resultado (tarjeta de error / éxito) ---
    var resultadoEsError by remember { mutableStateOf<Boolean?>(null) } // null = aún no se intentó guardar
    var mensajesResultado by remember { mutableStateOf(listOf<String>()) }
    var jsonUltimoRegistro by remember { mutableStateOf("") }

    // --- "Almacén" en memoria de todos los registros guardados en JSON ---
    var historial by remember { mutableStateOf(listOf<RegistroEntrega>()) }
    var mostrarJson by remember { mutableStateOf(false) }

    fun guardar() {
        val errores = mutableListOf<String>()

        val nombreLimpio = nombre.trim()
        errorNombre = when {
            nombreLimpio.isEmpty() -> "El nombre es obligatorio."
            nombreLimpio.length < 3 -> "El nombre debe tener al menos 3 caracteres."
            !Regex("^[A-Za-zÁÉÍÓÚÑÜáéíóúñü ]+$").matches(nombreLimpio) ->
                "El nombre solo puede contener letras y espacios."
            else -> null
        }
        errorNombre?.let { errores += it }

        val matriculaLimpia = matricula.trim()
        errorMatricula = when {
            matriculaLimpia.isEmpty() -> "La matrícula es obligatoria."
            matriculaLimpia.length !in 4..10 -> "La matrícula debe tener entre 4 y 10 dígitos."
            else -> null
        }
        errorMatricula?.let { errores += it }

        errorAsignatura = if (asignatura.isEmpty() || asignatura !in ASIGNATURAS) {
            "Selecciona una asignatura válida de la lista."
        } else null
        errorAsignatura?.let { errores += it }

        errorHora = if (hora.isEmpty()) "Selecciona la hora en que se imparte la asignatura." else null
        errorHora?.let { errores += it }

        errorFecha = when {
            fecha.isEmpty() || fechaEpochDay == null -> "Selecciona la fecha de entrega."
            fechaEpochDay!! < hoyEpochDay() -> "La fecha de entrega no puede ser anterior al día de hoy."
            else -> null
        }
        errorFecha?.let { errores += it }

        if (errores.isNotEmpty()) {
            resultadoEsError = true
            mensajesResultado = errores
            jsonUltimoRegistro = ""
        } else {
            val registro = RegistroEntrega(
                nombre = nombreLimpio,
                matricula = matriculaLimpia,
                asignatura = asignatura,
                hora = hora,
                fechaEntrega = fecha
            )
            historial = historial + registro          // <- aquí se "almacena" el registro
            jsonUltimoRegistro = registro.aJson()      // <- y aquí se serializa a JSON
            resultadoEsError = false
            mensajesResultado = listOf(
                "Nombre:  ${registro.nombre}",
                "Matrícula:  ${registro.matricula}",
                "Asignatura:  ${registro.asignatura}",
                "Hora:  ${registro.hora}",
                "Fecha de entrega:  ${registro.fechaEntrega}"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text("Entrega de Tarea", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            "Completa todos los campos para registrar la entrega",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; errorNombre = null },
            label = { Text("Nombre completo") },
            singleLine = true,
            isError = errorNombre != null,
            supportingText = { errorNombre?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Matrícula (solo dígitos)
        OutlinedTextField(
            value = matricula,
            onValueChange = { nuevo ->
                matricula = nuevo.filter { it.isDigit() }.take(10)
                errorMatricula = null
            },
            label = { Text("Matrícula") },
            singleLine = true,
            isError = errorMatricula != null,
            supportingText = { errorMatricula?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Asignatura (selección)
        CampoAsignatura(
            opciones = ASIGNATURAS,
            seleccion = asignatura,
            error = errorAsignatura,
            alSeleccionar = { asignatura = it; errorAsignatura = null }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Hora en que se imparte
        CampoSeleccionable(
            etiqueta = "Hora en que se imparte",
            valor = hora,
            error = errorHora,
            iconoTexto = "🕐",
            alHacerClic = { mostrarSelectorHora = true }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fecha de entrega
        CampoSeleccionable(
            etiqueta = "Fecha de entrega",
            valor = fecha,
            error = errorFecha,
            iconoTexto = "📅",
            alHacerClic = { mostrarSelectorFecha = true }
        )
        Spacer(modifier = Modifier.height(28.dp))

        // Botón Guardar
        Button(
            onClick = { guardar() },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Guardar", fontSize = 16.sp)
        }

        // Tarjeta de resultado: errores, o datos guardados + su JSON
        resultadoEsError?.let { esError ->
            val (colorFondo, colorTexto) = coloresResultado(esError, modoOscuro)
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorFondo)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = if (esError) "⚠ Se encontraron ${mensajesResultado.size} error(es)"
                        else "✔ Datos guardados correctamente",
                        color = colorTexto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = mensajesResultado.joinToString("\n") { if (esError) "•  $it" else it },
                        color = colorTexto,
                        fontSize = 15.sp
                    )
                    if (!esError) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("JSON:", color = colorTexto, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = jsonUltimoRegistro,
                            color = colorTexto,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Historial acumulado (todos los registros guardados en esta sesión, en JSON)
        if (historial.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Historial guardado (${historial.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Row {
                            TextButton(onClick = { mostrarJson = !mostrarJson }) {
                                Text(if (mostrarJson) "Ocultar JSON" else "Ver JSON")
                            }
                            TextButton(onClick = { historial = emptyList() }) {
                                Text("Limpiar")
                            }
                        }
                    }
                    if (mostrarJson) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = historial.aJsonArray(),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

    // Diálogo selector de hora (Material3 no trae un TimePickerDialog listo:
    // se arma envolviendo TimePicker en un AlertDialog, patrón estándar).
    if (mostrarSelectorHora) {
        AlertDialog(
            onDismissRequest = { mostrarSelectorHora = false },
            confirmButton = {
                TextButton(onClick = {
                    hora = "${dosDigitos(estadoHora.hour)}:${dosDigitos(estadoHora.minute)}"
                    errorHora = null
                    mostrarSelectorHora = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarSelectorHora = false }) { Text("Cancelar") }
            },
            text = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TimePicker(state = estadoHora)
                }
            }
        )
    }

    // Diálogo selector de fecha (no permite elegir fechas anteriores a hoy)
    if (mostrarSelectorFecha) {
        DatePickerDialog(
            onDismissRequest = { mostrarSelectorFecha = false },
            confirmButton = {
                TextButton(onClick = {
                    estadoFecha.selectedDateMillis?.let { millis ->
                        val epochDay = millis / 86_400_000L
                        val (anio, mes, dia) = fechaCivilDesdeEpochDay(epochDay)
                        fechaEpochDay = epochDay
                        fecha = formatoFecha(dia, mes, anio)
                        errorFecha = null
                    }
                    mostrarSelectorFecha = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarSelectorFecha = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = estadoFecha)
        }
    }
}

// =====================================================================
// Campo de selección (spinner) para la asignatura
// =====================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CampoAsignatura(
    opciones: List<String>,
    seleccion: String,
    error: String?,
    alSeleccionar: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = it }) {
        OutlinedTextField(
            value = seleccion,
            onValueChange = {},
            readOnly = true,
            label = { Text("Asignatura") },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            // Si tu versión de Material3 marca menuAnchor() (sin argumentos) como
            // obsoleto, cámbialo por: .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        alSeleccionar(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

// =====================================================================
// Campo de solo lectura que abre un diálogo al tocarlo (hora / fecha)
// =====================================================================
@Composable
private fun CampoSeleccionable(
    etiqueta: String,
    valor: String,
    error: String?,
    iconoTexto: String,
    alHacerClic: () -> Unit
) {
    Box {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            trailingIcon = { Text(iconoTexto, fontSize = 18.sp) },
            modifier = Modifier.fillMaxWidth()
        )
        // Caja transparente encima del campo: así el toque abre el diálogo
        // sin que el TextField intente mostrar el teclado (patrón estándar
        // recomendado para campos "de solo selección" en Compose).
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = alHacerClic)
        )
    }
}

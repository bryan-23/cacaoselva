// =====================================================
// CONFIGURACIÓN
// =====================================================

const API_URL = "http://localhost:5080";


// =====================================================
// VARIABLES
// =====================================================

let lotes = [];


// =====================================================
// INICIAR SISTEMA
// =====================================================

document.addEventListener("DOMContentLoaded", function () {

    console.log("=================================");
    console.log("CacaoSelva iniciado");
    console.log("API:", API_URL);
    console.log("=================================");

    cargarLotes();

});


// =====================================================
// CAMBIAR DE SECCIÓN
// =====================================================

function mostrarSeccion(nombreSeccion) {

    const secciones = document.querySelectorAll(".seccion");

    // Ocultar todas las secciones
    secciones.forEach(seccion => {
        seccion.classList.add("oculto");
    });


    // Mostrar la sección seleccionada
    const seccion = document.getElementById(nombreSeccion);

    if (seccion) {
        seccion.classList.remove("oculto");
    }


    // Cambiar título
    const titulo = document.getElementById("tituloPagina");

    const titulos = {
        dashboard: "Dashboard",
        lotes: "Lotes",
        registrar: "Registrar lote",
        reportes: "Reportes"
    };

    if (titulo) {
        titulo.textContent =
            titulos[nombreSeccion] || "CacaoSelva";
    }


    // Cambiar botón activo
    const botones = document.querySelectorAll(".menu");

    botones.forEach(boton => {
        boton.classList.remove("active");
    });


    botones.forEach(boton => {

        const texto = boton.textContent.toLowerCase();

        if (
            (nombreSeccion === "dashboard" && texto.includes("dashboard")) ||
            (nombreSeccion === "lotes" && texto.includes("lotes")) ||
            (nombreSeccion === "registrar" && texto.includes("registrar")) ||
            (nombreSeccion === "reportes" && texto.includes("reportes"))
        ) {
            boton.classList.add("active");
        }

    });

}


// =====================================================
// CARGAR LOTES DESDE LA API
// =====================================================

async function cargarLotes() {

    console.log("Cargando lotes...");

    try {

        const respuesta = await fetch(`${API_URL}/lotes`);


        if (!respuesta.ok) {

            throw new Error(
                `Error HTTP ${respuesta.status}`
            );

        }


        lotes = await respuesta.json();


        console.log("Lotes recibidos:", lotes);


        // Actualizar dashboard
        actualizarDashboard();


        // Mostrar tabla principal
        mostrarTablaDashboard();


        // Mostrar tabla de lotes
        mostrarTablaLotes(lotes);


        // Actualizar reportes
        actualizarReportes();


    } catch (error) {

        console.error(
            "Error al cargar los lotes:",
            error
        );


        const tablaDashboard =
            document.getElementById("tablaDashboard");

        const tablaLotes =
            document.getElementById("tablaLotes");


        if (tablaDashboard) {

            tablaDashboard.innerHTML = `
                <tr>
                    <td colspan="4" style="text-align:center;">
                        ❌ No se pudieron cargar los lotes.
                    </td>
                </tr>
            `;

        }


        if (tablaLotes) {

            tablaLotes.innerHTML = `
                <tr>
                    <td colspan="5" style="text-align:center;">
                        ❌ No se pudieron cargar los lotes.
                        <br>
                        Verifica que la API esté ejecutándose
                        en http://localhost:5080
                    </td>
                </tr>
            `;

        }

    }

}


// =====================================================
// ACTUALIZAR DASHBOARD
// =====================================================

function actualizarDashboard() {

    const total =
        lotes.length;


    const pendientes =
        lotes.filter(
            lote => lote.estado === "PENDIENTE"
        ).length;


    const liquidados =
        lotes.filter(
            lote => lote.estado === "LIQUIDADO"
        ).length;


    const peso =
        lotes.reduce(
            (suma, lote) =>
                suma + Number(lote.pesoKg || 0),
            0
        );


    // Total
    const elementoTotal =
        document.getElementById("totalLotes");

    if (elementoTotal) {
        elementoTotal.textContent = total;
    }


    // Pendientes
    const elementoPendientes =
        document.getElementById("totalPendientes");

    if (elementoPendientes) {
        elementoPendientes.textContent =
            pendientes;
    }


    // Liquidados
    const elementoLiquidados =
        document.getElementById("totalLiquidados");

    if (elementoLiquidados) {
        elementoLiquidados.textContent =
            liquidados;
    }


    // Peso
    const elementoPeso =
        document.getElementById("pesoTotal");

    if (elementoPeso) {
        elementoPeso.textContent =
            `${peso.toFixed(2)} kg`;
    }

}


// =====================================================
// TABLA DEL DASHBOARD
// =====================================================

function mostrarTablaDashboard() {

    const tabla =
        document.getElementById("tablaDashboard");


    if (!tabla) {
        return;
    }


    tabla.innerHTML = "";


    // Mostrar máximo 5 lotes
    const ultimosLotes =
        lotes.slice(-5).reverse();


    if (ultimosLotes.length === 0) {

        tabla.innerHTML = `
            <tr>
                <td colspan="4" style="text-align:center;">
                    No hay lotes registrados.
                </td>
            </tr>
        `;

        return;
    }


    ultimosLotes.forEach(lote => {

        const fila =
            document.createElement("tr");


        fila.innerHTML = `
            <td>${lote.id ?? "-"}</td>

            <td>${lote.socio ?? "-"}</td>

            <td>
                ${Number(lote.pesoKg || 0).toFixed(2)} kg
            </td>

            <td>
                <span class="estado ${obtenerClaseEstado(lote.estado)}">
                    ${lote.estado ?? "-"}
                </span>
            </td>
        `;


        tabla.appendChild(fila);

    });

}


// =====================================================
// TABLA COMPLETA DE LOTES
// =====================================================

function mostrarTablaLotes(lista) {

    const tabla =
        document.getElementById("tablaLotes");


    if (!tabla) {
        return;
    }


    tabla.innerHTML = "";


    if (lista.length === 0) {

        tabla.innerHTML = `
            <tr>
                <td colspan="5" style="text-align:center;">
                    No se encontraron lotes.
                </td>
            </tr>
        `;

        return;
    }


    lista.forEach(lote => {

        const fila =
            document.createElement("tr");


        fila.innerHTML = `
            <td>
                ${lote.id ?? "-"}
            </td>

            <td>
                ${lote.socio ?? "-"}
            </td>

            <td>
                ${Number(lote.pesoKg || 0).toFixed(2)} kg
            </td>

            <td>
                <span class="estado ${obtenerClaseEstado(lote.estado)}">
                    ${lote.estado ?? "-"}
                </span>
            </td>

            <td>
                <button
                    class="btn-ver"
                    onclick="verDetalle(${lote.id})">
                    Ver
                </button>
            </td>
        `;


        tabla.appendChild(fila);

    });

}


// =====================================================
// CLASE PARA EL ESTADO
// =====================================================

function obtenerClaseEstado(estado) {

    if (estado === "PENDIENTE") {
        return "pendiente";
    }


    if (estado === "LIQUIDADO") {
        return "liquidado";
    }


    return "";

}


// =====================================================
// BUSCAR Y FILTRAR LOTES
// =====================================================

function filtrarLotes() {

    const campoBusqueda =
        document.getElementById("busqueda");


    const campoEstado =
        document.getElementById("filtroEstado");


    const texto =
        campoBusqueda
            ? campoBusqueda.value
                .toLowerCase()
                .trim()
            : "";


    const estado =
        campoEstado
            ? campoEstado.value
            : "";


    const resultado =
        lotes.filter(lote => {

            const socio =
                String(lote.socio || "")
                    .toLowerCase();


            const coincideSocio =
                socio.includes(texto);


            const coincideEstado =
                estado === "" ||
                lote.estado === estado;


            return (
                coincideSocio &&
                coincideEstado
            );

        });


    mostrarTablaLotes(resultado);

}


// =====================================================
// REGISTRAR NUEVO LOTE
// =====================================================

const formulario =
    document.getElementById("formLote");


if (formulario) {

    formulario.addEventListener(
        "submit",
        async function (evento) {

            evento.preventDefault();


            const socio =
                document
                    .getElementById("socio")
                    .value
                    .trim();


            const pesoKg =
                document
                    .getElementById("pesoKg")
                    .value;


            const estado =
                document
                    .getElementById("estado")
                    .value;


            // -----------------------------------------
            // VALIDACIONES
            // -----------------------------------------

            if (socio === "") {

                alert(
                    "Ingrese el nombre del socio."
                );

                return;
            }


            if (
                pesoKg === "" ||
                Number(pesoKg) <= 0
            ) {

                alert(
                    "Ingrese un peso mayor que 0."
                );

                return;
            }


            if (estado === "") {

                alert(
                    "Seleccione un estado."
                );

                return;
            }


            // -----------------------------------------
            // DATOS
            // -----------------------------------------

            const nuevoLote = {

                socio: socio,

                pesoKg: Number(pesoKg),

                estado: estado

            };


            console.log(
                "Enviando:",
                nuevoLote
            );


            try {

                const respuesta =
                    await fetch(
                        `${API_URL}/lotes`,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    nuevoLote
                                )
                        }
                    );


                if (!respuesta.ok) {

                    const mensaje =
                        await respuesta.text();


                    throw new Error(
                        `Error ${respuesta.status}: ${mensaje}`
                    );

                }


                const loteCreado =
                    await respuesta.json();


                console.log(
                    "Lote creado:",
                    loteCreado
                );


                alert(
                    "✅ Lote registrado correctamente."
                );


                // Limpiar formulario
                formulario.reset();


                // Recargar datos
                await cargarLotes();


                // Ir a lotes
                mostrarSeccion("lotes");


            } catch (error) {

                console.error(
                    "Error al registrar:",
                    error
                );


                alert(
                    "❌ No se pudo registrar el lote.\n\n" +
                    error.message
                );

            }

        }
    );

}


// =====================================================
// VER DETALLE
// =====================================================

async function verDetalle(id) {

    try {

        const respuesta =
            await fetch(
                `${API_URL}/lotes/${id}`
            );


        if (!respuesta.ok) {

            throw new Error(
                `Error ${respuesta.status}`
            );

        }


        const lote =
            await respuesta.json();


        alert(
            "DETALLE DEL LOTE\n\n" +

            `ID: ${lote.id}\n` +

            `Socio: ${lote.socio}\n` +

            `Peso: ${
                Number(lote.pesoKg || 0)
                    .toFixed(2)
            } kg\n` +

            `Estado: ${lote.estado}`
        );


    } catch (error) {

        console.error(
            "Error:",
            error
        );


        alert(
            "❌ No se pudo consultar el lote."
        );

    }

}


// =====================================================
// ACTUALIZAR REPORTES
// =====================================================

function actualizarReportes() {

    const total =
        lotes.length;


    const peso =
        lotes.reduce(
            (suma, lote) =>
                suma + Number(lote.pesoKg || 0),
            0
        );


    const pendientes =
        lotes.filter(
            lote => lote.estado === "PENDIENTE"
        ).length;


    const liquidados =
        lotes.filter(
            lote => lote.estado === "LIQUIDADO"
        ).length;


    const reporteTotal =
        document.getElementById("reporteTotal");


    const reportePeso =
        document.getElementById("reportePeso");


    const reportePendientes =
        document.getElementById("reportePendientes");


    const reporteLiquidados =
        document.getElementById("reporteLiquidados");


    if (reporteTotal) {
        reporteTotal.textContent =
            total;
    }


    if (reportePeso) {
        reportePeso.textContent =
            `${peso.toFixed(2)} kg`;
    }


    if (reportePendientes) {
        reportePendientes.textContent =
            pendientes;
    }


    if (reporteLiquidados) {
        reporteLiquidados.textContent =
            liquidados;
    }

}
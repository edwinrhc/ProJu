//Variable globales
const idProcesoJudicial = $("#idProcesoJudicial").val();

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function abrirModalNuevoMovimiento() {
    $.ajax({
        url: '/movimientos/get',
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            const etapasProcesales = data.etapaProcesales;
            const tipoContigencia = data.tipoContigencia;

            // Genera el formulario vacío en el modal
            let content = `
                <form id="movimientoForm" style="max-width: 400px;">
                        <h4 class="text-2xl font-bold mb-6">Nuevo Movimiento</h4>
                    <div class="mb-4">
                        <input type="text" name="idProcesoJudicial" value="${idProcesoJudicial}"> <!-- Campo oculto para idProcesoJudiciales -->
                           
                        <label for="nombre" class="block font-semibold">Nombre:</label>
                        <textarea id="nombre" name="nombre" class="w-full p-2 border rounded" rows="3"></textarea>
                    </div>

                    <div class="mb-4">
                        <label for="idEtapaProcesal" class="block font-semibold">Etapa Procesal:</label>
                        <select id="idEtapaProcesal" name="idEtapaProcesal" class="w-full p-2 border rounded">
                            <option value="" selected>Seleccione</option>
                            ${etapasProcesales.map(etapa => `<option value="${etapa.idEtapa}">${etapa.nombre}</option>`).join('')}
                        </select>
                    </div>
                      <div class="mb-4">
                        <label for="idContingencia" class="block font-semibold">Contigencia:</label>
                        <select id="idContingencia" name="idContingencia" class="w-full p-2 border rounded">
                            <option value="" selected>Seleccione</option>
                            ${tipoContigencia.map(tipoContigencia => `<option value="${tipoContigencia.idTipoContigencia}">${tipoContigencia.nombre}</option>`).join('')}
                        </select>
                    </div>
                    <!-- Botones para guardar o cerrar el formulario -->
                    <div class="flex justify-end mt-4">
                        <button type="button" onclick="submitForm()" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
                        <button type="button" onclick="cerrarModal()" class="bg-gray-500 text-white px-4 py-2 rounded-md">Cerrar</button>
                    </div>
                </form>
            `;
            $('#modalMovimientoContent').html(content); // Inserta el formulario vacío en el modal
            $('#modalMovimiento').removeClass('hidden'); // Muestra el modal
        },
        error: function (xhr, status, error) {
            alert("Error al cargar el formulario.");
        }
    })
}

function submitForm() {
    const movimientoData = {
        idProcesoJudicial,
        nombre: $("#nombre").val(),
        idEtapaProcesal: $("#idEtapaProcesal").val(),
        idContingencia: $("#idContingencia").val(),
    };
    // console.log(movimientoData)

    // Obtener el token CSRF desde la cookie
    const csrfToken = getCookie('XSRF-TOKEN');
    const csrfHeader = 'X-XSRF-TOKEN';

    // console.log("csrfHeader ->: ", csrfHeader);
    // console.log("csrfToken ->: ", csrfToken);

    $.ajax({
        url: '/movimientos/create',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(movimientoData),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            alert("Movimiento creado exitosamente");
            cerrarModal();
        },
        error: function (xhr, status, error) {
            console.error("Error:", xhr.responseText);
            alert("Error al crear el movimiento: " + xhr.responseText);
        }
    });
}

function abrirModalMovimiento(idMovimiento) {
    // Obtener los datos del movimiento
    $.ajax({
        url: '/movimientos/get/' + idMovimiento,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // Obtener las listas de opciones
            $.ajax({
                url: '/movimientos/get/options',
                type: 'GET',
                dataType: 'json',
                success: function (optionsData) {
                    const etapasProcesales = optionsData.etapaProcesals;
                    const tipoContigencias = optionsData.tipoContigencias;

                    // Construir el formulario
                    let content = `
                        <form id="movimientoForm" style="max-width: 400px;">
                            <h4 class="text-2xl font-bold mb-6">Editar Movimiento</h4>
                            <input type="hidden" name="idMovimiento" value="${data.idMovimiento}">
                            <input type="hidden" name="idProcesoJudicial" value="${data.idProcesoJudicial}">
                            
                            <div class="mb-4">
                                <label for="nombre" class="block font-semibold">Nombre:</label>
                                <textarea id="nombre" name="nombre" class="w-full p-2 border rounded" rows="3">${data.nombre ? data.nombre : ''}</textarea>
                            </div>
                            
                            <div class="mb-4">
                                <label for="idEtapaProcesal" class="block font-semibold">Etapa Procesal:</label>
                                <select id="idEtapaProcesal" name="idEtapaProcesal" class="w-full p-2 border rounded">
                                    <option value="" disabled>Seleccione</option>
                                    ${etapasProcesales.map(etapa => `
                                        <option value="${etapa.idEtapa}" ${etapa.idEtapa === data.idEtapaProcesal ? 'selected' : ''}>${etapa.nombre}</option>
                                    `).join('')}
                                </select>
                            </div>
                            
                            <div class="mb-4">
                                <label for="idContingencia" class="block font-semibold">Contingencia:</label>
                                <select id="idContingencia" name="idContingencia" class="w-full p-2 border rounded">
                                    <option value="" disabled>Seleccione</option>
                                    ${tipoContigencias.map(contingencia => `
                                        <option value="${contingencia.idTipoContigencia}" ${contingencia.idTipoContigencia === data.idContingencia ? 'selected' : ''}>${contingencia.nombre}</option>
                                    `).join('')}
                                </select>
                            </div>
                            
                            <!-- Botones para guardar o cerrar el formulario -->
                            <div class="flex justify-end mt-4">
                                <button type="button" onclick="submitEditForm()" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
                                <button type="button" onclick="cerrarModal()" class="bg-gray-500 text-white px-4 py-2 rounded-md">Cerrar</button>
                            </div>
                        </form>
                    `;

                    $('#modalMovimientoContent').html(content); // Inserta el formulario en el modal
                    $('#modalMovimiento').removeClass('hidden'); // Muestra el modal
                },
                error: function (xhr, status, error) {
                    alert("Error al cargar las opciones.");
                }
            });
        },
        error: function (xhr, status, error) {
            alert("Error al cargar el movimiento.");
        }
    });
}

function submitEditForm() {
    const movimientoData = {
        idMovimiento: $("#movimientoForm input[name='idMovimiento']").val(),
        idProcesoJudicial: $("#movimientoForm input[name='idProcesoJudicial']").val(),
        nombre: $("#nombre").val(),
        idEtapaProcesal: $("#idEtapaProcesal").val(),
        idContingencia: $("#idContingencia").val(),
    };
    console.log(movimientoData);

    // Obtener el token CSRF desde la cookie
    const csrfToken = getCookie('XSRF-TOKEN');
    const csrfHeader = 'X-XSRF-TOKEN';


    $.ajax({
        url: '/movimientos/update', // Asegúrate de tener este endpoint en tu controlador
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(movimientoData),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            alert("Movimiento actualizado exitosamente");
            cerrarModal();
            // Opcional: actualizar la tabla o recargar la página para reflejar los cambios
        },
        error: function (xhr, status, error) {
            console.error("Error:", xhr.responseText);
            alert("Error al actualizar el movimiento: " + xhr.responseText);
        }
    });
}


/*
function abriModalProcesoJudicial(idProcesoJudicales) {


    $.ajax({
        url: '/procesoJudiciales/get/' + idProcesoJudicales,
        type: 'GET',
        dataType: 'json', // Indica que esperas recibir JSON
        success: function (data) {
            // Genera el contenido del modal utilizando los datos JSON recibidos
            let content = `
    <form th:action="@{/procesoJudiciales/crear}" th:object="${procesoJudiciales}" method="post"
          class="bg-white p-6 rounded-lg shadow-md space-y-6 w-full max-w-full mx-auto overflow-hidden">

        <h1 class="text-2xl font-bold mb-6" th:text="${titulo + ' - Proceso Judiciales'}"></h1>

        <!-- Campo de N° Expediente -->
        <div class="flex flex-wrap gap-4">
            <div class="flex flex-col w-1/8">
                <label for="numExpediente" class="text-gray-700 font-semibold">N° de Expediente</label>
                <input type="text" id="numExpediente" name="numExpediente"
                       placeholder="Ingrese el número de expediente"
                       th:classappend="${#fields.hasErrors('numExpediente')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{numExpediente}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('numExpediente')}"
                         th:errors="*{numExpediente}"></div>
            </div>

            <!-- Campo de Materia -->

            <div class="flex flex-col w-1/3">
                <label for="materia" class="text-gray-700 font-semibold">Materia</label>
                <input type="text" id="materia" name="materia" placeholder="Ingrese la materia"
                       th:classappend="${#fields.hasErrors('materia')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{materia}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('materia')}"
                         th:errors="*{materia}"></div>
            </div>

            <!--   Campo de Tipo moneda -->
            <div class="flex flex-col w-1/8">
                <label for="tipoMoneda" class="text-gray-700 font-semibold">Tipo moneda</label>
                <select id="tipoMoneda" name="tipoMoneda"
                        th:classappend="${#fields.hasErrors('tipoMoneda')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                        th:field="*{tipoMoneda}"
                        class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <option value="" selected>Seleccione</option>
                    <option th:value="soles">Soles</option>
                    <option th:value="dolares">Dólares</option>
                </select>
                <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('tipoMoneda')}"
                     th:errors="*{tipoMoneda}"></div>
            </div>

            <div class="flex flex-col w-1/8">
                <label for="monto" class="text-gray-700 font-semibold">Monto</label>
                <input type="text" id="monto" name="monto" placeholder="Ingrese el monto"
                       th:classappend="${#fields.hasErrors('monto')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{monto}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('monto')}"
                         th:errors="*{monto}"></div>
            </div>


            <div class="flex flex-col w-1/4">
                <label for="monto" class="text-gray-700 font-semibold">Demandante</label>
                <input type="text" id="demandante" name="demandante" placeholder="Ingrese el demandante"
                       th:classappend="${#fields.hasErrors('demandante')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{demandante}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('demandante')}"
                         th:errors="*{demandante}"></div>
            </div>
        </div>

        <div class="flex flex-wrap gap-4">

            <div class="flex flex-col w-1/4">
                <label for="monto" class="text-gray-700 font-semibold">Demandado</label>
                <input type="text" id="demandado" name="demandado" placeholder="Ingrese el demandado"
                       th:classappend="${#fields.hasErrors('demandado')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{demandado}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('demandado')}"
                         th:errors="*{demandado}"></div>
            </div>


            <div class="flex flex-col w-1/3">
                <label for="mi-select" class="text-gray-700 font-semibold">Juzgado</label>
                <select id="mi-select" name="idJuzgado"
                        th:classappend="${#fields.hasErrors('idJuzgado')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                        th:field="*{idJuzgado}"
                        class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full max-h-40 overflow-y-auto">

                    <option value="" selected>Seleccione</option>
                    <th:block th:each="juzgadoList : ${Listjuzgados}">
                        <option th:value="${juzgadoList.idJuzgado}" th:text="${juzgadoList.nombre}"></option>
                    </th:block>
                </select>
                <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('idJuzgado')}"
                     th:errors="*{idJuzgado}"></div>
            </div>

            <div class="flex flex-col w-1/4">
                <label for="monto" class="text-gray-700 font-semibold">Abogado</label>
                <input type="text" id="abogado" name="abogado" placeholder="Ingrese el nombre del abogado(a)"
                       th:classappend="${#fields.hasErrors('abogado')} ? 'border-red-500 bg-red-100' : 'border-gray-300'"
                       th:field="*{abogado}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                    <div class="text-red-500 text-sm mt-1" th:if="${#fields.hasErrors('abogado')}"
                         th:errors="*{abogado}"></div>
            </div>

        </div>
        <button type="button" onclick="abrirModalNuevoMovimiento()"
                class="bg-green-600 text-white px-4 py-2 rounded-md">Nuevo Movimiento
        </button> `;

            $('#modalMovimientoContent').html(content); // Inserta el contenido en el modal
            $('#modalMovimiento').removeClass('hidden'); // Muestra el modal

            $('#modalMovimientoContent').html(content); // Inserta el contenido generado en el modal
            $('#modalMovimiento').removeClass('hidden'); // Muestra el modal
        },
        error: function (xhr, status, error) {
            console.error("Error:", error); // Muestra el error en la consola
            alert("Error al cargar el detalle del movimiento.");
        }
    })

}
*/


function abrirModalProcesoJudicial(idProcesoJudiciales) {
    $.ajax({
        url: '/procesoJudiciales/get/' + idProcesoJudiciales,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $.ajax({
                url: '/procesoJudiciales/get/options',
                type: 'GET',
                dataType: 'json',
                success: function (optionsData) {
                    const juzgadosList = optionsData.juzgadosList;

                    let content = `
    <form id="procesoJudicialesForm" style="max-width: 400px;" class="bg-white p-6 rounded-lg shadow-md space-y-6 w-full max-w-full mx-auto overflow-hidden">
        <h4 class="text-2xl font-bold mb-6">Editar Proceso Judiciales</h4>
        
        <!-- Campo oculto para capturar el idProcesoJudiciales -->
        <input type="text" id="idProcesoJudicial" name="idProcesoJudicial" class="hidden">
        
        <!-- Campo de N° Expediente -->
        <div class="mb-4">
            <label for="numExpediente" class="block font-semibold">N° de Expediente</label>
            <input type="text" id="numExpediente" name="numExpediente" placeholder="Ingrese el número de expediente"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="numExpedienteError"></div>
        </div>

        <!-- Campo de Materia -->
        <div class="mb-4">
            <label for="materia" class="block font-semibold">Materia</label>
            <input type="text" id="materia" name="materia" placeholder="Ingrese la materia"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="materiaError"></div>
        </div>

        <!-- Campo de Tipo moneda -->
        <div class="mb-4">
            <label for="tipoMoneda" class="block font-semibold">Tipo moneda</label>
            <select id="tipoMoneda" name="tipoMoneda" class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                <option value="">Seleccione</option>
                <option value="soles">Soles</option>
                <option value="dolares">Dólares</option>
            </select>
            <div class="text-red-500 text-sm mt-1" id="tipoMonedaError"></div>
        </div>

        <!-- Campo de Monto -->
        <div class="mb-4">
            <label for="monto" class="block font-semibold">Monto</label>
            <input type="text" id="monto" name="monto" placeholder="Ingrese el monto"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="montoError"></div>
        </div>

        <!-- Campo de Demandante -->
        <div class="mb-4">
            <label for="demandante" class="block font-semibold">Demandante</label>
            <input type="text" id="demandante" name="demandante" placeholder="Ingrese el demandante"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="demandanteError"></div>
        </div>

        <!-- Campo de Demandado -->
        <div class="mb-4">
            <label for="demandado" class="block font-semibold">Demandado</label>
            <input type="text" id="demandado" name="demandado" placeholder="Ingrese el demandado"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="demandadoError"></div>
        </div>

        <!-- Campo de Juzgado -->
        <div class="mb-4">
            <label for="idJuzgado" class="block font-semibold">Juzgado</label>
            <select id="idJuzgado" name="idJuzgado" class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
                <option value="">Seleccione</option>
                <!-- Las opciones de juzgado se agregarán dinámicamente desde el backend -->
            </select>
            <div class="text-red-500 text-sm mt-1" id="idJuzgadoError"></div>
        </div>

        <!-- Campo de Abogado -->
        <div class="mb-4">
            <label for="abogado" class="block font-semibold">Abogado</label>
            <input type="text" id="abogado" name="abogado" placeholder="Ingrese el nombre del abogado(a)"
                   class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            <div class="text-red-500 text-sm mt-1" id="abogadoError"></div>
        </div>

        <!-- Botones para guardar o cerrar el formulario -->
        <div class="flex justify-end mt-4">
<!--            <button type="button" onclick="submitProcesoJudicialForm()" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>-->
            <button type="button" onclick="cerrarModalProcesoJudicial()" class="bg-gray-500 text-white px-4 py-2 rounded-md">Cerrar</button>
        </div>
    </form>
`;

// Inserta el formulario en el contenedor del modal
                    $('#modalProcesoJudicialesContent').html(content);
                    $('#procesoJudicialesModal').removeClass('hidden'); // Muestra el modal
                }
            })
        }
    })
}

function cerrarModal() {
    $('#modalMovimiento').addClass('hidden'); // Oculta el modal
}

function cerrarModalProcesoJudicial() {
    $('#procesoJudicialeslMovimiento').addClass('hidden'); // Oculta el modal
}


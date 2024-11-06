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
                <form id="movimientoNew" style="max-width: 400px;">
                        <h4 class="text-2xl font-bold mb-6">Nuevo Movimiento</h4>
                    <div class="mb-4">
                        <input type="hidden" name="idProcesoJudicial" value="${idProcesoJudicial}"> <!-- Campo oculto para idProcesoJudiciales -->
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
                        <button type="button" onclick="validaryEnviarFormMovimiento('movimientoNew')" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
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
function validaryEnviarFormMovimiento(formId){
    const nombre = $('#nombre').val().trim();
    const idEtapaProcesal = $('#idEtapaProcesal').val();
    const idContingencia = $('#idContingencia').val();

    // Validación de campos usando SweetAlert
    if (!nombre) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo obligatorio',
            text: "El campo 'Nombre' es obligatorio.",
            confirmButtonText: 'Entendido'
        });
        return;
    }
    if (!idEtapaProcesal) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo obligatorio',
            text: "Debe seleccionar una 'Etapa Procesal'.",
            confirmButtonText: 'Entendido'
        });
        return;
    }
    if (!idContingencia) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo obligatorio',
            text: "Debe seleccionar una 'Contingencia'.",
            confirmButtonText: 'Entendido'
        });
        return;
    }
    // Ejecuta la función de envío
    if(formId === 'movimientoNew'){
        submitForm()
    } else if(formId === 'movimientoUpdate'){
        submitEditForm();
    }
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
        success: function(response) {
            // Muestra la alerta de éxito solo si el registro fue exitoso
            Swal.fire({
                icon: 'success',
                title: 'Registro exitoso',
                text: 'El nuevo movimiento se ha registrado correctamente.',
                confirmButtonText: 'Perfecto'
            }).then(() => {
                cerrarModal(); // Cierra el modal después de confirmar
                location.reload(); // Refresca la página para ver los cambios
            });
        },
        error: function(xhr, status, error) {
            console.error("Error:", xhr.responseText);
            // Manejo de errores
            Swal.fire({
                icon: 'error',
                title: 'Error al registrar',
                text: 'Hubo un problema al intentar registrar el movimiento. Inténtelo nuevamente.',
                confirmButtonText: 'Entendido'
            });
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
                        <form id="movimientoUpdate" style="max-width: 400px;">
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
                                <button type="button" onclick="validaryEnviarFormMovimiento('movimientoUpdate')" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
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
        idMovimiento: $("#movimientoUpdate input[name='idMovimiento']").val(),
        idProcesoJudicial: $("#movimientoUpdate input[name='idProcesoJudicial']").val(),
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
        success: function(response) {
            // Muestra la alerta de éxito solo si el registro fue exitoso
            Swal.fire({
                icon: 'success',
                title: 'Actualización exitosa',
                text: 'El movimiento se ha actualizado correctamente.',
                confirmButtonText: 'Perfecto'
            }).then(() => {
                cerrarModal(); // Cierra el modal después de confirmar
                location.reload(); // Refresca la página para ver los cambios
            });
        },
        error: function(xhr, status, error) {
            console.error("Error:", xhr.responseText);
            // Manejo de errores
            Swal.fire({
                icon: 'error',
                title: 'Error al actualizar',
                text: 'Hubo un problema al intentar actualizar el movimiento. Inténtelo nuevamente.',
                confirmButtonText: 'Entendido'
            });
        }
    });
}


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
    <form id="procesoJudicialesForm" style="max-width: 800px;" class="bg-white p-6 rounded-lg shadow-md space-y-6 w-full max-w-full mx-auto overflow-hidden">
        <h4 class="text-2xl font-bold mb-6">Editar Proceso Judiciales</h4>
        
        <!-- Contenedor en Grid para organizar en columnas -->
        <div class="grid grid-cols-2 gap-4">
            <!-- Campo de N° Expediente -->
            <div class="mb-4">
                <label for="numExpediente" class="block font-semibold">N° de Expediente</label>
                   <input type="hidden" name="idProcesoJudicial" value="${data.idProcesoJudicial}">
                <input type="text" id="numExpediente" name="numExpediente" placeholder="Ingrese el número de expediente" value="${data.numExpediente}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="numExpedienteError"></div>
            </div>

            <!-- Campo de Materia -->
            <div class="mb-4">
                <label for="materia" class="block font-semibold">Materia</label>
                <input type="text" id="materia" name="materia" placeholder="Ingrese la materia" value="${data.materia}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="materiaError"></div>
            </div>

            <!-- Campo de Tipo moneda -->
            <div class="mb-4">
                <label for="tipoMoneda" class="block font-semibold">Tipo moneda</label>
                <select id="tipoMoneda" name="tipoMoneda" class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                    <option value="">Seleccione</option>
                    <option value="soles">Soles</option>
                    <option value="dolares">Dólares</option>
                </select>
                <div class="text-red-500 text-sm mt-1" id="tipoMonedaError"></div>
            </div>

            <!-- Campo de Monto -->
            <div class="mb-4">
                <label for="monto" class="block font-semibold">Monto</label>
                <input type="text" id="monto" name="monto" placeholder="Ingrese el monto" value="${data.monto}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="montoError"></div>
            </div>

            <!-- Campo de Demandante -->
            <div class="mb-4">
                <label for="demandante" class="block font-semibold">Demandante</label>
                <input type="text" id="demandante" name="demandante" placeholder="Ingrese el demandante"  value="${data.demandante}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="demandanteError"></div>
            </div>

            <!-- Campo de Demandado -->
            <div class="mb-4">
                <label for="demandado" class="block font-semibold">Demandado</label>
                <input type="text" id="demandado" name="demandado" placeholder="Ingrese el demandado" value="${data.demandado}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="demandadoError"></div>
            </div>

            <!-- Campo de Juzgado -->
            <div class="mb-4">
                <label for="idJuzgado" class="block font-semibold">Juzgado</label>
                <select id="idJuzgado" name="idJuzgado" class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                    <option value="" disabled>Seleccione</option>
                    ${juzgadosList.map(juzgado => `
                        <option value="${juzgado.idJuzgado}" ${juzgado.idJuzgado === data.idJuzgado ? 'selected': ''}> ${juzgado.nombre}</option>
                    `).join('')}
                    <!-- Opciones de juzgado dinámicas -->
                </select>
                <div class="text-red-500 text-sm mt-1" id="idJuzgadoError"></div>
            </div>

            <!-- Campo de Abogado -->
            <div class="mb-4">
                <label for="abogado" class="block font-semibold">Abogado</label>
                <input type="text" id="abogado" name="abogado" placeholder="Ingrese el nombre del abogado(a)" value="${data.abogado}"
                       class="mt-2 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent w-full">
                <div class="text-red-500 text-sm mt-1" id="abogadoError"></div>
            </div>
        </div>

        <!-- Botones para guardar o cerrar el formulario -->
        <div class="flex justify-end mt-4">
            <button type="button" onclick="submitEditFormProcesoJudicial()" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
            <button type="button" onclick="cerrarModalProcesoJudicial()" class="bg-gray-500 text-white px-4 py-2 rounded-md">Cerrar</button>
        </div>
    </form>
`;
// Inserta el formulario en el contenedor del modal
                    $('#modalProcesoJudicialesContent').html(content);
                    $('#procesoJudicialesModal').removeClass('hidden'); // Muestra el modal

                    $('#tipoMoneda').val(data.tipoMoneda || "");

                },
                error: function (xhr, status, error) {
                    console.error("Error al cargar las opciones:", error);
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Error al cargar los datos del proceso judicial:", error);
        }
    })
}

function submitEditFormProcesoJudicial(){
    const procesoJudicialData = {
        idProcesoJudicial: $("#procesoJudicialesForm input[name='idProcesoJudicial']").val(),
        numExpediente: $('#numExpediente').val(),
        materia: $('#materia').val(),
        tipoMoneda: $('#tipoMoneda').val(),
        monto: $('#monto').val(),
        demandante: $('#demandante').val(),
        demandado: $('#demandado').val(),
        abogado: $('#abogado').val(),
        idJuzgado: $('#idJuzgado').val()
    };
    console.log(procesoJudicialData)

    const csrfToken = getCookie('XSRF-TOKEN');
    const csrfHeader = 'X-XSRF-TOKEN';

    $.ajax({
        url:'/procesoJudiciales/update',
        type:'POST',
        contentType: 'application/json',
        data: JSON.stringify(procesoJudicialData),
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeader,csrfToken);
        },
        success: function (response){
           Swal.fire({
               icon:'success',
               title: 'Actualización exitosa',
               text: 'El proceso judicial se ha actualizado correctamente',
               confirmButtonText: 'Perfecto'
           }).then(() =>{
            cerrarModal();
            location.reload();

           })
        },
        error: function (xhr,status,error){
            console.log("Error: ", xhr.responseText);
            // Manejo de errores
            Swal.fire({
                icon: 'error',
                title: 'Error al actualizar',
                text: 'Hubo un problema al intentar actualizar el proceso judicial. Inténtelo nuevamente.',
                confirmButtonText: 'Entendido'
            });
        }
    });
}

function cerrarModal() {
    $('#modalMovimiento').addClass('hidden'); // Oculta el modal
}

function cerrarModalProcesoJudicial() {
    $('#procesoJudicialesModal').addClass('hidden'); // Oculta el modal
}


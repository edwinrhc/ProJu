
function abrirModalNuevoMovimiento(){
    $.ajax({
        url: '/movimientos/get',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // Genera el formulario vacío en el modal
            let content = `
                <form id="movimientoForm" style="max-width: 400px;">
                    <div class="mb-4">
                        <label for="nombre" class="block font-semibold">Nombre:</label>
                        <textarea id="nombre" name="nombre" class="w-full p-2 border rounded" rows="3"></textarea>
                    </div>

                    <div class="mb-4">
                        <label for="idEtapaProcesal" class="block font-semibold">ID Etapa Procesal:</label>
                        <input type="number" id="idEtapaProcesal" name="idEtapaProcesal" class="w-full p-2 border rounded">
                    </div>

                    <div class="mb-4">
                        <label for="idContingencia" class="block font-semibold">ID Contingencia:</label>
                        <input type="number" id="idContingencia" name="idContingencia" class="w-full p-2 border rounded">
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
        error: function(xhr, status, error) {
            alert("Error al cargar el formulario.");
        }
    })
}

function abrirModalMovimiento(idMovimiento) {
    $.ajax({
        url: '/movimientos/get/' + idMovimiento,
        type: 'GET',
        dataType: 'json', // Indica que esperas recibir JSON
        success: function (data) {
            // Genera el contenido del modal utilizando los datos JSON recibidos
            let content = `
    <form id="movimientoForm" style="max-width: 400px;">
        <div class="mb-4">
            <label for="nombre" class="block font-semibold">Nombre:</label>
            <textarea id="nombre" name="nombre" class="w-full p-2 border rounded" rows="3">${data.nombre}</textarea>
        </div>
        
        <div class="mb-4">
            <label for="idEtapaProcesal" class="block font-semibold">ID Etapa Procesal:</label>
            <input type="number" id="idEtapaProcesal" name="idEtapaProcesal" class="w-full p-2 border rounded" value="${data.idEtapaProcesal ? data.idEtapaProcesal : ''}">
        </div>
        
        <div class="mb-4">
            <label for="idContingencia" class="block font-semibold">ID Contingencia:</label>
            <input type="number" id="idContingencia" name="idContingencia" class="w-full p-2 border rounded" value="${data.idContingencia ? data.idContingencia : ''}">
        </div>
        
        <div class="mb-4">
            <label for="createdBy" class="block font-semibold">Creado por:</label>
            <input type="text" id="createdBy" name="createdBy" class="w-full p-2 border rounded" value="${data.createdBy ? data.createdBy : ''}" readonly>
        </div>
        
        <div class="mb-4">
            <label for="createdAt" class="block font-semibold">Fecha de Creación:</label>
            <input type="text" id="createdAt" name="createdAt" class="w-full p-2 border rounded" value="${data.createdAt ? data.createdAt : ''}" readonly>
        </div>

        <!-- Botones para guardar o cerrar el formulario -->
        <div class="flex justify-end mt-4">
            <button type="button" onclick="submitForm()" class="bg-blue-600 text-white px-4 py-2 rounded-md mr-2">Guardar</button>
            <button type="button" onclick="cerrarModal()" class="bg-gray-500 text-white px-4 py-2 rounded-md">Cerrar</button>
        </div>
    </form>
`;

            $('#modalMovimientoContent').html(content); // Inserta el contenido en el modal
            $('#modalMovimiento').removeClass('hidden'); // Muestra el modal

            $('#modalMovimientoContent').html(content); // Inserta el contenido generado en el modal
            $('#modalMovimiento').removeClass('hidden'); // Muestra el modal
        },
        error: function (xhr, status, error) {
            console.error("Error:", error); // Muestra el error en la consola
            alert("Error al cargar el detalle del movimiento.");
        }
    });
}

function cerrarModal() {
    $('#modalMovimiento').addClass('hidden'); // Oculta el modal
}
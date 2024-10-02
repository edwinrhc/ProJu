document.addEventListener("DOMContentLoaded", function () {
    cargarDashboard();
});


function cargarDashboard() {
    fetch('/api/solicitudes/dashboard', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(solicitudes => {
            // Limpiar los contenedores
            document.getElementById('pendientes-content').innerHTML = '';
            document.getElementById('asignadas-content').innerHTML = '';
            document.getElementById('finalizadas-content').innerHTML = '';

            // Agregar solicitudes pendientes
            solicitudes.pendientes.forEach(sol => {
                document.getElementById('pendientes-content').innerHTML += crearTarjetaSolicitud(sol, 'pendiente');
            });

            // Agregar solicitudes asignadas
            solicitudes.asignadas.forEach(sol => {
                document.getElementById('asignadas-content').innerHTML += crearTarjetaSolicitud(sol, 'asignada');
            });

            // Agregar solicitudes finalizadas
            solicitudes.finalizadas.forEach(sol => {
                document.getElementById('finalizadas-content').innerHTML += crearTarjetaSolicitud(sol, 'finalizada');
            });

        })
        .catch(error => console.error('Error al cargar el dashboard:', error));
}

const crearTarjetaSolicitud = (sol, estado) => {
    let borderColor;
    switch (estado) {
        case 'pendiente':
            borderColor = 'border-red-500';
            break;
        case 'asignada':
            borderColor = 'border-yellow-500';
            break;
        case 'finalizada':
            borderColor = 'border-green-500';
            break;
        default:
            borderColor = 'border-gray-500';
    }

    return `
<div class="bg-gray-200 p-2 mb-2 rounded-lg">
        <div class="bg-white p-4 rounded-lg shadow-md border-l-4 ${borderColor} mb-4"> <!-- Clase mb-4 añadida para margen -->
            <div class="flex items-center mb-2">
             <a onclick="openModal(${sol.solCID})" class="hover:underline flex items-center space-x-2" style="cursor: pointer">
                <i class="fa-solid fa-ticket fa-rotate-90 text-gray-600 mr-2"></i>
               <span class="font-bold text-lg text-gray-800">${sol.solDCodigo}</span></a>
            </div>
            <div class="border-t ${borderColor} mb-4"></div>
            <div class="text-sm text-gray-600 space-y-2">
                <p><strong>Fecha de creación:</strong> ${sol.solFechaCreacion}</p>
                <p><strong>Tipo de solicitud:</strong> ${sol.tipoSolicitudes.tipSolDescripcion}</p>
                <p><strong>Asignado a:</strong> ${sol.solAsignado || 'No asignado'}</p>
            </div>
        </div>
        
        </div>
    `;
};



function openModal(solCID) {
    // Hacer otra llamada AJAX para obtener los detalles de la solicitud
    fetch(`/api/solicitudes/${solCID}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener los detalles de la solicitud');
            }
            return response.json();
        })
        .then(detalles => {
            document.getElementById('modal-title').innerText = `Detalles de la Solicitud ${detalles.solDCodigo}`;
            document.getElementById('modal-content').innerHTML = `
            <p><strong>Estado:</strong> ${detalles.solEstado}</p>
            <p><strong>Prioridad:</strong> ${detalles.solPrioridad}</p>
            <p><strong>Categoria:</strong> ${detalles.solCategoria}</p>
            
            <p><strong>Descripción:</strong> ${detalles.solDescripcion}</p>
            <p><strong>Prioridad:</strong> ${detalles.solPrioridad}</p>
            <p><strong>Estado:</strong> ${detalles.solEstado}</p>
            <p><strong>Última modificación:</strong> ${detalles.solFechaModificacion}</p>
            <p><strong>Usuario Modificado</strong> ${detalles.solUsuarioModificacion}</p>
        `;
            document.getElementById('modal').classList.remove('hidden'); // Mostrar el modal
        })
        .catch(error => {
            console.error('Error al cargar los detalles de la solicitud:', error);
        });
}

function closeModal() {
    document.getElementById('modal').classList.add('hidden'); // Ocultar el modal
}

function toggleMenu() {
    var menu = document.getElementById('userMenu');
    menu.classList.toggle('hidden');
}

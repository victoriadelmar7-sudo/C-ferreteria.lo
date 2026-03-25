// =========================
// 🔹 CONFIGURACIÓN API
// =========================
const API_URL = "http://localhost:8080/api/productos";
const API_USUARIOS = "http://localhost:8080/usuarios";
const API_PEDIDOS = "http://localhost:8080/api/pedidos";

document.addEventListener("DOMContentLoaded", function () {

    // =========================
    // 🔹 LOGIN USUARIOS
    // =========================
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", function (e) {
            e.preventDefault();

            const correo = document.getElementById("correoLogin").value;
            const contrasena = document.getElementById("contrasenaLogin").value;

            fetch(`${API_USUARIOS}/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ correo, contrasena })
            })
            .then(response => {
                if (!response.ok) throw new Error("Credenciales incorrectas");
                return response.json();
            })
            .then(data => {

                localStorage.setItem("usuario", JSON.stringify(data));

                alert("Bienvenido " + data.nombre);

                window.location.href = "/productos";
            })
            .catch(() => {
                const mensajeError = document.getElementById("mensajeError");
                if (mensajeError) {
                    mensajeError.textContent =
                        "Correo o contraseña incorrectos";
                }
            });
        });
    }


    // =========================
    // 🔹 GESTIÓN DE USUARIOS
    // =========================
    const usuarioForm = document.getElementById("usuarioForm");
    if (usuarioForm) {
        usuarioForm.addEventListener("submit", function (e) {
            e.preventDefault();
            guardarUsuario();
        });
        cargarUsuarios();
    }


    // =========================
    // 🔹 PRODUCTOS
    // =========================
    const productoForm = document.getElementById("productoForm");
    if (productoForm) {
        productoForm.addEventListener("submit", function (e) {
            e.preventDefault();
            guardarProducto();
        });
        cargarProductos();
    }


    // =========================
    // MENÚ CATEGORÍAS
    // =========================

    const btnCategorias = document.getElementById("btnCategorias");
    const submenu = document.getElementById("submenuCategorias");

    if (btnCategorias && submenu) {

        btnCategorias.addEventListener("click", function(e){
            e.preventDefault();
            submenu.classList.toggle("activo");
        });

    }



    // =========================
    // 🔹 FUNCIONES USUARIOS
    // =========================
   function cargarUsuarios() {
       fetch(API_USUARIOS)
           .then(res => res.json())
           .then(data => {
               const lista = document.getElementById("listaUsuarios");
               if (!lista) return;

               lista.innerHTML = "";

               if (data.length === 0) {
                   lista.innerHTML = "<li>No hay usuarios registrados</li>";
                   return;
               }

               data.forEach(u => {
                   const li = document.createElement("li");
                   li.innerHTML = `
                       ${u.nombre} - ${u.correo} - ${u.rol}
                       <button onclick="eliminarUsuario(${u.idUsuario})">Eliminar</button>
                   `;
                   lista.appendChild(li);
               });
           });
   }
 function guardarUsuario() {
     const usuario = {
         nombre: document.getElementById("nombreUsuario").value,
         correo: document.getElementById("correoUsuario").value,
         contrasena: document.getElementById("contrasenaUsuario").value,
         rol: document.getElementById("rolUsuario").value
     };
        fetch(API_USUARIOS, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario)
        })
        .then(() => {
            limpiarFormularioUsuario();
            cargarUsuarios();
        })
        .catch(err => console.error(err));
    }

    window.eliminarUsuario = function(id) {
        fetch(`${API_USUARIOS}/${id}`, { method: "DELETE" })
            .then(() => cargarUsuarios())
            .catch(err => console.error(err));
    }

   function limpiarFormularioUsuario() {
       document.getElementById("nombreUsuario").value = "";
       document.getElementById("correoUsuario").value = "";
       document.getElementById("contrasenaUsuario").value = "";
       document.getElementById("rolUsuario").value = "CLIENTE";
   }

    // =========================
    // 🔹 FUNCIONES PRODUCTOS
    // =========================
    function cargarProductos() {
        fetch(API_URL)
            .then(res => res.json())
            .then(data => {
                const lista = document.getElementById("listaProductos");
                if (!lista) return;

                lista.innerHTML = "";

                if (data.length === 0) {
                    lista.innerHTML = "<li>No hay productos registrados</li>";
                    return;
                }

                data.forEach(p => {
                    const li = document.createElement("li");
                    li.innerHTML = `
                        ${p.nombre} - $${p.precio} - Stock: ${p.stock}
                        <button onclick="eliminarProducto(${p.id})">Eliminar</button>
                    `;
                    lista.appendChild(li);
                });
            });
    }

    function guardarProducto() {
        const producto = {
            nombre: document.getElementById("nombre").value,
            precio: document.getElementById("precio").value,
            stock: document.getElementById("stock").value
        };

        fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(producto)
        })
        .then(() => {
            limpiarFormularioProducto();
            cargarProductos();
        });
    }

    function limpiarFormularioProducto() {
        document.getElementById("nombre").value = "";
        document.getElementById("precio").value = "";
        document.getElementById("stock").value = "";
    }

    window.eliminarProducto = function(id) {
        fetch(`${API_URL}/${id}`, { method: "DELETE" })
            .then(() => cargarProductos());
    }

    // FUNCIÓN PARA VERIFICAR SESIÓN
    function usuarioLogueado(){
        return localStorage.getItem("usuario") !== null;
    }
    function verificarSesion(){

        const usuario = localStorage.getItem("usuario");

        const btnLogin = document.getElementById("btnLogin");
        const btnLogout = document.getElementById("btnLogout");

        if(!btnLogin || !btnLogout) return;

        if(usuario){
            btnLogin.style.display = "none";
            btnLogout.style.display = "block";
        }else{
            btnLogin.style.display = "block";
            btnLogout.style.display = "none";
        }

    }

    function cerrarSesion(){

        localStorage.removeItem("usuario");

        window.location.href="/usuarios/login";

    }

    document.addEventListener("DOMContentLoaded", verificarSesion);

    window.addEventListener("scroll", function() {

    const header = document.querySelector(".header-fijo");

    if(window.scrollY > 20){
    header.classList.add("scrolled");
    }else{
    header.classList.remove("scrolled");


    // =========================
    // 🔹 DETALLE DE PEDIDOS
    // =========================
    window.verDetallePedido = function(id) {

        fetch(`${API_PEDIDOS}/${id}`)
            .then(res => res.json())
            .then(data => {

                let contenido = `
                    <p><strong>ID:</strong> ${data.id}</p>
                    <p><strong>Fecha:</strong> ${data.fecha}</p>
                    <p><strong>Cliente:</strong> ${data.cliente.nombre}</p>
                    <p><strong>Correo:</strong> ${data.cliente.correo}</p>
                    <p><strong>Dirección:</strong> ${data.cliente.direccion}</p>

                    <h3>Productos:</h3>
                    <ul>
                        ${data.detalles.map(d => `
                            <li>
                                ${d.producto.nombre} -
                                Cantidad: ${d.cantidad} -
                                $${d.precio}
                            </li>
                        `).join("")}
                    </ul>

                    <h3>Total: $${data.total}</h3>
                `;

                document.getElementById("contenidoPedido").innerHTML = contenido;
                document.getElementById("modalPedido").style.display = "block";
            })
            .catch(err => console.error(err));
    }

    window.cerrarModal = function() {
        document.getElementById("modalPedido").style.display = "none";
    }



}


});
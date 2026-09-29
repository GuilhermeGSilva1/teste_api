const url = "http://localhost:8080/task/user/1"; // Define a URL da API que será utilizada para buscar as tarefas do usuário

function hideLoader() { // Cria uma função responsável por esconder o indicador de carregamento

    document.getElementById("loading").style.display = "none"; // Encontra o elemento loading e altera sua exibição para none

} // Fecha a função hideLoader


function show(tasks) { // Cria uma função que recebe a lista de tarefas e exibe elas na tabela

    let tab = `
        <thead>
            <tr>
            <th scope="col">#</th> 
            <th scope="col">Descrição</th>
            <th scope="col">Usuário</th>
            <th scope="col">User ID</th>
            </tr>
        </thead>
    `;

    for(let task of tasks){

        tab +=`
        <tr>
            <td scope="row">${task.id}</td>
            <td>${task.description}</td>
            <td>${task.user.username}</td>
        </tr>
        `;
    }
    document.getElementById("tasks").innerHTML = tab

    async function getAPI(url) {
        const response = await fetch(url,{method:"GET"});

        var data = await response.json();
        if(response0){
            hideLoader();
        }
        
    }
    
} 
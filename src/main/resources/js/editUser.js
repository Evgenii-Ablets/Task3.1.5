async function sendDataEditUser(user) {
    await fetch("/api/admin" ,
        {method:"PUT", headers: {'Content-type': 'application/json'}, body: JSON.stringify(user)} )
}

const modalEdit = document.getElementById("editModal");

async function EditModalHandler() {
    await fillModal(modalEdit);
}

modalEdit.addEventListener("submit", async function(event){
    event.preventDefault();

    const rolesSelected = document.getElementById("rolesEdit");

    let roles = [];
    for (let option of rolesSelected.selectedOptions) {
        if(option.value === ROLE_USER.name) {
            roles.push(ROLE_USER);
        } else if (option.value === ROLE_ADMIN.name) {
            roles.push(ROLE_ADMIN);
        }
    }

    let user = {
        id: document.getElementById("idEdit").value,
        name: document.getElementById("nameEdit").value,
        username: document.getElementById("usernameEdit").value,
        age: document.getElementById("ageEdit").value,
        password: document.getElementById("passwordEdit").value,
        roles: roles
    }

    await sendDataEditUser(user);
    await fillTableOfAllUsers();

    const modalBootstrap = bootstrap.Modal.getInstance(modalEdit);
    modalBootstrap.hide();
})

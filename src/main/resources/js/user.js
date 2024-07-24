document.addEventListener('DOMContentLoaded', async function () {
    await showUserNameOnNavbar()
    await fillTableAboutUser();
});

async function dataAboutCurrentUser() {
    const response = await fetch("/api/user")
    return await response.json();
}
async function fillTableAboutUser(){
    const currentUserTable = document.getElementById("currentUserTable");
    const currentUser = await dataAboutCurrentUser();

    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.name}</td>
            <td>${currentUser.username}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.password}</td>
            <td>${currentUser.roles.map(role => role.name).join(' ')}</td>
        </tr>`
    currentUserTable.innerHTML = currentUserTableHTML;
}

async function showUserNameOnNavbar() {
    const currentUserNameNavbar = document.getElementById("currentUserNameNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserNameNavbar.innerHTML =
        `<strong>${currentUser.username}</strong>
                 with roles: 
                 ${currentUser.roles.map(role => role.name).join(' ')}`;
}


document.addEventListener('DOMContentLoaded', async function () {
    await showUserNameOnNavbar()
    await fillTableOfAllUsers();
    await fillTableAboutCurrentUser();
    await addNewUserForm();
    await DeleteModalHandler();
    await EditModalHandler();
});

const ROLE_USER = {roleId: 1, name: "ROLE_USER"};
const ROLE_ADMIN = {roleId: 2, name: "ROLE_ADMIN"};


async function showUserNameOnNavbar() {
    const currentUserNameNavbar = document.getElementById("currentUserNameNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserNameNavbar.innerHTML =
                `<strong>${currentUser.username}</strong>
                 with roles: 
                 ${currentUser.roles.map(role => role.name).join(' ')}`;
}

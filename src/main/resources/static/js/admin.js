// ======= Constants ========
const BASE_URL = "http://localhost:8080/api/";
const PASSWORD_PLACEHOLDER = "******";
const USER_KEYS = {
    id: "id",
    email: "email",
    password: "password",
    firstName: "firstName",
    lastName: "lastName",
    birthDate: "birthDate",
    roles: "rolesArray"
};

// ======= Requests =======
async function fetchUserById(id) {
    const response = await fetch(BASE_URL + "admin/users/" + id);
    return response.json();
}

async function fetchAllUsers() {
    const response = await fetch(BASE_URL + "admin/users");
    return response.json();
}

async function fetchAllRoles() {
    const response = await fetch(BASE_URL + "roles");
    return response.json();
}

async function updateUser(id, user) {
    const response = await fetch(BASE_URL + "admin/users/" + id, {
        method: "PATCH",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });
    return response.json();
}

async function test() {
    const response = await fetch(BASE_URL + "roles", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: {"hello": "world"}
    });
    return response.json();
}

test().then(console.log);


// ====== Modal =======
async function handleAlterModal(e) {
    const userId = e.target.dataset.userId;
    if (!userId) return;

    modalBody.innerHTML = "";
    const userFromDb = await fetchUserById(userId);
    const roles = await fetchAllRoles();

    let form;
    if (e.target.dataset.action === "edit") {
        modalTitle.innerText = "Edit user";
        modalActionBtn.innerText = "Edit";
        modalActionBtn.className = "btn btn-primary";
        modalActionBtn.dataset.action = "edit";

        form = renderUserForm("edit", userFromDb, roles);
    } else if (e.target.dataset.action === "delete") {
        modalTitle.innerText = "Delete user";
        modalActionBtn.innerText = "Delete";
        modalActionBtn.className = "btn btn-danger";
        modalActionBtn.dataset.action = "delete";

        form = renderUserForm("delete", userFromDb, roles);
    }

    modalBody.append(form);
}

async function handleModalAction(e) {
    e.preventDefault();
    const form = document.querySelector("#alterForm");
    const userId = form.querySelector("[data-user-id]");

    if (modalActionBtn.dataset.action === "edit") {
        userId.disabled = false;
        const formData = new FormData(form);
        const output = {};
        for (const [key, value] of formData) {
            if (key === "roles") {
                output[key] ||= [];
                output[key].push(value);
            } else {
                output[key] = value;
            }
        }
        userId.disabled = true;
        // Remove password field if not changed
        if (output.password === PASSWORD_PLACEHOLDER) {
            delete output.password;
        }
        console.log(output);
        await updateUser(output.id, output);
    } else if (modalActionBtn.dataset.action === "delete") {
        console.log("delete");

    }
}

const modalActionBtn = document.querySelector(".modal-footer > [data-action]");
modalActionBtn.addEventListener("click", handleModalAction);

const modalTitle = document.querySelector(".modal-title");
const modalBody = document.querySelector("#modalBody");


// ======== Table ========
const tableBody = document.querySelector("#tableBody");
tableBody.addEventListener("click", handleAlterModal);

fetchAllUsers().then(users => {
    renderAllUsers(tableBody, users);
});

// Elements
/**
 * Create html user row with given parameters
 * @return HTML element
 */
function UserRow({id, email, firstName, lastName, birthDate, authorities}) {
    const userItem = createElement("tr");

    const userId = createElement("td", "py-3 ps-3 align-middle", userItem);
    userId.innerText = id;

    const userEmail = createElement("td", "py-3 ps-3 align-middle", userItem);
    userEmail.innerText = email;

    const userFirstName = createElement("td", "py-3 ps-3 align-middle", userItem);
    userFirstName.innerText = firstName;

    const userLastName = createElement("td", "py-3 ps-3 align-middle", userItem);
    userLastName.innerText = lastName;

    const userBirthDate = createElement("td", "py-3 ps-3 align-middle", userItem);
    userBirthDate.innerText = birthDate;

    const userRoles = createElement("td", "py-3 ps-3 align-middle", userItem);
    authorities.forEach(role => userRoles.innerText += role.name + " ");

    const editColum = createElement("td", "py-3 align-middle fs-6", userItem);
    const editBtn = createElement("button", "btn btn-info text-white", editColum);
    editBtn.innerText = "Edit";
    editBtn.dataset.bsToggle = "modal";
    editBtn.dataset.bsTarget = "#alterUserModal";
    editBtn.dataset.userId = id;
    editBtn.dataset.action = "edit";

    const deleteColum = createElement("td", "py-3 align-middle fs-6", userItem);
    const deleteBtn = createElement("button", "btn btn-danger", deleteColum);
    deleteBtn.innerText = "Delete";
    deleteBtn.dataset.bsToggle = "modal";
    deleteBtn.dataset.bsTarget = "#alterUserModal";
    deleteBtn.dataset.userId = id;
    deleteBtn.dataset.action = "delete";

    return userItem;
}

/**
 *
 * @param {HTMLElement} parentElement
 * @param {[]} usersData
 */
function renderAllUsers(parentElement, usersData) {
    usersData.forEach(user => parentElement.append(UserRow(user)));
}


/**
 *
 * @param {"edit" | "delete"} action Action will control how the form fields look
 * @param user
 * @param allRoles
 * @returns {HTMLElement}
 */
function renderUserForm(action, user, allRoles) {
    const {id, firstName, lastName, birthDate, email, password, authorities} = user;
    const formData = [
        {name: USER_KEYS.id, label: "ID", value: id},
        {name: USER_KEYS.firstName, label: "First name", value: firstName},
        {name: USER_KEYS.lastName, label: "Last name", value: lastName},
        {name: USER_KEYS.birthDate, label: "Birth date", value: birthDate},
        {name: USER_KEYS.email, label: "Email", value: email},
        {name: USER_KEYS.password, label: "Password", value: password},
        {name: USER_KEYS.roles, label: "Role", value: authorities}
    ];

    const form = createElement("form", "d-flex flex-column align-items-center");
    form.id = "alterForm";

    formData.forEach(data => {
        // Skip password field if we render delete form
        if (action === "delete" && data.name === USER_KEYS.password) return;


        const container = createElement("div", "col-10 mx-auto mb-3 d-flex flex-column align-items-center w-100", form);
        const label = createElement("label", "form-label fw-bold", container);
        label.innerText = data.label;
        label.setAttribute("for", data.name);


        // If Role -> create select with options -> skip to the next input
        if (data.name === USER_KEYS.roles) {
            const select = createElement("select", "form-control", container);
            select.name = data.name;
            select.multiple = true;
            select.required = true;
            // If delete size == user's roles
            select.size = action === "delete" ? data.value.length : allRoles.length;

            // If edit set options to all available roles, otherwise only user's roles
            (action === "delete" ? data.value : allRoles).forEach(role => {
                const option = createElement("option", "", select);
                option.value = role.name;
                option.innerText = role.name;

                // Select user's roles when editing and keep other available roles deselected
                if (action === "edit") {
                    option.selected = data.value.find(userRole => userRole.name === role.name);
                }
            });
            return;
        }

        // For basic inputs
        const input = createElement("input", "form-control", container);
        input.required = true;
        input.value = data.value;
        input.name = data.name;

        if (data.name === USER_KEYS.password) {
            input.type = "password";
            input.value = PASSWORD_PLACEHOLDER;
        } else if (data.name === USER_KEYS.birthDate) {
            input.type = "date";
        } else {
            input.type = "text";
        }

        // In delete form all fields are disabled, otherwise just ID
        if (action === "delete") {
            input.disabled = true;
        } else if (data.name === USER_KEYS.id) {
            input.disabled = true;
            input.dataset.userId = id;
        }
    });
    return form;
}

// Util functions
/**
 *
 * @param {string} tagName
 * @param {string} [classList]
 * @param {HTMLElement} [parent]
 * @returns HTMLElement
 */
function createElement(tagName, classList, parent) {
    const element = document.createElement(tagName);
    if (parent) parent.append(element);
    if (classList) {
        if (typeof classList === "string") {
            element.className = classList;
        } else {
            element.classList.add(...classList);
        }
    }
    return element;
}
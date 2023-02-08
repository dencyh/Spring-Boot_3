const BASE_URL = "http://localhost:8080/api/";

fetch(BASE_URL + "user")
    .then(response => {
        return response.json();
    })
    .then(currentUser => {
        renderAllUsers(tBody, currentUser);
    });

const tBody = document.querySelector("#tBody");

/**
 *
 * @param {HTMLElement} parentElement
 * @param {user[] | user} usersData
 */
function renderAllUsers(parentElement, usersData) {
    parentElement.innerHTML = "";
    if (Array.isArray(usersData)) {
        usersData.forEach(user => parentElement.append(UserRow(user)));
    } else {
        parentElement.append(UserRow(usersData));
    }
}

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


    return userItem;
}

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
let url = 'http://localhost:8081/api/users/'

let usersTable = document.querySelector('#usersTable tbody')
addForm = document.querySelector('#addForm')

function tableRow(users) {
    let out = ''
    users.forEach(user => {
        out += `
            <tr id=${'tr' + user.id}>
                <td>${user.id}</td>
                <td id=${'firstName' + user.id}>${user.firstName}</td>
                <td id=${'lastName' + user.id}>${user.lastName}</td>
                <td id=${'age' + user.id}>${user.age}</td>
                <td id=${'email' + user.id}>${user.email}</td>
                <td>
                    <div id=${'roles' + user.id}>
                        ${user.roles.map(role => role.name).join(" ")}
                    </div>
                </td>
                <td class="text-white">
                <button class="btnEdit btn btn-info" data-toggle ="modal" data-target="#edit"
                onclick="editUserForm(${user.id})">Edit</button></td>
                <td class="text-white">
                <button class="btnDelete btn btn-danger" data-toggle ="modal" data-target="#delete"
                onclick="deleteUser(${user.id})">Delete</button></td>
            </tr>
       `;
    });

    return out
}

function table(users) {
    let out = tableRow(users)
    usersTable.innerHTML = out;
}

fetch(url)
    .then(response => response.json())
    .then(users => table(users));

function deleteUser(id) {
    let roles = $('#roles' + id).text().trim().split(" ");

    $('#idDelete').val(id);
    $('#firstNameDelete').val($('#firstName' + id).text());
    $('#lastNameDelete').val($('#lastName' + id).text());
    $('#ageDelete').val($('#age' + id).text());
    $('#emailDelete').val($('#email' + id).text());
    $('#rolesDelete').empty()

    roles.forEach(role => {
        $('#rolesDelete').append('<option>' + role + '</option>');
    })

}
$('#deleteButton').on('click', function () {
    let id = $('#delete #idDelete').val();
    deleteAction(id)
})

function deleteAction (id) {
    fetch(url + id, {method: 'DELETE'})
    console.log(id)
    document.getElementById('tr' + id).remove()
}

function editUserForm(id){

    $('#editId').val(id)
    $('#editFirstName').val($('#firstName' + id).text())
    $('#editLastName').val($('#lastName' + id).text())
    $('#editAge').val($('#age' + id).text())
    $('#editEmail').val($('#email' + id).text())

    let roles = $('#roles' + id).text().trim().split(" ");
    let userRoles =[]
    roles.forEach(role => {
        userRoles[userRoles.length] = role === "ADMIN" ? 1 : 2
    })

    $('#editRoles').val(userRoles)

    $('#editButton').on('click', function () {
        let roles = $('#editRoles').val();
        let userRoles = []

        roles.forEach(roleId => {
            userRoles.push({
                id: roleId,
                name : roleId == 1 ? "ADMIN" : "USER"
            })
        })

        let user = {
            id: $('#editId').val().trim(),
            firstName: $('#editFirstName').val().trim(),
            lastName: $('#editLastName').val().trim(),
            age: $('#editAge').val().trim(),
            email: $('#editEmail').val().trim(),
            password: $('#editPassword').val(),
            roles: userRoles
        }

        console.log(user)

        fetch(url + id, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json; charset=utf-8'},
            body: JSON.stringify(user)
        }).then(response => response.json())
            .then((data) => {
                $('#firstName' + data.id).html(data.firstName)
                $('#lastName' + data.id).html(data.lastName)
                $('#age' + data.id).html(data.age)
                $('#email' + data.id).html(data.email)
                $('#roles' + data.id).html(data.roles.map(role => role.name).join(" "))
            })

    })
}

addForm.addEventListener('submit', (evt) => {
    evt.preventDefault()

    let roles = $('#addRoles').val();
    let userRoles = []

    roles.forEach(roleId => {
        userRoles.push({
            id: roleId,
            name : roleId == 1 ? "ADMIN" : "USER"
        })
    })

    let newUserData = {
        firstName: $('#addFirstName').val().trim(),
        lastName: $('#addLastName').val().trim(),
        age: $('#addAge').val().trim(),
        email: $('#addEmail').val().trim(),
        password: $('#addPassword').val().trim(),
        roles: userRoles
    }

    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(newUserData)
    })
        .then(res => res.json())
        .then(user => {
            $('#nav-home-tab').addClass('active');
            $('#nav-home').addClass('active show');
            $('#nav-profile-tab').removeClass('active');
            $('#nav-profile').removeClass('active show');
            document.querySelector('#nav-home-tab').setAttribute('aria-selected', 'true');
            document.querySelector('#nav-profile-tab').setAttribute('aria-selected', 'false');
            let newRow = usersTable.insertRow()
            newRow.id = 'tr' + user.id
                newRow.innerHTML = `
                <td>${user.id}</td>
                <td id=${'firstName' + user.id}>${user.firstName}</td>
                <td id=${'lastName' + user.id}>${user.lastName}</td>
                <td id=${'age' + user.id}>${user.age}</td>
                <td id=${'email' + user.id}>${user.email}</td>
                <td>
                    <div id=${'roles' + user.id}>
                        ${user.roles.map(role => role.name).join(" ")}
                    </div>
                </td>
                <td class="text-white">
                <button class="btnEdit btn btn-info" data-toggle ="modal" data-target="#edit"
                onclick="editUserForm(${user.id})">Edit</button></td>
                <td class="text-white">
                <button class="btnDelete btn btn-danger" data-toggle ="modal" data-target="#delete"
                onclick="deleteUser(${user.id})">Delete</button></td>
       `
            }
        );

})


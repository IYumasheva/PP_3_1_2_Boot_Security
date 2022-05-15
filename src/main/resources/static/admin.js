let url = 'http://localhost:8081/api/users/'

let usersTable = document.querySelector('#usersTable tbody')

function table(users) {
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

    $('#deleteButton').on('click', function () {
        fetch(url + id, {method: 'DELETE'})
        document.getElementById('tr' + id).remove()})
}

function editUserForm(id){
    let roles = $('#roles' + id).text().trim().split(" ")

    $('#editId').val(id)
    $('#editFirstName').val($('#firstName' + id).text())
    $('#editLastName').val($('#lastName' + id).text())
    $('#editAge').val($('#age' + id).text())
    $('#editEmail').val($('#email' + id).text())
    $('#editPassword').val($('#password' + id).text())
    $('#editRoles').val(roles)

    $('#editButton').on('click', function () {
        let user = {id: $('#editId').val().trim(),
        firstName: $('#editFirstName').val().trim(),
        lastName: $('#editLastName').val().trim(),
        age: $('#editAge').val().trim(),
        email: $('#editEmail').val().trim(),
        password: $('#editPassword').val().trim(),
        roles: $('#editRoles').val()
        }

        console.log(user)

        fetch(url + id, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json; charset=utf-8'},
            body: JSON.stringify(user)
        }).then(response => response.json())
            .then(user => {
                $('#firstName' + user.id).val(user.firstName)
                $('#lastName' + user.id).val(user.lastName)
                $('#age' + user.id).val(user.age)
                $('#email' + user.id).val(user.email)
                $('#roles' + user.id).val(user.roles)
            })

    })
}


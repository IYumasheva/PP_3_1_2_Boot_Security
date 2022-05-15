let url = 'http://localhost:8081/api/users/current_user'
let info = document.querySelector('#info tbody')

function table (currentUser) {
    info.innerHTML = `
                    <tr>
                        <td>${currentUser.id}</td>
                        <td>${currentUser.firstName}</td>
                        <td>${currentUser.lastName}</td>
                        <td>${currentUser.age}</td>
                        <td>${currentUser.email}</td>
                        <td>
                            <div>
                                ${currentUser.roles.map(role => role.name).join(" ")}
                            </div>
                        </td>
                    </tr>
               `;
}

fetch(url)
    .then(response => response.json())
    .then(o => table(o))
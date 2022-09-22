function showLogin() {
    document.getElementById("dropdown-login").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
// window.onclick = function(event) {
//     if (!event.target.matches('.dropdown-button') && !event.target.matches('.dropdown-login')) {
//         var dropdowns = document.getElementsByClassName("dropdown-login");
//         var i;
//         for (i = 0; i < dropdowns.length; i++) {
//             var openDropdown = dropdowns[i];
//             if (openDropdown.classList.contains('show')) {
//                 openDropdown.classList.remove('show');
//             }
//         }
//     }
// }
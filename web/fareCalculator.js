/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.getElementById("calculateFare").addEventListener("click", function() {
    let pickupLat = document.getElementById("pickupLat").value;
    let pickupLon = document.getElementById("pickupLon").value;
    let dropLat = document.getElementById("dropLat").value;
    let dropLon = document.getElementById("dropLon").value;

    fetch("FareCalculatorServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `pickupLat=${pickupLat}&pickupLon=${pickupLon}&dropLat=${dropLat}&dropLon=${dropLon}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert(data.error);
            return;
        }

        let distance = parseFloat(data.distance).toFixed(2); // Get distance from the backend response
        
        document.getElementById("fareDisplay").innerHTML = 
            `Distance: ${distance} km <br> Select a vehicle to see the fare.`;

        // Store distance in a hidden field for vehicle selection
        document.getElementById("rideDistance").value = distance;
    })
    .catch(error => console.error("Error:", error));
});

// Vehicle Selection & Fare Calculation
$(".vehicle-card").click(function() {
    $(".vehicle-card").removeClass("border border-primary");
    $(this).addClass("border border-primary");

    let baseFare = parseFloat($(this).data("price"));
    let distance = parseFloat($("#rideDistance").val()) || 0;

    if (distance === 0) {
        alert("Please calculate the distance first.");
        return;
    }

    let totalFare = baseFare * distance;
    $(this).find(".fare").text(`Rs. ${totalFare.toFixed(2)}`);

    $("#paymentSection").removeClass("d-none");
});




//
//document.getElementById("calculateFare").addEventListener("click", function() {
//    let pickupLat = document.getElementById("pickupLat").value;
//    let pickupLon = document.getElementById("pickupLon").value;
//    let dropLat = document.getElementById("dropLat").value;
//    let dropLon = document.getElementById("dropLon").value;
//
//    fetch("FareCalculatorServlet", {
//        method: "POST",
//        headers: { "Content-Type": "application/x-www-form-urlencoded" },
//        body: `pickupLat=${pickupLat}&pickupLon=${pickupLon}&dropLat=${dropLat}&dropLon=${dropLon}`
//    })
//    .then(response => response.json())
//    .then(data => {
//        let distance = data.distance.toFixed(2); // Get distance from the backend response
//        
//        document.getElementById("fareDisplay").innerHTML = 
//            `Distance: ${distance} km <br> Select a vehicle to see the fare.`;
//
//        // Store distance in a hidden field for vehicle selection
//        document.getElementById("rideDistance").value = distance;
//    })
//    .catch(error => console.error("Error:", error));
//});
//
//// Vehicle Selection & Fare Calculation
//document.querySelectorAll(".vehicle-card").forEach(vehicle => {
//    vehicle.addEventListener("click", function() {
//        document.querySelectorAll(".vehicle-card").forEach(v => v.classList.remove("border", "border-primary"));
//        this.classList.add("border", "border-primary");
//
//        let baseFare = parseFloat(this.getAttribute("data-price")); // Get base price per km
//        let distance = parseFloat(document.getElementById("rideDistance").value) || 0; // Get distance
//
//        if (distance === 0) {
//            alert("Please calculate the distance first.");
//            return;
//        }
//
//        let totalFare = baseFare * distance; // Calculate total fare
//        this.querySelector(".fare").textContent = `Rs. ${totalFare.toFixed(2)}`;
//
//        document.getElementById("paymentSection").classList.remove("d-none"); // Show payment section
//    });
//});

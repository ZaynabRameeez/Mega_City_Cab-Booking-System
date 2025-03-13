<%-- 
    Document   : Register
    Created on : Feb 10, 2025, 11:39:04 AM
    Author     : zainr
--%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Mega City Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card p-4 shadow-lg" style="width: 400px;">
        <h3 class="text-center">Register</h3>

         <!-- Dynamic Error Message Display -->
        <% String error = request.getParameter("error"); 
           if (error != null && !error.isEmpty()) { %>
            <div class="alert alert-danger text-center">
              <%= error %>
            </div>
        <% } %>

        <form id="registerForm" action="RegisterServlet" method="post" >
            <div class="mb-3">
                 <label for="firstName" class="form-label">First Name</label>
                 <input type="text" id="firstName" name="firstName" class="form-control" required>
                <span id="firstName-error" class="text-danger"></span>
            </div>
            
             <div class="mb-3">
                 <label for="lastName" class="form-label">Last Name</label>
            <input type="text" id="lastName" name="lastName" class="form-control" required>
                <span id="lastName-error" class="text-danger"></span>
            </div>
            
            <div class="mb-3">
            <label for="birthday" class="form-label">Birthday</label>
            <input type="date" id="birthday" name="birthday" class="form-control" required>
                 <span id="birthday-error" class="birthday"></span>
          </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" name="email" id="email" class="form-control" required>
                <span id="email-error" class="text-danger"></span>
            </div>

            <div class="mb-3">
              <label for="mobile" class="form-label">Mobile Number (+94)</label>
            <input type="text" id="mobile" name="mobile" class="form-control" placeholder="+94XXXXXXXXX" required>
                <span id="mobile-error" class="text-danger"></span>
            </div>
            
            <div class="mb-3">
              <label for="nic" class="form-label">NIC</label>
               <input type="text" id="nic" name="nic" class="form-control" required>
               <span id="nic-error" class="text-danger"></span>
              </div>


           
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <input type="password" name="password" id="password" class="form-control" required>
                    <button class="btn btn-outline-secondary toggle-password" type="button" data-target="password">
                        <i class="bi bi-eye"></i>
                    </button>
                </div>
                <span id="password-error" class="text-danger"></span>
            </div>

                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <textarea class="form-control" name="address" required></textarea>
                </div>

            <button type="submit" class="btn btn-dark w-100">Register</button>
        </form>

        <div class="mt-3 text-center">
            <a href="Login.jsp">Already have an account? Login</a>
        </div>
    </div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    let form = document.getElementById("registerForm");

    function validateField(input, regex, errorMessage, errorSpanId) {
        let errorSpan = document.getElementById(errorSpanId);
        if (!regex.test(input.value)) {
            errorSpan.textContent = errorMessage;
            return false;
        } else {
            errorSpan.textContent = "";
            return true;
        }
    }

    function validatePassword() {
        let password = document.getElementById("password").value;
        let passwordError = document.getElementById("password-error");
        let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

        if (!passwordRegex.test(password)) {
            passwordError.textContent = "Min 8 chars, 1 letter, 1 number, 1 special char";
            return false;
        } else {
            passwordError.textContent = "Password is strong!";
            passwordError.style.color = "green";
            return true;
        }
    }

    document.getElementById("mobile").addEventListener("keyup", function () {
        validateField(this, /^[0-9]{10}$/, "Phone must be 10 digits.", "mobile-error");
    });

    document.getElementById("nic").addEventListener("keyup", function () {
        validateField(this, /^[0-9]{9}[Vv]$|^[0-9]{12}$/, "NIC must be in format 123456789V or 123456789012.", "nic-error");
    });

    document.getElementById("email").addEventListener("keyup", function () {
        validateField(this, /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, "Enter a valid email.", "email-error");
    });

    document.getElementById("password").addEventListener("keyup", validatePassword);

    form.addEventListener("submit", function (event) {
        let isMobileValid = validateField(document.getElementById("mobile"), /^[0-9]{10}$/, "Phone must be 10 digits.", "mobile-error");
        let isNicValid = validateField(document.getElementById("nic"), /^[0-9]{9}[Vv]$|^[0-9]{12}$/, "NIC must be 123456789V or 123456789012.", "nic-error");
        let isEmailValid = validateField(document.getElementById("email"), /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, "Enter a valid email.", "email-error");
        let isPasswordValid = validatePassword();

        if (!isMobileValid || !isNicValid || !isEmailValid || !isPasswordValid) {
            event.preventDefault(); // Stop form submission if validation fails
        }
    });

    document.querySelectorAll(".toggle-password").forEach(button => {
        button.addEventListener("click", function () {
            let targetId = this.getAttribute("data-target");
            let input = document.getElementById(targetId);
            let icon = this.querySelector("i");

            if (input.type === "password") {
                input.type = "text";
                icon.classList.replace("bi-eye", "bi-eye-slash");
            } else {
                input.type = "password";
                icon.classList.replace("bi-eye-slash", "bi-eye");
            }
        });
    });
});

</script>

</body>
</html>













<!--<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Mega City Cab</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <script>
        // Validate Password Matching
        function validatePassword() {
            let password = document.getElementById("password").value;
            let confirmPassword = document.getElementById("confirmPassword").value;
            let message = document.getElementById("passwordMismatch");

            if (password !== confirmPassword) {
                message.style.display = "block";
            } else {
                message.style.display = "none";
            }
        }

        // Toggle Password Visibility
        function togglePassword() {
            let passwordField = document.getElementById("password");
            let toggleIcon = document.getElementById("toggle-password");

            if (passwordField.type === "password") {
                passwordField.type = "text";
                toggleIcon.classList.remove("fa-eye");
                toggleIcon.classList.add("fa-eye-slash");
            } else {
                passwordField.type = "password";
                toggleIcon.classList.remove("fa-eye-slash");
                toggleIcon.classList.add("fa-eye");
            }
        }

        // Password Strength Check
        function checkPasswordStrength() {
            let password = document.getElementById("password").value;
            let strengthText = document.getElementById("password-strength");

            if (password.length === 0) {
                strengthText.textContent = "";
            } else if (password.length < 6) {
                strengthText.textContent = "Password is too short";
            } else if (!/[A-Z]/.test(password) || !/[0-9]/.test(password)) {
                strengthText.textContent = "Must have 1 uppercase & 1 number";
            } else {
                strengthText.textContent = "Strong password!";
            }
        }

        // Email Validation
        function validateEmail() {
            let email = document.getElementById("email").value;
            let emailError = document.getElementById("email-error");
            let emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

            if (!emailRegex.test(email)) {
                emailError.textContent = "Invalid email format";
            } else {
                emailError.textContent = "";
            }
        }

        // Phone Number Validation (Only digits, max 10)
        function validatePhone() {
            let phone = document.getElementById("phone").value;
            let phoneError = document.getElementById("phone-error");

            if (phone.length > 10) {
                phoneError.textContent = "Max 10 digits allowed";
            } else if (/[^0-9]/.test(phone)) {
                document.getElementById("phone").value = phone.replace(/[^0-9]/g, '');
                phoneError.textContent = "Only numbers allowed";
            } else {
                phoneError.textContent = "";
            }
        }

        // NIC Validation (Basic rule: 10 characters or 12 digits)
        function validateNIC() {
            let nic = document.getElementById("Nic").value;
            let nicError = document.getElementById("nic-error");
            let nicRegex = /^([0-9]{9}[VX]|[0-9]{12})$/i;

            if (!nicRegex.test(nic)) {
                nicError.textContent = "Invalid NIC format (Ex: 123456789V or 123456789012)";
            } else {
                nicError.textContent = "";
            }
        }
    </script>
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow-lg" style="width: 35rem; max-width: 100%; max-height: 90vh; overflow-y: auto;">
            <h2 class="text-center mb-4">Create an Account</h2>
            
        
            
            <form action="RegisterServlet" method="POST">

                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" class="form-control" name="username" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required onkeyup="validateEmail()">
                    <span id="email-error" class="text-danger" style="font-size: 12px;"></span>
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <input type="text" class="form-control" id="phone" name="phone" required onkeyup="validatePhone()">
                    <span id="phone-error" class="text-danger" style="font-size: 12px;"></span>
                </div>

                <div class="mb-3">
                    <label class="form-label">NIC Number</label>
                    <input type="text" class="form-control" id="Nic" name="nic" required onkeyup="validateNIC()">
                    <span id="nic-error" class="text-danger" style="font-size: 12px;"></span>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="password" name="password" required onkeyup="checkPasswordStrength()">
                        <button type="button" class="btn btn-outline-secondary" onclick="togglePassword()">
                            <i id="toggle-password" class="fa fa-eye"></i>
                        </button>
                        
                    </div>
                    <span id="password-strength" class="text-danger" style="font-size: 12px;"></span>
                </div>

                <div class="mb-3">
                    <label class="form-label">Confirm Password</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required onkeyup="validatePassword()">
                    <span id="passwordMismatch" class="text-danger" style="font-size: 12px; display: none;">Passwords do not match</span>
                </div>

                <div class="mb-3">
                    <label class="form-label">Role</label>
                    <select class="form-select" name="role" required>
                        <option value="user">User</option>
                        <option value="driver">Driver</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Gender</label>
                    <select class="form-select" name="gender" required>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <textarea class="form-control" name="address" required></textarea>
                </div>

                <button type="submit" class="btn btn-dark w-100">Register</button>
            </form>

            <div class="text-center mt-3">
                <p>Already have an account? <a href="Login.jsp" class="text-primary">Login</a></p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>-->

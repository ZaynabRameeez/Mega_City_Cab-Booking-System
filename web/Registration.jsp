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
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card p-4 shadow-lg" style="width: 400px;">
        <h3 class="text-center">Register</h3>

        <%-- Display Error Messages --%>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-danger text-center"><%= errorMessage %></div>
        <% } %>

        <form action="RegisterServlet" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" name="username" id="username" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" name="email" id="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input type="text" name="phone" id="phone" class="form-control" required>
                <span id="phone-error" class="text-danger" style="font-size: 12px;"></span>
            </div>

            <div class="mb-3">
                <label class="form-label">NIC Number</label>
                <input type="text" class="form-control" id="nic" name="nic" required>
                <span id="nic-error" class="text-danger" style="font-size: 12px;"></span>
            </div>

            <div class="mb-3">
                <label for="gender" class="form-label">Gender</label>
                <select name="gender" id="gender" class="form-select" required>
                    <option value="">Select Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <textarea name="address" id="address" class="form-control" rows="2" required></textarea>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <input type="password" name="password" id="password" class="form-control" required>
                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                        <i class="bi bi-eye"></i>
                    </button>
                </div>
                <span id="password-error" class="text-danger" style="font-size: 12px;"></span>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password</label>
                <div class="input-group">
                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
                    <button class="btn btn-outline-secondary" type="button" id="toggleConfirmPassword">
                        <i class="bi bi-eye"></i>
                    </button>
                </div>
                <span id="confirm-password-error" class="text-danger" style="font-size: 12px;"></span>
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
        let phoneInput = document.getElementById("phone");
        let nicInput = document.getElementById("nic");
        let passwordInput = document.getElementById("password");
        let confirmPasswordInput = document.getElementById("confirmPassword");
        
        phoneInput.addEventListener("keyup", validatePhone);
        nicInput.addEventListener("keyup", validateNIC);
        passwordInput.addEventListener("keyup", validatePassword);
        confirmPasswordInput.addEventListener("keyup", validateConfirmPassword);

        function validatePhone() {
            let phoneRegex = /^[0-9]{10}$/;
            let phoneError = document.getElementById("phone-error");

            if (!phoneRegex.test(phoneInput.value)) {
                phoneError.textContent = "Phone number must be exactly 10 digits.";
            } else {
                phoneError.textContent = "";
            }
        }

        function validateNIC() {
            let nicRegex = /^[0-9]{9}V$|^[0-9]{12}$/;
            let nicError = document.getElementById("nic-error");

            if (!nicRegex.test(nicInput.value)) {
                nicError.textContent = "NIC must be in the format 123456789V or 123456789012.";
            } else {
                nicError.textContent = "";
            }
        }

        function validatePassword() {
            let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
            let passwordError = document.getElementById("password-error");

            if (!passwordRegex.test(passwordInput.value)) {
                passwordError.textContent = "Password must have 8+ chars, 1 letter, 1 number, 1 special char.";
            } else {
                passwordError.textContent = "";
            }
        }

        function validateConfirmPassword() {
            let confirmPasswordError = document.getElementById("confirm-password-error");

            if (passwordInput.value !== confirmPasswordInput.value) {
                confirmPasswordError.textContent = "Passwords do not match.";
            } else {
                confirmPasswordError.textContent = "";
            }
        }

        // Password toggle functionality
        document.getElementById("togglePassword").addEventListener("click", function () {
            let type = passwordInput.type === "password" ? "text" : "password";
            passwordInput.type = type;
            this.innerHTML = type === "password" ? '<i class="bi bi-eye"></i>' : '<i class="bi bi-eye-slash"></i>';
        });

        document.getElementById("toggleConfirmPassword").addEventListener("click", function () {
            let type = confirmPasswordInput.type === "password" ? "text" : "password";
            confirmPasswordInput.type = type;
            this.innerHTML = type === "password" ? '<i class="bi bi-eye"></i>' : '<i class="bi bi-eye-slash"></i>';
        });

        // Redirect to login.jsp on successful registration
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            alert("<%= successMessage %>");
            window.location.href = "Login.jsp";
        <% } %>
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

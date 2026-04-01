// Authentication Toggle (Login/Signup)
const toggleBtn = document.getElementById('toggle-auth');
if (toggleBtn) {
    toggleBtn.addEventListener('click', (e) => {
        e.preventDefault();
        const title = document.getElementById('auth-title');
        const signupFields = document.getElementById('signup-fields');
        
        if (title.innerText === "Citizen Login") {
            title.innerText = "Create Account";
            signupFields.innerHTML = '<input type="text" placeholder="Full Name" required>';
            toggleBtn.innerText = "Login here";
        } else {
            title.innerText = "Citizen Login";
            signupFields.innerHTML = '';
            toggleBtn.innerText = "Create Account";
        }
    });
}

// Simple Form Handlers
document.addEventListener('submit', (e) => {
    e.preventDefault();
    if (e.target.id === 'login-form') {
        window.location.href = 'submit.html'; // Redirect on "Login"
    } else if (e.target.id === 'grievance-form') {
        alert("Grievance submitted successfully!");
        window.location.href = 'status.html';
    }
});

// Forgot Password
const forgotLink = document.getElementById('forgot-pw');
if (forgotLink) {
    forgotLink.addEventListener('click', () => alert("Password reset link sent to your email!"));
}
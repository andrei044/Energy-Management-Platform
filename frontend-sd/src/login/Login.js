import React, { useState } from 'react';
import { makeRequestToUser } from '../api/api';
import { useNavigate } from 'react-router-dom';

// const HOST_USER = process.env.REACT_APP_HOST_USER;

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    
    const handleLogin = async (e) => {
      e.preventDefault();
      localStorage.clear();

      const loginData={
        "username":username,
        "password":password
      }
  
      try {
        const token = await makeRequestToUser('/login', 'POST', loginData);
        console.log(token)
        if (!token) throw new Error("Token not found in response");
        // Decode the Base64 token
        // Split the token into parts
        const parts = token.split('.');
        // The payload is the second part
        const payload = parts[1];

        // Decode the Base64URL payload
        const decodedPayload = JSON.parse(atob(payload));
        console.log(decodedPayload)
        const role = decodedPayload.role; // Adjust based on the token's structure

        localStorage.setItem("username",decodedPayload.sub)
        localStorage.setItem("token",token)
        localStorage.setItem("role",role)
        
        // Navigate based on the role
        if (role === "ROLE_ADMIN") {
          navigate("/admin");
        } else if (role === "ROLE_CLIENT") {
            navigate("/client");
        } else {
            throw new Error("Unknown role in token");
        }
      } catch (error) {
        console.error('Login error:', error);
        alert('Login failed. Please try again.');
      }
    };
    

    const handleTest = async (e) =>{
        e.preventDefault();
        const response = await makeRequestToUser('/admin','GET');

        console.log(response)
    }
  
    return (
      <div>
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
          <div>
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    );
  };
  
  export default Login;


// import React, { useState } from 'react';

// const Login = () => {
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');

//     const handleLogin = async (event) => {
//         event.preventDefault();

//         const formData = new URLSearchParams();
//         formData.append('username', username);
//         formData.append('password', password);

//         try {
//             const response = await fetch(`${process.env.REACT_APP_HOST_USER || 'http://localhost:8080'}/login`, {
//                 method: 'POST',
//                 body: formData,
//                 credentials: 'include', // Important for receiving cookies
//                 headers: {
//                     'Content-Type': 'application/x-www-form-urlencoded',
//                 },
//             });

//             if (!response.ok) {
//                 throw new Error('Login failed');
//             }

//             const data = await response.json();
//             console.log('Login successful:', data);
//             // Handle successful login (e.g., redirect or store user info)
//         } catch (error) {
//             console.error('Error:', error);
//             // Handle login error (e.g., show error message)
//         }
//     };

//     return (
//         <form onSubmit={handleLogin}>
//             <div>
//                 <label htmlFor="username">Username:</label>
//                 <input
//                     type="text"
//                     id="username"
//                     value={username}
//                     onChange={(e) => setUsername(e.target.value)}
//                     required
//                 />
//             </div>
//             <div>
//                 <label htmlFor="password">Password:</label>
//                 <input
//                     type="password"
//                     id="password"
//                     value={password}
//                     onChange={(e) => setPassword(e.target.value)}
//                     required
//                 />
//             </div>
//             <button type="submit">Login</button>
//         </form>
//     );
// };

// export default Login;
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Register.css";

const Register = () => {
  const [user, setUser] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    phoneNumber: "",
    email: "",
    address: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/register", user);
      alert(response.data); // "User Registered Successfully!"
      navigate("/login"); // Redirect to Login page after successful registration
    } catch (error) {
      alert("Registration failed!");
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
        <input type="text" name="firstName" placeholder="First Name" onChange={handleChange} required />
        <input type="text" name="middleName" placeholder="Middle Name" onChange={handleChange} />
        <input type="text" name="lastName" placeholder="Last Name" onChange={handleChange} required />
        <input type="text" name="phoneNumber" placeholder="Phone Number" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
        <input type="text" name="address" placeholder="Address" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <button onClick={() => navigate("/login")}>Login</button></p>
    </div>
  );
};

export default Register;

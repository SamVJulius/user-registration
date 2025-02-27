import React, { useState } from "react";
import axios from "axios";

const UpdateUser = () => {
  const [user, setUser] = useState({
    firstName: "",
    middleName: "",
    lastName: "",
    phoneNumber: "",
    email: "",
    address: "",
  });

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put("http://localhost:8080/api/auth/update", user);
      alert(response.data);
    } catch (error) {
      alert("Error updating info!");
    }
  };

  return (
    <div>
      <h2>Update User Information</h2>
      <form onSubmit={handleUpdate}>
        <input type="text" name="firstName" placeholder="First Name" onChange={handleChange} required />
        <input type="text" name="middleName" placeholder="Middle Name" onChange={handleChange} />
        <input type="text" name="lastName" placeholder="Last Name" onChange={handleChange} required />
        <input type="text" name="phoneNumber" placeholder="Phone Number" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
        <input type="text" name="address" placeholder="Address" onChange={handleChange} required />
        <button type="submit">Update</button>
      </form>
    </div>
  );
};

export default UpdateUser;

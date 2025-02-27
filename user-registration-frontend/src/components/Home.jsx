import React from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Welcome to Home Page</h2>
      <button onClick={() => navigate("/update")}>Update Info</button>
    </div>
  );
};

export default Home;

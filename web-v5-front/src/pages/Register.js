import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    username: "",
    password: "",
    // enabled: "true",
  });

  const onChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  const userSave = (e) => {
    e.preventDefault();

    fetch(`/api/user/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    })
      .then((res) => res.json())
      .then((newData) => {
        console.log(newData);
        navigate("/login");
      });
  };

  return (
    <div>
      <div>
        <h2>회원가입</h2>
        <span>아이디</span>
        <input
          name="username"
          type="text"
          value={user.author}
          onChange={onChange}
        />
      </div>
      <div>
        <span>패스워드</span>
        <input
          name="password"
          type="password"
          value={user.title}
          onChange={onChange}
        />
      </div>
      <button onClick={userSave}>확인</button>
      <button onClick={() => navigate("/login")}>로그인으로</button>
    </div>
  );
}

export default Register;

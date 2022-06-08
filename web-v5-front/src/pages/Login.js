import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    username: "",
    password: "",
  });

  const onChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
    // console.log(encodeURIComponent(user.username));
  };

  const userLogin = (e) => {
    e.preventDefault();
    // console.log(user);
    fetch(`/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      body: JSON.stringify(user),
    })
      .then((res) => {
        console.log("status: ", res.status);
        if (res.status !== 200) {
          return navigate("/login");
        } else {
          window.location.href = "/board";
          let jwtToken = res.headers.get("Authorization");
          localStorage.setItem("Authorization", jwtToken);
          // return res.json();
        }
      })
      // .then((res) => {
      //   console.log(res);
      //   // console.log("로그인 성공");
      //   navigate("/board");
      // })
      .catch((err) => {
        console.log(err);
        alert(err);
      });
  };

  return (
    <div>
      <div>
        <h2>로그인</h2>
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
      <button onClick={userLogin}>확인</button>
      <button onClick={() => navigate("/register")}>회원가입으로</button>
    </div>
  );
}

export default Login;

import React from "react";
import { Link, useNavigate } from "react-router-dom";

function Nav() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("Authorization");
    if (localStorage.getItem("Authorization") === null) {
      console.log("로그아웃 완료");
      return navigate("/login");
    } else {
      alert("로그아웃 실패");
    }
    // fetch("/", {
    //   method: "GET",
    //   headers: {
    //     Authorization: localStorage.getItem("Authorization"),
    //   },
    // })
    //   .then((res) => {
    //     localStorage.setItem("Authorization", "");
    //     res.json()})
    //   .then((newData) => {
    //     console.log(newData);
    //     navigate("/login");
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //     alert(err);
    //   });
  };

  return (
    <div>
      <h1>WEB - v5 - spring boot</h1>
      {localStorage.getItem("Authorization") === null ? (
        ""
      ) : (
        <>
          <Link to="/user/detail">사용자 정보</Link>
          <button onClick={logout}>로그아웃</button>
          <div>
            <Link to="/board">글목록</Link>
          </div>
        </>
      )}
    </div>
  );
}

export default Nav;

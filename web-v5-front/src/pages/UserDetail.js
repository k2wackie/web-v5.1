import React, { useEffect, useState } from "react";
import jwt_decode from "jwt-decode";

function UserDetail() {
  const [user, setUser] = useState([]);

  useEffect(() => {
    const jwtHeader = window.localStorage.getItem("Authorization");
    // console.log(jwtHeader);
    const decodeUser = jwt_decode(jwtHeader);
    console.log("decodeUser: ", decodeUser.id);

    fetch(`/api/user/${decodeUser.id}`, {
      method: "GET",
      headers: {
        Authorization: localStorage.getItem("Authorization"),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        if (res === "err") {
          window.location.href = "/login";
        }
        console.log(res);
        setUser(res);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div>
      <span>사용자 이름: </span>
      <span>{user.username}</span>
    </div>
  );
}

export default UserDetail;

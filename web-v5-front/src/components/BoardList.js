import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import BoardItem from "./BoardItem";

function BoardList() {
  const navigate = useNavigate();

  const [data, setData] = useState([]);

  // console.log(data);
  useEffect(() => {
    fetch("/api/board", {
      method: "GET",
      headers: {
        Authorization: localStorage.getItem("Authorization"),
      },
    })
      .then((res) => res.json())
      .then((newData) => {
        console.log(newData);
        if (newData === "err") {
          window.location.href = "/login";
        }
        setData(newData);
      })
      .catch((err) => console.log(err));
  }, [navigate]);

  return (
    <div>
      <Link to="/board/create">새 글 생성</Link>

      <br />
      <br />
      {data.map((it) => (
        <BoardItem key={it.id} {...it} />
      ))}
    </div>
  );
}

export default BoardList;

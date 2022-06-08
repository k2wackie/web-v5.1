import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function BoardCreate() {
  const navigate = useNavigate();

  const [data, setData] = useState({
    user: {
      username: "ackie",
    },
    title: "",
    content: "",
  });

  const onChange = (e) => {
    setData({
      ...data,
      [e.target.name]: e.target.value,
    });
  };

  const boardSave = (e) => {
    e.preventDefault();

    fetch(`/api/board`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("Authorization"),
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        console.log("status: ", res.status);
        res.json();
      })
      .then((newData) => {
        console.log(newData);
        navigate("/board");
      });
  };

  return (
    <div>
      <div>
        <span>제목</span>
        <input name="title" value={data.title} onChange={onChange} />
      </div>
      <div>
        <span>내용</span>
        <input name="content" value={data.content} onChange={onChange} />
      </div>
      <button onClick={boardSave}>확인</button>
      <button onClick={() => navigate("/board")}>뒤로</button>
    </div>
  );
}

export default BoardCreate;

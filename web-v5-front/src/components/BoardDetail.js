import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import jwt_decode from "jwt-decode";

function BoardDetail() {
  const [data, setData] = useState([]);
  const [mode, setMode] = useState("read");
  const [isUser, setIsUser] = useState("notUser");
  const [reply, setReply] = useState([]);

  const navigate = useNavigate();

  const id = useParams().id;

  const username = data.user === undefined || null ? "" : data.user.username;

  useEffect(() => {
    fetch(`/api/board/${id}`, {
      method: "GET",
      headers: {
        Authorization: localStorage.getItem("Authorization"),
      },
    })
      .then((res) => res.json())
      .then((newData) => {
        if (newData === "err") {
          window.location.href = "/login";
        }
        setData(newData);
        setReply(newData.replies);
      });
  }, [id, navigate]);

  useEffect(() => {
    const jwtHeader = window.localStorage.getItem("Authorization");
    // console.log(jwtHeader);
    const decodeUser = jwt_decode(jwtHeader);
    // console.log("decodeUser: ", decodeUser.username);

    if (decodeUser.username === username || decodeUser.username === "admin") {
      setIsUser("isUser");
    }
  }, [username]);

  const modeChange = (e) => {
    e.preventDefault();
    if (mode === "read") {
      setMode("write");
    } else if (mode === "write") {
      fetch(`/api/board/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("Authorization"),
        },
        body: JSON.stringify(data),
      })
        .then((res) => console.log("status: ", res.status))
        .then(() => {
          navigate("/board");
          setMode("read");
        })
        .catch((err) => console.log(err));
    }
  };

  const onChange = (e) => {
    setData({
      ...data,
      [e.target.name]: e.target.value,
    });
  };

  const deleteBoard = (e) => {
    e.preventDefault();

    fetch(`/api/board/delete/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("Authorization"),
      },
      body: JSON.stringify(data),
    })
      .then((res) => console.log("status: ", res.status))
      .then(() => {
        navigate("/board");
      })
      .catch((err) => console.log(err));
  };

  const date = new Date(data.updateDateTime);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  console.log(reply);
  console.log(reply.length === 0);
  console.log(reply.length === 0 ? "" : reply[1].user.username);

  return (
    <div>
      <div className="board-item">
        {mode === "read" ? (
          <>
            <div>글 id: {data.id}</div>
            <div>작성자: {username}</div>
            <div>제목: {data.title}</div>
            <div>내용: {data.content}</div>
            <div>
              작성시간: {year}년 {month}월 {day}일 {hours}시 {minutes}분
            </div>
            <div>
              댓글:
              <div>
                {reply.length === 0
                  ? ""
                  : reply.map((data, i) => (
                      <div key={i}>
                        <div>이름: {data.user.username}</div>
                        <div>{data.content}</div>
                      </div>
                    ))}
              </div>
            </div>
          </>
        ) : (
          <>
            <div>글 id: {data.id}</div>
            <div>
              <span>제목</span>
              <input name="title" value={data.title} onChange={onChange} />
            </div>
            <div>
              <span>내용</span>
              <input name="content" value={data.content} onChange={onChange} />
            </div>
            <div>
              <div>댓글 작성</div>
              <input name="replies" value={data.replies} onChange={onChange} />
            </div>
          </>
        )}
        <br />
        {isUser === "isUser" ? (
          <>
            <button onClick={modeChange}>수정</button>
            <button onClick={deleteBoard}>삭제</button>
          </>
        ) : (
          <></>
        )}
        <button onClick={() => navigate("/board")}>글목록 보기</button>
      </div>
    </div>
  );
}

export default BoardDetail;

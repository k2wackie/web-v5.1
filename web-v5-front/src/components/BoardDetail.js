import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import jwt_decode from "jwt-decode";

function BoardDetail() {
  const [data, setData] = useState([]);
  const [reply, setReply] = useState([]);
  const [modeBoard, setModeBoard] = useState("read");
  const [isUser, setIsUser] = useState("notUser");
  const [loginUser, setLoginUser] = useState("");
  const [newReply, setNewReply] = useState({ content: "" });
  const [editReply, setEditReply] = useState({ content: "" });
  const [modeRelpy, setModeReply] = useState("read");
  const [update, setUpdate] = useState();

  const navigate = useNavigate();

  const id = useParams().id;

  const boardUsername =
    data.user === undefined || null ? "null" : data.user.username;

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
        // setReply(newData.replies);
      });
  }, [id, update]);

  useEffect(() => {
    const jwtHeader = window.localStorage.getItem("Authorization");
    // console.log(jwtHeader);
    const decodeUser = jwt_decode(jwtHeader);
    // console.log("decodeUser: ", decodeUser.username);
    setLoginUser(decodeUser.username);
    if (loginUser === boardUsername || loginUser === "admin") {
      setIsUser("isUser");
    }
  }, [loginUser, boardUsername]);

  const modeEditBoard = (e) => {
    e.preventDefault();
    if (modeBoard === "read") {
      setModeBoard("write");
    } else if (modeBoard === "write") {
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
          setModeBoard("read");
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

  //------------------reply----------------------------------------------------------------------------------------
  //------------------reply----------------------------------------------------------------------------------------
  //------------------reply----------------------------------------------------------------------------------------

  useEffect(() => {
    fetch(`/api/board/${id}/reply`, {
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
        setReply(newData);
      });
  }, [id, update]);

  const replyOnChange = (e) => {
    setNewReply({ [e.target.name]: e.target.value });
  };

  const replySave = (e) => {
    e.preventDefault();

    fetch(`/api/board/${id}/reply`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("Authorization"),
      },
      body: JSON.stringify(newReply),
    })
      .then((res) => {
        console.log("status: ", res.status);
        res.json();
      })
      .then((res) => {
        // console.log(reply);
        setNewReply({ content: "" });
        // console.log(newReply);
        setUpdate((n) => !n);
        navigate(`/board/${id}`);
      })
      .catch((err) => console.log(err));
  };

  const editReplyOnChange = (e) => {
    setEditReply({ [e.target.name]: e.target.value });
  };

  const modeEditReply = (e) => {
    editReply.id = parseInt(e.target.value);
    if (modeRelpy === "read") {
      // console.log(e.target.value);
      // console.log(reply.find((r) => r.id === parseInt(e.target.value)).content);
      setModeReply(`write_${e.target.value}`);
    } else {
      fetch(`/api/board/${id}/reply`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("Authorization"),
        },
        body: JSON.stringify(editReply),
      })
        .then((res) => {
          console.log("status: ", res.status);
          res.json();
        })
        .then((res) => {
          setEditReply({ content: "" });
          setModeReply("read");
          setUpdate((n) => !n);
          navigate(`/board/${id}`);
        })
        .catch((err) => console.log(err));
    }
  };

  const delReply = (e) => {
    e.preventDefault();

    console.log(e.target.value);

    const delReply = {
      id: e.target.value,
    };

    fetch("/api/reply/delete", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("Authorization"),
      },
      body: JSON.stringify(delReply),
    })
      .then((res) => console.log("status: ", res.status))
      .then(() => {
        setUpdate((n) => !n);
        navigate(`/board/${id}`);
      })
      .catch((err) => console.log(err));
  };

  const date = new Date(data.updateDateTime);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  // console.log(reply);

  return (
    <div>
      <div className="board-item">
        {modeBoard === "read" ? (
          <>
            <div>??? id: {data.id}</div>
            <div>?????????: {boardUsername}</div>
            <div>??????: {data.title}</div>
            <div>??????: {data.content}</div>
            <div>
              ????????????: {year}??? {month}??? {day}??? {hours}??? {minutes}???
            </div>
            <div>
              ??????------------------------------------
              <div>
                {reply.length === 0
                  ? ""
                  : reply.map((data, i) => (
                      <div key={i}>
                        <div>??????: {data.user.username}</div>
                        <div>{data.content}</div>
                        {modeRelpy === `write_${data.id}` ? (
                          <input
                            type="text"
                            name="content"
                            onChange={editReplyOnChange}
                          ></input>
                        ) : (
                          ""
                        )}

                        <div>
                          {loginUser === data.user.username ||
                          loginUser === "admin" ? (
                            <>
                              <button
                                name="id"
                                value={data.id}
                                onClick={modeEditReply}
                              >
                                ??????
                              </button>
                              <button value={data.id} onClick={delReply}>
                                ??????
                              </button>
                            </>
                          ) : (
                            ""
                          )}
                        </div>
                      </div>
                    ))}
              </div>
            </div>
            <br />
            {modeRelpy === "read" ? (
              <div>
                <input
                  type="text"
                  value={newReply.content}
                  name="content"
                  onChange={replyOnChange}
                />
                <button onClick={replySave}>????????????</button>
              </div>
            ) : (
              ""
            )}
          </>
        ) : (
          <>
            <div>??? id: {data.id}</div>
            <div>
              <span>??????</span>
              <input
                type="text"
                name="title"
                value={data.title}
                onChange={onChange}
              />
            </div>
            <div>
              <span>??????</span>
              <input
                type="text"
                name="content"
                value={data.content}
                onChange={onChange}
              />
            </div>
          </>
        )}
        <br />

        {isUser === "isUser" ? (
          <div>
            <button onClick={modeEditBoard}>?????????</button>
            <button onClick={deleteBoard}>?????????</button>
          </div>
        ) : (
          <></>
        )}
        <button onClick={() => navigate("/board")}>????????? ??????</button>
      </div>
    </div>
  );
}

export default BoardDetail;

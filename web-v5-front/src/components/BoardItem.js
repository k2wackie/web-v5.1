import { useNavigate } from "react-router-dom";

function BoardItem(data) {
  const navigate = useNavigate();

  // console.log(data.updateDateTime);

  const username = data.user === undefined || null ? "" : data.user.username;

  const handleDetatil = () => {
    navigate(`/board/${data.id}`);
  };

  // const date = data.updateDateTime;
  const date = new Date(data.updateDateTime);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  // console.log(date);

  return (
    <div>
      <div className="board-item" onClick={handleDetatil}>
        <div>글 id: {data.id}</div>
        <div>작성자: {username}</div>
        <div>제목: {data.title}</div>
        <div>내용: {data.content}</div>
        <div>
          작성시간: {year}년 {month}월 {day}일 {hours}시 {minutes}분
          {/* 작성시간: {date[0]}년 {date[1]}월 {date[2]}일 {date[3]}시 {date[4]}분 */}
        </div>
        <br />
      </div>
    </div>
  );
}

export default BoardItem;

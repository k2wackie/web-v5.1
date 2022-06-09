import { BrowserRouter, Route, Routes } from "react-router-dom";
import BoardCreate from "./components/BoardCreate";
import BoardDetail from "./components/BoardDetail";
import Nav from "./components/Nav";
import "./css/App.css";
import Board from "./pages/Board";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import UserDetail from "./pages/UserDetail";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Nav></Nav>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/board" element={<Board />} />
          <Route path="/board/create" element={<BoardCreate />} />
          <Route path="/board/:id" element={<BoardDetail />} />
          <Route path="/user/detail" element={<UserDetail />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;

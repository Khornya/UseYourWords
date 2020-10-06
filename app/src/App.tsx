import React, { SyntheticEvent } from "react";
import logo from "./logo.svg";
import "./App.css";
import $ from "jquery";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

function App() {
  return (
    <div className="App">
      <div id="userJoin" className="container">
        <br />
        <br />
        <div className="card">
          <div className="card-body">
            <h1>My Chat App Example - nulPointerException.com</h1>
            <a
              className="btn btn-primary"
              href="https://nulpointerexception.com/"
              role="button"
            >
              More tutorials at nulPointerException.com
            </a>
          </div>
        </div>
        <br />
        <br />
        <form id="userJoinForm" name="userJoinForm">
          <div className="form-group">
            <label htmlFor="name">Enter Name:</label>
            <input
              type="text"
              className="form-control"
              id="name"
              aria-describedby="name"
              placeholder="Enter name"
            />
          </div>
          <div className="form-group">
            <label htmlFor="room">Enter Room:</label>
            <input
              type="text"
              className="form-control"
              id="room"
              aria-describedby="exampleInputRoom"
              placeholder="Enter room"
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>

      <div id="chatPage" className="container d-none">
        <div className="card">
          <div className="card-body">
            <h1>My Chat App Example - nulPointerException.com</h1>
            <a
              className="btn btn-primary"
              href="https://nulpointerexception.com/"
              role="button"
            >
              More tutorials at nulPointerException.com
            </a>
          </div>
        </div>
        <div className="chat-header">
          <h2>
            Chatroom [<span id="room-id-display"></span>]
          </h2>
        </div>
        <div className="waiting">We are waiting to enter the room.</div>
        <div className="card">
          <div className="card-body">
            <ul id="messageArea"></ul>
          </div>
        </div>
        <form id="messagebox" name="messagebox">
          <div className="form-group">
            <label htmlFor="message">Enter Message:</label>
            <input
              type="text"
              className="form-control"
              id="message"
              aria-describedby="name"
              placeholder="Enter message to chat ...."
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Send
          </button>
        </form>
      </div>
    </div>
  );
}

let usernamePage = document.querySelector("#userJoin");
let chatPage = document.querySelector("#chatPage");
let room = $("#room");
let name = $("#name").val().toString().trim();
let waiting = document.querySelector(".waiting");
let roomIdDisplay = document.querySelector("#room-id-display");
let stompClient: any = null;
let currentSubscription;
let topic = null;
let username;

function connect(event: Event) {
  let name1 = $("#name").val().toString().trim();
  // Cookies.set("name", name1);
  usernamePage.classList.add("d-none");
  chatPage.classList.remove("d-none");
  let socket = new SockJS("/sock");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
  event.preventDefault();
}

function onConnected() {
  enterRoom(Number(room.val()));
  waiting.classList.add("d-none");
}

function onError(error: Error) {
  waiting.textContent = "uh oh! service unavailable";
}

function enterRoom(newRoomId: number) {
  let roomId = newRoomId;
  // Cookies.set("roomId", room);
  roomIdDisplay.textContent = roomId.toString();
  topic = `/chat-app/chat/${newRoomId}`;

  currentSubscription = stompClient.subscribe(
    `/chat-room/${roomId}`,
    onMessageReceived
  );
  let username = $("#name").val().toString().trim();
  stompClient.send(
    `${topic}/addUser`,
    {},
    JSON.stringify({ sender: username, type: "JOIN" })
  );
}

function sendMessage(event: Event) {
  let messageContent = $("#message").val().toString().trim();
  let username = $("#name").val().toString().trim();
  let newRoomId = parseInt($("#room").val().toString().trim());
  topic = `/chat-app/chat/${newRoomId}`;
  if (messageContent && stompClient) {
    let chatMessage = {
      sender: username,
      content: messageContent,
      type: "CHAT",
    };

    stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));
    (document.querySelector("#message") as HTMLInputElement).value = "";
  }
  event.preventDefault();
}

function onMessageReceived(payload: any) {
  let message = JSON.parse(payload.body);
  let messageElement = document.createElement("li");
  let divCard = document.createElement("div");
  divCard.className = "card";

  if (message.type === "JOIN") {
    messageElement.classList.add("event-message");
    message.content = message.sender + " joined!";
  } else if (message.type === "LEAVE") {
    messageElement.classList.add("event-message");
    message.content = message.sender + " left!";
  } else {
    messageElement.classList.add("chat-message");

    let avatarElement = document.createElement("i");
    let avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);

    messageElement.appendChild(avatarElement);

    let usernameElement = document.createElement("span");
    let usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    let divCardBody = document.createElement("div");
    divCardBody.className = "card-body";

    divCardBody.appendChild(messageElement);
    divCard.appendChild(divCardBody);
  }

  let textElement = document.createElement("p");
  let messageText = document.createTextNode(message.content);
  textElement.appendChild(messageText);

  messageElement.appendChild(textElement);
  let messageArea = document.querySelector("#messageArea");
  messageArea.appendChild(divCard);
  messageArea.scrollTop = messageArea.scrollHeight;
}

$(document).ready(function () {
  $("#userJoinForm").on("submit", connect);
  $("#messageBox").on("submit", sendMessage);
});

export default App;

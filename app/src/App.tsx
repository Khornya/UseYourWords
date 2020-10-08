import * as React from "react";
import logo from "./logo.svg";
import "./App.css";
import $ from "jquery";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

class App extends React.PureComponent {
  namePage: any;
  chatPage: any;
  room: JQuery<HTMLElement>;
  name: string;
  waiting: any;
  roomIdDisplay: any;
  stompClient: any;
  currentSubscription: any;
  topic: any;
  username: any;

  public App() {}

  componentDidMount = () => {
    this.namePage = document.querySelector("#userJoin");
    this.chatPage = document.querySelector("#chatPage");
    this.room = $("#room");
    this.name = $("#name").val().toString().trim();
    this.waiting = document.querySelector(".waiting");
    this.roomIdDisplay = document.querySelector("#room-id-display");
    this.stompClient = null;
    this.currentSubscription = null;
    this.topic = null;
    this.username = null;

    let socket = new SockJS("/sock");
    this.stompClient = Stomp.over(socket);
  };

  render = () => {
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
          <form id="userJoinForm" name="userJoinForm" onSubmit={this.connect}>
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
          <form id="messagebox" name="messagebox" onSubmit={this.sendMessage}>
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
  };

  connect = (event: React.FormEvent) => {
    event.preventDefault();
    event.stopPropagation();
    let name1 = $("#name").val().toString().trim();
    this.namePage.classList.add("d-none");
    this.chatPage.classList.remove("d-none");
    let socket = new SockJS("/sock");
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, this.onConnected, this.onError);
  };

  onConnected = () => {
    console.log("connected");
    this.enterRoom(Number(this.room.val()));
    this.waiting.classList.add("d-none");
  };

  onError = (error: Error) => {
    this.waiting.textContent = "uh oh! service unavailable";
  };

  enterRoom = (newRoomId: number) => {
    let roomId = newRoomId;
    this.roomIdDisplay.textContent = roomId.toString();
    this.topic = `/chat-app/chat/${newRoomId}`;

    this.currentSubscription = this.stompClient.subscribe(
      `/chat-room/${roomId}`,
      this.onMessageReceived
    );
    let username = $("#name").val().toString().trim();
    this.stompClient.send(
      `${this.topic}/addUser`,
      {},
      JSON.stringify({ sender: username, type: "JOIN" })
    );
  };

  sendMessage = (event: React.FormEvent) => {
    event.preventDefault();
    let messageContent = $("#message").val().toString().trim();
    let username = $("#name").val().toString().trim();
    let newRoomId = parseInt($("#room").val().toString().trim());
    this.topic = `/chat-app/chat/${newRoomId}`;
    if (messageContent && this.stompClient) {
      let chatMessage = {
        sender: username,
        content: messageContent,
        type: "CHAT",
      };

      this.stompClient.send(
        `${this.topic}/sendMessage`,
        {},
        JSON.stringify(chatMessage)
      );
      (document.querySelector("#message") as HTMLInputElement).value = "";
    }
  };

  onMessageReceived = (payload: any) => {
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
  };
}

export default App;

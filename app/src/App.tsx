import * as React from "react";
import "./App.css";
import $ from "jquery";
import SockJS from "sockjs-client";
import Stomp, { Frame } from "stompjs";
import WaitingMessage from "./waitingMessage";
import GameForm from "./gameForm";
import WaitingRoom from "./waitingRoom";
import GameRoom from "./gameRoom";

class App extends React.PureComponent {
  private stompClient: Stomp.Client;
  private stompSubscription: Stomp.Subscription;

  state = {
    isWaitingForConnection: true,
    isWaitingToPlay: false,
    isJoining: false,
    isPlaying: false,
  };

  componentDidMount = () => {
    this.stompClient = Stomp.over(new SockJS("/sock"));
    this.stompClient.connect({}, this.onConnected, this.onError);
  };

  render = () => {
    return (
      <div className="app d-flex justify-content-center flex-column">
        <div className="banner d-flex justify-content-center">
          <h1>Use Your Words</h1>
        </div>
        {this.state.isWaitingToPlay && <WaitingRoom />}
        {this.state.isWaitingForConnection && <WaitingMessage />}
        {this.state.isJoining && <GameForm stompClient={this.stompClient} />}
        {this.state.isPlaying && <GameRoom />}
      </div>
    );
  };

  private enterRoom = (newGameId?: string) => {
    let gameId = newGameId;
    let username = $("#name").val().toString().trim();
    newGameId = newGameId ? newGameId : "null";
    // this.topic = `app/join/${newGameId}/${username}`;

    this.stompClient.send(
      "/app/join/null/Khornya",
      {} //JSON.stringify({ sender: username, type: "JOIN" })
    );
    this.stompClient.send(
      "app/chat/null/addUser",
      {},
      "" //JSON.stringify({ sender: username, type: "JOIN" })
    );
  };

  onConnected = () => {
    console.log("connected");
    this.setState({
      isWaitingForConnection: false,
      isJoining: true,
    });
    // this.stompSubscription = this.stompClient.subscribe(
    //   `/game-room/${gameId}`,
    //   this.onMessageReceived
    // );
    this.enterRoom();
    return true;
  };

  onError = (error: string | Frame) => {
    console.error("Error while connecting to websocket : ", error);
    // TODO: display error
    return true;
  };

  sendMessage = (event: React.FormEvent) => {
    event.preventDefault();
    let messageContent = $("#message").val().toString().trim();
    let username = $("#name").val().toString().trim();
    let newRoomId = parseInt($("#room").val().toString().trim());
    // this.topic = `/chat-app/chat/${newRoomId}`;
    if (messageContent && this.stompClient) {
      let chatMessage = {
        sender: username,
        content: messageContent,
        type: "CHAT",
      };

      // this.stompClient.send(
      //   `${this.topic}/sendMessage`,
      //   {},
      //   JSON.stringify(chatMessage)
      // );
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

import * as React from "react";
import "./App.css";
import $ from "jquery";
import Stomp, { Frame } from "stompjs";
import WaitingMessage from "./waitingMessage";
import GameForm from "./gameForm";
import WaitingRoom from "./waitingRoom";
import GameRoom from "./gameRoom";
import _ from "lodash-es";

class App extends React.Component {
  private stompClient: Stomp.Client;
  private stompSubscription: Stomp.Subscription;
  private gameId: string;
  private joinFormError: string;

  state = {
    isWaitingForConnection: true,
    isWaitingToPlay: false,
    isJoining: false,
    isPlaying: false,
  };

  componentDidMount = () => {
    this.stompClient = Stomp.client("ws://localhost:8080/sock");
    this.stompClient.connect({}, this.onConnected, this.onError);
    this.joinFormError = "";
  };

  render = () => {
    return (
      <div className="app d-flex justify-content-center flex-column">
        <div className="banner d-flex justify-content-center">
          <h1>Use Your Words</h1>
        </div>
        {this.state.isWaitingToPlay && <WaitingRoom />}
        {this.state.isWaitingForConnection && <WaitingMessage />}
        {this.state.isJoining && (
          <GameForm
            stompClient={this.stompClient}
            joinFormError={this.joinFormError}
          />
        )}
        {this.state.isPlaying && <GameRoom gameId={this.gameId} />}
      </div>
    );
  };

  private onConnected = () => {
    console.log("connected");
    this.setState({
      isWaitingForConnection: false,
      isJoining: true,
    });
    this.stompSubscription = this.stompClient.subscribe(
      `/user/queue/reply`,
      this.onMessageReceived
    );
    return true;
  };

  private onError = (error: string | Frame) => {
    console.error("Error while connecting to websocket : ", error);
    // TODO: display error
    return true;
  };

  onMessageReceived = (payload: any) => {
    let message = JSON.parse(payload.body);
    switch (message.type) {
      case "JOINED":
        console.log(`You joined game ${message.gameId}`);
        break;
      case "ERROR":
        console.error(`ERROR : ${message.message}`);
        this.joinFormError = message.message;
        break;
      default:
        console.log(message);
    }
  };
}

export default App;

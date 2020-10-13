import * as React from "react";
import "./App.css";
import $ from "jquery";
import Stomp, { Frame } from "stompjs";
import WaitingMessage from "./waitingMessage";
import GameForm from "./gameForm";
import WaitingRoom from "./waitingRoom";
import GameRoom from "./gameRoom";
import _ from "lodash-es";
import {
  IErrorMessageContent,
  IJoinedMessageContent,
  IMessage,
  IMessageContent,
} from "./models";

class App extends React.Component {
  private stompClient: Stomp.Client;
  private stompSubscription: Stomp.Subscription;
  private gameId: string;

  state = {
    isWaitingForConnection: true,
    isWaitingToPlay: false,
    isJoining: false,
    isPlaying: false,
    joinFormError: "",
  };

  componentDidMount = () => {
    this.stompClient = Stomp.client("ws://localhost:8080/sock");
    this.stompClient.connect({}, this.onConnected, this.onError);
    this.setState({ joinFormError: "" });
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
            joinFormError={this.state.joinFormError}
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

  onMessageReceived = (payload: Frame) => {
    const message: IMessage = JSON.parse(payload.body);
    const content: IMessageContent = message.content;
    switch (message.type) {
      case "JOINED":
        const joinedMessageContent = message.content as IJoinedMessageContent;
        console.log(`You joined game ${joinedMessageContent.gameId}`);
        break;
      case "ERROR":
        const errorMessageContent = message.content as IErrorMessageContent;
        console.error(
          `ERROR (${errorMessageContent.code}): ${errorMessageContent.message}`
        );
        this.setState({
          joinFormError: (content as IErrorMessageContent).message,
        });
        break;
      default:
        console.log(message);
    }
  };
}

export default App;

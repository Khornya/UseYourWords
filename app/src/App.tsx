import * as React from "react";
import "./App.css";
import $ from "jquery";
import Stomp, { Frame } from "stompjs";
import WaitingMessage from "./waitingMessage";
import GameForm from "./gameForm";
import WaitingRoom from "./waitingRoom";
import GameRoom from "./gameRoom";
import { Element } from "./models"
import _ from "lodash-es";
import {
  IErrorMessageContent,
  IGameRoundMessageContent,
  IJoinedMessageContent,
  IMessage,
  IMessageContent,
  IPlayerJoinedMessageContent,
  IStartMessageContent,
  Team,
} from "./models";


class App extends React.Component {
  private stompClient: Stomp.Client;
  private stompPlayerSubscription: Stomp.Subscription;
  private stompGameSubscription: Stomp.Subscription;

  state = {
    isWaitingForConnection: true,
    isWaitingToPlay: false,
    isJoining: false,
    isPlaying: false,
    joinFormError: "",
    gameId: "",
    teams: ([] as Team[]),
    element: null as Element,
    roundNumber: 0
  };

  componentDidMount = () => {
    this.stompClient = Stomp.client("ws://localhost:8080/sock");
    this.stompClient.connect({}, this.onConnected, this.onError);
  };

  render = () => {
    return (
      <div className="app d-flex justify-content-center flex-column">
        <div className="banner d-flex justify-content-center">
          <h1>Use Your Words</h1>
        </div>
        {this.state.isWaitingToPlay && <WaitingRoom gameId={this.state.gameId} />}
        {this.state.isWaitingForConnection && <WaitingMessage />}
        {this.state.isJoining && (
          <GameForm
            stompClient={this.stompClient}
            joinFormError={this.state.joinFormError}
          />
        )}
        {this.state.isPlaying && <GameRoom gameId={this.state.gameId} teams={this.state.teams} element={this.state.element} roundNumber={this.state.roundNumber} />}
      </div>
    );
  };

  private onConnected = () => {
    console.log("connected");
    this.setState({
      isWaitingForConnection: false,
      isJoining: true,
    });
    this.stompPlayerSubscription = this.stompClient.subscribe(
      `/user/queue/reply`,
      this.onPlayerMessageReceived
    );
    return true;
  };

  private onError = (error: string | Frame) => {
    console.error("Error while connecting to websocket : ", error);
    // TODO: display error
    return true;
  };

  private onPlayerMessageReceived = (payload: Frame) => {
    const message: IMessage = JSON.parse(payload.body);
    const content: IMessageContent = message.content;
    switch (message.type) {
      case "JOINED":
        const joinedMessageContent = message.content as IJoinedMessageContent;
        console.log(`You joined game ${joinedMessageContent.gameId}`);
        this.stompGameSubscription = this.stompClient.subscribe(
          `/game-room/${joinedMessageContent.gameId}`,
          this.onGameMessageReceived
        )
        this.stompClient.send(`/app/ready`, {}, JSON.stringify(joinedMessageContent))
        this.setState({
          isJoining: false,
          isWaitingToPlay: true,
          joinFormError: "",
          gameId: joinedMessageContent.gameId
        })
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

  private onGameMessageReceived = (payload: Frame) => {
    const message: IMessage = JSON.parse(payload.body);
    const content: IMessageContent = message.content;
    switch (message.type) {
      case "START":
        this.setState({
          isWaitingToPlay: false,
          isPlaying: true,
          teams: (message.content as IStartMessageContent).teams
        })
        break;
      case "PLAYER_JOINED":
        const playerJoinedMessageContent = message.content as IPlayerJoinedMessageContent
        console.log(`${playerJoinedMessageContent.name} joined the game`)
        break;
      case "NEXT_ROUND":
        const gameRoundMessageContent = message.content as IGameRoundMessageContent
        this.setState({
          roundNumber: gameRoundMessageContent.roundNumber,
          element: gameRoundMessageContent.element
        })
        break;
      default:
        console.log(message)
    }
  }

}

export default App;

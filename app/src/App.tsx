import * as React from "react";
import "./App.css";
import Stomp, { Frame } from "stompjs";
import { stompClient, initStompClient } from "./stompClient";
import WaitingMessage from "./waitingMessage";
import GameForm from "./gameForm";
import WaitingRoom from "./waitingRoom";
import GameRoom from "./gameRoom";
import { Element, IEndRoundMessageContent, IHideAnswerMessageContent, ITimerMessageContent } from "./models"
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
  private stompPlayerSubscription: Stomp.Subscription;
  private stompGameSubscription: Stomp.Subscription;

  state = {
    isWaitingForConnection: true,
    isWaitingToPlay: false,
    isJoining: false,
    isPlaying: false,
    joinFormError: "",
    gameId: "",
    gameState: {
      teams: [] as Team[],
      element: null as Element,
      roundNumber: 0,
      showTimer: false,
      answers: [] as string[],
      displayAnswerForm: true,
      displayVoteForm: false,
      displayVoteResult: false,
      gameOver: false
    },
    playerIndex: -1,
    playerAnswerIndex: -1
  };

  componentDidMount = () => {
    initStompClient("ws://localhost:8080/sock");
    stompClient.connect({}, this.onConnected, this.onError);
  };

  render = () => {
    return (
      <div className="app d-flex justify-content-center flex-column">
        <div className="banner d-flex justify-content-center">
          <h1>Use Your Words</h1>
        </div>
        {this.state.isWaitingToPlay && <WaitingRoom gameId={this.state.gameId} />}
        {this.state.isWaitingForConnection && <WaitingMessage />}
        {this.state.isJoining && (<GameForm joinFormError={this.state.joinFormError} />)}
        {this.state.isPlaying && <GameRoom gameId={this.state.gameId} gameState={this.state.gameState} playerIndex={this.state.playerIndex} playerAnswerIndex={this.state.playerAnswerIndex}/>}
      </div>
    );
  };

  private onConnected = () => {
    console.log("connected");
    this.setState({
      isWaitingForConnection: false,
      isJoining: true,
    });
    this.stompPlayerSubscription = stompClient.subscribe(
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
        this.stompGameSubscription = stompClient.subscribe(
          `/game-room/${joinedMessageContent.gameId}`,
          this.onGameMessageReceived
        )
        stompClient.send(`/app/ready`, {}, JSON.stringify(joinedMessageContent))
        this.setState({
          isJoining: false,
          isWaitingToPlay: true,
          joinFormError: "",
          gameId: joinedMessageContent.gameId,
          playerIndex: joinedMessageContent.index
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
      case "HIDE_ANSWER":
        this.setState({
          playerAnswerIndex: (content as IHideAnswerMessageContent).answerIndex
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
          gameState: {
            ...this.state.gameState,
            teams: (message.content as IStartMessageContent).teams
          }
        })
        break;
      case "PLAYER_JOINED":
        const playerJoinedMessageContent = message.content as IPlayerJoinedMessageContent
        console.log(`${playerJoinedMessageContent.name} joined the game`)
        break;
      case "NEXT_ROUND":
        const gameRoundMessageContent = message.content as IGameRoundMessageContent
        this.setState({
          gameState: {
            ...this.state.gameState,
            roundNumber: gameRoundMessageContent.roundNumber,
            element: gameRoundMessageContent.element,
            displayVoteResult: false,
            displayAnswerForm: true
          }
        })
        break;
      case "TIMER":
        const timerMessageContent = message.content as ITimerMessageContent
        this.setState({
          gameState: {
            ...this.state.gameState,
            showTimer: true
          }
        })
        break;
      case "END_ROUND":
        const endRoundMessageContent = message.content as IEndRoundMessageContent
        this.setState({
          gameState: {
            ...this.state.gameState,
            answers: endRoundMessageContent.answers,
            displayVoteForm: true,
            displayAnswerForm: false
          }
        })
        break;
      case "VOTES":
        this.setState({
          gameState: {
            ...this.state.gameState,
            teams: (message.content as IStartMessageContent).teams,
            displayAnswerForm: false,
            displayVoteForm: false,
            displayVoteResult: true
          }
        })
        break;
      case "GAME_OVER":
        this.setState({
          gameState: {
            ...this.state.gameState,
            teams: (message.content as IStartMessageContent).teams,
            displayVoteResult: false,
            gameOver: true
          }
        })
      default:
        console.log(message)
    }
  }

}

export default App;

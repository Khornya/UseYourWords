import * as React from "react";
import { GameResult } from "./gameResult";
import { IGameState } from "../models";
import { Round } from "./round";
import { TeamComponent } from "./teamComponent";
import { VoteResult } from "./voteResult";

interface IGameRoomProps {
  gameId: string
  playerIndex: number
  playerAnswerIndex: number
  gameState: IGameState
}

class GameRoom extends React.Component<IGameRoomProps> {
  render = () => {
    return (
      <div className="game-room">
        {!this.props.gameState.gameOver && <div className="teams">
          {this.props.gameState.teams.map((team, index) => {
            return (
              <TeamComponent team={team} number={index + 1} key={index} displayScoreDiff={false} displayPlayers={true}/>
            )
          })}
        </div>}
        {this.props.gameState.roundNumber > 0 && !this.props.gameState.gameOver && <Round gameId={this.props.gameId} playerIndex={this.props.playerIndex} playerAnswerIndex={this.props.playerAnswerIndex} gameState={this.props.gameState}/>}
        <div className="rightColumn"></div>
        {this.props.gameState.displayVoteResult && <VoteResult teams={this.props.gameState.teams} />}
        {this.props.gameState.gameOver && <GameResult teams={this.props.gameState.teams} />}
      </div>
    )
  }
}

export default GameRoom;

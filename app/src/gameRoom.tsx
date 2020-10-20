import * as React from "react";
import { GameResult } from "./gameResult";
import { IGameState } from "./models";
import { Round } from "./round";
import { TeamComponent } from "./teamComponent";
import { VoteForm } from "./voteForm";
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
        <div className="teams">
          <h3>Teams</h3>
          {this.props.gameState.teams.map((team, index) => {
            return (
              <TeamComponent team={team} number={index + 1} key={index} displayScoreDiff={false}/>
            )
          })}
        </div>
        {this.props.gameState.roundNumber > 0 && this.props.gameState.displayAnswerForm && <Round gameId={this.props.gameId} playerIndex={this.props.playerIndex} roundNumber={this.props.gameState.roundNumber} element={this.props.gameState.element} showTimer={this.props.gameState.showTimer} displayAnswerForm={this.props.gameState.displayAnswerForm}/>}
        {this.props.gameState.displayVoteForm && <VoteForm gameId={this.props.gameId} answers={this.props.gameState.answers} playerAnswerIndex={this.props.playerAnswerIndex} />}
        {this.props.gameState.displayVoteResult && <VoteResult teams={this.props.gameState.teams} />}
        {this.props.gameState.gameOver && <GameResult teams={this.props.gameState.teams} />}
      </div>
    )
  }
}

export default GameRoom;

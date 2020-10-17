import * as React from "react";
import { IGameState } from "./models";
import { Round } from "./round";
import { TeamComponent } from "./teamComponent";
import { VoteForm } from "./voteForm";

interface IGameRoomProps {
  gameId: string
  playerIndex: number
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
              <TeamComponent team={team} number={index + 1} key={index}/>
            )
          })}
        </div>
        {this.props.gameState.roundNumber > 0 && !this.props.gameState.displayVoteForm && <Round gameId={this.props.gameId} playerIndex={this.props.playerIndex} roundNumber={this.props.gameState.roundNumber} element={this.props.gameState.element} showTimer={this.props.gameState.showTimer}/>}
        {this.props.gameState.displayVoteForm && <VoteForm gameId={this.props.gameId} answers={this.props.gameState.answers}/>}
      </div>
    )
  }
}

export default GameRoom;

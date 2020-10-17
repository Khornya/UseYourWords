import * as React from "react";
import { Team, Element, IGameState } from "./models";
import { Round } from "./round";
import Stomp from "stompjs";
import { TeamComponent } from "./teamComponent";

interface IGameRoomProps {
  gameId: string;
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
        {this.props.gameState.roundNumber > 0 && <Round roundNumber={this.props.gameState.roundNumber} element={this.props.gameState.element} showTimer={this.props.gameState.showTimer}/>}
      </div>
    )
  }
}

export default GameRoom;

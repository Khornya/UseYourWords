import * as React from "react";
import { Team, Element } from "./models";
import { Round } from "./round";
import { TeamComponent } from "./teamComponent";

interface IGameRoomProps {
  gameId: string;
  teams: Team[]
  element: Element
  roundNumber: number
  showTimer: boolean
}

class GameRoom extends React.Component<IGameRoomProps> {
  render = () => {
    return (
      <div className="game-room">
        <div className="teams">
          <h3>Teams</h3>
          {this.props.teams.map((team, index) => {
            return (
              <TeamComponent team={team} number={index + 1} key={index}/>
            )
          })}
        </div>
        {this.props.roundNumber > 0 && <Round roundNumber={this.props.roundNumber} element={this.props.element} showTimer={this.props.showTimer}/>}
      </div>
    )
  }
}

export default GameRoom;

import React from "react";
import { Team } from "../models";
import { TeamComponent } from "./teamComponent";

interface IVoteResultProps {
  teams: Team[];
  roundNumber: number
}

export class VoteResult extends React.Component<IVoteResultProps> {
  render = () => {
    return (
      <div className="voteResult">
        <h3>La manche {this.props.roundNumber} est terminée !</h3>
        <div className="voteResultTeams">
          {this.props.teams.map((team, index) => {
            return (
              <TeamComponent
                team={team}
                number={index + 1}
                key={index}
                displayScoreDiff={true}
                displayPlayers={false}
              />
            );
          })}
        </div>
      </div>
    );
  };
}

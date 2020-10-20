import React from "react";
import { Team } from "./models"
import { PlayerComponent } from "./playerComponent";

interface ITeamProps {
    team: Team
    number: number
    displayScoreDiff: boolean
}

export class TeamComponent extends React.Component<ITeamProps> {
    render = () => {
        return (
            <div className="team card border-primary">
                <div className="card-header bg-primary border-primary">Team {this.props.number}</div>
                <ul className="list-group list-group-flush">
                    {this.props.team.players.map((player, index) => {
                        return (
                            <PlayerComponent player={player} key={index} />
                        )
                    })}
                </ul>
                <div className="card-footer bg-light border-primary">{this.props.displayScoreDiff ? `${this.props.team.scoreDiff >= 0 ? "+" + this.props.team.scoreDiff : this.props.team.scoreDiff}` : this.props.team.score} pts</div>
            </div>
        )
    }
}
import React from "react";
import { Team } from "../models"
import { PlayerComponent } from "./playerComponent";

interface ITeamProps {
    team: Team
    number: number
    displayScoreDiff: boolean
    displayPlayers: boolean
}

export class TeamComponent extends React.Component<ITeamProps> {
    render = () => {
        let scoreColor;
        let primaryColor;
        if (this.props.displayScoreDiff) {
            if (this.props.team.scoreDiff >= 0) {
                scoreColor = "success"
            } else {
                scoreColor = "danger"
            }
        } else scoreColor = "primary"
        if (this.props.displayPlayers) {
            if (this.props.displayScoreDiff) {
                primaryColor = scoreColor
            } else {
                primaryColor = "success"
            }
        } else {
            primaryColor = "primary"
        }
        primaryColor = this.props.displayPlayers ? "primary" : scoreColor
        return (
            <div className={`team card border-${primaryColor}`}>
                <div className={`card-header bg-${primaryColor} border-${primaryColor}`}>Team {this.props.number}</div>
                {this.props.displayPlayers &&
                    <ul className="list-group list-group-flush">
                        {this.props.team.players.map((player, index) => {
                            return (
                                <PlayerComponent player={player} key={index} />
                            )
                        })}
                    </ul>}
                <div className={`card-footer bg-light border-${primaryColor}`}>{this.props.displayScoreDiff ? `${this.props.team.scoreDiff >= 0 ? "+" + this.props.team.scoreDiff : this.props.team.scoreDiff}` : this.props.team.score} pts</div>
            </div>
        )
    }
}
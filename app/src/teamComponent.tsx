import React from "react";
import {Team} from "./models"
import { PlayerComponent } from "./playerComponent";

interface ITeamProps {
    team: Team
    number: number
}

export class TeamComponent extends React.Component<ITeamProps> {
    render = () => {
        return (
            <div className="team">
                <h4>Team {this.props.number}</h4>
                <p>Score : {this.props.team.score}</p>
                <ul>
                    {this.props.team.players.map((player, index) => {
                        return (
                        <PlayerComponent player={player} key={index}/>
                        )
                    })}
                </ul>
            </div>
        )
    }
}
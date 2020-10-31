import React from "react"
import { Team } from "../models";
import { TeamComponent } from "./teamComponent";
import { proxy } from "../../package.json"

interface IGameResultProps {
    teams: Team[]
}

export class GameResult extends React.Component<IGameResultProps> {
    render = () => {
        const max = Math.max.apply(Math, this.props.teams.map(function (team) { return team.score; }))
        const winners = this.props.teams.filter((team) => { return team.score === max })
        return (
            <div className="gameResult">
                <h1>Team{winners.length === 1 ? ` ${(winners[0].number + 1).toString()} won !` : `s ${this.formatTeamNumbers(winners)} won !`}</h1>
                <div className="gameResultTeams">
                    {this.props.teams.map((team, index) => {
                        return (<TeamComponent team={team} number={index + 1} key={index} displayScoreDiff={false} displayPlayers={true} />)
                    })}
                </div>
                <a href={`${proxy}/home`} className="btn btn-primary" role="button">Back to main page</a>
            </div>
        )
    }

    private formatTeamNumbers = (teams: Team[]) => {
        let formattedString = ""
        for (let i = 0; i < teams.length - 2; i++) {
            formattedString += ((teams[i].number + 1) + ", ").toString();
        }
        formattedString += (teams[teams.length - 2].number + 1).toString() + " & " + (teams[teams.length - 1].number + 1).toString()
        return formattedString;
    }
}
import React from "react"
import { Team } from "./models";
import { TeamComponent } from "./teamComponent";

interface IVoteResultProps {
    teams: Team[]
}

export class VoteResult extends React.Component<IVoteResultProps> {
    render = () => {
        return (
            <div className="voteResult">
                {this.props.teams.map((team, index) => {
                    return (<TeamComponent team={team} number={index + 1} key={index} displayScoreDiff={true} displayPlayers={false}/>)
                })}
            </div>
        )
    }
}